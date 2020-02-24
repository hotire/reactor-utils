package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

class YearConverterTest {
    @Test
    void convert() {
        // Given
        final YearConverter converter = new YearConverter();
        final Year request = Year.now();

        // When
        final Year result = converter.convert(Year.class, String.valueOf(request.getValue()));

        // Then
        assertThat(result).isEqualTo(request);
    }
}