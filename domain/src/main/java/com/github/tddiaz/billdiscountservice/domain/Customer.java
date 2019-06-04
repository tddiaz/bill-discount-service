package com.github.tddiaz.billdiscountservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

@Getter
@RequiredArgsConstructor
public class Customer {
    private final CustomerType type;
    private final LocalDate joinedDate;

    public long numberOfYearsSinceJoinDate() {
        return YEARS.between(this.joinedDate, LocalDate.now());
    }
}
