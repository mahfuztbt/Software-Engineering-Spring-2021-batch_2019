package com.mitonal.edu.common.validate;

import com.mitonal.edu.common.enums.IdableEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsIdableEnumValiadator.class })
public @interface IsIdableEnum {

	boolean requried() default false;

	/**
	 * 通过注解后输出的信息,可以自定义
	 * @return
	 */
	String message() default "不是支持的枚举类型";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends IdableEnum> support();

}
