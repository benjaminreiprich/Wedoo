package com.example.service;

import com.example.dtos.CompanyDto;
import com.example.exception.CompanyException;
import com.example.mapper.CompanyDtoMapper;
import com.example.repository.CompanyRepository;
import com.example.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {

    private CompanyRepository companyRepository;
    private CompanyDtoMapper companyDtoMapper;
    private CompanyService companyService;

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestData.class);
    private TestData testData = (TestData) context.getBean("testDataBean");

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        companyDtoMapper = mock(CompanyDtoMapper.class);
        companyService = new CompanyService(companyRepository,companyDtoMapper);
    }

    @Test
    void getById_Should_Throw_CompanyException() throws Exception{
        when(companyRepository.save(testData.company)).thenThrow(NoSuchElementException.class);

        assertThatThrownBy(() -> companyService.getById(testData.company.getId()))
                .isInstanceOf(CompanyException.class);
    }

    @Test
    void getById_Should_Return_CompanyDto() throws Exception{
        when(companyRepository.findById(testData.company.getId())).thenReturn(Optional.of(testData.company));
        when(companyDtoMapper.mapToDto(testData.company)).thenReturn(Optional.of(testData.companyDto));

        CompanyDto result = companyService.getById(testData.company.getId());

        assertThat(result).isEqualTo(testData.companyDto);
        verify(companyRepository, times(1)).findById(1);
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    void updateCompany_should_return_CompanyUpdated() {
        when(companyRepository.save(testData.company)).thenReturn(testData.company);
        when(companyDtoMapper.mapToDto(testData.company)).thenReturn(Optional.of(testData.companyDto));
        when(companyDtoMapper.mapToCompany(testData.companyDto)).thenReturn(Optional.of(testData.company));

        CompanyDto result = companyService.update(testData.companyDto);
        assertThat(result).isEqualTo(testData.companyDto);
        verify(companyRepository, times(1)).save(testData.company);
        verifyNoMoreInteractions(companyRepository);
    }

}
