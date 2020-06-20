package com.github.hotire.reactor.utils.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ReactiveCacheAspectTest {

    @Test
    void generateKey() {
        // given
        final String expected = "1:1:1";
        final Object[] objects = new Object[]{"1", 1, 1L};
        final ReactiveCacheAspect aspect = new ReactiveCacheAspect(mock(ReactiveCacheManager.class));

        // when
        final String result = aspect.generateKey(objects);

        // then
        assertThat(result).isEqualTo(expected);
    }
}