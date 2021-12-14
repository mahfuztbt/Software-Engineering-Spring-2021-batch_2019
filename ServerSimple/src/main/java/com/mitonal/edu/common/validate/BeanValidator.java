package com.mitonal.edu.common.validate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@Slf4j
public class BeanValidator {

	private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

	private static <T> Map<String, String> validate(T t, Class... groups) {
		Validator validator = validatorFactory.getValidator();
		Set validateResult = validator.validate(t, groups);
		if (validateResult.isEmpty()) {
			return new LinkedHashMap<String, String>();
		}
		else {
			LinkedHashMap<String, String> errors = new LinkedHashMap<String, String>();
			for (Object o : validateResult) {
				ConstraintViolation violation = (ConstraintViolation) o;
				errors.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			return errors;
		}
	}

	private static Map<String, String> validateList(Collection<?> collection) {
		Iterator iterator = collection.iterator();
		Map<String, String> errors;
		do {
			if (!iterator.hasNext()) {
				return Collections.emptyMap();
			}
			Object object = iterator.next();
			errors = validate(object);
		}
		while (errors.isEmpty());
		return errors;
	}

	private static Map<String, String> validateObject(Object first, Object... objects) {
		if (objects != null && objects.length > 0) {
			return validateList(Arrays.asList(first, objects));
		}
		else {
			return validate(first);
		}
	}

	public static void check(Object param) throws BeanValidationException {
		Map<String, String> map = BeanValidator.validateObject(param);
		if (!(map == null || map.isEmpty())) {
			String objectName = param.getClass().getSimpleName();
			BindingResult bindingResult = new BeanPropertyBindingResult(param, objectName);

			for (Map.Entry<String, String> entry : map.entrySet()) {
				log.trace("{}->{}", entry.getKey(), entry.getValue());
				bindingResult.addError(new FieldError(objectName, entry.getKey(), entry.getValue()));
			}
			throw new BeanValidationException("error.request.data.format", bindingResult);
		}

	}

}
