package com.github.hotire.reactor.utils.bind.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.hotire.reactor.utils.bind.converter.spring.BooleanConverter;
import org.junit.Test;

public class BooleanConverterTest {

  @Test
  public void convert() {
    // Given
    final BooleanConverter converter = new BooleanConverter();

    // When
    final Boolean result = converter.convert("true");

    // Then
    assertThat(result).isEqualTo(true);
  }
}