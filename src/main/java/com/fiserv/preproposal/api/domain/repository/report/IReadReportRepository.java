package com.fiserv.preproposal.api.domain.repository.report;

import java.io.File;

public interface IReadReportRepository {

    /**
     * @param path String
     * @return File
     */
    File read(final String path);
}
