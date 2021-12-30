package com.fiserv.luc.api.infrastructure.database.audited.revision;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiserv.luc.api.infrastructure.aid.util.DAOUtil;
import lombok.Data;
import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AbstractService to converting revision objects to legible objects
 *
 * @param <T>
 */
public abstract class AbstractRevisionService<T> {

    /**
     *
     */
    private AuditReader reader;

    /**
     *
     */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     *
     */
    private final Class<T> clazz = (Class<T>) DAOUtil.getTypeArguments(AbstractRevisionService.class, this.getClass()).get(0);

    /**
     *
     */
    @PostConstruct
    public void postConstruct() {
        reader = AuditReaderFactory.get(Objects.requireNonNull(entityManagerFactory.createEntityManager()));
    }

    /**
     * @param id Long
     * @return Revisions<Long, BRPhysicPerson>
     */
    public List<Object> findRevisionsById(final Long id) {
        final List<Object> returnList = new ArrayList<>();
        final AuditQuery query = this.reader.createQuery().forRevisionsOfEntityWithChanges(clazz, true).add(AuditEntity.id().eq(id));
        final List<Object[]> results = query.getResultList();
        for (final Object[] result : results) {
            final T object = (T) result[0];
            final FiservRevision revEntity = (FiservRevision) result[1];
            final RevisionType revType = (RevisionType) result[2];
            final Set<String> properties = (Set<String>) result[3];

            final RevisionDTO<T> revisionDTO = new RevisionDTO<>();
            revisionDTO.setRevisionId(revEntity.getId());
            revisionDTO.setDateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(revEntity.getTimestamp()), TimeZone.getDefault().toZoneId()));
            revisionDTO.setUsername(revEntity.getUsername());
            revisionDTO.setType(revType);
            revisionDTO.setChangedProps(String.join(",", properties));
            revisionDTO.setEntity(object);

            returnList.add(revisionDTO);
        }

        return returnList;
    }


    /**
     * @return Revisions<Long, BRPhysicPerson>
     */
    public List<Object> findRevisions() {
        final List<Object> returnList = new ArrayList<>();
        final AuditQuery query = this.reader.createQuery().forRevisionsOfEntityWithChanges(clazz, true);
        final List<Object[]> results = query.getResultList();
        for (final Object[] result : results) {
            final T object = (T) result[0];
            final FiservRevision revEntity = (FiservRevision) result[1];
            final RevisionType revType = (RevisionType) result[2];
            final Set<String> properties = (Set<String>) result[3];

            final RevisionDTO<T> revisionDTO = new RevisionDTO<>();
            revisionDTO.setRevisionId(revEntity.getId());
            revisionDTO.setDateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(revEntity.getTimestamp()), TimeZone.getDefault().toZoneId()));
            revisionDTO.setUsername(revEntity.getUsername());
            revisionDTO.setType(revType);
            revisionDTO.setChangedProps(String.join(",", properties));
            revisionDTO.setEntity(object);

            returnList.add(revisionDTO);
        }

        return returnList;
    }


    /**
     * @param id Long
     * @return Revisions<Long, BRPhysicPerson>
     */
    public Page<T> findRevisionsById(final Long id, final Pageable pageable) {
        throw new NotImplementedException("You're probably the first to need it, so implement it for us...");
    }

    /**
     * DTO to exporting revision informations to client
     *
     * @param <T>
     */
    @Data
    private static final class RevisionDTO<T> {

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        public LocalDateTime dateTime;
        private Long revisionId;
        private String username;
        private RevisionType type;
        private String changedProps;

        private T entity;

        /**
         * @param username String
         */
        public void setUsername(final String username) {
            if (username!= null && username.length() != 0)
                this.username = username;
        }

        /**
         * @param changedProps String
         */
        public void setChangedProps(final String changedProps) {
            if (changedProps != null && changedProps.length() != 0)
                this.changedProps = changedProps;
        }
    }
}
