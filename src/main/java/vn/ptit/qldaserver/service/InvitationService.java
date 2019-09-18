package vn.ptit.qldaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Invitation;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.repository.InvitationRepository;

import java.util.List;

@Service
public class InvitationService {
    private final String ENTITY_NAME = "Invitation";

    @Autowired
    InvitationRepository invitationRepository;

    public Invitation save(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    public Invitation findOne(Long id) {
        try {
            return invitationRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<Invitation> findAll() {
        return invitationRepository.findAll();
    }

    public void delete(Long id) {
        invitationRepository.deleteById(id);
    }
}
