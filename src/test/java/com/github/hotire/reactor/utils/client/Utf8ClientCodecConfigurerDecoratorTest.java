package com.github.hotire.reactor.utils.client;


import com.github.hotire.reactor.utils.codec.Utf8HttpMessageWriterDecorator;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.HttpMessageWriter;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Utf8ClientCodecConfigurerDecoratorTest {

    @Test
    void getWriters() {
        // given
        final ClientCodecConfigurer writer = mock(ClientCodecConfigurer.class);
        final Utf8ClientCodecConfigurerDecorator decorator = new Utf8ClientCodecConfigurerDecorator(writer);

        // when
        // noinspection unchecked
        when(writer.getWriters()).thenReturn(Lists.newArrayList(mock(HttpMessageWriter.class)));
        final List<HttpMessageWriter<?>> result = decorator.getWriters();

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isInstanceOf(Utf8HttpMessageWriterDecorator.class);
    }
}