package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;
@Repository
public class BasicReportRepository extends AbstractReportRepository<BasicReport> {

    @Override
    public String[] extractFieldsToIgnore(String[] fields) {
        Assert.notNull(fields, "fields cannot be null");
        return toArray(new BasicReport().extractFields().stream().filter(field -> Arrays.stream(fields).noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }

    @Override
    public BeanWriterProcessor<BasicReport> configProcessor() {
        return new BeanWriterProcessor<>(BasicReport.class);
    }
}
