package org.springframework.samples.petclinic.model.priceCalculators;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.UserType;

import java.util.List;

public class SimplePriceCalculator implements PriceCalculator {

    private static final double BASE_RARE_COEF = 1.2;

    @Override
    public double calcPrice(List<Pet> pets, double baseCharge, double basePricePerPet, UserType userType) {
        double totalPrice = baseCharge;

        for (int i=0; i<pets.size(); i++) {
            double price = 0;

            if (pets.get(i).getType().getRare()) {
                price = basePricePerPet * BASE_RARE_COEF;
            } else {
                price = basePricePerPet;
            }
            totalPrice += price;
        }

        if (userType == UserType.NEW) {
                totalPrice = totalPrice * userType.discountRate;
        }

        return totalPrice;
    }
}
