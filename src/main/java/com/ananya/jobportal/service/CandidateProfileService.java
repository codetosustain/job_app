package com.ananya.jobportal.service;

import com.ananya.jobportal.dto.CreateCandidateProfileRequest;
import com.ananya.jobportal.entity.CandidateProfile;
import com.ananya.jobportal.entity.User;
import com.ananya.jobportal.exception.ResourceNotFoundException;
import com.ananya.jobportal.repository.CandidateProfileRepository;
import com.ananya.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.ananya.jobportal.dto.CandidateProfileResponse;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;

    public CandidateProfileService(
            CandidateProfileRepository candidateProfileRepository,
            UserRepository userRepository) {

        this.candidateProfileRepository = candidateProfileRepository;
        this.userRepository = userRepository;
    }

    public void createProfile(CreateCandidateProfileRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CandidateProfile profile = new CandidateProfile();

        profile.setPhone(request.getPhone());
        profile.setEducation(request.getEducation());
        profile.setExperience(request.getExperience());
        profile.setSkills(request.getSkills());
        profile.setCurrentSalary(request.getCurrentSalary());
        profile.setExpectedSalary(request.getExpectedSalary());
        profile.setPreferredLocation(request.getPreferredLocation());
        profile.setNoticePeriod(request.getNoticePeriod());
        profile.setUser(user);

        candidateProfileRepository.save(profile);
    }

    public CandidateProfileResponse getProfile(Long id) {

        CandidateProfile profile = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate Profile not found"));

        CandidateProfileResponse response = new CandidateProfileResponse();

        response.setId(profile.getId());
        response.setPhone(profile.getPhone());
        response.setEducation(profile.getEducation());
        response.setExperience(profile.getExperience());
        response.setSkills(profile.getSkills());
        response.setCurrentSalary(profile.getCurrentSalary());
        response.setExpectedSalary(profile.getExpectedSalary());
        response.setPreferredLocation(profile.getPreferredLocation());
        response.setNoticePeriod(profile.getNoticePeriod());

        response.setName(profile.getUser().getName());
        response.setEmail(profile.getUser().getEmail());

        return response;
    }

    public void updateProfile(Long id, CreateCandidateProfileRequest request) {

        CandidateProfile profile = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate Profile not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        profile.setPhone(request.getPhone());
        profile.setEducation(request.getEducation());
        profile.setExperience(request.getExperience());
        profile.setSkills(request.getSkills());
        profile.setCurrentSalary(request.getCurrentSalary());
        profile.setExpectedSalary(request.getExpectedSalary());
        profile.setPreferredLocation(request.getPreferredLocation());
        profile.setNoticePeriod(request.getNoticePeriod());
        profile.setUser(user);

        candidateProfileRepository.save(profile);
    }
}
