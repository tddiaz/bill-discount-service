package com.github.tddiaz.billdiscountservice.domain;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class LoyaltyDiscountTest {


    @Test
    public void givenAmount1000_whenCalculate_shouldReturnDiscountedValue950() {
        PercentageDiscount loyaltyDiscount = new LoyaltyDiscount();
        MonetaryAmount netAmount = loyaltyDiscount.calculate(Money.of(BigDecimal.valueOf(1000), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(950));
        assertThat(loyaltyDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(50), "USD"));
    }

    @Test
    public void givenAmount1000_1_whenCalculate_shouldReturnDiscountedValue950_095() {
        PercentageDiscount loyaltyDiscount = new LoyaltyDiscount();
        MonetaryAmount netAmount = loyaltyDiscount.calculate(Money.of(BigDecimal.valueOf(1000.1), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(950.095));
        assertThat(loyaltyDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(50.005), "USD"));
    }

    @Test
    public void givenAmount999_whenCalculate_shouldReturnDiscountedValue949_05() {
        PercentageDiscount loyaltyDiscount = new LoyaltyDiscount();
        MonetaryAmount netAmount = loyaltyDiscount.calculate(Money.of(BigDecimal.valueOf(999), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(949.05));
        assertThat(loyaltyDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(49.95), "USD"));
    }

    @Test
    public void testGetDiscountType() {
        PercentageDiscount loyaltyDiscount = new LoyaltyDiscount();
        assertThat(loyaltyDiscount.getType()).isEqualTo(DiscountType.LOYALTY);
    }

    @Test
    public void testGetPercentage() {
        PercentageDiscount loyaltyDiscount = new LoyaltyDiscount();
        assertThat(loyaltyDiscount.getPercentage()).isEqualTo(0.05);
    }
}