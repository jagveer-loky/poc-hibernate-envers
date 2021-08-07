package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.entity.EReport;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

public interface IWriteReportRepository {

    /**
     * @param jobParams JobParams
     */
    void create(final JobParams jobParams);

    /**
     * @param jobParams JobParams
     */
    void runAsync(final EReport eReport, final JobParams jobParams);

    /**
     * @return Set<String>
     */
    String[] extractFieldsToIgnore(final JobParams jobParam);
}
