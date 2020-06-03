package com.github.hotire.reactor.utils.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import reactor.cache.CacheFlux;
import reactor.cache.CacheMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ReactiveCacheManager {

    private final CacheManager cacheManager;

    public ReactiveCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <T> Mono<T> cacheMono(final String cacheName, final Object key, final Supplier<Mono<T>> retriever, final Class<T> classType) {
        return CacheMono
                .lookup(k -> Mono.justOrEmpty(getCache(cacheName).get(k, classType)).map(Signal::next), key)
                .onCacheMissResume(Mono.defer(retriever))
                .andWriteWith((k, signal) -> Mono.just(signal)
                                                 .filter(it -> !it.isOnError())
                                                 .doOnNext(it -> getCache(cacheName).put(k, it.get()))
                                                 .thenEmpty(Mono.empty()));
    }

    public <T> Mono<T> cacheMono(final String cacheName, final Object key, final Class<T> classType) {
        return cacheMono(cacheName, key, () -> null, classType);
    }

    public <T> Flux<T> cacheFlux(final String cacheName, final Object key, final Supplier<Flux<T>> retriever, final Class<T> classType) {
        return CacheFlux
                .lookup(k -> Mono.justOrEmpty(getCache(cacheName).get(k, List.class))
                                .map(list -> (List<Signal<T>>) list), key)
                .onCacheMissResume(Flux.defer(retriever))
                .andWriteWith((k, signalList) -> Mono.just(signalList)
                                                     .doOnNext(list -> getCache(cacheName).put(k, list))
                                                     .then());
    }

    protected Cache getCache(final String name) {
        return Objects.requireNonNull(cacheManager.getCache(name));
    }
}
