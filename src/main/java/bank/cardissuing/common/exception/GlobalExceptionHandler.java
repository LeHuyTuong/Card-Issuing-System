package bank.cardissuing.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ApiErrorResponse> handleBusinessException(
                        BusinessException ex, HttpServletRequest request) {
                log.warn("Business exception at {}: {}", request.getRequestURI(), ex.getMessage());

                return ResponseEntity.status(ex.getHttpStatus())
                                .body(ApiErrorResponse.builder()
                                                .errorCode(ex.getErrorCode())
                                                .message(ex.getMessage())
                                                .path(request.getRequestURI())
                                                .timestamp(LocalDateTime.now())
                                                .traceId(MDC.get("traceId"))
                                                .build());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex, HttpServletRequest request) {
                List<ApiErrorResponse.FieldValidationError> fieldErrors = ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(e -> new ApiErrorResponse.FieldValidationError(e.getField(),
                                                e.getDefaultMessage()))
                                .toList();

                log.warn("Validation exception at {}: {}", request.getRequestURI(), fieldErrors);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiErrorResponse.builder()
                                                .errorCode("VALIDATION_ERROR")
                                                .message("Validation failed for one or more fields")
                                                .fieldErrors(fieldErrors)
                                                .path(request.getRequestURI())
                                                .timestamp(LocalDateTime.now())
                                                .traceId(MDC.get("traceId"))
                                                .build());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGenericException(
                        Exception ex, HttpServletRequest request) {
                log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ApiErrorResponse.builder()
                                                .errorCode("INTERNAL_SERVER_ERROR")
                                                .message("An unexpected error occurred: " + ex.toString()) // Temporary
                                                                                                           // for
                                                                                                           // debugging
                                                .path(request.getRequestURI())
                                                .timestamp(LocalDateTime.now())
                                                .traceId(MDC.get("traceId"))
                                                .build());
        }

}
