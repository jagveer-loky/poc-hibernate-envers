package com.fiserv.preproposal.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DReport2 {

    public static final String NAME = "report2";

    private String filename;
    private String institution;
    private String processingDate;
    private String fileStatus;
    private Long proposals;
    private Long onlineRejected;
    private Long pendingInFiservOnline;
    private Long pendingComplement;
    private Long preproposalError;
    private Long finished;
    private Long tmpCanceled;
    private Long credOnline;

}
