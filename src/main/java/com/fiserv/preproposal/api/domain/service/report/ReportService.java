package com.fiserv.preproposal.api.domain.service.report;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.ReportRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.application.runnable.ThreadsComponent.execute;
import static com.fiserv.preproposal.api.domain.entity.EReport.*;
import static com.fiserv.preproposal.api.infrastrucutre.aid.util.MessageSourceUtil.cropMessage;

@Service
@Transactional(timeout = 9999999)
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
    private final StringRedisTemplate stringRedisTemplate;

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
        return reportRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * @param eReport IOutputReport
     * @return EReport
     */
    public EReport save(final IOutputReport eReport) {
        return reportRepository.save((EReport) eReport);
    }

    /**
     * @param eReport EReport
     */
    @Transactional(timeout = 9999999)
    public void createBasicReport(final EReport eReport) {

        try {

            LOGGER.info("STARTING " + eReport.getId() + " BASIC REPORT!     REQUESTER:" + eReport.getRequester() + "; INSTITUTION: " + eReport.getInstitution() + "; SERVICE CONTRACT: " + eReport.getServiceContract() + "; INITIAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(eReport.getInitialDate()) + "; FINAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(eReport.getFinalDate()) + "; NOT IN: " + eReport.getNotIn() + "; RESPONSES TYPES: " + eReport.getResponsesTypes() + "; STATUS: " + eReport.getStatus());

            final Stream<?> stream = proposalRepository.getBasicReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus());

            reportProcessorService.convertToCSV((Stream<AbstractReport>) stream, eReport, this::next, this::done);
        } catch (final Exception e) {
            e.printStackTrace();
            eReport.setError(cropMessage(e.getMessage(), 254));
            error(eReport);
        }
    }

    /**
     * @param eReport EReport
     */
    @Transactional(timeout = 9999999)
    public void createCompleteReport(final EReport eReport) {

        try {

            LOGGER.info("STARTING " + eReport.getId() + " COMPLETE REPORT!     REQUESTER:" + eReport.getRequester() + "; INSTITUTION: " + eReport.getInstitution() + "; SERVICE CONTRACT: " + eReport.getServiceContract() + "; INITIAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(eReport.getInitialDate()) + "; FINAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(eReport.getFinalDate()) + "; NOT IN: " + eReport.getNotIn() + "; RESPONSES TYPES: " + eReport.getResponsesTypes() + "; STATUS: " + eReport.getStatus());

            final Stream<?> stream = proposalRepository.getCompleteReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus());

            reportProcessorService.convertToCSV((Stream<AbstractReport>) stream, eReport, this::next, this::done);
        } catch (final Exception e) {
            e.printStackTrace();
            eReport.setError(cropMessage(e.getMessage(), 254));
            error(eReport);
        }
    }

    /**
     * @param eReport EReport
     */
    @Transactional(timeout = 9999999)
    public void createQuantitativeReport(final EReport eReport) {

        try {

            LOGGER.info("STARTING " + eReport.getId() + " QUANTITATIVE REPORT!     REQUESTER:" + eReport.getRequester() + "; INSTITUTION: " + eReport.getInstitution() + "; SERVICE CONTRACT: " + eReport.getServiceContract() + "; INITIAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(eReport.getInitialDate()) + "; FINAL DATE: " + DateTimeFormatter.ofPattern(DATE_PATTERN).format(eReport.getFinalDate()) + "; NOT IN: " + eReport.getNotIn() + "; RESPONSES TYPES: " + eReport.getResponsesTypes() + "; STATUS: " + eReport.getStatus());

            final Stream<?> stream = proposalRepository.getQuantitativeReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus());

            reportProcessorService.convertToCSV((Stream<AbstractReport>) stream, eReport, this::next, this::done);
        } catch (final Exception e) {
            e.printStackTrace();
            eReport.setError(cropMessage(e.getMessage(), 254));
            error(eReport);
        }
    }

    /**
     * Used ONLY to update percentage in redis database.
     *
     * @param output IOutputReport
     */
    public void next(final IOutputReport output) {
        stringRedisTemplate.opsForValue().set(output.getId().toString(), output.getCurrentLine().toString());
        LOGGER.info(output.getConcludedPercentage() + "% OF " + output.getId() + " " + output.getType() + " REPORT IS DONE! (THIS REPORT WAS REQUESTED IN " + DateTimeFormatter.ofPattern(DATE_PATTERN + " " + TIME_PATTERN).format(output.getRequestedDate()) + ")");
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
    public void error(final IOutputReport output) {
        save(output);
        LOGGER.info(" REPORT " + output.getId() + " PRESENT A GENERAL ERROR: " + output.getError());
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
        final List<EReport> reports = this.reportRepository.findByRequesterOrAllOrderByRequestedDateDesc(requester);
        reports.forEach(output -> {

            if (!output.getConcludedPercentage().equals(100) && stringRedisTemplate.opsForValue().get(output.getId().toString()) != null) {
                output.setCurrentLine(Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(output.getId().toString()))));
                output.calculatePercentage();
            }
        });
        return reports;
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
        LOGGER.info("DELETING EXPIRED REPORTS");
        final LocalDateTime today = LocalDateTime.now();
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
     * TODO testar
     */
    public void generateReports(final LocalDate now) {

        LOGGER.info("GENERATING DAILY REPORTS");

        final EReport eReport = new EReport();
        eReport.setInitialDate(now.minusDays(daysToExpire));
        eReport.setFinalDate(now);
        eReport.setRequester(SYSTEM_USER);
        eReport.setServiceContract(SERVICE_CONTRACT);
        eReport.setInstitution(INSTITUTION);
        eReport.setResponsesTypes(Arrays.asList("FISERV_ONLINE", "LEAD", "LEAD_FISERV", "LNK_PAYMENT", "MANUAL_PROC"));

        eReport.setType(TypeReport.BASIC);
        eReport.setFields(new BasicReport().extractLabels());
        eReport.setCountLines(getCountBasicReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus()));
        execute(() -> createBasicReport(save(eReport)));

        eReport.setType(TypeReport.COMPLETE);
        eReport.setFields(new CompleteReport().extractLabels());
        eReport.setCountLines(getCountCompleteReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus()));
        execute(() -> createCompleteReport(save(eReport)));

        eReport.setType(TypeReport.QUANTITATIVE);
        eReport.setFields(new QuantitativeReport().extractLabels());
        eReport.setCountLines(getCountQuantitativeReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus()));
        execute(() -> createQuantitativeReport(save(eReport)));

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
    public HashMap<String, Collection<String>> getFieldsFromReports() {
        final HashMap<String, Collection<String>> fieldsFromReports = new HashMap<>();
        fieldsFromReports.put(TypeReport.BASIC_VALUE, new BasicReport().extractLabels());
        fieldsFromReports.put(TypeReport.COMPLETE_VALUE, new CompleteReport().extractLabels());
        fieldsFromReports.put(TypeReport.QUANTITATIVE_VALUE, new QuantitativeReport().extractLabels());
        return fieldsFromReports;
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String
     * @param status          Collection<String>
     * @return int
     */
    public int getCountCompleteReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCountCompleteReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String
     * @param status          Collection<String>
     * @return int
     */
    public int getCountBasicReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCountBasicReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String
     * @param status          Collection<String>
     * @return int
     */
    public int getCountQuantitativeReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCountQuantitativeReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
    }
}
