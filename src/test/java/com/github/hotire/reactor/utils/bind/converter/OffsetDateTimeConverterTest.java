package com.github.hotire.reactor.utils.bind.converter;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OffsetDateTimeConverterTest {
    @Test
    void convert() {
        // given
        final String value = "2018-11-30T18:35:24.00Z" ;
        final OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

        // when
        final OffsetDateTime result = converter.convert(value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void convertByBeanUtils() {
        // given
        final String expected = "2007-12-03T10:15:30+01:00";
        final OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

        // when
        final OffsetDateTime result = converter.convert(OffsetDateTime.class, expected);

        // then
        assertThat(result.toString()).isEqualTo(expected);
    }

}