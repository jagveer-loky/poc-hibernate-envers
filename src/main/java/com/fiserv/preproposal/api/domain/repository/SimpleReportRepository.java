package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.dtos.SimpleReport;
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
public interface SimpleReportRepository extends JpaRepository<SimpleReport, Long> {

    /**
     * @param institution     String
     * @param serviceContract String
     * @return Stream<BasicReport>
     */
//    @Query(
//            name = "simpleNamedNativeQuery",
//            countProjection = "tpd.id",
////                    "       FROM TB_PROPOSAL_DATA" +
////                    "  LEFT join TB_FILE_CONTROL TFC ON TFC.ID = TB_PROPOSAL_DATA.id_file_control" +
////                    "  WHERE tfc.INSTITUTION = :institution AND tfc.SERVICE_CONTRACT = :serviceContract ",
//            countQuery =
//                    "       FROM TB_PROPOSAL_DATA tpd" +
//                    "  LEFT join TB_FILE_CONTROL tfc ON tfc.ID = tpd.id_file_control" +
//                    "  WHERE tfc.INSTITUTION = :institution AND tfc.SERVICE_CONTRACT = :serviceContract ",
//            nativeQuery = true
//    )
//    Page<SimpleReport> getCompleteReport(@Param("institution") final String institution, @Param("serviceContract") final String serviceContract, Pageable page);
    @Query(value = "select tpd.id from TB_PROPOSAL_DATA tpd",
            countQuery = "select count(id) from TB_PROPOSAL_DATA tpd",
            nativeQuery = true)
    Page<SimpleReport> getCompleteReport(/*@Param("institution") final String institution, @Param("serviceContract") final String serviceContract, */Pageable page);

}
