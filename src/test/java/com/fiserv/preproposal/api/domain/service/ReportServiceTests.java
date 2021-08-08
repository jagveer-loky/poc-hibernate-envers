package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.entity.EReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

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

}
