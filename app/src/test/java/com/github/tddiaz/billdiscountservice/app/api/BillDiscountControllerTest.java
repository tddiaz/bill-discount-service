package com.github.tddiaz.billdiscountservice.app.api;

import com.github.tddiaz.billdiscountservice.app.api.data.AmountDto;
import com.github.tddiaz.billdiscountservice.app.api.data.BillDto;
import com.github.tddiaz.billdiscountservice.app.api.data.CustomerDto;
import com.github.tddiaz.billdiscountservice.app.api.data.DiscountDto;
import com.github.tddiaz.billdiscountservice.app.api.data.DiscountedBillDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BillDiscountControllerTest {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .create();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Rule
    public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.jUnitRestDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(30080)
                ).build();
    }

    @Test
    public void givenValidRequest_whenPost_shouldReturnDiscountedBill() throws Exception {

        BillDto billDto = new BillDto();
        billDto.setGrossAmount(new AmountDto(BigDecimal.valueOf(1000), "USD"));
        billDto.setCustomer(new CustomerDto("EMPLOYEE", LocalDate.of(2018, 01, 01)));
        billDto.setItemsCategory("ELECTRONICS");

        MvcResult mvcResult = this.mockMvc.perform(
                post("/bills/calculate-discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GSON.toJson(billDto)))
                .andExpect(status().isOk())
                .andDo(document("calculate-discount", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        CalculateDiscountApiDocs.apiRequestFields(),
                        CalculateDiscountApiDocs.apiResponseFields()
                )).andReturn();

        DiscountedBillDto discountedBillDto = GSON.fromJson(mvcResult.getResponse().getContentAsString(), DiscountedBillDto.class);
        assertThat(discountedBillDto.getGrossAmount()).isEqualTo(billDto.getGrossAmount());
        assertThat(discountedBillDto.getNetPayableAmount()).isEqualTo(new AmountDto(BigDecimal.valueOf(665), "USD"));
        assertThat(discountedBillDto.getAppliedDiscounts()).hasSize(2);
        assertThat(discountedBillDto.getAppliedDiscounts())
                .containsAnyOf(
                        new DiscountDto("NET_DENOMINATION", new AmountDto(BigDecimal.valueOf(35), "USD")),
                        new DiscountDto("EMPLOYEE", new AmountDto(BigDecimal.valueOf(300), "USD"))
                );

    }

    @Test
    public void givenInValidRequest_whenPost_shouldReturnErrorMessages() throws Exception {

        BillDto billDto = new BillDto();
        billDto.setGrossAmount(new AmountDto(null, "INVALID"));
        billDto.setCustomer(new CustomerDto("INVALID", LocalDate.now()));

        this.mockMvc.perform(
                post("/bills/calculate-discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GSON.toJson(billDto)))
                .andExpect(status().isBadRequest())
                .andDo(document("calculate-discount-error", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        CalculateDiscountApiDocs.errorResponseFields()
                ));
    }
}