package edu.practice.parking.error;
import edu.practice.parking.dto.ApiError;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice public class ApiExceptionHandler {
 // TODO Task 3: map IllegalArgumentException -> HTTP 400 and PassNotFoundException -> HTTP 404.
 // Each response body must use ApiError with BAD_REQUEST or NOT_FOUND.

    @ExceptionHandler(PassNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(PassNotFoundException ex) {
        ApiError error = new ApiError("NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex) {
        ApiError error = new ApiError("BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}
