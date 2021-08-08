package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;

public abstract class AbstractReportRepository<T> implements IWriteReportRepository {

    /**
     *
     */
    private final Normalizer<T> normalizer = new Normalizer<>();

    /**
     * @param stream    Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param eReport   EReport to saving the progress of the writing file
     * @param jobParams JobParams to extract bean type (type report) and fields to write in file
     * @return byte[]
     */
    @Transactional
    public void convertToCSV(@NonNull final Stream<T> stream, final EReport eReport, final JobParams jobParams, final Consumer<EReport> consumer) {

        final File file = new File(eReport.getPath());

        final CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.getFormat().setLineSeparator("\r\n");
        writerSettings.getFormat().setDelimiter(';');
        writerSettings.setQuoteAllFields(true);
        writerSettings.setColumnReorderingEnabled(true);
        writerSettings.setHeaderWritingEnabled(true);
        writerSettings.setHeaders(toArray(jobParams.getFields()));
        writerSettings.excludeFields(extractFieldsToIgnore(jobParams));

        final BeanWriterProcessor<T> processor = new BeanWriterProcessor((jobParams.getBeanType()));
        writerSettings.setRowWriterProcessor(processor);

        final CsvWriter csvWriter = new CsvWriter(file, writerSettings);

        stream.forEach(object -> {
            csvWriter.processRecord(normalizer.normalize(object));
//            eReport.setCurrentLine(eReport.getCurrentLine() + 1);
            consumer.accept(eReport);
        });

        csvWriter.close();

    }



}
