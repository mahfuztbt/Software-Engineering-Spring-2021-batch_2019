package com.mitonal.edu.common.utils;

import com.mitonal.edu.common.enums.IdableEnum;

import java.util.Objects;

public class EnumHelper {

	public static <T> T fromId(Class<? extends IdableEnum> enumClass, int id) {
		if (!enumClass.isEnum()) {
			throw new IllegalArgumentException("parse enum to int fail: the class is not a enum implements IdableEnum");
		}
		IdableEnum[] enums = enumClass.getEnumConstants();
		for (IdableEnum idableEnum : enums) {
			if (Objects.equals(idableEnum.getId(), id)) {
				return (T) idableEnum;
			}
		}
		throw new IllegalArgumentException("parse enum to int fail: the " + id + " is not legal id in " + enumClass);
	}

	public static boolean isValid(Class<? extends IdableEnum> enumClass, int id) {
		if (!enumClass.isEnum()) {
			return false;
		}
		IdableEnum[] enums = enumClass.getEnumConstants();
		for (IdableEnum idableEnum : enums) {
			if (Objects.equals(idableEnum.getId(), id)) {
				return true;
			}
		}
		return false;
	}

}
