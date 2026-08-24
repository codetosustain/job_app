package com.ananya.jobportal.service;

import com.ananya.jobportal.dto.CreateJobRequest;
import com.ananya.jobportal.dto.JobResponse;
import com.ananya.jobportal.entity.Company;
import com.ananya.jobportal.entity.Job;
import com.ananya.jobportal.repository.CompanyRepository;
import com.ananya.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;
import com.ananya.jobportal.exception.ResourceNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.ananya.jobportal.specification.JobSpecification;
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository,
                      CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    // Create Job
    public void createJob(CreateJobRequest request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setExperience(request.getExperience());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setSkills(request.getSkills());
        job.setLastDate(request.getLastDate());

        job.setPostedDate(LocalDate.now());
        job.setStatus("OPEN");
        job.setCompany(company);

        jobRepository.save(job);
    }

    // Get All Jobs
    public Page<JobResponse> getAllJobs(
            Pageable pageable,
            String location,
            String title,
            Integer experience) {

        Specification<Job> specification = Specification.allOf();

        if (location != null && !location.isBlank()) {
            specification = specification.and(
                    JobSpecification.hasLocation(location)
            );
        }

        if (title != null && !title.isBlank()) {
            specification = specification.and(
                    JobSpecification.hasTitle(title)
            );
        }

        if (experience != null) {
            specification = specification.and(
                    JobSpecification.hasExperience(experience)
            );
        }

        Page<Job> jobs =
                jobRepository.findAll(specification, pageable);

        return jobs.map(job -> {
            JobResponse response = new JobResponse();

            response.setId(job.getId());
            response.setTitle(job.getTitle());
            response.setLocation(job.getLocation());
            response.setSalary(job.getSalary());
            response.setCompanyName(
                    job.getCompany().getCompanyName()
            );
            response.setStatus(job.getStatus());

            return response;
        });
    }

    public JobResponse getJobById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        JobResponse response = new JobResponse();

        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setLocation(job.getLocation());
        response.setSalary(job.getSalary());
        response.setCompanyName(job.getCompany().getCompanyName());
        response.setStatus(job.getStatus());

        return response;
    }

    public void updateJob(Long id, CreateJobRequest request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setExperience(request.getExperience());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setSkills(request.getSkills());
        job.setLastDate(request.getLastDate());
        job.setCompany(company);

        jobRepository.save(job);
    }
    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        jobRepository.delete(job);
    }
}