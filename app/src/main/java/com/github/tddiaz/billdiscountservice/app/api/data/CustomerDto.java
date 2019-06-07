package com.github.tddiaz.billdiscountservice.app.api.data;

import com.github.tddiaz.billdiscountservice.app.api.validation.constraints.CustomerType;
import com.github.tddiaz.billdiscountservice.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotBlank(message = "customer type is required")
    @CustomerType
    private String customerType;

    @NotNull(message = "customer joined date is required")
    @Past(message = "customer joined date should be past date")
    private LocalDate joinedDate;

    public static Customer toDomain(CustomerDto dto) {
        return new Customer(com.github.tddiaz.billdiscountservice.domain.CustomerType.valueOf(dto.getCustomerType()), dto.getJoinedDate());
    }
}
