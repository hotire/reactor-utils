package com.github.hotire.reactor.utils.cache;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class ReactiveCacheManager {

    private final CacheManager cacheManager;

    public <T> Mono<T> cacheMono(final String cacheName, final Object key, final Supplier<Mono<T>> retriever, final Class<T> classType) {
        return CacheMono
                .lookup(k -> Mono.justOrEmpty(getCache(cacheName).get(k, classType)).map(Signal::next), key)
                .onCacheMissResume(Mono.defer(retriever))
                .andWriteWith((k, signal) -> Mono.just(signal)
                                                 .filter(it -> !it.isOnError())
                                                 .doOnNext(it -> getCache(cacheName).put(k, it.get()))
                                                 .then());
    }

    @SuppressWarnings("unchecked")
    public <T> Flux<T> cacheFlux(final String cacheName, final Object key, final Supplier<Flux<T>> retriever, final Class<T> classType) {
        return CacheFlux
                .lookup(k -> Mono.justOrEmpty(getCache(cacheName).get(k, List.class))
                                 .map(list -> (List<T>) list)
                                 .flatMap(list -> Flux.fromIterable(list)
                                                        .materialize()
                                                        .collectList())
                        , key)
                .onCacheMissResume(Flux.defer(retriever))
                .andWriteWith((k, signalList) -> Flux.fromIterable(signalList)
                                                     .dematerialize()
                                                     .collectList()
                                                     .doOnNext(list -> getCache(cacheName).put(k, list))
                                                     .then());
    }

    public Mono<Void> evict(final String cacheName, final Object key) {
        return Mono.fromRunnable(() -> getCache(cacheName).evict(key));
    }

    protected Cache getCache(final String name) {
        return Objects.requireNonNull(cacheManager.getCache(name));
    }

}
