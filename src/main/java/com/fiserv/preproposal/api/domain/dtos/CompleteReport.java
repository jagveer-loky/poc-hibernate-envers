package com.fiserv.preproposal.api.domain.dtos;

import com.fiserv.preproposal.api.application.annotation.Index;
import com.univocity.parsers.annotations.Parsed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteReport extends AbstractReport implements Serializable {

    @Index(2)
    @Parsed(field = "ID da proposta")
    @Schema(
            description = "ID da Pre Proposta",
            example = "O00000001976443",
            type = "String"
    )
    private String preproposalId;

    @Index(1)
    @Parsed(field = "Numero da pre proposta")
    @Schema(
            description = "Numero da Proposta no Fiserv Online",
            example = "5763",
            type = "number"
    )
    private Long proposalNumber;

    @Index(3)
    @Parsed(field = "Merchant ID")
    @Schema(
            description = "Merchant do Fiserv Online",
            example = "91041155",
            type = "String"
    )
    private String merchant;

    @Index(4)
    @Parsed(field = "UserId")
    @Schema(
            description = "ID do Usuario",
            example = "CEFP12",
            type = "String"
    )
    private String userId;

    @Index(5)
    @Parsed(field = "CPF ou CNPJ do agente")
    @Schema(
            description = "CPF/CNPJ do agente",
            example = "27231934897",
            type = "String"
    )
    private String agentCpfCnpj;

    @Index(6)
    @Parsed(field = "Instituicao")
    @Schema(
            description = "Instituicao",
            example = "00000007",
            type = "String"
    )
    private String institution;

    @Index(7)
    @Parsed(field = "SERVICE_CONTRACT")
    @Schema(
            description = "Service Contract",
            example = "149",
            type = "number"
    )
    private Integer serviceContract;

    @Index(95)
    @Parsed(field = "Opt In")
    @Schema(
            description = "Opt In",
            example = "SIM/NAO",
            type = "String"
    )
    private String optIn;

    @Index(94)
    @Parsed(field = "Matricula do Vendedor")
    @Schema(
            description = "Matrícula do Vendedor",
            example = "C654321",
            type = "String"
    )
    private String sellerRegistration;

    @Index(8)
    @Parsed(field = "Sub Canal")
    @Schema(
            description = "Sub Canal",
            example = "0425",
            type = "String"
    )
    private String subChannel;

    @Index(9)
    @Parsed(field = "Tecnologia")
    @Schema(
            description = "Tecnologia",
            example = "365-POS Auto Credenc - POS Auto Credenc",
            type = "String"
    )
    private String technology;

    @Index(10)
    @Parsed(field = "Numero de terminais")
    @Schema(
            description = "Numero de Terminais",
            example = "12",
            type = "number"
    )
    private Long terminalsNumber;

    @Index(11)
    @Parsed(field = "Valor unitario")
    @Schema(
            description = "Valor Unitario",
            example = "10.50",
            type = "String"
    )
    private String unitaryValue;

    @Index(12)
    @Parsed(field = "Status - FISERV")
    @Schema(
            description = "Fiserv Status",
            example = "PRE1-Pendente análise pré proposta",
            type = "String"
    )
    private String fiservStatus;

    @Index(14)
    @Parsed(field = "Status - CAIXA")
    @Schema(
            description = "Caixa Status",
            example = "1-Efetivada",
            type = "String"
    )
    private String caixaStatus;

    @Index(15)
    @Parsed(field = "Mensagem Caixa")
    @Schema(
            description = "Caixa Mensagem",
            example = "Concluido",
            type = "String"
    )
    private String caixaMessage;

//    @Index(13)
//    @Parsed(field = "Erros")
//    @Schema(
//            description = "Erros de processamento dos bulks",
//            example = "Errors",
//            type = "String"
//    )
//    private String errors;

    @Index(16)
    @Parsed(field = "Inclusao em")
    @Schema(
            description = "Data de Inclusao",
            example = "12/05/2021 11:44",
            type = "String"
    )
    private String includeIn;

    @Index(17)
    @Parsed(field = "Conclusao em")
    @Schema(
            description = "Data de Conclusao",
            example = "30/06/2021 12:06",
            type = "String"
    )
    private String finishedIn;

    @Index(18)
    @Parsed(field = "Submissao ao online em")
    @Schema(
            description = "Data de Envio para o Fiserv Online",
            example = "28/06/2021 14:23",
            type = "String"
    )
    private String submissionOnlineDate;

    @Index(19)
    @Parsed(field = "Tipo de pessoa")
    @Schema(
            description = "Tipo de Pessoa",
            example = "Fisica",
            type = "String"
    )
    private String personType;

    @Index(20)
    @Parsed(field = "CPF/CNPJ")
    @Schema(
            description = "CPF/CNPJ",
            example = "27231934897",
            type = "String"
    )
    private String cpfCnpj;

    @Index(21)
    @Parsed(field = "Nome Fantasia")
    @Schema(
            description = "Nome Fantasia",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String fantasyName;

    @Index(22)
    @Parsed(field = "Razao Social")
    @Schema(
            description = "Razao Social",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String socialReason;

    @Index(23)
    @Parsed(field = "Nome Plaqueta (Comprovante)")
    @Schema(
            description = "Nome da Plaqueta",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String plateletName;

    @Index(24)
    @Parsed(field = "Faturamento Mensal")
    @Schema(
            description = "Faturamento Mensal",
            example = "12.50",
            type = "String"
    )
    private String montlyBilling;

    @Index(25)
    @Parsed(field = "Nascimento/Constituicao")
    @Schema(
            description = "Data de Nascimento",
            example = "2016/03/11",
            type = "String"
    )
    private String birthDate;

    @Index(26)
    @Parsed(field = "Genero")
    @Schema(
            description = "Genero (M - Masculino, F - Feminino)",
            example = "M",
            type = "String"
    )
    private String gender;

    @Index(27)
    @Parsed(field = "Pronome de tratamento")
    @Schema(
            description = "Pronome de Tratamento",
            example = "MR",
            type = "String"
    )
    private String treatmentPronoun;

    @Index(28)
    @Parsed(field = "Local de nascimento")
    @Schema(
            description = "Local de Nascimento",
            example = "Sao Paulo",
            type = "String"
    )
    private String birthPlace;

    @Index(29)
    @Parsed(field = "Nacionalidade")
    @Schema(
            description = "Nacionalidade",
            example = "Brasileiro",
            type = "String"
    )
    private String nacionality;

    @Index(30)
    @Parsed(field = "Nome completo")
    @Schema(
            description = "Nome Completo",
            example = "Jhon Doe",
            type = "String"
    )
    private String fullName;

    @Index(31)
    @Parsed(field = "Inscricao Municipal")
    @Schema(
            description = "Inscricao Municipal",
            type = "String"
    )
    private String municipalRegistration;

    @Index(32)
    @Parsed(field = "Inscricao estadual")
    @Schema(
            description = "Inscricao Estadual",
            example = "000000000000000",
            type = "String"
    )
    private String stateRegistration;

    @Index(33)
    @Parsed(field = "Forma de constituicao")
    @Schema(
            description = "Forma de Constituicao",
            example = "Sociedade Empresária",
            type = "String"
    )
    private String constitutionForm;

    @Index(34)
    @Parsed(field = "Data de abertura")
    @Schema(
            description = "Data de Abertura",
            example = "2021/01/01",
            type = "String"
    )
    private String openDate;

    @Index(35)
    @Parsed(field = "ANNUAL_BILLING_VOL")
    @Schema(
            description = "Faturamento Anual",
            example = "150.25",
            type = "String"
    )
    private String annualBillingVol;

    @Index(36)
    @Parsed(field = "ANNUAL_VOL_CASH")
    @Schema(
            description = "Volume Anual de Faturamento",
            example = "150.25",
            type = "String"
    )
    private String annualVolCash;

    @Index(37)
    @Parsed(field = "ANNUAL_VOL_SALES_CARD")
    @Schema(
            description = "Volume Anual de Vendas de Cartao",
            example = "150.25",
            type = "String"
    )
    private String annualVolSalesCard;

    @Index(38)
    @Parsed(field = "ANNUAL_VOL_SALES_CARD_GROUP")
    @Schema(
            description = "Volume Anual de Venda de Cartao por Grupo",
            example = "150.25",
            type = "String"
    )
    private String annualVolSalesCardGroup;

    @Index(39)
    @Parsed(field = "AVERAGE_TICKET")
    @Schema(
            description = "Faturamento Mensal",
            example = "12,52",
            type = "String"
    )
    private String averageTicket;

    @Index(40)
    @Parsed(field = "Permissao Bacen")
    @Schema(
            description = "Permissao do Bacen",
            example = "SIM",
            type = "String"
    )
    private String bacenPermission;

    @Index(41)
    @Parsed(field = "Codigo da campanha")
    @Schema(
            description = "Codigo da Campanha",
            example = "10",
            type = "String"
    )
    private String campaingId;

    @Index(42)
    @Parsed(field = "CNAE")
    @Schema(
            description = "CNAE",
            example = "8630504",
            type = "String"
    )
    private String cnae;

    @Index(43)
    @Parsed(field = "ECOMMERCE")
    @Schema(
            description = "E-Commerce",
            example = "TRADITIONAL",
            type = "String"
    )
    private String eCommerce;

    @Index(44)
    @Parsed(field = "Cartao estrangeiro")
    @Schema(
            description = "Cartao Extrangeiro",
            example = "NÃO",
            type = "String"
    )
    private String foreignCard;

    @Index(45)
    @Parsed(field = "MANUAL_PREPAYMENT_ENABLED")
    @Schema(
            description = "Antecipacao Manual",
            example = "SIM",
            type = "String"
    )
    private String manualPrepaymentEnabled;

    @Index(46)
    @Parsed(field = "BOARDING_BRANCHING")
    @Schema(
            description = "Agencia de Boarding",
            example = "02949",
            type = "String"
    )
    private String boardingBranching;

    @Index(47)
    @Parsed(field = "Porc. cartao n/ presente")
    @Schema(
            description = "Percentual de Cartao Nao Presente",
            example = "20",
            type = "String"
    )
    private String percCardNotPresent;

    @Index(48)
    @Parsed(field = "Porc. cartao presente")
    @Schema(
            description = "Percentual de Cartao Presente",
            example = "20",
            type = "String"
    )
    private String percCardPresent;

    @Index(49)
    @Parsed(field = "Porc. Internet")
    @Schema(
            description = "Percentual Internet",
            example = "20",
            type = "String"
    )
    private String percInternet;

    @Index(50)
    @Parsed(field = "PREPAYMENT_INDICATOR")
    @Schema(
            description = "Indicador de Antecipacao",
            example = "SIM",
            type = "String"
    )
    private String prepaymentIndicator;

    @Index(51)
    @Parsed(field = "Transacao recorrente")
    @Schema(
            description = "Transação Recorrente",
            example = "SIM",
            type = "String"
    )
    private String recurringTransaction;

    @Index(52)
    @Parsed(field = "SERVICE_DAY_0")
    @Schema(
            description = "Dia de Servico 0",
            example = "20",
            type = "String"
    )
    private String serviceDay0;

    @Index(53)
    @Parsed(field = "SERVICE_DAY_15_30")
    @Schema(
            description = "Dia de Servico 15-30",
            example = "20",
            type = "String"
    )
    private String serviceDay1530;

    @Index(54)
    @Parsed(field = "SERVICE_DAY_1_7")
    @Schema(
            description = "Dia de Servico 1-7",
            example = "20",
            type = "String"
    )
    private String serviceDay17;

    @Index(55)
    @Parsed(field = "SERVICE_DAY_8_14")
    @Schema(
            description = "Dia de Servico 8-14",
            example = "20",
            type = "String"
    )
    private String serviceDay814;

    @Index(56)
    @Parsed(field = "SERVICE_DAY_OVER_30")
    @Schema(
            description = "Dia de Servico Mais de 30",
            example = "20",
            type = "String"
    )
    private String serviceDayOver30;

    @Index(57)
    @Parsed(field = "Pendente BW em")
    @Schema(
            description = "Data em que foi para o BW",
            example = "2021/01/01",
            type = "String"
    )
    private String pendingBwDate;

    @Index(58)
    @Parsed(field = "Data de instalacao")
    @Schema(
            description = "Data de Instalacao",
            example = "2021/01/01",
            type = "String"
    )
    private String installationDate;

    @Index(59)
    @Parsed(field = "Tipo de endereco")
    @Schema(
            description = "Tipo de Endereco",
            example = "Comercial",
            type = "String"
    )
    private String addressType;

    @Index(60)
    @Parsed(field = "CEP")
    @Schema(
            description = "CEP",
            example = "71070080",
            type = "String"
    )
    private String cep;

    @Index(61)
    @Parsed(field = "Rua")
    @Schema(
            description = "Rua",
            example = "Rua Elm",
            type = "String"
    )
    private String street;

    @Index(62)
    @Parsed(field = "Numero do Endereco")
    @Schema(
            description = "Numero do Endereco",
            example = "123",
            type = "String"
    )
    private String number;

    @Index(63)
    @Parsed(field = "Bairro")
    @Schema(
            description = "Distrito",
            example = "Asa Sul",
            type = "String"
    )
    private String district;

    @Index(64)
    @Parsed(field = "Complemento")
    @Schema(
            description = "Complemento",
            example = "Casa",
            type = "String"
    )
    private String complement;

    @Index(65)
    @Parsed(field = "Cidade")
    @Schema(
            description = "Cidade",
            example = "BRASILIA",
            type = "String"
    )
    private String city;

    @Index(66)
    @Parsed(field = "Estado")
    @Schema(
            description = "Estado",
            example = "DF",
            type = "String"
    )
    private String state;

    @Index(67)
    @Parsed(field = "Tipo do socio")
    @Schema(
            description = "Tipo de Socio",
            example = "PF",
            type = "String"
    )
    private String partnerType;

    @Index(68)
    @Parsed(field = "CPF ou CNPJ do socio")
    @Schema(
            description = "CPF ou CNPJ do socio",
            example = "61999503058",
            type = "String"
    )
    private String cpfCnpjPartner;

    @Index(69)
    @Parsed(field = "Dta. Nasc. do socio")
    @Schema(
            description = "Data de Nascimento do Socio",
            example = "091198",
            type = "String"
    )
    private String partnerBirthdate;

    @Index(70)
    @Parsed(field = "Tipo de constituicao - socio")
    @Schema(
            description = "Forma de constituicao",
            example = "4",
            type = "String"
    )
    private String constitutionType;

    @Index(71)
    @Parsed(field = "Contato - socio")
    @Schema(
            description = "Numero do Telefone de Contato do Socio",
            example = "61981494546",
            type = "String"
    )
    private String contract;

    @Index(72)
    @Parsed(field = "Nome do socio")
    @Schema(
            description = "Nome do Socio",
            example = "ANTONIO VAZ PESSOA",
            type = "String"
    )
    private String partnerName;

    @Index(73)
    @Parsed(field = "Pronome de tratamento - Socio")
    @Schema(
            description = "Pronome de Tratamento",
            example = "Mr",
            type = "String"
    )
    private String pronounTreatmentPartner;

    @Index(74)
    @Parsed(field = "Nacionalidade do socio")
    @Schema(
            description = "Nacionalidade do Socio",
            example = "Brasileiro",
            type = "String"
    )
    private String partnerNacionality;

    @Index(75)
    @Parsed(field = "Funcao do socio")
    @Schema(
            description = "Nacionalidade do Socio",
            example = "Partner",
            type = "String"
    )
    private String partnerFunction;

    @Index(76)
    @Parsed(field = "Perc. Participacao do socio")
    @Schema(
            description = "Percentual do Socio",
            example = "20",
            type = "String"
    )
    private String percPartner;

    @Index(77)
    @Parsed(field = "Dta. nasc. contato")
    @Schema(
            description = "Data de Nascimento do Contato",
            example = "111121",
            type = "String"
    )
    private String contactBirthdate;

    @Index(78)
    @Parsed(field = "CPF contato")
    @Schema(
            description = "CPF do Contato",
            example = "61999503058",
            type = "String"
    )
    private String contactCpf;

    @Index(79)
    @Parsed(field = "EMAIL contato")
    @Schema(
            description = "Email do Contato",
            example = "user@email.com",
            type = "String"
    )
    private String contactEmail;

    @Index(80)
    @Parsed(field = "Nome contato")
    @Schema(
            description = "Nome do Contato",
            example = "Bruce Wayne",
            type = "String"
    )
    private String contactName;

    @Index(81)
    @Parsed(field = "telefone contato")
    @Schema(
            description = "Telefone do Contato",
            example = "61033018534",
            type = "String"
    )
    private String contactPhone;

    @Index(82)
    @Parsed(field = "Celular contato")
    @Schema(
            description = "Celular do Contato",
            example = "61033018534",
            type = "String"
    )
    private String contactCellphone;

    @Index(83)
    @Parsed(field = "DESCRIPTION")
    @Schema(
            description = "Descricao do Account Fee",
            example = "Taxa do pacote de serviços",
            type = "String"
    )
    private String description;

    @Index(84)
    @Parsed(field = "DISCOUNT_FEE")
    @Schema(
            description = "Desconto do Account Fee",
            example = "12",
            type = "String"
    )
    private String discountFee;

    @Index(85)
    @Parsed(field = "FEE")
    @Schema(
            description = "Fee do Account Fee",
            example = "0.00",
            type = "String"
    )
    private String fee;

    @Index(86)
    @Parsed(field = "FEE_ID_NUMBER")
    @Schema(
            description = "ID do Account Fee",
            example = "538283",
            type = "String"
    )
    private String feeIdNumber;

    @Index(87)
    @Parsed(field = "Codigo do banco")
    @Schema(
            description = "Codigo do Banco",
            example = "104",
            type = "String"
    )
    private String bankCode;

    @Index(88)
    @Parsed(field = "Agencia")
    @Schema(
            description = "Agencia do Banco",
            example = "006300",
            type = "String"
    )
    private String agency;

    @Index(89)
    @Parsed(field = "Dig. conta")
    @Schema(
            description = "Digito da Conta",
            example = "1",
            type = "String"
    )
    private String accountDigit;

    @Index(90)
    @Parsed(field = "Num. Conta")
    @Schema(
            description = "Numero da Conta",
            example = "000300002738",
            type = "String"
    )
    private String accountNumber;

    @Index(91)
    @Parsed(field = "Nome do responsavel")
    @Schema(
            description = "Dono da Conta",
            example = "ANTONIO VAZ PESSOA",
            type = "String"
    )
    private String accountOwner;

    @Index(92)
    @Parsed(field = "Tipo de conta")
    @Schema(
            description = "Tipo da Conta",
            example = "Conta corrente",
            type = "String"
    )
    private String accountType;


    @Index(97)
    @Parsed(field = "Dias de trabalho")
    @Schema(
            description = "Dias trabalhados",
            example = "Domingo 10:00 as 22:00",
            type = "String"
    )
    private String workdays;

    @Index(98)
    @Parsed(field = "Tipo de proposta")
    @Schema(
            description = "Tipo da resposta",
            example = "FISERV_ONLINE",
            type = "String"
    )
    private String responseType;

    @Index(96)
    @Parsed(field = "Termo de aceite")
    @Schema(
            description = "Termo de aceite",
            example = "Sim",
            type = "String"
    )
    private String acceptTerm;

    @Index(93)
    @Parsed(field = "Tipo de Estabelecimento")
    @Schema(
            description = "Tipo de Estabelecimento",
            example = "Comercial",
            type = "String"
    )
    private String typeOfEstablishment;
}
