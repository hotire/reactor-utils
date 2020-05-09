package com.github.hotire.reactor.utils.bind.converter.spring;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;

public class OffsetDateTimeConverter implements Converter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(final String source) {
        return OffsetDateTime.parse(source);
    }
}
