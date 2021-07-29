package com.fiserv.preproposal.api.infrastrucutre.io;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IOService<T> {


    /**
     * Return with all fields
     *
     * @param objects  Stream<T>
     * @param beanType Class<T>
     * @return byte[]
     */
    public byte[] convertToCSV(@NonNull final Stream<T> objects, final Class<T> beanType) {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.getFormat().setLineSeparator("\r\n");
        writerSettings.getFormat().setDelimiter(';');
        writerSettings.setQuoteAllFields(true);
        writerSettings.setColumnReorderingEnabled(true);
        writerSettings.setHeaderWritingEnabled(true);

        final BeanWriterProcessor<T> processor = new BeanWriterProcessor<>(beanType);
        writerSettings.setRowWriterProcessor(processor);

        final CsvWriter csvWriter = new CsvWriter(outputStream, writerSettings);

        objects.forEach(csvWriter::processRecord);

        csvWriter.close();

        return outputStream.toByteArray();
    }

    /**
     * @param objects  Stream<T>
     * @param beanType Class<T>
     * @param fields   Collection<String>
     * @return byte[]
     */
    public byte[] convertToCSV(@NonNull final Stream<T> objects, final Class<T> beanType, final Collection<String> fields) {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

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

        final CsvWriter csvWriter = new CsvWriter(outputStream, writerSettings);

        objects.forEach(csvWriter::processRecord);

        csvWriter.close();

        return outputStream.toByteArray();
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
