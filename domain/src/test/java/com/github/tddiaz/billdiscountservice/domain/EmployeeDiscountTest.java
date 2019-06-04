package com.github.tddiaz.billdiscountservice.domain;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeDiscountTest {


    @Test
    public void givenAmount1000_whenCalculate_shouldReturnDiscountedValue700() {
        PercentageDiscount employeeDiscount = new EmployeeDiscount();
        MonetaryAmount netAmount = employeeDiscount.calculate(Money.of(BigDecimal.valueOf(1000), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(700));
        assertThat(employeeDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(300), "USD"));
    }

    @Test
    public void givenAmount1000_1_whenCalculate_shouldReturnDiscountedValue700_07() {
        PercentageDiscount employeeDiscount = new EmployeeDiscount();
        MonetaryAmount netAmount = employeeDiscount.calculate(Money.of(BigDecimal.valueOf(1000.1), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(700.07));
        assertThat(employeeDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(300.03), "USD"));
    }

    @Test
    public void givenAmount999_whenCalculate_shouldReturnDiscountedValue699_3() {
        PercentageDiscount employeeDiscount = new EmployeeDiscount();
        MonetaryAmount netAmount = employeeDiscount.calculate(Money.of(BigDecimal.valueOf(999), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(699.3));
        assertThat(employeeDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(299.7), "USD"));
    }

    @Test
    public void testGetDiscountType() {
        PercentageDiscount employeeDiscount = new EmployeeDiscount();
        assertThat(employeeDiscount.getType()).isEqualTo(DiscountType.EMPLOYEE);
    }

    @Test
    public void testGetPercentage() {
        PercentageDiscount employeeDiscount = new EmployeeDiscount();
        assertThat(employeeDiscount.getPercentage()).isEqualTo(0.3);
    }
}