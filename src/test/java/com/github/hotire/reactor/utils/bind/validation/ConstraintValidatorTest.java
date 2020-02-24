package com.github.hotire.reactor.utils.bind.validation;

import com.github.hotire.reactor.utils.bind.BindingTestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConstraintValidatorTest {
    @Test
    void validate() {
        // given
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        final ConstraintValidator validator = new ConstraintValidator(localValidatorFactoryBean);
        final BindingTestEntity bindingTestEntity = new BindingTestEntity("hotire", 0);
        final Errors errors = new BindException(bindingTestEntity, bindingTestEntity.getClass().getSimpleName());

        // when
        validator.validate(bindingTestEntity, errors);

        // then
        assertThat(errors.getAllErrors().size()).isEqualTo(1);
    }
}