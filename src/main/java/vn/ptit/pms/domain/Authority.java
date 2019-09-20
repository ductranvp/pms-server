package vn.ptit.pms.domain;

import org.hibernate.annotations.NaturalId;
import vn.ptit.pms.domain.enumeration.AuthorityName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 50, unique = true)
    private AuthorityName name;

    public Authority(Long id, AuthorityName name) {
        this.id = id;
        this.name = name;
    }

    public Authority() {
    }

    public Long getId() {
        return this.id;
    }

    public AuthorityName getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Authority)) return false;
        final Authority other = (Authority) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Authority;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "Authority(id=" + this.getId() + ", name=" + this.getName() + ")";
    }
}
