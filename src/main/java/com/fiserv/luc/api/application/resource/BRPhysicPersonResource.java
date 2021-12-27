package com.fiserv.luc.api.application.resource;

import com.fiserv.luc.api.application.aspect.exceptions.NotFoundException;
import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.service.brazil.BRPhysicPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/br/physic-persons")
public class BRPhysicPersonResource {

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
     * @param physicPerson BRPhysicPerson
     * @return BRPhysicPerson
     */
    @PostMapping
    public BRPhysicPerson save(final @RequestBody BRPhysicPerson physicPerson) {
        return physicPersonService.save(physicPerson);
    }

    /**
     * @param physicPerson BRPhysicPerson
     * @return BRPhysicPerson
     */
    @PutMapping("{id}")
    public BRPhysicPerson save(final @PathVariable Long id, final @RequestBody BRPhysicPerson physicPerson) {
        physicPerson.setId(id);
        return physicPersonService.save(physicPerson);
    }

    /**
     * @param id Long
     */
    @DeleteMapping("{id}")
    public void deleteById(final @PathVariable Long id) {
        physicPersonService.deleteById(id);
    }

    /**
     * @param id Long
     */
    @GetMapping("{id}/revisions")
    public List<Object> findRevisionsById(final @PathVariable Long id) {
         return physicPersonService.findRevisionsById(id);
    }


    /**
     *
     * @return
     */
    @GetMapping("revisions")
    public List<Object> findRevisions() {
        return physicPersonService.findRevisions();
    }
}
