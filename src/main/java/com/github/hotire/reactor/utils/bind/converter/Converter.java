package com.github.hotire.reactor.utils.bind.converter;

public interface Converter<S, T> extends org.springframework.core.convert.converter.Converter<S, T>, org.apache.commons.beanutils.Converter {
}
