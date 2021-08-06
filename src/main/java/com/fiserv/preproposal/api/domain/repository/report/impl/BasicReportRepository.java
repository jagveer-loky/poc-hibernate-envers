package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.entity.EProposalData;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Slf4j
@Repository
public class BasicReportRepository extends AbstractReportRepository<BasicReport> {

    /**
     * @param ownerJob  String
     * @param jobParams JobParams
     */
    @Override
    public void run(final String ownerJob, final JobParams jobParams) {
        // Config and set path of the file
        setFullPath(getPath() + "/" + ownerJob + "/" + BasicReport.NAME + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_PATTERN)));
        log.info(String.format("Generating basic report to user '%s' in the '%s' file", ownerJob, getFullPath()));

        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setPath(getFullPath());
        eReport.setOwner(ownerJob);
        eReport.setCountLines(0);
        eReport.setRequestedDate(LocalDateTime.now());
        reportRepository.save(eReport);

        // Start the job
        BackgroundJob.enqueue(() -> runAsync(ownerJob, eReport, jobParams));
    }

    @Transactional
    @Job(name = "Generating basic report to %0", retries = 2)
    public void runAsync(final String ownerJob, final EReport eReport, final JobParams jobParams) {

        // Get count of the report
        final int countLines = proposalRepository.getCountBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());

        // Set count lines in tb
        eReport.setCountLines(countLines);

        // Save async report
        save(eReport);

        // Get data as stream
        final Stream<BasicReport> stream = proposalRepository.getBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());

        // Write the stream data in to the file
        convertToCSV(stream, eReport, BasicReport.class, jobParams.getFields());
        log.info(String.format("Generated basic report to user '%s' in the '%s' file", ownerJob, getFullPath()));

    }
}
