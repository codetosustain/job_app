package com.ananya.jobportal.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateApplicationStatusRequest {

    @NotBlank
    private String status;

    public UpdateApplicationStatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}