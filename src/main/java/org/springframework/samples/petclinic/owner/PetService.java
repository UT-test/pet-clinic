package org.springframework.samples.petclinic.owner;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.stereotype.Service;

@Service
public class PetService {

	private final PetTimedCache pets;

	private final OwnerRepository owners;

	private final Logger log;

	@Autowired
	public PetService(
		PetTimedCache pets,
		OwnerRepository owners,
		Logger criticalLogger) {
		this.pets = pets;
		this.owners = owners;
		this.log = criticalLogger;
	}

	public Owner findOwner(int ownerId) {
		log.info("find owner {}", ownerId);
		return this.owners.findById(ownerId);
	}

	public Pet newPet(Owner owner) {
		log.info("add pet for owner {}", owner.getId());
		Pet pet = new Pet();
		owner.addPet(pet);
		return pet;
	}

	public Pet findPet(int petId) {
		log.info("find pet by id {}", petId);
		return this.pets.get(petId);
	}

	public void savePet(Pet pet, Owner owner) {
		log.info("save pet {}", pet.getId());
		owner.addPet(pet);
		this.pets.save(pet);
	}

}
