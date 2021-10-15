package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(Theories.class)
public class OwnerTheoryTest {
	private Owner owner;
	Pet pet1, pet2, pet3, pet4;
	Set<Pet> pets;
	PetType dog, cat;

	public OwnerTheoryTest(){}

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

		pet4 = new Pet();
		pet4.setName("cat2");
		pet4.setOwner(owner);
		pet4.setType(cat);
		pets.add(pet4);
	}

	void setUpTypes(){
		dog = new PetType();
		dog.setName("dog");
		dog.setId(1);
		cat = new PetType();
		cat.setName("cat");
		cat.setId(3);
	}


	@DataPoints
	public static String[] pet_names = { "cat1", null, "dog1", "dog2", "", "cat2"};

	@DataPoints
	public static boolean[] ignore_new = {true};

	@Theory
	public void Owner_owns_a_pet_with_an_specific_name(String name){
		owner = new Owner();
		setUpTypes();
		setUpPets();
		owner.setPetsInternal(pets);
		System.out.println("-------------------------------------------------------");
		System.out.printf("Testing with %s.\n", name);
		assumeNotNull(name);
		assumeTrue(!name.isEmpty());
		Pet pet = owner.getPet(name);
		assertNotNull(pet);
		assertEquals(name, pet.getName());
		assertEquals(owner, pet.getOwner());
		System.out.printf("Owner owns %s.\n", name);
	}

	@Theory
	public void New_pets_are_not_shown_if_they_are_ignored(String name, boolean ignore_new){
		owner = new Owner();
		setUpTypes();
		setUpPets();
		owner.setPetsInternal(pets);
		System.out.println("-------------------------------------------------------");
		System.out.printf("Testing with %s.\n", name);
		assumeNotNull(name);
		assumeTrue(!name.isEmpty());
		Pet pet = owner.getPet(name, ignore_new);
		if(pet == null){
			assertNull(owner.getPet(name).getId());
			System.out.println(name + " is a new pet and ignored.");
		}
		else{
			assertSame(pet.getOwner(), owner);
			System.out.println(name + " is not a new pet.");
		}
	}
}
