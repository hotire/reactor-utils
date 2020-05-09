package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.apache.commons.beanutils.Converter;

import java.time.Instant;

public class InstantConverter implements Converter {

    @Override
    public <T> T convert(final Class<T> type, final Object value) {
        return type.cast(Instant.parse((String)value));
    }
}
