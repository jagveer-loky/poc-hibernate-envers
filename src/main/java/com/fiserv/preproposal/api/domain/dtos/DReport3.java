package com.fiserv.preproposal.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DReport3 {

    public static final String NAME = "report3";

    private String filename;
    private String institution;
    private Integer serviceContract;
    private String userId;
    private String agentCpfCnpj;
    private Long preproposalId;
    private String proposalNumber;
    private String merchant;
    private String processingDate;
    private String error;
    private String errorDescription;
    private String errorMessage;
    private String detail;

}
