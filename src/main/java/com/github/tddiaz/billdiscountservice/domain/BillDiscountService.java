package com.github.tddiaz.billdiscountservice.domain;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class BillDiscountService {


    public DiscountedBill calculateDiscount(Discountable discountable) {
        return null;
    }

    interface Discountable {
        String getBillReference();
        BigDecimal getAmount();
        String getItemsCategory();
        String getCustomerType();
        LocalDate getJoinedDate();
    }
}
