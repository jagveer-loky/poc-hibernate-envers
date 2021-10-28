package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.entity.EReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.fiserv.preproposal.api.domain.entity.EReport.SYSTEM_USER;

@Repository
public interface ReportRepository extends JpaRepository<EReport, Long> {

    /**
     * @param requester String
     * @return List<EReport>
     */
    @Query("SELECT new EReport( e.id, e.requester,e.requestedDate, e.concludedDate, e.concludedPercentage, e.countLines, e.currentLine, e.type, e.error) " +
            " FROM EReport e WHERE (e.requester = :requester OR e.requester = '" + SYSTEM_USER + "') order by e.requestedDate desc")
    List<EReport> findByRequesterOrAllOrderByRequestedDateDesc(@Param("requester") final String requester);

    /**
     * @param dateTime LocalDateTime
     * @return List<EReport>
     */
    @Query("SELECT new EReport( e.id, e.requester,e.requestedDate, e.concludedDate, e.concludedPercentage, e.countLines, e.currentLine, e.type, e.error) " +
            " FROM EReport e WHERE (e.requestedDate <= :dateTime)")
    List<EReport> getBeforeAt(final LocalDateTime dateTime);
}
