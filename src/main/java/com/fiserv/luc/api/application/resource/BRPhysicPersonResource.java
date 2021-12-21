package com.fiserv.luc.api.application.resource;

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.service.brazil.BRPhysicPersonService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

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
     * @param filters     String
     * @param pageRequest PageRequest
     * @return Page<BRPhysicPerson>
     */
    @GetMapping
    public Page<BRPhysicPerson> listByFilters(final String filters, final PageRequest pageRequest) {
        return physicPersonService.listByFilters(filters, pageRequest);
    }
}
