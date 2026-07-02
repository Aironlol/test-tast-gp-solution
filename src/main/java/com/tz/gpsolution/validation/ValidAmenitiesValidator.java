package com.tz.gpsolution.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class ValidAmenitiesValidator implements ConstraintValidator<ValidAmenities, Collection<String>> {

    @Override
    public boolean isValid(Collection<String> amenities, ConstraintValidatorContext context) {
        if (amenities == null || amenities.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Список удобств не может быть пустым").addConstraintViolation();
            return false;
        }

        boolean hasInvalidAmenity = amenities.stream()
                .anyMatch(a -> a == null || a.trim().isEmpty());

        if (hasInvalidAmenity) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Удобство не может быть null или пустой строкой").addConstraintViolation();
            return false;
        }

        return true;
    }
}