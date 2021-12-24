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
@MappedSuperclass
@Audited(withModifiedFlag = true)
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
    public AbstractEntity() {
    }

    /**
     * @param id Long
     */
    public AbstractEntity(final Long id) {
        this.setId(id);
    }
}
