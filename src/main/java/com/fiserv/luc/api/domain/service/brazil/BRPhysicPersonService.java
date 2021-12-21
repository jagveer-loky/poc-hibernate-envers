package com.fiserv.luc.api.domain.service.brazil;

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.repository.brazil.BRPhysicPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
