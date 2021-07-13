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
public class ErrorsReport {

    public static final String HEADER_NAME = "header";

    public static final String NAME = "errors";

    @Schema(
            description = "Nome do arquivo",
            example = "CNT.CRD.MZ.BCX0.MREMPCRE.D20210625.H191504",
            type = "String"
    )
    private String filename;

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
            description = "Tipo da resposta",
            example = "FISERV_ONLINE",
            type = "String"
    )
    private String responseType;

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
            description = "Data de Processamento",
            example = "30/06/2021 08:06",
            type = "String"
    )
    private String processingDate;

    @Schema(
            description = "Field de erro",
            example = "businessAddressNumber",
            type = "String"
    )
    private String error;

    @Schema(
            description = "Descricao do erro",
            example = "Erro ao enviar para o Fiserv Online",
            type = "String"
    )
    private String errorDescription;

    @Schema(
            description = "Mensagem de erro",
            example = "Não pode ser zero",
            type = "String"
    )
    private String errorMessage;

    @Schema(
            description = "Detalhe",
            example = "Detalhe",
            type = "String"
    )
    private String detail;

}
