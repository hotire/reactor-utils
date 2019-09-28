package com.github.hotire.reactor.utils.bind.converter.beanutils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.Test;

public class LocalDateTimeConverterTest {

  @Test
  public void convert() {
    // Given
    final LocalDateTimeConverter converter = new LocalDateTimeConverter();
    final LocalDateTime request = LocalDateTime.now();

    // When
    final LocalDateTime result = converter.convert(LocalDateTime.class, request.toString());

    // Then
    assertThat(result).isEqualTo(request);
  }
}