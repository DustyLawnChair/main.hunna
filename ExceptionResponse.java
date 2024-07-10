package com.PlayTrackerWebApp.playtracker.controller;

public class ExceptionResponse
{
    private String message;
    private String requestURI;

    public String getRequestURI()
    {
        return requestURI;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }
}
