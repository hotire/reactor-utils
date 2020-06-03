package com.github.hotire.reactor.utils;

import com.github.hotire.reactor.utils.cache.ReactiveCacheAspect;
import com.github.hotire.reactor.utils.cache.ReactiveCacheManager;
import com.github.hotire.reactor.utils.cache.ReactiveCacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Import(ReactiveCacheAspect.class)
@Profile("local")
@Configuration
public class LocalTestConfig {

    @Bean
    public CacheManager localSimpleCacheManager() {
        return new SimpleCacheManager() {
            @Override
            protected Cache getMissingCache(String name) {
                return new ConcurrentMapCache(name);
            }
        };
    }

    @Bean
    public ReactiveCacheManager reactiveCacheManager() {
        return new ReactiveCacheManager(localSimpleCacheManager());
    }

    @RestController
    public static class HelloController {

        @Autowired
        private HelloService helloService;

        @Autowired
        private ReactiveCacheManager reactiveCacheManager;

        @GetMapping("/test/{id}")
        public Mono<String> test(@PathVariable final String id) {
            return helloService.hello(id);
        }

        @GetMapping("/test/flux/{id}")
        public Flux<String> testFlux(@PathVariable final String id) {
            return helloService.helloFlux(id);
        }


        @GetMapping("/cache/{id}")
        public Mono<String> cache(@PathVariable final String id) {
            return reactiveCacheManager.cacheMono("cache", id, () ->  helloService.hello(id), String.class);
        }

        @GetMapping("/cache/flux/{id}")
        public Flux<String> cacheFlux(@PathVariable final String id) {
            return reactiveCacheManager.cacheFlux("cache", id, () ->  helloService.helloFlux(id), String.class);
        }

        @GetMapping("/hello/{id}")
        public String hello(@PathVariable final String id) {
            return helloService.hello2(id);
        }
    }

    @Service
    public static class HelloService {
        @ReactiveCacheable(name = "hello", key = "'id:' + #id")
        public Mono<String> hello(final String id) {
            return Mono.create(monoSink ->  {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("hello");
                monoSink.success("hello");
            });
        }

//        @ReactiveCacheable(name = "hello", key = "'id:' + #id")
        public Flux<String> helloFlux(final String id) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("stop");
            return Flux.just("1", "2", "3");
        }

        @ReactiveCacheable(name = "hello", key = "'id:' + #id")
        public String hello2(String id) {
            return "hello";
        }
    }
}
