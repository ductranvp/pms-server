package vn.ptit.qldaserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto {
    private Boolean success;
    private String message;
    private Object payload;

    public ApiResponseDto(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
