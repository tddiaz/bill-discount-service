package com.github.tddiaz.billdiscountservice.domain;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AffiliateDiscountTest {

    @Test
    public void givenAmount1000_whenCalculate_shouldReturnDiscountedValue900() {
        PercentageDiscount affiliateDiscount = new AffiliateDiscount();
        MonetaryAmount netAmount = affiliateDiscount.calculate(Money.of(BigDecimal.valueOf(1000), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(900));
        assertThat(affiliateDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(100), "USD"));
    }

    @Test
    public void givenAmount1000_1_whenCalculate_shouldReturnDiscountedValue900_09() {
        PercentageDiscount affiliateDiscount = new AffiliateDiscount();
        MonetaryAmount netAmount = affiliateDiscount.calculate(Money.of(BigDecimal.valueOf(1000.1), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(900.09));
        assertThat(affiliateDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(100.01), "USD"));
    }

    @Test
    public void givenAmount999_whenCalculate_shouldReturnDiscountedValue899_1() {
        PercentageDiscount affiliateDiscount = new AffiliateDiscount();
        MonetaryAmount netAmount = affiliateDiscount.calculate(Money.of(BigDecimal.valueOf(999), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(899.1));
        assertThat(affiliateDiscount.getDiscountedAmount()).isEqualTo(Money.of(BigDecimal.valueOf(99.9), "USD"));
    }

    @Test
    public void testGetDiscountType() {
        PercentageDiscount affiliateDiscount = new AffiliateDiscount();
        assertThat(affiliateDiscount.getType()).isEqualTo(DiscountType.AFFILIATE);
    }

    @Test
    public void testGetPercentage() {
        PercentageDiscount affiliateDiscount = new AffiliateDiscount();
        assertThat(affiliateDiscount.getPercentage()).isEqualTo(0.1);
    }
}