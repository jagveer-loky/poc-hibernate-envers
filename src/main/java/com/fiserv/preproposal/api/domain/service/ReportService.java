package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.infrastrucutre.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Value("${layout}")
    private String layout;

    /**
     *
     */
    private final ProposalRepository proposalRepository;

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<BasicReport>
     */
    public List<BasicReport> getBasicReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getBasicReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<QuantitativeReport>
     */
    public List<QuantitativeReport> getQuantitativeReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getQuantitativeReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<ErrorsReport>
     */
    public List<ErrorsReport> getErrorsReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getErrorsReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<ProposalDataReport>
     */
    public List<ProposalDataReport> getProposalDataReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getProposalDataReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return List<CompleteProposalDataReport>
     */
    public List<CompleteProposalDataReport> getCompleteProposalDataReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCompleteProposalDataReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return byte[]
     */
    public byte[] getBasicCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        final List<BasicReport> list = this.getBasicReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<BasicReport>().writeInMemory(list.stream(), BasicReport.HEADER_NAME, layout, BasicReport.NAME);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return byte[]
     */
    public byte[] getQuantitativeCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status) {
        final List<QuantitativeReport> list = getQuantitativeReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<QuantitativeReport>().writeInMemory(list.stream(), QuantitativeReport.HEADER_NAME, layout, QuantitativeReport.NAME);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return byte[]
     */
    public byte[] getErrorsCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status) {
        final List<ErrorsReport> list = getErrorsReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<ErrorsReport>().writeInMemory(list.stream(), ErrorsReport.HEADER_NAME, layout, ErrorsReport.NAME);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return byte[]
     */
    public byte[] getCSVProposalDataReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status) {
        final List<ProposalDataReport> list = getProposalDataReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<ProposalDataReport>().writeInMemory(list.stream(), ProposalDataReport.HEADER_NAME, layout, ProposalDataReport.NAME);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return byte[]
     */
    public byte[] getCSVCompleteProposalDataReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status) {
        final List<CompleteProposalDataReport> list = getCompleteProposalDataReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<CompleteProposalDataReport>().writeInMemory(list.stream(), ProposalDataReport.HEADER_NAME, layout, CompleteProposalDataReport.NAME);
    }

}
