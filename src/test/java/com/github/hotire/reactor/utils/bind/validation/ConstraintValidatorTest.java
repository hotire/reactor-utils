package com.github.hotire.reactor.utils.bind.validation;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class ConstraintValidatorTest {

    void validate() {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        final ConstraintValidator = new ConstraintValidator(localValidatorFactoryBean);
    }
}