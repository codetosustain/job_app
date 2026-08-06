package com.ananya.jobportal.repository;

import com.ananya.jobportal.entity.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository
        extends JpaRepository<CandidateProfile, Long> {
}