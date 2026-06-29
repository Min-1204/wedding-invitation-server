package com.wedding.wedding_invitation.domain.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberFindPasswordRequest {

    private String username;
    private String email;
}
