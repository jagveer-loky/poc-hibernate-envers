package com.fiserv.luc.api.application.resource;

import com.fiserv.luc.api.application.aspect.exceptions.NotFoundException;
import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.service.brazil.BRPhysicPersonService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/br/physic-persons")
public class BRPhysicPersonResource {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     *
     */
    private final BRPhysicPersonService physicPersonService;

    /**
     * @param filters  String
     * @param pageable Pageable
     * @return Page<BRPhysicPerson>
     */
    @GetMapping
    public Page<BRPhysicPerson> listByFilters(final String filters, final Pageable pageable) {
        return physicPersonService.listByFilters(filters, pageable);
    }

    /**
     * @param id Long
     * @return BRPhysicPerson
     */
    @GetMapping("{id}")
    public BRPhysicPerson findById(final @PathVariable Long id) {
        return physicPersonService.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * @param id Long
     * @return BRPhysicPerson
     */
    @GetMapping("{id}/revisions")
    public List<Revision<Long, BRPhysicPerson>> findRevisionsById(final @PathVariable Long id) {
        return physicPersonService.findRevisionsById(id);
    }
}
