package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerTest {

	private Owner george;

	@BeforeEach
	void setup() {
		this.george = new Owner();
	}

	@Test
	void testSetter_setsAddress() throws NoSuchFieldException, IllegalAccessException {
		String georgeAddress = "5040 N Moapa Valley Blvd, Logandale, NV, 89021";
		george.setAddress(georgeAddress);
		final Field addressField = george.getClass().getDeclaredField("address");
		addressField.setAccessible(true);
		assertEquals(georgeAddress, addressField.get(george), "Addresses didn't match");
	}

	@Test
	void testGetter_getsAddress() throws NoSuchFieldException, IllegalAccessException {
		String georgeAddress = "45315 County 54 Hwy, Ottertail, MN, 56571";
		final Field addressField = george.getClass().getField("address");
		addressField.setAccessible(true);
		addressField.set(george, georgeAddress);
		assertEquals(georgeAddress, george.getAddress(), "Addresses didn't match" );
	}

	@Test
	void testSetter_setsCity() throws NoSuchFieldException, IllegalAccessException {
		String georgeCity = "San Diego";
		george.setCity(georgeCity);
		final Field cityField = george.getClass().getDeclaredField("city");
		cityField.setAccessible(true);
		assertEquals(georgeCity, cityField.get(george), "Cities didn't match");
	}

	@Test
	void testGetter_getsCity() throws NoSuchFieldException, IllegalAccessException {
		String georgeCity = "Chicago";
		final Field addressField = george.getClass().getField("city");
		addressField.setAccessible(true);
		addressField.set(george, georgeCity);
		assertEquals(georgeCity, george.getCity(), "Cities didn't match" );
	}

	@Test
	void testSetter_setsTelephone() throws NoSuchFieldException, IllegalAccessException {
		String georgeTelephone = "(218) 367-3807";
		george.setTelephone(georgeTelephone);
		final Field telephoneField = george.getClass().getDeclaredField("telephone");
		telephoneField.setAccessible(true);
		assertEquals(georgeTelephone, telephoneField.get(george), "Telephone numbers didn't match");
	}

	@Test
	void testGetter_getsTelephone() throws NoSuchFieldException, IllegalAccessException {
		String georgeTelephone = "(718) 515-0647";
		final Field telephoneField = george.getClass().getField("telephone");
		telephoneField.setAccessible(true);
		telephoneField.set(george, georgeTelephone);
		assertEquals(georgeTelephone, george.getTelephone(), "Telephone numbers didn't match" );
	}
}
