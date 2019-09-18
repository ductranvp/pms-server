package vn.ptit.qldaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.UserInvitation;
import vn.ptit.qldaserver.domain.key.UserInvitationKey;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.repository.UserInvitationRepository;

import java.util.List;

@Service
public class UserInvitationService {
    private final String ENTITY_NAME = "User Invitation";

    @Autowired
    UserInvitationRepository repository;

    public UserInvitation save(UserInvitation invitation) {
        return repository.save(invitation);
    }

    public UserInvitation findOne(UserInvitationKey id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<UserInvitation> findByUserId(Long userId) {
        return repository.findByIdUserId(userId);
    }

    public List<UserInvitation> findByInvitationId(Long invitationId) {
        return repository.findByIdInvitationId(invitationId);
    }

    public List<UserInvitation> findAll() {
        return repository.findAll();
    }

    public void delete(UserInvitationKey id) {
        repository.deleteById(id);
    }
}
