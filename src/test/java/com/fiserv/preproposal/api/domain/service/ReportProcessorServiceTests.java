package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.fiserv.preproposal.api.domain.service.report.ReportProcessorService.getFile;
import static com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer.normalize;
import static org.apache.commons.lang3.StringUtils.join;

//@SpringBootTest
class ReportProcessorServiceTests {

    /**
     *
     */
    @Test
    void testExtractFieldsFromBasicReportClass() {
        final Collection<String> fields = new BasicReport().extractLabels();
        Assertions.assertEquals(30, fields.size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromCompleteReportClass() {
        final Collection<String> fields = new CompleteReport().extractLabels();
        Assertions.assertEquals(97, fields.size());
    }

    /**
     *
     */
    @Test
    void testExtractFieldsFromQuantitativeReportClass() {
        final Collection<String> fields = new QuantitativeReport().extractLabels();
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

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));
        eReport.setType(TypeReport.COMPLETE);

        Assertions.assertEquals((Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 54, 55, 56, 57, 61, 62, 63, 64, 65, 66, 67, 68, 69, 71, 72, 73, 75, 76, 78, 79, 80, 81, 83, 84, 86, 87, 89, 90, 91, 92, 94, 96, 98)), ((AbstractReport) eReport.getType().getType().newInstance()).extractIndexes(eReport.getFields()));
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

