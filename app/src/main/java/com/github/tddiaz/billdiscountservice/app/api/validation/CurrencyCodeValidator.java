package com.github.tddiaz.billdiscountservice.app.api.validation;

import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.money.Monetary;
import javax.money.MonetaryException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class CurrencyCodeValidator implements ConstraintValidator<CurrencyCode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        try {
            Monetary.getCurrency(value);
        } catch (MonetaryException ex) {
            LOGGER.info(ex.getMessage());
            return false;
        }

        return true;
    }
}
