package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.entity.EProposalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<EProposalData, Long> {

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<BasicReport>
     */
    @Query(name = "getBasicReport", nativeQuery = true)
    List<BasicReport> getBasicReport(@Param("institution") final String institution,
                                     @Param("serviceContract") final String serviceContract,
                                     @Param("initialDate") final LocalDate initialDate,
                                     @Param("finalDate") final LocalDate finalDate,
                                     @Param("notIn") final boolean notIn,
                                     @Param("responsesTypes") final Collection<String> responsesTypes,
                                     @Param("status") final Collection<String> status);

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<QuantitativeReport>
     */
    @Query(name = "getQuantitativeReport", nativeQuery = true)
    List<QuantitativeReport> getQuantitativeReport(@Param("institution") final String institution,
                                                   @Param("serviceContract") final String serviceContract,
                                                   @Param("initialDate") final LocalDate initialDate,
                                                   @Param("finalDate") final LocalDate finalDate,
                                                   @Param("notIn") final boolean notIn,
                                                   @Param("responsesTypes") final Collection<String> responsesTypes,
                                                   @Param("status") final Collection<String> status);

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<CompleteReport>
     */
    @Query(name = "getCompleteReport", nativeQuery = true)
    List<CompleteReport> getCompleteReport(@Param("institution") final String institution,
                                           @Param("serviceContract") final String serviceContract,
                                           @Param("initialDate") final LocalDate initialDate,
                                           @Param("finalDate") final LocalDate finalDate,
                                           @Param("notIn") final boolean notIn,
                                           @Param("responsesTypes") final Collection<String> responsesTypes,
                                           @Param("status") final Collection<String> status);
}
