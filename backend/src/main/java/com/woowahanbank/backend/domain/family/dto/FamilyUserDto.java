package com.woowahanbank.backend.domain.family.dto;

import com.woowahanbank.backend.domain.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyUserDto {
    private String nickname;
    private Role role;
    private Long money;
}
