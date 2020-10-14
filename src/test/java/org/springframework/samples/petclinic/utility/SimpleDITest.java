package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.PetRepository;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

// todo pass these tests
class SimpleDITest {

	@Test
	public void testProvideByInstance() throws Exception {
		SimpleDI diContainer = SimpleDI.getDIContainer();
		PetRepository petRepoMock = mock(PetRepository.class);
		diContainer.provideByInstance(PetRepository.class, petRepoMock);
		PetRepository diProvidedRepo = (PetRepository) diContainer.getInstanceOf(PetRepository.class);
		assertEquals(petRepoMock, diProvidedRepo);
	}

	@Test
	public void testProvideByFunction() throws Exception {
		SimpleDI simpleDi = SimpleDI.getDIContainer();
		simpleDi.provideByAConstructorFunction(PetRepository.class, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return mock(PetRepository.class);
			}
		});
		assertNotNull(simpleDi.getInstanceOf(PetRepository.class));
	}
}
