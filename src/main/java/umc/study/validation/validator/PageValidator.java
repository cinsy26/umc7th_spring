package umc.study.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import umc.study.validation.annotation.Page;

@Component
public class PageValidator implements ConstraintValidator<Page, Integer> {

    @Override
    public void initialize(Page constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null || value < 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("페이지 번호는 1 이상이어야 합니다.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
