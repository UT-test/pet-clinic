package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class PetServiceTest {
	private final PetService petService;
	private static Pet pet1, pet2, pet3;
	private final int expectedId;
	private final Pet expectedPet;

	public PetServiceTest(int id, Pet pet) {
		expectedId = id;
		expectedPet = pet;
		petService = mock(PetService.class);
	}

	public void setUp() {
		setUpPets();
		when(petService.findPet(1)).thenReturn(pet1);
		when(petService.findPet(2)).thenReturn(pet2);
		when(petService.findPet(3)).thenReturn(pet3);
	}

	void setUpPets() {
		pet1 = new Pet();
		pet1.setName("pet1");

		pet2 = new Pet();
		pet2.setName("pet2");

		pet3 = new Pet();
		pet3.setName("pet3");
	}

	@Parameters
	public static Collection<Object[]> pets() {
		pet1 = new Pet();
		pet1.setName("pet1");

		pet2 = new Pet();
		pet2.setName("pet2");

		pet3 = new Pet();
		pet3.setName("pet3");
		return Arrays.asList(new Object[][]{
			{1, pet1},
			{2, pet2},
			{3, pet3},
			{-1, pet2},
			{0, pet1},
			{1, null}
		});
	}

	@Test
	public void Pets_are_found_correctly(){
		assumeTrue(this.expectedId >= 1);
		assumeNotNull(this.expectedPet);
		setUp();
		System.out.println("------------------------------------------------------------");
		System.out.printf("parametrized test with id: %d\n", expectedId);
		Pet actualPet = petService.findPet(expectedId);
		assertNotNull(actualPet);
		assertEquals("pet" + expectedId, actualPet.getName());
		System.out.printf("pet %s with id: %d was found.\n", actualPet.getName(), expectedId);
	}
}
