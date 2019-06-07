package com.github.tddiaz.billdiscountservice.domain;

import com.github.tddiaz.billdiscountservice.domain.exception.DomainViolationException;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
public class Bill {
    private MonetaryAmount grossAmount;
    private ItemsCategory itemsCategory;
    private Customer customer;
    private MonetaryAmount netPayableAmount;
    private Set<Discount> discountsApplied;

    private Bill(MonetaryAmount grossAmount, ItemsCategory itemsCategory, Customer customer) {
        this.grossAmount = grossAmount;
        this.itemsCategory = itemsCategory;
        this.customer = customer;
    }

    public static Bill valueOf(@NonNull MonetaryAmount grossAmount, @NonNull ItemsCategory itemsCategory, @NonNull Customer customer) {
        return new Bill(grossAmount, itemsCategory, customer);
    }

    public DiscountType getApplicablePercentageDiscount() {

        if (itemsCategory == ItemsCategory.GROCERIES) {
            return DiscountType.NOT_APPLICABLE;
        }

        switch (customer.getType()) {
            case AFFILIATE:
                return DiscountType.AFFILIATE;
            case EMPLOYEE:
                return DiscountType.EMPLOYEE;
            case REGULAR:
                if (customer.numberOfYearsSinceJoinDate() >= 2) return DiscountType.LOYALTY;
            default:
                return DiscountType.NOT_APPLICABLE;
        }
    }

    Bill applyDiscount(Discount discount) {

        if (discount instanceof PercentageDiscount) {
            throwIfPercentageDiscountIsAlreadyApplied();
        }

        this.netPayableAmount = discount.calculate(Objects.isNull(netPayableAmount) ? grossAmount : netPayableAmount);
        addDiscountApplied(discount);

        return this;
    }


    private void addDiscountApplied(Discount discount) {
        if (CollectionUtils.isEmpty(discountsApplied)) {
            this.discountsApplied = new HashSet<>();
        }

        this.discountsApplied.add(discount);
    }

    private void throwIfPercentageDiscountIsAlreadyApplied() {
        if (CollectionUtils.isEmpty(discountsApplied)) {
            return;
        }

        if (discountsApplied.stream().anyMatch(dc -> dc instanceof PercentageDiscount)) {
            throw new DomainViolationException("Percentage discount was already applied");
        }
    }
}
