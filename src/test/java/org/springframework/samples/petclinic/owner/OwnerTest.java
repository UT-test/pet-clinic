package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.lang.UnsupportedOperationException;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.samples.petclinic.owner.GetObjectField.getPrivateFiled;

class OwnerTest {

	private Owner george;
	private PetType cat;

	@BeforeEach
	void setup() {
		this.george = new Owner();
		george.setFirstName("george");
		george.setLastName("Merline");
		this.cat = new PetType();
		cat.setId(3875);
		cat.setName("hamster");
	}

	@Test
	void testSetter_setsAddressValidValue() throws IllegalAccessException {
		String georgeAddress = "5040 N Moapa Valley Blvd, Logandale, NV, 89021";
		george.setAddress(georgeAddress);
		assertEquals(georgeAddress, Objects.requireNonNull(getPrivateFiled(george, "address")).get(george), "Addresses didn't match.");
	}

	@Test
	void testSetter_setsAddressNullValue() throws IllegalAccessException {
		george.setAddress(null);
		assertNull(Objects.requireNonNull(getPrivateFiled(george, "address")).get(george), "Address expected to be null.");
	}

	@Test
	void testGetter_getsAddressValidValue() throws IllegalAccessException {
		String georgeAddress = "45315 County 54 Hwy, Ottertail, MN, 56571";
		Objects.requireNonNull(getPrivateFiled(george, "address")).set(george, georgeAddress);
		assertEquals(georgeAddress, george.getAddress(), "Addresses didn't match.");
	}

	@Test
	void testGetter_getsAddressNullValue() {
		assertNull(george.getAddress(), "Address expected to be null.");
	}

	@Test
	void testSetter_setsCityValidValue() throws IllegalAccessException {
		String georgeCity = "San Diego";
		george.setCity(georgeCity);
		assertEquals(georgeCity, Objects.requireNonNull(getPrivateFiled(george, "city")).get(george), "Cities didn't match.");
	}

	@Test
	void testSetter_setsCityNullValue() throws IllegalAccessException {
		george.setCity(null);
		assertNull(Objects.requireNonNull(getPrivateFiled(george, "city")).get(george), "City expected to be null.");
	}

	@Test
	void testGetter_getsCityValidValue() throws IllegalAccessException {
		String georgeCity = "Chicago";
		Objects.requireNonNull(getPrivateFiled(george, "city")).set(george, georgeCity);
		assertEquals(georgeCity, george.getCity(), "Cities didn't match.");
	}

	@Test
	void testGetter_getsCityNullValue() {
		assertNull(george.getCity(), "City expected to be null.");
	}

	@Test
	void testSetter_setsTelephoneValidValue() throws IllegalAccessException {
		String georgeTelephone = "(218) 367-3807";
		george.setTelephone(georgeTelephone);
		assertEquals(georgeTelephone, Objects.requireNonNull(getPrivateFiled(george, "telephone")).get(george), "Telephone numbers didn't match.");
	}

	@Test
	void testSetter_setsTelephoneNullValue() throws IllegalAccessException {
		george.setTelephone(null);
		assertNull(Objects.requireNonNull(getPrivateFiled(george, "telephone")).get(george), "Telephone expected to be null.");
	}

	@Test
	void testGetter_getsTelephoneValidValue() throws IllegalAccessException {
		String georgeTelephone = "(718) 515-0647";
		Objects.requireNonNull(getPrivateFiled(george, "telephone")).set(george, georgeTelephone);
		assertEquals(georgeTelephone, george.getTelephone(), "Telephone numbers didn't match.");
	}

	@Test
	void testGetter_getsTelephoneNullValue() {
		assertNull(george.getTelephone(), "Telephone expected to be null.");
	}

	@Test
	void testGetter_getsPetsInternalNullValue() {
		assertEquals(new HashSet<>(), george.getPetsInternal(), "Pets expected to be empty");
	}

