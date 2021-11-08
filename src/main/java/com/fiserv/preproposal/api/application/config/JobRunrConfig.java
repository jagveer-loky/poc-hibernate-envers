package com.fiserv.preproposal.api.application.config;

import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobRunrConfig {
    /**
     * @param jobMapper JobMapper
     * @return StorageProvider
     */
    @Bean
    public StorageProvider provider(final JobMapper jobMapper) {
        final InMemoryStorageProvider provider = new InMemoryStorageProvider();
        provider.setJobMapper(jobMapper);

        return provider;
    }
}