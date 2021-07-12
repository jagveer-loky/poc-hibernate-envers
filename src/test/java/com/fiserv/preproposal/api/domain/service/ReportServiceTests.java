package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class ReportServiceTests {

    @Autowired
    ReportService reportService;

    /**
     *
     */
    @Test
    void basicReportMustBeContainOnlyFiservOnlineResponsesTypesMustPass() {
        final List<BasicReport> basicReport = reportService.getBasicReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertEquals(report.getResponseType(), "FISERV_ONLINE"));
    }

    /**
     *
     */
    @Test
    void basicReportMustBeNotContainFiservOnlineResponseTypeMustPass() {
        final List<BasicReport> basicReport = reportService.getBasicReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertNotEquals(report.getResponseType(), "FISERV_ONLINE"));
    }

    /**
     *
     */
    @Test
    void basicReportMustReturnAllResponsesTypesMustPass() {
        final List<BasicReport> basicReport = reportService.getBasicReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), null, null, null);
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty()) {
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("FISERV_ONLINE")));
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LEAD")));
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LNK_PAYMENT")));
        }
    }

}
