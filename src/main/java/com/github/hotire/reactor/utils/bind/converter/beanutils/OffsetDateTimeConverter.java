package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.apache.commons.beanutils.Converter;

import java.time.OffsetDateTime;

public class OffsetDateTimeConverter implements Converter {

    @Override
    public <T> T convert(final Class<T> type, final Object value) {
        return type.cast(OffsetDateTime.parse((String)value));
    }
}
