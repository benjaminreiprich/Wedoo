package com.example.controller;

import com.example.dtos.UserDto;
import com.example.exception.CustomException;
import com.example.service.DistributionService;
import com.example.utils.TestData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;
    private DistributionService distributionService;
    private ObjectMapper objectMapper;

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            TestData.class);
    private TestData testData = (TestData) context.getBean("testDataBean");

    @BeforeEach
    void setUp() {
        distributionService = mock(DistributionService.class);
        UserController controller = new UserController(distributionService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomException())
                .build();
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }

    /*--------------------getGiftBalanceByUserId--------------------*/

    @Test
    void getGiftBalanceByUserId_should_return_status_ok_and_user_dto() throws Exception {
        int userId = 1;
        when(distributionService.getGiftCardsByUserId(1)).thenReturn(testData.userDto);

        String result = mockMvc.perform(get("/user/gift/balance/{userId}", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto dto = objectMapper.readValue(result, UserDto.class);

        verify(distributionService, times(1)).getGiftCardsByUserId(userId);
        verifyNoMoreInteractions(distributionService);

        assertThat(dto).isEqualTo(testData.userDto);
    }

    /*--------------------getMealBalanceByUserId--------------------*/

    @Test
    void getMealBalanceByUserId_should_return_status_ok_and_user_dto() throws Exception {
        int userId = 1;
        when(distributionService.getMealsByUserId(1)).thenReturn(testData.userDto);

        String result = mockMvc.perform(get("/user/meal/balance/{userId}", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto dto = objectMapper.readValue(result, UserDto.class);

        verify(distributionService, times(1)).getMealsByUserId(userId);
        verifyNoMoreInteractions(distributionService);

        assertThat(dto).isEqualTo(testData.userDto);
    }
}
