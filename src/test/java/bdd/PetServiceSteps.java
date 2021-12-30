package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PetServiceSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	private Owner owner;
	private Pet newPet;
	private PetType petType;


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

}
