package com.ananya.jobportal.controller;

import com.ananya.jobportal.dto.CreateCompanyRequest;
import com.ananya.jobportal.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<String> createCompany(
            @Valid @RequestBody CreateCompanyRequest request) {

        companyService.createCompany(request);

        return ResponseEntity.ok("Company Created Successfully");
    }
}