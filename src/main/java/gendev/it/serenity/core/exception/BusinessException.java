package gendev.it.serenity.core.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception {
    private int status;
    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status.value();
    }
    public int getStatus() {
        return status;
    }
    
}
