package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.ReportRepository;
import com.fiserv.preproposal.api.domain.repository.report.impl.BasicReportRepository;
import com.fiserv.preproposal.api.domain.repository.report.impl.CompleteReportRepository;
import com.fiserv.preproposal.api.domain.repository.report.impl.QuantitativeReportRepository;
import com.fiserv.preproposal.api.infrastrucutre.io.IOService;
import com.univocity.parsers.annotations.Parsed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    /**
     *
     */
    public final static String DATETIME_PATTERN = "ddMMyyyyHHmmss";

    /**
     *
     */
    @Value("${io.output}")
    private String path;

    /**
     *
     */
    private String absolutePath;

    /**
     *
     */
    private final ReportRepository reportRepository;

    /**
     *
     */
    private final ProposalRepository proposalRepository;

    /**
     *
     */
    private final BasicReportRepository basicReportRepository;

    /**
     *
     */
    private final CompleteReportRepository completeReportRepository;

    /**
     *
     */
    private final QuantitativeReportRepository quantitativeReportRepository;

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
//    @Transactional(readOnly = true)
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

    public EReport createEReportFromJobParams(final JobParams jobParams) {

        // Config and set path of the file
        final String absolutePath = (path + "/" + jobParams.getRequester() + "/" + jobParams.getType() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_PATTERN))).toLowerCase(); // TODO MASTIGAÇÃO

        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setPath(absolutePath);
        eReport.setType(jobParams.getType());
        eReport.setRequester(jobParams.getRequester());
        eReport.setCountLines(0);

        return eReport;
    }

    @Transactional
    public EReport save(final EReport eReport) {
        return this.reportRepository.save(eReport);
    }

    int teste = 0;

    /**
     * @param eReport EReport
     */
    @Job(name = "Saving in the database")
    public void saveAsync(final EReport eReport) {

        if (teste < eReport.getCurrentLine() || eReport.getCurrentLine() == eReport.getCountLines()) {
            teste = eReport.getCurrentLine();
            eReport.calculatePercentage();
            save(eReport);
        }
    }

    @Transactional
    @Job(name = "Generating basic report", retries = 2)
    public void startBasicReport(final JobParams jobParams, final EReport eReport) {
        eReport.setCountLines(proposalRepository.getCountBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus()));
        final Stream<BasicReport> basicStream = proposalRepository.getBasicReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());
//        basicReportRepository.convertToCSV(basicStream, eReport, jobParams, e -> BackgroundJob.enqueue(() -> saveAsync(e)));
        basicReportRepository.convertToCSV(basicStream, eReport, jobParams, eRport -> {
            eReport.setCurrentLine(eReport.getCurrentLine() + 1);
            BackgroundJob.enqueue(() -> saveAsync(eRport));
        });
    }

    @Transactional
    @Job(name = "Generating complete report", retries = 2)
    public void startCompleteReport(final JobParams jobParams, final EReport eReport) {
        eReport.setCountLines(proposalRepository.getCountCompleteReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus()));
        final Stream<CompleteReport> completeStream = proposalRepository.getCompleteReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());
        completeReportRepository.convertToCSV(completeStream, eReport, jobParams, e -> BackgroundJob.enqueue(() -> saveAsync(e)));

    }

    @Transactional
    @Job(name = "Generating quantitative report", retries = 2)
    public void startQuantitativeReport(final JobParams jobParams, final EReport eReport) {
        eReport.setCountLines(proposalRepository.getCountCompleteReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus()));
        final Stream<QuantitativeReport> quantitativeStream = proposalRepository.getQuantitativeReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());
        quantitativeReportRepository.convertToCSV(quantitativeStream, eReport, jobParams, e -> BackgroundJob.enqueue(() -> saveAsync(e)));
    }

    public byte[] downloadById(final Long id) throws NotFound, IOException {
        final EReport eReport = reportRepository.findById(id).orElseThrow(NotFound::new);
        return Files.readAllBytes(new File(eReport.getPath()).toPath());
    }

    public List<EReport> findByRequester(final String requester) {
        return this.reportRepository.findByRequester(requester);
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

//    /**
//     * @param jobOwner  String
//     * @param jobParams JobParams
//     */
//    @Job(name = "getAsyncQuantitativeCSVReport %0", retries = 2)
////    @Transactional(readOnly = true)
//    public void getAsyncQuantitativeCSVReport(final String jobOwner, final JobParams jobParams) {
//        final Stream<QuantitativeReport> stream = this.getQuantitativeReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());
//        this.ioServiceQuantitativeReport.convertToCSV(stream, QuantitativeReport.class, jobParams.getFields());
//    }
//
//    /**
//     * @param jobOwner  String
//     * @param jobParams JobParams
//     */
//    @Job(name = "getAsyncQuantitativeCSVReport %0", retries = 2)
////    @Transactional(readOnly = true)
//    public void getAsyncCompleteCSVReport(final String jobOwner, final JobParams jobParams) {
//        final Stream<CompleteReport> stream = this.getCompleteReport(jobParams.getInstitution(), jobParams.getServiceContract(), jobParams.getInitialDate(), jobParams.getFinalDate(), jobParams.getNotIn(), jobParams.getResponsesTypes(), jobParams.getStatus());
//        this.ioServiceCompleteReport.convertToCSV(stream, CompleteReport.class, jobParams.getFields());
//    }

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
            case TypeReport.BASIC_VALUE:
                return extractFieldsFromBasicReport();
            case TypeReport.COMPLETE_VALUE:
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
        fieldsFromReports.put(TypeReport.BASIC_VALUE, getFieldsFromReport(TypeReport.BASIC_VALUE));
        fieldsFromReports.put(TypeReport.COMPLETE_VALUE, getFieldsFromReport(TypeReport.COMPLETE_VALUE));
        fieldsFromReports.put(TypeReport.QUANTITATIVE_VALUE, getFieldsFromReport(TypeReport.QUANTITATIVE_VALUE));
        return fieldsFromReports;
    }
}
