package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.JobParams;

import java.time.LocalDate;
import java.util.Collection;

public interface IWriteReportRepository {

    /**
     * @param ownerJob        String
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @param fields          Collection<String>
     */
    void create(final String ownerJob, final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status, final Collection<String> fields);

    /**
     * @param path String
     */
    void remove(final String path);
}
