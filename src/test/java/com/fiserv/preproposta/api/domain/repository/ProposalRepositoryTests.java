package com.fiserv.preproposta.api.domain.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        proposalRepository.getLinksPaymentsToReload().forEach(eProposalData -> Assertions.assertEquals("LNK_PAYMENT", eProposalData.getResponseType()));
    }


}
