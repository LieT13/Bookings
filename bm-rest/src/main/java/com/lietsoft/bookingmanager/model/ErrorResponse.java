package com.lietsoft.bookingmanager.model;

public class ErrorResponse {

    private Integer status;

    private String info;

    public ErrorResponse(Integer status, String info) {
        this.status = status;
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
