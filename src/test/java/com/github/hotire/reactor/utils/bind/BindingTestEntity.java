package com.github.hotire.reactor.utils.bind;

import com.github.hotire.reactor.utils.bind.validation.Constraint;
import com.github.hotire.reactor.utils.bind.validation.Validator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


public class BindingTestEntity {
    @NotEmpty
    private final String name;
    @Min(0)
    private final Integer age;

    public BindingTestEntity(final String name, final Integer age) {
        this.name = name;
        this.age = age;
    }

    @Constraint(fieldName = "name", message = "hotrie is admin")
    private Validator predicate = () -> "hotire".equals(getName());

    public String getName() {
        return name;
    }
}
