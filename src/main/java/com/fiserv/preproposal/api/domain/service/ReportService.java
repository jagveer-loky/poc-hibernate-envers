package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.report.impl.BasicReportRepository;
import com.fiserv.preproposal.api.infrastrucutre.io.IOService;
import com.univocity.parsers.annotations.Parsed;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Autowired
    BasicReportRepository basicReportRepository;

    private final IOService<BasicReport> ioServiceBasicReport = new IOService<>();

    private final IOService<CompleteReport> ioServiceCompleteReport = new IOService<>();

    private final IOService<QuantitativeReport> ioServiceQuantitativeReport = new IOService<>();

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
     * @return Stream<BasicReport>
     */
    @Transactional(readOnly = true)
    public Stream<BasicReport> getBasicReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
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
     * @return int
     */
    public int getCountBasicReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCountBasicReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return Stream<QuantitativeReport>
     */
    public Stream<QuantitativeReport> getQuantitativeReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
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
     * @return int
     */
    public int getCountQuantitativeReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCountQuantitativeReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @return Stream<CompleteReport>
     */
    public Stream<CompleteReport> getCompleteReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
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
     * @return int
     */
    public int getCountCompleteReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status) {
        return proposalRepository.getCountCompleteReport(institution, serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @param fields          Collection<String>
     */
    public void createBasicCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status, final Collection<String> fields) {
        basicReportRepository.create("usuarioTal", institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status, fields);
    }

    public void getBasicCSVReport(final String path) {
        basicReportRepository.read(path);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @param fields          Collection<String>
     * @return byte[]
     */
    public byte[] getQuantitativeCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status, final Collection<String> fields) {
        final Stream<QuantitativeReport> stream = getQuantitativeReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<QuantitativeReport>().convertToCSV(stream, QuantitativeReport.class, fields);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @param fields          Collection<String>
     * @return byte[]
     */
    public byte[] getCompleteCSVReport(final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Set<String> status, final Set<String> fields) {
        final Stream<CompleteReport> stream = getCompleteReport(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status);
        return new IOService<CompleteReport>().convertToCSV(stream, CompleteReport.class, fields);
    }

    /**
     * @param jobOwner  String
     * @param jobParams JobParams
     */
    @Job(name = "getAsyncQuantitativeCSVReport %0", retries = 2)
    @Transactional(readOnly = true)
    public void getAsyncQuantitativeCSVReport(final String jobOwner, final JobParams jobParams) {
        final Stream<QuantitativeReport> stream = this.getQuantitativeReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());
        this.ioServiceQuantitativeReport.convertToCSV(stream, QuantitativeReport.class, jobParams.getFields());
    }

    /**
     * @param jobOwner  String
     * @param jobParams JobParams
     */
    @Job(name = "getAsyncQuantitativeCSVReport %0", retries = 2)
    @Transactional(readOnly = true)
    public void getAsyncCompleteCSVReport(final String jobOwner, final JobParams jobParams) {
        final Stream<CompleteReport> stream = this.getCompleteReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());
        this.ioServiceCompleteReport.convertToCSV(stream, CompleteReport.class, jobParams.getFields());
    }

    /**
     * @return Set<String>
     */
    public Set<String> extractFieldsFromBasicReport() {
        final Set<String> fields = new HashSet<>();
        try {
            for (final String attribute : IOService.getAttributesFromClass(BasicReport.class)) {
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
            for (final String attribute : IOService.getAttributesFromClass(CompleteReport.class)) {
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
            for (final String attribute : IOService.getAttributesFromClass(QuantitativeReport.class)) {
                final Parsed annotation = QuantitativeReport.class.getDeclaredField(attribute).getAnnotation(Parsed.class);
                fields.add(annotation.field()[0]);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return fields;
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
     *
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
