package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

//@SpringBootTest
class ReportProcessorServiceTests {

    /**
     *
     */
    @Test
    void testExtractFieldsFromBasicReportClass() {
        final Set<String> fields = new BasicReport().extractLabels();
        Assertions.assertEquals(30, fields.size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromCompleteReportClass() {
        final Set<String> fields = new CompleteReport().extractLabels();
        Assertions.assertEquals(97, fields.size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromQuantitativeReportClass() {
        final Set<String> fields = new QuantitativeReport().extractLabels();
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
    @SneakyThrows
    void extractIndexesToIgnoreFromCompleteReportWithSeveralLabelsMustPass() {

        final String[] headers = new String[]{"Numero da pre proposta", "ID da proposta", "Merchant ID", "UserId", "CPF ou CNPJ do agente", "Instituicao", "SERVICE_CONTRACT", "Sub Canal", "Tecnologia", "Numero de terminais", "Valor unitario", "Status - FISERV", "Erros", "Status - CAIXA", "Mensagem Caixa", "Inclusao em", "Conclusao em", "Submissao ao online em", "Tipo de pessoa", "CPF/CNPJ", "Nome Fantasia", "Razao Social", "Nome Plaqueta (Comprovante)", "Faturamento Mensal", "Nascimento/Constituicao", "Genero", "Pronome de tratamento", "Local de nascimento", "Nacionalidade", "Nome completo", "Inscricao Municipal", "Inscricao estadual", "Forma de constituicao", "Data de abertura", "ANNUAL_BILLING_VOL", "ANNUAL_VOL_CASH", "ANNUAL_VOL_SALES_CARD", "ANNUAL_VOL_SALES_CARD_GROUP", "AVERAGE_TICKET", "Permissao Bacen", "Codigo da campanha", "CNAE", "ECOMMERCE", "Cartao estrangeiro", "MANUAL_PREPAYMENT_ENABLED", "BOARDING_BRANCHING", "Porc. cartao n/ presente", "Porc. cartao presente", "Porc. Internet", "Transacao recorrente", "SERVICE_DAY_0", "SERVICE_DAY_15_30", "SERVICE_DAY_1_7", "SERVICE_DAY_8_14", "SERVICE_DAY_OVER_30", "Pendente BW em", "Rua", "Numero do Endereco", "Bairro", "Complemento", "Cidade", "Estado", "Tipo do socio", "CPF ou CNPJ do socio", "Dta. Nasc. do socio", "Contato - socio", "Nome do socio", "Pronome de tratamento - Socio", "Funcao do socio", "Perc. Participacao do socio", "CPF contato", "EMAIL contato", "Nome contato", "telefone contato", "DESCRIPTION", "DISCOUNT_FEE", "FEE_ID_NUMBER", "Codigo do banco", "Dig. conta", "Num. Conta", "Nome do responsavel", "Tipo de conta", "Matricula do Vendedor", "Termo de aceite", "Tipo de proposta"};

        final ReportParams reportParams = new ReportParams();
        reportParams.setFields(Arrays.asList(headers));
        reportParams.setType(TypeReport.COMPLETE);

        Assertions.assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 54, 55, 56, 57, 61, 62, 63, 64, 65, 66, 67, 68, 69, 71, 72, 73, 75, 76, 78, 79, 80, 81, 83, 84, 86, 87, 89, 90, 91, 92, 94, 96, 98)), ((AbstractReport) reportParams.getType().getType().newInstance()).extractIndexes(reportParams.getFields()));
    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractIndexesToIgnoreFromCompleteReportMustPass() {

        final List<Field> allFieldsFromReport = new ArrayList<>(new CompleteReport().extractFields());

        final Set<Integer> indexesToIgnore = new HashSet<>();
        // Removing 20 random labels from header
        for (int i = 0; i < 20; i++) {
            final int indexToIgnore = new Random().nextInt(allFieldsFromReport.size());

            if (indexesToIgnore.stream().anyMatch(index -> index.equals(indexToIgnore)) || indexToIgnore == 0) {
                i--;
                continue;
            }

            indexesToIgnore.add(indexToIgnore);
        }

        final Set<String> labelsToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getLabel).collect(Collectors.toSet());
        final Set<Integer> indexesToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getIndex).collect(Collectors.toSet());

        final ReportParams reportParams = new ReportParams();
        reportParams.setFields(labelsToReport);
        reportParams.setType(TypeReport.COMPLETE);

        indexesToReport.forEach(indexToReport -> indexesToIgnore.forEach(indexToIgnore -> Assertions.assertNotEquals(indexesToIgnore, indexToIgnore)));

        Assertions.assertEquals(indexesToReport, ((AbstractReport) reportParams.getType().getType().newInstance()).extractIndexes(reportParams.getFields()));
    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractIndexesToIgnoreFromBasicReportMustPass() {

        final List<Field> allFieldsFromReport = new ArrayList<>(new BasicReport().extractFields());

        final Set<Integer> indexesToIgnore = new HashSet<>();
        // Removing 20 random labels from header
        for (int i = 0; i < 8; i++) {
            final int indexToIgnore = new Random().nextInt(allFieldsFromReport.size());

            if (indexesToIgnore.stream().anyMatch(index -> index.equals(indexToIgnore)) || indexToIgnore == 0) {
                i--;
                continue;
            }

            indexesToIgnore.add(indexToIgnore);
        }

        final Set<String> labelsToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getLabel).collect(Collectors.toSet());
        final Set<Integer> indexesToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getIndex).collect(Collectors.toSet());

        final ReportParams reportParams = new ReportParams();
        reportParams.setFields(labelsToReport);
        reportParams.setType(TypeReport.BASIC);

        indexesToReport.forEach(indexToReport -> indexesToIgnore.forEach(indexToIgnore -> Assertions.assertNotEquals(indexesToIgnore, indexToIgnore)));

        Assertions.assertEquals(indexesToReport, ((AbstractReport) reportParams.getType().getType().newInstance()).extractIndexes(reportParams.getFields()));
    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractIndexesToIgnoreFromQuantitativeReportMustPass() {

        final List<Field> allFieldsFromReport = new ArrayList<>(new QuantitativeReport().extractFields());

        final Set<Integer> indexesToIgnore = new HashSet<>();
        // Removing 20 random labels from header
        for (int i = 0; i < 6; i++) {
            final int indexToIgnore = new Random().nextInt(allFieldsFromReport.size());

            if (indexesToIgnore.stream().anyMatch(index -> index.equals(indexToIgnore)) || indexToIgnore == 0) {
                i--;
                continue;
            }

            indexesToIgnore.add(indexToIgnore);
        }

        final Set<String> labelsToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getLabel).collect(Collectors.toSet());
        final Set<Integer> indexesToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getIndex).collect(Collectors.toSet());