        final List<String> labelsToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getLabel).collect(Collectors.toList());
        final List<Integer> indexesToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getIndex).collect(Collectors.toList());

        final EReport eReport = new EReport();
        eReport.setFields(labelsToReport);
        eReport.setType(TypeReport.COMPLETE);

        indexesToReport.forEach(indexToReport -> indexesToIgnore.forEach(indexToIgnore -> Assertions.assertNotEquals(indexesToIgnore, indexToIgnore)));

        Assertions.assertEquals(indexesToReport, ((AbstractReport) eReport.getType().getType().newInstance()).extractIndexes(eReport.getFields()));
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

        final List<String> labelsToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getLabel).collect(Collectors.toList());
        final List<Integer> indexesToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getIndex).collect(Collectors.toList());

        final EReport eReport = new EReport();
        eReport.setFields(labelsToReport);
        eReport.setType(TypeReport.BASIC);

        indexesToReport.forEach(indexToReport -> indexesToIgnore.forEach(indexToIgnore -> Assertions.assertNotEquals(indexesToIgnore, indexToIgnore)));

        Assertions.assertEquals(indexesToReport, ((AbstractReport) eReport.getType().getType().newInstance()).extractIndexes(eReport.getFields()));
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

        final List<String> labelsToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getLabel).collect(Collectors.toList());
        final List<Integer> indexesToReport = allFieldsFromReport.stream().filter(field -> indexesToIgnore.stream().noneMatch(index -> index.equals(field.getIndex()))).map(Field::getIndex).collect(Collectors.toList());

        final EReport eReport = new EReport();
        eReport.setFields(labelsToReport);
        eReport.setType(TypeReport.QUANTITATIVE);

        indexesToReport.forEach(indexToReport -> indexesToIgnore.forEach(indexToIgnore -> Assertions.assertNotEquals(indexesToIgnore, indexToIgnore)));

        Assertions.assertEquals(indexesToReport, ((AbstractReport) eReport.getType().getType().newInstance()).extractIndexes(eReport.getFields()));
    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractCompleteReportValuesFromLabelsMustPass() {
        final String[] headers = new String[]{"Numero da pre proposta", "ID da proposta", "Merchant ID", "CPF ou CNPJ do agente", "SERVICE_CONTRACT", "Sub Canal", "Tecnologia", "Numero de terminais"};

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<CompleteReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final CompleteReport completeReport = new CompleteReport();
            completeReport.setProposalNumber((long) i);
            completeReport.setPreproposalId("id da proposta: " + i);
            completeReport.setMerchant("merchant ID: " + i);
            completeReport.setAgentCpfCnpj("cpf ou cnpj do agente: " + i);
            completeReport.setServiceContract(i);
            completeReport.setSubChannel("Sub Canal: " + i);
            completeReport.setTechnology("id da proposta: " + i);
            completeReport.setTerminalsNumber((long) i);
            list.add(completeReport);
        }

        list.forEach(completeReport -> {

            final List<Object> values = completeReport.extractValues(eReport.getFields());
            Assertions.assertEquals(headers.length, values.size());

            values.forEach(Assertions::assertNotNull);

            boolean foundRepeated = false;

            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.size(); j++) {
                    if (i != j) {
                        if (values.get(i).equals(values.get(j))) {
                            foundRepeated = true;
                            break;
                        }
                    }
                }
            }

            Assertions.assertTrue(foundRepeated);

        });

    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractCompleteReportValuesFromLabelsWithNullValuesMustPass() {
        final String[] headers = new String[]{"Numero da pre proposta", "ID da proposta", "Merchant ID", "CPF ou CNPJ do agente", "SERVICE_CONTRACT", "Sub Canal", "Tecnologia", "Numero de terminais"};

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<CompleteReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final CompleteReport completeReport = new CompleteReport();
            completeReport.setProposalNumber((long) i);
            completeReport.setPreproposalId("id da proposta: " + i);
            completeReport.setMerchant("merchant ID: " + i);
            completeReport.setAgentCpfCnpj("cpf ou cnpj do agente: " + i);
            completeReport.setServiceContract(null);
            completeReport.setSubChannel("Sub Canal: " + i);
            completeReport.setTechnology("id da proposta: " + i);
            completeReport.setTerminalsNumber((long) i);
            list.add(completeReport);
        }

        list.forEach(completeReport -> {
            final List<Object> values = completeReport.extractValues(eReport.getFields());

            Assertions.assertEquals(headers.length, values.size());

            Assertions.assertEquals(7, (int) values.stream().filter(Objects::nonNull).count());
            Assertions.assertEquals(1, (int) values.stream().filter(Objects::isNull).count());
        });

    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractCompleteReportValuesFromLabelsWithAllValuesMustPass() {
        final String[] headers = ListUtil.toArrayString(new CompleteReport().extractLabels());

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<CompleteReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final CompleteReport completeReport = new CompleteReport();
            completeReport.setProposalNumber((long) i);
            completeReport.setPreproposalId("id da proposta: " + i);
            completeReport.setMerchant("merchant ID: " + i);
            completeReport.setAgentCpfCnpj("cpf ou cnpj do agente: " + i);
            completeReport.setServiceContract(null);
            completeReport.setSubChannel("Sub Canal: " + i);
            completeReport.setTechnology("id da proposta: " + i);
            completeReport.setTerminalsNumber((long) i);
            list.add(completeReport);
        }

        list.forEach(completeReport -> {
            final List<Object> values = completeReport.extractValues(eReport.getFields());

            Assertions.assertEquals(7, (int) values.stream().filter(Objects::nonNull).count());
            Assertions.assertEquals(90, (int) values.stream().filter(Objects::isNull).count());
        });
    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractBasicReportValuesFromLabelsMustPass() {
        final String[] headers = new String[]{"Id da Proposta", "Numero da Pre-proposta", "CPF/CNPJ", "Nome Fantasia", "Razao Social"};

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<BasicReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final BasicReport basicReport = new BasicReport();
            basicReport.setId("Id da Proposta: " + i);
            basicReport.setProposalNumber("Numero da Pre-proposta: " + i);
            basicReport.setCpfCnpj("Numero da Pre-proposta: " + i);
            basicReport.setFantasyName("Nome Fantasia: " + i);
            basicReport.setSocialReason("Razao Social: " + i);
            list.add(basicReport);
        }

        list.forEach(report -> {

            final List<Object> values = report.extractValues(eReport.getFields());
            Assertions.assertEquals(headers.length, values.size());

            values.forEach(Assertions::assertNotNull);

            boolean foundRepeated = false;

            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.size(); j++) {
                    if (i != j) {
                        if (values.get(i).equals(values.get(j))) {
                            foundRepeated = true;
                            break;
                        }
                    }
                }
            }

            Assertions.assertTrue(foundRepeated);

        });

    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractBasicReportValuesFromLabelsWithNullValuesMustPass() {
        final String[] headers = new String[]{"Id da Proposta", "Numero da Pre-proposta", "CPF/CNPJ", "Nome Fantasia", "Razao Social"};

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<BasicReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final BasicReport basicReport = new BasicReport();
            basicReport.setId("Id da Proposta: " + i);
            basicReport.setProposalNumber("Numero da Pre-proposta: " + i);
            basicReport.setCpfCnpj(null);
            basicReport.setFantasyName("Nome Fantasia: " + i);
            basicReport.setSocialReason("Razao Social: " + i);
            list.add(basicReport);
        }

        list.forEach(report -> {
            final List<Object> values = report.extractValues(eReport.getFields());

            Assertions.assertEquals(headers.length, values.size());

            Assertions.assertEquals(4, (int) values.stream().filter(Objects::nonNull).count());
            Assertions.assertEquals(1, (int) values.stream().filter(Objects::isNull).count());
        });

    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractBasicReportValuesFromLabelsWithAllValuesMustPass() {
        final String[] headers = ListUtil.toArrayString(new BasicReport().extractLabels());

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<BasicReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final BasicReport basicReport = new BasicReport();
            basicReport.setId("Id da Proposta: " + i);
            basicReport.setProposalNumber("Numero da Pre-proposta: " + i);
            basicReport.setCpfCnpj(null);
            basicReport.setFantasyName("Nome Fantasia: " + i);
            basicReport.setSocialReason("Razao Social: " + i);
            list.add(basicReport);
        }

        list.forEach(report -> {
            final List<Object> values = report.extractValues(eReport.getFields());

            Assertions.assertEquals(4, (int) values.stream().filter(Objects::nonNull).count());
            Assertions.assertEquals(26, (int) values.stream().filter(Objects::isNull).count());
        });
    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractQuantitativeReportValuesFromLabelsMustPass() {
        final String[] headers = new String[]{"Nome do arquivo", "Instituicao", "Data de processamento", "Status do Arquivo"};

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<QuantitativeReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final QuantitativeReport quantitativeReport = new QuantitativeReport();
            quantitativeReport.setFilename("Nome do Arquivo: " + i);
            quantitativeReport.setInstitution("Instituicao: " + i);
            quantitativeReport.setProcessingDate("Instituicao: " + i);
            quantitativeReport.setFileStatus("Status do Arquivo: " + i);
            list.add(quantitativeReport);
        }

        list.forEach(report -> {

            final List<Object> values = report.extractValues(eReport.getFields());
            Assertions.assertEquals(headers.length, values.size());

            values.forEach(Assertions::assertNotNull);

            boolean foundRepeated = false;

            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.size(); j++) {
                    if (i != j) {
                        if (values.get(i).equals(values.get(j))) {
                            foundRepeated = true;
                            break;
                        }
                    }
                }
            }

            Assertions.assertTrue(foundRepeated);

        });

    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractQuantitativeReportValuesFromLabelsWithNullValuesMustPass() {
        final String[] headers = new String[]{"Nome do arquivo", "Instituicao", "Data de processamento", "Status do Arquivo"};

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<QuantitativeReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final QuantitativeReport quantitativeReport = new QuantitativeReport();
            quantitativeReport.setFilename("Nome do arquivo: " + i);
            quantitativeReport.setInstitution("Instituicao: " + i);
            quantitativeReport.setProcessingDate(null);
            quantitativeReport.setFileStatus("Status do Arquivo: " + i);
            list.add(quantitativeReport);
        }

        list.forEach(report -> {
            final List<Object> values = report.extractValues(eReport.getFields());

            Assertions.assertEquals(headers.length, values.size());

            Assertions.assertEquals(3, (int) values.stream().filter(Objects::nonNull).count());
            Assertions.assertEquals(1, (int) values.stream().filter(Objects::isNull).count());
        });

    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void extractQuantitativeReportValuesFromLabelsWithAllValuesMustPass() {
        final String[] headers = ListUtil.toArrayString(new QuantitativeReport().extractLabels());

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<QuantitativeReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final QuantitativeReport quantitativeReport = new QuantitativeReport();
            quantitativeReport.setFilename("Nome do arquivo: " + i);
            quantitativeReport.setInstitution("Instituicao: " + i);
            quantitativeReport.setProcessingDate(null);
            quantitativeReport.setFileStatus("Status do Arquivo: " + i);
            list.add(quantitativeReport);
        }

        list.forEach(report -> {
            final List<Object> values = report.extractValues(eReport.getFields());

            Assertions.assertEquals(3, (int) values.stream().filter(Objects::nonNull).count());
            Assertions.assertEquals(9, (int) values.stream().filter(Objects::isNull).count());
        });
    }

    /**
     *
     */
    @Test
    @SneakyThrows
    void writeCompleteReportValuesFromLabelsWithAllValuesMustPass() {
        final String[] headers = ListUtil.toArrayString(new CompleteReport().extractLabels());

        final EReport eReport = new EReport();
        eReport.setFields(Arrays.asList(headers));

        final List<CompleteReport> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final CompleteReport completeReport = new CompleteReport();
            completeReport.setProposalNumber((long) i);
            completeReport.setPreproposalId("id da propósta: " + i);
            completeReport.setMerchant("merchant ID: " + i);
            completeReport.setAgentCpfCnpj("cpf ou cnpj do agente: " + i);
            completeReport.setServiceContract(null);
            completeReport.setSubChannel("Sub Canal: " + i);
            completeReport.setTechnology("id da proposta: " + i);
            completeReport.setTerminalsNumber((long) i);
            list.add(completeReport);
        }

        final File file = getFile("target/tmp/pre-proposal-api", "file.csv");
        final FileWriter fileWriter = new FileWriter(file, true);
        final BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

        for (final CompleteReport report : list) {
            final List<Object> values = report.extractValues(eReport.getFields());
            bufferWriter.write(normalize(join(values, ";")) + "\n");
        }
        bufferWriter.close();
        fileWriter.close();
    }

}
