package org.springframework.samples.petclinic.owner;

import java.lang.reflect.Field;

public class GetObjectField {
	public static Field getPrivateFiled(Object obj, String fieldName) {

		final Field field;
		try {
			field = obj.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return null;
		}
		field.setAccessible(true);
		return field;
	}
}