        final ReportParams reportParams = new ReportParams();
        reportParams.setFields(labelsToReport);
        reportParams.setType(TypeReport.QUANTITATIVE);

        indexesToReport.forEach(indexToReport -> indexesToIgnore.forEach(indexToIgnore -> Assertions.assertNotEquals(indexesToIgnore, indexToIgnore)));

        Assertions.assertEquals(indexesToReport, ((AbstractReport) reportParams.getType().getType().newInstance()).extractIndexes(reportParams.getFields()));
    }

    //    /**
//     *
//     */
//    @SneakyThrows
//    @Test
//    void extractIndexesToIgnoreFromCompleteReportMustPass() {
//
////        final List<CompleteReport> list = new ArrayList<>();
////        for (int i = 1; i <= 10; i++) {
////            final CompleteReport completeReport = new CompleteReport();
////            completeReport.setProposalNumber((long) i);
////            completeReport.setPreproposalId("id da proposata: " + i);
////            completeReport.setMerchant("merchant ID: " + i);
////            completeReport.setAgentCpfCnpj("cpf ou cnpj do agente: " + i);
////            completeReport.setServiceContract(i);
////            completeReport.setSubChannel("Sub Canal: " + i);
////            completeReport.setTechnology("Tecnologia: " + i);
////            completeReport.setTerminalsNumber((long) i);
////
////            list.add(completeReport);
////        }
//
//
//        final String[] headers = new String[]{"Numero da pre proposta", "ID da proposta", "Merchant ID", "CPF ou CNPJ do agente", "SERVICE_CONTRACT", "Sub Canal", "Tecnologia", "Numero de terminais"};
//
//        final ReportParams reportParams = new ReportParams();
//        reportParams.setFields(Arrays.asList(headers));
//        reportParams.setType(TypeReport.COMPLETE);
//
//        Assertions.assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 5, 7, 8, 9, 10)), ((AbstractReport) reportParams.getType().getType().newInstance()).extractIndexes(reportParams.getFields()));
//    }

}
