package vn.ptit.pms.service.dto.core;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

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

    public ErrorEntity() {
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

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getErrors() {
        return this.errors;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ErrorEntity)) return false;
        final ErrorEntity other = (ErrorEntity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        if (this.getStatus() != other.getStatus()) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$errors = this.getErrors();
        final Object other$errors = other.getErrors();
        if (this$errors == null ? other$errors != null : !this$errors.equals(other$errors)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ErrorEntity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        result = result * PRIME + this.getStatus();
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $errors = this.getErrors();
        result = result * PRIME + ($errors == null ? 43 : $errors.hashCode());
        return result;
    }

    public String toString() {
        return "ErrorEntity(timestamp=" + this.getTimestamp() + ", status=" + this.getStatus() + ", message=" + this.getMessage() + ", errors=" + this.getErrors() + ")";
    }
}
