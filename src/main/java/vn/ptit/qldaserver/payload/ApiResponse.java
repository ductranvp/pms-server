package vn.ptit.qldaserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object payload;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
