package com.fiserv.luc.api.infrastructure.database.audited.revision;

import org.hibernate.envers.RevisionType;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @version 1.0
 */
public class EntityTrackingRevisionListener implements org.hibernate.envers.EntityTrackingRevisionListener {
    /**
     *
     */
    @Override
    public void newRevision(final Object revisionEntity) {

        final String username = SecurityContextHolder.getContext() == null ? null : (SecurityContextHolder.getContext().getAuthentication() == null ? null : SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase());
        ((FiservRevision<?, ?>) revisionEntity).setUsername(username);
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.envers.EntityTrackingRevisionListener#entityChanged(java.lang.Class, java.lang.String, java.io.Serializable, org.hibernate.envers.RevisionType, java.lang.Object)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void entityChanged(Class entityClass, String entityName, Serializable entityId, RevisionType revisionType, Object revisionEntity) {
        final String username = SecurityContextHolder.getContext() == null ? null : (SecurityContextHolder.getContext().getAuthentication() == null ? null : SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase());
        ((FiservRevision<?, ?>) revisionEntity).setUsername(username);
    }

}
