package com.fiserv.luc.api.domain.service.brazil;

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.repository.brazil.BRPhysicPersonRepository;
import com.fiserv.luc.api.infrastructure.database.audited.revision.AbstractRevisionService;
import com.fiserv.luc.api.infrastructure.database.audited.revision.FiservRevision;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BRPhysicPersonRevisionService extends AbstractRevisionService<BRPhysicPerson> {

    /**
     *
     */
    private final BRPhysicPersonRepository physicPersonRepository;

    /**
     * @param filters  String
     * @param pageable Pageable
     * @return Page<BRPhysicPerson>
     */
    public Page<BRPhysicPerson> listByFilters(final String filters, final Pageable pageable) {
        return this.physicPersonRepository.findAll(pageable);
    }

    /**
     * @param id Long
     * @return Optional<BRPhysicPerson>
     */
    public Optional<BRPhysicPerson> findById(final Long id) {
        return this.physicPersonRepository.findById(id);
    }

    /**
     * @param physicPerson BRPhysicPerson
     * @return BRPhysicPerson
     */
    public BRPhysicPerson save(final BRPhysicPerson physicPerson) {
        return this.physicPersonRepository.save(physicPerson);
    }

    /**
     * @param id Long
     */
    public void deleteById(final Long id) {
        this.physicPersonRepository.deleteById(id);
    }

}
