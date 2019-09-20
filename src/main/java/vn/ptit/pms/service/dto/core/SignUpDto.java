package vn.ptit.pms.service.dto.core;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignUpDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String langKey;

    public SignUpDto(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String username, @NotBlank @Email String email, @NotBlank String password, String langKey) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.langKey = langKey;
    }

    public SignUpDto() {
    }

    public @NotBlank String getFirstName() {
        return this.firstName;
    }

    public @NotBlank String getLastName() {
        return this.lastName;
    }

    public @NotBlank String getUsername() {
        return this.username;
    }

    public @NotBlank @Email String getEmail() {
        return this.email;
    }

    public @NotBlank String getPassword() {
        return this.password;
    }

    public String getLangKey() {
        return this.langKey;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SignUpDto)) return false;
        final SignUpDto other = (SignUpDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$langKey = this.getLangKey();
        final Object other$langKey = other.getLangKey();
        if (this$langKey == null ? other$langKey != null : !this$langKey.equals(other$langKey)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SignUpDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $langKey = this.getLangKey();
        result = result * PRIME + ($langKey == null ? 43 : $langKey.hashCode());
        return result;
    }

    public String toString() {
        return "SignUpDto(firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", langKey=" + this.getLangKey() + ")";
    }
}
