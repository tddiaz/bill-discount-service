package com.github.tddiaz.billdiscountservice.app.api.validation;

import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.ItemsCategory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ItemsCategoryValidator implements ConstraintValidator<ItemsCategory, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringUtils.isBlank(value)) {
            return true;
        }

        try {
            com.github.tddiaz.billdiscountservice.domain.ItemsCategory.valueOf(value);
        } catch (IllegalArgumentException ex) {
            LOGGER.info(ex.getMessage());
            return false;
        }
        return true;
    }
}
