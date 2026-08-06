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
import java.util.stream.Collectors;

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
    public List<JobResponse> getAllJobs() {

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(job -> {
                    JobResponse response = new JobResponse();

                    response.setId(job.getId());
                    response.setTitle(job.getTitle());
                    response.setLocation(job.getLocation());
                    response.setSalary(job.getSalary());
                    response.setCompanyName(job.getCompany().getCompanyName());
                    response.setStatus(job.getStatus());

                    return response;
                })
                .collect(Collectors.toList());
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