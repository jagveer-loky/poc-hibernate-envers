package com.fiserv.preproposal.api.application.schedulling;

import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobRunrConfig {
    @Bean
    public StorageProvider provider(JobMapper jobMapper) {
        InMemoryStorageProvider provider = new InMemoryStorageProvider();
        provider.setJobMapper(jobMapper);

        return provider;
    }
}