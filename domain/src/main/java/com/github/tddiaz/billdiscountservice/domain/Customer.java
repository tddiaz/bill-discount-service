package com.github.tddiaz.billdiscountservice.domain;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

@Getter
public class Customer {
    private CustomerType type;
    private LocalDate joinedDate;

    public Customer(@NonNull CustomerType type, @NonNull LocalDate joinedDate) {
        this.type = type;
        this.joinedDate = joinedDate;
    }

    public long numberOfYearsSinceJoinDate() {
        return YEARS.between(this.joinedDate, LocalDate.now());
    }
}
