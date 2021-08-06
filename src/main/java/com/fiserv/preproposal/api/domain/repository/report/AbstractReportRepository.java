package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.ReportRepository;
import com.fiserv.preproposal.api.domain.service.Test;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public abstract class AbstractReportRepository<T> implements IReadReportRepository, IWriteReportRepository {

    /**
     *
     */
    public final static String DATETIME_PATTERN = "ddMMyyyyHHmmss";

    /*

     */
    private final Normalizer<T> normalizer = new Normalizer<>();

    /**
     *
     */
    @Autowired
    public ReportRepository reportRepository;

    /**
     *
     */
    @Autowired
    public ProposalRepository proposalRepository;

    private final Test test;

    public AbstractReportRepository() {
        test = Test.getInstance();
    }

    /**
     *
     */
    @Setter
    @Getter
    @Value("${io.output}")
    private String path;

    /**
     *
     */
    @Setter
    @Getter
    private String fullPath;

    /**
     * @param path String
     */
    @Override
    public void remove(final String path) {

    }

    /**
     * @param path String
     * @return byte[]
     */
    @Override
    public File read(final String path) {
        return new File(path);
    }

    /**
     * @param ownerJob        String
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param notIn           Boolean
     * @param responsesTypes  Collection<String>
     * @param status          Collection<String>
     * @param fields          Collection<String>
     */
    @Override
    public void create(final String ownerJob, final String institution, final String serviceContract, final LocalDate initialDate, final LocalDate finalDate, final Boolean notIn, final Collection<String> responsesTypes, final Collection<String> status, final Collection<String> fields) {

        // Extract the params to putting to job
        final JobParams jobParams = JobParams.builder().institution(institution).serviceContract(serviceContract).initialDate(initialDate).finalDate(finalDate).notIn(notIn).responsesTypes(responsesTypes).status(status).fields(fields).build();

        run(ownerJob, jobParams);
    }

    /**
     * @param objects  Stream<T>
     * @param beanType Class<T>
     * @param fields   Collection<String>
     * @return byte[]
     */
    public File convertToCSV(@NonNull final Stream<T> objects, final EReport eReport, final Class<T> beanType, final Collection<String> fields) {

        final File file = new File(this.getFullPath());

        final CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.getFormat().setLineSeparator("\r\n");
        writerSettings.getFormat().setDelimiter(';');
        writerSettings.setQuoteAllFields(true);
        writerSettings.setColumnReorderingEnabled(true);
        writerSettings.setHeaderWritingEnabled(true);
        writerSettings.setHeaders(toList(fields));
        final Collection<String> fieldsToIgnore = extractFieldsToIgnore(beanType, fields);
        writerSettings.excludeFields(toList(fieldsToIgnore));

        final BeanWriterProcessor<T> processor = new BeanWriterProcessor<>(beanType);
        writerSettings.setRowWriterProcessor(processor);

        final CsvWriter csvWriter = new CsvWriter(file, writerSettings);

        final AtomicInteger lines = new AtomicInteger(); //TODO
        objects.forEach(object -> {
            lines.set(lines.get() + 1);
            eReport.setCurrentLine(lines.get());

            csvWriter.processRecord(normalizer.normalize(object));

            save(eReport);

        });

        csvWriter.close();

        return file;
    }

    /**
     * @param eReport EReport
     */
    public void save(final EReport eReport) {
        BackgroundJob.enqueue(() -> saveAsync(UUID.randomUUID().toString(), eReport));
    }

    /**
     * @param job     String
     * @param eReport EReport
     */
    @Job(name = "name %0")
    public void saveAsync(final String job /*todo*/, final EReport eReport) {
        if (test.getCurrentLine() < eReport.getCurrentLine() || eReport.getCurrentLine() == eReport.getCountLines()) {
            test.setCurrentLine(eReport.getCurrentLine());
            eReport.calculatePercentage();
            this.reportRepository.saveAndFlush(eReport); //TODO
        }
    }

    /**
     * @param list Collection<String>
     * @return String[]
     */
    private static String[] toList(final Collection<String> list) {
        if (list == null)
            return new String[]{};
        return list.toArray(new String[0]);
    }

    /**
     * TODO construct test
     *
     * @param beanType Class<T>
     * @param fields   Set<String>
     * @return Set<String>
     */
    public Set<String> extractFieldsToIgnore(final Class<T> beanType, final Collection<String> fields) {
        if (fields == null || fields.isEmpty())
            return new HashSet<>();
        return extractFieldsFromReport(beanType).stream().filter(field -> fields.stream().noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet());
    }

    /**
     * @return Set<String>
     */
    public Set<String> extractFieldsFromReport(final Class<T> beanType) {
        final Set<String> fields = new HashSet<>();
        try {
            for (final String attribute : getAttributesFromClass(beanType)) {
                final Parsed annotation = beanType.getDeclaredField(attribute).getAnnotation(Parsed.class);
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
    public static Set<String> getAttributesFromClass(final Class<?> clazz) {

        return Arrays.stream(clazz.getDeclaredMethods())
                .map(Method::getName)
                .filter(method -> method.contains("get") || method.contains("set"))
                .map(method -> {
                    final String attribute = method.replace("get", "").replace("set", "");
                    return attribute.substring(0, 1).toLowerCase() + attribute.substring(1);
                }).collect(Collectors.toSet());
    }


}
