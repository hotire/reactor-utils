package com.github.hotire.reactor.utils.bind.converter.beanutils;

import java.time.Year;
import org.apache.commons.beanutils.Converter;

public class YearConverter implements Converter {

  @Override
  public <T> T convert(Class<T> type, Object value) {
    return type.cast(Year.parse((String)value));
  }
}
