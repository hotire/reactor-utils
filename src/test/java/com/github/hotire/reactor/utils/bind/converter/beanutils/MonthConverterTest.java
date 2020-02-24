package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class MonthConverterTest {

    @Test
    void convert() {
        // Given
        final MonthConverter converter = new MonthConverter();
        final Month request = Month.APRIL;

        // When
        final Month result = converter.convert(Month.class, String.valueOf(request.getValue()));

        // Then
        assertThat(result).isEqualTo(request);
    }

}