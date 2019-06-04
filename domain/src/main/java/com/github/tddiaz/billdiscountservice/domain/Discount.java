package com.github.tddiaz.billdiscountservice.domain;

import lombok.Getter;

import javax.money.MonetaryAmount;

@Getter
public abstract class Discount {

    protected MonetaryAmount discountedAmount;

    public abstract DiscountType getType();
    public abstract MonetaryAmount calculate(MonetaryAmount amount);
}
