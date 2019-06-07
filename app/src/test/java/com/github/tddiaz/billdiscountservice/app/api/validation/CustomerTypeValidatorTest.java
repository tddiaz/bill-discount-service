package com.github.tddiaz.billdiscountservice.app.api.validation;

import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTypeValidatorTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void givenBlankOrNullValue_whenValidate_shouldReturnNoViolations() {
        {
            Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass(""));
            assertThat(constraintViolations).hasSize(0);
        }
        {
            Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass(null));
            assertThat(constraintViolations).hasSize(0);
        }
    }

    @Test
    public void givenValidCustomerType_whenValidate_shouldReturnNoViolations() {
        Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass("EMPLOYEE"));
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    public void givenInvalidCustomerType_whenValidate_shouldReturnViolations() {
        Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass("INVALID"));
        assertThat(constraintViolations).hasSize(1);
    }

    @AllArgsConstructor
    @Getter
    private static class TestClass {
        @CustomerType
        private String value;
    }

}