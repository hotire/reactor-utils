package com.github.hotire.reactor.utils.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class MediaTypeContextTest {

    @MethodSource("provideArguments")
    @ParameterizedTest
    void equals(final MediaType target, final MediaType other, final boolean expected) {
        // given
        final MediaTypeContext mediaTypeContext = () -> target;

        // when
       final boolean result = mediaTypeContext.equals(other);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(MediaType.APPLICATION_JSON, new MediaType("application", "json"), true),
                Arguments.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8, false),
                Arguments.of(MediaType.APPLICATION_JSON, null, false)
        );
    }
}