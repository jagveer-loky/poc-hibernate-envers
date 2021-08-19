package com.fiserv.preproposal.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Max;
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
    @Column(name = "PATH", nullable = false)
    private String path;

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
    @PrePersist
    public void prePersist() {
        countLines = 0;
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
}
