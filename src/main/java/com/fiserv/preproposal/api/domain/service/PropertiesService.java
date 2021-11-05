package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.application.exceptions.NsaNotFoundException;
import com.fiserv.preproposal.api.domain.entity.EProperties;
import com.fiserv.preproposal.api.domain.repository.PropertiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertiesService {

    private static final Long SEQUENCE_NSA = 1L;

    private final PropertiesRepository propertiesRepository;

    public void setNsa(Long value) {
        Optional<EProperties> optProperties = propertiesRepository.findById(SEQUENCE_NSA);
        if(optProperties.isPresent()) {
            EProperties properties = optProperties.get();
            properties.setValue(value);
            propertiesRepository.save(properties);
        } else {
           throw new NsaNotFoundException();
        }
    }

}
