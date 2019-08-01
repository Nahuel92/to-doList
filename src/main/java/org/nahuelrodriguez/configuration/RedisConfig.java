package org.nahuelrodriguez.configuration;

import org.springframework.beans.factory.annotation.Value;
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
    private final String url;
    private final String password;

    public RedisConfig(@Value("${spring.redis.host}") final String url, @Value("${spring.redis.password}") final String password) {
        this.url = url;
        this.password = password;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final var redisConfig = new RedisStandaloneConfiguration(url);
        redisConfig.setPassword(RedisPassword.of(password));
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
