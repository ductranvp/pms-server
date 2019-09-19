package vn.ptit.qldaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Invitation;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.domain.UserInvitation;
import vn.ptit.qldaserver.domain.UserProject;
import vn.ptit.qldaserver.domain.enumeration.InvitationStatus;
import vn.ptit.qldaserver.domain.enumeration.ProjectRole;
import vn.ptit.qldaserver.domain.key.UserInvitationKey;
import vn.ptit.qldaserver.domain.key.UserProjectKey;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.exception.BadRequestException;
import vn.ptit.qldaserver.repository.UserInvitationRepository;
import vn.ptit.qldaserver.repository.UserProjectRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.service.dto.InvitationRequestDto;
import vn.ptit.qldaserver.service.dto.InvitationResponseDto;
import vn.ptit.qldaserver.service.dto.UserInvitationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserInvitationService {
    private final Logger log = LoggerFactory.getLogger(UserInvitationService.class);
    private final String ENTITY_NAME = "User Invitation";
    @Autowired
    InvitationService invitationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;
    @Autowired
    private UserInvitationRepository userInvitationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserProjectService userProjectService;
    @Autowired
    private UserProjectRepository userProjectRepository;
    @Autowired
    private UserInvitationService userInvitationService;

    public UserInvitation save(UserInvitation invitation) {
        return userInvitationRepository.save(invitation);
    }

    public UserInvitation findOne(UserInvitationKey id) {
        try {
            return userInvitationRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<UserInvitation> findByUserId(Long userId) {
        return userInvitationRepository.findByIdUserId(userId);
    }

    public List<UserInvitation> findByInvitationId(Long invitationId) {
        return userInvitationRepository.findByIdInvitationId(invitationId);
    }

    public List<UserInvitation> findAll() {
        return userInvitationRepository.findAll();
    }

    public void delete(UserInvitationKey id) {
        userInvitationRepository.deleteById(id);
    }

    public void responseToInvitation(InvitationResponseDto dto) {
        User currentUser = userService.getCurrentUser();
        UserInvitation userInvitation = userInvitationService.findOne(new UserInvitationKey(currentUser.getId(), dto.getInvitationId()));
        if (userInvitation.getStatus().equals(InvitationStatus.NO_ACTION)) {
            if (dto.isAccept()) {
                userInvitation.setStatus(InvitationStatus.ACCEPTED);
                Long projectId = userInvitation.getInvitation().getProject().getId();
                Long userId = currentUser.getId();
                userProjectService.addUserToProject(userId, projectId);
            } else {
                userInvitation.setStatus(InvitationStatus.REJECTED);
            }
            userInvitationRepository.save(userInvitation);
        } else {
            throw new BadRequestException("The invitation has been accepted or rejected");
        }
    }

    public void sendInvitationRequest(InvitationRequestDto dto) {
        User currentUser = userService.getCurrentUser();
        UserProject userProject = userProjectService.findOne(new UserProjectKey(currentUser.getId(), dto.getProjectId()));
        /*Check current user is Project manager or not */
        if (userProject.getRole().equals(ProjectRole.ROLE_MANAGER)) {
            Invitation invitation = new Invitation(dto.getContent(), dto.getProjectId());
            Invitation savedInvitation = invitationService.save(invitation);
            dto.getListEmail().forEach(email -> {
                Optional<User> optionalUser = userRepository.findByEmail(email);
                /*Check if email existed or not*/
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    /*Check if user has already in project or not*/
                    if (!userProjectRepository.findById(new UserProjectKey(user.getId(), dto.getProjectId())).isPresent()) {
                        UserInvitation userInvitation = new UserInvitation(new UserInvitationKey(user.getId(), savedInvitation.getId()));
                        userInvitationService.save(userInvitation);
                        mailService.sendEmailFromTemplate(user, "projectInvitationEmail", "email.invitation.title");
                    } else {
                        log.info("Email {} has already in project", email);
                    }
                }
            });
        } else {
            throw new BadRequestException("You don't have permission to invite users to this project");
        }
    }

    public List<UserInvitationDto> findByCurrentUser() {
        List<UserInvitation> list = findByUserId(userService.getCurrentUser().getId());
        List<UserInvitationDto> result = new ArrayList<>();
        list.forEach(userInvitation -> {
            if (userInvitation.getStatus().equals(InvitationStatus.NO_ACTION)) {
                UserInvitationDto dto = new UserInvitationDto();
                User inviter = userRepository.findById(userInvitation.getInvitation().getCreatedBy()).get();
                dto.setInviter(inviter.getFirstName() + " " + inviter.getLastName());
                dto.setInvitation(userInvitation.getInvitation());
                dto.setProject(userInvitation.getInvitation().getProject());
                result.add(dto);
            }
        });
        return result;
    }
}
