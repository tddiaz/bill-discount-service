package com.github.tddiaz.billdiscountservice.app.api;

import com.github.tddiaz.billdiscountservice.app.api.data.BillDto;
import com.github.tddiaz.billdiscountservice.app.api.data.DiscountedBillDto;
import com.github.tddiaz.billdiscountservice.app.api.data.FieldErrorsDto;
import com.github.tddiaz.billdiscountservice.domain.DiscountCalculator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class BillDiscountController {

    @PostMapping("/bills/calculate-discount")
    @ResponseStatus(HttpStatus.OK)
    public DiscountedBillDto calculateDiscounts(@RequestBody @Valid BillDto billDto) {
        LOGGER.info("received request: {}", billDto);
        return DiscountedBillDto.valueOf(DiscountCalculator.calculate(BillDto.toDomain(billDto)));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<FieldErrorsDto> handleException(MethodArgumentNotValidException ex) {

        final MultiValuedMap<String, String> fieldErrorsMap = new ArrayListValuedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> fieldErrorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return fieldErrorsMap.asMap().entrySet()
                .stream()
                .map(e -> new FieldErrorsDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
