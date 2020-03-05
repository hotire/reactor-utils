package com.github.hotire.reactor.utils.hooks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OperatorErrorHandlerDecoratorTest {

    @Test
    void getDelegate() {
        // given
        final OperatorErrorHandler errorHandler = mock(OperatorErrorHandler.class);
        final OperatorErrorHandlerDecorator decorator = new OperatorErrorHandlerDecorator(errorHandler);

        // when
        final OperatorErrorHandler result = decorator.getDelegate();

        // then
        assertThat(result).isEqualTo(errorHandler);
    }
}