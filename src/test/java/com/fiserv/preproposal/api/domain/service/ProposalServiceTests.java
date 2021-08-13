package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.repository.ProposalHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@SpringBootTest
class ProposalServiceTests {

    /**
     *
     */
    @Autowired
    ProposalService proposalService;

    /**
     *
     */
    @Test
    void reloadLinkPaymentsTest() {
        proposalService.reloadLinkPayments(Stream.of(8271L, 8287L, 8295L, 8295L, 8295L, 8295L, 8295L, 8295L, 8271L, 8271L, 8271L).collect(Collectors.toCollection(HashSet::new)));
    }

    /**
     *
     */
    @Test
    void reloadLinkPaymentsTestMustFail() {
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> proposalService.reloadLinkPayments(Stream.of(8298L, 8287L, 8295L, 8295L, 8295L, 8295L, 8295L, 8295L, 8271L, 8271L, 8271L).collect(Collectors.toCollection(HashSet::new))));
    }


}
