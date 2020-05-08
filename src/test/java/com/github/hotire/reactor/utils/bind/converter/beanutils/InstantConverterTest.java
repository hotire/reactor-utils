package com.github.hotire.reactor.utils.bind.converter.beanutils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class InstantConverterTest {
    @Test
    void convert() {
        // given
        final Long expected = 1543602924000L;
        final String value = "2018-11-30T18:35:24.00Z" ;
        final InstantConverter converter = new InstantConverter();

        final Instant result = converter.convert(Instant.class, value);

        Assertions.assertThat(result.toEpochMilli()).isEqualTo(expected);
    }
}