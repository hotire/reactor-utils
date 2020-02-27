package com.github.hotire.reactor.utils.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.codec.HttpMessageReader;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

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
    }

    @Test
    void canRead() {
    }

    @Test
    void readMono() {
    }

    @Test
    void read() {
    }
}