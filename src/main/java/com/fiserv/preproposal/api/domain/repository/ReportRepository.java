package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.entity.EReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<EReport, Long> {

    /**
     * @param requester String
     * @return List<EReport>
     */
    List<EReport> findByRequesterOrderByRequestedDateDesc(final String requester);
}
