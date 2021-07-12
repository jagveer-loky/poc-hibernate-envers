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

    @Query(name = "getBasicReport", nativeQuery = true)
    List<BasicReport> getBasicReport(@Param("institution") final String institution,
                                     @Param("serviceContract") final String serviceContract,
                                     @Param("initialDate") final LocalDate initialDate,
                                     @Param("finalDate") final LocalDate finalDate,
                                     @Param("in") final boolean in,
                                     @Param("responsesTypes") final Collection<String> responsesTypes,
                                     @Param("status") final Collection<String> status);

    @Query(name = "getQuantitativeReport", nativeQuery = true)
    List<QuantitativeReport> getQuantitativeReport(@Param("institution") final String institution,
                                                   @Param("serviceContract") final String serviceContract,
                                                   @Param("initialDate") final LocalDate initialDate,
                                                   @Param("finalDate") final LocalDate finalDate,
                                                   @Param("in") final boolean in,
                                                   @Param("responsesTypes") final Collection<String> responsesTypes,
                                                   @Param("status") final Collection<String> status);

    @Query(name = "getReport3", nativeQuery = true)
    List<DReport3> getReport3(@Param("institution") final String institution,
                              @Param("serviceContract") final String serviceContract,
                              @Param("initialDate") final LocalDate initialDate,
                              @Param("finalDate") final LocalDate finalDate,
                              @Param("status") final Collection<String> status);

    @Query(name = "getReport4", nativeQuery = true)
    List<DReport4> getReport4(@Param("institution") final String institution,
                              @Param("serviceContract") final String serviceContract,
                              @Param("initialDate") final LocalDate initialDate,
                              @Param("finalDate") final LocalDate finalDate,
                              @Param("status") final Collection<String> status);

    @Query(name = "getReport5", nativeQuery = true)
    List<DReport5> getReport5(@Param("institution") final String institution,
                              @Param("serviceContract") final String serviceContract,
                              @Param("initialDate") final LocalDate initialDate,
                              @Param("finalDate") final LocalDate finalDate,
                              @Param("status") final Collection<String> status);
}
