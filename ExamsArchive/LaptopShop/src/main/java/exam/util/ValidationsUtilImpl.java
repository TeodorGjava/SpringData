package exam.util;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
@Component
public class ValidationsUtilImpl implements ValidationsUtil {
    private final Validator validator;

    public ValidationsUtilImpl(Validator validator) {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }
}
