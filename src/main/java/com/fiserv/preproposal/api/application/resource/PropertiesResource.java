package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.service.PropertiesService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestScope
@RequestMapping(value = "/preproposal/properties", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PropertiesResource {

    private static final Logger LOG = LogManager.getLogger(PropertiesResource.class);

    private final PropertiesService propertiesService;

    @PostMapping("/nsa/{nsaValue}")
    public ResponseEntity<Void> setNsa(@PathVariable Long nsaValue) {
        propertiesService.setNsa(nsaValue);
        return ResponseEntity.ok().build();
    }
}
