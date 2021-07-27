package com.fiserv.preproposal.api.domain.dtos;

import com.univocity.parsers.annotations.Parsed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteReport {

    public static final String NAME = "complete";

    @Parsed(field = "ID da proposta")
    @Schema(
            description = "ID da Pre Proposta",
            example = "5763",
            type = "number"
    )
    private Long preproposalId;

    @Parsed(field = "Numero da pre proposta")
    @Schema(
            description = "Numero da Proposta no Fiserv Online",
            example = "O00000001976443",
            type = "String"
    )
    private String proposalNumber;

    @Parsed(field = "Merchant ID")
    @Schema(
            description = "Merchant do Fiserv Online",
            example = "91041155",
            type = "String"
    )
    private String merchant;

    @Parsed(field = "UserId")
    @Schema(
            description = "ID do Usuario",
            example = "CEFP12",
            type = "String"
    )
    private String userId;

    @Parsed(field = "CPF ou CNPJ do agente")
    @Schema(
            description = "CPF/CNPJ do agente",
            example = "27231934897",
            type = "String"
    )
    private String agentCpfCnpj;

    @Parsed(field = "Instituicao")
    @Schema(
            description = "Instituicao",
            example = "00000007",
            type = "String"
    )
    private String institution;

    @Parsed(field = "service_contract")
    @Schema(
            description = "Service Contract",
            example = "149",
            type = "number"
    )
    private Integer serviceContract;

    @Parsed(field = "Opt In")
    @Schema(
            description = "Opt In",
            example = "SIM/NAO",
            type = "String"
    )
    private String optIn;

    @Parsed(field = "Matricula do Vendedor")
    @Schema(
            description = "Matrícula do Vendedor",
            example = "C654321",
            type = "String"
    )
    private String sellerRegistration;

    @Parsed(field = "Sub Canal")
    @Schema(
            description = "Sub Canal",
            example = "0425",
            type = "String"
    )
    private String subChannel;

    @Parsed(field = "Tecnologia")
    @Schema(
            description = "Tecnologia",
            example = "365-POS Auto Credenc - POS Auto Credenc",
            type = "String"
    )
    private String technology;

    @Parsed(field = "Numero de terminais")
    @Schema(
            description = "Numero de Terminais",
            example = "12",
            type = "number"
    )
    private Long terminalsNumber;

    @Parsed(field = "Valor unitario")
    @Schema(
            description = "Valor Unitario",
            example = "10.50",
            type = "String"
    )
    private String unitaryValue;

    @Parsed(field = "Status - FISERV")
    @Schema(
            description = "Fiserv Status",
            example = "PRE1-Pendente análise pré proposta",
            type = "String"
    )
    private String fiservStatus;

    @Parsed(field = "Status - CAIXA")
    @Schema(
            description = "Caixa Status",
            example = "1-Efetivada",
            type = "String"
    )
    private String caixaStatus;

    @Parsed(field = "Mensagem Caixa")
    @Schema(
            description = "Caixa Mensagem",
            example = "Concluido",
            type = "String"
    )
    private String caixaMessage;

    @Parsed(field = "Erros")
    @Schema(
            description = "Erros de processamento dos bulks",
            example = "Errors",
            type = "String"
    )
    private String errors;

    @Parsed(field = "Inclusao em")
    @Schema(
            description = "Data de Inclusao",
            example = "12/05/2021 11:44",
            type = "String"
    )
    private String includeIn;

    @Parsed(field = "Conclusao em")
    @Schema(
            description = "Data de Conclusao",
            example = "30/06/2021 12:06",
            type = "String"
    )
    private String finishedIn;

    @Parsed(field = "Submissao ao online em")
    @Schema(
            description = "Data de Envio para o Fiserv Online",
            example = "28/06/2021 14:23",
            type = "String"
    )
    private String submissionOnlineDate;

    @Parsed(field = "Tipo de pessoa")
    @Schema(
            description = "Tipo de Pessoa",
            example = "Fisica",
            type = "String"
    )
    private String personType;

    @Parsed(field = "CPF/CNPJ")
    @Schema(
            description = "CPF/CNPJ",
            example = "27231934897",
            type = "String"
    )
    private String cpfCnpj;

    @Parsed(field = "Nome Fantasia")
    @Schema(
            description = "Nome Fantasia",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String fantasyName;

    @Parsed(field = "Razao Social")
    @Schema(
            description = "Razao Social",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String socialReason;

    @Parsed(field = "Nome Plaqueta (Comprovante)")
    @Schema(
            description = "Nome da Plaqueta",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String plateletName;

    @Parsed(field = "Faturamento Mensal")
    @Schema(
            description = "Faturamento Mensal",
            example = "12.50",
            type = "String"
    )
    private String montlyBilling;

    @Parsed(field = "Nascimento/Constituicao")
    @Schema(
            description = "Data de Nascimento",
            example = "2016/03/11",
            type = "String"
    )
    private String birthDate;

    @Parsed(field = "Genero")
    @Schema(
            description = "Genero (M - Masculino, F - Feminino)",
            example = "M",
            type = "String"
    )
    private String gender;

    @Parsed(field = "Pronome de tratamento")
    @Schema(
            description = "Pronome de Tratamento",
            example = "MR",
            type = "String"
    )
    private String treatmentPronoun;

    @Parsed(field = "Local de nascimento")
    @Schema(
            description = "Local de Nascimento",
            example = "Sao Paulo",
            type = "String"
    )
    private String birthPlace;

    @Parsed(field = "Nacionalidade")
    @Schema(
            description = "Nacionalidade",
            example = "Brasileiro",
            type = "String"
    )
    private String nacionality;

    @Parsed(field = "Nome completo")
    @Schema(
            description = "Nome Completo",
            example = "Jhon Doe",
            type = "String"
    )
    private String fullName;

    @Parsed(field = "Inscricao Municipal")
    @Schema(
            description = "Inscricao Municipal",
            type = "String"
    )
    private String municipalRegistration;

    @Parsed(field = "Inscricao estadual")
    @Schema(
            description = "Inscricao Estadual",
            example = "000000000000000",
            type = "String"
    )
    private String stateRegistration;

    @Parsed(field = "Forma de constituicao")
    @Schema(
            description = "Forma de Constituicao",
            example = "Sociedade Empresária",
            type = "String"
    )
    private String constitutionForm;

    @Parsed(field = "Data de abertura")
    @Schema(
            description = "Data de Abertura",
            example = "2021/01/01",
            type = "String"
    )
    private String openDate;

    @Parsed(field = "ANNUAL_BILLING_VOL")
    @Schema(
            description = "Faturamento Anual",
            example = "150.25",
            type = "String"
    )
    private String annualBillingVol;

    @Parsed(field = "ANNUAL_VOL_CASH")
    @Schema(
            description = "Volume Anual de Faturamento",
            example = "150.25",
            type = "String"
    )
    private String annualVolCash;

    @Parsed(field = "ANNUAL_VOL_SALES_CARD")
    @Schema(
            description = "Volume Anual de Vendas de Cartao",
            example = "150.25",
            type = "String"
    )
    private String annualVolSalesCard;

    @Parsed(field = "ANNUAL_VOL_SALES_CARD_GROUP")
    @Schema(
            description = "Volume Anual de Venda de Cartao por Grupo",
            example = "150.25",
            type = "String"
    )
    private String annualVolSalesCardGroup;

    @Parsed(field = "AVERAGE_TICKET")
    @Schema(
            description = "Faturamento Mensal",
            example = "12,52",
            type = "String"
    )
    private String averageTicket;

    @Parsed(field = "Permissao Bacen")
    @Schema(
            description = "Permissao do Bacen",
            example = "SIM",
            type = "String"
    )
    private String bacenPermission;

    @Parsed(field = "Codigo da campanha")
    @Schema(
            description = "Codigo da Campanha",
            example = "10",
            type = "String"
    )
    private String campaingId;

    @Parsed(field = "CNAE")
    @Schema(
            description = "CNAE",
            example = "8630504",
            type = "String"
    )
    private String cnae;

    @Parsed(field = "ECOMMERCE")
    @Schema(
            description = "E-Commerce",
            example = "TRADITIONAL",
            type = "String"
    )
    private String eCommerce;

    @Parsed(field = "Cartao estrangeiro")
    @Schema(
            description = "Cartao Extrangeiro",
            example = "NÃO",
            type = "String"
    )
    private String foreignCard;

    @Parsed(field = "MANUAL_PREPAYMENT_ENABLED")
    @Schema(
            description = "Antecipacao Manual",
            example = "SIM",
            type = "String"
    )
    private String manualPrepaymentEnabled;

    @Parsed(field = "BOARDING_BRANCHING")
    @Schema(
            description = "Agencia de Boarding",
            example = "02949",
            type = "String"
    )
    private String boardingBranching;

    @Parsed(field = "Porc. cartao n/ presente")
    @Schema(
            description = "Percentual de Cartao Nao Presente",
            example = "20",
            type = "String"
    )
    private String percCardNotPresent;

    @Parsed(field = "Porc. cartao presente")
    @Schema(
            description = "Percentual de Cartao Presente",
            example = "20",
            type = "String"
    )
    private String percCardPresent;

    @Parsed(field = "Porc. Internet")
    @Schema(
            description = "Percentual Internet",
            example = "20",
            type = "String"
    )
    private String percInternet;

    @Parsed(field = "PREPAYMENT_INDICATOR")
    @Schema(
            description = "Indicador de Antecipacao",
            example = "SIM",
            type = "String"
    )
    private String prepaymentIndicator;

    @Parsed(field = "Transacao recorrente")
    @Schema(
            description = "Transação Recorrente",
            example = "SIM",
            type = "String"
    )
    private String recurringTransaction;

    @Parsed(field = "SERVICE_DAY_0")
    @Schema(
            description = "Dia de Servico 0",
            example = "20",
            type = "String"
    )
    private String serviceDay0;

    @Parsed(field = "SERVICE_DAY_15_30")
    @Schema(
            description = "Dia de Servico 15-30",
            example = "20",
            type = "String"
    )
    private String serviceDay1530;

    @Parsed(field = "SERVICE_DAY_1_7")
    @Schema(
            description = "Dia de Servico 1-7",
            example = "20",
            type = "String"
    )
    private String serviceDay17;

    @Parsed(field = "SERVICE_DAY_8_14")
    @Schema(
            description = "Dia de Servico 8-14",
            example = "20",
            type = "String"
    )
    private String serviceDay814;

    @Parsed(field = "SERVICE_DAY_OVER_30")
    @Schema(
            description = "Dia de Servico Mais de 30",
            example = "20",
            type = "String"
    )
    private String serviceDayOver30;

    @Parsed(field = "Pendente BW em")
    @Schema(
            description = "Data em que foi para o BW",
            example = "2021/01/01",
            type = "String"
    )
    private String pendingBwDate;

    @Parsed(field = "Data de instalacao")
    @Schema(
            description = "Data de Instalacao",
            example = "2021/01/01",
            type = "String"
    )
    private String installationDate;

    @Parsed(field = "Tipo de endereco")
    @Schema(
            description = "Tipo de Endereco",
            example = "Comercial",
            type = "String"
    )
    private String addressType;

    @Parsed(field = "CEP")
    @Schema(
            description = "CEP",
            example = "71070080",
            type = "String"
    )
    private String cep;

    @Parsed(field = "Rua")
    @Schema(
            description = "Rua",
            example = "Rua Elm",
            type = "String"
    )
    private String street;

    @Parsed(field = "Numero")
    @Schema(
            description = "Numero do Endereco",
            example = "123",
            type = "String"
    )
    private String number;

    @Parsed(field = "Bairro")
    @Schema(
            description = "Distrito",
            example = "Asa Sul",
            type = "String"
    )
    private String district;

    @Parsed(field = "Complemento")
    @Schema(
            description = "Complemento",
            example = "Casa",
            type = "String"
    )
    private String complement;

    @Parsed(field = "Cidade")
    @Schema(
            description = "Cidade",
            example = "BRASILIA",
            type = "String"
    )
    private String city;

    @Parsed(field = "Estado")
    @Schema(
            description = "Estado",
            example = "DF",
            type = "String"
    )
    private String state;

    @Parsed(field = "Tipo do socio")
    @Schema(
            description = "Tipo de Socio",
            example = "PF",
            type = "String"
    )
    private String partnerType;

    @Parsed(field = "CPF/CNPJ do socio")
    @Schema(
            description = "CPF/CNPJ do Socio",
            example = "61999503058",
            type = "String"
    )
    private String cpfCnpjPartner;

    @Parsed(field = "Dta. Nasc. do socio")
    @Schema(
            description = "Data de Nascimento do Socio",
            example = "091198",
            type = "String"
    )
    private String partnerBirthdate;

    @Parsed(field = "Tipo de constituicao - socio")
    @Schema(
            description = "Forma de constituicao",
            example = "4",
            type = "String"
    )
    private String constitutionType;

    @Parsed(field = "Contato - socio")
    @Schema(
            description = "Numero do Telefone de Contato do Socio",
            example = "61981494546",
            type = "String"
    )
    private String contract;

    @Parsed(field = "Nome do socio")
    @Schema(
            description = "Nome do Socio",
            example = "ANTONIO VAZ PESSOA",
            type = "String"
    )
    private String partnerName;

    @Parsed(field = "Pronome de tratamento - Socio")
    @Schema(
            description = "Pronome de Tratamento",
            example = "Mr",
            type = "String"
    )
    private String pronounTreatmentPartner;

    @Parsed(field = "Nacionalidade do socio")
    @Schema(
            description = "Nacionalidade do Socio",
            example = "Brasileiro",
            type = "String"
    )
    private String partnerNacionality;

    @Parsed(field = "Funcao do socio")
    @Schema(
            description = "Nacionalidade do Socio",
            example = "Partner",
            type = "String"
    )
    private String partnerFunction;

    @Parsed(field = "Perc. Participacao do socio")
    @Schema(
            description = "Percentual do Socio",
            example = "20",
            type = "String"
    )
    private String percPartner;

    @Parsed(field = "Dta. nasc. contato")
    @Schema(
            description = "Data de Nascimento do Contato",
            example = "111121",
            type = "String"
    )
    private String contactBirthdate;

    @Parsed(field = "CPF contato")
    @Schema(
            description = "CPF do Contato",
            example = "61999503058",
            type = "String"
    )
    private String contactCpf;

    @Parsed(field = "EMAIL contato")
    @Schema(
            description = "Email do Contato",
            example = "user@email.com",
            type = "String"
    )
    private String contactEmail;

    @Parsed(field = "Nome contato")
    @Schema(
            description = "Nome do Contato",
            example = "Bruce Wayne",
            type = "String"
    )
    private String contactName;

    @Parsed(field = "telefone contato")
    @Schema(
            description = "Telefone do Contato",
            example = "61033018534",
            type = "String"
    )
    private String contactPhone;

    @Parsed(field = "Celular contato")
    @Schema(
            description = "Celular do Contato",
            example = "61033018534",
            type = "String"
    )
    private String contactCellphone;

    @Parsed(field = "DESCRIPTION")
    @Schema(
            description = "Descricao do Account Fee",
            example = "Taxa do pacote de serviços",
            type = "String"
    )
    private String description;

    @Parsed(field = "DISCOUNT_FEE")
    @Schema(
            description = "Desconto do Account Fee",
            example = "12",
            type = "String"
    )
    private String discountFee;

    @Parsed(field = "FEE")
    @Schema(
            description = "Fee do Account Fee",
            example = "0.00",
            type = "String"
    )
    private String fee;

    @Parsed(field = "FEE_ID_NUMBER")
    @Schema(
            description = "ID do Account Fee",
            example = "538283",
            type = "String"
    )
    private String feeIdNumber;

    @Parsed(field = "Codigo do banco")
    @Schema(
            description = "Codigo do Banco",
            example = "104",
            type = "String"
    )
    private String bankCode;

    @Parsed(field = "Agencia")
    @Schema(
            description = "Agencia do Banco",
            example = "006300",
            type = "String"
    )
    private String agency;

    @Parsed(field = "Dig. conta")
    @Schema(
            description = "Digito da Conta",
            example = "1",
            type = "String"
    )
    private String accountDigit;

    @Parsed(field = "Num. Conta")
    @Schema(
            description = "Numero da Conta",
            example = "000300002738",
            type = "String"
    )
    private String accountNumber;

    @Parsed(field = "Nome do responsavel")
    @Schema(
            description = "Dono da Conta",
            example = "ANTONIO VAZ PESSOA",
            type = "String"
    )
    private String accountOwner;

    @Parsed(field = "Tipo de conta")
    @Schema(
            description = "Tipo da Conta",
            example = "Conta corrente",
            type = "String"
    )
    private String accountType;


    @Parsed(field = "Dias de trabalho")
    @Schema(
            description = "Dias trabalhados",
            example = "Domingo 10:00 as 22:00",
            type = "String"
    )
    private String workdays;

    @Parsed(field = "Tipo de proposta")
    @Schema(
            description = "Tipo da resposta",
            example = "FISERV_ONLINE",
            type = "String"
    )
    private String responseType;
}
