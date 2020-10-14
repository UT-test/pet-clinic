package org.springframework.samples.petclinic.utility;

import org.springframework.samples.petclinic.owner.PetRepository;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * this simple class shows the main idea behind a Dependency Injection library
 */
public abstract class SimpleDI {

	public static SimpleDI getDIContainer() throws Exception {
		// todo return the singleton instance of your implementation of dependency injection container
		throw new Exception("not implemented");
	}

	public abstract void provideByInstance(Class<?> typeClass, Object instanceOfType);

	public abstract void provideByAConstructorFunction(Class<?> typeClass, Callable<Object> providerFunction);

	public abstract Object getInstanceOf(Class<?> requiredType) throws Exception;
}
