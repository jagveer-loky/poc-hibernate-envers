package com.fiserv.luc.api.application.config.common

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
open class CommonBeansConfiguration {

//    /**
//     * @return ObjectMapper
//     */
//    @Bean
//    open fun objectMapper(): ObjectMapper {
//        val objectMapper = builder.createXmlMapper(false).build<ObjectMapper>()
//        //        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
//        return objectMapper
//    }


    /**
     * @param builder Jackson2ObjectMapperBuilder
     * @return ObjectMapper
     */
    @Bean
    open fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper? {
        val objectMapper = builder.createXmlMapper(false).build<ObjectMapper>()
        //        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return objectMapper
    }
}