package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.JobParams;

public interface IWriteReportRepository {

    /**
     * @return Set<String>
     */
    String[] extractFieldsToIgnore(final JobParams jobParam);
}
