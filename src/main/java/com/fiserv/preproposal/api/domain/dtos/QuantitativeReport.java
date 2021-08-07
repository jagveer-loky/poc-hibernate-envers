package com.fiserv.preproposal.api.domain.dtos;

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
public class QuantitativeReport extends AbstractReport implements Serializable {

    @Parsed(field = "Nome do Arquivo")
    @Schema(
            description = "Nome do arquivo",
            example = "CNT.CRD.MZ.BCX0.MREMPCRE.D20210625.H191504",
            type = "String"
    )
    private String filename;

    @Parsed(field = "Instituicao")
    @Schema(
            description = "Instituicao",
            example = "00000007",
            type = "String"
    )
    private String institution;

    @Parsed(field = "Data de processamento")
    @Schema(
            description = "Data de Processamento",
            example = "30/06/2021 08:06",
            type = "String"
    )
    private String processingDate;

    @Parsed(field = "Status do Arquivo")
    @Schema(
            description = "Status do arquivo",
            example = "Lido com sucesso",
            type = "String"
    )
    private String fileStatus;

    @Parsed(field = "Qtd. de Propostas")
    @Schema(
            description = "Numero de Propostas",
            example = "12",
            type = "number"
    )
    private Long proposals;

    @Parsed(field = "Qtd. Rejeitado no Online")
    @Schema(
            description = "Numero de Propostas Rejeitadas pelo Fiserv Online",
            example = "1",
            type = "number"
    )
    private Long onlineRejected;

    @Parsed(field = "Qtd. Pendente no Online")
    @Schema(
            description = "Numero de Propostas Pendentes pelo Fiserv Online",
            example = "4",
            type = "number"
    )
    private Long pendingInFiservOnline;

    @Parsed(field = "Qtd. Pendente Complemento")
    @Schema(
            description = "Numero de Propostas Pendentes Complemento",
            example = "3",
            type = "number"
    )
    private Long pendingComplement;

    @Parsed(field = "Qtd. erro pre proposta")
    @Schema(
            description = "Numero de Propostas Com Erro",
            example = "1",
            type = "number"
    )
    private Long preproposalError;

    @Parsed(field = "Qtd. Concluida")
    @Schema(
            description = "Numero de Propostas Finalizadas",
            example = "1",
            type = "number"
    )
    private Long finished;

    @Parsed(field = "Qtd. Cancelada TMP")
    @Schema(
            description = "Numero de Propostas Canceladas no TMP",
            example = "1",
            type = "number"
    )
    private Long tmpCanceled;

    @Parsed(field = "Qtd. Credenciada Online")
    @Schema(
            description = "Numero de Propostas no Cred Online",
            example = "1",
            type = "number"
    )
    private Long credOnline;

}
