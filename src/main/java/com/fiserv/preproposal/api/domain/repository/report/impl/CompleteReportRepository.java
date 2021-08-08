package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;
@Repository
public class CompleteReportRepository extends AbstractReportRepository<CompleteReport> {

    /**
     * @param reportParams JobParams
     * @return String[]
     */
    @Override
    public String[] extractFieldsToIgnore(final ReportParams reportParams) {
        Assert.notNull(reportParams, "JobParams cannot be null");
        return toArray(new CompleteReport().extractFields().stream().filter(field -> reportParams.getFields() != null && reportParams.getFields().stream().noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }
}
