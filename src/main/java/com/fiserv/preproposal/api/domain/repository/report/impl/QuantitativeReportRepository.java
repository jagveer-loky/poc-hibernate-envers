package com.fiserv.preproposal.api.domain.repository.report.impl;

import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil;
import com.fiserv.preproposal.api.domain.repository.report.AbstractReportRepository;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.stream.Collectors;

@Repository
public class QuantitativeReportRepository extends AbstractReportRepository<QuantitativeReport> {

    /**
     * @param fields String[]
     * @return String[]
     */
    @Override
    public String[] extractFieldsToIgnore(final String[] fields) {
        Assert.notNull(fields, "fields cannot be null");
        return ListUtil.toArray(new QuantitativeReport().extractFields().stream().filter(field -> Arrays.stream(fields).noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }

    /**
     * @return BeanWriterProcessor<QuantitativeReport>
     */
    @Override
    public BeanWriterProcessor<QuantitativeReport> configProcessor() {
        return new BeanWriterProcessor<>(QuantitativeReport.class);
    }
}
