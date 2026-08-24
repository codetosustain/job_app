package com.ananya.jobportal.controller;
import com.ananya.jobportal.dto.ApplicationResponse;
import com.ananya.jobportal.dto.CreateApplicationRequest;
import com.ananya.jobportal.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.ananya.jobportal.dto.UpdateApplicationStatusRequest;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<String> apply(
            @Valid @RequestBody CreateApplicationRequest request) {

        applicationService.apply(request);

        return ResponseEntity.ok("Application Submitted Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplication(
            @PathVariable Long id) {

        return ResponseEntity.ok(applicationService.getApplication(id));
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<ApplicationResponse>>
    getApplicationsByCandidate(@PathVariable Long candidateId) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByCandidate(candidateId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByJob(jobId));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationStatusRequest request) {

        applicationService.updateApplicationStatus(id, request);

        return ResponseEntity.ok("Application status updated successfully");
    }
}