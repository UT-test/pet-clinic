package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class OwnerTest {
	@Test
	void testSetAndGetAddress() {
		Owner owner = new Owner();
		owner.setAddress("myaddress");
		assertEquals("myaddress",owner.getAddress());
	}

	@Test
	void testSetAndGetCity() {
		Owner owner = new Owner();
		owner.setCity("tehran");
		assertEquals("tehran", owner.getCity());
	}

	@Test
	void testSetAndGetTelephone() {
		Owner owner = new Owner();
		owner.setTelephone("9213208201");
		assertEquals("9213208201", owner.getTelephone());
	}

	@Test
	void testAddGetRemovePets() {
		Owner owner = new Owner();
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		pet1.setName("fandogh");
		pet2.setName("peste");
		owner.addPet(pet1);
		assertEquals(1 , owner.getPets().size());
		owner.addPet(pet2);
		assertEquals(2 , owner.getPets().size());
		owner.removePet(pet2);
		assertEquals(1 , owner.getPets().size());
		assertEquals(owner.getPet("fandogh",false).getId(), pet1.getId());
	}
}
