package com.github.hotire.reactor.utils.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ClientCodecConfigurerDecoratorTest {

    @Test
    void getDelegate() {
        // given
        final ClientCodecConfigurer expected = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(expected);

        // when
        final ClientCodecConfigurer result = decorator.getDelegate();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void defaultCodecs() {
        // given
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        when(clientCodecConfigurer.defaultCodecs()).thenReturn(mock(ClientCodecConfigurer.ClientDefaultCodecs.class));
        decorator.defaultCodecs();

        // then
        verify(clientCodecConfigurer, times(1)).defaultCodecs();
    }

    @Test
    void customCodecs() {
        // given
        final CodecConfigurer.CustomCodecs expected = mock(ClientCodecConfigurer.CustomCodecs.class);
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        when(clientCodecConfigurer.customCodecs()).thenReturn(expected);
        final CodecConfigurer.CustomCodecs result = decorator.customCodecs();

        // then
        assertThat(result).isEqualTo(expected);
        verify(clientCodecConfigurer, times(1)).customCodecs();
    }

    @Test
    void registerDefaults() {
        // given
        final boolean registerDefaults = true;
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        decorator.registerDefaults(registerDefaults);

        // then
        verify(clientCodecConfigurer, times(1)).registerDefaults(registerDefaults);
    }

    @Test
    void getReaders() {
        // given
        @SuppressWarnings("unchecked")
        final List<HttpMessageReader<?>> expected = mock(List.class);
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        when(clientCodecConfigurer.getReaders()).thenReturn(expected);
        final List<HttpMessageReader<?>> result = decorator.getReaders();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getWriters() {
        // given
        @SuppressWarnings("unchecked")
        final List<HttpMessageWriter<?>> expected = mock(List.class);
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        when(clientCodecConfigurer.getWriters()).thenReturn(expected);
        final List<HttpMessageWriter<?>> result = decorator.getWriters();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testClone() {
        // given
        final ClientCodecConfigurer expected = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        when(clientCodecConfigurer.clone()).thenReturn(expected);
        final ClientCodecConfigurer result = decorator.clone();

        // then
        assertThat(result).isEqualTo(expected);
    }
}