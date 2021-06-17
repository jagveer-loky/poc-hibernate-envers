package com.fiserv.preproposalApi.infrastrucutre.reports.impl;

import com.fiserv.preproposalApi.infrastrucutre.reports.IReportManager;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JasperReportManager implements IReportManager {

    /**
     *
     */
    private final static String SOURCE_FILE_EXTENSION = ".jrxml";

    /**
     *
     */
    private final static String COMPILED_FILE_EXTENSION = ".jasper";

    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(JasperReportManager.class.getName());

    /**
     *
     */
    private final DataSource dataSource;

    /**
     * @param reportPath {String}
     * @return {JasperReport}
     */
    private JasperReport createJasperReport(final String reportPath) {
        Assert.hasLength(reportPath, "The path to the report template must not be null or empty.");
        Assert.isTrue(reportPath.contains(COMPILED_FILE_EXTENSION) || reportPath.contains(SOURCE_FILE_EXTENSION), "The path to the report template must be a correct .jrxml or .jasper file.");

        //get the input stream of file
        final InputStream reportStream = this.getClass().getResourceAsStream(reportPath);
        Assert.notNull(reportStream, "Was not possible to find the report template: '" + reportPath + "'");

        final JasperReport jasperReport;

        //should we compile this report?
        if (reportPath.contains(SOURCE_FILE_EXTENSION)) {
            LOG.warning("Do not compile a report out of development environment. Compiling report: '" + reportPath + "'...");
            try {
                final long now = System.currentTimeMillis();
                jasperReport = JasperCompileManager.compileReport(reportStream);
                LOG.info("Time to compile: " + (System.currentTimeMillis() - now));
            } catch (JRException e) {
                throw new IllegalArgumentException("Was not to possible to compile the report: '" + reportPath + "'", e);
            }
        }
        //this file is already compile, so we just load the jasper file
        else {
            try {
                jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
            } catch (JRException e) {
                throw new IllegalArgumentException("Was not possible to load the compiled report: '" + reportPath + "'", e);
            }
        }

        return jasperReport;

    }

    /**
     * @param jasperReport {JasperReport}
     * @param parameters   {Map<String, Object>}
     * @return {JasperPrint}
     */
    private JasperPrint fillReport(final JasperReport jasperReport, final Map<String, Object> parameters) {
        Assert.notNull(this.dataSource, "The data source must be not null to generate a report. Please check if the bean is correctly configured.");
        Assert.notNull(jasperReport, "The jasper report must not be null.");

        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(this.dataSource);
            return JasperFillManager.fillReport(jasperReport, parameters, connection);
        } catch (JRException e) {
            throw new IllegalStateException("Was not possible to fill the report: '" + jasperReport.getName() + "'", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, this.dataSource);
            }
        }
    }

    /**
     *
     */
    @Override
    public ByteArrayOutputStream exportToPDF(final Map<String, Object> parameters, final String reportPath) {
        final JasperReport jasperReport = this.createJasperReport(reportPath);
        final JasperPrint jasperPrint = this.fillReport(jasperReport, parameters);

        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            return byteArrayOutputStream;
        } catch (JRException e) {
            throw new IllegalStateException("Was not possible generate a PDF stream of report: '" + jasperReport.getName() + "'", e);
        }
    }

    /**
     *
     */
    @Override
    public ByteArrayOutputStream exportToHTML(final Map<String, Object> parameters, final String reportPath) {
        throw new NotImplementedException("Not implemented yet. HTML report must be generated and zipped as a OutputStream.");
    }

    /**
     *
     */
    @Override
    public ByteArrayOutputStream exportToXLS(final Map<String, Object> parameters, final String reportPath) {
        final SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(true);
        configuration.setWhitePageBackground(false);
        return this.exportToXLS(parameters, reportPath, configuration);
    }

    /**
     *
     */
    @Override
    public ByteArrayOutputStream exportToXLS(final Map<String, Object> parameters, final String reportPath, final SimpleXlsReportConfiguration configuration) {
        final JasperReport jasperReport = this.createJasperReport(reportPath);
        final JasperPrint jasperPrint = this.fillReport(jasperReport, parameters);

        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final JRXlsExporter exporter = new JRXlsExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.setConfiguration(configuration);
            exporter.exportReport();

            return byteArrayOutputStream;
        } catch (Exception e) {
            throw new IllegalStateException("Was not possible generate a XLS stream of report: '" + jasperReport.getName() + "'", e);
        }
    }

    /**
     *
     */
    @Override
    public ByteArrayOutputStream exportToXML(final Map<String, Object> parameters, final String reportPath) {
        final JasperReport jasperReport = this.createJasperReport(reportPath);
        final JasperPrint jasperPrint = this.fillReport(jasperReport, parameters);

        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToXmlStream(jasperPrint, byteArrayOutputStream);
            return byteArrayOutputStream;
        } catch (JRException e) {
            throw new IllegalStateException("Was not possible generate a XML stream of report: '" + jasperReport.getName() + "'", e);
        }
    }

//    /**
//     *
//     */
//    public void setDataSource(final DataSource dataSource) {
//        Assert.notNull(dataSource, "The data source must be not null.");
//        this.dataSource = dataSource;
//    }
}
