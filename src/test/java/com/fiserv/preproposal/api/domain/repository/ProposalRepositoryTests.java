package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.entity.EProposalHistory;
import com.fiserv.preproposal.api.domain.entity.EnumProposalResponseType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest
class ProposalRepositoryTests {

    /**
     *
     */
    @Autowired
    ProposalRepository proposalRepository;

    /**
     *
     */
    @Test
    void getLinksPaymentsToReloadTest() {
        proposalRepository.getLinksPaymentsToReload().forEach(eProposalData -> Assertions.assertEquals(EnumProposalResponseType.LNK_PAYMENT, eProposalData.getResponseType()));
    }


}
