package com.fiserv.preproposal.api.domain.repository.report;

import com.univocity.parsers.common.processor.BeanWriterProcessor;

public interface IWriteReportRepository<T> {

    /**
     * @return Set<String>
     */
    String[] extractFieldsToIgnore(final String[] fields);

    BeanWriterProcessor<T> configProcessor();
}
