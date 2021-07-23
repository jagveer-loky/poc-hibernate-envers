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
public class CompleteReport {

    public static final String HEADER_NAME = "header";

    public static final String NAME = "complete";

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
            description = "Opt In",
            example = "SIM/NAO",
            type = "String"
    )
    private String optIn;

    @Schema(
            description = "Matrícula do Vendedor",
            example = "C654321",
            type = "String"
    )
    private String sellerRegistration;

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
    private String technology;

    @Schema(
            description = "Numero de Terminais",
            example = "12",
            type = "number"
    )
    private Long terminalsNumber;

    @Schema(
            description = "Valor Unitario",
            example = "10.50",
            type = "String"
    )
    private String unitaryValue;

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
            description = "Erros de processamento dos bulks",
            example = "Errors",
            type = "String"
    )
    private String errors;

    @Schema(
            description = "Data de Inclusao",
            example = "12/05/2021 11:44",
            type = "String"
    )
    private String includeIn;

    @Schema(
            description = "Data de Conclusao",
            example = "30/06/2021 12:06",
            type = "String"
    )
    private String finishedIn;

    @Schema(
            description = "Data de Envio para o Fiserv Online",
            example = "28/06/2021 14:23",
            type = "String"
    )
    private String submissionOnlineDate;

    @Schema(
            description = "Tipo de Pessoa",
            example = "Fisica",
            type = "String"
    )
    private String personType;

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
            description = "Nome da Plaqueta",
            example = "A & A RESTAURANTE E LANCHONETE",
            type = "String"
    )
    private String plateletName;

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
    private String annualBillingVol;

    @Schema(
            description = "Volume Anual de Faturamento",
            example = "150.25",
            type = "String"
    )
    private String annualVolCash;

    @Schema(
            description = "Volume Anual de Vendas de Cartao",
            example = "150.25",
            type = "String"
    )
    private String annualVolSalesCard;

    @Schema(
            description = "Volume Anual de Venda de Cartao por Grupo",
            example = "150.25",
            type = "String"
    )
    private String annualVolSalesCardGroup;

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
    private String campaingId;

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
            description = "Data de Instalacao",
            example = "2021/01/01",
            type = "String"
    )
    private String installationDate;

    @Schema(
            description = "Tipo de Endereco",
            example = "Comercial",
            type = "String"
    )
    private String addressType;

    @Schema(
            description = "CEP",
            example = "71070080",
            type = "String"
    )
    private String cep;

    @Schema(
            description = "Rua",
            example = "Rua Elm",
            type = "String"
    )
    private String street;

    @Schema(
            description = "Numero do Endereco",
            example = "123",
            type = "String"
    )
    private String number;

    @Schema(
            description = "Distrito",
            example = "Asa Sul",
            type = "String"
    )
    private String district;

    @Schema(
            description = "Complemento",
            example = "Casa",
            type = "String"
    )
    private String complement;

    @Schema(
            description = "Cidade",
            example = "BRASILIA",
            type = "String"
    )
    private String city;

    @Schema(
            description = "Estado",
            example = "DF",
            type = "String"
    )
    private String state;

    @Schema(
            description = "Tipo de Socio",
            example = "PF",
            type = "String"
    )
    private String partnerType;

    @Schema(
            description = "CPF/CNPJ do Socio",
            example = "61999503058",
            type = "String"
    )
    private String cpfCnpjPartner;

    @Schema(
            description = "Data de Nascimento do Socio",
            example = "091198",
            type = "String"
    )
    private String partnerBirthdate;

    @Schema(
            description = "Forma de constituicao",
            example = "4",
            type = "String"
    )
    private String constitutionType;

    @Schema(
            description = "Numero do Telefone de Contato do Socio",
            example = "61981494546",
            type = "String"
    )
    private String contract;

    @Schema(
            description = "Nome do Socio",
            example = "ANTONIO VAZ PESSOA",
            type = "String"
    )
    private String partnerName;

    @Schema(
            description = "Pronome de Tratamento",
            example = "Mr",
            type = "String"
    )
    private String pronounTreatmentPartner;

    @Schema(
            description = "Nacionalidade do Socio",
            example = "Brasileiro",
            type = "String"
    )
    private String partnerNacionality;

    @Schema(
            description = "Nacionalidade do Socio",
            example = "Partner",
            type = "String"
    )
    private String partnerFunction;

    @Schema(
            description = "Percentual do Socio",
            example = "20",
            type = "String"
    )
    private String percPartner;

    @Schema(
            description = "Data de Nascimento do Contato",
            example = "111121",
            type = "String"
    )
    private String contactBirthdate;

    @Schema(
            description = "CPF do Contato",
            example = "61999503058",
            type = "String"
    )
    private String contactCpf;

    @Schema(
            description = "Email do Contato",
            example = "user@email.com",
            type = "String"
    )
    private String contactEmail;

    @Schema(
            description = "Nome do Contato",
            example = "Bruce Wayne",
            type = "String"
    )
    private String contactName;

    @Schema(
            description = "Telefone do Contato",
            example = "61033018534",
            type = "String"
    )
    private String contactPhone;

    @Schema(
            description = "Celular do Contato",
            example = "61033018534",
            type = "String"
    )
    private String contactCellphone;

    @Schema(
            description = "Descricao do Account Fee",
            example = "Taxa do pacote de serviços",
            type = "String"
    )
    private String description;

    @Schema(
            description = "Desconto do Account Fee",
            example = "12",
            type = "String"
    )
    private String discountFee;

    @Schema(
            description = "Fee do Account Fee",
            example = "0.00",
            type = "String"
    )
    private String fee;

    @Schema(
            description = "ID do Account Fee",
            example = "538283",
            type = "String"
    )
    private String feeIdNumber;

    @Schema(
            description = "Codigo do Banco",
            example = "104",
            type = "String"
    )
    private String bankCode;

    @Schema(
            description = "Agencia do Banco",
            example = "006300",
            type = "String"
    )
    private String agency;

    @Schema(
            description = "Digito da Conta",
            example = "1",
            type = "String"
    )
    private String accountDigit;

    @Schema(
            description = "Numero da Conta",
            example = "000300002738",
            type = "String"
    )
    private String accountNumber;

    @Schema(
            description = "Dono da Conta",
            example = "ANTONIO VAZ PESSOA",
            type = "String"
    )
    private String accountOwner;

    @Schema(
            description = "Tipo da Conta",
            example = "Conta corrente",
            type = "String"
    )
    private String accountType;




    @Schema(
            description = "Dias trabalhados",
            example = "Domingo 10:00 as 22:00",
            type = "String"
    )
    private String workdays;

    @Schema(
            description = "Tipo da resposta",
            example = "FISERV_ONLINE",
            type = "String"
    )
    private String responseType;
}
