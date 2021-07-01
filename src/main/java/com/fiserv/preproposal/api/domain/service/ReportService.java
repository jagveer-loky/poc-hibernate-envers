package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.infrastrucutre.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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

    public List<DReport1> getReport1(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Collection<String> status) {
        return proposalRepository.getReport1(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    public List<DReport2> getReport2(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Collection<String> status) {
        return proposalRepository.getReport2(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
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
     * @param status          String[]
     * @return byte[]
     */
    public byte[] getCSVReport1(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Set<String> status) {
        final List<DReport1> list = proposalRepository.getReport1(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
        return new IOService<DReport1>().writeInMemory(list.stream(), layout, DReport1.NAME);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param status          String[]
     * @return byte[]
     */
    public byte[] getCSVReport2(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Set<String> status) {
        final List<DReport2> list = proposalRepository.getReport2(institution, serviceContract, initialDate, finalDate, (Objects.nonNull(status) && status.isEmpty()) ? null : status);
        return new IOService<DReport2>().writeInMemory(list.stream(), layout, DReport2.NAME);
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
