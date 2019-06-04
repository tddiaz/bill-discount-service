package com.github.tddiaz.billdiscountservice.domain;

import com.github.tddiaz.billdiscountservice.domain.exception.DomainViolationException;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class BillTest {

    private static final MonetaryAmount AMOUNT = Money.of(BigDecimal.valueOf(1000), "USD");
    private static final LocalDate NOW = LocalDate.now();

    @Test
    public void whenCreate_shouldReturnBill() {
        Customer customer = new Customer(CustomerType.REGULAR, NOW);
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.GROCERIES, customer);

        assertThat(bill.getGrossAmount()).isEqualTo(AMOUNT);
        assertThat(bill.getItemsCategory()).isEqualTo(ItemsCategory.GROCERIES);
        assertThat(bill.getCustomer()).isEqualTo(customer);
    }

    @Test
    public void givenBillOfGroceries_whenGetPercentageDiscountType_returnNotApplicable() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.GROCERIES, new Customer(CustomerType.REGULAR, NOW));
        assertThat(bill.getApplicablePercentageDiscount()).isEqualTo(DiscountType.NOT_APPLICABLE);
    }

    @Test
    public void givenBillOfEmployee_whenGetPercentageDiscountType_returnEmployeeDiscountType() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.EMPLOYEE, NOW));
        assertThat(bill.getApplicablePercentageDiscount()).isEqualTo(DiscountType.EMPLOYEE);
    }

    @Test
    public void givenBillOfAffiliate_whenGetPercentageDiscountType_returnAffiliateDiscountType() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.AFFILIATE, NOW));
        assertThat(bill.getApplicablePercentageDiscount()).isEqualTo(DiscountType.AFFILIATE);
    }

    @Test
    public void givenBillOfNewRegularCustomer_whenGetPercentageDiscountType_returnNotApplicable() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.REGULAR, NOW));
        assertThat(bill.getApplicablePercentageDiscount()).isEqualTo(DiscountType.NOT_APPLICABLE);
    }

    @Test
    public void givenBillOfRegularCustomerOverTwoYearsSinceJoinDate_whenGetPercentageDiscountType_returnLoyaltyDiscountType() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.REGULAR, LocalDate.of(2017, 01, 01)));
        assertThat(bill.getApplicablePercentageDiscount()).isEqualTo(DiscountType.LOYALTY);
    }

    @Test
    public void givenDiscount_whenApplyDiscountOnBill_shouldSetNetPayableAmountAndDiscountsApplied() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.EMPLOYEE, NOW));
        Discount discount = new EmployeeDiscount();

        bill.applyDiscount(discount);

        assertThat(bill.getNetPayableAmount()).isNotNull();
        assertThat(bill.getDiscountsApplied()).contains(discount);
    }

    @Test
    public void givenMultipleDiscount_whenApplyDiscountOnBill_shouldSetNetPayableAmountAndDiscountsApplied() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.EMPLOYEE, NOW));
        Discount employeeDiscount = new EmployeeDiscount();
        Discount netDenominationDiscount = new NetDenominationDiscount();

        bill.applyDiscount(employeeDiscount);
        bill.applyDiscount(netDenominationDiscount);

        assertThat(bill.getNetPayableAmount()).isNotNull();
        assertThat(bill.getDiscountsApplied()).contains(employeeDiscount, netDenominationDiscount);
    }

    @Test(expected = DomainViolationException.class)
    public void givenMultiplePercentageDiscount_whenApplyDiscountOnBill_shouldThrowError() {
        Bill bill = Bill.valueOf(AMOUNT, ItemsCategory.ELECTRONICS, new Customer(CustomerType.EMPLOYEE, NOW));
        Discount employeeDiscount = new EmployeeDiscount();
        Discount affiliateDiscount = new AffiliateDiscount();

        bill.applyDiscount(employeeDiscount);
        bill.applyDiscount(affiliateDiscount);
    }
}