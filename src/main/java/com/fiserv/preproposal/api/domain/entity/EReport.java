package com.fiserv.preproposal.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_REPORT")
public class EReport {

    @Id
    @GeneratedValue(generator = "TB_REPORT_SEQ")
    @SequenceGenerator(name = "TB_REPORT_SEQ" , sequenceName = "TB_REPORT_SEQ", allocationSize = 1)
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
    @Column(name = "OWNER", nullable = false)
    private String owner;

    /**
     *
     */
    @NotNull
    @Column(name = "REQUESTED_DATE", nullable = false)
    private LocalDateTime requestedDate;

    /**
     *
     */
    @Max(3)
    @NotNull
    @Column(name = "PERCENTAGE_TO_DONE", nullable = false)
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
    @Transient
    private int currentLine;

    /**
     *
     */
    @PrePersist
    public void prePersist() {
        requestedDate = LocalDateTime.now();
        concludedPercentage = 0;
    }

    /**
     *
     */
    public void calculatePercentage() {
        this.concludedPercentage = (currentLine * 100) / countLines;
        if (this.concludedPercentage == 100)
            concludedDate = LocalDateTime.now();
    }
}
