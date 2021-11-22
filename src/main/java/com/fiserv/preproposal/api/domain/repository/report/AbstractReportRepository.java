package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil;
import com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.aid.util.MessageSourceUtil.cropMessage;

public abstract class AbstractReportRepository<T> implements IWriteReportRepository<T> {

    /**
     *
     */
    private final Normalizer<T> normalizer = new Normalizer<>();

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
    public void convertToCSV(@NonNull final Stream<T> stream, final IInputReport input, final IOutputReport output, final Consumer<IOutputReport> nextLineOutputReport, final Consumer<IOutputReport> doneReportOutputReport, final Consumer<IOutputReport> lineErrorConsumer, final Consumer<IOutputReport> generalErrorConsumer) {

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
            writerSettings.excludeFields(extractFieldsToIgnore(ListUtil.toArray(input.getFields())));

            writerSettings.setRowWriterProcessor(configProcessor());

            final ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();
            final CsvWriter csvWriter = new CsvWriter(byteArrayOutputStream, writerSettings);

            stream.forEach(object -> {

                try {
                    // *** Next line flux
                    // Writing in file
                    csvWriter.processRecord(normalizer.normalize(object));

                    // Save the old percentage
                    final int oldPercentage = output.getConcludedPercentage();

                    // Set the current line
                    output.setCurrentLine((int) (csvWriter.getRecordCount()));

                    // Calculate the new percentage
                    output.calculatePercentage();

                    // If the current percentage is different from the previous percentage, and the current percentage is the exact divisor of the divisor., then
                    if (output.getConcludedPercentage() != oldPercentage && output.getConcludedPercentage() % output.getType().getDivisorToSave() == 0 && output.getConcludedPercentage() < 80) {
                        //// Read byte array from file
//                        output.setContent(Files.readAllBytes(file.toPath()));
                        // Emmit the event
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

            // Set the current line with the last line
            output.setCurrentLine(output.getCountLines());

            // Calculate the new percentage, 100%
            output.calculatePercentage();

            // Read byte array from file
            output.setContent(byteArrayOutputStream.toByteArray());

            // Emmit the event
            doneReportOutputReport.accept(output);

        } catch (final Exception e) {
            // *** General error flux
            e.printStackTrace();
            output.setError(cropMessage(e.getMessage(), 254));
            generalErrorConsumer.accept(output);
        }
    }
}