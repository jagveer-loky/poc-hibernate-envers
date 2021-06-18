package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.entity.EProposalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<EProposalData, Long> {

    @Query(name = "getReport1", nativeQuery = true)
    List<DReport1> getReport1(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, Collection<String> status);

    @Query(name = "getReport2", nativeQuery = true)
    List<DReport2> getReport2(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, Collection<String> status);

    @Query(name = "getReport3", nativeQuery = true)
    List<DReport3> getReport3();

    @Query(name = "getReport4", nativeQuery = true)
    List<DReport4> getReport4();

    @Query(name = "getReport5", nativeQuery = true)
    List<DReport5> getReport5();
}
