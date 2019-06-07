package com.github.tddiaz.billdiscountservice.app.api.data;

import com.github.tddiaz.billdiscountservice.domain.Bill;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class DiscountedBillDto {
    private AmountDto grossAmount;
    private AmountDto netPayableAmount;
    private Set<DiscountDto> appliedDiscounts;

    public static DiscountedBillDto valueOf(Bill bill) {
        final DiscountedBillDto dto = new DiscountedBillDto();
        dto.setGrossAmount(AmountDto.valueOf(bill.getGrossAmount()));
        dto.setNetPayableAmount(AmountDto.valueOf(bill.getNetPayableAmount()));
        dto.setAppliedDiscounts(
                bill.getDiscountsApplied()
                        .stream()
                            .map(discount -> new DiscountDto(discount.getType().toString(), AmountDto.valueOf(discount.getDiscountedAmount())))
                            .collect(Collectors.toSet())
        );

        return dto;
    }
}
