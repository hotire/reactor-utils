package com.github.hotire.reactor.utils.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.CodecConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ClientCodecConfigurerDecoratorTest {

    @Test
    void getDelegate() {
        // given
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        final ClientCodecConfigurer result = decorator.getDelegate();

        // then
        assertThat(result).isEqualTo(clientCodecConfigurer);
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
        final CodecConfigurer.CustomCodecs customCodecs = mock(ClientCodecConfigurer.CustomCodecs.class);
        final ClientCodecConfigurer clientCodecConfigurer = mock(ClientCodecConfigurer.class);
        final ClientCodecConfigurerDecorator decorator = new ClientCodecConfigurerDecorator(clientCodecConfigurer);

        // when
        when(clientCodecConfigurer.customCodecs()).thenReturn(customCodecs);
        final CodecConfigurer.CustomCodecs result = decorator.customCodecs();

        // then
        assertThat(result).isEqualTo(customCodecs);
        verify(clientCodecConfigurer, times(1)).customCodecs();
    }

    @Test
    void registerDefaults() {
    }

    @Test
    void getReaders() {
    }

    @Test
    void getWriters() {
    }

    @Test
    void testClone() {
    }
}