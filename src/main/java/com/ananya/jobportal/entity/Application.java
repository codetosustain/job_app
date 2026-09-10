package com.ananya.jobportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"candidate_id", "job_id"}
                )
        }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many applications can belong to one candidate
    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    // Many applications can belong to one job
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    private String resume;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private LocalDateTime appliedDate;


    public enum ApplicationStatus {
        APPLIED,
        VIEWED,
        SHORTLISTED,
        INTERVIEW,
        REJECTED,
        SELECTED
    }


    public Application() {
    }


    public Long getId() {
        return id;
    }


    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }


    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }


    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }


    public LocalDateTime getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDateTime appliedDate) {
        this.appliedDate = appliedDate;
    }
}