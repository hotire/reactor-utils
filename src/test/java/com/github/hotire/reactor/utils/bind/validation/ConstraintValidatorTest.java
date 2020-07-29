package com.github.hotire.reactor.utils.bind.validation;

import com.github.hotire.reactor.utils.bind.BindingTestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

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

    static class TestErrorEntity {
        @Constraint(fieldName = "name", message = "hotrie is admin")
        private Consumer<String> predicate = s -> {};
    }

    @Test
    void validateNotBooleanSupplierType() {
        // given
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        final ConstraintValidator validator = new ConstraintValidator(localValidatorFactoryBean);
        final TestErrorEntity testErrorEntity = new TestErrorEntity();
        final Errors errors = new BindException(testErrorEntity, testErrorEntity.getClass().getSimpleName());

        // when
        validator.validate(testErrorEntity, errors);

        // then
        assertThat(errors.getAllErrors().size()).isEqualTo(0);
    }

    @Test
    void validateError() {
        // given
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        final ConstraintValidator validator = new ConstraintValidator(localValidatorFactoryBean) {
            @Override
            protected Optional<BooleanSupplier> castBooleanSupplier(final Object obj, final String fieldName) {
                throw new RuntimeException();
            }
        };
        final TestErrorEntity testErrorEntity = new TestErrorEntity();
        final Errors errors = new BindException(testErrorEntity, testErrorEntity.getClass().getSimpleName());

        // when
        validator.validate(testErrorEntity, errors);

        // then
        assertThat(errors.getAllErrors().size()).isEqualTo(0);
    }




}

