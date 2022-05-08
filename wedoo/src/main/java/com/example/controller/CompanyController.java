package com.example.controller;

import com.example.dtos.CompanyDto;
import com.example.exception.CompanyException;
import com.example.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    public CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<?> getById(@PathVariable Integer companyId) throws CompanyException {
        try {
            CompanyDto companyDto = companyService.getById(companyId);
            return ResponseEntity.ok().body(companyDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CompanyException("La compagnie demandée est introuvable.").getMessage());
        }
    }
}
