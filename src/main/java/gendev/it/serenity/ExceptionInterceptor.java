package gendev.it.serenity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import gendev.it.serenity.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e) {

        Throwable cause = e.getMostSpecificCause();

        log.error("Erreur JSON", cause);

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Erreur de validation : " + cause.getMessage());
    }
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<?> handleGeneralException(Exception ex) {
    //     HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    //     return ResponseEntity
    //             .status(ex.)
    //             .body(ex.getMessage());
    // }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }
}
