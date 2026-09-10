package com.ananya.jobportal.dto;

import jakarta.validation.constraints.NotNull;

public class CreateApplicationRequest {

    @NotNull
    private Long jobId;

    public CreateApplicationRequest() {
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}