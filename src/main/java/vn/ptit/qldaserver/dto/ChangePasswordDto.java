package vn.ptit.qldaserver.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
