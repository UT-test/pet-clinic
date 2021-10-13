package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class PetServiceTest {
	private PetService petService;
	private Pet pet1, pet2, pet3;
	private Owner owner;
	private PetTimedCache petTimedCache;
	private Logger criticalLogger;

	public PetServiceTest(){}

	void setUp(){
		petService = new PetService(petTimedCache, null , criticalLogger);
		owner = new Owner();
		petService.savePet(pet1, owner);
		petService.savePet(pet2, owner);
		petService.savePet(pet3, owner);
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

}
