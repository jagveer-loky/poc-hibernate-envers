package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.infrastrucutre.io.IOService;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional(readOnly = true)
class ReportsServiceTests {

    @Autowired
    ReportsService reportsService;

    /**
     *
     */
    @Test
    void basicReportMustBeContainOnlyFiservOnlineResponsesTypesMustPass() {
        final List<BasicReport> basicReport = reportsService.getBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countBasicReport = reportsService.getCountBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countBasicReport, basicReport.size());
    }

    /**
     *
     */
    @Test
    void basicReportMustBeNotContainFiservOnlineResponseTypeMustPass() {
        final List<BasicReport> basicReport = reportsService.getBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertNotEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countBasicReport = reportsService.getCountBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countBasicReport, basicReport.size());
    }

    /**
     *
     */
    @Test
    void basicReportMustReturnAllResponsesTypesMustPass() {
        final List<BasicReport> basicReport = reportsService.getBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null).collect(Collectors.toList());
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty()) {
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("FISERV_ONLINE")));
//            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LEAD")));
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LNK_PAYMENT")));
        }
        final int countBasicReport = reportsService.getCountBasicReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null);
        Assertions.assertEquals(countBasicReport, basicReport.size());
    }

    /**
     *
     */
    @Test
    void basicCSVReportMustReturnAllFieldsMustPass() {

        final IOService<BasicReport> ioService = new IOService<>();

        final CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setHeaderExtractionEnabled(true);

        final RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setRowProcessor(rowProcessor);

        final CsvParser parser = new CsvParser(parserSettings);
        final byte[] completeBasicCSVReport = reportsService.getBasicCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, null);
        parser.parse(new ByteArrayInputStream(completeBasicCSVReport));

        final String[] oldHeaders = rowProcessor.getHeaders();

        final Set<String> headersToRemove = new HashSet<>();
        headersToRemove.add("Id da Proposta");
        headersToRemove.add("Nome Fantasia");
        headersToRemove.add("Tipo da resposta");

        headersToRemove.forEach(header -> Assertions.assertTrue(oldHeaders[0].contains(header)));

        final byte[] croppedBasicCSVReport = reportsService.getBasicCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, ioService.extractFieldsToIgnore(BasicReport.class, headersToRemove));
        parser.parse(new ByteArrayInputStream(croppedBasicCSVReport));

        final String[] newHeaders = rowProcessor.getHeaders();

        headersToRemove.forEach(header -> Assertions.assertFalse(newHeaders[0].contains(header)));

        Assertions.assertNotEquals(oldHeaders[0].split(";").length, newHeaders[0].split(";").length);

    }

    /**
     *
     */
    @Test
    void quantitativeReportSeveralTestsTypesMustPass() {
        final List<QuantitativeReport> quantitativeReportsWithOnlyFiservOnline = reportsService.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        final int countQuantitativeReportWithOnlyFiservOnline = reportsService.getCountQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countQuantitativeReportWithOnlyFiservOnline, quantitativeReportsWithOnlyFiservOnline.size());
        final List<QuantitativeReport> quantitativeReportsWithoutFiservOnline = reportsService.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        final int countQuantitativeReportWithoutFiservOnline = reportsService.getCountQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countQuantitativeReportWithoutFiservOnline, quantitativeReportsWithoutFiservOnline.size());
        final List<QuantitativeReport> quantitativeReportsWithAllFiservOnline = reportsService.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null).collect(Collectors.toList());
        final int countQuantitativeReportWithAllFiservOnline = reportsService.getCountQuantitativeReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null);
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
    void quantitativeCSVReportMustReturnAllFieldsMustPass() {

        final IOService<QuantitativeReport> ioService = new IOService<>();

        final CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setHeaderExtractionEnabled(true);

        final RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setRowProcessor(rowProcessor);

        final CsvParser parser = new CsvParser(parserSettings);
        final byte[] completeQuantitativeCSVReport = reportsService.getQuantitativeCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        parser.parse(new ByteArrayInputStream(completeQuantitativeCSVReport));

        final String[] oldHeaders = rowProcessor.getHeaders();

        final Set<String> headersToRemove = new HashSet<>();
        headersToRemove.add("Instituicao");
        headersToRemove.add("Status do Arquivo");
        headersToRemove.add("Nome do Arquivo");

        headersToRemove.forEach(header -> Assertions.assertTrue(oldHeaders[0].contains(header)));

        final byte[] croppedQuantitativeCSVReport = reportsService.getQuantitativeCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, ioService.extractFieldsToIgnore(QuantitativeReport.class, headersToRemove));
        parser.parse(new ByteArrayInputStream(croppedQuantitativeCSVReport));

        final String[] newHeaders = rowProcessor.getHeaders();

        headersToRemove.forEach(header -> Assertions.assertFalse(newHeaders[0].contains(header)));

        Assertions.assertNotEquals(oldHeaders[0].split(";").length, newHeaders[0].split(";").length);

    }

    /**
     *
     */
    @Test
    void completeReportMustBeContainOnlyFiservOnlineResponsesTypesMustPass() {
        final List<CompleteReport> completeReport = reportsService.getCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        if (!completeReport.isEmpty())
            completeReport.forEach(report -> Assertions.assertEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countCompleteReport = reportsService.getCountCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countCompleteReport, completeReport.size());
    }

    /**
     *
     */
    @Test
    void completeReportMustBeNotContainFiservOnlineResponseTypeMustPass() {
        final List<CompleteReport> completeReport = reportsService.getCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null).collect(Collectors.toList());
        Assertions.assertNotNull(completeReport);
        if (!completeReport.isEmpty())
            completeReport.forEach(report -> Assertions.assertNotEquals(report.getResponseType(), "FISERV_ONLINE"));
        final int countCompleteReport = reportsService.getCountCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertEquals(countCompleteReport, completeReport.size());
    }

    /**
     *
     */
    @Test
    void completeReportMustReturnAllResponsesTypesMustPass() {
        final List<CompleteReport> completeReport = reportsService.getCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null).collect(Collectors.toList());
        Assertions.assertNotNull(completeReport);
        if (!completeReport.isEmpty()) {
            Assertions.assertTrue(completeReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("FISERV_ONLINE")));
