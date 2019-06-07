package com.github.tddiaz.billdiscountservice.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.money.MonetaryAmount;

@RequiredArgsConstructor
@Getter
public abstract class PercentageDiscount extends Discount {

    private final double percentage;

    @Override
    public MonetaryAmount calculate(@NonNull MonetaryAmount amount) {
        final MonetaryAmount discountAmount = amount.multiply(percentage);
        this.discountedAmount = discountAmount;

        return amount.subtract(discountAmount);
    }
}
