package com.fiserv.preproposal.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

/**
 * @author achetype-fiserv
 */

@SpringBootApplication
public class ApplicationConfig extends SpringBootServletInitializer {
	
	 @Override
     protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
         return application.sources(ApplicationConfig.class);
     }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
    
    @Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("/git/git.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }
}