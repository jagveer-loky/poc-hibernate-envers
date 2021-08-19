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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

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
        eReport.setConcludedPercentage(counter.get(eReport.getId()));
        eReport.calculatePercentage();
        save(eReport);
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

        basicReportRepository.convertToCSV(basicStream, new File(eReport.getPath()), reportParams.getFields(),
                completeReport -> next(eReport),
                exception -> {
                    show(exception);
                    next(eReport);
                });
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

        completeReportRepository.convertToCSV(completeStream, new File(eReport.getPath()), reportParams.getFields(),
                completeReport -> next(eReport),
                exception -> {
                    show(exception);
                    next(eReport);
                });
    }

    /**
     * @param exception Exception
     */
    private void show(final Exception exception) {
        exception.printStackTrace();
        log.error("");
        log.error(exception.getMessage());
        System.err.println(exception.getMessage());
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
     * @param reportParams ReportParams
     * @param eReport      EReport
     */
    @Transactional
    @Job(name = "Generating quantitative report", retries = 2)
    public void startQuantitativeReport(final ReportParams reportParams, final EReport eReport) {

        eReport.setCountLines(proposalRepository.getCountQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        final Stream<QuantitativeReport> quantitativeStream = proposalRepository.getQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus());

        quantitativeReportRepository.convertToCSV(quantitativeStream, new File(eReport.getPath()), reportParams.getFields(),
                completeReport -> next(eReport),
                exception -> {
                    show(exception);
                    next(eReport);
                });
    }

    /**
     * @param id Long
     * @return byte[]
     * @throws NotFoundException
     * @throws IOException
     */
    @Transactional
    public byte[] downloadById(final Long id) throws NotFoundException, IOException {

        final EReport eReport = reportRepository.findById(id).orElseThrow(NotFoundException::new);

        final byte[] fileToReturn = Files.readAllBytes(new File(eReport.getPath()).toPath());

        if (eReport.getConcludedPercentage() == 100) {

            // Delete from system of file
            Files.deleteIfExists(new File(eReport.getPath()).toPath());

            // Delete from Database
            reportRepository.deleteById(eReport.getId());

            // Delete from Counter
            counter.remove(eReport.getId());
        }
        return fileToReturn;
    }

    /**
     * @param requester String
     * @return List<EReport>
     */
    public List<EReport> findByRequester(final String requester) {
        return this.reportRepository.findByRequesterOrderByRequestedDateDesc(requester);
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
