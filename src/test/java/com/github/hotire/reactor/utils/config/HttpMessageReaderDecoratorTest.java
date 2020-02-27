package com.github.hotire.reactor.utils.config;

import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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
        @SuppressWarnings("unchecked")
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void readMono() {
        // given
        final Mono expected = mock(Mono.class);
        final HttpMessageReader<?> reader = mock(HttpMessageReader.class);
        final HttpMessageReaderDecorator<?> decorator = new HttpMessageReaderDecorator<>(reader);

        // when
        when(reader.readMono(any(), any(), anyMap())).thenReturn(expected);
        final Mono<?> result = decorator.readMono(mock(ResolvableType.class), mock(ReactiveHttpInputMessage.class), mock(Map.class));

        // then
        assertThat(result).isEqualTo(expected);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void read() {
        // given
        final Flux expected = mock(Flux.class);
        final HttpMessageReader<?> reader = mock(HttpMessageReader.class);
        final HttpMessageReaderDecorator<?> decorator = new HttpMessageReaderDecorator<>(reader);

        // when
        when(reader.read(any(), any(), anyMap())).thenReturn(expected);
        final Flux<?> result = decorator.read(mock(ResolvableType.class), mock(ReactiveHttpInputMessage.class), mock(Map.class));

        // then
        assertThat(result).isEqualTo(expected);
    }
}