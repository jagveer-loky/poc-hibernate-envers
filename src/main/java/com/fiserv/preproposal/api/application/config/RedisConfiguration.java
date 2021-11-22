package com.fiserv.preproposal.api.application.config;

import org.redisson.config.Config;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
//@PropertySource(name="application", value="classpath:application-uat.properties")
public class RedisConfiguration {

    @Value("${spring.cache.type}")
    private String type;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, Integer.parseInt(port));
        redisStandaloneConfiguration.setPassword(password);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public HashOperations hashConfig() {
        final RedisTemplate<String, String> template = redisTemplate();
        return template.opsForHash();
    }

    @Bean
    public RedissonClient createRedisson() {
        final Config config = new Config();
        config.useSingleServer().setAddress(type+"://"+host+":"+port).setPassword(password);
        return Redisson.create(config);
    }

}
