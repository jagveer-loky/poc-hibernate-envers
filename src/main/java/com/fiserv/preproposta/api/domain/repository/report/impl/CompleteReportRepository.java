package com.fiserv.preproposta.api.domain.repository.report.impl;

import com.fiserv.preproposta.api.domain.dtos.CompleteReport;
import com.fiserv.preproposta.api.domain.repository.report.AbstractReportRepository;
import com.fiserv.preproposta.api.infrastrucutre.aid.util.ListUtil;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.stream.Collectors;

@Repository
public class CompleteReportRepository extends AbstractReportRepository<CompleteReport> {


    @Override
    public String[] extractFieldsToIgnore(String[] fields) {
        Assert.notNull(fields, "fields cannot be null");
        return ListUtil.toArray(new CompleteReport().extractFields().stream().filter(field -> Arrays.stream(fields).noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }

    @Override
    public BeanWriterProcessor<CompleteReport> configProcessor() {
        return new BeanWriterProcessor<>(CompleteReport.class);
    }
}
