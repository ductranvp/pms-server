package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.UserInvitation;
import vn.ptit.qldaserver.domain.key.UserInvitationKey;

public interface UserInvitationRepository extends JpaRepository<UserInvitation, UserInvitationKey> {
}
