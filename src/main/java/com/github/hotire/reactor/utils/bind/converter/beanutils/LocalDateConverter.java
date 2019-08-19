package com.github.hotire.reactor.utils.bind.converter.beanutils;

import java.time.LocalDate;
import org.apache.commons.beanutils.Converter;

public class LocalDateConverter implements Converter {

  @Override
  public <T> T convert(Class<T> type, Object value) {
    return type.cast(LocalDate.parse((String)value));
  }
}
