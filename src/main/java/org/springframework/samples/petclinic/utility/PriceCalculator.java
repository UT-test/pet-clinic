package org.springframework.samples.petclinic.utility;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.util.ArrayList;
import java.util.List;

public class PriceCalculator {

	private static int INFANT_YEARS = 2;
	private static double RARE_INFANCY_COEF = 1.4;
	private static double BASE_RARE_COEF = 1.2;
	private static int DISCOUNT_MIN_SCORE = 10;
	private static int DISCOUNT_PRE_VISIT = 2;

	public static double calcPrice(List<Pet> pets, double baseCharge, double basePricePerPet) {
		double totalPrice = 0;
		int discountCounter = 0;
		LocalDate today = LocalDate.now();

		for (Pet pet : pets) {
			double price = 0;
			long age = YEARS.between(pet.getBirthDate(), today);
			long daysFromLastVisit = 0;
			List<Visit> visits = new ArrayList<>(pet.getVisitsUntilAge((int) age));
			if (visits.size() != 0)
				daysFromLastVisit = DAYS.between(visits.get(visits.size() - 1).getDate(), today);

			price = basePricePerPet * BASE_RARE_COEF;
			if (age <= INFANT_YEARS) {
				price = price * RARE_INFANCY_COEF;
				discountCounter = discountCounter + 2;
			} else {
				discountCounter = discountCounter + 1;
			}

			if (discountCounter >= DISCOUNT_MIN_SCORE) {
				if (daysFromLastVisit < 100) {
					totalPrice = (totalPrice * DISCOUNT_PRE_VISIT) + baseCharge;
				} else {
					totalPrice = (totalPrice + baseCharge) * (daysFromLastVisit / 100 + visits.size());
				}
			}
			totalPrice += price;
		}

		return totalPrice;
	}

}

