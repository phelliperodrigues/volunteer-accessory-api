package dev.phelliperodrigues.volunteerAccessoryApi.config.infrastructure;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@EnableCaching
@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration defaultCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}