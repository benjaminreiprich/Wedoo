package com.example.mapper;

import com.example.dtos.CompanyDto;
import com.example.model.Company;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyDtoMapper {

    public Optional<Company> mapToCompany(CompanyDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        Company company = new Company();
        company.setId(dto.getId());
        company.setBalance(dto.getBalance());

        return Optional.of(company);
    }

    public List<Company> mapToCompanys(List<CompanyDto> dtos) {

        if (dtos == null) {
            return Collections.emptyList();
        }

        return dtos.stream().map(companyDto -> mapToCompany(companyDto).orElse(null)).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public Optional<CompanyDto> mapToDto(Company company) {
        if (company == null) {
            return Optional.empty();
        }

        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setBalance(company.getBalance());

        return Optional.of(dto);
    }

    public List<CompanyDto> mapToDtos(List<Company> companys) {

        if (companys == null) {
            return Collections.emptyList();
        }

        return companys.stream().map(company -> mapToDto(company).orElse(null)).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

}
