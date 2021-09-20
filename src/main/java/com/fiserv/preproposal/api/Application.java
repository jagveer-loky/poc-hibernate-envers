package com.fiserv.preproposal.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * @author achetype-fiserv
 */

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Pre Proposal Api", description = "API used to consult reports of preproposals and update preproposal properties."))
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {

        final String[] ordered = new String[]{
                "Numero da pre proposta"
                , "ID da proposta"
                , "Merchant ID"
                , "UserId"
                , "CPF ou CNPJ do agente"
                , "Instituicao"
                , "SERVICE_CONTRACT"
                , "Sub Canal"
                , "Tecnologia"
                , "Numero de terminais"
                , "Valor unitario"
                , "Status - FISERV"
                , "Erros"
                , "Status - CAIXA"
                , "Mensagem Caixa"
                , "Inclusao em"
                , "Conclusao em"
                , "Submissao ao online em"
                , "Tipo de pessoa"
                , "CPF/CNPJ"
                , "Nome Fantasia"
                , "Razao Social"
                , "Nome Plaqueta (Comprovante)"
                , "Faturamento Mensal"
                , "Nascimento/Constituicao"
                , "Genero"
                , "Pronome de tratamento"
                , "Local de nascimento"
                , "Nacionalidade"
                , "Nome completo"
                , "Inscricao Municipal"
                , "Inscricao estadual"
                , "Forma de constituicao"
                , "Data de abertura"
                , "ANNUAL_BILLING_VOL"
                , "ANNUAL_VOL_CASH"
                , "ANNUAL_VOL_SALES_CARD"
                , "ANNUAL_VOL_SALES_CARD_GROUP"
                , "AVERAGE_TICKET"
                , "Permissao Bacen"
                , "Codigo da campanha"
                , "CNAE"
                , "ECOMMERCE"
                , "Cartao estrangeiro"
                , "MANUAL_PREPAYMENT_ENABLED"
                , "BOARDING_BRANCHING"
                , "Porc. cartao n/ presente"
                , "Porc. cartao presente"
                , "Porc. Internet"
                , "PREPAYMENT_INDICATOR"
                , "Transacao recorrente"
                , "SERVICE_DAY_0"
                , "SERVICE_DAY_15_30"
                , "SERVICE_DAY_1_7"
                , "SERVICE_DAY_8_14"
                , "SERVICE_DAY_OVER_30"
                , "Pendente BW em"
                , "Data de instalacao"
                , "Tipo de endereco"
                , "CEP"
                , "Rua"
                , "Numero do Endereco"
                , "Bairro"
                , "Complemento"
                , "Cidade"
                , "Estado"
                , "Tipo do socio"
                , "CPF ou CNPJ do socio"
                , "Dta. Nasc. do socio"
                , "Tipo de constituicao - socio"
                , "Contato - socio"
                , "Nome do socio"
                , "Pronome de tratamento - Socio"
                , "Nacionalidade do socio"
                , "Funcao do socio"
                , "Perc. Participacao do socio"
                , "Dta. nasc. contato"
                , "CPF contato"
                , "EMAIL contato"
                , "Nome contato"
                , "telefone contato"
                , "Celular contato"
                , "DESCRIPTION"
                , "DISCOUNT_FEE"
                , "FEE"
                , "FEE_ID_NUMBER"
                , "Codigo do banco"
                , "Agencia"
                , "Dig. conta"
                , "Num. Conta"
                , "Nome do responsavel"
                , "Tipo de conta"
                , "Tipo de Estabelecimento"
                , "Matricula do Vendedor"
                , "Opt In"
                , "Termo de aceite"
                , "Dias de trabalho"
                , "Tipo de proposta"
        };

//        try {
//
//            final File file = new File("src/main/java/com/fiserv/preproposal/api/domain/dtos/CompleteReport.java");
//
//            final List<String> lines = Files.readAllLines(file.toPath());
//
//            Assert.isTrue(lines.stream().filter(s -> s.contains("@Parsed")).count() == ordered.length);
//
//            for (final String s : ordered) {
//                for (int k = 0; k < lines.size(); k++) {
//                    if (lines.get(k).contains("@Parsed")) {
//                        if (lines.get(k).substring(lines.get(k).indexOf("\"") + 1, lines.get(k).indexOf("\")")).equals(s)) {
//                            System.out.println("Encontrou: " + s);
//                            break;
//                        }
//                    } else if (k == lines.size() - 1) {
//                        throw new RuntimeException(s + " não encontrado");
//                    }
//                }
//            }
//
//
//            for (int j = 0; j < lines.size(); j++) {
//                boolean find = false;
//                for (int i = 0; i < ordered.length; i++) {
//                    if (lines.get(j).contains("@Parsed") && lines.get(j).substring(lines.get(j).indexOf("\"") + 1, lines.get(j).indexOf("\")")).equals(ordered[i])) {
//                        System.out.println(lines.get(j) + " line: " + (i + 1));
//                        lines.set(j, "@Index(" + (i + 1) + ") @Parsed(field = \"" + ordered[i] + "\")");
//                        find = true;
//                        break;
//                    }
//                }
//                if (!find && lines.get(j).contains("@Parsed"))
//                    throw new RuntimeException("NÃO ENCONTROU: " + lines.get(j));
//            }
//
//            Files.write(file.toPath(), lines); // You can add a charset and other options too
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("/git/git.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }
}