package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static  org.junit.Assert.*;
import java.util.*;

class OwnerTest {

	private List<Pet> petList;
	private String telephone;
	private Owner owner;
	private Pet pet;

	public void creatPetList(int numberOfPets)
	{
		for(int i = 0; i < numberOfPets; i++)	{
			Pet pet = new Pet();
			this.petList.add(pet);
			this.owner.addPet(pet);
		}
	}

	@BeforeEach
	public void setUp()
	{
		this.petList = new ArrayList<Pet>();
		this.owner = new Owner();
		this.pet = new Pet();

		System.out.println("Before");
	}

	@DisplayName("addPet")
	@Test
	public void TestAddPet()
	{
		//Execution
		this.petList.add(pet);
		this.owner.addPet(pet);
		//Result Verification
		Assertions.assertEquals(petList, owner.getPets());
		System.out.println("Test 1");
	}

	@DisplayName("removePet Positive")
	@Test
	public void TestRemovePetPositive()
	{
		//Delegated Setup
		creatPetList(3);
		//Execution
		Pet pet = this.petList.get(1);
		this.petList.remove(pet);
		this.owner.removePet(pet);
		//Result Verification
		Assertions.assertEquals(petList, owner.getPets());

		System.out.println("Test 2");
	}

	@DisplayName("removePet Negative")
	@Test
	public void TestRemovePetNegative()
	{
		creatPetList(3);

		this.owner.removePet(pet);


		System.out.println("Test 2");
	}


	@DisplayName("getPet Positive,ignoreNew = false")
	@Test
	public void TestGetPetPositive()
	{
		creatPetList(3);

		this.petList.get(0).setName("Hoshang");
		this.petList.get(1).setName("Pishtol");
		this.petList.get(2).setName("Soltan");

		Assertions.assertEquals(this.petList.get(1), owner.getPet("pishtol", false));

		System.out.println("Test 2");
	}

	@DisplayName("getPet Negative, ignoreNew = false")
	@Test
	public void TestGetPetNegative()
	{
		creatPetList(3);

		this.petList.get(0).setName("Hoshang");
		this.petList.get(1).setName("Pishtol");
		this.petList.get(2).setName("Soltan");

		Assertions.assertEquals(null, owner.getPet("shayat", false));

		System.out.println("Test 2");
	}

	@AfterEach
	public void tearDown()
	{
		this.petList = null;
		this.owner = null;
		this.pet = null;
		System.out.println("After");
	}
}
