package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PetServiceSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTimedCache petTimedCache;

	@Autowired
	PetTypeRepository petTypeRepository;

	private Owner owner;
	private Pet newPet, pet;
	private PetType petType;

	@Before("@pet_service")
	public void setup() {
		petType = new PetType();
		petType.setName("cat");
		petTypeRepository.save(petType);
	}


	@Given("There is a pet owner with id {int}")
	public void thereIsAPetOwnerWithId(Integer id) {
		owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("M. Kelly");
		owner.setAddress("CA., Beverly Hills St., No. 132");
		owner.setCity("Los Angeles");
		owner.setTelephone("+12309876");
		owner.setId(id);
		ownerRepository.save(owner);
	}

	@When("Owner is looked up in pet service")
	public void lookedUpPetService() {
		petService.findOwner(owner.getId());
	}

	@Then("The owner is returned successfully")
	public void ownerIsFound() {
		assertNotNull(petService.findOwner(owner.getId()));
	}

	@When("Request for a new pet arrives")
	public void newPetRequestedPetService() {
		newPet = petService.newPet(owner);
	}

	@Then("The new pet is returned successfully")
	public void newPetReturnedPetService() { assertNotNull(newPet); }

	@Then("The new pet is saved in owners pets correctly")
	public void newPetIsSaved() {
		assertThat(owner.getPets()).contains(newPet);
	}


	@Given("There is a pet with id {int}")
	public void thereIsAPetWithId(Integer id) {
		pet = new Pet();
		pet.setName("Whiskers");
		pet.setType(petType);
		pet.setId(id);
		pet.setBirthDate(LocalDate.of(2020, 4, 7));
	}

	@Given("The pet has owner with id {int}")
	public void addPetToOwner(int ownerId) {
		thereIsAPetOwnerWithId(ownerId);
		owner.addPet(pet);
		petTimedCache.save(pet);
	}

	@Then("The pet is returned successfully")
	public void petIsFound() {
		assertNotNull(petTimedCache.get(pet.getId()));
	}

	@When("Owner performs save pet service to add a pet to his list")
	public void savePet() {
		petService.savePet(pet, owner);
	}

	@Then("The pet is saved into pet cache successfully")
	public void petIsSaved() {
		assertThat(petTimedCache.get(pet.getId()).toString()).isEqualTo(pet.toString());
	}

}
