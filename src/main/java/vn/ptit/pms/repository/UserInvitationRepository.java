package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.UserInvitation;
import vn.ptit.pms.domain.key.UserInvitationKey;

import java.util.List;

public interface UserInvitationRepository extends JpaRepository<UserInvitation, UserInvitationKey> {
    List<UserInvitation> findByIdUserId(Long userId);

    List<UserInvitation> findByIdInvitationId(Long invitationId);
}
