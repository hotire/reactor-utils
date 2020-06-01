package com.github.hotire.reactor.utils.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Objects;
import java.util.function.Supplier;

public class ReactiveCacheManager {

    private final CacheManager cacheManager;

    public ReactiveCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <T> Mono<T> findCachedMono(final String cacheName, final Object key, final Supplier<Mono<T>> retriever, final Class<T> classType) {
        return CacheMono
                .lookup(k -> Mono.justOrEmpty(getCache(cacheName).get(k, classType)).map(Signal::next), key)
                .onCacheMissResume(Mono.defer(retriever))
                .andWriteWith((k, signal) -> Mono.just(signal)
                                                 .filter(it -> !it.isOnError())
                                                 .doOnNext(it -> getCache(cacheName).put(k, it.get()))
                                                 .thenEmpty(Mono.empty()));
    }

    public <T> Mono<T> findCachedMono(final String cacheName, final Object key, final Class<T> classType) {
        return findCachedMono(cacheName, key, () -> null, classType);
    }

    protected Cache getCache(final String name) {
        return Objects.requireNonNull(cacheManager.getCache(name));
    }
}
