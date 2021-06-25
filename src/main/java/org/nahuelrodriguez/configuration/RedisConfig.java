package org.nahuelrodriguez.configuration;

import org.nahuelrodriguez.property.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {
    private final RedisProperties properties;

    public RedisConfig(RedisProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final var redisConfig = new RedisStandaloneConfiguration(properties.getHost());
        redisConfig.setPassword(RedisPassword.of(properties.getPassword()));
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
