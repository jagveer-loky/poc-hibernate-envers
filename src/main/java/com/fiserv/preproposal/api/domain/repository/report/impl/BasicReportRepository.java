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
     * @param jobParams JobParams
     */
    @Override
    public void runAsync(final JobParams jobParams) {
        // Config and set path of the file
        setFullPath((getPath() + "/" + jobParams.getRequester() + "/" + jobParams.getType() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_PATTERN))).toLowerCase()); // TODO MASTIGAÇÃO
        log.info(String.format("Generating basic report to requester '%s' in the '%s' file", jobParams.getRequester(), getFullPath()));

        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setPath(getFullPath());
        eReport.setType(jobParams.getType());
        eReport.setRequester(jobParams.getRequester());
        eReport.setCountLines(0);
        eReport.setRequestedDate(LocalDateTime.now());
        reportRepository.save(eReport);

        // TODO por o stream Start the job
        BackgroundJob.enqueue(() -> runAsync(jobParams.getRequester(), eReport, jobParams));
    }

    @Transactional
    @Job(name = "Generating basic report to %0", retries = 2)
    public void runAsync(final String jobName, final EReport eReport, final JobParams jobParams) {

        // Get count of the report
        // todo meter if aqui
        final int countLines = proposalRepository.getCountBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());

        // Set count lines in tb
        eReport.setCountLines(countLines);

        // Save async report
        saveAsync(eReport);

        // Get data as stream
        final Stream<BasicReport> /*TODO acoplamento */stream = proposalRepository.getBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());

        // Write the stream data in to the file TODO passar jpParams.getBeanType
        convertToCSV(stream, eReport, jobParams);
        log.info(String.format("Generated basic report to user '%s' in the '%s' file", jobName, getFullPath()));

    }
}
