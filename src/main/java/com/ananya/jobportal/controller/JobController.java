package com.ananya.jobportal.controller;

import com.ananya.jobportal.dto.CreateJobRequest;
import com.ananya.jobportal.dto.JobResponse;
import com.ananya.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Create Job
    @PostMapping
    public ResponseEntity<String> createJob(
            @Valid @RequestBody CreateJobRequest request) {

        jobService.createJob(request);

        return ResponseEntity.ok("Job Created Successfully");
    }

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getAllJobs(
            Pageable pageable,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer experience) {

        return ResponseEntity.ok(
                jobService.getAllJobs(
                        pageable,
                        location,
                        title,
                        experience
                )
        );
    }

    // Get Job By ID
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobService.getJobById(id)
        );
    }

    // Update Job
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody CreateJobRequest request) {

        jobService.updateJob(id, request);

        return ResponseEntity.ok("Job Updated Successfully");
    }

    // Delete Job
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);

        return ResponseEntity.ok("Job Deleted Successfully");
    }
}