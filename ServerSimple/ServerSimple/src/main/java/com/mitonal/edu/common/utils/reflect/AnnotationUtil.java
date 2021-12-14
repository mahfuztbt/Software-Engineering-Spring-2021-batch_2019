package com.mitonal.edu.common.utils.reflect;

import java.lang.reflect.Field;

/**
 * 注解相关操作工具类
 */
public class AnnotationUtil {

	/**
	 * 获取对象中被特定注解标注的属性值
	 * @param object 目标对象
	 * @param annotationClass 指定注解class
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValueByObject(Object object, Class annotationClass) throws Exception {

		// 获取该对象的Class
		Class objClass = object.getClass();
		// 获取所有的属性数组
		Field[] fields = objClass.getDeclaredFields();
		for (Field field : fields) {
			// 判断属性上是否有特定注解
			boolean hasAnnotation = field.isAnnotationPresent(annotationClass);
			if (hasAnnotation) {
				field.setAccessible(true);
				return field.get(object);
			}
			else {
				continue;
			}
		}
		return null;
	}

}