package com.fiserv.luc.api.infrastructure.database.audited.revision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiserv.luc.api.Application;
import lombok.Data;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;

import static com.fiserv.luc.api.infrastructure.database.audited.revision.FiservRevision.TABLE_NAME;


/**
 * @param <T>
 * @param <ID>
 */
@Data
@Entity
@lombok.EqualsAndHashCode
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Table(name = Application.PREFIX /*TODO ACOPLAMENTO*/+ TABLE_NAME)
@org.hibernate.envers.RevisionEntity(EntityTrackingRevisionListener.class)
public class FiservRevision<T extends IEntity<ID>, ID extends Serializable> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4193623660483050410L;

    /**
     *
     */
    public static final String TABLE_NAME = "REVISION";

    /**
     * id da {@link FiservRevision}
     */
    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * data da {@link FiservRevision}
     */
    @RevisionTimestamp
    private long timestamp;

    /**
     * Username do usuário logado {@link FiservRevision}
     */
    private String username;


}
