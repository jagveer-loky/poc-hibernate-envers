package com.fiserv.preproposalApi.domain.repository;

import com.fiserv.preproposalApi.domain.entity.EProposalData;
import com.fiserv.preproposalApi.domain.dtos.DReport1;
import com.fiserv.preproposalApi.domain.dtos.DReport2;
import com.fiserv.preproposalApi.domain.dtos.DReport3;
import com.fiserv.preproposalApi.domain.dtos.DReport4;
import com.fiserv.preproposalApi.domain.dtos.DReport5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<EProposalData, Long> {

    @Query(name = "getReport1", nativeQuery = true)
    List<DReport1> getReport1(String institution, String serviceContract, LocalDate initialDate, LocalDate finalDate, List<String> status);

    @Query(name = "getReport2", nativeQuery = true)
    List<DReport2> getReport2(String institution, String serviceContract, LocalDate initialDate, LocalDate finalDate, List<String> status);

    @Query(name = "getReport3", nativeQuery = true)
    List<DReport3> getReport3();

    @Query(name = "getReport4", nativeQuery = true)
    List<DReport4> getReport4();

    @Query(name = "getReport5", nativeQuery = true)
    List<DReport5> getReport5();
}
