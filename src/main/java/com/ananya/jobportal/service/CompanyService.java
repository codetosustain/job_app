package com.ananya.jobportal.service;

import com.ananya.jobportal.dto.CreateCompanyRequest;
import com.ananya.jobportal.entity.Company;
import com.ananya.jobportal.entity.User;
import com.ananya.jobportal.repository.CompanyRepository;
import com.ananya.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository,
                          UserRepository userRepository) {

        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public void createCompany(CreateCompanyRequest request) {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = new Company();

        company.setCompanyName(request.getCompanyName());
        company.setCompanySize(request.getCompanySize());
        company.setDescription(request.getDescription());
        company.setIndustry(request.getIndustry());
        company.setLocation(request.getLocation());
        company.setLogo(request.getLogo());
        company.setWebsite(request.getWebsite());

        company.setOwner(owner);

        companyRepository.save(company);
    }
}