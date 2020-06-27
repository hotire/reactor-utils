package com.github.hotire.reactor.utils.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReactiveCacheManagerTest {

    @Test
    void cacheMono() {
        // given
        final String cacheName = "";
        final String key = "";
        final Object data = new Object();
        final CacheManager cacheManager = mock(CacheManager.class);
        final Cache cache = new ConcurrentMapCache(cacheName);
        cache.put(key, data);
        final ReactiveCacheManager manager = new ReactiveCacheManager(cacheManager,  mock(ObjectMapper.class));

        // when
        when(cacheManager.getCache(cacheName)).thenReturn(cache);
        final Mono<Object> result = manager.cacheMono(cacheName, key, Object.class);

        // then
        StepVerifier.create(result)
                    .expectNext(data)
                    .verifyComplete();
    }

    @Test
    void cast() {
        // given
        final Object expected = mock(Object.class);
        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final ReactiveCacheManager manager = new ReactiveCacheManager(mock(CacheManager.class), objectMapper);
        final Object target = mock(Object.class);
        final TypeReference<Object> typeReference = mock(TypeReference.class);

        // when
        when(objectMapper.convertValue(target, typeReference)).thenReturn(expected);
        final Object result = manager.cast(target, typeReference);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }
}