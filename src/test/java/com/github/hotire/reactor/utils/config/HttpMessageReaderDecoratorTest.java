package com.github.hotire.reactor.utils.config;

import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

class HttpMessageReaderDecoratorTest {

    @Test
    void getDelegate() {
        // given
        final HttpMessageReader<?> reader = mock(HttpMessageReader.class);
        final HttpMessageReaderDecorator<?> decorator = new HttpMessageReaderDecorator<>(reader);

        // when
        final HttpMessageReader<?> result = decorator.getDelegate();

        // then
        assertThat(result).isEqualTo(reader);
    }

    @Test
    void getReadableMediaTypes() {
        // given
        final List<MediaType> mediaTypes = mock(List.class);
        final HttpMessageReader<?> reader = mock(HttpMessageReader.class);
        final HttpMessageReaderDecorator<?> decorator = new HttpMessageReaderDecorator<>(reader);

        // when
        when(reader.getReadableMediaTypes()).thenReturn(mediaTypes);
        final List<MediaType> result = decorator.getReadableMediaTypes();

        // then
        assertThat(result).isEqualTo(mediaTypes);
    }

    @Test
    void canRead() {
        // given
        final boolean expected = true;
        final HttpMessageReader<?> reader = mock(HttpMessageReader.class);
        final HttpMessageReaderDecorator<?> decorator = new HttpMessageReaderDecorator<>(reader);

        // when
        when(reader.canRead(any(), any())).thenReturn(expected);
        final boolean result = decorator.canRead(mock(ResolvableType.class), mock(MediaType.class));

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void readMono() {
    }

    @Test
    void read() {
    }
}