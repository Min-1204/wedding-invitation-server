package com.wedding.wedding_invitation.domain.member.dto.response;

import com.wedding.wedding_invitation.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginResponse {

    private Long userId;
    private String username;
    private MemberRole role;

}
