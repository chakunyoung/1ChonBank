package com.woowahanbank.backend.domain.family.controller;

import com.google.firebase.messaging.Notification;
import com.woowahanbank.backend.domain.family.dto.FamilyUserDto;
import com.woowahanbank.backend.domain.family.service.FamilyService;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.service.UserService;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.notification.dto.NotificationDto;
import com.woowahanbank.backend.global.notification.event.NotificationEvent;
import com.woowahanbank.backend.global.response.BaseResponse;
import com.woowahanbank.backend.global.util.NotificationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"Family API"})
@RequestMapping("/api/families")
public class FamilyController {
    private final FamilyService familyService;
    private final ApplicationEventPublisher eventPublisher;

    @ApiOperation(value = "가족 구성원 조회")
    @ApiResponse(code = 200, message = "가족 구성원 조회 성공")
    @GetMapping
    public ResponseEntity<?> getFamily(@AuthenticationPrincipal CustomUserDetails customUser) {
        List<FamilyUserDto> familyMembers = familyService.findFamilyMembers(customUser.getUser());
        return BaseResponse.okWithData(HttpStatus.OK, "가족 구성원 조회 성공", familyMembers);
    }

    @ApiOperation(value = "가족 생성")
    @ApiImplicitParam(name = "familyName", value = "가족 이름", required = true, dataType = "string", paramType = "path")
    @ApiResponse(code = 200, message = "가족 생성 성공")
    @PostMapping("{familyName}")
    public ResponseEntity<?> registerFamily(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUser, @PathVariable String familyName) {
        familyService.createFamily(customUser.getNickname(), familyName);

        return BaseResponse.ok(HttpStatus.OK, "가족 생성 성공");
    }


    @ApiOperation(value = "가족 이름 수정")
    @ApiImplicitParam(name = "familyName", value = "가족 이름", required = true, dataType = "string", paramType = "path")
    @ApiResponse(code = 200, message = "가족 이름 수정 성공")
    @PatchMapping("{familyName}")
    public ResponseEntity<?> modifyFamily(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUser, @PathVariable String familyName) {
        familyService.updateFamily(customUser.getNickname(), familyName);

        return BaseResponse.ok(HttpStatus.OK, "가족 이름 수정 성공");
    }

    @ApiOperation(value = "가족 삭제")
    @ApiResponse(code = 200, message = "가족 그룹 삭제 성공")
    @DeleteMapping
    public ResponseEntity<?> removeFamily(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUser) {
        familyService.deleteFamily(customUser.getNickname());

        return BaseResponse.ok(HttpStatus.OK, "가족 그룹 삭제 성공");
    }


    @ApiOperation(value = "가족 초대")
    @ApiImplicitParam(name = "nickname", value = "상대방 닉네임", required = true, dataType = "string", paramType = "path")
    @ApiResponse(code = 200, message = "초대 전송 성공")
    @PostMapping("/invitation/{nickname}")
    public ResponseEntity<?> sendFamilyInvitation(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUser, @PathVariable String nickname) {
        familyService.inviteToFamily(customUser.getNickname(), nickname);
        eventPublisher.publishEvent(new NotificationEvent(
                this, nickname,
                NotificationUtil.clickUrl("mypage"),
                NotificationDto.builder()
                .title("가족 초대")
                .body(customUser.getNickname() + "님이 [" + customUser.getUser().getFamily().getFamilyName() + "] 가족방에 초대했습니다.")
                .build()
        ));

        return BaseResponse.ok(HttpStatus.OK, "초대 전송 성공");
    }

    @ApiOperation(value = "가족 초대 거절")
    @ApiResponse(code = 200, message = "초대 거절 성공")
    @DeleteMapping("/invitation/deny")
    public ResponseEntity<?> denyFamilyInvite(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUser) {
        familyService.rejectInvitation(customUser.getNickname());

        return BaseResponse.ok(HttpStatus.OK, "초대 거절 성공");
    }

    @ApiOperation(value = "가족 초대 수락")
    @ApiResponse(code = 200, message = "초대 수락 성공")
    @PostMapping("/invitation/confirm")
    public ResponseEntity<?> confirmInvitation(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUser) {
        familyService.acceptInvitation(customUser.getNickname());

        return BaseResponse.ok(HttpStatus.OK, "초대 수락 성공");
    }

    @ApiOperation(value = "가족 구성원 추방")
    @ApiImplicitParam(name = "nickname", value = "상대방 닉네임", required = true, dataType = "string", paramType = "path")
    @ApiResponse(code = 200, message = "유저 추방 성공")
    @DeleteMapping("{nickname}")
    public ResponseEntity<?> removeUserFromFamily(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUser, @PathVariable String nickname) {
        familyService.deleteUserFromFamily(customUser.getNickname(), nickname);

        return BaseResponse.ok(HttpStatus.OK, "유저 추방 성공");
    }
}
