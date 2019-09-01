package vn.ptit.qldaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.qldaserver.model.User;

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

    public AccountDto(User user) {
        this(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getImageUrl(), user.getLangKey());
    }
}
