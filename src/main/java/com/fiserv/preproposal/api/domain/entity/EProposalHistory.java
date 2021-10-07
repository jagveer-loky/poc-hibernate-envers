package com.fiserv.preproposal.api.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PRE_PROPOSAL_HISTORY")
public class EProposalHistory {

    @Id
    @GeneratedValue(generator = "PROP_HISTORY_SEQ")
    @SequenceGenerator(name = "PROP_HISTORY_SEQ", sequenceName = "PROP_HISTORY_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull(message = "The proposalData cannot be null")
    @ManyToOne
    @JoinColumn(name = "ID_PROPOSAL_DATA", referencedColumnName = "ID")
    private EProposalData proposalData;

    @Size(max = 155)
    @NotNull(message = "The description cannot be null")
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull(message = "The status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private EnumWorkflowStatus status;

    @NotNull(message = "The insertData cannot be null")
    @Column(name = "INSERT_DATA")
    private LocalDateTime insertData;

    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = EProposalHistoryError.class, mappedBy = "proposalHistory", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<EProposalHistoryError> errors;

    /**
     * EProposalHistory constructor, description will be status description when this field (description) has null.
     *
     * @param proposalData EProposalData
     * @param status       EnumWorkflowStatus
     */
    public EProposalHistory(final EProposalData proposalData, final EnumWorkflowStatus status) {
        this.proposalData = proposalData;
        this.status = status;
        this.description = status.getDescription();
    }

    public EProposalHistory(EProposalData eProposalData, String description, EnumWorkflowStatus status) {
        if (Objects.isNull(description)) {
            this.description = status.getDescription();
        } else {
            this.description = description;
        }
        this.status = status;
        this.proposalData = eProposalData;
    }

    /**
     *
     */
    @PrePersist
    public void prePersist() {
        this.insertData = LocalDateTime.now();
    }

}
