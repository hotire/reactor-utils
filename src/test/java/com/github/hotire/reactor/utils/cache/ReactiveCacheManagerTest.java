package com.github.hotire.reactor.utils.cache;

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
        final ReactiveCacheManager manager = new ReactiveCacheManager(cacheManager);

        // when
        when(cacheManager.getCache(cacheName)).thenReturn(cache);
        final Mono<Object> result = manager.cacheMono(cacheName, key, Object.class);

        // then
        StepVerifier.create(result)
                    .expectNext(data)
                    .verifyComplete();
    }

}