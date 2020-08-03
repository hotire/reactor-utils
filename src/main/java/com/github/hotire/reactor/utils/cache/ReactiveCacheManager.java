package com.github.hotire.reactor.utils.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    public ReactiveCacheManager(final CacheManager cacheManager, final ObjectMapper objectMapper) {
        this.cacheManager = cacheManager;
        this.objectMapper = objectMapper;
    }

    public <T> Mono<T> cacheMono(final String cacheName, final Object key, final Supplier<Mono<T>> retriever, final Class<T> classType) {
        return CacheMono
                .lookup(k -> Mono.justOrEmpty(getCache(cacheName).get(k, classType)).map(Signal::next), key)
                .onCacheMissResume(Mono.defer(retriever))
                .andWriteWith((k, signal) -> Mono.just(signal)
                                                 .filter(it -> !it.isOnError())
                                                 .doOnNext(it -> getCache(cacheName).put(k, it.get()))
                                                 .then());
    }

    public <T> Mono<T> cacheMono(final String cacheName, final Object key, final Class<T> classType) {
        return cacheMono(cacheName, key, Mono::empty, classType);
    }

    public <T> Flux<T> cacheFlux(final String cacheName, final Object key, final Supplier<Flux<T>> retriever, final Class<T> classType) {
        return CacheFlux
                .lookup(k -> Mono.justOrEmpty(getCache(cacheName).get(k, List.class))
                                 .map(list -> cast(list, new TypeReference<List<Signal<T>>>() {}))
                                 .map(signals -> signals), key)
                .onCacheMissResume(Flux.defer(retriever))
                .andWriteWith((k, signalList) -> Mono.just(signalList)
                                                     .doOnNext(list -> getCache(cacheName).put(k, list))
                                                     .then());
    }

    public <T> Flux<T> cacheFlux(final String cacheName, final Object key, final Class<T> classType) {
        return cacheFlux(cacheName, key, Flux::empty, classType);
    }

    public Mono<Void> evict(final String cacheName, final Object key) {
        return Mono.fromRunnable(() -> getCache(cacheName).evict(key));
    }

    protected Cache getCache(final String name) {
        return Objects.requireNonNull(cacheManager.getCache(name));
    }

    protected <T> T cast(final Object target, final TypeReference<T> typeReference) {
        return objectMapper.convertValue(target, typeReference);
    }
}
