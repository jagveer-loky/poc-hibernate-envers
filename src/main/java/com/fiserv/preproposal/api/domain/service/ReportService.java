package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.ReportRepository;
import com.fiserv.preproposal.api.domain.repository.report.impl.BasicReportRepository;
import com.fiserv.preproposal.api.domain.repository.report.impl.CompleteReportRepository;
import com.fiserv.preproposal.api.domain.repository.report.impl.QuantitativeReportRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.domain.entity.EReport.*;

@Slf4j
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
    private final BasicReportRepository basicReportRepository;

    /**
     *
     */
    private final CompleteReportRepository completeReportRepository;

    /**
     *
     */
    private final QuantitativeReportRepository quantitativeReportRepository;

    /**
     *
     */
    private final HashMap<Long, Integer> loadings = new HashMap<>();


    /**
     * @param id long
     * @return EReport
     */
    public EReport findById(final long id) {
        return this.reportRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * @param eReport EReport
     * @return EReport
     */
    public EReport save(final EReport eReport) {
        return this.reportRepository.save(eReport);
    }

    /**
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "startBasicReport", retries = 2)
    public void startBasicReport(final ReportParams reportParams, final EReport eReport) {

        eReport.setCountLines(proposalRepository.getCountBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        // Init loading
        loadings.put(eReport.getId(), eReport.getConcludedPercentage());

        final Stream<BasicReport> basicStream = proposalRepository.getBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

        basicReportRepository.convertToCSV(basicStream, eReport.getCountLines(), reportParams,
                nextLineByteArray -> next(eReport, nextLineByteArray),
                doneByteArray -> done(eReport, doneByteArray),
                lineException -> {
                    show(lineException);
                    next(eReport, eReport.getContent());
                },
                generalException -> {
                    show(generalException);
                    eReport.setError(generalException.getMessage());
                    save(eReport);
                }
        );
    }

    /**
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "startCompleteReport", retries = 2)
    public void startCompleteReport(final ReportParams reportParams, final EReport eReport) {

        eReport.setCountLines(proposalRepository.getCountCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        // Init counter
        loadings.put(eReport.getId(), eReport.getConcludedPercentage());

        final Stream<CompleteReport> completeStream = proposalRepository.getCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

        completeReportRepository.convertToCSV(completeStream, eReport.getCountLines(), reportParams,
                nextLineByteArray -> next(eReport, nextLineByteArray),
                doneByteArray -> done(eReport, doneByteArray),
                lineException -> {
                    show(lineException);
                    next(eReport, eReport.getContent());
                },
                generalException -> {
                    show(generalException);
                    eReport.setError(generalException.getMessage());
                    save(eReport);
                }
        );
    }

    /**
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "startQuantitativeReport", retries = 2)
    public void startQuantitativeReport(final ReportParams reportParams, final EReport eReport) {

        eReport.setCountLines(proposalRepository.getCountQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        // Init loading
        loadings.put(eReport.getId(), eReport.getConcludedPercentage());

        final Stream<QuantitativeReport> quantitativeStream = proposalRepository.getQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

        quantitativeReportRepository.convertToCSV(quantitativeStream, eReport.getCountLines(), reportParams,
                nextLineByteArray -> next(eReport, nextLineByteArray),
                doneByteArray -> done(eReport, doneByteArray),
                lineException -> {
                    show(lineException);
                    next(eReport, eReport.getContent());
                },
                generalException -> {
                    show(generalException);
                    eReport.setError(generalException.getMessage());
                    save(eReport);
                }
        );
    }

    /**
     * @param exception Exception
     */
    private void show(final Exception exception) {
        exception.printStackTrace();
        log.error(exception.getMessage());
    }

    /**
     * @param eReport EReport
     */
    private void next(final EReport eReport, final byte[] byteArray) {

        eReport.setContent(byteArray);

        eReport.setCurrentLine(eReport.getCurrentLine() + 1);

        if (eReport.getConcludedPercentage() % eReport.getType().getMultiplierToSave() == 0)
            if (loadings.get(eReport.getId()) != null && loadings.get(eReport.getId()) < eReport.getConcludedPercentage()) {
                loadings.put(eReport.getId(), eReport.getConcludedPercentage());
                BackgroundJob.enqueue(() -> startNext(eReport));
            }
    }

    /**
     * @param eReport EReport
     */
    @Job(name = "startNext", retries = 2)
    public void startNext(final EReport eReport) {
        if (loadings.get(eReport.getId()) != null && loadings.get(eReport.getId()) <= 75) {
            log.info(loadings.get(eReport.getId()) + "% OF REPORT " + eReport.getId() + " HAS DONE! (THIS REPORT WAS REQUESTED IN " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(eReport.getRequestedDate()) + ")");
            eReport.setConcludedPercentage(loadings.get(eReport.getId()));
            eReport.calculatePercentage();
            save(eReport);
        }
    }

    /**
     * @param eReport   EReport
     * @param byteArray byte[]
     */
    private void done(final EReport eReport, final byte[] byteArray) {
//        loadings.remove(eReport.getId());
        eReport.setContent(byteArray);
        eReport.setCurrentLine(eReport.getCountLines());
        eReport.setConcludedDate(LocalDateTime.now());
        eReport.calculatePercentage();
        BackgroundJob.enqueue(() -> startDone(eReport));
    }

    /**
     * @param eReport EReport
     */
    @Job(name = "startDone", retries = 2)
    public void startDone(final EReport eReport) {
        log.info("DONE REPORT (100%)" + eReport.getId() + " (THIS REPORT WAS REQUESTED IN " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(eReport.getRequestedDate()) + ")");
        loadings.remove(eReport.getId());
        save(eReport);
    }

    /**
     * @param id Long
     * @return byte[]
     */
    @Transactional
    public byte[] downloadById(final Long id) throws NotFoundException {

        final EReport eReport = reportRepository.findById(id).orElseThrow(NotFoundException::new);

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
     * @return HashMap<String, Set < String>>
     */
    public HashMap<String, Set<String>> getFieldsFromReports() {
        final HashMap<String, Set<String>> fieldsFromReports = new HashMap<>();
        fieldsFromReports.put(TypeReport.BASIC_VALUE, new BasicReport().extractFields());
        fieldsFromReports.put(TypeReport.COMPLETE_VALUE, new CompleteReport().extractFields());
        fieldsFromReports.put(TypeReport.QUANTITATIVE_VALUE, new QuantitativeReport().extractFields());
        return fieldsFromReports;
    }

    /**
     *
     */
    @Transactional
    public void deleteExpired() {
        log.info("DELETING EXPIRED REPORTS");
        reportRepository.deleteInBatch(this.reportRepository.getBeforeAt(LocalDateTime.now().minusDays(daysToExpire)));
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

        Assert.isTrue(!todayLock.isLocked(), "Job já executado");

        todayLock.lock();
        generateReports(today);
    }

    /**
     *
     */
    public void generateReports(final LocalDate now) {

        log.info("GENERATING DAILY REPORTS");

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

}
