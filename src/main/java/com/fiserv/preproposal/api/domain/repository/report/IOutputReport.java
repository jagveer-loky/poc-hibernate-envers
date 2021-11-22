package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.entity.TypeReport;

import java.time.temporal.TemporalAccessor;
import java.util.Collection;

public interface IOutputReport {

    String getRequester();

    Integer getCountLines();

    Long getId();

    Integer getCurrentLine();

    void setCurrentLine(final Integer currentLine);

    Integer getConcludedPercentage();

    TypeReport getType();

    void setContent(final byte[] readAllBytes);

    void calculatePercentage();

    TemporalAccessor getRequestedDate();

    void setError(final String message);

    String getError();
}
