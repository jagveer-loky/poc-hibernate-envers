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
public class DReport1 {

    public static final String NAME = "1";

    @Schema(
            description = "CPF/CNPJ da Proposta",
            example = "10167584057",
            type = "String"
    )
    private String cpfCnpj;

    @Schema(
            description = "Nome Fantasia",
            example = "Arasaka",
            type = "String"
    )
    private String fantasyName;

    @Schema(
            description = "Razao Social",
            example = "Arasaka Corporation",
            type = "String"
    )
    private String socialReason;

    @Schema(
            description = "Nome Plaqueta (Comprovante)",
            example = "Arasaka Corp",
            type = "String"
    )
    private String voucherName;

    @Schema(
            description = "ID do Usuario",
            example = "CEFP3333",
            type = "String"
    )
    private String userId;

    @Schema(
            description = "CPF/CNPJ do Agente",
            example = "73478568099",
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
            type = "String"
    )
    private String serviceContract;

    @Schema(
            description = "Sub Canal",
            example = "00647",
            type = "String"
    )
    private String subChannel;

    @Schema(
            description = "Tecnologia",
            example = "18-POS GPRS WIFI",
            type = "String"
    )
    private String tecnology;

    @Schema(
            description = "Data de Inclusao",
            example = "03/06/2021 12:06",
            type = "String"
    )
    private String includeDate;

    @Schema(
            description = "Data de Conclusao",
            example = "03/06/2021 12:06",
            type = "String"
    )
    private String conclusionDate;

    @Schema(
            description = "ID da Proposta",
            example = "O00000001965857",
            type = "String"
    )
    private String proposalNumber;

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
    private String cefStatus;

    @Schema(
            description = "Caixa Mensagem",
            example = "Concluido",
            type = "String"
    )
    private String cefMessage;

    @Schema(
            description = "Data de Submissao",
            example = "28/06/2021 12:06",
            type = "String"
    )
    private String submissionDate;

}
