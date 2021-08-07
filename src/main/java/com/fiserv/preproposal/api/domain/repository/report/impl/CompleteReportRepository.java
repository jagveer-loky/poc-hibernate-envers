package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;
@Repository
public class CompleteReportRepository extends AbstractReportRepository<CompleteReport> {

    /**
     * @param jobParams JobParams
     * @return String[]
     */
    @Override
    public String[] extractFieldsToIgnore(final JobParams jobParams) {
        Assert.notNull(jobParams, "JobParams cannot be null");
        return toArray(new CompleteReport().extractFields().stream().filter(field -> jobParams.getFields() != null && jobParams.getFields().stream().noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }
}
