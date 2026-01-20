package bank.cardissuing.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ApiErrorResponse {
    private String errorCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private String traceId;

    @JsonInclude(JsonInclude.Include.NON_NULL) // ko hiện field nếu null
    private List<FieldValidationError> fieldErrors;

    @Getter
    @AllArgsConstructor
    public static class FieldValidationError {
        private String field;
        private String message;
    }

}
