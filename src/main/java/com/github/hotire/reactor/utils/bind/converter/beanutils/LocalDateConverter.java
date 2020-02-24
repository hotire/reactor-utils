package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.apache.commons.beanutils.Converter;

import java.time.LocalDate;

public class LocalDateConverter implements Converter {

  @Override
  public <T> T convert(final Class<T> type, final Object value) {
    return type.cast(LocalDate.parse((String)value));
  }
}
