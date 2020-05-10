package com.github.hotire.reactor.utils.bind.converter.spring;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;

public class InstantConverter implements Converter<String, Instant> {
    @Override
    public Instant convert(final String source) {
        return Instant.parse(source);
    }
}
