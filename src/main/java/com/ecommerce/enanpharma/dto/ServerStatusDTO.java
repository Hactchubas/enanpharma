package com.ecommerce.enanpharma.dto;

public class ServerStatusDTO {
    private String status;

    public ServerStatusDTO() {
    }

    public ServerStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
