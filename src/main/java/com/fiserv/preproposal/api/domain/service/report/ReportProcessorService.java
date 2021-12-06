package com.fiserv.preproposal.api.domain.service.report;

import com.fiserv.preproposal.api.domain.dtos.AbstractReport;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.ListUtil;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fiserv.preproposal.api.infrastrucutre.normalizer.Normalizer.normalize;
import static org.apache.commons.lang3.StringUtils.join;

@Service
public class ReportProcessorService {

    @Value("${reports.temp-output}")
    private String tempOutput;

    /**
     *
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @param labels String[]
     * @return String[]
     */
    public static String[] extractLabelsToIgnore(final IOutputReport output, final String[] labels) throws InstantiationException, IllegalAccessException {
        Assert.notNull(labels, "fields cannot be null");
        return ListUtil.toArrayString(((AbstractReport) output.getType().getType().newInstance()).extractLabels().stream().filter(field -> Arrays.stream(labels).noneMatch(innerField -> innerField.equals(field))).collect(Collectors.toSet()));
    }

    /**
     * @param indexes String[]
     * @return String[]
     */
    public static Integer[] extractIndexesToIgnore(final IOutputReport output, final Integer[] indexes) throws InstantiationException, IllegalAccessException {
        Assert.notNull(indexes, "fields cannot be null");
        return ListUtil.toArrayInteger(((AbstractReport) output.getType().getType().newInstance()).extractIndexes().stream().filter(index -> Arrays.stream(indexes).noneMatch(innerField -> innerField.equals(index))).collect(Collectors.toList()));
    }

    /**
     * @param stream                 Stream<T> To running when writing file. At each new iteration, a new register is written in file.
     * @param eRport                 IReport implementation from ReportParams
     * @param nextLineOutputReport   Consumer<IReport>
     * @param doneReportOutputReport Consumer<IReport> in the end of the process, update EReport register on database with the file saved in the system file.
     */
    public void convertToCSV(@NonNull final Stream<AbstractReport> stream, final EReport eRport, final Consumer<IOutputReport> nextLineOutputReport, final Consumer<IOutputReport> doneReportOutputReport) throws IOException, InstantiationException, IllegalAccessException {

        if (eRport.getCountLines() == 0) {
            if (eRport.getRequester().equals(EReport.SYSTEM_USER))
                throw new RuntimeException("Nenhum registro encontrado para a data " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().minusDays(1)));
            throw new RuntimeException("Nenhum registro encontrado para essa solicitação, revise os filtros e tente novamente!");
        }

        final File file = getFile(tempOutput);
        final FileWriter fileWriter = new FileWriter(file, true);
        final BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

        // Writing header in file
        bufferWriter.write(normalize(join(((AbstractReport) eRport.getBeanType().newInstance()).extractLabels(eRport.getFields()), ";")) + "\n");

        // Count lines
        final AtomicInteger i = new AtomicInteger();
        stream.forEach(object -> {
            i.getAndIncrement();
            try {
                // *** Next line flux
                // Extract values
                final List<Object> values = object.extractValues(eRport.getFields());
                // Writing line in file
                bufferWriter.write(normalize(join(values, ";")) + "\n");

                // Save the old percentage
                final int oldPercentage = eRport.getConcludedPercentage();

                // Set the current line
                eRport.setCurrentLine(i.get());

                // Calculate the new percentage
                eRport.calculatePercentage();

                // If the current percentage is different from the previous percentage, and the current percentage is the exact divisor of the divisor., then
                if (eRport.getConcludedPercentage() != oldPercentage && eRport.getConcludedPercentage() % eRport.getType().getDivisorToSave() == 0)

                    // Emmit next line event
                    nextLineOutputReport.accept(eRport);

            } catch (final Exception e) {
                // *** Error in line flux
                // Show de stack trace
                e.printStackTrace();
                // Logging
                LOGGER.info("ERROR IN " + eRport.getType() + " REPORT " + eRport.getId() + " - LINE: " + eRport.getCurrentLine());
                // Emmit next line event
                nextLineOutputReport.accept(eRport);
            }
        });

        // *** Done flux
        // After then running the stream.
        // Read byte array from file
        eRport.setContent(Files.readAllBytes(file.toPath()));
        // Emmit done event
        doneReportOutputReport.accept(eRport);
        // Close writer
        bufferWriter.close();
        fileWriter.close();
//        csvWriter.close();
        // Delete de tmp file
        Files.deleteIfExists(file.toPath());
    }

    /**
     * @param path String
     * @return File
     * @throws IOException
     */
    public static File getFile(final String path) throws IOException {
        final File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return new File(path + "/" + UUID.randomUUID() + ".csv");
    }

    /**
     * @param path String
     * @return File
     * @throws IOException
     */
    public static File getFile(final String path, final String file) throws IOException {
        final File filee = new File(path);
        if (!filee.exists())
            filee.mkdirs();
        return new File(path + "/" + file);
    }
}