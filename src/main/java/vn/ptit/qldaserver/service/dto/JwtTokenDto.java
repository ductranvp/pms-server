package vn.ptit.qldaserver.service.dto;

import lombok.Data;

@Data
public class JwtTokenDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
