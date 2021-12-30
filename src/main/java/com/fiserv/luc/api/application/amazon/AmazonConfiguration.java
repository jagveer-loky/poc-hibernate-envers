package com.fiserv.luc.api.application.amazon;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfiguration {

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .build();
    }

}
