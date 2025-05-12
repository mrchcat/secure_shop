package com.github.mrchcat.intershop.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.PageWrapper;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Configuration
public class RedisConfig {
//    @Bean
//    public RedisCacheManagerBuilderCustomizer ItemDtoCacheCustomizer() {
//        return builder -> {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//            PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
//                    .allowIfBaseType(Objects.class)
//                    .build();
//            mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//            var serializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);
//
//            builder.withCacheConfiguration("name", RedisCacheConfiguration.defaultCacheConfig()
//                    .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
//                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
//            );
//        };
//    }

    @Bean
    public RedisCacheManagerBuilderCustomizer itemDtoCacheCustomizer() {
        return builder -> builder.withCacheConfiguration(
                "itemDto",
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair
                                .fromSerializer(new Jackson2JsonRedisSerializer<>(ItemDto.class))
                        )
        );
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer PageCacheCustomizer() {
        return builder -> builder.withCacheConfiguration(
                "itemDtoPage",
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair
                                .fromSerializer(new Jackson2JsonRedisSerializer<>(PageWrapper.class))
                        )
        );
    }

}
