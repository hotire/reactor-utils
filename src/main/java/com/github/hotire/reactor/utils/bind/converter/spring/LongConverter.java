package com.github.hotire.reactor.utils.bind.converter.spring;

import org.springframework.core.convert.converter.Converter;

public class LongConverter implements Converter<String, Long> {

  @Override
  public Long convert(final String source) {
    return Long.valueOf(source);
  }
}
