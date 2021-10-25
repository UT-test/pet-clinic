package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetManagerTest {

	@Mock
	PetTimedCache pets;
	@Mock
	OwnerRepository owners;
	@Mock
	Logger log;
	@InjectMocks
	private PetManager pet_manager;

	// Mockisty, State verification
	@Test
	public void TestFindOwnerNotNull() {
		Owner owner = mock(Owner.class);
		owner.setId(1);
		when(owners.findById(1)).thenReturn(owner);
		assertEquals(owner, pet_manager.findOwner(1));
	}

	// Mockisty, State verification
	@Test
	public void TestFindOwnerNull() {
		when(owners.findById(Mockito.anyInt())).thenReturn(null);
		assertNull(pet_manager.findOwner(1));
	}

	// Mockisty, Behavior verification
	@Test
	public void TestNewPet() {
		Owner owner = mock(Owner.class);
		pet_manager.newPet(owner);
		verify(log).info("add pet for owner {}", owner.getId());
		verify(owner).addPet(isA(Pet.class));
	}

	// Mockisty, State and Behavior ???? verification
	@Test
	public void TestFindPetNotNull() {
		Pet pet = mock(Pet.class);
		pet.setId(1);
		when(pets.get(1)).thenReturn(pet);
		assertEquals(pet, pet_manager.findPet(1));
		verify(log).info("find pet by id {}", 1);
	}

	// Mockisty, State verification
	@Test
	public void TestFindPetNull() {
		when(pets.get(Mockito.anyInt())).thenReturn(null);
		assertNull(pet_manager.findPet(1));
	}

	// Mockisty, Behavior verification
	@Test
	public void TestSavePet() {
		Owner owner = mock(Owner.class);
		Pet pet = mock(Pet.class);
		pet_manager.savePet(pet, owner);
		verify(log).info("save pet {}", pet.getId());
		verify(owner).addPet(pet);
		verify(pets).save(pet);
	}
}
