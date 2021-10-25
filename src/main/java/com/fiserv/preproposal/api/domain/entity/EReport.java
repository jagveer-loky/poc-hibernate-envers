package com.fiserv.preproposal.api.domain.entity;

import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_REPORT")
public class EReport implements Serializable {

    @Id
    @GeneratedValue(generator = "TB_REPORT_SEQ")
    @SequenceGenerator(name = "TB_REPORT_SEQ", sequenceName = "TB_REPORT_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    /**
     *
     */
    @NotNull
    @Column(name = "REQUESTER", nullable = false)
    private String requester;

    /**
     *
     */
    @NotNull
    @Column(name = "REQUESTED_DATE", nullable = false)
    private LocalDateTime requestedDate;

    /**
     *
     */
    @NotNull
    @Column(name = "CONCLUDED_PERCENTAGE", nullable = false)
    private int concludedPercentage;

    /**
     *
     */
    @Column(name = "CONCLUDED_DATE")
    private LocalDateTime concludedDate;

    /**
     *
     */
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
    @Column(name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private TypeReport type;

    /**
     *
     */
    @Column(name = "ERROR")
    private String error;

    /**
     *
     */
    @Lob
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
        } else
            concludedPercentage = countLines == 0 ? concludedPercentage : (currentLine * 100) / countLines;
    }

    /**
     * @return int
     */
    public int getConcludedPercentage() {
        calculatePercentage();
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
}
