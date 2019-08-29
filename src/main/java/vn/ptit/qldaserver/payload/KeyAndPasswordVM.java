package vn.ptit.qldaserver.payload;

import lombok.Data;

@Data
public class KeyAndPasswordVM {
    private String key;
    private String newPassword;
}
