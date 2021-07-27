package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.infrastrucutre.io.IOService;
import com.univocity.parsers.annotations.Parsed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsService {

    /**
     *
     */
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
     * @return List<CompleteReport>
     */
    public List<CompleteReport> getCompleteReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCompleteReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
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
    public byte[] getBasicCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status, final Collection<String> fieldsToIgnore) {
        final List<BasicReport> list = this.getBasicReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<BasicReport>().convertToCSV(list.stream(), BasicReport.class, fieldsToIgnore);
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
    public byte[] getQuantitativeCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status, final Collection<String> fieldsToIgnore) {
        final List<QuantitativeReport> list = getQuantitativeReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<QuantitativeReport>().convertToCSV(list.stream(), QuantitativeReport.class, fieldsToIgnore);
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
    public byte[] getCompleteCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status, final Set<String> fieldsToIgnore) {
        final List<CompleteReport> list = getCompleteReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<CompleteReport>().convertToCSV(list.stream(), CompleteReport.class, fieldsToIgnore);
    }

    /**
     * @return Set<String>
     */
    public Set<String> extractFieldsFromBasicReport() {
        final Set<String> fields = new HashSet<>();
        try {
            for (final String attribute : getAttributesFromClass(BasicReport.class)) {
                final Parsed annotation = BasicReport.class.getDeclaredField(attribute).getAnnotation(Parsed.class);
                fields.add(annotation.field()[0]);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return fields;
    }

    /**
     * @return Set<String>
     */
    public Set<String> extractFieldsFromCompleteReport() {
        final Set<String> fields = new HashSet<>();
        try {
            for (final String attribute : getAttributesFromClass(CompleteReport.class)) {
                final Parsed annotation = CompleteReport.class.getDeclaredField(attribute).getAnnotation(Parsed.class);
                fields.add(annotation.field()[0]);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return fields;
    }

    /**
     * @return Set<String>
     */
    public Set<String> extractFieldsFromQuantitativeReport() {
        final Set<String> fields = new HashSet<>();
        try {
            for (final String attribute : getAttributesFromClass(QuantitativeReport.class)) {
                final Parsed annotation = QuantitativeReport.class.getDeclaredField(attribute).getAnnotation(Parsed.class);
                fields.add(annotation.field()[0]);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return fields;
    }

    /**
     * Extract the attributes from class
     *
     * @return Set<String>
     */
    public Set<String> getAttributesFromClass(final Class<?> clazz) {

        return Arrays.stream(clazz.getDeclaredMethods())
                .map(Method::getName)
                .filter(method -> method.contains("get") || method.contains("set"))
                .map(method -> {
                    final String attribute = method.replace("get", "").replace("set", "");
                    return attribute.substring(0, 1).toLowerCase() + attribute.substring(1);
                }).collect(Collectors.toSet());
    }

    /**
     * @param report String
     * @return Set<String>
     */
    public Set<String> getFieldsFromReport(final String report) {
        switch (report) {
            case BasicReport.NAME:
                return extractFieldsFromBasicReport();
            case CompleteReport.NAME:
                return extractFieldsFromCompleteReport();
            default:
                return extractFieldsFromQuantitativeReport();
        }
    }

    /**
     * TODO construct test
     * @return Set<String>
     */
    public HashMap<String, Set<String>> getFieldsFromReports() {
        final HashMap<String, Set<String>> fieldsFromReports = new HashMap<>();
        fieldsFromReports.put(BasicReport.NAME, getFieldsFromReport(BasicReport.NAME));
        fieldsFromReports.put(CompleteReport.NAME, getFieldsFromReport(CompleteReport.NAME));
        fieldsFromReports.put(QuantitativeReport.NAME, getFieldsFromReport(QuantitativeReport.NAME));
        return fieldsFromReports;
    }
}
