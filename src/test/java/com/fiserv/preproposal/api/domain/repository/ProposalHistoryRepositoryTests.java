package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.entity.EProposalHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest
class ProposalHistoryRepositoryTests {

    /**
     *
     */
    @Autowired
    ProposalHistoryRepository proposalHistoryRepository;

    /**
     *
     */
    @Test
    void testingOrder() {
        LocalDateTime dateTime = LocalDateTime.now().minusYears(5);

        for (final EProposalHistory eProposalHistory : proposalHistoryRepository.findByProposalDataIdOrderByInsertData(8271L)) {
            Assertions.assertTrue(dateTime.isBefore(eProposalHistory.getInsertData()));
            dateTime = eProposalHistory.getInsertData();
        }
    }


}
