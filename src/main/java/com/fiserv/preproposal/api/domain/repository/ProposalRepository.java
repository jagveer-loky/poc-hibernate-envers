package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.entity.EProposalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

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
     * @return int
     */
    @Query(name = "getCountBasicReport", nativeQuery = true)
    int getCountBasicReport(@Param("institution") final String institution,
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
     * @return Stream<BasicReport>
     */
    @Query(name = "getBasicReport", nativeQuery = true)
    Stream<BasicReport> getBasicReport(@Param("institution") final String institution,
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
     * @return Stream<QuantitativeReport>
     */
    @Query(name = "getQuantitativeReport", nativeQuery = true)
    Stream<QuantitativeReport> getQuantitativeReport(@Param("institution") final String institution,
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
     * @return int
     */
    @Query(name = "getCountQuantitativeReport", nativeQuery = true)
    int getCountQuantitativeReport(@Param("institution") final String institution,
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
     * @return Stream<CompleteReport>
     */
    @Query(name = "getCompleteReport", /*countName = "getCompleteReport.count",*/  nativeQuery = true)
    Page<CompleteReport> getCompleteReport(@Param("institution") final String institution,
                                           @Param("serviceContract") final String serviceContract,
                                           /*@Param("initialDate") final LocalDate initialDate,
                                           @Param("finalDate") final LocalDate finalDate,
                                           @Param("notIn") final boolean notIn,
                                           @Param("responsesTypes") final Collection<String> responsesTypes,
                                           @Param("status") final Collection<String> status,*/
                                           Pageable page);

//    /**
//     * @param institution     String
//     * @param serviceContract String
//     * @param initialDate     LocalDate
//     * @param finalDate       LocalDate
//     * @param notIn           Boolean
//     * @param responsesTypes  Collection<String>
//     * @param status          Collection<String>
//     * @return int
//     */
//    @Query(name = "getCompleteReport.count", nativeQuery = true)
//    int getCountCompleteReport(@Param("institution") final String institution,
//                               @Param("serviceContract") final String serviceContract,
//                               @Param("initialDate") final LocalDate initialDate,
//                               @Param("finalDate") final LocalDate finalDate,
//                               @Param("notIn") final boolean notIn,
//                               @Param("responsesTypes") final Collection<String> responsesTypes,
//                               @Param("status") final Collection<String> status);


    @Query(value = "SELECT link" +
            " FROM EProposalData link " +
            " WHERE" +
            "       link.responseType = 'LNK_PAYMENT'" +
            "   AND ( SELECT COUNT(historyLink.id) FROM EProposalHistory historyLink WHERE historyLink.proposalData.id = link.id AND historyLink.status LIKE '%_ERROR') > 0" +
            "   AND ( SELECT COUNT(historyOrigin.id) FROM EProposalHistory historyOrigin WHERE historyOrigin.proposalData.id = link.idOrigin AND historyOrigin.status LIKE '%_ERROR') = 0")
    Set<EProposalData> getLinksPaymentsToReload();
}
