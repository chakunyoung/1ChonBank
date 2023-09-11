package com.woowahanbank.backend.domain.family.controller;

import com.woowahanbank.backend.domain.family.service.FamilyService;
import com.woowahanbank.backend.domain.user.service.UserService;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/family")
public class FamilyController {
    private final FamilyService familyService;
    private final UserService userService;

    public ResponseEntity<?> registerFamily(@AuthenticationPrincipal CustomUserDetails customUser, String familyName) {
        familyService.createFamily(customUser.getNickname(), familyName);

        return BaseResponse.ok(HttpStatus.OK, "가족 생성 성공");
    }

    public ResponseEntity<?> modifyFamily(@AuthenticationPrincipal CustomUserDetails customUser, String familyName) {
        familyService.updateFamily(customUser.getNickname(), familyName);

        return BaseResponse.ok(HttpStatus.OK, "가족 이름 수정 성공");
    }

    public ResponseEntity<?> removeFamily(@AuthenticationPrincipal CustomUserDetails customUser) {
        familyService.deleteFamily(customUser.getNickname());

        return BaseResponse.ok(HttpStatus.OK, "가족 그룹 삭제 성공");
    }

    public ResponseEntity<?> sendFamilyInvitation(@AuthenticationPrincipal CustomUserDetails customUser, Map<String, String> toUserMap) {
        String toNickname = toUserMap.get("nickname");
        familyService.inviteToFamily(customUser.getNickname(), toNickname);

        return BaseResponse.ok(HttpStatus.OK, "초대 전송 성공");
    }

    public ResponseEntity<?> denyFamilyInvite(@AuthenticationPrincipal CustomUserDetails customUser) {
        familyService.rejectInvitation(customUser.getNickname());

        return BaseResponse.ok(HttpStatus.OK, "초대 거절 성공");
    }

    public ResponseEntity<?> confirmInvitation(@AuthenticationPrincipal CustomUserDetails customUser) {
        familyService.acceptInvitation(customUser.getNickname());

        return BaseResponse.ok(HttpStatus.OK, "초대 수락 성공");
    }

    public ResponseEntity<?> removeUserFromFamily(@AuthenticationPrincipal CustomUserDetails customUser, Map<String, String> targetUserMap) {
        String targetNickname = targetUserMap.get("nickname");
        familyService.deleteUserFromFamily(customUser.getNickname(), targetNickname);

        return BaseResponse.ok(HttpStatus.OK, "유저 추방 성공");
    }
}
