package com.github.hotire.reactor.utils.bind.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BooleanSupplier;

@Slf4j
public class ConstraintValidator extends SpringValidatorAdapter {

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
            castBooleanSupplier(field.get(target), field.getName())
                    .filter(BooleanSupplier::getAsBoolean)
                    .ifPresent(isValid -> error.rejectValue(constraint.fieldName(), constraint.errorCode(), constraint.message()));
        } catch (Exception e) {
          log.warn("validate constraint error : ", e);
        }
      });
  }

  protected Optional<BooleanSupplier> castBooleanSupplier(final Object obj, final String fieldName) {
      if (obj instanceof BooleanSupplier) {
          return Optional.of((BooleanSupplier)obj);
      }
      log.warn(fieldName + " is not the java.util.function.BooleanSupplier type");
      return Optional.empty();
  }

}
