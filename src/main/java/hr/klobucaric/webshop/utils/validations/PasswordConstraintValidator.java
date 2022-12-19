package hr.klobucaric.webshop.utils.validations;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword arg0) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
				new LengthRule(8, 30),
				new UppercaseCharacterRule(1),
				new DigitCharacterRule(1),
				new WhitespaceRule()));

		RuleResult result = validator.validate(new PasswordData(password));
		if (result.isValid()) {
			return true;
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(
						validator.getMessages(result).toString())
				.addConstraintViolation();
		return false;
	}
}