package com.woowahanbank.backend.domain.mission.service;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.family.repository.FamilyRepository;
import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.mission.dto.MissionDetailDto;
import com.woowahanbank.backend.domain.mission.dto.MissionGiveMoneyDto;
import com.woowahanbank.backend.domain.mission.dto.MissionMakeDto;
import com.woowahanbank.backend.domain.mission.repository.MissionRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MissionService {

	private final MissionRepository missionRepository;
	private final UserRepository userRepository;
	private final FamilyRepository familyRepository;

	@Transactional
	public void createMission(MissionMakeDto missionMakeDto) {

		Family familyId = familyRepository.findByFamilyName(missionMakeDto.getMissionFamilyName())
			.orElseThrow(() -> new IllegalArgumentException("오류"));

		User parentUser = userRepository.findByUserId(missionMakeDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("오류"));

		User childUser = userRepository.findByNickname(missionMakeDto.getSelectedChild())
			.orElseThrow(() -> new IllegalArgumentException("오류"));

		Mission mission = Mission.builder()
			.missionName(missionMakeDto.getMissionName())
			.familyId(familyId)
			.parentUser(parentUser)
			.childUser(childUser)
			.missionDescription(missionMakeDto.getMissionDescription())
			.missionPoint(missionMakeDto.getMissionPoint())
			.missionStatus(missionMakeDto.getMissionStatus())
			.missionTerminateDate(missionMakeDto.getMissionTerminateDate())
			.build();

		System.out.println(mission);

		missionRepository.save(mission);
	}

	public List<MissionMakeDto> getMissionByFamilyId(User user) {

		List<MissionMakeDto> missionDtoList = new ArrayList<>();
		Family family = user.getFamily();
		List<Mission> missionList = missionRepository.findByFamilyId(family);

		for (Mission mission : missionList) {

			MissionMakeDto missionMakeDto = new MissionMakeDto();
			missionMakeDto.setMissionId(mission.getId());
			missionMakeDto.setMissionName(mission.getMissionName());
			missionMakeDto.setMissionDescription(mission.getMissionDescription());
			missionMakeDto.setMissionPoint(mission.getMissionPoint());
			missionMakeDto.setMissionStatus(mission.getMissionStatus());

			missionDtoList.add(missionMakeDto);
		}

		return missionDtoList;
	}

	public List<MissionMakeDto> getMissionByChildNickName(User user) {

		List<MissionMakeDto> missionDtoList = new ArrayList<>();

		Family family = user.getFamily();
		List<Mission> missionList = missionRepository.findByFamilyId(family);

		for (Mission mission : missionList) {
			MissionMakeDto missionMakeDto = new MissionMakeDto();
			missionMakeDto.setMissionId(mission.getId());
			missionMakeDto.setMissionName(mission.getMissionName());
			missionMakeDto.setMissionDescription(mission.getMissionDescription());
			missionMakeDto.setMissionPoint(mission.getMissionPoint());
			missionMakeDto.setMissionStatus(mission.getMissionStatus());

			missionDtoList.add(missionMakeDto);
		}

		return missionDtoList;
	}

	public MissionDetailDto getDetail(Long id) {
		Mission mission = missionRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Mission 아이디 없음"));
		return MissionDetailDto.builder()
			.missionId(mission.getId())
			.missionName(mission.getMissionName())
			.childName(mission.getChildUser().getNickname())
			.missionDescription(mission.getMissionDescription())
			.missionPoint(mission.getMissionPoint())
			.missionStatus(mission.getMissionStatus())
			.missionTerminateDate(mission.getMissionTerminateDate())
			.build();
	}

	@Transactional
	public MissionDetailDto updateMissionStatus(Long missionId) {
		Mission mission = missionRepository.findById(missionId)
			.orElseThrow(() -> new IllegalArgumentException(" 미션 정보 없음"));
		mission.solved();
		return MissionDetailDto.builder()
			.missionId(mission.getId())
			.missionName(mission.getMissionName())
			.childName(mission.getChildUser().getNickname())
			.missionDescription(mission.getMissionDescription())
			.missionPoint(mission.getMissionPoint())
			.missionStatus(mission.getMissionStatus())
			.missionTerminateDate(mission.getMissionTerminateDate())
			.build();
	}

	@Transactional
	public MissionDetailDto refuseMission(Long missionId) {
		Mission mission = missionRepository.findById(missionId)
			.orElseThrow(() -> new IllegalArgumentException(" 미션 정보 없음"));
		mission.refuse();
		return MissionDetailDto.builder()
			.missionId(mission.getId())
			.missionName(mission.getMissionName())
			.childName(mission.getChildUser().getNickname())
			.missionDescription(mission.getMissionDescription())
			.missionPoint(mission.getMissionPoint())
			.missionStatus(mission.getMissionStatus())
			.missionTerminateDate(mission.getMissionTerminateDate())
			.build();

	}

	@Transactional
	public void missionClearMoney(MissionGiveMoneyDto missionGiveMoneyDto){
		Mission mission = missionRepository.findById(missionGiveMoneyDto.getMissionId()).orElseThrow(() -> new IllegalArgumentException("미션 정보 없음"));
		User user = userRepository.findByUserId(mission.getChildUser().getUserId()).orElseThrow(()-> new IllegalArgumentException("회원 정보 없음"));
		user.updatePoint(missionGiveMoneyDto.getMoney());

	}

	public void deleteMissionById(Long missionId) {
		missionRepository.deleteById(missionId);
	}

	public void deleteAll() {
		missionRepository.deleteAll();
	}
}

