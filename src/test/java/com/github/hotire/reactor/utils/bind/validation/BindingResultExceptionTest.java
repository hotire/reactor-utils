package com.github.hotire.reactor.utils.bind.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BindingResultExceptionTest {

    @Test
    void getMessage() {
        // given
        final String expected = "defaultMessage";
        final Errors errors = mock(Errors.class);
        final List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError("objectName", expected));

        // when
        when(errors.getAllErrors()).thenReturn(objectErrors);
        final BindingResultException bindingResultException = new BindingResultException(errors);
        final String message = bindingResultException.getMessage();

         // then
        assertThat(message).isEqualTo(expected);
    }

    @Test
    void getErrors() {
        // given
        final ObjectError expected = new ObjectError("objectName", "defaultMessage");
        final Errors errors = mock(Errors.class);
        final List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(expected);

        // when
        when(errors.getAllErrors()).thenReturn(objectErrors);
        final BindingResultException bindingResultException = new BindingResultException(errors);
        final Errors result = bindingResultException.getErrors();

        // then
        Assertions.assertThat(result.getAllErrors().size()).isEqualTo(1);
        Assertions.assertThat(result.getAllErrors().get(0)).isEqualTo(expected);
    }
}