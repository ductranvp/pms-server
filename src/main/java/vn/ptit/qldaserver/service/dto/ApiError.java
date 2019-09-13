package vn.ptit.qldaserver.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiError {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private String errors;


    public ApiError(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = error;
    }

    public static ApiError internalError(String message) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public static ApiError badRequest(String message) {
        return new ApiError(HttpStatus.BAD_REQUEST.value(), message, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    public static ApiError unauthorized(String message) {
        return new ApiError(HttpStatus.UNAUTHORIZED.value(), message, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    public static ApiError notFound(String message) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), message, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    public static ApiError forbidden(String message) {
        return new ApiError(HttpStatus.FORBIDDEN.value(), message, HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    public ApiError error(HttpStatus status, String message) {
        return new ApiError(status.value(), message, status.getReasonPhrase());
    }

    public ApiError error(HttpStatus status, String message, String error) {
        return new ApiError(status.value(), message, error);
    }
}
