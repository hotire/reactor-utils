package com.github.hotire.reactor.utils.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReactiveCacheManagerTest {

    @Test
    void cast() {
        // given
        final Object expected = mock(Object.class);
        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final ReactiveCacheManager manager = new ReactiveCacheManager(mock(CacheManager.class), objectMapper);
        final Object target = mock(Object.class);
        final TypeReference<Object> typeReference = mock(TypeReference.class);

        // when
        when(objectMapper.convertValue(target, typeReference)).thenReturn(expected);
        final Object result = manager.cast(target, typeReference);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }
}