package com.woowahanbank.backend.domain.mission.controller;

import com.woowahanbank.backend.domain.mission.dto.MissionMakeDto;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.woowahanbank.backend.domain.mission.service.MissionService;
import com.woowahanbank.backend.domain.mission.domain.Mission;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

	private final MissionService missionService;

	@PostMapping("/make")
	public ResponseEntity<?> createMission(@RequestBody MissionMakeDto missionMakeDto) {
		missionService.createMission(missionMakeDto);
		System.out.println(missionMakeDto);
		System.out.println("받았다");
		return BaseResponse.ok(HttpStatus.OK, "새로운 미션");
	}

	@GetMapping("/family-id")
	public ResponseEntity<?> getMissionListByFamilyId(@AuthenticationPrincipal CustomUserDetails customUser) {
		User customUserUser = customUser.getUser();
		List<Mission> missionListByFamilyId = missionService.getMissionByFamilyId(customUserUser);
		return BaseResponse.okWithData(HttpStatus.OK, "가족 전체 미션 리스트", missionListByFamilyId);
	}

	@GetMapping("/child-nickname")
	public ResponseEntity<?>getMissionListByChildNickName(@AuthenticationPrincipal CustomUserDetails customUser) {
		String nickname = customUser.getUser().getNickname();
		List<Mission> missionListByChildNickname = missionService.getMissionByChildNickName(nickname);
		return BaseResponse.okWithData(HttpStatus.OK, "해당 아이 미션 리스트", missionListByChildNickname);
	}

	@PutMapping("/{missionName}")
	public Mission updateMission(@AuthenticationPrincipal CustomUserDetails user,
			@RequestBody MissionMakeDto updatedMission) {
		return missionService.updateMission(user.getUser(), updatedMission);
	}

	@DeleteMapping("/{missionName}")
	public void deleteMissionById(@AuthenticationPrincipal CustomUserDetails user,
			@RequestBody MissionMakeDto deletedMission) {
		missionService.deleteMissionById(user.getUser(), deletedMission);
	}

}
