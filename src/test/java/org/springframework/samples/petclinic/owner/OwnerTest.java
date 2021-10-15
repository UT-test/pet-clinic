package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest {
	public OwnerTest(){}
	private Owner owner;
	private Validator validator;
	List<ConstraintViolation<Owner>> constraintViolations;
	Pet pet1, pet2, pet3;
	Set<Pet> pets;
	PetType dog, cat;

	@BeforeEach
	void setUp(){
		setUpOwner();
		setUpTypes();
		setUpPets();
		owner.setPetsInternal(pets);
		constraintViolations = new ArrayList<>();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@AfterEach
	public void tearDown(){
		owner = null;
		pets = null;
		dog = null;
		cat = null;
		pet1 = null;
		pet2 = null;
		pet3 = null;
		constraintViolations = null;
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
		pet1.setName("dog1");
		pet1.setType(dog);
		pet1.setBirthDate(LocalDate.parse("2000-07-06"));
		pet1.setId(1);
		pet1.setOwner(owner);
		pets.add(pet1);

		pet2 = new Pet();
		pet2.setName("dog2");
		pet2.setType(dog);
		pet2.setBirthDate(LocalDate.parse("2000-07-06"));
		pet2.setId(2);
		pet2.setOwner(owner);
		pets.add(pet2);

		pet3 = new Pet();
		pet3.setName("cat1");
		pet3.setType(cat);
		pet3.setBirthDate(LocalDate.parse("2000-07-06"));
		pet3.setId(3);
		pet3.setOwner(owner);
		pets.add(pet3);
	}

	void setUpTypes(){
		dog = new PetType();
		dog.setName("dog");
		dog.setId(1);
		cat = new PetType();
		cat.setName("cat");
		cat.setId(3);
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
			.containsExactly(pet3, pet1, pet2);
		List<String> petsName = Arrays.asList("cat1", "dog1", "dog2");
		int i = 0;
		for(Pet pet: owner.getPets()) {
			assertEquals(petsName.get(i), pet.getName());
			i++;
		}
	}

	@Test
	public void Owner_does_not_own_removed_pets(){
		owner.removePet(pet2);
		assertFalse(owner.getPets().contains(pet2));
		assertNull(owner.getPet("dog2"));
	}

	@Test
	public void Owner_owns_a_pet_with_an_specific_name(){
		Pet pet = owner.getPet("cat1");
		assertNotNull(pet);
		assertEquals("cat1", pet.getName());
		assertEquals(owner, pet.getOwner());
	}

	@Test
	public void Owner_does_not_own_a_pet_with_given_name(){
		Pet pet = owner.getPet("cat2");
		assertNull(pet);
	}

	@Test
	public void New_pets_with_no_owner_are_owned_when_added_to_an_owners_list(){
		Pet pet = new Pet();
		pet.setName("cat2");
		pet.setType(cat);
		owner.addPet(pet);
		assertNotNull(pet.getOwner());
		assertEquals(owner, pet.getOwner());
	}

	@Test
	public void New_pets_are_not_shown_if_they_are_ignored(){
		Pet pet = new Pet();
		pet.setName("cat2");
		pet.setType(cat);
		owner.addPet(pet);
		assertNull(owner.getPet("cat2", true));
	}

	@Test
	public void New_pets_are_shown_if_they_are_not_ignored(){
		Pet pet = new Pet();
		pet.setName("cat2");
		pet.setType(cat);
		owner.addPet(pet);
		assertNotNull(owner.getPet("cat2", false));
	}
}
