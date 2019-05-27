package com.github.tddiaz.billdiscountservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
    private CustomerType type;
    private LocalDate joinedDate;

    public Customer of(CustomerType type, LocalDate joinedDate) {
        return new Customer(type, joinedDate);
    }
}
