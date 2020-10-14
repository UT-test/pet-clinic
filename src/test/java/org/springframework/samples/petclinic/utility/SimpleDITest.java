package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.PetRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SimpleDITest {

	@Test
	public void sampleTest() {
		SimpleDI simpleDi = SimpleDI.getInstance();
		PetRepository petRepoMock = mock(PetRepository.class);
		simpleDi.put(PetRepository.class, petRepoMock);
		PetRepository diProvidedRepo = (PetRepository) simpleDi.get(PetRepository.class);
		assertEquals(petRepoMock, diProvidedRepo);
	}
}
