package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractReportRepository<T> implements IReadReportRepository, IWriteReportRepository {

    /**
     *
     */
    public final static String DATETIME_PATTERN = "ddMMyyyyHHmmss";

    /**
     *
     */
    @Setter
    @Getter
    @Value("${io.output}")
    private String path;

    /**
     * @param objects  Stream<T>
     * @param beanType Class<T>
     * @param fields   Collection<String>
     * @return byte[]
     */
    public byte[] convertToCSV(@NonNull final Stream<T> objects, final Class<T> beanType, final Collection<String> fields) {

        final File file = new File(this.getPath()); //TODO

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

        final Normalizer<T> normalizer = new Normalizer<>();
        objects.forEach(object -> csvWriter.processRecord(normalizer.normalize(object)));

        csvWriter.close();

        try {
            return Files.readAllBytes(file.toPath());
        } catch (final IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
        return this.extractFieldsFromReport(beanType).stream().filter(field -> fields.stream().noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet());
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
