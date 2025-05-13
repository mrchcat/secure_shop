package com.github.mrchcat.intershop.config;

import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.PageWrapper;
import com.github.mrchcat.intershop.item.service.ItemService;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManagerBuilderCustomizer cacheCustomizer() {
        return builder -> builder
                .withCacheConfiguration(ItemService.ITEM_DTO, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair
                                .fromSerializer(new Jackson2JsonRedisSerializer<>(ItemDto.class))
                        )
                )
                .withCacheConfiguration(
                        ItemService.PAGE_ITEM_DTO, RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(new Jackson2JsonRedisSerializer<>(PageWrapper.class))
                                )
                );
    }

}