	@Test
	void testGetter_getPetsIntervalValidValue() throws IllegalAccessException {
		Set<Pet> pets = new HashSet<>(createFiveCats());
		Objects.requireNonNull(getPrivateFiled(george, "pets")).set(george, pets);
		assertEquals(pets, george.getPetsInternal(), "Pets didn't match.");
	}

	private Set<Pet> createFiveCats() {
		Set<Pet> pets = new HashSet<>();

		for (int i = 0; i < 5; i++) {
			Pet pet = new Pet();
			pet.setId(i + 100);
			pet.setName("cat" + (4 - i));
			pet.setType(cat);
			pets.add(pet);
		}
		return pets;
	}

	private List<Pet> createFiveCatsSortedWithName() {
		List<Pet> pets = new ArrayList<>();

		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("hamster");

		for (int i = 0; i < 5; i++) {
			Pet pet = new Pet();
			pet.setId(i + 100);
			pet.setName("cat" + i);
			pet.setType(cat);
			pets.add(pet);
		}
		return pets;
	}


	@Test
	void testSetter_setPetsInternalValidValue() throws IllegalAccessException {
		Set<Pet> georgePets = createFiveCats();
		george.setPetsInternal(georgePets);
		assertNotNull(Objects.requireNonNull(getPrivateFiled(george, "pets")).get(george), "Pets didn't expect to be null.");
		assertEquals(georgePets, Objects.requireNonNull(getPrivateFiled(george, "pets")).get(george), "Pets didn't match.");
	}

	@Test
	void testSetter_setPetsInternalNullValue() throws IllegalAccessException {
		george.setPetsInternal(null);
		assertNull(Objects.requireNonNull(getPrivateFiled(george, "pets")).get(george), "Pets excepted to be null.");
	}

	@Test
	void testGetter_getPets() throws IllegalAccessException {
		List<Pet> sortedPets = createFiveCatsSortedWithName();
		Objects.requireNonNull(getPrivateFiled(george, "pets")).set(george, new HashSet<>(sortedPets));
		assertArrayEquals(sortedPets.toArray(), george.getPets().toArray());
		assertThrows(UnsupportedOperationException.class, () -> {george.getPets().add(new Pet());});
	}

	private Owner createNewOwner() {
		Owner william = new Owner();
		william.setFirstName("William");
		william.setLastName("Queen");
		return william;
	}

	@Test
	void testAddPet() {
		Pet pet = new Pet();
		pet.setId(1);
		Owner newOwner = createNewOwner();
		pet.setOwner(newOwner);
		george.addPet(pet);
		assertEquals(pet.getOwner().toString(), george.toString(), "owners didn't match when added pet");
	}

	@Test
	void testRemovePet() throws IllegalAccessException {
		Pet pet = new Pet();
		pet.setType(cat);
		Set<Pet> pets = new HashSet<>();
		pets.add(pet);
		Objects.requireNonNull(getPrivateFiled(george, "pets")).set(george, new HashSet<>(pets));
		george.removePet(pet);
		assertNotEquals(pets, Objects.requireNonNull(getPrivateFiled(george, "pets")).get(george));
		pets.remove(pet);
		assertEquals(pets, Objects.requireNonNull(getPrivateFiled(george, "pets")).get(george));
	}

	@Test
	void testGetNewPet() throws IllegalAccessException {
		Set<Pet> pets = new HashSet<>();
		Pet pet = new Pet();
		pet.setName("cat1");
		pets.add(pet);
		Objects.requireNonNull(getPrivateFiled(george, "pets")).set(george, new HashSet<>(pets));
		assertEquals(pet, george.getPet("cAt1", false));
		assertNull(george.getPet("cat1", true));
	}

	@Test
	void testGetNotNewPet() throws IllegalAccessException {
		Set<Pet> pets = new HashSet<>();
		Pet pet = new Pet();
		pet.setName("cat1");
		pet.setId(1);
		pets.add(pet);
		Objects.requireNonNull(getPrivateFiled(george, "pets")).set(george, new HashSet<>(pets));
		assertEquals(pet, george.getPet("Cat1", false));
		assertEquals(pet, george.getPet("Cat1", true));
	}
}
