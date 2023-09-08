package com.woowahanbank.backend.domain.mission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.woowahanbank.backend.domain.mission.service.MissionService;
import com.woowahanbank.backend.domain.mission.domain.Mission;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

	private final MissionService missionService;

	@PostMapping("/")
	public Mission createMission(@RequestBody Mission mission) {
		return missionService.createMission(mission);
	}

	@GetMapping("/family/{familyId}")
	public List<Optional<Mission>> getMissionsByFamilyId(@PathVariable Long missionfamilyId) {
		return missionService.getMissionsByFamilyId(missionfamilyId);
	}

	@GetMapping("/child/{childId}")
	public List<Optional<Mission>> getMissionsByChildId(@PathVariable Long missionChildId) {
		return missionService.getMissionsByChildId(missionChildId);
	}

	@GetMapping("/status/{status}")
	public List<Optional<Mission>> getMissionsByStatus(@PathVariable String missionStatus) {
		return missionService.getMissionsByStatus(missionStatus);
	}

	@PutMapping("/{missionId}")
	public Mission updateMission(@PathVariable Long missionId, @RequestBody Mission updatedMission) {

		return missionService.updateMission(updatedMission);
	}

	@DeleteMapping("/{missionId}")
	public void deleteMissionById(@PathVariable Long missionId) {
		missionService.deleteMissionById(missionId);
	}

	@DeleteMapping("/terminateDate/{terminateDate}")
	public void deleteMissionsByTerminateDateBefore(@PathVariable Date terminateDate) {
		missionService.deleteMissionsByTerminateDateBefore(terminateDate);
	}
}
