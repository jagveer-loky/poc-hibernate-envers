package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.report.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class ReportServiceTests {

    /**
     *
     */
    @Autowired
    ReportService reportService;

    /**
     *
     */
    @Test
    void deleteExpiredMustPass() {

        final List<EReport> reportList = new ArrayList<>();

        // Populate file
        byte[] content = new byte[0];
        long countLines = 0;
        final File file = new File("src/main/java/com/fiserv/preproposal/api/domain/dtos/CompleteReport.java");
        try {
            content = Files.readAllBytes(file.toPath());
            countLines = Files.lines(file.toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 30; i++) {

            final ReportParams reportParams = new ReportParams();

            reportParams.setInitialDate(LocalDate.now().minusYears(500));
            reportParams.setFinalDate(LocalDate.now());
            reportParams.setRequester("SYSTEM");
            reportParams.setServiceContract("149");
            reportParams.setInstitution("00000007");
            reportParams.setResponsesTypes(Collections.singletonList("FISERV_ONLINE"));

            if (i < 10) {
                reportParams.setType(TypeReport.BASIC);
                reportParams.setFields(new BasicReport().extractLabels());
            } else if (i < 20) {
                reportParams.setType(TypeReport.COMPLETE);
                reportParams.setFields(new CompleteReport().extractLabels());
            } else {
                reportParams.setType(TypeReport.QUANTITATIVE);
                reportParams.setFields(new QuantitativeReport().extractLabels());
            }

            final EReport eReport = EReport.createFrom(reportParams);
            eReport.setContent(content);
            eReport.setCountLines((int) countLines);
            eReport.setCurrentLine(eReport.getCountLines());
            eReport.setRequestedDate(LocalDateTime.now().minusDays(reportService.getDaysToExpire()).minusMinutes(30));
            eReport.setConcludedDate(LocalDateTime.now());

            reportList.add(reportService.save(eReport));
        }

        // Delete expired
        reportService.deleteExpiredReports();

        reportList.forEach(report -> Assertions.assertThrows(NotFoundException.class, () -> reportService.findById(report.getId())));
    }

    /**
     *
     */
    @Test
    void createReportsMustPass() {

        // Erase reports
        reportService.findAll().forEach(eReport -> this.reportService.deleteById(eReport.getId()));

        Assertions.assertEquals(0, reportService.findAll().size());

        // Create reports
        reportService.generateReports();

        Assertions.assertEquals(3, reportService.findAll().size());
    }

}
