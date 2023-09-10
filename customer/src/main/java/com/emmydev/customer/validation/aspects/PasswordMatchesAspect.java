package com.emmydev.customer.validation.aspects;

import com.emmydev.customer.model.CustomerRegistrationRequest;
import com.emmydev.customer.validation.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesAspect implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        CustomerRegistrationRequest customer = (CustomerRegistrationRequest) obj;
        return customer.getPassword().equals(customer.getPasswordConfirmation());
    }
}
