package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.apache.commons.beanutils.Converter;

import java.time.OffsetDateTime;

public class OffsetDateTimeConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        return type.cast(OffsetDateTime.parse((String)value));
    }
}
