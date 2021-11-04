package com.fiserv.preproposta.api.domain.repository.report;

import com.fiserv.preproposta.api.domain.dtos.ReportParams;
import com.fiserv.preproposta.api.domain.entity.EReport;
import com.fiserv.preproposta.api.infrastrucutre.normalizer.Normalizer;
import com.fiserv.preproposta.api.infrastrucutre.aid.util.ListUtil;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractReportRepository<T> implements IWriteReportRepository<T> {

    /**
     *
     */
    private final Normalizer<T> normalizer = new Normalizer<>();

    /**
     * @param stream               Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param reportParams         ReportParams
     * @param nextLine             Consumer<byte[]>
     * @param done                 Consumer<byte[]> in the end of the process, update EReport register on database with the file saved in the system file.
     * @param lineErrorConsumer    Consumer<Exception>
     * @param generalErrorConsumer Consumer<Exception>
     */
    @Transactional
    public void convertToCSV(@NonNull final Stream<T> stream, final int countLines, final ReportParams reportParams, final Consumer<byte[]> nextLine, final Consumer<byte[]> done, final Consumer<Exception> lineErrorConsumer, final Consumer<Exception> generalErrorConsumer) {

        try {

            if (countLines == 0) {
                if (reportParams.getRequester().equals(EReport.SYSTEM_USER))
                    throw new RuntimeException("Nenhum registro encontrado para a data " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().minusDays(1)));
                throw new RuntimeException("Nenhum registro encontrado para essa solicitação, revise os filtros e tente novamente!");
            }

            final CsvWriterSettings writerSettings = new CsvWriterSettings();
            writerSettings.getFormat().setLineSeparator("\r\n");
            writerSettings.getFormat().setDelimiter(';');
            writerSettings.setQuoteAllFields(true);
            writerSettings.setColumnReorderingEnabled(true);
            writerSettings.setHeaderWritingEnabled(true);
            writerSettings.setHeaders(ListUtil.toArray(reportParams.getFields()));
            writerSettings.excludeFields(extractFieldsToIgnore(ListUtil.toArray(reportParams.getFields())));

            writerSettings.setRowWriterProcessor(configProcessor());

            final ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();
            final CsvWriter csvWriter = new CsvWriter(byteArrayOutputStream, writerSettings);

            stream.forEach(object -> {

                try {
                    // Writing in file
                    csvWriter.processRecord(normalizer.normalize(object));

                    nextLine.accept(byteArrayOutputStream.toByteArray());

                } catch (final Exception e) {
                    lineErrorConsumer.andThen(lineError -> csvWriter.close()).accept(e);
                }

            });

            csvWriter.close();
            done.accept(byteArrayOutputStream.toByteArray());

        } catch (final Exception e) {
            generalErrorConsumer.accept(e);
        }
    }
}