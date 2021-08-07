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
     * @param jobParams JobParams
     */
    @Override
    public void create(final JobParams jobParams) {
        runAsync(jobParams);
    }

    /**
     * @param objects   Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param eReport   EReport to saving the progress of the writing file
     * @param jobParams JobParams to extract bean type (type report) and fields to write in file
     * @return byte[]
     */
    public File convertToCSV(@NonNull final Stream<T> objects, final EReport eReport, final JobParams jobParams) {

        final File file = new File(this.getFullPath());

        final CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.getFormat().setLineSeparator("\r\n");
        writerSettings.getFormat().setDelimiter(';');
        writerSettings.setQuoteAllFields(true);
        writerSettings.setColumnReorderingEnabled(true);
        writerSettings.setHeaderWritingEnabled(true);
        writerSettings.setHeaders(toList(jobParams.getFields()));
        final Collection<String> fieldsToIgnore = extractFieldsToIgnore(jobParams.getBeanType(), jobParams.getFields()); //TODO vai dar pau
        writerSettings.excludeFields(toList(fieldsToIgnore));

        final BeanWriterProcessor<T> processor = new BeanWriterProcessor((jobParams.getBeanType())); //TODO vai dar pau
        writerSettings.setRowWriterProcessor(processor);

        final CsvWriter csvWriter = new CsvWriter(file, writerSettings);

        final AtomicInteger lines = new AtomicInteger(); //TODO
        objects.forEach(object -> {
            lines.set(lines.get() + 1);
            eReport.setCurrentLine(lines.get());

            csvWriter.processRecord(normalizer.normalize(object));

            saveAsync(eReport);

        });

        csvWriter.close();

        return file;
    }

    /**
     * @param eReport EReport
     */
    public void saveAsync(final EReport eReport) {
        BackgroundJob.enqueue(() -> saveAsync(String.valueOf(eReport.getCurrentLine()), eReport));
    }

    /**
     * @param jobName String
     * @param eReport EReport
     */
    @Job(name = "Saving in the database %0")
    public void saveAsync(final String jobName, final EReport eReport) {
        log.info("Saving in the database " + jobName);
        if (test.getCurrentLine() < eReport.getCurrentLine() || eReport.getCurrentLine() == eReport.getCountLines()) {
            test.setCurrentLine(eReport.getCurrentLine());
            eReport.calculatePercentage();
            this.reportRepository.save(eReport);
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
