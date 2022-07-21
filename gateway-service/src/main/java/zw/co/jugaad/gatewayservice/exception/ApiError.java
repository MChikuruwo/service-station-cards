package zw.co.jugaad.gatewayservice.exception;


import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;


public class ApiError {

    private String timestamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(String timestamp, int statusCode, HttpStatus status, String message, List<String> errors) {
        super();
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(String timestamp, int statusCode, HttpStatus status, String message, String error) {
        super();
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

