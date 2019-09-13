package vn.ptit.qldaserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.qldaserver.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private String langKey;
    private Set<String> authorities;

    public AccountDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.authorities = user.getAuthorities().stream().map(Authority -> Authority.getName().name()).collect(Collectors.toSet());
    }
}
