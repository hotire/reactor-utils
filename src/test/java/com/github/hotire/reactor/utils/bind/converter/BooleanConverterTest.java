package com.github.hotire.reactor.utils.bind.converter;

import com.github.hotire.reactor.utils.bind.converter.spring.BooleanConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanConverterTest {

  @Test
  void convert() {
    // Given
    final BooleanConverter converter = new BooleanConverter();

    // When
    final Boolean result = converter.convert("true");

    // Then
    assertThat(result).isEqualTo(true);
  }
}