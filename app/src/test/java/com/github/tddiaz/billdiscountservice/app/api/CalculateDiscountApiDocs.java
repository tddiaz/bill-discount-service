package com.github.tddiaz.billdiscountservice.app.api;

import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

class CalculateDiscountApiDocs {

    public static RequestFieldsSnippet apiRequestFields() {
        return requestFields(
            subsectionWithPath("grossAmount").type(OBJECT).description("bill gross amount"),
            fieldWithPath("grossAmount.value").type(NUMBER).description("bill gross amount value"),
            fieldWithPath("grossAmount.currency").type(STRING).description("bill gross amount currency. ISO 4217 format ex 'AED'"),
            subsectionWithPath("customer").type(OBJECT).description("bill payer"),
            fieldWithPath("customer.customerType").type(STRING).description("customer type. allowed values [EMPLOYEE, AFFILIATE, REGULAR]"),
            fieldWithPath("customer.joinedDate").type(STRING).description("customer joined date. ISO-8601 date format [YYYY-MM-DD] "),
            fieldWithPath("itemsCategory").type(STRING).description("Item category of bought goods. allowed values [GROCERIES, ELECTRONICS]")
        );
    }

    public static ResponseFieldsSnippet apiResponseFields() {
        return responseFields(
            subsectionWithPath("grossAmount").description("bill gross amount"),
            fieldWithPath("grossAmount.value").type(NUMBER).description("bill gross amount value"),
            fieldWithPath("grossAmount.currency").type(STRING).description("bill gross amount currency. ISO 4217 format ex 'AED'"),
            subsectionWithPath("netPayableAmount").description("bill net payable amount"),
            fieldWithPath("netPayableAmount.value").type(NUMBER).description("bill net payable amount value"),
            fieldWithPath("netPayableAmount.currency").type(STRING).description("bill net payable amount currency. ISO 4217 format ex 'AED'"),
            fieldWithPath("appliedDiscounts[].type").type(STRING).description("type of discount applied"),
            fieldWithPath("appliedDiscounts[].amount").type(OBJECT).description("discounted amount"),
            fieldWithPath("appliedDiscounts[].amount.value").type(NUMBER).description("discounted amount value"),
            fieldWithPath("appliedDiscounts[].amount.currency").type(STRING).description("discounted amount currency")
        );
    }

    public static ResponseFieldsSnippet errorResponseFields() {
        return responseFields(
                fieldWithPath("[].fieldName").type(STRING).description("name of the field with error").optional(),
                fieldWithPath("[].errorMessages").type(ARRAY).description("error message").optional()
        );
    }
}
