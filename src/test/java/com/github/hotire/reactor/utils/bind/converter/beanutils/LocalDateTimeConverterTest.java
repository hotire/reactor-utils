package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeConverterTest {

  @Test
  void convert() {
    // Given
    final LocalDateTimeConverter converter = new LocalDateTimeConverter();
    final LocalDateTime request = LocalDateTime.now();

    // When
    final LocalDateTime result = converter.convert(LocalDateTime.class, request.toString());

    // Then
    assertThat(result).isEqualTo(request);
  }
}