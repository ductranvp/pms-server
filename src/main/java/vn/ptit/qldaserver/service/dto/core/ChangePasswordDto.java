package vn.ptit.qldaserver.service.dto.core;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
