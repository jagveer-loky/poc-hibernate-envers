package com.fiserv.preproposal.api.domain.service.report;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.ReportRepository;
import com.fiserv.preproposal.api.domain.service.report.IInputReport;
import com.fiserv.preproposal.api.domain.service.report.IOutputReport;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.domain.entity.EReport.*;
import static com.fiserv.preproposal.api.infrastrucutre.aid.util.MessageSourceUtil.cropMessage;

@Service
@RequiredArgsConstructor
public class ReportService {


    /**
     *
     */
    private final static String DATE_PATTERN = "dd/MM/yyyy";

    /**
     *
     */
    private final static String TIME_PATTERN = "HH:mm";

    /**
     *
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     *
     */
    @Getter
    @Value("${reports.days-to-expire:1}")
    private Long daysToExpire;

    /**
     *
     */
    private final RedissonClient client;

    /**
     *
     */
    private final ReportRepository reportRepository;

    /**
     *
     */
    private final ProposalRepository proposalRepository;

    /**
     *
     */
    private final ReportProcessorService reportProcessorService;

    /**
     * @param id long
     * @return EReport
     */
    public EReport findById(final long id) {
        return this.reportRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * @param eReport IOutputReport
     * @return EReport
     */
    public EReport save(final IOutputReport eReport) {
        return this.reportRepository.save((EReport) eReport);
    }

    /**
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "startBasicReport")
    public void startBasicReport(final ReportParams reportParams, final EReport eReport) {

        try {

            LOGGER.info("STARTING " + eReport.getId() + " BASIC REPORT!     REQUESTER:" + reportParams.getRequester() + "; INSTITUTION: " + reportParams.getInstitution() + "; SERVICE CONTRACT: " + reportParams.getServiceContract() + "; INITIAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(reportParams.getInitialDate()) + "; FINAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(reportParams.getFinalDate()) + "; NOT IN: " + reportParams.getNotIn() + "; RESPONSES TYPES: " + reportParams.getResponsesTypes() + "; STATUS: " + reportParams.getStatus());

            eReport.setCountLines(proposalRepository.getCountBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

            final Stream<BasicReport> basicStream = proposalRepository.getBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

            reportProcessorService.convertToCSV(basicStream, reportParams, eReport,
                    nextLineOutputReport -> BackgroundJob.enqueue(() -> startNext(nextLineOutputReport)),
                    this::done,
                    errorInLineOutputReport -> BackgroundJob.enqueue(() -> startNext(errorInLineOutputReport)),
                    this::generalError
            );
        } catch (final Exception e) {
            e.printStackTrace();
            eReport.setError(cropMessage(e.getMessage(), 254));
            generalError(eReport);
        }
    }

    /**
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "startCompleteReport")
    public void startCompleteReport(final ReportParams reportParams, final EReport eReport) {

        try {
            LOGGER.info("STARTING " + eReport.getId() + " COMPLETE REPORT!     REQUESTER:" + reportParams.getRequester() + "; INSTITUTION: " + reportParams.getInstitution() + "; SERVICE CONTRACT: " + reportParams.getServiceContract() + "; INITIAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(reportParams.getInitialDate()) + "; FINAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(reportParams.getFinalDate()) + "; NOT IN: " + reportParams.getNotIn() + "; RESPONSES TYPES: " + reportParams.getResponsesTypes() + "; STATUS: " + reportParams.getStatus());

            eReport.setCountLines(proposalRepository.getCountCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

            final Stream<CompleteReport> completeStream = proposalRepository.getCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

            reportProcessorService.convertToCSV(completeStream, reportParams, eReport,
                    nextLineOutputReport -> BackgroundJob.enqueue(() -> startNext(nextLineOutputReport)),
                    this::done,
                    errorInLineOutputReport -> BackgroundJob.enqueue(() -> startNext(errorInLineOutputReport)),
                    this::generalError
            );
        } catch (final Exception e) {
            e.printStackTrace();
            eReport.setError(cropMessage(e.getMessage(), 254));
            generalError(eReport);
        }
    }

    /**
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "startQuantitativeReport")
    public void startQuantitativeReport(final ReportParams reportParams, final EReport eReport) {
        try {
            LOGGER.info("STARTING " + eReport.getId() + " QUANTITATIVE REPORT!     REQUESTER:" + reportParams.getRequester() + "; INSTITUTION: " + reportParams.getInstitution() + "; SERVICE CONTRACT: " + reportParams.getServiceContract() + "; INITIAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(reportParams.getInitialDate()) + "; FINAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(reportParams.getFinalDate()) + "; NOT IN: " + reportParams.getNotIn() + "; RESPONSES TYPES: " + reportParams.getResponsesTypes() + "; STATUS: " + reportParams.getStatus());

            eReport.setCountLines(proposalRepository.getCountQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

            final Stream<QuantitativeReport> quantitativeStream = proposalRepository.getQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

            reportProcessorService.convertToCSV(quantitativeStream, reportParams, eReport,
                    nextLineOutputReport -> BackgroundJob.enqueue(() -> startNext(nextLineOutputReport)),
                    this::done,
                    errorInLineOutputReport -> BackgroundJob.enqueue(() -> startNext(errorInLineOutputReport)),
                    this::generalError
            );
        } catch (final Exception e) {
            e.printStackTrace();
            eReport.setError(cropMessage(e.getMessage(), 254));
            generalError(eReport);
        }
    }

    /**
     * Save with async mode the entity in the database.
     * Used to update percentage in database.
     *
     * @param output IOutputReport
     */
    @Job(name = "startNext")
    public void startNext(final IOutputReport output) {
        if (ReportProcessorService.getLoadings().get(output.getId()) <= output.getConcludedPercentage()) {
            save(output);
            LOGGER.info(output.getConcludedPercentage() + "% OF " + output.getId() + " " + output.getType() + " REPORT IS DONE! (THIS REPORT WAS REQUESTED IN " + DateTimeFormatter.ofPattern(DATE_PATTERN + " " + TIME_PATTERN).format(output.getRequestedDate()) + ")");
        }
    }

    /**
     * Save with NON async mode the entity in the database.
     * Used to save 100% percentage of the file processed in database.
     *
     * @param output IOutputReport
     */
    public void done(final IOutputReport output) {
        save(output);
        LOGGER.info("DONE " + output.getType() + " REPORT " + output.getId() + " - 100% CONCLUDED. (THIS REPORT WAS REQUESTED IN " + DateTimeFormatter.ofPattern(DATE_PATTERN + " " + TIME_PATTERN).format(output.getRequestedDate()) + ")");
    }

    /**
     * Save with NON async mode the entity in the database.
     * Used to save general/critical error of the file processing in the database.
     *
     * @param output IOutputReport
     */
    public void generalError(final IOutputReport output) {
        save(output);
        LOGGER.info(" REPORT " + output.getId() + " PRESENT GENERAL ERROR: " + output.getError());
    }

    /**
     * @param id Long
     * @return byte[]
     */
    @Transactional
    public byte[] downloadById(final Long id) throws NotFoundException {

        final EReport eReport = reportRepository.findById(id).orElseThrow(NotFoundException::new);

        Assert.isTrue(eReport.getConcludedPercentage() == 100 && eReport.getConcludedDate() != null, "O relatório ainda não foi processado");

        return eReport.getContent();

    }

    /**
     * @param requester String
     * @return List<EReport>
     */
    public List<EReport> findByRequester(final String requester) {
        return this.reportRepository.findByRequesterOrAllOrderByRequestedDateDesc(requester);
    }

    /**
     *
     */
    public void deleteExpiredReports() {
        final LocalDateTime today = LocalDateTime.now();
        final RLock todayLock = getLock("deleteExpiredReports" + DateTimeFormatter.ofPattern(DATE_PATTERN).format(today));

        Assert.isTrue(!todayLock.isLocked(), "Job 'deleteExpiredReports' já executado");

        todayLock.lock();
        deleteBeforeYesterday();
    }

    /**
     *
     */
    @Transactional
    public void deleteBeforeYesterday() {
        final LocalDateTime today = LocalDateTime.now();
        LOGGER.info("DELETING EXPIRED REPORTS");
        reportRepository.deleteInBatch(this.reportRepository.getBeforeAt(today.minusDays(daysToExpire)));
        LOGGER.info("DELETED EXPIRED REPORTS");
    }

    /**
     * @param id long
     */
    @Transactional
    public void deleteById(final long id) {
        reportRepository.deleteById(id);
    }

    /**
     * @param lockName String
     * @return RLock
     */
    private RLock getLock(final String lockName) {
        return client.getLock(lockName);
    }

    /**
     *
     */
    public void generateReports() {

        final LocalDate today = LocalDate.now();
        final RLock todayLock = getLock("generateReports" + DateTimeFormatter.ofPattern(DATE_PATTERN).format(today));

        Assert.isTrue(!todayLock.isLocked(), "Job 'generateReports' já executado");

        todayLock.lock();
        generateReports(today);
    }

    /**
     *
     */
    public void generateReports(final LocalDate now) {

        LOGGER.info("GENERATING DAILY REPORTS");

        final ReportParams reportParams = new ReportParams();
        reportParams.setInitialDate(now.minusDays(daysToExpire));
        reportParams.setFinalDate(now);
        reportParams.setRequester(SYSTEM_USER);
        reportParams.setServiceContract(SERVICE_CONTRACT);
        reportParams.setInstitution(INSTITUTION);
        reportParams.setResponsesTypes(Arrays.asList("FISERV_ONLINE", "LEAD", "LEAD_FISERV", "LNK_PAYMENT", "MANUAL_PROC"));

        reportParams.setType(TypeReport.BASIC);
        reportParams.setFields(new BasicReport().extractFields());
        final EReport basicReport = save(EReport.createFrom(reportParams));
        BackgroundJob.enqueue(() -> startBasicReport(reportParams, basicReport));

        reportParams.setType(TypeReport.COMPLETE);
        reportParams.setFields(new CompleteReport().extractFields());
        final EReport completeReport = save(EReport.createFrom(reportParams));
        BackgroundJob.enqueue(() -> startCompleteReport(reportParams, completeReport));

        reportParams.setType(TypeReport.QUANTITATIVE);
        reportParams.setFields(new QuantitativeReport().extractFields());
        final EReport quantitativeReport = save(EReport.createFrom(reportParams));
        BackgroundJob.enqueue(() -> startQuantitativeReport(reportParams, quantitativeReport));

    }

    /**
     * @return List<EReport>
     */
    @Transactional(readOnly = true)
    public List<EReport> findAll() {
        return reportRepository.findAll();
    }

    /**
     * @return HashMap<String, Set < String>>
     */
    public HashMap<String, Set<String>> getFieldsFromReports() {
        final HashMap<String, Set<String>> fieldsFromReports = new HashMap<>();
        fieldsFromReports.put(TypeReport.BASIC_VALUE, new BasicReport().extractFields());
        fieldsFromReports.put(TypeReport.COMPLETE_VALUE, new CompleteReport().extractFields());
        fieldsFromReports.put(TypeReport.QUANTITATIVE_VALUE, new QuantitativeReport().extractFields());
        return fieldsFromReports;
    }
}
