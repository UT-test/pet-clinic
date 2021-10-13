package org.springframework.samples.petclinic.owner;

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

	void setUpType(){
		type = new PetType();
		type.setName("dog");
	}

	void setUpPet(){
		pet = new Pet();
		pet.setName("dog1");
		pet.setBirthDate(LocalDate.parse("2000-01-01"));
		pet.setType(type);
		pet.setId(1);
	}

	@DataPoint
	public static List<Visit> visits1() {
		List<Visit> visits = new ArrayList<>();
		Visit visit1, visit2, visit3;

		visit1 = new Visit();
		visit1.setId(1);
		visit1.setDate(LocalDate.parse("2000-01-05"));
		visit1.setPetId(1);
		visit1.setDescription("visit1.");
		visits.add(visit1);

		visit2 = new Visit();
		visit2.setId(2);
		visit2.setDate(LocalDate.parse("2000-01-06"));
		visit2.setPetId(1);
		visit2.setDescription("visit2.");
		visits.add(visit2);

		visit3 = new Visit();
		visit3.setId(3);
		visit3.setDate(LocalDate.parse("2000-01-03"));
		visit3.setPetId(1);
		visit3.setDescription("visit3.");
		visits.add(visit3);

		return visits;
	}

	@DataPoint
	public static List<Visit> visits2() {
		List<Visit> visits = new ArrayList<>();
		Visit visit1, visit2, visit3;

		visit1 = new Visit();
		visit1.setId(1);
		visit1.setDate(LocalDate.parse("2000-03-05"));
		visit1.setPetId(1);
		visit1.setDescription("visit1.");
		visits.add(visit1);

		visit2 = new Visit();
		visit2.setId(2);
		visit2.setDate(LocalDate.parse("2000-02-06"));
		visit2.setPetId(1);
		visit2.setDescription("visit2.");
		visits.add(visit2);

		visit3 = new Visit();
		visit3.setId(3);
		visit3.setDate(LocalDate.parse("2000-05-03"));
		visit3.setPetId(1);
		visit3.setDescription("visit3.");
		visits.add(visit3);

		return visits;
	}

	@Theory
	public void visits_are_sorted_by_their_date(List<Visit> expected){
		System.out.println("--------------------------------------------------------------");
		setUpType();
		setUpPet();
		expected.forEach(visit -> pet.addVisit(visit));
		expected.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
		System.out.println("Expected:");
		expected.forEach(visit -> System.out.println(visit.getDescription() + ":" + visit.getDate().toString()));
		List<Visit> actual = pet.getVisits();
		assertEquals(expected, actual);
		System.out.println("Actual:");
		actual.forEach(visit -> System.out.println(visit.getDescription() + ":" + visit.getDate().toString()));

	}
}
