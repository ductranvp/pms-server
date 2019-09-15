package vn.ptit.qldaserver.service.dto.core;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String key;
    private String newPassword;
}
