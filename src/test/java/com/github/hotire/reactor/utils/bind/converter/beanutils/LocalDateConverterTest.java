package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateConverterTest {

    @Test
    void convert() {
        // Given
        final LocalDateConverter converter = new LocalDateConverter();
        final LocalDate request = LocalDate.now();

        // When
        final LocalDate result = converter.convert(LocalDate.class, request.toString());

        // Then
        assertThat(result).isEqualTo(request);
    }
}