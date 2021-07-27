package com.fiserv.preproposal.api.infrastrucutre.io;

import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.stream.Stream;

@Service
public class IOService<T> {

    /**
     * @param objects        Stream<T>
     * @param beanType       Class<T>
     * @param fieldsToIgnore Collection<String>
     * @return byte[]
     */
    public byte[] convertToCSV(@NonNull final Stream<T> objects, final Class<T> beanType, final Collection<String> fieldsToIgnore) {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.getFormat().setLineSeparator("\r\n");
        writerSettings.getFormat().setDelimiter(';');
        writerSettings.setQuoteAllFields(true);
        writerSettings.setColumnReorderingEnabled(true);
        writerSettings.setHeaderWritingEnabled(true);
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
}
