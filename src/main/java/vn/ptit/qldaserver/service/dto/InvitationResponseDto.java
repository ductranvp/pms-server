package vn.ptit.qldaserver.service.dto;

import lombok.Data;

@Data
public class InvitationResponseDto {
    private Long invitationId;
    private boolean accept;
}
