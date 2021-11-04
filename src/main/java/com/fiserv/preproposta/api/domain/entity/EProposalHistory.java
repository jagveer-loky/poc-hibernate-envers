package com.fiserv.preproposta.api.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
    @Column(name = "STATUS")
    private String status;

    @NotNull(message = "The insertData cannot be null")
    @Column(name = "INSERT_DATA")
    private LocalDateTime insertData;

    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = EProposalHistoryError.class, mappedBy = "proposalHistory", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<EProposalHistoryError> errors;

    public EProposalHistory(final EProposalData eProposalData, final String description, final String status) {
        this.description = description;
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
