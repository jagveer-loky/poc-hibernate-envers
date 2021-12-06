package com.fiserv.preproposal.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiserv.preproposal.api.domain.service.report.IOutputReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_REPORT")
public class EReport implements Serializable, IOutputReport {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy";

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

    /*
     * ------------------------------------
     * Filters
     * ------------------------------------
     */
    @Setter
    @Transient
    private String institution;

    @Setter
    @Getter
    @Transient
    private String serviceContract;

    @Setter
    @Getter
    @Transient
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDate initialDate;

    @Setter
    @Getter
    @Transient
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDate finalDate;

    @Setter
    @Transient
    private Boolean notIn;

    @Setter
    @Transient
    private Collection<String> responsesTypes;

    @Setter
    @Transient
    private Collection<String> status;

    @Setter
    @Getter
    @Transient
    private Collection<String> fields;

    @Setter
    @Getter
    @Transient
    private Collection<String> fieldsToIgnore;

    /*
     * ------------------------------------
     *              End Filters
     * ------------------------------------
     */

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

//    /**
//     * @param reportParams ReportParams
//     * @return EReport
//     */
//    public static EReport createFrom(final ReportParams reportParams) {
//
//        // Instancing the jpa Entity to persist
//        // This entity will save the percentage done of the job
//        final EReport eReport = new EReport();
//        eReport.setType(reportParams.getType());
//        eReport.setRequester(reportParams.getRequester());
//
//        return eReport;
//    }

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

    /**
     * @return institution String
     */
    public String getInstitution() {
        return format(institution);
    }

    /**
     * @return Boolean
     */
    public Boolean getNotIn() {
        return !Objects.isNull(notIn) && notIn;
    }

    /**
     * @return responsesTypes Collection<String>
     */
    public Collection<String> getResponsesTypes() {
        return (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes;
    }

    /**
     * @return status Collection<String>
     */
    public Collection<String> getStatus() {
        return (Objects.isNull(status) || status.isEmpty()) ? null : status;
    }

    /**
     * @return Class
     */
    public Class getBeanType() {
        if(type == null)
            return null;
        return type.getType();
    }

    /**
     * @param value Object
     * @return String
     */
    public String format(final Object value) {
        if(value == null)
            return null;
        return String.format("%0" + 8 + "d", Long.valueOf((String) value));
    }
}
