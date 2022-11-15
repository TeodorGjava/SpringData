package com.softuni.usersystem.domain.annotations;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static com.softuni.usersystem.domain.annotations.AnnotationsUtil.setErrorMessage;

@Component
public class PasswordValidator implements ConstraintValidator<Password, CharSequence> {
    private static final Pattern PATTERN_LOWER = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_UPPER =  Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGIT =  Pattern.compile("[0-9]");
    private static final Pattern PATTERN_SYMBOL =  Pattern.compile("[!@#$%^&*()_<>?]");

    private static final String NOT_NULL_PASSWORD = "Password cannot be null!";
    private static final String TOO_SHORT_PASSWORD = "Password too short!";
    private static final String TOO_LONG_PASSWORD = "Password too long!";
    private static final String SHOULD_CONTAIN_LOWERCASE_LETTER = "Password should contain lowercase letter!";
    private static final String SHOULD_CONTAIN_UPPERCASE_LETTER =  "Password should contain uppercase letter!";
    private static final String SHOULD_CONTAIN_DIGIT = "Password should contain digit!";
    private static final String SHOULD_CONTAIN_SPECIAL_SYMBOL = "Password should contain one of: !@#$%^&*()_+<>?";


    private int minLength;
    private int maxLength;
    private boolean validUpper;
    private boolean validLower;
    private boolean validDigit;
    private boolean validSpecialSymbol;

    @Override
    public void initialize(Password pass) {
        this.minLength = pass.minLength();
        this.maxLength = pass.maxLength();
        this.validUpper = pass.containsUpperCase();
        this.validLower = pass.containsLowerCase();
        this.validDigit = pass.containsDigit();
        this.validSpecialSymbol = pass.containsSpecialSymbols();
    }

    @Override
    public boolean isValid(final CharSequence value, final ConstraintValidatorContext validatorContext) {
        if (value == null) {
            setErrorMessage(validatorContext,NOT_NULL_PASSWORD );
            return false;
        }
        if (value.length() < this.minLength) {
            setErrorMessage(validatorContext, TOO_SHORT_PASSWORD);
            return false;
        }

        if (value.length() > this.maxLength) {
            setErrorMessage(validatorContext, TOO_LONG_PASSWORD);
            return false;
        }

        String password = value.toString();

        if (!PATTERN_LOWER.matcher(password).find() && this.validLower) {
            setErrorMessage(validatorContext,
                    SHOULD_CONTAIN_LOWERCASE_LETTER);
            return false;
        }

        if (!PATTERN_UPPER.matcher(password).find() && this.validUpper) {
            setErrorMessage(validatorContext, SHOULD_CONTAIN_UPPERCASE_LETTER);
            return false;
        }

        if (!PATTERN_DIGIT.matcher(password).find() && this.validDigit) {
            setErrorMessage(validatorContext, SHOULD_CONTAIN_DIGIT);
            return false;
        }

        if (!PATTERN_SYMBOL.matcher(password).find() && this.validSpecialSymbol) {
            setErrorMessage(validatorContext, SHOULD_CONTAIN_SPECIAL_SYMBOL);
            return false;
        }

        return true;
    }
}

enum AnnotationsUtil {
    ;

    public static void setErrorMessage(final ConstraintValidatorContext context, final String errorMessage) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    }
}
