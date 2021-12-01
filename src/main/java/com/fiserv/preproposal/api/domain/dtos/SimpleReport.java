package com.fiserv.preproposal.api.domain.dtos;

import com.fiserv.preproposal.api.application.annotation.Index;
import com.univocity.parsers.annotations.Parsed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@SqlResultSetMappings(value = {
        @SqlResultSetMapping(
                name = "simpleReportMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = SimpleReport.class,
                                columns = {
                                        @ColumnResult(name = "PREPROPOSALID", type = String.class),
                                        @ColumnResult(name = "PROPOSALNUMBER", type = Long.class),
                                        @ColumnResult(name = "MERCHANT", type = String.class),
                                        @ColumnResult(name = "USERID", type = String.class),
                                        @ColumnResult(name = "AGENTCPFCNPJ", type = String.class),
                                        @ColumnResult(name = "INSTITUTION", type = String.class),
                                        @ColumnResult(name = "SERVICECONTRACT", type = Integer.class)
                                }
                        )
                }
        )
}
)

@NamedNativeQuery(
        name = "simpleNamedNativeQuery",
        query =  "SELECT  " +
                "       tpd.PROPOSAL_NUMBER AS \"PREPROPOSALID\"," +
                "       tpd.id AS \"PROPOSALNUMBER\"," +
                "       tpd.merchant_id AS \"MERCHANT\"," +
                "       tpd.AGENT_CHANNEL AS \"USERID\", " +
                "       tpd.AGENT_CPF_CNPJ AS \"AGENTCPFCNPJ\"," +
                "       tfc.institution AS \"INSTITUTION\"," +
                "       tfc.service_contract AS \"SERVICECONTRACT\"" +
                "       FROM TB_PROPOSAL_DATA tpd" +
                "  LEFT join TB_FILE_CONTROL tfc ON tfc.ID = tpd.id_file_control" +
                "  WHERE tfc.INSTITUTION = :institution AND tfc.SERVICE_CONTRACT = :serviceContract ",
        resultSetMapping = "simpleReportMapping")

//@NamedNativeQuery(
//        name = "simpleNamedNativeQuery.count",
//        query = "SELECT COUNT(TB_PROPOSAL_DATA.ID) " +
//                "       FROM TB_PROPOSAL_DATA" +
//                "  LEFT join TB_FILE_CONTROL TFC ON TFC.ID = TB_PROPOSAL_DATA.id_file_control" +
//                "  WHERE tfc.INSTITUTION = :institution AND tfc.SERVICE_CONTRACT = :serviceContract ",
//resultClass = Long.class)

@Entity
@Table(name = "TB_PROPOSAL_DATA")
@Getter
@Setter
public class SimpleReport extends AbstractReport implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

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

    public SimpleReport() {
    }

    public SimpleReport(Long id) {
        this.id = id;
    }

    public SimpleReport(Long id, String preproposalId, Long proposalNumber, String merchant, String userId, String agentCpfCnpj, String institution, Integer serviceContract) {
        this.id = id;
        this.preproposalId = preproposalId;
        this.proposalNumber = proposalNumber;
        this.merchant = merchant;
        this.userId = userId;
        this.agentCpfCnpj = agentCpfCnpj;
        this.institution = institution;
        this.serviceContract = serviceContract;
    }

    public SimpleReport(String preproposalId, Long proposalNumber, String merchant, String userId, String agentCpfCnpj, String institution, Integer serviceContract) {
        this.preproposalId = preproposalId;
        this.proposalNumber = proposalNumber;
        this.merchant = merchant;
        this.userId = userId;
        this.agentCpfCnpj = agentCpfCnpj;
        this.institution = institution;
        this.serviceContract = serviceContract;
    }
}
