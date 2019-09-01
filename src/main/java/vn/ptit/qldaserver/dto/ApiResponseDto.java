package vn.ptit.qldaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
