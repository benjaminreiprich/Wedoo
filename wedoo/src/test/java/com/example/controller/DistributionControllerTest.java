package com.example.controller;

import com.example.dtos.DistributionDto;
import com.example.exception.CustomException;
import com.example.mapper.CompanyDtoMapper;
import com.example.service.CompanyService;

import com.example.service.DistributionService;
import com.example.utils.TestData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DistributionControllerTest {

    private MockMvc mockMvc;
    private DistributionService distributionService;
    private CompanyService companyService;
    private CompanyDtoMapper companyDtoMapper;
    private ObjectMapper objectMapper;

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            TestData.class);
    private TestData testData = (TestData) context.getBean("testDataBean");

    @BeforeEach
    void setUp() {
        distributionService = mock(DistributionService.class);
        companyDtoMapper = mock(CompanyDtoMapper.class);
        companyService = mock(CompanyService.class);
        DistributionController controller = new DistributionController(distributionService, companyService, companyDtoMapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomException())
                .build();
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }

    @Test
    void addGift_Should_return_BadRequest() throws Exception {
        when(distributionService.addGift(testData.distributionDtoGift)).thenThrow(CustomException.class);

        String result = mockMvc.perform(post("/distribution", testData.distributionDtoGift))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    void addGift_Should_return_OkAndDistributionDto() throws Exception {
        when(distributionService.addGift(testData.distributionDtoGift)).thenReturn(Optional.of(testData.distributionDtoGift));
        when(companyService.getById(1)).thenReturn(testData.companyDto);
        when(companyService.update(testData.companyDto)).thenReturn(testData.companyDto);

        String result = mockMvc.perform(post("/distribution")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(testData.distributionDtoGift)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        DistributionDto dto = objectMapper.readValue(result, DistributionDto.class);
        verify(distributionService, times(1)).addGift(testData.distributionDtoGift);
        verifyNoMoreInteractions(distributionService);

        assertThat(dto).isEqualTo(testData.distributionDtoGift);


    }





}
