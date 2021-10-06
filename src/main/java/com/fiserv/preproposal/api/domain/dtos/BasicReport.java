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
public class BasicReport extends AbstractReport implements Serializable {

    @Index(14)
    @Parsed(field = "Id da Proposta")
    @Schema(
            description = "Id da Proposta",
            example = "985641232457887845L",
            type = "String"
    )
    private String id;

    @Index(1)
    @Parsed(field = "Numero da Pre-proposta")
    @Schema(
            description = "Numero da Proposta",
            example = "O00000001965857",
            type = "String"
    )
    private String proposalNumber;

    @Index(2)
    @Parsed(field = "CPF/CNPJ")
    @Schema(
            description = "CPF/CNPJ da Proposta",
            example = "10167584057",
            type = "String"
    )
    private String cpfCnpj;

    @Index(3)
    @Parsed(field = "Nome Fantasia")
    @Schema(
            description = "Nome Fantasia",
            example = "Arasaka",
            type = "String"
    )
    private String fantasyName;

    @Index(4)
    @Parsed(field = "Razao Social")
    @Schema(
            description = "Razao Social",
            example = "Arasaka Corporation",
            type = "String"
    )
    private String socialReason;

    @Index(5)
    @Parsed(field = "Nome Plaqueta (Comprovante)")
    @Schema(
            description = "Nome Plaqueta (Comprovante)",
            example = "Arasaka Corp",
            type = "String"
    )
    private String voucherName;

    @Index(23)
    @Parsed(field = "Tipo da resposta")
    @Schema(
            description = "Tipo da resposta",
            example = "FISERV_ONLINE",
            type = "String"
    )
    private String responseType;

    @Index(6)
    @Parsed(field = "ID do Usuario")
    @Schema(
            description = "ID do Usuario",
            example = "CEFP3333",
            type = "String"
    )
    private String userId;

    @Index(7)
    @Parsed(field = "CPF/CNPJ do Agente")
    @Schema(
            description = "CPF/CNPJ do Agente",
            example = "73478568099",
            type = "String"
    )
    private String agentCpfCnpj;

    @Index(8)
    @Parsed(field = "Instituicao")
    @Schema(
            description = "Instituicao",
            example = "00000007",
            type = "String"
    )
    private String institution;

    @Index(9)
    @Parsed(field = "SERVICE_CONTRACT")
    @Schema(
            description = "Service Contract",
            example = "149",
            type = "String"
    )
    private String serviceContract;

    @Index(17)
    @Parsed(field = "Opt In")
    @Schema(
            description = "Opt In",
            example = "SIM/NAO",
            type = "String"
    )
    private String optIn;

    @Index(16)
    @Parsed(field = "Matricula do Vendedor")
    @Schema(
            description = "Matricula do Vendedor",
            example = "C654321",
            type = "String"
    )
    private String sellerRegistration;

    @Index(10)
    @Parsed(field = "Sub Canal")
    @Schema(
            description = "Sub Canal",
            example = "00647",
            type = "String"
    )
    private String subChannel;

    @Index(11)
    @Parsed(field = "Tecnologia")
    @Schema(
            description = "Tecnologia",
            example = "18-POS GPRS WIFI",
            type = "String"
    )
    private String tecnology;

    @Index(12)
    @Parsed(field = "Inclusao em")
    @Schema(
            description = "Data de Inclusao",
            example = "03/06/2021 12:06",
            type = "String"
    )
    private String includeDate;

    @Index(13)
    @Parsed(field = "Conclusao em")
    @Schema(
            description = "Data de Conclusao",
            example = "03/06/2021 12:06",
            type = "String"
    )
    private String conclusionDate;

    @Index(15)
    @Parsed(field = "Merchant ID")
    @Schema(
            description = "Merchant ID",
            example = "O01965857",
            type = "String"
    )
    private String merchantId;

    @Index(18)
    @Parsed(field = "Status - FISERV")
    @Schema(
            description = "Fiserv Status",
            example = "PRE1-Pendente análise pre proposta",
            type = "String"
    )
    private String fiservStatus;

    @Index(19)
    @Parsed(field = "Status - CAIXA")
    @Schema(
            description = "Caixa Status",
            example = "1-Efetivada",
            type = "String"
    )
    private String cefStatus;

    @Index(20)
    @Parsed(field = "Mensagem Caixa")
    @Schema(
            description = "Caixa Mensagem",
            example = "Concluido",
            type = "String"
    )
    private String cefMessage;

    @Index(22)
    @Parsed(field = "Erros de processamento dos bulks")
    @Schema(
            description = "Erros de processamento dos bulks",
            example = "Errors",
            type = "String"
    )
    private String errors;

    @Index(21)
    @Parsed(field = "Submissao ao online em")
    @Schema(
            description = "Data de Submissao",
            example = "28/06/2021 12:06",
            type = "String"
    )
    private String submissionDate;

    @Index(25)
    @Parsed(field = "Campo de erro")
    @Schema(
            description = "Campo de erro",
            type = "String"
    )
    private String errorField;

    @Index(26)
    @Parsed(field = "Descricao do erro")
    @Schema(
            description = "Descricao do erro",
            type = "String"
    )
    private String errorDescription;

    @Index(27)
    @Parsed(field = "Mensagem de erro")
    @Schema(
            description = "Mensagem de erro",
            type = "String"
    )
    private String errorMessage;

    @Index(28)
    @Parsed(field = "Detalhe")
    @Schema(
            description = "Detalhe",
            type = "String"
    )
    private String detail;

    @Index(29)
    @Parsed(field = "Etapa da proposta")
    @Schema(
            description = "Etapa da proposta",
            type = "String"
    )
    private String step;

    @Index(30)
    @Parsed(field = "Informacoes adicionais")
    @Schema(
            description = "Informacoes adicionais",
            type = "String"
    )
    private String moreInformation;

    @Index(31)
    @Parsed(field = "Resposta FO")
    @Schema(
            description = "Resposta FO",
            type = "String"
    )
    private String fiservOnlineResponse;

}
