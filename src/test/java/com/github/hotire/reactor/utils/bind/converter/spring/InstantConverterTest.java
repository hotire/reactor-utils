package com.github.hotire.reactor.utils.bind.converter.spring;


import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class InstantConverterTest {

    @Test
    void convert() {
        // given
        final Long expected = 1543602924000L;
        final String value = "2018-11-30T18:35:24.00Z" ;
        final InstantConverter converter = new InstantConverter();

        // when
        final Instant result = converter.convert(value);

        // then
        assertThat(result).isNotNull();
        assertThat(result.toEpochMilli()).isEqualTo(expected);
    }
}