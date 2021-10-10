package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
	private Owner owner;
	private Validator validator;
	List<ConstraintViolation<Owner>> constraintViolations;

	@BeforeEach
	void setUp(){
		owner = new Owner();
		constraintViolations = new ArrayList<>();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	void setUpOwner(){
		owner.setFirstName("Amir");
		owner.setLastName("Aliz");
		owner.setCity("Tehran");
		owner.setAddress("Rey");
		owner.setTelephone("9212776104");
	}

	@Test
	public void Valid_owner_can_not_have_empty_fields(){
		constraintViolations.addAll(validator.validate(owner));
		assertEquals(5, constraintViolations.size(), "Owner has 5 empty fields.");
	}

	@Test
	public void Owners_telephone_can_not_contain_characters(){
		setUpOwner();
		owner.setTelephone("123456789a");
		constraintViolations.addAll(validator.validate(owner));
		assertEquals(1, constraintViolations.size());
		assertEquals("numeric value out of bounds (<10 digits>.<0 digits> expected)", constraintViolations.get(0).getMessage());
	}

	@Test
	public void Owners_telephone_must_be_at_most_a_ten_digits_number(){
		setUpOwner();
		owner.setTelephone("12345678123");
		constraintViolations.addAll(validator.validate(owner));
		assertEquals(1, constraintViolations.size());
		assertEquals("numeric value out of bounds (<10 digits>.<0 digits> expected)", constraintViolations.get(0).getMessage());
	}
}
