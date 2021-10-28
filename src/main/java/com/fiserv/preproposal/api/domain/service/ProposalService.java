package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.entity.EProposalData;
import com.fiserv.preproposal.api.domain.entity.EProposalHistory;
import com.fiserv.preproposal.api.domain.repository.ProposalHistoryRepository;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProposalService {

    /**
     *
     */
    private final ProposalRepository proposalRepository;

    /**
     *
     */
    private final ProposalHistoryRepository proposalHistoryRepository;

    /**
     * @param ids Set<Long>
     */
    @Transactional
    @Job(name = "reloadLinkPayments")
    public void reloadLinkPayments(final Set<Long> ids) {
        ids.forEach(id -> proposalRepository.findById(id).ifPresent(eProposalData -> {

            // Check type of proposal
            Assert.isTrue(eProposalData.getResponseType().equals("LNK_PAYMENT"), "Contém propostas que não são links de pagamento");

            // Check if origin proposal no have errors
            proposalHistoryRepository.findByProposalDataIdOrderByInsertData(eProposalData.getIdOrigin()).forEach(eProposalHistory -> {
                Assert.isTrue(!eProposalHistory.getStatus().toString().contains("_ERROR"), "A proposta original contém erros");
            });

            final EProposalHistory proposalHistory = getLastProposalHistoryFromProposalDataId(id);
            proposalHistoryRepository.deleteById(proposalHistory.getId());
        }));
    }

    /**
     * @param proposalId Long
     * @return EProposalHistory
     */
    public EProposalHistory getLastProposalHistoryFromProposalDataId(final Long proposalId) {
        final List<EProposalHistory> proposalHistories = proposalHistoryRepository.findByProposalDataIdOrderByInsertData(proposalId);
        return proposalHistories.get(proposalHistories.size() - 1);
    }

    /**
     * @return Set<Long>
     */
    public Set<Long> getLinksPaymentsToReload() {
        return proposalRepository.getLinksPaymentsToReload().stream().map(EProposalData::getId).collect(Collectors.toSet());
    }

    /**
     *
     */
    public void getAndReloadLinksPayments() {
        reloadLinkPayments(getLinksPaymentsToReload());
    }
}
