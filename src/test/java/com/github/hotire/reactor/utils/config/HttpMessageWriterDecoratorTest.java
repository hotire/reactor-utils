package com.github.hotire.reactor.utils.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HttpMessageWriterDecoratorTest {

    @Test
    void getDelegate() {
        // given
        final HttpMessageWriter<?> reader = mock(HttpMessageWriter.class);
        final HttpMessageWriterDecorator<?> decorator = new HttpMessageWriterDecorator<>(reader);

        // when
        final HttpMessageWriter<?> result = decorator.getDelegate();

        // then
        assertThat(result).isEqualTo(reader);
    }

    @Test
    void getWritableMediaTypes() {
        // given
        @SuppressWarnings("unchecked")
        final List<MediaType> expected = mock(List.class);
        final HttpMessageWriter<?> reader = mock(HttpMessageWriter.class);
        final HttpMessageWriterDecorator<?> decorator = new HttpMessageWriterDecorator<>(reader);

        // when
        when(reader.getWritableMediaTypes()).thenReturn(expected);
        final List<MediaType> result = decorator.getWritableMediaTypes();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void canWrite() {
    }

    @Test
    void write() {
    }
}