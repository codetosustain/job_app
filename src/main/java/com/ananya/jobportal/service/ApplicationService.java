package com.ananya.jobportal.service;

import com.ananya.jobportal.dto.ApplicationResponse;
import com.ananya.jobportal.dto.CreateApplicationRequest;
import com.ananya.jobportal.dto.UpdateApplicationStatusRequest;
import com.ananya.jobportal.entity.Application;
import com.ananya.jobportal.entity.Job;
import com.ananya.jobportal.entity.User;
import com.ananya.jobportal.exception.ResourceNotFoundException;
import com.ananya.jobportal.repository.ApplicationRepository;
import com.ananya.jobportal.repository.JobRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final UserService userService;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            JobRepository jobRepository,
            UserService userService) {

        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userService = userService;
    }

    // Candidate applies for a job
    public void apply(CreateApplicationRequest request) {

        User candidate = userService.getCurrentUser();

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        Application application = new Application();

        application.setCandidate(candidate);
        application.setJob(job);
        application.setAppliedDate(LocalDateTime.now());
        application.setStatus(
                Application.ApplicationStatus.APPLIED
        );

        applicationRepository.save(application);
    }

    // Get one application
    public ApplicationResponse getApplication(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        return convertToResponse(application);
    }

    // Get applications of a candidate
    public List<ApplicationResponse> getApplicationsByCandidate(
            Long candidateId) {

        User currentUser = userService.getCurrentUser();

        if (!currentUser.getId().equals(candidateId)) {
            throw new AccessDeniedException(
                    "You are not allowed to view these applications"
            );
        }

        List<Application> applications =
                applicationRepository.findByCandidateId(candidateId);

        return applications.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get applications for a recruiter's job
    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {

        User currentUser = userService.getCurrentUser();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        // Check that the current recruiter owns this job
        if (!job.getCompany().getOwner().getId()
                .equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "You are not allowed to view applications for this job"
            );
        }

        List<Application> applications =
                applicationRepository.findByJobId(jobId);

        return applications.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Recruiter updates application status
    public void updateApplicationStatus(
            Long id,
            UpdateApplicationStatusRequest request) {

        User currentUser = userService.getCurrentUser();

        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        Job job = application.getJob();

        // Check that the recruiter owns the job
        if (!job.getCompany().getOwner().getId()
                .equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "You are not allowed to update this application"
            );
        }

        application.setStatus(
                Application.ApplicationStatus.valueOf(
                        request.getStatus().toUpperCase()
                )
        );

        applicationRepository.save(application);
    }

    // Convert Application entity to ApplicationResponse DTO
    private ApplicationResponse convertToResponse(
            Application application) {

        ApplicationResponse response = new ApplicationResponse();

        response.setId(application.getId());
        response.setCandidateName(
                application.getCandidate().getName()
        );
        response.setCandidateEmail(
                application.getCandidate().getEmail()
        );
        response.setJobTitle(
                application.getJob().getTitle()
        );
        response.setCompanyName(
                application.getJob()
                        .getCompany()
                        .getCompanyName()
        );
        response.setStatus(
                application.getStatus().name()
        );
        response.setAppliedDate(
                application.getAppliedDate()
        );

        return response;
    }
}