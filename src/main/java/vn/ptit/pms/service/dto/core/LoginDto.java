package vn.ptit.pms.service.dto.core;

import javax.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public LoginDto(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto() {
    }

    public @NotBlank String getUsername() {
        return this.username;
    }

    public @NotBlank String getPassword() {
        return this.password;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LoginDto)) return false;
        final LoginDto other = (LoginDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LoginDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "LoginDto(username=" + this.getUsername() + ", password=" + this.getPassword() + ")";
    }
}
