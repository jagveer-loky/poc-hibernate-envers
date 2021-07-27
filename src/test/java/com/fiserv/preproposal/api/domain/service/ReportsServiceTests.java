package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class ReportsServiceTests {

    @Autowired
    ReportsService reportsService;

    /**
     *
     */
    @Test
    void basicReportMustBeContainOnlyFiservOnlineResponsesTypesMustPass() {
        final List<BasicReport> basicReport = reportsService.getBasicReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertEquals(report.getResponseType(), "FISERV_ONLINE"));
    }

    /**
     *
     */
    @Test
    void basicReportMustBeNotContainFiservOnlineResponseTypeMustPass() {
        final List<BasicReport> basicReport = reportsService.getBasicReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty())
            basicReport.forEach(report -> Assertions.assertNotEquals(report.getResponseType(), "FISERV_ONLINE"));
    }

    /**
     *
     */
    @Test
    void basicReportMustReturnAllResponsesTypesMustPass() {
        final List<BasicReport> basicReport = reportsService.getBasicReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), null, null, null);
        Assertions.assertNotNull(basicReport);
        if (!basicReport.isEmpty()) {
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("FISERV_ONLINE")));
//            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LEAD")));
            Assertions.assertTrue(basicReport.stream().anyMatch(basicReport1 -> basicReport1.getResponseType().equals("LNK_PAYMENT")));
        }
    }

    /**
     *
     */
    @Test
    void basicCSVReportMustReturnAllFieldsMustPass() {

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

        final byte[] croppedBasicCSVReport = reportsService.getBasicCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, headersToRemove);
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
        final List<QuantitativeReport> quantitativeReportWithOnlyFiservOnline = reportsService.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        final List<QuantitativeReport> quantitativeReportWithoutFiservOnline = reportsService.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        final List<QuantitativeReport> quantitativeReportWithAllFiservOnline = reportsService.getQuantitativeReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), null, null, null);
        for (int i = 0; i < quantitativeReportWithAllFiservOnline.size(); i++) {

            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getProposals(), quantitativeReportWithOnlyFiservOnline.get(i).getProposals() + quantitativeReportWithoutFiservOnline.get(i).getProposals());
            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getOnlineRejected(), quantitativeReportWithOnlyFiservOnline.get(i).getOnlineRejected() + quantitativeReportWithoutFiservOnline.get(i).getOnlineRejected());
            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getCredOnline(), quantitativeReportWithOnlyFiservOnline.get(i).getCredOnline() + quantitativeReportWithoutFiservOnline.get(i).getCredOnline());
            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getPendingInFiservOnline(), quantitativeReportWithOnlyFiservOnline.get(i).getPendingInFiservOnline() + quantitativeReportWithoutFiservOnline.get(i).getPendingInFiservOnline());
            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getPendingComplement(), quantitativeReportWithOnlyFiservOnline.get(i).getPendingComplement() + quantitativeReportWithoutFiservOnline.get(i).getPendingComplement());
            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getPreproposalError(), quantitativeReportWithOnlyFiservOnline.get(i).getPreproposalError() + quantitativeReportWithoutFiservOnline.get(i).getPreproposalError());
            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getTmpCanceled(), quantitativeReportWithOnlyFiservOnline.get(i).getTmpCanceled() + quantitativeReportWithoutFiservOnline.get(i).getTmpCanceled());
            Assertions.assertEquals(quantitativeReportWithAllFiservOnline.get(i).getFinished(), quantitativeReportWithOnlyFiservOnline.get(i).getFinished() + quantitativeReportWithoutFiservOnline.get(i).getFinished());

        }
    }

    /**
     *
     */
    @Test
    void quantitativeCSVReportMustReturnAllFieldsMustPass() {

        final CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setHeaderExtractionEnabled(true);

        final RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setRowProcessor(rowProcessor);

        final CsvParser parser = new CsvParser(parserSettings);
        final byte[] completeQuantitativeCSVReport = reportsService.getQuantitativeCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null, null);
        parser.parse(new ByteArrayInputStream(completeQuantitativeCSVReport));

        final String[] oldHeaders = rowProcessor.getHeaders();

        final Set<String> headersToRemove = new HashSet<>();
        headersToRemove.add("Instituicao");
        headersToRemove.add("Status do Arquivo");
        headersToRemove.add("Nome do Arquivo");

        headersToRemove.forEach(header -> Assertions.assertTrue(oldHeaders[0].contains(header)));

        final byte[] croppedQuantitativeCSVReport = reportsService.getQuantitativeCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, headersToRemove);
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
        final List<CompleteReport> completeReport = reportsService.getCompleteReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), false, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertNotNull(completeReport);
        if (!completeReport.isEmpty())
            completeReport.forEach(report -> Assertions.assertEquals(report.getResponseType(), "FISERV_ONLINE"));
    }

    /**
     *
     */
    @Test
    void completeReportMustBeNotContainFiservOnlineResponseTypeMustPass() {
        final List<CompleteReport> completeReport = reportsService.getCompleteReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), true, Collections.singleton("FISERV_ONLINE"), null);
        Assertions.assertNotNull(completeReport);
        if (!completeReport.isEmpty())
            completeReport.forEach(report -> Assertions.assertNotEquals(report.getResponseType(), "FISERV_ONLINE"));
    }

    /**
     *
     */
    @Test
    void completeReportMustReturnAllResponsesTypesMustPass() {
        final List<CompleteReport> completeReport = reportsService.getCompleteReport("00000007", "149", LocalDate.now().minusDays(120), LocalDate.now(), null, null, null);
        Assertions.assertNotNull(completeReport);
        if (!completeReport.isEmpty()) {
            Assertions.assertTrue(completeReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("FISERV_ONLINE")));
//            Assertions.assertTrue(proposalDataReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("LEAD")));
            Assertions.assertTrue(completeReport.stream().anyMatch(errorsReport1 -> errorsReport1.getResponseType().equals("LNK_PAYMENT")));
        }
    }

    /**
     *
     */
    @Test
    void completeCSVReportMustReturnAllFieldsMustPass() {

        final CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setHeaderExtractionEnabled(true);

        final RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setRowProcessor(rowProcessor);

        final CsvParser parser = new CsvParser(parserSettings);
        final byte[] completeCompleteCSVReport = reportsService.getCompleteCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, null);
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

        final byte[] croppedCompleteCSVReport = reportsService.getCompleteCSVReport("00000007", "149", LocalDate.now().minusDays(20), LocalDate.now(), null, null, null, headersToRemove);
        parser.parse(new ByteArrayInputStream(croppedCompleteCSVReport));

        final String[] newHeaders = rowProcessor.getHeaders();

        headersToRemove.forEach(header -> Assertions.assertFalse(newHeaders[0].contains(header)));

        Assertions.assertNotEquals(oldHeaders[0].split(";").length, newHeaders[0].split(";").length);

    }

}
