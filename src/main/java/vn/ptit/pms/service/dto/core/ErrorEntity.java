package vn.ptit.pms.service.dto.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorEntity {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private String errors;


    public ErrorEntity(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = error;
    }

    public static ErrorEntity internalError(String message) {
        return new ErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public static ErrorEntity badRequest(String message) {
        return new ErrorEntity(HttpStatus.BAD_REQUEST.value(), message, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    public static ErrorEntity unauthorized(String message) {
        return new ErrorEntity(HttpStatus.UNAUTHORIZED.value(), message, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    public static ErrorEntity notFound(String message) {
        return new ErrorEntity(HttpStatus.NOT_FOUND.value(), message, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    public static ErrorEntity forbidden(String message) {
        return new ErrorEntity(HttpStatus.FORBIDDEN.value(), message, HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    public ErrorEntity error(HttpStatus status, String message) {
        return new ErrorEntity(status.value(), message, status.getReasonPhrase());
    }

    public ErrorEntity error(HttpStatus status, String message, String error) {
        return new ErrorEntity(status.value(), message, error);
    }
}
