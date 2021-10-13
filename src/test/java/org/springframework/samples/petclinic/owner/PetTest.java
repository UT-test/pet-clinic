package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.*;


@RunWith(Theories.class)
public class PetTest {
	private Pet pet;
	private PetType type;

	public PetTest(){}

	@BeforeEach
	public void setUp(){
		setUpType();
		setUpPet();
	}

	void setUpType(){
		type = new PetType();
		type.setName("dog");
	}

	void setUpPet(){
		pet = new Pet();
		pet.setName("dog1");
		pet.setBirthDate(LocalDate.parse("2000-01-01"));
		pet.setType(type);
	}
}
