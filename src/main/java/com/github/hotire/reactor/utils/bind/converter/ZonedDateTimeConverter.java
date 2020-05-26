package com.github.hotire.reactor.utils.bind.converter;

import java.time.ZonedDateTime;

public class ZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
    @Override
    public <T> T convert(final Class<T> type, final Object value) {
        return type.cast(ZonedDateTime.parse((String)value));
    }

    @Override
    public ZonedDateTime convert(final String source) {
        return ZonedDateTime.parse(source);
    }
}
