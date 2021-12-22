package com.fiserv.luc.api.infrastructure.database.audited.revision;

import com.fiserv.luc.api.Application;
import lombok.Data;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;

import static com.fiserv.luc.api.infrastructure.database.audited.revision.Revision.TABLE_NAME;


/**
 * @param <T>
 * @param <ID>
 */
@Data
@Entity
@lombok.EqualsAndHashCode
@Table(name = Application.PREFIX /*TODO ACOPLAMENTO*/+ TABLE_NAME)
@org.hibernate.envers.RevisionEntity(EntityTrackingRevisionListener.class)
public class Revision<T extends IEntity<ID>, ID extends Serializable> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4193623660483050410L;

    /**
     *
     */
    public static final String TABLE_NAME = "REVISION";

    /**
     * id da {@link Revision}
     */
    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * data da {@link Revision}
     */
    @RevisionTimestamp
    private long timestamp;

    /**
     * Username do usuário logado {@link Revision}
     */
    private String username;

    /**
     * entidade da {@link Revision}
     */
    @Transient
    private T entity;

}
