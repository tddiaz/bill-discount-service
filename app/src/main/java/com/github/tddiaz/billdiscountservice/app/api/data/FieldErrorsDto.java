package com.github.tddiaz.billdiscountservice.app.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldErrorsDto {
    private String fieldName;
    private Collection<String> errorMessages;
}
