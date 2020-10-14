package org.springframework.samples.petclinic.utility;

import java.util.HashMap;

public class SimpleDI extends HashMap<Class<? extends Object>, Object> {

	private SimpleDI() {
		super();
	}

	private static SimpleDI INSTANCE;

	public static SimpleDI getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SimpleDI();
		}
		return INSTANCE;
	}
}
