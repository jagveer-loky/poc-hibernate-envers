package com.fiserv.preproposal.api.infrastrucutre.io;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.common.processor.RowWriterProcessor;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthWriter;
import com.univocity.parsers.fixed.FixedWidthWriterSettings;
import lombok.Data;
import lombok.NonNull;
import org.beanio.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@SuppressWarnings("unchecked")
public class IOService<T> {

    /**
     * @param layout     String
     * @param input      String
     * @param streamName String
     * @return T
     */
    public T read(@NonNull final String layout, @NonNull final String input, @NonNull final String streamName) {
        return read(layout, input, streamName, null);
    }

    /**
     * @param layout        String
     * @param input         String
     * @param streamName    String
     * @param linesToIgnore String
     * @return T
     */
    private T read(@NonNull final String layout, @NonNull final String input, @NonNull final String streamName, Set<String> linesToIgnore) {

        final StreamFactory factory = createStreamFactoryFromLayout(layout);

        final File file = new File(input);

        try {

            InputStream inputStream = new FileInputStream(file);

            if (linesToIgnore != null && !linesToIgnore.isEmpty()) {
                final ByteArrayOutputStream buffer = (ByteArrayOutputStream) getFileWithIgnoredLine(file, linesToIgnore);
                final byte[] bytes = buffer.toByteArray();
                inputStream = new ByteArrayInputStream(bytes);
            }

            final Reader reader = new InputStreamReader(inputStream);

            final BeanReader br = factory.createReader(streamName, reader);

            T read = (T) br.read();
            br.close();

            return read;

        } catch (final InvalidRecordException e) {

            for (int i = 0; i < e.getRecordCount(); i++) {
                if (e.getRecordContext(i).hasErrors()) {
                    if (linesToIgnore == null)
                        linesToIgnore = new HashSet<>();
                    linesToIgnore.add(e.getRecordContext(i).getRecordText());
                }
            }

            return this.read(layout, input, streamName, linesToIgnore);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param inputFile     File
     * @param linesToIgnore Set<String>
     * @return OutputStream
     */
    private OutputStream getFileWithIgnoredLine(final File inputFile, final Set<String> linesToIgnore) throws IOException {
        final List<String> lines = Files.lines(inputFile.toPath())
                .filter(line -> linesToIgnore.stream().noneMatch(s -> s.equals(line)))
                .collect(Collectors.toList());

        final OutputStream out = new ByteArrayOutputStream();

        try (final PrintWriter w = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {
            lines.forEach(s -> {
                w.write(s);
                w.println();
            });

        } //

        return out;
    }

    /**
     * @param layout String
     * @return StreamFactory
     */
    private static StreamFactory createStreamFactoryFromLayout(final String layout) {
        try {
            final StreamFactory factory = StreamFactory.newInstance();
            ClassPathResource classPathResource = new ClassPathResource(layout);
            factory.load(classPathResource.getInputStream());
            return factory;
        } catch (final Exception e) {
            final StreamFactory factory = StreamFactory.newInstance();
            factory.load(layout);
            return factory;
        }
    }

    /**
     * @param objects    Stream<T>
     * @param layout     String
     * @param streamName String
     * @param headerName String
     */
    public byte[] writeInMemory(@NonNull final Stream<T> objects, final @NonNull String headerName, final @NonNull String layout, @NonNull final String streamName) {
        final StreamFactory factory = createStreamFactoryFromLayout(layout);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {

            final Writer writer = new OutputStreamWriter(outputStream);

            final BeanWriter beanWriter = factory.createWriter(streamName, writer);

            beanWriter.write(headerName, null);

            objects.forEach(beanWriter::write);

            beanWriter.flush();
            beanWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    /**
     * @param objects
     * @return
     */
    public byte[] writeInMemory(@NonNull final Stream<T> objects/*,  final @NonNull String headerName, final @NonNull String layout, @NonNull final String streamName, @NonNull List<Column> ignoredColumns*/) {
//        final StreamFactory factory = createStreamFactoryFromLayout(layout);
//
//        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        try {
//
//            final Writer writer = new OutputStreamWriter(outputStream);
//
//            final BeanWriter beanWriter = factory.createWriter(streamName, writer);
//
//            beanWriter.write(headerName, null);
//
//            objects.forEach(beanWriter::write);
//
//            beanWriter.flush();
//            beanWriter.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

////        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
//
//        BeanWriterProcessor<Bean> processor = new BeanWriterProcessor(Bean.class);  //TODO zoado
//
//
//        CsvWriterSettings  settings = new CsvWriterSettings ();  //TODO zoado
////        final CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
//        settings.setRowWriterProcessor(processor);
////        csvWriterSettings.excludeFields("Age");
//        settings.setHeaderWritingEnabled(true);
//        settings.setColumnReorderingEnabled(true); //this does the trick
//        // Sets the file headers
//        settings.setHeaders("id", "cpfCnpj");
//
//        settings.selectFields("id", "cpfCnpj");
//        Bean bean = new Bean();
//        bean.setId("1");
//        bean.setCpfCnpj("1");

//        final CsvWriter writer = new CsvWriter(outputStream, settings); //TODO ZOADO
//
//        // Writes the headers specified in the settings
//        writer.writeHeaders();
//
//        writer.processRecord(bean);
////        objects.forEach(asdfasd -> {
////            writer.processRecord(asdfasd);
////        });
//
//
//        writer.close();
//
//        return outputStream.toByteArray();
//


        // This time we're going to parse a list of beans at once and write them to an output.
// First we configure the input format
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator("\n");

// Then the output format
        CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.getFormat().setLineSeparator("\r\n");
        writerSettings.getFormat().setDelimiter(';');
        writerSettings.setQuoteAllFields(true);
//        writerSettings.setHeaders("id", "CPF ou CNPJ");
//        writerSettings.selectFields("id", "CPF ou CNPJ");
        writerSettings.setColumnReorderingEnabled(true);
        writerSettings.setHeaderWritingEnabled(true);
        writerSettings.excludeFields("Id da Proposta");
// Let's create a new routines object with the parser and writer configuration.

        BeanWriterProcessor processor = new BeanWriterProcessor(BasicReport.class);
        writerSettings.setRowWriterProcessor(processor);
        CsvWriter csvWriter = new CsvWriter(outputStream, writerSettings);
//        csvWriter.processRecordsAndClose(Arrays.asList(bean));

        objects.forEach(bean1 -> {
            csvWriter.processRecord(bean1);
        });

        csvWriter.close();

//        CsvRoutines routines = new CsvRoutines(parserSettings, writerSettings); // Can also use TSV and Fixed-width routines
//
//// Now, let's write all beans to the output using the writeAll routine:
//// Note that it takes an Iterable as the input. You could use routines.iterate(),
//// as shown in the previous example, to avoid loading all objects in memory.
//        routines.writeAll(Arrays.asList(bean), Bean.class, outputStream);

// And here's the result
        return outputStream.toByteArray();
    }
//
//    @Data
//    public class Bean {
//        @Parsed
//        private String id;
//        @Parsed
//        private String cpfCnpj;
//    }

    /**
     * @param objects    Stream<T>
     * @param layout     String
     * @param streamName String
     */
    public byte[] writeInMemory(@NonNull final Stream<T> objects, final @NonNull String layout, @NonNull final String streamName) {

        final StreamFactory factory = createStreamFactoryFromLayout(layout);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {

            final Writer writer = new OutputStreamWriter(outputStream);

            final BeanWriter beanWriter = factory.createWriter(streamName, writer);

            objects.forEach(beanWriter::write);

            beanWriter.flush();
            beanWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();

    }

    /**
     * @param objects    List<T>
     * @param layout     String
     * @param output     String
     * @param streamName String
     */
    public void write(@NonNull final Stream<T> objects, final @NonNull String layout, @NonNull final String output, @NonNull final String streamName) {

        final StreamFactory factory = createStreamFactoryFromLayout(layout);

        try {

            try {
                Files.deleteIfExists(Paths.get(output));
            } catch (final IOException e) {
                e.printStackTrace();
            }

            final BeanWriter beanWriter = factory.createWriter(streamName, new File(output));

            objects.forEach(beanWriter::write);

            beanWriter.flush();
            beanWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param objects    T
     * @param layout     String
     * @param output     String
     * @param streamName String
     */
    public void write(@NonNull final T objects, final @NonNull String layout, @NonNull final String output, @NonNull final String streamName) {

        final StreamFactory factory = createStreamFactoryFromLayout(layout);

        try {

            try {
                Files.deleteIfExists(Paths.get(output));
            } catch (final IOException e) {
                e.printStackTrace();
            }

            final BeanWriter beanWriter = factory.createWriter(streamName, new File(output));

            beanWriter.write(streamName, objects);
            beanWriter.flush();
            beanWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param e BeanReaderException
     * @return String
     */
    public static List<ILine> treatException(final BeanReaderException e) {

        final List<ILine> dBulkFileLines = new ArrayList<>();
//        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < e.getRecordCount(); i++) {
            final RecordContext context = e.getRecordContext(i);
            if (!context.hasErrors())
                continue;
//            sb.append(String.format("Line %s contain errors", i + 1));
//            sb.append(";");
//
//            if (context.hasRecordErrors()) {
//                sb.append(String.format("    >> Record errors: %s", String.join(", ", context.getRecordErrors())));
//                sb.append(";");
//            }
//
//            if (context.hasFieldErrors()) {
//                for (final Map.Entry<String, Collection<String>> field : context.getFieldErrors().entrySet()) {
//                    sb.append(String.format("    >> Field %s errors: %s", field.getKey(), String.join(", ", field.getValue())));
//                    sb.append(";");
//                }
//            }

            final ILine dBulkFileLine = new ILineImpl();
            dBulkFileLine.setLineNumber(i + 1);
            dBulkFileLine.setContent(e.getRecordContext().getRecordText());
            dBulkFileLine.setException(e.getMessage());
            dBulkFileLines.add(dBulkFileLine);
        }

        return dBulkFileLines;
    }

    /**
     * @param layout     String
     * @param input      String
     * @param streamName String
     * @return String
     */
    public List<ILine> errors(@NonNull final String layout, @NonNull final String input, @NonNull final String streamName) {

        final StreamFactory factory = createStreamFactoryFromLayout(layout);

        try {
            final File file = new File(input);

            final BeanReader br = factory.createReader(streamName, file);
            br.read();
            br.close();

        } catch (final BeanReaderException e) {
            return treatException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return new ArrayList<>();
    }

    /**
     * @param layout     String
     * @param input      String
     * @param streamName String
     * @return List<DBulkFileLine>
     */
    public List<ILine> lines(@NonNull final String layout, @NonNull final String input, @NonNull final String streamName) {

        final StreamFactory factory = createStreamFactoryFromLayout(layout);

        try {

            final File file = new File(input);

            final BeanReader br = factory.createReader(streamName, file);
            br.read();
            br.close();

            return lines(file);

        } catch (final BeanReaderException e) {
            return lines(e);
        }

    }

    /**
     * @param e BeanReaderException
     * @return String
     */
    public static List<ILine> lines(final BeanReaderException e) {

        final List<ILine> dBulkFileLines = new ArrayList<>();
        for (int i = 0; i < e.getRecordCount(); i++) {
            final RecordContext context = e.getRecordContext(i);
            if (context.hasErrors())
                continue;

            final ILine dBulkFileLine = new ILineImpl();
            dBulkFileLine.setLineNumber(i + 1);
            dBulkFileLine.setContent(context.getRecordText());
            dBulkFileLines.add(dBulkFileLine);
        }

        return dBulkFileLines;
    }

    /**
     * @param file File
     * @return List<ILine>
     */
    public static List<ILine> lines(final File file) {

        final List<ILine> dBulkFileLines = new ArrayList<>();
        try {
            final List<String> lines = Files.lines(file.toPath()).collect(Collectors.toList());

            for (int i = 0; i < lines.size(); i++) {
                final ILine dBulkFileLine = new ILineImpl();
                dBulkFileLine.setLineNumber(i + 1);
                dBulkFileLine.setContent(lines.get(i));
                dBulkFileLines.add(dBulkFileLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dBulkFileLines;
    }


    /**
     *
     */
    public interface ILine {

        void setLineNumber(final int lineNumber);

        void setContent(final String content);

        void setException(final String exception);

    }

    @Data
    public static class ILineImpl implements ILine {
        private int lineNumber;
        private String content;
        private String exception;
    }
}
