package org.springframework.samples.petclinic.model.priceCalculators;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.UserType;

import java.util.List;

public interface PriceCalculator {
    double calcPrice(List<Pet> pets, double baseCharge, double basePricePerPet, UserType userType);
}
