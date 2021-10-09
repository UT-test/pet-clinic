package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
	private Owner owner;
	private ValidatorFactory factory;
	private Validator validator;

	@BeforeEach
	void setUp(){
		owner = new Owner();
		owner.setFirstName("Amir");
		owner.setLastName("Aliz");
		owner.setCity("Tehran");
		owner.setAddress("Rey");
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void Owner_can_not_have_empty_fields(){
		owner.setCity("");
		owner.setFirstName("");
		owner.setAddress("");
		owner.setLastName("");
		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);
		assertEquals(5, constraintViolations.size(), "Owner has 5 empty fields.");
	}
}
