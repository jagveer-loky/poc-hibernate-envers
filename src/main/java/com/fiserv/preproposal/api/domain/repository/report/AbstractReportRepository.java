package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import com.fiserv.preproposal.api.domain.repository.ReportRepository;
import com.fiserv.preproposal.api.domain.service.Test;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;

public abstract class AbstractReportRepository<T> implements IWriteReportRepository {

    /**
     *
     */
    private final Normalizer<T> normalizer = new Normalizer<>();

//    /**
//     *
//     */
//    @Autowired
//    public ReportRepository reportRepository;



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

        final AtomicInteger lines = new AtomicInteger(); //TODO
        stream.forEach(object -> {
            lines.set(lines.get() + 1);
            eReport.setCurrentLine(lines.get());
            csvWriter.processRecord(normalizer.normalize(object));
            consumer.accept(eReport);
        });

        csvWriter.close();

    }



}
