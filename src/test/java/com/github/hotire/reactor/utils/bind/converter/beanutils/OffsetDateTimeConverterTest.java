package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OffsetDateTimeConverterTest {

    @Test
    void test() {
        // given
        final String expected = "2007-12-03T10:15:30+01:00";
        final OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

        // when
        final OffsetDateTime result = converter.convert(OffsetDateTime.class, expected);

        // then
        assertThat(result.toString()).isEqualTo(expected);
    }
}