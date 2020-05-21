package com.github.hotire.reactor.utils.bind.converter;


import java.time.Instant;

public class InstantConverter implements Converter<String, Instant> {

    @Override
    public Instant convert(final String source) {
        return Instant.parse(source);
    }

    @Override
    public <T> T convert(final Class<T> type, final Object value) {
        return type.cast(Instant.parse((String)value));
    }
}
