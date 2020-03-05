package com.github.hotire.reactor.utils.hooks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LoggingOperatorErrorHandlerTest {

    @Test
    void apply() {
        // given
        final Throwable throwable = mock(Throwable.class);
        final LoggingOperatorErrorHandler loggingOperatorErrorHandler = new LoggingOperatorErrorHandler();

        // when
        final Throwable result = loggingOperatorErrorHandler.apply(throwable, new Object());

        // then
        assertThat(result).isEqualTo(throwable);
    }
}