package com.github.hotire.reactor.utils.bind.converter;


import java.time.OffsetDateTime;

public class OffsetDateTimeConverter implements Converter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(final String source) {
        return OffsetDateTime.parse(source);
    }

    @Override
    public <T> T convert(final Class<T> type, final Object value) {
        return type.cast(OffsetDateTime.parse((String)value));
    }
}
