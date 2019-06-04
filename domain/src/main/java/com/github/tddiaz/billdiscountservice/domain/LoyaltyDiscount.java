package com.github.tddiaz.billdiscountservice.domain;

public class LoyaltyDiscount extends PercentageDiscount {

    private static final double PERCENTAGE = 0.05; // as 5 percent

    public LoyaltyDiscount() {
        super(PERCENTAGE);
    }

    @Override
    public DiscountType getType() {
        return DiscountType.LOYALTY;
    }
}
