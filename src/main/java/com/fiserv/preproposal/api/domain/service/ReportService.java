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
     * @param responsesTypes  String[]
     * @param status          String[]
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
     * @param responsesTypes  String[]
     * @param status          String[]
     * @return List<QuantitativeReport>
     */
    public List<QuantitativeReport> getQuantitativeReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getQuantitativeReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    public List<DReport3> getReport3(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Collection<String> status) {
        return proposalRepository.getReport3(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    public List<DReport4> getReport4(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Collection<String> status) {
        return proposalRepository.getReport4(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    public List<DReport5> getReport5(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Collection<String> status) {
        return proposalRepository.getReport5(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param responsesTypes  String[]
     * @param status          String[]
     * @return byte[]
     */
    public byte[] getBasicCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final boolean in, final Collection<String> responsesTypes, final Collection<String> status) {
        final List<BasicReport> list = this.getBasicReport(institution, serviceContract, initialDate, finalDate, in, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
        return new IOService<BasicReport>().writeInMemory(list.stream(), layout, BasicReport.NAME, BasicReport.HEADER_NAME);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param status          String[]
     * @return byte[]
     */
    public byte[] getQuantitativeCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final boolean in, final Collection<String> responsesTypes, final Set<String> status) {
        final List<QuantitativeReport> list = getQuantitativeReport(institution, serviceContract, initialDate, finalDate, in, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.nonNull(status) && status.isEmpty()) ? null : status);
        return new IOService<QuantitativeReport>().writeInMemory(list.stream(), layout, QuantitativeReport.NAME);
    }

    /**
     * @return byte[]
     */
    public byte[] getCSVReport3(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Set<String> status) {
        final List<DReport3> list = proposalRepository.getReport3(institution, serviceContract, initialDate, finalDate, (Objects.nonNull(status) && status.isEmpty()) ? null : status);
        return new IOService<DReport3>().writeInMemory(list.stream(), layout, DReport3.NAME);
    }

    /**
     * @return byte[]
     */
    public byte[] getCSVReport4(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Set<String> status) {
        final List<DReport4> list = proposalRepository.getReport4(institution, serviceContract, initialDate, finalDate, (Objects.nonNull(status) && status.isEmpty()) ? null : status);
        return new IOService<DReport4>().writeInMemory(list.stream(), layout, DReport4.NAME);
    }

    /**
     * @return byte[]
     */
    public byte[] getCSVReport5(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Set<String> status) {
        final List<DReport5> list = proposalRepository.getReport5(institution, serviceContract, initialDate, finalDate, (Objects.nonNull(status) && status.isEmpty()) ? null : status);
        return new IOService<DReport5>().writeInMemory(list.stream(), layout, DReport5.NAME);
    }

}
