package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.entity.EProposalData;
import com.fiserv.preproposal.api.domain.entity.EProposalHistory;
import com.fiserv.preproposal.api.domain.entity.EnumProposalResponseType;
import com.fiserv.preproposal.api.domain.repository.ProposalHistoryRepository;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

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
     * @return Boolean
     */
    @Transactional
    public Boolean reloadLinkPayments(final Set<Long> ids) {
        for (final Long id : ids) {
            proposalRepository.findById(id).ifPresent(eProposalData -> {

                // Check type of proposal
                Assert.isTrue(eProposalData.getResponseType().equals(EnumProposalResponseType.LNK_PAYMENT), "Contém propostas que não são links de pagamento");

                // Check if origin proposal no have errors
                proposalHistoryRepository.findByProposalDataIdOrderByInsertData(eProposalData.getIdOrigin()).forEach(eProposalHistory -> {
                    Assert.isTrue(!eProposalHistory.getStatus().toString().contains("_ERROR"), "A proposta original contém erros");
                });

                final EProposalHistory proposalHistory = getLastProposalHistoryFromProposalDataId(id);
            });
        }
        return true;
    }

    /**
     * @param proposalId Long
     * @return EProposalHistory
     */
    public EProposalHistory getLastProposalHistoryFromProposalDataId(final Long proposalId) {
        final List<EProposalHistory> proposalHistories = proposalHistoryRepository.findByProposalDataIdOrderByInsertData(proposalId);
        return proposalHistories.get(proposalHistories.size() - 1);
    }
}
