package com.github.tddiaz.billdiscountservice.app.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.ItemsCategory;
import com.github.tddiaz.billdiscountservice.domain.Bill;
import lombok.Data;
import org.javamoney.moneta.Money;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillDto {

    @Valid
    @NotNull(message = "gross amount is required.")
    private AmountDto grossAmount;

    @NotBlank(message = "items category is required.")
    @ItemsCategory
    private String itemsCategory;

    @Valid
    @NotNull(message = "customer data is required.")
    private CustomerDto customer;

    public static Bill toDomain(BillDto dto) {
        return Bill.valueOf(Money.of(dto.getGrossAmount().getValue(), dto.getGrossAmount().getCurrency()),
                com.github.tddiaz.billdiscountservice.domain.ItemsCategory.valueOf(dto.getItemsCategory()),
                CustomerDto.toDomain(dto.getCustomer()));
    }
}
