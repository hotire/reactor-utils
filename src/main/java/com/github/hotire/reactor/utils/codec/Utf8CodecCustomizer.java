package com.github.hotire.reactor.utils.codec;

import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.http.codec.CodecConfigurer;

public class Utf8CodecCustomizer implements CodecCustomizer {
    @Override
    public void customize(final CodecConfigurer configurer) {
        configurer.getWriters().forEach(writer -> configurer.customCodecs().register(new Utf8HttpMessageWriterDecorator<>(writer)));
    }
}
