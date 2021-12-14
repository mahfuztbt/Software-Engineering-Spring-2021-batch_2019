package com.mitonal.edu.common.validate;

import com.mitonal.edu.common.enums.IdableEnum;
import com.mitonal.edu.common.utils.EnumHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsIdableEnumValiadator implements ConstraintValidator<IsIdableEnum, Integer> {

	public boolean required = false;

	public Class<? extends IdableEnum> idableEnum = null;

	@Override
	public void initialize(IsIdableEnum constraintAnnotation) {
		// 初始化:拿到IsMobile中的required中的值
		required = constraintAnnotation.requried();
		idableEnum = constraintAnnotation.support();
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (required) {
			return EnumHelper.isValid(idableEnum, value);
		}
		else {
			if (value == null) {
				return true;
			}
			else {
				return EnumHelper.isValid(idableEnum, value);
			}
		}
	}

}
