package com.whackdackery.openrota.app.common.validation;

import org.passay.*;
import org.springframework.stereotype.Component;

@Component
public class PasswordConstraintValidator {

    public RuleResult validate(String password) {

        PasswordValidator validator = new PasswordValidator(
                // length between 8 and 16 characters
                new LengthRule(8, 64),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // no whitespace
                new WhitespaceRule());

        return validator.validate(new PasswordData(password));
    }

}
