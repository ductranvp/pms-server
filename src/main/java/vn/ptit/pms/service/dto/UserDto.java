package vn.ptit.pms.service.dto;

import vn.ptit.pms.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean activated;
    private String email;
    private String imageUrl;
    private String langKey;
    private Set<String> authorities;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.activated = user.isActivated();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.authorities = user.getAuthorities().stream().map(Authority -> Authority.getName().name()).collect(Collectors.toSet());
    }

    public UserDto(Long id, String username, String firstName, String lastName, boolean activated, String email, String imageUrl, String langKey, Set<String> authorities) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activated = activated;
        this.email = email;
        this.imageUrl = imageUrl;
        this.langKey = langKey;
        this.authorities = authorities;
    }

    public UserDto() {
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public String getEmail() {
        return this.email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getLangKey() {
        return this.langKey;
    }

    public Set<String> getAuthorities() {
        return this.authorities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDto)) return false;
        final UserDto other = (UserDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        if (this.isActivated() != other.isActivated()) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$imageUrl = this.getImageUrl();
        final Object other$imageUrl = other.getImageUrl();
        if (this$imageUrl == null ? other$imageUrl != null : !this$imageUrl.equals(other$imageUrl)) return false;
        final Object this$langKey = this.getLangKey();
        final Object other$langKey = other.getLangKey();
        if (this$langKey == null ? other$langKey != null : !this$langKey.equals(other$langKey)) return false;
        final Object this$authorities = this.getAuthorities();
        final Object other$authorities = other.getAuthorities();
        if (this$authorities == null ? other$authorities != null : !this$authorities.equals(other$authorities))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        result = result * PRIME + (this.isActivated() ? 79 : 97);
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $imageUrl = this.getImageUrl();
        result = result * PRIME + ($imageUrl == null ? 43 : $imageUrl.hashCode());
        final Object $langKey = this.getLangKey();
        result = result * PRIME + ($langKey == null ? 43 : $langKey.hashCode());
        final Object $authorities = this.getAuthorities();
        result = result * PRIME + ($authorities == null ? 43 : $authorities.hashCode());
        return result;
    }

    public String toString() {
        return "UserDto(id=" + this.getId() + ", username=" + this.getUsername() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", activated=" + this.isActivated() + ", email=" + this.getEmail() + ", imageUrl=" + this.getImageUrl() + ", langKey=" + this.getLangKey() + ", authorities=" + this.getAuthorities() + ")";
    }
}
