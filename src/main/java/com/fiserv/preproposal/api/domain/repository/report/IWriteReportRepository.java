package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.JobParams;

import java.time.LocalDate;
import java.util.Collection;

public interface IWriteReportRepository {

    /**
     * @param jobParams JobParams
     */
    void create(final JobParams jobParams);

    /**
     * @param jobParams JobParams
     */
    void runAsync(final JobParams jobParams);

    /**
     * @param path String
     */
    void remove(final String path);
}
