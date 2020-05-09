package com.github.hotire.reactor.utils.bind.converter.spring;

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

}