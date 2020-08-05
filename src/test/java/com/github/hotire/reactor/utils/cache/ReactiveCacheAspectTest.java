package com.github.hotire.reactor.utils.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReactiveCacheAspectTest {


    private static Stream<Arguments> provideCache() {
        return Stream.of(
                Arguments.of(Mono.class, 1, 0),
                Arguments.of(Flux.class, 0 ,1),
                Arguments.of(Object.class, 0, 0)
        );
    }

    @MethodSource("provideCache")
    @ParameterizedTest
    void cache(final Class<?> returnType, final int monoCacheCount, final int fluxCacheCount) throws Throwable {
        // given
        final ReactiveCacheManager cacheManager = mock(ReactiveCacheManager.class);
        final ReactiveCacheAspect aspect = new ReactiveCacheAspect(cacheManager) {
            @Override
            protected String parseSpel(final String[] params, final Object[] arguments, final String spel) {
                return "key";
            }
        };
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final ReactiveCacheable reactiveCacheable = mock(ReactiveCacheable.class);
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = ReactiveCacheAspectTest.class.getMethod("method");


        // when
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getReturnType()).thenReturn(returnType);
        when(methodSignature.getMethod()).thenReturn(method);
        aspect.cache(joinPoint, reactiveCacheable);

        // then
        verify(cacheManager, times(monoCacheCount)).cacheMono(eq(reactiveCacheable.name()), any(), any(), any());
        verify(cacheManager, times(fluxCacheCount)).cacheFlux(eq(reactiveCacheable.name()), any(), any(), any());
    }

    @Test
    void retriever() throws Throwable {
        // given
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final ReactiveCacheAspect aspect = new ReactiveCacheAspect(mock(ReactiveCacheManager.class));

        // when
        when(joinPoint.proceed()).thenThrow(new Exception());
        final Supplier<?> result = aspect.retriever(joinPoint);

        // then
        Assertions.assertThrows(RuntimeException.class, result::get);
    }

    @Test
    void parseSpel() {
        // given
        final String[] parmas = new String[]{"id"};
        final Object[] arguments = new Object[]{"a"};
        final String spel = "#id";
        final ReactiveCacheAspect aspect = new ReactiveCacheAspect(mock(ReactiveCacheManager.class));

        // when
        final String result = aspect.parseSpel(parmas, arguments, spel);

        // then
        assertThat(result).isEqualTo(arguments[0]);
    }

    public Mono<String> method() {
        return Mono.empty();
    }
}