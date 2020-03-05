package com.github.hotire.reactor.utils.hooks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

    @Test
    void apply() {
        // given
        final Throwable throwable = mock(Throwable.class);
        final Object obj = mock(Object.class);

        final OperatorErrorHandler errorHandler = mock(OperatorErrorHandler.class);
        final OperatorErrorHandlerDecorator decorator = new OperatorErrorHandlerDecorator(errorHandler);

        // when
        when(errorHandler.apply(throwable, obj)).thenReturn(throwable);
        final Throwable result = decorator.apply(throwable, obj);

        // then
        assertThat(result).isEqualTo(throwable);
        verify(errorHandler, times(1)).apply(throwable, obj);
    }
}