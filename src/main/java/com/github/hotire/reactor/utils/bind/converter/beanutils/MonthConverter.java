package com.github.hotire.reactor.utils.bind.converter.beanutils;

import java.time.Month;
import org.apache.commons.beanutils.Converter;

public class MonthConverter implements Converter {

  @Override
  public <T> T convert(final Class<T> type, final Object value) {
    return type.cast(Month.of(Integer.parseInt((String)value)));
  }
}
