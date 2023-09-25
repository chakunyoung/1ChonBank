package com.woowahanbank.backend.domain.mission.service;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.family.repository.FamilyRepository;
import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.mission.dto.MissionMakeDto;
import com.woowahanbank.backend.domain.mission.repository.MissionRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
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

        Family familyId = familyRepository.findByFamilyName(missionMakeDto.getMissionFamilyName()).orElseThrow(() -> new IllegalArgumentException("오류"));

        User parentUser = userRepository.findByUserId(missionMakeDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("오류"));

        User childUser = userRepository.findByNickname(missionMakeDto.getSelectedChild()).orElseThrow(() -> new IllegalArgumentException("오류"));

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
            missionMakeDto.setMissionName(mission.getMissionName());
            missionMakeDto.setMissionDescription(mission.getMissionDescription());
            missionMakeDto.setMissionPoint(mission.getMissionPoint());
            missionMakeDto.setMissionStatus(mission.getMissionStatus());

            missionDtoList.add(missionMakeDto);
        }

        return missionDtoList;
    }

    public Mission updateMission(User user, MissionMakeDto updatedMission) {
        String missionName = updatedMission.getMissionName();
        Family family = familyRepository.findById(user.getFamily().getId())
                .orElseThrow(() -> new IllegalArgumentException(" 오류 "));

        Mission missionUpdated = missionRepository.findByFamilyIdAndMissionName(family, missionName)
            .orElseThrow(() -> new IllegalArgumentException("해당되는 미션이 없습니다."));
        missionUpdated.solved();
        return missionRepository.save(missionUpdated);
    }

    public void deleteMissionById(User user, MissionMakeDto deletedMission) {
        String missionName = deletedMission.getMissionName();
        Family family = familyRepository.findById(user.getFamily().getId())
                .orElseThrow(() -> new IllegalArgumentException(" 오류 "));

        missionRepository.deleteByFamilyIdAndMissionName(family, missionName);
    }

    public void deleteAll() {
        missionRepository.deleteAll();
    }
}

