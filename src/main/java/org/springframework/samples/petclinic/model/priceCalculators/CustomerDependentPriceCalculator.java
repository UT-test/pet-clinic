package org.springframework.samples.petclinic.model.priceCalculators;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.UserType;

import java.util.List;

public class CustomerDependentPriceCalculator implements PriceCalculator {

    private static int INFANT_YEARS = 2;
    private static double RARE_INFANCY_COEF = 1.4;
    private static double COMMON_INFANCY_COEF = 1.2;
    private static double BASE_RARE_COEF = 1.2;
    private static int DISCOUNT_MIN_SCORE = 10;

    @Override
    public double calcPrice(List<Pet> pets, double baseCharge, double basePricePerPet, UserType userType) {
        double totalPrice = 0;
        int discountCounter = 0;
        DateTime today = new DateTime();

        for (Pet pet : pets) {
            double price = 0;
            int age = Years.yearsBetween(new DateTime(pet.getBirthDate()), today).getYears();
            boolean isRare = pet.getType().getRare();

            if (isRare) {
                price = basePricePerPet * BASE_RARE_COEF;
                if (age <= INFANT_YEARS) {
                    price = price * RARE_INFANCY_COEF;
                    discountCounter = discountCounter + 2;
                } else {
                    discountCounter = discountCounter + 1;
                }
            } else {
                price = basePricePerPet;
                if (age <= INFANT_YEARS) {
                    price = price * COMMON_INFANCY_COEF;
                }
                discountCounter++;
            }

            totalPrice += price;
        }

        if (discountCounter >= DISCOUNT_MIN_SCORE) {
            if (userType == UserType.NEW) {
                totalPrice = (totalPrice * userType.discountRate) + baseCharge;
            } else {
                totalPrice = (totalPrice + baseCharge) * userType.discountRate;
            }
        } else if(userType == UserType.GOLD) {
            totalPrice = (totalPrice * userType.discountRate) + baseCharge;
        }

        return totalPrice;
    }
}
