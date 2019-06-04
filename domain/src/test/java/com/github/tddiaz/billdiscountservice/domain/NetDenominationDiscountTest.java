package com.github.tddiaz.billdiscountservice.domain;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class NetDenominationDiscountTest {

    @Test
    public void givenAmount100_whenCalculate_returnDiscountValue95() {
        NetDenominationDiscount discount = new NetDenominationDiscount();
        MonetaryAmount netAmount = discount.calculate(Money.of(BigDecimal.valueOf(100), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(new BigDecimal(95));
        assertThat(discount.getDiscountedAmount()).isEqualTo(Money.of(5, "USD"));
    }

    @Test
    public void givenAmount99_whenCalculate_returnDiscountValue99() {
        NetDenominationDiscount discount = new NetDenominationDiscount();
        MonetaryAmount netAmount = discount.calculate(Money.of(BigDecimal.valueOf(99), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(99));
        assertThat(discount.getDiscountedAmount()).isEqualTo(Money.of(0, "USD"));
    }

    @Test
    public void givenAmount101_whenCalculate_returnDiscountValue96() {
        NetDenominationDiscount discount = new NetDenominationDiscount();
        MonetaryAmount netAmount = discount.calculate(Money.of(BigDecimal.valueOf(101), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(96));
        assertThat(discount.getDiscountedAmount()).isEqualTo(Money.of(5, "USD"));
    }

    @Test
    public void givenAmount101_99_whenCalculate_returnDiscountValue96_99() {
        NetDenominationDiscount discount = new NetDenominationDiscount();
        MonetaryAmount netAmount = discount.calculate(Money.of(BigDecimal.valueOf(101.99), "USD"));

        assertThat(MoneyUtils.getBigDecimal(netAmount.getNumber())).isEqualTo(BigDecimal.valueOf(96.99));
        assertThat(discount.getDiscountedAmount()).isEqualTo(Money.of(5, "USD"));
    }

    @Test
    public void givenAmount99_99_whenCalculate_returnDiscountValue99_99() {
        NetDenominationDiscount discount = new NetDenominationDiscount();
        MonetaryAmount netAmount = discount.calculate(Money.of(BigDecimal.valueOf(99.99), "USD"));

        assertThat(netAmount).isEqualTo(Money.of(BigDecimal.valueOf(99.99), "USD"));
        assertThat(discount.getDiscountedAmount()).isEqualTo(Money.of(0, "USD"));
    }

    @Test
    public void testGetDiscountType() {
        NetDenominationDiscount discount = new NetDenominationDiscount();
        assertThat(discount.getType()).isEqualTo(DiscountType.NET_DENOMINATION);
    }
}