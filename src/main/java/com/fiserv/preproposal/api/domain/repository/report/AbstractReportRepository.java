package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;

public abstract class AbstractReportRepository<T> implements IWriteReportRepository {

    /**
     *
     */
    private final Normalizer<T> normalizer = new Normalizer<>();

    /**
     * @param stream   Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param file     File who will written
     * @param fields   Fields to write in file
     * @param consumer TODO must be complemented
     */
    @Transactional
    public void convertToCSV(@NonNull final Stream<T> stream, final File file, final Collection<String> fields, final Consumer<T> consumer) {

        final CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.getFormat().setLineSeparator("\r\n");
        writerSettings.getFormat().setDelimiter(';');
        writerSettings.setQuoteAllFields(true);
        writerSettings.setColumnReorderingEnabled(true);
        writerSettings.setHeaderWritingEnabled(true);
        writerSettings.setHeaders(toArray(fields));
        writerSettings.excludeFields(extractFieldsToIgnore(toArray(fields)));

        writerSettings.setRowWriterProcessor(configProcessor());

        final CsvWriter csvWriter = new CsvWriter(file, writerSettings);

        stream.forEach(object -> {

            // Writing in file
            csvWriter.processRecord(normalizer.normalize(object));

            //
            consumer.accept(object);
        });

        csvWriter.close();

    }


}
