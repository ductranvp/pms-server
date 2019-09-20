package vn.ptit.pms.service.dto.core;

public class JwtTokenDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JwtTokenDto)) return false;
        final JwtTokenDto other = (JwtTokenDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$accessToken = this.getAccessToken();
        final Object other$accessToken = other.getAccessToken();
        if (this$accessToken == null ? other$accessToken != null : !this$accessToken.equals(other$accessToken))
            return false;
        final Object this$tokenType = this.getTokenType();
        final Object other$tokenType = other.getTokenType();
        if (this$tokenType == null ? other$tokenType != null : !this$tokenType.equals(other$tokenType)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JwtTokenDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $accessToken = this.getAccessToken();
        result = result * PRIME + ($accessToken == null ? 43 : $accessToken.hashCode());
        final Object $tokenType = this.getTokenType();
        result = result * PRIME + ($tokenType == null ? 43 : $tokenType.hashCode());
        return result;
    }

    public String toString() {
        return "JwtTokenDto(accessToken=" + this.getAccessToken() + ", tokenType=" + this.getTokenType() + ")";
    }
}
