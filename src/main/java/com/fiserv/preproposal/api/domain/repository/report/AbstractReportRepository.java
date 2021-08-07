package com.fiserv.preproposal.api.domain.repository.report;

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

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil.toArray;

@Slf4j
public abstract class AbstractReportRepository<T> implements IWriteReportRepository {

    /**
     *
     */
    public final static String DATETIME_PATTERN = "ddMMyyyyHHmmss";

    /**
     *
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
    @Value("${io.output}")
    private String path;

    /**
     *
     */
    private String absolutePath;

    /**
     * @param jobParams JobParams
     */
    @Override
    public void create(final JobParams jobParams) {

        // Config and set path of the file
        absolutePath = (path + "/" + jobParams.getRequester() + "/" + jobParams.getType() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_PATTERN))).toLowerCase(); // TODO MASTIGAÇÃO
        log.info(String.format("Generating basic report to requester '%s' in the '%s' file", jobParams.getRequester(), absolutePath));

        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setPath(absolutePath);
        eReport.setType(jobParams.getType());
        eReport.setRequester(jobParams.getRequester());
        eReport.setCountLines(0);
        eReport.setRequestedDate(LocalDateTime.now()); //TODO remover
        reportRepository.save(eReport);

        runAsync(reportRepository.save(eReport), jobParams);
    }

    /**
     * @param objects   Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param eReport   EReport to saving the progress of the writing file
     * @param jobParams JobParams to extract bean type (type report) and fields to write in file
     * @return byte[]
     */
    public File convertToCSV(@NonNull final Stream<T> objects, final EReport eReport, final JobParams jobParams) {

        final File file = new File(absolutePath);

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

}
