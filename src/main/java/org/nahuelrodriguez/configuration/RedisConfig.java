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
    @Value("${redis.host}")
    private String url;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.password}")
    private String password;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(url, port);
        redisConfig.setPassword(RedisPassword.of(password));
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
