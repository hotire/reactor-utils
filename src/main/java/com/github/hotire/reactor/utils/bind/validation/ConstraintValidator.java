package com.github.hotire.reactor.utils.bind.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class ConstraintValidator extends SpringValidatorAdapter {

  private static final Logger log = LoggerFactory.getLogger(ConstraintValidator.class);

  public ConstraintValidator(final Validator targetValidator) {
    super(targetValidator);
  }

  public void validate(final Object target, final Errors error) {
    super.validate(target, error);
    validateConstraint(target, error);
  }

  protected void validateConstraint(final Object target, final Errors error) {
    Arrays.stream(target.getClass().getDeclaredFields())
      .filter(field -> field.isAnnotationPresent(Constraint.class))
      .forEach(field -> {
        final Constraint constraint = field.getAnnotation(Constraint.class);
        field.setAccessible(true);
        try {
          final BooleanSupplier predicate = (BooleanSupplier) field.get(target);

          if (Objects.isNull(predicate)) {
            log.warn(field.getName() + " is not the java.util.function.BooleanSupplier type");
          } else if (predicate.getAsBoolean()) {
            error.rejectValue(constraint.fieldName(), constraint.errorCode(), constraint.message());
          }

        } catch (Exception e) {
          log.warn("validate constraint error : {}", e);
        }
      });
  }

}
