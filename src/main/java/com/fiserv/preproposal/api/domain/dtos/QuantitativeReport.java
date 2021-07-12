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
public class QuantitativeReport {

    public static final String HEADER_NAME = "header";

    public static final String NAME = "quantitative";

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
            description = "Data de Processamento",
            example = "30/06/2021 08:06",
            type = "String"
    )
    private String processingDate;

    @Schema(
            description = "Status do arquivo",
            example = "Lido com sucesso",
            type = "String"
    )
    private String fileStatus;

    @Schema(
            description = "Numero de Propostas",
            example = "12",
            type = "number"
    )
    private Long proposals;

    @Schema(
            description = "Numero de Propostas Rejeitadas pelo Fiserv Online",
            example = "1",
            type = "number"
    )
    private Long onlineRejected;

    @Schema(
            description = "Numero de Propostas Pendentes pelo Fiserv Online",
            example = "4",
            type = "number"
    )
    private Long pendingInFiservOnline;

    @Schema(
            description = "Numero de Propostas Pendentes Complemento",
            example = "3",
            type = "number"
    )
    private Long pendingComplement;

    @Schema(
            description = "Numero de Propostas Com Erro",
            example = "1",
            type = "number"
    )
    private Long preproposalError;

    @Schema(
            description = "Numero de Propostas Finalizadas",
            example = "1",
            type = "number"
    )
    private Long finished;

    @Schema(
            description = "Numero de Propostas Canceladas no TMP",
            example = "1",
            type = "number"
    )
    private Long tmpCanceled;

    @Schema(
            description = "Numero de Propostas no Cred Online",
            example = "1",
            type = "number"
    )
    private Long credOnline;

}
