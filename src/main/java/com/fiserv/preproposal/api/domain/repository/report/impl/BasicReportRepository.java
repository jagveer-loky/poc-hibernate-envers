package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Stream;

@Slf4j
@Repository
public class BasicReportRepository extends AbstractReportRepository<BasicReport> {

    /**
     *
     */
    @Autowired
    ProposalRepository proposalRepository; //TODO

    /**
     * @param path String
     * @return byte[]
     */
    @Override
    public File read(final String path) {
        return new File(path);
    }

    /**
     * @param ownerJob        String
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @param fields          Collection<String>
     */
    @Override
    public void create(final String ownerJob, final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status, final Collection<String> fields) {

        // Extract the params to putting to job
        final JobParams jobParams = JobParams.builder().institution(institution).serviceContract(serviceContract).initialDate(initialDate).finalDate(finalDate).notIn(notIn).responsesTypes(responsesTypes).status(status).fields(fields).build();

        // Start the job
        BackgroundJob.enqueue(() -> {
            runJob(ownerJob, jobParams);
        });
    }

    /**
     * @param ownerJob  String
     * @param jobParams JobParams
     */
    @Transactional(readOnly = true)
    @Job(name = "Generating basic report to %0", retries = 2)
    public void runJob(final String ownerJob, final JobParams jobParams) {

        // Setting path of file
        setPath(getPath() + "/" + ownerJob + "/" + BasicReport.NAME + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_PATTERN)));

        log.info(String.format("Generating basic report to the user '%s' in the '%s' file", ownerJob, getPath()));

        // Get data as stream
        final Stream<BasicReport> stream = proposalRepository.getBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());

        // Write the stream data in to the file
        this.convertToCSV(stream, BasicReport.class, jobParams.getFields());
    }

    /**
     * @param path String
     */
    @Override
    public void remove(final String path) {

    }
}
