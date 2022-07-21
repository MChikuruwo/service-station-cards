package zw.co.jugaad.transactiondesignpatterns.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RestResponse {

    private int statusCode;
    private String message;
    private String timestamp;
    private HttpStatus status;
    private int errorCode;

    public RestResponse() {
        this.statusCode = HttpStatus.OK.value();
    }

    public RestResponse(String message, String timestamp, HttpStatus status) {
        this.statusCode = status.value();
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public RestResponse(String message, String timestamp, HttpStatus status, int errorCode) {
        this.statusCode = status.value();
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
    }


}

