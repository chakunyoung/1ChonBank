package com.woowahanbank.backend.domain.mission.controller;

import com.woowahanbank.backend.domain.mission.dto.MissionDetailDto;
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
	public ResponseEntity<?> createMission(@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody MissionMakeDto missionMakeDto) {
		missionService.createMission(missionMakeDto);

		return BaseResponse.ok(HttpStatus.OK, "새로운 미션");
	}

	@GetMapping("/family-id")
	public ResponseEntity<?> getMissionListByFamilyId(@AuthenticationPrincipal CustomUserDetails customUser) {
		User customUserUser = customUser.getUser();

		List<MissionMakeDto> missionListByFamilyId = missionService.getMissionByFamilyId(customUserUser);
		return BaseResponse.okWithData(HttpStatus.OK, "가족 전체 미션 리스트", missionListByFamilyId);
	}

	@GetMapping("/child-nickname")
	public ResponseEntity<?> getMissionListByChildNickName(@AuthenticationPrincipal CustomUserDetails customUser) {
		User customUserUser = customUser.getUser();

		List<MissionMakeDto> missionListByChildNickName = missionService.getMissionByChildNickName(customUserUser);
		return BaseResponse.okWithData(HttpStatus.OK, "가족 전체 미션 리스트", missionListByChildNickName);
	}

	@GetMapping("/detail/{missionId}")
	public ResponseEntity<?> getMissionDetailByMissionId(@PathVariable Long missionId) {
		MissionDetailDto missionDetailDto = missionService.getDetail(missionId);
		return BaseResponse.okWithData(HttpStatus.OK, "미션 디테일", missionDetailDto);
	}

	@PutMapping("/{missionId}")
	public ResponseEntity<?> updateMission(@PathVariable Long missionId) {
		missionService.updateMissionStatus(missionId);
		return BaseResponse.ok(HttpStatus.OK,"상태 변경 완료");
	}

	@PutMapping("/refuse/{missionId}")
	public ResponseEntity<?> refuseMission(@PathVariable Long missionId) {
		missionService.refuseMission(missionId);
		return BaseResponse.ok(HttpStatus.OK,"미션 거절 완료");
	}

	@DeleteMapping("/{missionId}")
	public void deleteMissionById(@PathVariable Long missionId) {
		missionService.deleteMissionById(missionId);
	}

	@PutMapping("/give-money/{missionId}")
	public ResponseEntity<?> giveMoneyTochild(@PathVariable Long missionId, Long money){

		return BaseResponse.ok(HttpStatus.OK, "돈 지급 완료");
	}

	@DeleteMapping("/all")
	public void deleteAll(@AuthenticationPrincipal CustomUserDetails user) {
		missionService.deleteAll();
	}

}
