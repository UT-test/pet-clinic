package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.PetRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

// todo pass these tests
class SimpleDITest {

	@Test
	public void testProvideByInstance() throws Exception {
		SimpleDI simpleDi = SimpleDI.getInstance();
		PetRepository petRepoMock = mock(PetRepository.class);
		simpleDi.provideByInstance(PetRepository.class, petRepoMock);
		PetRepository diProvidedRepo = (PetRepository) simpleDi.get(PetRepository.class);
		assertEquals(petRepoMock, diProvidedRepo);
	}

	@Test
	public void testProvideByFunction() throws Exception {
		SimpleDI simpleDi = SimpleDI.getInstance();
		simpleDi.provideByAConstructorFunction(PetRepository.class, () -> mock(PetRepository.class));
		assertNotNull(simpleDi.get(PetRepository.class));
	}
}
