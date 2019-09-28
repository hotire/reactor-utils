package com.github.hotire.reactor.utils.bind.converter.beanutils;

import java.time.LocalDateTime;
import org.apache.commons.beanutils.Converter;

public class LocalDateTimeConverter implements Converter {

  @Override
  public <T> T convert(Class<T> type, Object value) {
    return type.cast(LocalDateTime.parse((String) value));
  }
}
