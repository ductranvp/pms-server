package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
