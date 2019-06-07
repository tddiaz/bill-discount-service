package com.github.tddiaz.billdiscountservice.app.api.validation;

import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class CurrencyCodeValidatorTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void givenBlankOrNullValue_whenValidate_shouldReturnEmptyViolations() {
        {
            Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass(""));
            Assertions.assertThat(constraintViolations).hasSize(0);
        }
        {
            Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass(null));
            Assertions.assertThat(constraintViolations).hasSize(0);
        }
    }

    @Test
    public void givenInvalidCurrencyCode_whenValidate_shouldReturnViolations() {
        Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass("INVALID"));
        Assertions.assertThat(constraintViolations).hasSize(1);
    }

    @Test
    public void givenValidCurrencyCode_whenValidate_shouldReturnViolations() {
        Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass("AED"));
        Assertions.assertThat(constraintViolations).hasSize(0);
    }

    @AllArgsConstructor
    @Getter
    private static class TestClass {
        @CurrencyCode
        private String value;
    }

}