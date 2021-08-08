package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.ReportParams;

public interface IWriteReportRepository {

    /**
     * @return Set<String>
     */
    String[] extractFieldsToIgnore(final ReportParams jobParam);
}
