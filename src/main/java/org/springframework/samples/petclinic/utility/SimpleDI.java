package org.springframework.samples.petclinic.utility;

import org.springframework.samples.petclinic.owner.PetRepository;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * this simple class shows the main idea behind a Dependency Injection library
 */
public abstract class SimpleDI {

	public static SimpleDI getInstance() throws Exception {
		// todo return the singleton instance of dependency injection
		throw new Exception("not implemented");
	}

	public abstract void provideByInstance(Class<? extends Object> typeClass, Object instanceOfType);

	public abstract void provideByAConstructorFunction(Class<? extends Object> typeClass, Callable<Object> providerFunction);

	public abstract Object get(Class<? extends Object> requiredType) throws Exception;
}
