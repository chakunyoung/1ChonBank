package com.woowahanbank.backend.domain.mission.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.mission.dto.MissionMakeDto;
import com.woowahanbank.backend.domain.mission.repository.MissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MissionService {

	private final MissionRepository missionRepository;

	public void createMission(MissionMakeDto missionMakeDto) {
		missionRepository.save(missionMakeDto.MissiontoEntity(missionMakeDto));
	}

	public List<Optional<Mission>> getMissionByFamilyId(Long missionFamilyId) {
		return missionRepository.findByMissionFamilyId(missionFamilyId);

	}

	public List<Optional<Mission>> getMissionByChildNickName(String nickname) {
		return missionRepository.findByChildNickname(nickname);
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

