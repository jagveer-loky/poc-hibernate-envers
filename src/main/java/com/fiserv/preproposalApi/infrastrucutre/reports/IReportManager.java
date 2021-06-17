package com.fiserv.preproposalApi.infrastrucutre.reports;

import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public interface IReportManager {
    /**
     * @param parameters {Map<String, Object>}
     * @param reportPath {String}
     */
    ByteArrayOutputStream exportToPDF(final Map<String, Object> parameters, final String reportPath);

    /**
     * @param parameters {Map<String, Object>}
     * @param reportPath {String}
     */
    ByteArrayOutputStream exportToHTML(final Map<String, Object> parameters, final String reportPath);

    /**
     * @param parameters {Map<String, Object>}
     * @param reportPath {String}
     */
    ByteArrayOutputStream exportToXML(final Map<String, Object> parameters, final String reportPath);

    /**
     * @param parameters {Map<String, Object>}
     * @param reportPath {String}
     */
    ByteArrayOutputStream exportToXLS(final Map<String, Object> parameters, final String reportPath);

    /**
     * @param parameters    {Map<String, Object>}
     * @param reportPath    {String}
     * @param configuration {SimpleXlsReportConfiguration}
     * @return ByteArrayOutputStream
     */
    ByteArrayOutputStream exportToXLS(final Map<String, Object> parameters, final String reportPath, final SimpleXlsReportConfiguration configuration);

//	/**
//	 *
//	 * @param dataSource
//	 */
//	public void setDataSource( DataSource dataSource );
}
