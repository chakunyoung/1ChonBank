package com.woowahanbank.backend.domain.mission.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.mission.repository.MissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MissionService {

	private final MissionRepository missionRepository;

	public Mission createMission(Mission mission) {
		return missionRepository.save(mission);
	}

	public List<Mission> getMissionAll(){
		return missionRepository.findAll();
	}
	public Optional<Mission> getMissionsById(Long missionId) {
		return missionRepository.findById(missionId);
	}

	public List<Optional<Mission>> getMissionsByName(String missionName) {
		return missionRepository.findByMissionName(missionName);
	}

	public List<Optional<Mission>> getMissionsByFamilyId(Long MissionFamilyId) {
		return missionRepository.findByMissionFamilyId(MissionFamilyId);
	}

	public List<Optional<Mission>> getMissionsByChildId(Long missionChildId) {
		return missionRepository.findByMissionChildId(missionChildId);
	}

	public List<Optional<Mission>> getMissionsByStatus(String missionStatus) {
		return missionRepository.findByMissionStatus(missionStatus);
	}

	public Mission updateMission(Mission updatedMission) {
		return missionRepository.save(updatedMission);
	}

	public void deleteMissionById(Long missionId) {
		missionRepository.deleteById(missionId);
	}

	public void deleteMissionsByTerminateDateBefore(Date terminateDate) {
		missionRepository.deleteByMissionTerminateDate(terminateDate);
	}

}

