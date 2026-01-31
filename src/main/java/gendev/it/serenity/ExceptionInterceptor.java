package gendev.it.serenity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionInterceptor {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e) {

        Throwable cause = e.getMostSpecificCause();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Erreur de validation : " + cause.getMessage());
    }

}
