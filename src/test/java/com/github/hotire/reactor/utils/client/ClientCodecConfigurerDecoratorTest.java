package com.github.hotire.reactor.utils.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.codec.ClientCodecConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
    }

    @Test
    void customCodecs() {
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