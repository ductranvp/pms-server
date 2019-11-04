package vn.ptit.pms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.*;
import vn.ptit.pms.domain.enumeration.InvitationStatus;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.key.UserInvitationKey;
import vn.ptit.pms.domain.key.UserNotificationKey;
import vn.ptit.pms.domain.key.UserProjectKey;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.exception.BadRequestException;
import vn.ptit.pms.repository.UserInvitationRepository;
import vn.ptit.pms.repository.UserProjectRepository;
import vn.ptit.pms.repository.UserRepository;
import vn.ptit.pms.service.dto.*;

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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserNotificationService userNotificationService;

    public UserInvitation save(UserInvitation invitation) {
        return userInvitationRepository.save(invitation);
    }

    public UserInvitation getOneById(UserInvitationKey id) {
        try {
            return userInvitationRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<UserInvitation> getByUserId(Long userId) {
        return userInvitationRepository.findByIdUserId(userId);
    }

    public List<UserInvitation> getByInvitationId(Long invitationId) {
        return userInvitationRepository.findByIdInvitationId(invitationId);
    }

    public List<UserInvitation> getAll() {
        return userInvitationRepository.findAll();
    }

    public void delete(UserInvitationKey id) {
        userInvitationRepository.deleteById(id);
    }

    public void responseToInvitation(InvitationResponseDto dto) {
        User currentUser = userService.getCurrentUser();
        UserInvitation userInvitation = userInvitationService.getOneById(new UserInvitationKey(currentUser.getId(), dto.getInvitationId()));
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
        UserProject userProject = userProjectService.getOneById(new UserProjectKey(currentUser.getId(), dto.getProjectId()));

        /*Check current user is Project manager or not */
        if (userProject.getRole().equals(ProjectRole.ROLE_MANAGER)) {
            Invitation invitation = new Invitation(dto.getContent(), dto.getProjectId());
            Invitation savedInvitation = invitationService.save(invitation);

            /* Create notification */
            Notification savedNotification = notificationService.save(NotificationDto.inviteToProject(dto.getProjectId()));

            dto.getListEmail().forEach(email -> {
                Optional<User> optionalUser = userRepository.findByEmail(email);
                /*Check if email existed or not*/
                if (optionalUser.isPresent()) {
                    User invitedUser = optionalUser.get();
                    /*Check if user has already in project or not*/
                    if (!userProjectRepository.findById(new UserProjectKey(invitedUser.getId(), dto.getProjectId())).isPresent()) {
                        UserInvitation userInvitation = new UserInvitation(new UserInvitationKey(invitedUser.getId(), savedInvitation.getId()));
                        userInvitationService.save(userInvitation);

                        /* Create notification for user */
                        UserNotificationKey key = new UserNotificationKey(invitedUser.getId(), savedNotification.getId());
                        userNotificationService.save(new UserNotification(key));

                        log.info("Send invitation mail to {}", email);
                        mailService.sendEmailFromTemplate(invitedUser, "projectInvitationEmail", "email.invitation.title");
                    } else {
                        log.info("Email {} has already in project", email);
                    }
                } else {
                    // send mail to unregistered user
                }
            });
        } else {
            throw new BadRequestException("You don't have permission to invite users to this project");
        }
    }

    public List<UserInvitationDto> getByCurrentUser() {
        List<UserInvitation> list = getByUserId(userService.getCurrentUser().getId());
        List<UserInvitationDto> result = new ArrayList<>();
        list.forEach(userInvitation -> {
            if (userInvitation.getStatus().equals(InvitationStatus.NO_ACTION)) {
                UserInvitationDto dto = new UserInvitationDto();
                User inviter = userRepository.findById(userInvitation.getInvitation().getCreatedBy()).get();
                dto.setInviter(new UserDto(inviter));
                dto.setInvitation(userInvitation.getInvitation());
                dto.setProject(userInvitation.getInvitation().getProject());
                result.add(dto);
            }
        });
        return result;
    }
}
