package com.github.hotire.reactor.utils.cache;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class ReactiveCacheManagerTest {

    @Test
    void cacheMono() {
        // given
        final String cacheName = "";
        final String key = "";
        final CacheManager cacheManager = mock(CacheManager.class);
        final Cache cache = new ConcurrentMapCache(cacheName);
        final ReactiveCacheManager manager = new ReactiveCacheManager(cacheManager);

        // when
        when(cacheManager.getCache(cacheName)).thenReturn(cache);
        final Mono<Object> result = manager.cacheMono(cacheName, key, Mono::empty, Object.class);

        // then
        StepVerifier.create(result)
                    .verifyComplete();
    }

    @Test
    void cacheMonoHit() {
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
        final Mono<Object> result = manager.cacheMono(cacheName, key, Mono::empty, Object.class);

        // then
        StepVerifier.create(result)
                    .expectNext(data)
                    .verifyComplete();
    }

    @Test
    void cacheFlux() {
        // given
        final String cacheName = "";
        final String key = "";
        final CacheManager cacheManager = mock(CacheManager.class);
        final Cache cache = new ConcurrentMapCache(cacheName);
        final ReactiveCacheManager manager = new ReactiveCacheManager(cacheManager);

        // when
        when(cacheManager.getCache(cacheName)).thenReturn(cache);
        final Flux<Object> result = manager.cacheFlux(cacheName, key, Flux::empty, Object.class);

        // then
        StepVerifier.create(result)
                    .verifyComplete();
    }

    @Test
    void cacheFluxHit() {
        // given
        final String cacheName = "";
        final String key = "";
        final List<?> data = Lists.newArrayList(new Object());
        final CacheManager cacheManager = mock(CacheManager.class);
        final Cache cache = new ConcurrentMapCache(cacheName);
        cache.put(key, data);
        final ReactiveCacheManager manager = new ReactiveCacheManager(cacheManager);

        // when
        when(cacheManager.getCache(cacheName)).thenReturn(cache);
        final Flux<Object> result = manager.cacheFlux(cacheName, key, Flux::empty, Object.class);

        // then
        StepVerifier.create(result)
                    .expectNextCount(1L)
                    .verifyComplete();
    }

    @Test
    void evict() {
        // given
        final String cacheName = "";
        final String key = "";
        final CacheManager cacheManager = mock(CacheManager.class);
        final Cache cache = mock(Cache.class);
        final ReactiveCacheManager manager = new ReactiveCacheManager(cacheManager);

        // when
        when(cacheManager.getCache(cacheName)).thenReturn(cache);
        final Mono<Void> result = manager.evict(cacheName, key);

        // then
        StepVerifier.create(result)
                    .then(() -> verify(cache, times(1)).evict(key))
                    .verifyComplete();
    }

}