package com.github.tddiaz.billdiscountservice.domain;

public class AffiliateDiscount extends PercentageDiscount {

    private static final double PERCENTAGE = 0.1; // as 10 percent

    public AffiliateDiscount() {
        super(PERCENTAGE);
    }

    public DiscountType getType() {
        return DiscountType.AFFILIATE;
    }
}
