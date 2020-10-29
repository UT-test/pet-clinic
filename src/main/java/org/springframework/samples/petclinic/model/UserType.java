package org.springframework.samples.petclinic.model;

public enum UserType {
    NEW(0.95),
    SILVER(0.90),
    GOLD(0.80);

    public final double discountRate;

    private UserType(double discountRate) {
        this.discountRate = discountRate;
    }
}
