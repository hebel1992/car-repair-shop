package pl.coderslab.servicestation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    private int value;

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.value = constraintAnnotation.minAge();
    }

    @Override
    public boolean isValid(LocalDate age, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate today = LocalDate.now();
        if (age != null) {
            return Period.between(age, today).getYears() >= 16;
        }
        return true;
    }
}
