package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
	private Owner owner;
	private Validator validator;
	List<ConstraintViolation<Owner>> constraintViolations;
	Pet pet1, pet2, pet3;
	Set<Pet> pets;
	PetType type1, type2;

	@BeforeEach
	void setUp(){
		setUpOwner();
		setUpTypes();
		setUpPets();
		owner.setPetsInternal(pets);
		constraintViolations = new ArrayList<>();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	void setUpOwner(){
		owner = new Owner();
		owner.setFirstName("Amir");
		owner.setLastName("Aliz");
		owner.setCity("Tehran");
		owner.setAddress("Rey");
		owner.setTelephone("9212776104");
	}

	void setUpPets(){
		pets = new HashSet<>();

		pet1 = new Pet();
		pet1.setName("Ali Zare");
		pet1.setType(type1);
		pet1.setBirthDate(LocalDate.parse("2000-07-06"));
		pet1.setId(1);
		pet1.setOwner(owner);
		pets.add(pet1);

		pet2 = new Pet();
		pet2.setName("dog1");
		pet2.setType(type1);
		pet2.setBirthDate(LocalDate.parse("2000-07-06"));
		pet2.setId(2);
		pet2.setOwner(owner);
		pets.add(pet2);

		pet3 = new Pet();
		pet3.setName("cat1");
		pet3.setType(type2);
		pet3.setBirthDate(LocalDate.parse("2000-07-06"));
		pet3.setId(3);
		pet3.setOwner(owner);
		pets.add(pet3);
	}

	void setUpTypes(){
		type1 = new PetType();
		type1.setName("dog");
		type1.setId(1);
		type2 = new PetType();
		type2.setName("cat");
		type2.setId(3);
	}

	@Test
	public void Valid_owner_can_not_have_empty_fields(){
		owner = new Owner();
		constraintViolations.addAll(validator.validate(owner));
		assertEquals(5, constraintViolations.size());
	}

	@Test
	public void Owners_telephone_can_not_contain_characters(){
		owner.setTelephone("123456789a");
		constraintViolations.addAll(validator.validate(owner));
		assertEquals(1, constraintViolations.size());
		assertEquals("numeric value out of bounds (<10 digits>.<0 digits> expected)", constraintViolations.get(0).getMessage());
	}

	@Test
	public void Owners_telephone_must_be_at_most_a_ten_digits_number(){
		owner.setTelephone("12345678123");
		constraintViolations.addAll(validator.validate(owner));
		assertEquals(1, constraintViolations.size());
		assertEquals("numeric value out of bounds (<10 digits>.<0 digits> expected)", constraintViolations.get(0).getMessage());
	}

	@Test
	public void Owner_with_valid_fields_is_created_with_no_violations(){
		constraintViolations.addAll(validator.validate(owner));
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void Owner_fields_are_saved_correctly(){
		assertEquals("Amir", owner.getFirstName());
		assertEquals("Aliz", owner.getLastName());
		assertEquals("Tehran", owner.getCity());
		assertEquals("Rey", owner.getAddress());
		assertEquals("9212776104", owner.getTelephone());
	}

	@Test
	public void New_owners_dont_have_any_pets(){
		owner = new Owner();

		assertThat(owner.getPets())
			.isNotNull()
			.isEmpty();

		assertThat(owner.getPets())
			.isNotNull()
			.isEmpty();
	}

	@Test
	public void Owners_pets_are_saved_correctly(){
		assertThat(owner.getPetsInternal())
			.containsExactlyInAnyOrder(pet1, pet2, pet3);
	}

	@Test
	public void Owners_pets_are_sorted_alphabetically(){
		assertThat(owner.getPets())
			.containsExactly(pet1, pet3, pet2);
		List<String> petsName = Arrays.asList("Ali Zare", "cat1", "dog1");
		int i = 0;
		for(Pet pet: owner.getPets()) {
			assertEquals(petsName.get(i), pet.getName());
			i++;
		}
	}
}
