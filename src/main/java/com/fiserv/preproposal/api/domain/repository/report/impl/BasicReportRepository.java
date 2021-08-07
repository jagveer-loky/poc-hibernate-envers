package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;

@Slf4j
@Repository
public class BasicReportRepository extends AbstractReportRepository<BasicReport> {

    /**
     * @param eReport   EReport
     * @param jobParams JobParams
     */
    @Override
    public void runAsync(final EReport eReport, final JobParams jobParams) {
        // TODO por o stream Start the job
        BackgroundJob.enqueue(() -> runAsync(jobParams.getRequester(), eReport, jobParams));
    }

    @Transactional
    @Job(name = "Generating basic report to %0", retries = 2)
    public void runAsync(final String jobName, final EReport eReport, final JobParams jobParams) {

        // Get count of the report
        final int countLines = proposalRepository.getCountBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());

        // Set count lines in tb
        eReport.setCountLines(countLines);

        // Save async report
        saveAsync(eReport);

        // Get data as stream
        final Stream<BasicReport> stream = proposalRepository.getBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());

        // Write the stream data in to the file TODO passar jpParams.getBeanType
        convertToCSV(stream, eReport, jobParams);

    }

    /**
     * @param jobParams JobParams
     * @return String[]
     */
    @Override
    public String[] extractFieldsToIgnore(final JobParams jobParams) {
        Assert.notNull(jobParams, "JobParams cannot be null");
        return toArray(new BasicReport().extractFields().stream().filter(field -> jobParams.getFields() != null && jobParams.getFields().stream().noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }
}
