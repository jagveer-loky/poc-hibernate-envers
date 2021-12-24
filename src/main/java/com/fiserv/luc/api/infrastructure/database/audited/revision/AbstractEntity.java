package com.fiserv.luc.api.infrastructure.database.audited.revision;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 */
@Data
@Audited
@MappedSuperclass
public abstract class AbstractEntity implements IEntity<Long> {

    private static final long serialVersionUID = -3875946542616104733L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    /**
     *
     */
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false)
    public LocalDateTime created;

    /**
     *
     */
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime updated;

    /**
     * Username of who update or create register {@link FiservRevision}
     */
    @Transient
    private String username;

    /**
     * Username of who update or create register {@link FiservRevision}
     */
    @Transient
    private RevisionMetadata.RevisionType revisionType;

    /**
     *
     */
    public AbstractEntity() {
    }

    /**
     * @param id Long
     */
    public AbstractEntity(final Long id) {
        this.setId(id);
    }

    /**
     *
     */
    @PrePersist
    protected void prePersist() {
        this.created = LocalDateTime.now();
    }

    /**
     *
     */
    @PreUpdate
    protected void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
