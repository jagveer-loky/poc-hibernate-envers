package com.fiserv.preproposal.api.domain.repository;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@SpringBootTest
class ReportRepositoryTests {

    @Autowired
    ProposalRepository proposalRepository;

    /**
     *
     */
    @Test
    void basicReportMustBeContainOnlyFiservOnlineResponsesTypesMustPass() {
        final List<BasicReport> basicReport = proposalRepository.getBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countBasicReport = proposalRepository.getCountBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countBasicReport, basicReport.size());
    }

    /**
     *
     */
    @Test
    void basicReportMustBeNotContainFiservOnlineResponseTypeMustPass() {
        final List<BasicReport> basicReport = proposalRepository.getBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertNotEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countBasicReport = proposalRepository.getCountBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countBasicReport, basicReport.size());
    }

    /**
     *
     */
    @Test
    void basicReportMustReturnAllResponsesTypesMustPass() {
        final List<BasicReport> basicReport = proposalRepository.getBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, null, null).collect(Collectors.toList());
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty()) {
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("FISERV_ONLINE")));
//            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LEAD")));
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LNK_PAYMENT")));
        }
        final int countBasicReport = proposalRepository.getCountBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, null, null);
        Assertions.assertEquals(countBasicReport, basicReport.size());
    }

    /**
     *
     */
    @Test
    void quantitativeReportSeveralTestsTypesMustPass() {
        final List<QuantitativeReport> quantitativeReportsWithOnlyFiservOnline = proposalRepository.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        final int countQuantitativeReportWithOnlyFiservOnline = proposalRepository.getCountQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countQuantitativeReportWithOnlyFiservOnline, quantitativeReportsWithOnlyFiservOnline.size());
        final List<QuantitativeReport> quantitativeReportsWithoutFiservOnline = proposalRepository.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        final int countQuantitativeReportWithoutFiservOnline = proposalRepository.getCountQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countQuantitativeReportWithoutFiservOnline, quantitativeReportsWithoutFiservOnline.size());
        final List<QuantitativeReport> quantitativeReportsWithAllFiservOnline = proposalRepository.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, null, null).collect(Collectors.toList());
        final int countQuantitativeReportWithAllFiservOnline = proposalRepository.getCountQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, null, null);
        Assertions.assertEquals(countQuantitativeReportWithAllFiservOnline, quantitativeReportsWithAllFiservOnline.size());

        for (final QuantitativeReport quantitativeReportWithAllFiservOnline : quantitativeReportsWithAllFiservOnline) {
            for (final QuantitativeReport quantitativeReportWithoutFiservOnline : quantitativeReportsWithoutFiservOnline) {
                for (final QuantitativeReport quantitativeReportWithOnlyFiservOnline : quantitativeReportsWithOnlyFiservOnline) {
                    if (quantitativeReportWithAllFiservOnline != null && quantitativeReportWithoutFiservOnline != null && quantitativeReportWithOnlyFiservOnline != null && quantitativeReportWithAllFiservOnline.getFilename().equals(quantitativeReportWithoutFiservOnline.getFilename()) && quantitativeReportWithoutFiservOnline.getFilename().equals(quantitativeReportWithOnlyFiservOnline.getFilename())) {
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getProposals(), quantitativeReportWithOnlyFiservOnline.getProposals() + quantitativeReportWithoutFiservOnline.getProposals());
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getOnlineRejected(), quantitativeReportWithOnlyFiservOnline.getOnlineRejected() + quantitativeReportWithoutFiservOnline.getOnlineRejected());
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getCredOnline(), quantitativeReportWithOnlyFiservOnline.getCredOnline() + quantitativeReportWithoutFiservOnline.getCredOnline());
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getPendingInFiservOnline(), quantitativeReportWithOnlyFiservOnline.getPendingInFiservOnline() + quantitativeReportWithoutFiservOnline.getPendingInFiservOnline());
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getPendingComplement(), quantitativeReportWithOnlyFiservOnline.getPendingComplement() + quantitativeReportWithoutFiservOnline.getPendingComplement());
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getPreproposalError(), quantitativeReportWithOnlyFiservOnline.getPreproposalError() + quantitativeReportWithoutFiservOnline.getPreproposalError());
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getTmpCanceled(), quantitativeReportWithOnlyFiservOnline.getTmpCanceled() + quantitativeReportWithoutFiservOnline.getTmpCanceled());
                        Assertions.assertEquals(quantitativeReportWithAllFiservOnline.getFinished(), quantitativeReportWithOnlyFiservOnline.getFinished() + quantitativeReportWithoutFiservOnline.getFinished());
                    }
                }
            }
        }
    }

    /**
     *
     */
    @Test
    void completeReportMustBeContainOnlyFiservOnlineResponsesTypesMustPass() {
        final List<CompleteReport> completeReport = proposalRepository.getCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        if (!completeReport.isEmpty())
            completeReport.forEach(report -> Assertions.assertEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countCompleteReport = proposalRepository.getCountCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countCompleteReport, completeReport.size());
    }

    /**
     *
     */
    @Test
    void completeReportMustBeNotContainFiservOnlineResponseTypeMustPass() {
        final List<CompleteReport> completeReport = proposalRepository.getCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        Assertions.assertNotNull(completeReport);
        if (!completeReport.isEmpty())
            completeReport.forEach(report -> Assertions.assertNotEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countCompleteReport = proposalRepository.getCountCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countCompleteReport, completeReport.size());
    }

    /**
     *
     */
    @Test
    void completeReportMustReturnAllResponsesTypesMustPass() {
        final List<CompleteReport> completeReport = proposalRepository.getCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, null, null).collect(Collectors.toList());
        Assertions.assertNotNull(completeReport);
        if (!completeReport.isEmpty()) {
            Assertions.assertTrue(completeReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("FISERV_ONLINE")));
//            Assertions.assertTrue(proposalDataReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("LEAD")));
            Assertions.assertTrue(completeReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("LNK_PAYMENT")));
        }
        final int countCompleteReport = proposalRepository.getCountCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, null, null);
        Assertions.assertEquals(countCompleteReport, completeReport.size());
    }

}
