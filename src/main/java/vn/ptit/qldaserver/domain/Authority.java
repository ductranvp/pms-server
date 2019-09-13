package vn.ptit.qldaserver.domain;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import vn.ptit.qldaserver.domain.enumeration.AuthorityName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, unique = true)
    private AuthorityName name;
}
