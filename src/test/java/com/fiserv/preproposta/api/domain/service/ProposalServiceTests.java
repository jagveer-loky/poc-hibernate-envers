package com.fiserv.preproposta.api.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        proposalService.reloadLinkPayments(proposalService.getLinksPaymentsToReload());
    }

    /**
     *
     */
    @Test
    void getAndReloadLinkPaymentsTest() {
        proposalService.getAndReloadLinksPayments();
    }


    /**
     *
     */
    @Test
    void reloadLinkPaymentsTestMustPass() {
        proposalService.reloadLinkPayments(Stream.of(8295L).collect(Collectors.toCollection(HashSet::new)));
    }

    /**
     *
     */
    @Test
    void reloadLinkPaymentsTestMustFail() {
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> proposalService.reloadLinkPayments(Stream.of(8271L).collect(Collectors.toCollection(HashSet::new))));
    }

}