//            Assertions.assertTrue(proposalDataReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("LEAD")));
            Assertions.assertTrue(completeReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("LNK_PAYMENT")));
        }
        final int countCompleteReport = reportsService.getCountCompleteReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null);
        Assertions.assertEquals(countCompleteReport, completeReport.size());
    }

    /**
     *
     */
    @Test
    void completeCSVReportMustReturnAllFieldsMustPass() {

        final IOService<CompleteReport> ioService = new IOService<>();

        final CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setHeaderExtractionEnabled(true);

        final RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setRowProcessor(rowProcessor);

        final CsvParser parser = new CsvParser(parserSettings);
        // Get with all fields
        final byte[] completeCompleteCSVReport = reportsService.getCompleteCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null);
        parser.parse(new ByteArrayInputStream(completeCompleteCSVReport));

        final String[] oldHeaders = rowProcessor.getHeaders();

        final Set<String> headersToRemove = new HashSet<>();
        headersToRemove.add("ID da proposta");
        headersToRemove.add("UserId");
        headersToRemove.add("Instituicao");
        headersToRemove.add("service_contract");
        headersToRemove.add("Tecnologia");
        headersToRemove.add("Erros");

        headersToRemove.forEach(header -> Assertions.assertTrue(oldHeaders[0].contains(header)));

        // Get with only some fields
        final byte[] croppedCompleteCSVReport = reportsService.getCompleteCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, ioService.extractFieldsToIgnore(CompleteReport.class, headersToRemove));
        parser.parse(new ByteArrayInputStream(croppedCompleteCSVReport));

        final String[] newHeaders = rowProcessor.getHeaders();

        headersToRemove.forEach(header -> Assertions.assertFalse(newHeaders[0].contains(header)));

        Assertions.assertNotEquals(oldHeaders[0].split(";").length, newHeaders[0].split(";").length);

    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromBasicReportClass() {
        final Set<String> fields = this.reportsService.extractFieldsFromBasicReport();
        Assertions.assertEquals(fields.size(), IOService.getAttributesFromClass(BasicReport.class).size());
        Assertions.assertEquals(this.reportsService.getFieldsFromReport(BasicReport.NAME).size(), IOService.getAttributesFromClass(BasicReport.class).size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromCompleteReportClass() {
        final Set<String> fields = this.reportsService.extractFieldsFromCompleteReport();
        Assertions.assertEquals(fields.size(), IOService.getAttributesFromClass(CompleteReport.class).size());
        Assertions.assertEquals(this.reportsService.getFieldsFromReport(CompleteReport.NAME).size(), IOService.getAttributesFromClass(CompleteReport.class).size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromQuantitativeReportClass() {
        final Set<String> fields = this.reportsService.extractFieldsFromQuantitativeReport();
        Assertions.assertEquals(fields.size(), IOService.getAttributesFromClass(QuantitativeReport.class).size());
        Assertions.assertEquals(this.reportsService.getFieldsFromReport(QuantitativeReport.NAME).size(), IOService.getAttributesFromClass(QuantitativeReport.class).size());
    }

}
