package com.github.tddiaz.billdiscountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DiscountedBill {
    private String reference;
    private DiscountType discountApplied;
    private BigDecimal grossPayableAmount;
    private BigDecimal netPayableAmount;
}
