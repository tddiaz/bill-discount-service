package com.github.tddiaz.billdiscountservice.app.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiscountDto {
    private String type;
    private AmountDto amount;
}
