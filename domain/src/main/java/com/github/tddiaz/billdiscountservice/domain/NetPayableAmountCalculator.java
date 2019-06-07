package com.github.tddiaz.billdiscountservice.domain;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public final class NetPayableAmountCalculator {

    private Map<DiscountType, PercentageDiscount> percentageDiscountsMap;
    private NetDenominationDiscount netDenominationDiscount;

    private NetPayableAmountCalculator() {
        this.percentageDiscountsMap = new HashMap<>();
        this.percentageDiscountsMap.put(DiscountType.AFFILIATE, new AffiliateDiscount());
        this.percentageDiscountsMap.put(DiscountType.EMPLOYEE, new EmployeeDiscount());
        this.percentageDiscountsMap.put(DiscountType.LOYALTY, new LoyaltyDiscount());

        this.netDenominationDiscount = new NetDenominationDiscount();
    }


    public static Bill calculate(@NonNull Bill bill) {
        return new NetPayableAmountCalculator().calc(bill);
    }

    private Bill calc(Bill bill) {

        final DiscountType percentageDiscountType = bill.getApplicablePercentageDiscount();

        if (percentageDiscountType != DiscountType.NOT_APPLICABLE) {
            bill.applyDiscount(percentageDiscountsMap.get(percentageDiscountType));
        }

        return bill.applyDiscount(netDenominationDiscount);
    }
}
