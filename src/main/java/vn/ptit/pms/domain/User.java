package vn.ptit.pms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import vn.ptit.pms.domain.audit.AuditEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 50)
    @NaturalId
    private String username;

    @JsonIgnore
    @NotBlank
    private String password;

    @NotNull
    private boolean activated = false;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "lang_key")
    private String langKey = "";

    @NotBlank
    @Column(length = 100)
    @NaturalId
    private String email;

    @JsonIgnore
    @Column(name = "activation_key")
    private String activationKey;

    @JsonIgnore
    @Column(name = "reset_key")
    private String resetKey;

    @JsonIgnore
    @Column(name = "receive_mail")
    private boolean receiveMail = true;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, @NotBlank String username, @NotBlank String password, @NotNull boolean activated, String imageUrl, String firstName, String lastName, String langKey, @NotBlank String email, String activationKey, String resetKey, boolean receiveMail, Set<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.activated = activated;
        this.imageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.langKey = langKey;
        this.email = email;
        this.activationKey = activationKey;
        this.resetKey = resetKey;
        this.receiveMail = receiveMail;
        this.authorities = authorities;
    }

    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getUsername() {
        return this.username;
    }

    public @NotBlank String getPassword() {
        return this.password;
    }

    public @NotNull boolean isActivated() {
        return this.activated;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getLangKey() {
        return this.langKey;
    }

    public @NotBlank String getEmail() {
        return this.email;
    }

    public String getActivationKey() {
        return this.activationKey;
    }

    public String getResetKey() {
        return this.resetKey;
    }

    public boolean isReceiveMail() {
        return this.receiveMail;
    }

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public void setActivated(@NotNull boolean activated) {
        this.activated = activated;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public void setReceiveMail(boolean receiveMail) {
        this.receiveMail = receiveMail;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        if (this.isActivated() != other.isActivated()) return false;
        final Object this$imageUrl = this.getImageUrl();
        final Object other$imageUrl = other.getImageUrl();
        if (this$imageUrl == null ? other$imageUrl != null : !this$imageUrl.equals(other$imageUrl)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$langKey = this.getLangKey();
        final Object other$langKey = other.getLangKey();
        if (this$langKey == null ? other$langKey != null : !this$langKey.equals(other$langKey)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$activationKey = this.getActivationKey();
        final Object other$activationKey = other.getActivationKey();
        if (this$activationKey == null ? other$activationKey != null : !this$activationKey.equals(other$activationKey))
            return false;
        final Object this$resetKey = this.getResetKey();
        final Object other$resetKey = other.getResetKey();
        if (this$resetKey == null ? other$resetKey != null : !this$resetKey.equals(other$resetKey)) return false;
        if (this.isReceiveMail() != other.isReceiveMail()) return false;
        final Object this$authorities = this.getAuthorities();
        final Object other$authorities = other.getAuthorities();
        if (this$authorities == null ? other$authorities != null : !this$authorities.equals(other$authorities))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        result = result * PRIME + (this.isActivated() ? 79 : 97);
        final Object $imageUrl = this.getImageUrl();
        result = result * PRIME + ($imageUrl == null ? 43 : $imageUrl.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $langKey = this.getLangKey();
        result = result * PRIME + ($langKey == null ? 43 : $langKey.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $activationKey = this.getActivationKey();
        result = result * PRIME + ($activationKey == null ? 43 : $activationKey.hashCode());
        final Object $resetKey = this.getResetKey();
        result = result * PRIME + ($resetKey == null ? 43 : $resetKey.hashCode());
        result = result * PRIME + (this.isReceiveMail() ? 79 : 97);
        final Object $authorities = this.getAuthorities();
        result = result * PRIME + ($authorities == null ? 43 : $authorities.hashCode());
        return result;
    }

    public String toString() {
        return "User(id=" + this.getId() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ", activated=" + this.isActivated() + ", imageUrl=" + this.getImageUrl() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", langKey=" + this.getLangKey() + ", email=" + this.getEmail() + ", activationKey=" + this.getActivationKey() + ", resetKey=" + this.getResetKey() + ", receiveMail=" + this.isReceiveMail() + ", authorities=" + this.getAuthorities() + ")";
    }
}
