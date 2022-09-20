package com.tweetapp.vo;

import java.time.LocalDate;

public class ErrorResponse {

    private String errorCode;

    private LocalDate timeStamp;

    public ErrorResponse(String errorCode, LocalDate timeStamp) {
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }
}
