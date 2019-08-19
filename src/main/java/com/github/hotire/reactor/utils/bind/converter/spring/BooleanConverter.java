package com.github.hotire.reactor.utils.bind.converter.spring;

import org.springframework.core.convert.converter.Converter;

public class BooleanConverter implements Converter<String, Boolean> {

  @Override
  public Boolean convert(String source) {
    return Boolean.valueOf(source);
  }
}
