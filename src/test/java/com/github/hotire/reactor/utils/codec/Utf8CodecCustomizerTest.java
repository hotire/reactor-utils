package com.github.hotire.reactor.utils.codec;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.HttpMessageWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class Utf8CodecCustomizerTest {

    @Test
    void customize() {
        // given
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<HttpMessageWriter<?>> argumentCaptor = ArgumentCaptor.forClass(HttpMessageWriter.class);
        final CodecConfigurer codecConfigurer = mock(CodecConfigurer.class);
        final HttpMessageWriter<?> writer = mock(HttpMessageWriter.class);
        final CodecConfigurer.CustomCodecs customCodecs = mock(CodecConfigurer.CustomCodecs.class);
        final Utf8CodecCustomizer utf8CodecCustomize = new Utf8CodecCustomizer();

        // when
        when(codecConfigurer.getWriters()).thenReturn(Lists.newArrayList(writer));
        when(codecConfigurer.customCodecs()).thenReturn(customCodecs);
        utf8CodecCustomize.customize(codecConfigurer);

        // then
        verify(customCodecs, times(1)).register(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isInstanceOf(Utf8HttpMessageWriterDecorator.class);
    }
}