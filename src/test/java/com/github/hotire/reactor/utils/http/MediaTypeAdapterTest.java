package com.github.hotire.reactor.utils.http;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

class MediaTypeAdapterTest {

    @Test
    void of() {
        // given  when
        final MediaTypeAdapter adapter = MediaTypeAdapter.of(mock(MediaType.class));

        // then
        assertThat(adapter).isNotNull();
    }
}