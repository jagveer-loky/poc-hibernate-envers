package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class ReportServiceTests {

    /**
     *
     */
    @Autowired
    ReportService reportService;


//    /**
//     *
//     */
//    @Test
//    void quantitativeCSVReportMustReturnAllFieldsMustPass() {
//
//        final IOService<QuantitativeReport> ioService = new IOService<>();
//
//        final CsvParserSettings parserSettings = new CsvParserSettings();
//        parserSettings.setLineSeparatorDetectionEnabled(true);
//        parserSettings.setHeaderExtractionEnabled(true);
//
//        final RowListProcessor rowProcessor = new RowListProcessor();
//        parserSettings.setRowProcessor(rowProcessor);
//
//        final CsvParser parser = new CsvParser(parserSettings);
//        final byte[] completeQuantitativeCSVReport = proposalRepository.getQuantitativeCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null, null);
//        parser.parse(new ByteArrayInputStream(completeQuantitativeCSVReport));
//
//        final String[] oldHeaders = rowProcessor.getHeaders();
//
//        final Set<String> headersToRemove = new HashSet<>();
//        headersToRemove.add("Instituicao");
//        headersToRemove.add("Status do Arquivo");
//        headersToRemove.add("Nome do Arquivo");
//
//        headersToRemove.forEach(header -> Assertions.assertTrue(oldHeaders[0].contains(header)));
//
//        final byte[] croppedQuantitativeCSVReport = proposalRepository.getQuantitativeCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, ioService.extractFieldsToIgnore(QuantitativeReport.class, headersToRemove));
//        parser.parse(new ByteArrayInputStream(croppedQuantitativeCSVReport));
//
//        final String[] newHeaders = rowProcessor.getHeaders();
//
//        headersToRemove.forEach(header -> Assertions.assertFalse(newHeaders[0].contains(header)));
//
//        Assertions.assertNotEquals(oldHeaders[0].split(";").length, newHeaders[0].split(";").length);
//
//    }
//
//
//    /**
//     *
//     */
//    @Test
//    void completeCSVReportMustReturnAllFieldsMustPass() {
//
//        final IOService<CompleteReport> ioService = new IOService<>();
//
//        final CsvParserSettings parserSettings = new CsvParserSettings();
//        parserSettings.setLineSeparatorDetectionEnabled(true);
//        parserSettings.setHeaderExtractionEnabled(true);
//
//        final RowListProcessor rowProcessor = new RowListProcessor();
//        parserSettings.setRowProcessor(rowProcessor);
//
//        final CsvParser parser = new CsvParser(parserSettings);
//        // Get with all fields
//        final byte[] completeCompleteCSVReport = proposalRepository.getCompleteCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, null);
//        parser.parse(new ByteArrayInputStream(completeCompleteCSVReport));
//
//        final String[] oldHeaders = rowProcessor.getHeaders();
//
//        final Set<String> headersToRemove = new HashSet<>();
//        headersToRemove.add("ID da proposta");
//        headersToRemove.add("UserId");
//        headersToRemove.add("Instituicao");
//        headersToRemove.add("service_contract");
//        headersToRemove.add("Tecnologia");
//        headersToRemove.add("Erros");
//
//        headersToRemove.forEach(header -> Assertions.assertTrue(oldHeaders[0].contains(header)));
//
//        // Get with only some fields
//        final byte[] croppedCompleteCSVReport = proposalRepository.getCompleteCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, ioService.extractFieldsToIgnore(CompleteReport.class, headersToRemove));
//        parser.parse(new ByteArrayInputStream(croppedCompleteCSVReport));
//
//        final String[] newHeaders = rowProcessor.getHeaders();
//
//        headersToRemove.forEach(header -> Assertions.assertFalse(newHeaders[0].contains(header)));
//
//        Assertions.assertNotEquals(oldHeaders[0].split(";").length, newHeaders[0].split(";").length);
//
//    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromBasicReportClass() {
        final Set<String> fields = new BasicReport().extractFields();
        Assertions.assertEquals(23, fields.size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromCompleteReportClass() {
        final Set<String> fields = new CompleteReport().extractFields();
        Assertions.assertEquals(96, fields.size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromQuantitativeReportClass() {
        final Set<String> fields = new QuantitativeReport().extractFields();
        Assertions.assertEquals(12, fields.size());
    }

    /**
     *
     */
    @Test
    void testCalculatePercentage() {
//        reportService.createBasicCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, null);
        final EReport eReport = new EReport();
        eReport.setCountLines(805);

        for (int i = 1; i <= 805; i++) {
            eReport.setCurrentLine(i);
            eReport.calculatePercentage();
            if (i == 1)
                Assertions.assertEquals(eReport.getConcludedPercentage(), 0);
            if (i == 805)
                Assertions.assertEquals(eReport.getConcludedPercentage(), 100);
        }
        Assertions.assertEquals(eReport.getConcludedPercentage(), 100);
    }

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
                reportParams.setFields(new BasicReport().extractFields());
            } else if (i < 20) {
                reportParams.setType(TypeReport.COMPLETE);
                reportParams.setFields(new CompleteReport().extractFields());
            } else {
                reportParams.setType(TypeReport.QUANTITATIVE);
                reportParams.setFields(new QuantitativeReport().extractFields());
            }

            final EReport eReport = EReport.createFrom(reportParams);
            eReport.setContent(content);
            eReport.setCountLines((int) countLines);
            eReport.setCurrentLine(eReport.getCountLines());
            eReport.setRequestedDate(LocalDateTime.now().minusDays(2).minusMinutes(30));
            eReport.setConcludedDate(LocalDateTime.now());

            reportList.add(reportService.save(eReport));
        }

        // Delete expired
        reportService.deleteExpired();

        reportList.forEach(report -> Assertions.assertThrows(NotFoundException.class, () -> reportService.findById(report.getId())));
    }

}
