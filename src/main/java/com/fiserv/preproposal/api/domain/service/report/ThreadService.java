package com.fiserv.preproposal.api.domain.service.report;

import com.fiserv.preproposal.api.domain.dtos.AbstractReport;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.MessageSourceUtil.cropMessage;

@Service
public class ReportProcessorService {

    // creates a thread pool
    @Getter
    private final ExecutorService executorService = Executors.newFixedThreadPool(50);

    /**
     * Aux var to store the status of the individual processing report
     */
    @Getter
    private static final HashMap<Long, Integer> loadings = new HashMap<>();

    /**
     * @param fields String[]
     * @return String[]
     */
    public String[] extractFieldsToIgnore(final IOutputReport output, final String[] fields) throws InstantiationException, IllegalAccessException {
        Assert.notNull(fields, "fields cannot be null");
        return ListUtil.toArray(((AbstractReport) output.getType().getType().newInstance()).extractFields().stream().filter(field -> Arrays.stream(fields).noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }

    /**
     * @param stream                 Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param input                  IReport implementation from ReportParams
     * @param output                 IReport implementation from ERport
     * @param nextLineOutputReport   Consumer<IReport>
     * @param doneReportOutputReport Consumer<IReport> in the end of the process, update EReport register on database with the file saved in the system file.
     * @param lineErrorConsumer      Consumer<Exception>
     * @param generalErrorConsumer   Consumer<Exception>
     */
    @Transactional
    public void convertToCSV(@NonNull final Stream<?> stream, final IInputReport input, final IOutputReport output, final Consumer<IOutputReport> nextLineOutputReport, final Consumer<IOutputReport> doneReportOutputReport, final Consumer<IOutputReport> lineErrorConsumer, final Consumer<IOutputReport> generalErrorConsumer) {

        try {

            if (output.getCountLines() == 0) {
                if (input.getRequester().equals(EReport.SYSTEM_USER))
                    throw new RuntimeException("Nenhum registro encontrado para a data " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().minusDays(1)));
                throw new RuntimeException("Nenhum registro encontrado para essa solicitação, revise os filtros e tente novamente!");
            }

            final CsvWriterSettings writerSettings = new CsvWriterSettings();
            writerSettings.getFormat().setLineSeparator("\r\n");
            writerSettings.getFormat().setDelimiter(';');
            writerSettings.setQuoteAllFields(true);
            writerSettings.setColumnReorderingEnabled(true);
            writerSettings.setHeaderWritingEnabled(true);
            writerSettings.setHeaders(ListUtil.toArray(input.getFields()));
            writerSettings.excludeFields(extractFieldsToIgnore(output, ListUtil.toArray(input.getFields())));

            writerSettings.setRowWriterProcessor(new BeanWriterProcessor<>(output.getType().getType()));

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final CsvWriter csvWriter = new CsvWriter(byteArrayOutputStream, writerSettings);

            stream.forEach(object -> {

                try {

                    // *** Next line flux
                    // Writing in file
                    csvWriter.processRecord(new Normalizer<>().normalize(object));

                    // Save the old percentage
                    final int oldPercentage = output.getConcludedPercentage();

                    // Set the current line
                    output.setCurrentLine((int) (csvWriter.getRecordCount()));

                    // Calculate the new percentage
                    output.calculatePercentage();

                    // If the current percentage is different from the previous percentage, and the current percentage is the exact divisor of the divisor., then
                    if (output.getConcludedPercentage() != oldPercentage && output.getConcludedPercentage() % output.getType().getDivisorToSave() == 0) {

                        // Populate the loading
                        loadings.put(output.getId(), output.getConcludedPercentage());

                        // If concluded percentage is 100%,
                        if (output.getConcludedPercentage().equals(100)) {
                            // Read byte array from file
                            output.setContent(byteArrayOutputStream.toByteArray());
                            // Emmit done event
                            doneReportOutputReport.accept(output);
                        } else
                            // Emmit next line event
                            nextLineOutputReport.accept(output);

                    }
                } catch (final Exception e) {
                    // *** Error in line flux
                    e.printStackTrace();
                    lineErrorConsumer.accept(output);
                }
            });

            // *** Done flux
            // Close writer
            csvWriter.close();
        } catch (final Exception e) {
            // *** General error flux
            e.printStackTrace();
            output.setError(cropMessage(e.getMessage(), 254));
            generalErrorConsumer.accept(output);
        }
    }
}