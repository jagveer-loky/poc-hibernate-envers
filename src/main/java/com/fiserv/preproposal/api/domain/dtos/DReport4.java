package com.fiserv.preproposal.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DReport4 {

    public static final String NAME = "report4";

    private Long preproposalId;
    private String proposalNumber;
    private String merchant;
    private String userId;
    private String agentCpfCnpj;
    private String institution;
    private Integer serviceContract;
    private String subChannel;
    private String tecnology;
    private String fiservStatus;
    private String caixaStatus;
    private String caixaMessage;
    private String inclusionDate;
    private String conclusionDate1;
    private String onlineSubmitionDate;
    private String persionType;
    private String cpfCnpj;
    private String fantasyName;
    private String socialReason;
    private String vouncherName;
    private String montlyBilling;
    private String birthDate;
    private String gnere;
    private String treatmentPronoun;
    private String birthPlace;
    private String nacionality;
    private String fullName;
    private String municipalRegistration;
    private String stateRegistration;
    private String constitutionForm;
    private String openDate;
    private String anualBillingVol;
    private String anualVolCash;
    private String anualVolSalesCard;
    private String anualVolSalesCardGroup;
    private String averageTicket;
    private String bacenPermission;
    private String campaingCode;
    private String cnae;
    private String eCommerce;
    private String foreignCard;
    private String manualPrepaymentEnabled;
    private String boardingBranching;
    private String percCardNotPresent;
    private String percCardPresent;
    private String percInternet;
    private String prepaymentIndicator;
    private String recurringTransaction;
    private String serviceDay0;
    private String serviceDay1530;
    private String serviceDay17;
    private String serviceDay814;
    private String serviceDayOver30;
    private String pendingBwDate;
    private String conclusionDate;

}
