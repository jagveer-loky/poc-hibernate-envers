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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String SYSTEM_USER = "SYSTEM";
    private static final String SERVICE_CONTRACT = "149";
    private static final String INSTITUTION = "149";

    /**
     *
     */
    @Getter
    @Value("${reports.days-to-expire:2}")
    private Long daysToExpire;

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
    private final HashMap<Long, Integer> counter = new HashMap<>();


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
     * @param eReport EReport
     */
    @Job(name = "Updating in the database", retries = 2)
    public void update(final EReport eReport) {
        if (counter.get(eReport.getId()) != null) {
            eReport.setConcludedPercentage(counter.get(eReport.getId()));
            eReport.calculatePercentage();
            save(eReport);
        }
    }

    /**
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "Generating basic report", retries = 2)
    public void startBasicReport(final ReportParams reportParams, final EReport eReport) {

        eReport.setCountLines(proposalRepository.getCountBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        final Stream<BasicReport> basicStream = proposalRepository.getBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

        basicReportRepository.convertToCSV(basicStream, eReport, reportParams.getFields(),
                report -> next(eReport),
                lineException -> {
                    show(lineException);
                    next(eReport);
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
    @Job(name = "Generating complete report", retries = 2)
    public void startCompleteReport(final ReportParams reportParams, final EReport eReport) {

        eReport.setCountLines(proposalRepository.getCountCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        final Stream<CompleteReport> completeStream = proposalRepository.getCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

        completeReportRepository.convertToCSV(completeStream, eReport, reportParams.getFields(),
                report -> next(eReport),
                lineException -> {
                    show(lineException);
                    next(eReport);
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
    @Job(name = "Generating quantitative report", retries = 2)
    public void startQuantitativeReport(final ReportParams reportParams, final EReport eReport) {

        eReport.setCountLines(proposalRepository.getCountQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        final Stream<QuantitativeReport> quantitativeStream = proposalRepository.getQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

        quantitativeReportRepository.convertToCSV(quantitativeStream, eReport, reportParams.getFields(),
                report -> next(eReport),
                lineException -> {
                    show(lineException);
                    next(eReport);
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
    private void next(final EReport eReport) {

        eReport.setCurrentLine(eReport.getCurrentLine() + 1);

        if (eReport.getConcludedPercentage() % 5 == 0)
            if (counter.get(eReport.getId()) == null || counter.get(eReport.getId()) < eReport.getConcludedPercentage()) {
                counter.put(eReport.getId(), eReport.getConcludedPercentage());
                BackgroundJob.enqueue(() -> update(eReport));
            }
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
        this.reportRepository.getBeforeAt(LocalDateTime.now().minusDays(daysToExpire)).forEach(eReport -> this.reportRepository.deleteById(eReport.getId()));
    }

    /**
     *
     */
    public void createReports() {

        final ReportParams reportParams = new ReportParams();
        reportParams.setInitialDate(LocalDate.now().minusDays(1));
        reportParams.setFinalDate(LocalDate.now());
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
     * @param id long
     */
    @Transactional
    public void deleteById(final long id) {
        reportRepository.deleteById(id);
    }
}
