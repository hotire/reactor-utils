package com.github.hotire.reactor.utils.bind.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.hotire.reactor.utils.bind.converter.spring.LongConverter;
import org.junit.Test;

public class LongConverterTest {

  @Test
  public void convert() {
    // Given
    final LongConverter converter = new LongConverter();

    // When
    final Long result = converter.convert("1");

    // Then
    assertThat(result).isEqualTo(1L);
  }
}