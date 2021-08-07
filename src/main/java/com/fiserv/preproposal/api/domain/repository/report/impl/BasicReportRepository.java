package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;
@Repository
public class BasicReportRepository extends AbstractReportRepository<BasicReport> {

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
