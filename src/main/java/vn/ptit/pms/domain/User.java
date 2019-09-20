package vn.ptit.pms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
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
}
