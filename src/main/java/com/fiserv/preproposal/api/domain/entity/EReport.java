package com.fiserv.preproposal.api.domain.entity;

import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.repository.report.IOutputReport;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_REPORT")
public class EReport implements Serializable, IOutputReport {

    public static final String SYSTEM_USER = "SYSTEM";
    public static final String SERVICE_CONTRACT = "149";
    public static final String INSTITUTION = "00000007";

    @Id
    @Setter
    @Getter
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = "TB_REPORT_SEQ")
    @SequenceGenerator(name = "TB_REPORT_SEQ", sequenceName = "TB_REPORT_SEQ", allocationSize = 1)
    private Long id;

    /**
     *
     */
    @Setter
    @Getter
    @NotNull
    @Column(name = "REQUESTER", nullable = false)
    private String requester;

    /**
     *
     */
    @Setter
    @Getter
    @NotNull
    @Column(name = "REQUESTED_DATE", nullable = false)
    private LocalDateTime requestedDate;

    /**
     *
     */
    @Setter
    @NotNull
    @Column(name = "CONCLUDED_PERCENTAGE", nullable = false)
    private int concludedPercentage;

    /**
     *
     */
    @Setter
    @Getter
    @Column(name = "CONCLUDED_DATE")
    private LocalDateTime concludedDate;

    /**
     *
     */
    @Setter
    @Column(name = "COUNT_LINES")
    private int countLines;

    /**
     *
     */
    @Column(name = "CURRENT_LINE")
    private int currentLine;

    /**
     *
     */
    @Setter
    @Getter
    @Column(name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private TypeReport type;

    /**
     *
     */
    @Setter
    @Getter
    @Column(name = "ERROR")
    private String error;

    /**
     *
     */
    @Lob
    @Setter
    @Getter
    @Column(name = "CONTENT", columnDefinition = "BLOB")
    private byte[] content;

    /**
     * @param id                  Long
     * @param requester           String
     * @param requestedDate       LocalDateTime
     * @param concludedDate       LocalDateTime
     * @param concludedPercentage int
     * @param countLines          int
     * @param currentLine         int
     * @param type                TypeReport
     * @param error               String
     */
    public EReport(final Long id, final String requester,
                   final LocalDateTime requestedDate, final LocalDateTime concludedDate,
                   final int concludedPercentage, final int countLines, final int currentLine, final TypeReport type, final String error) {
        this.id = id;
        this.requester = requester;
        this.requestedDate = requestedDate;
        this.concludedPercentage = concludedPercentage;
        this.concludedDate = concludedDate;
        this.countLines = countLines;
        this.currentLine = currentLine;
        this.type = type;
        this.error = error;
    }

    /**
     *
     */
    @PrePersist
    public void prePersist() {
        countLines = 0;
        if (requestedDate == null)
            requestedDate = LocalDateTime.now();
        calculatePercentage();
    }

    /**
     *
     */
    public void calculatePercentage() {
        if (concludedPercentage == 100 && concludedDate == null) {
            concludedDate = LocalDateTime.now();
        } else if (concludedDate != null) {
            concludedPercentage = 100;
        } else if (currentLine != 0 && currentLine == countLines) {
            concludedPercentage = 100;
            concludedDate = LocalDateTime.now();
        } else
            concludedPercentage = countLines == 0 ? concludedPercentage : (currentLine * 100) / countLines;
    }

    /**
     * @return int
     */
    public Integer getConcludedPercentage() {
//        calculatePercentage();
        return concludedPercentage;
    }

    /**
     * @return boolean return if has error
     */
    public boolean hasError() {
        return this.error != null && !this.error.trim().isEmpty();
    }

    /**
     * @param reportParams ReportParams
     * @return EReport
     */
    public static EReport createFrom(final ReportParams reportParams) {

        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setType(reportParams.getType());
        eReport.setRequester(reportParams.getRequester());

        return eReport;
    }

    /**
     * @return Integer
     */
    @Override
    public Integer getCountLines() {
        return this.countLines;
    }

    /**
     * @param currentLine Integer
     */
    @Override
    public void setCurrentLine(final Integer currentLine) {
        this.currentLine = currentLine;
    }

    /**
     * @return Integer
     */
    @Override
    public Integer getCurrentLine() {
        return this.currentLine;
    }
}
