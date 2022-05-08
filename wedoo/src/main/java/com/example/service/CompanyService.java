package com.example.service;

import com.example.dtos.CompanyDto;
import com.example.exception.CompanyException;
import com.example.mapper.CompanyDtoMapper;
import com.example.model.Company;
import com.example.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;
    private CompanyDtoMapper companyDtoMapper;


    public CompanyService(CompanyRepository companyRepository, CompanyDtoMapper companyDtoMapper) {
        this.companyRepository = companyRepository;
        this.companyDtoMapper = companyDtoMapper;
    }

    public CompanyDto getById (Integer companyId) throws CompanyException {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            throw new CompanyException("La compagnie demandée est introuvable");
        }
        CompanyDto companyDto = companyDtoMapper.mapToDto(company.get()).get();

        return companyDto;
    }

    public CompanyDto update(CompanyDto companyDto) {
        return companyDtoMapper.mapToDto(companyRepository.save(companyDtoMapper.mapToCompany(companyDto).get())).get();
    }
}
