package com.fiserv.preproposta.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PRE_PROPOSAL_HISTORY_ERROR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EProposalHistoryError {

    @Id
    @GeneratedValue(generator = "PROP_HIST_ERROR_SEQ")
    @SequenceGenerator(name = "PROP_HIST_ERROR_SEQ" , sequenceName = "PROP_HIST_ERROR_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Size(max = 155)
    @Column(name = "FIELD")
    private String field;

    /**
     * Used for {@link EProposalHistoryError#field} translation
     */
    @Size(max = 155)
    @Column(name = "FIELD_DESCRIPTION")
    private String fieldDescription;

    @Size(max = 255)
    @NotNull(message = "The message cannot be null")
    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Size(max = 255)
    @Column(name = "MESSAGE_DETAIL")
    private String messageDetail;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "ERROR_CODE", nullable = false)
    private Integer errorCode;

    @NotNull(message = "The proposalHistory cannot be null")
    @ManyToOne
    @JoinColumn(name = "ID_PROPOSAL_HISTORY", referencedColumnName = "ID")
    private EProposalHistory proposalHistory;

    public EProposalHistoryError(String field, String message, Integer errorCode, EProposalHistory proposalHistory) {
        this.field = field;
        this.message = message;
        this.errorCode = errorCode;
        this.proposalHistory = proposalHistory;
    }
}
