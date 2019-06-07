package com.github.tddiaz.billdiscountservice.app.api.data;

import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AmountDto {

    @NotNull(message = "amount value is required")
    private BigDecimal value;

    @NotBlank(message = "currency is required")
    @CurrencyCode
    private String currency;
}
