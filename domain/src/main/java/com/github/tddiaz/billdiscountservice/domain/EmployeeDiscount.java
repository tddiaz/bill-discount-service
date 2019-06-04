package com.github.tddiaz.billdiscountservice.domain;

public class EmployeeDiscount extends PercentageDiscount {

    private static final double PERCENTAGE = 0.3; // as employee

    public EmployeeDiscount() {
        super(PERCENTAGE);
    }

    public DiscountType getType() {
        return DiscountType.EMPLOYEE;
    }
}
