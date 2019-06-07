package com.github.tddiaz.billdiscountservice.app.api.validation;

import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.ItemsCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemsCategoryValidatorTest {

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
    public void givenValidItemsCategory_whenValidate_shouldReturnNoViolations() {
        Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass("GROCERIES"));
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    public void givenInvalidItemCategory_whenValidate_shouldReturnViolations() {
        Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(new TestClass("INVALID"));
        assertThat(constraintViolations).hasSize(1);
    }

    @AllArgsConstructor
    @Getter
    private static class TestClass {
        @ItemsCategory
        private String value;
    }

}