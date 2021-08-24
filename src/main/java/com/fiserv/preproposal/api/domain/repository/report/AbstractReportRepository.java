package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;

public abstract class AbstractReportRepository<T> implements IWriteReportRepository<T> {

    /**
     *
     */
    private final Normalizer<T> normalizer = new Normalizer<>();

    /**
     * @param stream               Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param file                 File who will written
     * @param fields               Fields to write in file
     * @param consumer             Consumer<T>
     * @param lineErrorConsumer    Consumer<Exception>
     * @param generalErrorConsumer Consumer<Exception>
     */
    @Transactional
    public void convertToCSV(@NonNull final Stream<T> stream, final File file, final Collection<String> fields, final Consumer<T> consumer, final Consumer<Exception> lineErrorConsumer, final Consumer<Exception> generalErrorConsumer) {

        try {

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

                try {
                    // Writing in file
                    csvWriter.processRecord(normalizer.normalize(object));

                    //
                    consumer.accept(object);
                } catch (final Exception e) {
                    lineErrorConsumer.accept(e);
                }

            });

            csvWriter.close();

        } catch (final Exception e) {
            generalErrorConsumer.accept(e);
        }

    }


}
