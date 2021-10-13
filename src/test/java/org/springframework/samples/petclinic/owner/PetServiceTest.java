package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetServiceTest {
	private Pet pet1, pet2, pet3;
	private Owner owner;
	@Autowired
	private PetService petService;
	@MockBean
	private PetRepository petRepository;

	void setUp(){
		owner = new Owner();
		petService.savePet(pet1, owner);
		petService.savePet(pet2, owner);
		petService.savePet(pet3, owner);
		when(petRepository.findById(1)).thenReturn(pet1);
		when(petRepository.findById(2)).thenReturn(pet2);
		when(petRepository.findById(3)).thenReturn(pet3);

	}

	void setUpPets(){
		pet1 = new Pet();
		pet1.setName("pet1");
		pet1.setId(1);

		pet2 = new Pet();
		pet2.setName("pet2");
		pet2.setId(2);

		pet3 = new Pet();
		pet3.setName("pet3");
		pet3.setId(3);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3})
	public void Pet_with_an_specific_id_is_found_and_returned_correctly(int expectedId){
		setUpPets();
		setUp();
		Pet actualPet = petService.findPet(expectedId);
		assertNotNull(actualPet);
		assertEquals("pet" + expectedId, actualPet.getName());
	}

}
