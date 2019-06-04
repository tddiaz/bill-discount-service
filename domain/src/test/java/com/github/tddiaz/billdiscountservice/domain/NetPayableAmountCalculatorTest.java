package com.github.tddiaz.billdiscountservice.domain;

import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class NetPayableAmountCalculatorTest {

    private static final MonetaryAmount AMOUNT = Money.of(BigDecimal.valueOf(1000), "USD");
    private static final LocalDate NOW = LocalDate.now();

    @Test(expected = IllegalArgumentException.class)
    public void givenNullBill_whenCalculate_shouldThrowError() {
        NetPayableAmountCalculator.calculate(null);
    }

    @Test
    public void givenBillNotApplicableForPercentageDiscount_whenCalculate_shouldReturnBillWithNetPayableAmount() {
        Bill bill = NetPayableAmountCalculator
                .calculate(Bill.valueOf(AMOUNT, ItemsCategory.GROCERIES, new Customer(CustomerType.REGULAR, NOW)));

        // net denomination discount only applied
        assertThat(bill.getNetPayableAmount()).isEqualTo(Money.of(BigDecimal.valueOf(950), "USD"));
    }

    @Test
    public void givenBillApplicableForPercentageDiscount_whenCalculate_shouldReturnBillWithNetPayableAmount() {
        Bill bill = NetPayableAmountCalculator
                .calculate(Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.EMPLOYEE, NOW)));

        // employee discount and net denomination discount was applied
        assertThat(bill.getNetPayableAmount()).isEqualTo(Money.of(BigDecimal.valueOf(665), "USD"));
    }
}