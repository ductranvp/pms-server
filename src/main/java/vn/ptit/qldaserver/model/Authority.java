package vn.ptit.qldaserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import vn.ptit.qldaserver.model.enumeration.AuthorityName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    private AuthorityName name;
}
