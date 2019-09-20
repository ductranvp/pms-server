package vn.ptit.pms.service.dto.core;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String key;
    private String newPassword;
}
