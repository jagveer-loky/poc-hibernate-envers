package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.entity.EProposalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalHistoryRepository extends JpaRepository<EProposalHistory, Long> {

    List<EProposalHistory> findByProposalDataIdOrderByInsertData(final Long proposalDataId);

}
