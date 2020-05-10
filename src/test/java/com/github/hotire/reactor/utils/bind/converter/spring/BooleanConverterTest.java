package com.github.hotire.reactor.utils.bind.converter.spring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanConverterTest {

  @Test
  void convert() {
    // given
    final BooleanConverter converter = new BooleanConverter();

    // when
    final Boolean result = converter.convert("true");

    // then
    assertThat(result).isEqualTo(true);
  }
}