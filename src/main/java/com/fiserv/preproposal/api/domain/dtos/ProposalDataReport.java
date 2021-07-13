package com.fiserv.preproposal.api.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalDataReport {

    public static final String HEADER_NAME = "header";

    public static final String NAME = "proposal-data";

    @Schema(
            description = "ID da Pre Proposta",
            example = "5763",
            type = "number"
    )
    private Long preproposalId;

    @Schema(
            description = "Numero da Proposta no Fiserv Online",
            example = "O00000001976443",
            type = "String"
    )
    private String proposalNumber;

    @Schema(
            description = "Merchant do Fiserv Online",
            example = "91041155",
            type = "String"
    )
    private String merchant;

    @Schema(
            description = "ID do Usuario",
            example = "CEFP12",
            type = "String"
    )
    private String userId;

    @Schema(
            description = "CPF/CNPJ do agente",
            example = "27231934897",
            type = "String"
    )
    private String agentCpfCnpj;

    @Schema(
            description = "Instituicao",
            example = "00000007",
            type = "String"
    )
    private String institution;

    @Schema(
            description = "Service Contract",
            example = "149",
            type = "number"
    )
    private Integer serviceContract;

    @Schema(
            description = "Sub Canal",
            example = "0425",
            type = "String"
    )
    private String subChannel;

    @Schema(
            description = "Tecnologia",
            example = "365-POS Auto Credenc - POS Auto Credenc",
            type = "String"
    )
    private String tecnology;

    @Schema(
            description = "Fiserv Status",
            example = "PRE1-Pendente análise pré proposta",
            type = "String"
    )
    private String fiservStatus;

    @Schema(
            description = "Caixa Status",
            example = "1-Efetivada",
            type = "String"
    )
    private String caixaStatus;

    @Schema(
            description = "Caixa Mensagem",
            example = "Concluido",
            type = "String"
    )
    private String caixaMessage;

    @Schema(
            description = "Data de Inclusao",
            example = "12/05/2021 11:44",
            type = "String"
    )
    private String inclusionDate;

    @Schema(
            description = "Data de Conclusao",
            example = "30/06/2021 12:06",
            type = "String"
    )
    private String conclusionDate;

    @Schema(
            description = "Data de Envio para o Fiserv Online",
            example = "28/06/2021 14:23",
            type = "String"
    )
    private String onlineSubmitionDate;


    @Schema(
            description = "Tipo da resposta",
            example = "FISERV_ONLINE",
            type = "String"
    )
    private String responseType;

    @Schema(
            description = "Tipo de Pessoa",
            example = "Fisica",
            type = "String"
    )
    private String persionType;

    @Schema(
            description = "CPF/CNPJ",
            example = "27231934897",
            type = "String"
    )
    private String cpfCnpj;

    @Schema(
            description = "Nome Fantasia",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String fantasyName;

    @Schema(
            description = "Razao Social",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String socialReason;

    @Schema(
            description = "Nome do Comprovante",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String vouncherName;

    @Schema(
            description = "Faturamento Mensal",
            example = "12.50",
            type = "String"
    )
    private String montlyBilling;

    @Schema(
            description = "Data de Nascimento",
            example = "2016/03/11",
            type = "String"
    )
    private String birthDate;

    @Schema(
            description = "Genero (M - Masculino, F - Feminino)",
            example = "M",
            type = "String"
    )
    private String gender;

    @Schema(
            description = "Pronome de Tratamento",
            example = "MR",
            type = "String"
    )
    private String treatmentPronoun;

    @Schema(
            description = "Local de Nascimento",
            example = "Sao Paulo",
            type = "String"
    )
    private String birthPlace;

    @Schema(
            description = "Nacionalidade",
            example = "Brasileiro",
            type = "String"
    )
    private String nacionality;

    @Schema(
            description = "Nome Completo",
            example = "Jhon Doe",
            type = "String"
    )
    private String fullName;

    @Schema(
            description = "Inscricao Municipal",
            type = "String"
    )
    private String municipalRegistration;

    @Schema(
            description = "Inscricao Estadual",
            example = "000000000000000",
            type = "String"
    )
    private String stateRegistration;


    @Schema(
            description = "Forma de Constituicao",
            example = "Sociedade Empresária",
            type = "String"
    )
    private String constitutionForm;

    @Schema(
            description = "Data de Abertura",
            example = "2021/01/01",
            type = "String"
    )
    private String openDate;

    @Schema(
            description = "Faturamento Anual",
            example = "150.25",
            type = "String"
    )
    private String anualBillingVol;

    @Schema(
            description = "Volume Anual de Faturamento",
            example = "150.25",
            type = "String"
    )
    private String anualVolCash;

    @Schema(
            description = "Volume Anual de Vendas de Cartao",
            example = "150.25",
            type = "String"
    )
    private String anualVolSalesCard;

    @Schema(
            description = "Volume Anual de Venda de Cartao por Grupo",
            example = "150.25",
            type = "String"
    )
    private String anualVolSalesCardGroup;

    @Schema(
            description = "Faturamento Mensal",
            example = "12,52",
            type = "String"
    )
    private String averageTicket;

    @Schema(
            description = "Permissao do Bacen",
            example = "SIM",
            type = "String"
    )
    private String bacenPermission;

    @Schema(
            description = "Codigo da Campanha",
            example = "10",
            type = "String"
    )
    private String campaingCode;

    @Schema(
            description = "CNAE",
            example = "8630504",
            type = "String"
    )
    private String cnae;

    @Schema(
            description = "E-Commerce",
            example = "TRADITIONAL",
            type = "String"
    )
    private String eCommerce;

    @Schema(
            description = "Cartao Extrangeiro",
            example = "NÃO",
            type = "String"
    )
    private String foreignCard;

    @Schema(
            description = "Antecipacao Manual",
            example = "SIM",
            type = "String"
    )
    private String manualPrepaymentEnabled;

    @Schema(
            description = "Agencia de Boarding",
            example = "02949",
            type = "String"
    )
    private String boardingBranching;

    @Schema(
            description = "Percentual de Cartao Nao Presente",
            example = "20",
            type = "String"
    )
    private String percCardNotPresent;

    @Schema(
            description = "Percentual de Cartao Presente",
            example = "20",
            type = "String"
    )
    private String percCardPresent;

    @Schema(
            description = "Percentual Internet",
            example = "20",
            type = "String"
    )
    private String percInternet;

    @Schema(
            description = "Indicador de Antecipacao",
            example = "SIM",
            type = "String"
    )
    private String prepaymentIndicator;


    @Schema(
            description = "Transação Recorrente",
            example = "SIM",
            type = "String"
    )
    private String recurringTransaction;

    @Schema(
            description = "Dia de Servico 0",
            example = "20",
            type = "String"
    )
    private String serviceDay0;

    @Schema(
            description = "Dia de Servico 15-30",
            example = "20",
            type = "String"
    )
    private String serviceDay1530;

    @Schema(
            description = "Dia de Servico 1-7",
            example = "20",
            type = "String"
    )
    private String serviceDay17;

    @Schema(
            description = "Dia de Servico 8-14",
            example = "20",
            type = "String"
    )
    private String serviceDay814;

    @Schema(
            description = "Dia de Servico Mais de 30",
            example = "20",
            type = "String"
    )
    private String serviceDayOver30;

    @Schema(
            description = "Data em que foi para o BW",
            example = "2021/01/01",
            type = "String"
    )
    private String pendingBwDate;

    @Schema(
            description = "Data de Conclusao",
            example = "2021/01/01",
            type = "String"
    )
    private String conclusionBWDate;

}
