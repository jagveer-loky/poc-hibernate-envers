package com.fiserv.luc.api.infrastructure.database.audited.revision;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.repository.history.RevisionRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
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
    @Autowired
    public RevisionRepository<T, Long, Long> revisionRepository;

    /***
     *
     * @param list List<Revision<Long, T>>
     * @return List<T>
     */
    public List<T> map(final List<Revision<Long, T>> list) {
        list.forEach(longObjectRevision -> {
            setUsername(longObjectRevision.getEntity(), ((FiservRevision) longObjectRevision.getMetadata().getDelegate()).getUsername());
            setRevisionType(longObjectRevision.getEntity(), longObjectRevision.getMetadata().getRevisionType());
        });
        return list.stream().map(Revision::getEntity).collect(Collectors.toList());
    }

    /**
     * @param object   Object
     * @param username String
     */
    private static void setUsername(final Object object, final String username) {
        for (final Method method : object.getClass().getMethods()) {
            if (method.getName().equals("setUsername")) {
                try {
                    method.invoke(object, username);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param object       Object
     * @param revisionType RevisionType
     */
    private static void setRevisionType(final Object object, final RevisionMetadata.RevisionType revisionType) {
        for (final Method method : object.getClass().getMethods()) {
            if (method.getName().equals("setRevisionType")) {
                try {
                    method.invoke(object, revisionType);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param id Long
     * @return Revisions<Long, BRPhysicPerson>
     */
    public List<T> findRevisionsById(final Long id) {
        return map(revisionRepository.findRevisions(id).getContent());
    }

    /**
     * @param id Long
     * @return Revisions<Long, BRPhysicPerson>
     */
    public Page<T> findRevisionsById(final Long id, final Pageable pageable) {
        throw new NotImplementedException("You're probably the first to need it, so implement it for us...");
    }
}
