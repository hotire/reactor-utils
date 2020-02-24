package com.github.hotire.reactor.utils.bind.converter.spring;

import com.github.hotire.reactor.utils.bind.converter.spring.LongConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongConverterTest {

  @Test
  void convert() {
    // Given
    final LongConverter converter = new LongConverter();

    // When
    final Long result = converter.convert("1");

    // Then
    assertThat(result).isEqualTo(1L);
  }
}