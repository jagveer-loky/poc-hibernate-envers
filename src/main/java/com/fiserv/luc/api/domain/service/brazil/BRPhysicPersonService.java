package com.fiserv.luc.api.domain.service.brazil;

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.repository.brazil.BRPhysicPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BRPhysicPersonService {

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
     * @param id Long
     * @return Revisions<Long, BRPhysicPerson>
     */
    public List<Revision<Long, BRPhysicPerson>> findRevisionsById(final Long id) {
        return this.physicPersonRepository.findRevisions(id).getContent();
    }
}
