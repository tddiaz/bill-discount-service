package com.github.tddiaz.billdiscountservice.app.api.validation.constraints;

import com.github.tddiaz.billdiscountservice.app.api.validation.CustomerTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CustomerTypeValidator.class)
@Documented
public @interface CustomerType {

    String message() default "Invalid customer type";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
