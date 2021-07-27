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
public class BasicReport {

    public static final String HEADER_NAME = "header";

    public static final String NAME = "basic";

    @Parsed(field = "Id da Proposta")
    @Schema(
            description = "Id da Proposta",
            example = "985641232457887845L",
            type = "String"
    )
    private String id;

    @Parsed(field = "Numero da Proposta")
    @Schema(
            description = "Numero da Proposta",
            example = "O00000001965857",
            type = "String"
    )
    private String proposalNumber;

    @Parsed(field = "CPF/CNPJ da Proposta")
    @Schema(
            description = "CPF/CNPJ da Proposta",
            example = "10167584057",
            type = "String"
    )
    private String cpfCnpj;

    @Parsed(field = "Nome Fantasia")
    @Schema(
            description = "Nome Fantasia",
            example = "Arasaka",
            type = "String"
    )
    private String fantasyName;

    @Parsed(field = "Razao Social")
    @Schema(
            description = "Razao Social",
            example = "Arasaka Corporation",
            type = "String"
    )
    private String socialReason;

    @Parsed(field = "Nome Plaqueta (Comprovante)")
    @Schema(
            description = "Nome Plaqueta (Comprovante)",
            example = "Arasaka Corp",
            type = "String"
    )
    private String voucherName;

    @Parsed(field = "Tipo da resposta")
    @Schema(
            description = "Tipo da resposta",
            example = "FISERV_ONLINE",
            type = "String"
    )
    private String responseType;

    @Parsed(field = "ID do Usuario")
    @Schema(
            description = "ID do Usuario",
            example = "CEFP3333",
            type = "String"
    )
    private String userId;

    @Parsed(field = "CPF/CNPJ do Agente")
    @Schema(
            description = "CPF/CNPJ do Agente",
            example = "73478568099",
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

    @Parsed(field = "Service Contract")
    @Schema(
            description = "Service Contract",
            example = "149",
            type = "String"
    )
    private String serviceContract;

    @Parsed(field = "Opt In")
    @Schema(
            description = "Opt In",
            example = "SIM/NAO",
            type = "String"
    )
    private String optIn;

    @Parsed(field = "Matricula do Vendedor")
    @Schema(
            description = "Matricula do Vendedor",
            example = "C654321",
            type = "String"
    )
    private String sellerRegistration;

    @Parsed(field = "Sub Canal")
    @Schema(
            description = "Sub Canal",
            example = "00647",
            type = "String"
    )
    private String subChannel;

    @Parsed(field = "Tecnologia")
    @Schema(
            description = "Tecnologia",
            example = "18-POS GPRS WIFI",
            type = "String"
    )
    private String tecnology;

    @Parsed(field = "Data de Inclusao")
    @Schema(
            description = "Data de Inclusao",
            example = "03/06/2021 12:06",
            type = "String"
    )
    private String includeDate;

    @Parsed(field = "Data de Conclusao")
    @Schema(
            description = "Data de Conclusao",
            example = "03/06/2021 12:06",
            type = "String"
    )
    private String conclusionDate;

    @Parsed(field = "Merchant ID")
    @Schema(
            description = "Merchant ID",
            example = "O01965857",
            type = "String"
    )
    private String merchantId;

    @Parsed(field = "Fiserv Status")
    @Schema(
            description = "Fiserv Status",
            example = "PRE1-Pendente análise pre proposta",
            type = "String"
    )
    private String fiservStatus;

    @Parsed(field = "Caixa Status")
    @Schema(
            description = "Caixa Status",
            example = "1-Efetivada",
            type = "String"
    )
    private String cefStatus;

    @Parsed(field = "Caixa Mensagem")
    @Schema(
            description = "Caixa Mensagem",
            example = "Concluido",
            type = "String"
    )
    private String cefMessage;

    @Parsed(field = "Erros de processamento dos bulks")
    @Schema(
            description = "Erros de processamento dos bulks",
            example = "Errors",
            type = "String"
    )
    private String errors;

    @Parsed(field = "Data de Submissao")
    @Schema(
            description = "Data de Submissao",
            example = "28/06/2021 12:06",
            type = "String"
    )
    private String submissionDate;

}
