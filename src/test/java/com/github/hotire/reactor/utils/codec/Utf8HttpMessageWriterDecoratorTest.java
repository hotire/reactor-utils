package com.github.hotire.reactor.utils.codec;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

class Utf8HttpMessageWriterDecoratorTest {

    @SuppressWarnings({"unchecked", "deprecation"})
    @Test
    void write_mediaType() {
        // given
        final ArgumentCaptor<MediaType> argumentCaptor = ArgumentCaptor.forClass(MediaType.class);
        final ReactiveHttpOutputMessage reactiveHttpOutputMessage = mock(ReactiveHttpOutputMessage.class);
        final HttpHeaders httpHeaders = mock(HttpHeaders.class);
        final HttpMessageWriter<?> writer = mock(HttpMessageWriter.class);
        final Utf8HttpMessageWriterDecorator<?> utf8HttpMessageWriterDecorator = new Utf8HttpMessageWriterDecorator<>(writer);

        // when
        when(reactiveHttpOutputMessage.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getContentType()).thenReturn(MediaType.APPLICATION_XML);
        utf8HttpMessageWriterDecorator.write(mock(Publisher.class), mock(ResolvableType.class), MediaType.APPLICATION_JSON, reactiveHttpOutputMessage, mock(Map.class));

        // then
        verify(writer, times(1)).write(any(), any(), argumentCaptor.capture(), eq(reactiveHttpOutputMessage), anyMap());
        assertThat(argumentCaptor.getValue().toString()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @Test
    void write_reactiveHttpOutputMessage() {
        // given
        final ArgumentCaptor<MediaType> argumentCaptor = ArgumentCaptor.forClass(MediaType.class);
        final ReactiveHttpOutputMessage reactiveHttpOutputMessage = mock(ReactiveHttpOutputMessage.class);
        final HttpHeaders httpHeaders = mock(HttpHeaders.class);
        final HttpMessageWriter<?> writer = mock(HttpMessageWriter.class);
        final Utf8HttpMessageWriterDecorator<?> utf8HttpMessageWriterDecorator = new Utf8HttpMessageWriterDecorator<>(writer);

        // when
        when(reactiveHttpOutputMessage.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getContentType()).thenReturn(MediaType.APPLICATION_JSON);
        utf8HttpMessageWriterDecorator.write(mock(Publisher.class), mock(ResolvableType.class), MediaType.APPLICATION_XML, reactiveHttpOutputMessage, mock(Map.class));

        // then
        verify(httpHeaders, times(1)).setContentType(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().toString()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
    }

}