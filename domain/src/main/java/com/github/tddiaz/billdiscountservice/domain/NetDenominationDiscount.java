package com.github.tddiaz.billdiscountservice.domain;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;

public class NetDenominationDiscount extends Discount {

    private static final long DENOMINATION = 100;
    private static final long DISCOUNT_VALUE = 5;

    @Override
    public MonetaryAmount calculate(MonetaryAmount amount) {

        final long numberOfDenominations = amount.getNumber().longValue() / DENOMINATION;

        if (numberOfDenominations == 0) {
            this.discountedAmount = Money.of(0, amount.getCurrency());
            return amount;
        }

        final MonetaryAmount discountAmount = Money.of(numberOfDenominations * DISCOUNT_VALUE, amount.getCurrency());
        this.discountedAmount = discountAmount;

        return amount.subtract(discountAmount);
    }

    @Override
    public DiscountType getType() {
        return DiscountType.NET_DENOMINATION;
    }
}
