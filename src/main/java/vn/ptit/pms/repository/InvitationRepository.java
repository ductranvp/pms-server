package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
