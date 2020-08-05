package com.github.hotire.reactor.utils.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReactiveCacheAspectTest {

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
}