package com.github.hotire.reactor.utils.bind.validation;

import static java.util.stream.Collectors.joining;

import javax.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

public class BindingResultException extends ValidationException {
  private Errors errors;
  public BindingResultException(Errors errors) {
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
