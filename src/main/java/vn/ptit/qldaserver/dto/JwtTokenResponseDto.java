package vn.ptit.qldaserver.dto;

import lombok.Data;

@Data
public class JwtTokenResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
