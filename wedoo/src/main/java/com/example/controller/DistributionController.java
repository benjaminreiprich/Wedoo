package com.example.controller;

import com.example.dtos.CompanyDto;
import com.example.dtos.DistributionDto;
import com.example.exception.CustomException;
import com.example.mapper.CompanyDtoMapper;
import com.example.service.CompanyService;
import com.example.service.DistributionService;

import com.example.validator.ValidatorCompany;
import com.example.validator.ValidatorGiftCard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/distribution")
public class DistributionController {


    private final DistributionService distributionService;
    private final CompanyService companyService;
    private final CompanyDtoMapper companyDtoMapper;

    public DistributionController(DistributionService giftService,
                                  CompanyService companyService,
                                  CompanyDtoMapper companyDtoMapper) {
        this.distributionService = giftService;
        this.companyService = companyService;
        this.companyDtoMapper = companyDtoMapper;

    }

    @PostMapping
    public ResponseEntity<?> addGift(@RequestBody DistributionDto distributionDto) throws Exception {
        try {
            ValidatorGiftCard.validateGiftCard(distributionDto);
            CompanyDto companyDto = companyService.getById(distributionDto.getCompanyId());
            ValidatorCompany.validateCompany(distributionDto, companyDto.getBalance());
            Optional<DistributionDto> addedDistribution = distributionService.addGift(distributionDto);
            if (addedDistribution.isEmpty()) {
                new CustomException("Une erreur est survenue lors de l'ajout de l'objet");
            }
            companyDto.setBalance(companyDto.getBalance() - distributionDto.getAmount());
            companyService.update(companyDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedDistribution.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Exception(e.getMessage()));
        }
    }

}
