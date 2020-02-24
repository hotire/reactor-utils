package com.github.hotire.reactor.utils.bind.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import javax.validation.ValidationException;

import static java.util.stream.Collectors.joining;

public class BindingResultException extends ValidationException {

  private Errors errors;

  public BindingResultException(final Errors errors) {
    super(errors.getAllErrors()
      .stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .distinct()
      .collect(joining()));
    this.errors = errors;
  }

  public Errors getErrors() {
    return this.errors;
  }
}
