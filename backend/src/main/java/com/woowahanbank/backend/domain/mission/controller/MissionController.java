package com.woowahanbank.backend.domain.mission.controller;

import com.woowahanbank.backend.domain.mission.dto.MissionMakeDto;
import com.woowahanbank.backend.global.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		return BaseResponse.ok(HttpStatus.OK, "새로운 미션");
	}

	@GetMapping("/family-id")
	public ResponseEntity<?> getMissionListByFamilyId(@RequestParam Long missionFamilyId) {
		List<Optional<Mission>> missionListByFamilyId = missionService.getMissionByFamilyId(missionFamilyId);
		return BaseResponse.okWithData(HttpStatus.OK, "가족 전체 미션 리스트", missionListByFamilyId);
	}

	@GetMapping("/child-nickname")
	public ResponseEntity<?>getMissionListByChildNickName(@RequestParam String nickname) {
		List<Optional<Mission>> missionListByChildNickname = missionService.getMissionByChildNickName(nickname);
		return BaseResponse.okWithData(HttpStatus.OK, "가족 전체 미션 리스트", missionListByChildNickname);
	}

	@PutMapping("/{missionId}")
	public Mission updateMission(@PathVariable Long missionId, @RequestBody Mission updatedMission) {

		return missionService.updateMission(updatedMission);
	}

	@DeleteMapping("/{missionId}")
	public void deleteMissionById(@PathVariable Long missionId) {
		missionService.deleteMissionById(missionId);
	}

	@DeleteMapping("/terminate-date/{terminateDate}")
	public void deleteMissionsByTerminateDateBefore(@PathVariable Date terminateDate) {
		missionService.deleteMissionsByTerminateDateBefore(terminateDate);
	}
}
