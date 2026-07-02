package com.tz.gpsolution.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidAmenitiesValidator.class)
@Documented
public @interface ValidAmenities {
    String message() default "Список удобств не может быть пустым или содержать пустые значения";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}