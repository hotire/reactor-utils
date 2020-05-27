package com.github.hotire.reactor.utils.bind.converter;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ZonedDateTimeConverterTest {

    @Test
    void convert() {
        // given
        final String value = "2002-06-18T20:30+09:00[Asia/Seoul]";
        final OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

        // when
        final OffsetDateTime result = converter.convert(value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void convertByBeanUtils() {
        // given
        final String expected = "2002-06-18T20:30+09:00[Asia/Seoul]";
        final ZonedDateTimeConverter converter = new ZonedDateTimeConverter();

        // when
        final ZonedDateTime result = converter.convert(ZonedDateTime.class, expected);

        // then
        assertThat(result).isEqualTo(expected);
    }
}