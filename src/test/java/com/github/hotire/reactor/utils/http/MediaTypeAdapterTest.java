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

    @Test
    void getMediaType() {
        // given
        final MediaType mediaType = mock(MediaType.class);
        final MediaTypeAdapter adapter = MediaTypeAdapter.of(mediaType);

        // when
        final MediaType result = adapter.getMediaType();

        // then
        assertThat(result).isNotNull();
    }
}