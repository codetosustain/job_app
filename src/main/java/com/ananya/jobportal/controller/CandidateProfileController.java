package com.ananya.jobportal.controller;
import com.ananya.jobportal.dto.CandidateProfileResponse;
import com.ananya.jobportal.dto.CreateCandidateProfileRequest;
import com.ananya.jobportal.service.CandidateProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate/profile")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    public CandidateProfileController(
            CandidateProfileService candidateProfileService) {

        this.candidateProfileService = candidateProfileService;
    }

    @PostMapping
    public ResponseEntity<String> createProfile(
            @Valid @RequestBody CreateCandidateProfileRequest request) {

        candidateProfileService.createProfile(request);

        return ResponseEntity.ok("Candidate Profile Created Successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<CandidateProfileResponse> getProfile(@PathVariable Long id) {

        return ResponseEntity.ok(candidateProfileService.getProfile(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody CreateCandidateProfileRequest request) {

        candidateProfileService.updateProfile(id, request);

        return ResponseEntity.ok("Candidate Profile Updated Successfully");
    }
}
