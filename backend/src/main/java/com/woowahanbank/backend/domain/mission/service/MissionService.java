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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    @Transactional
    public void createMission(MissionMakeDto missionMakeDto) {

        Family familyId = familyRepository.findByFamilyName(missionMakeDto.getMissionFamilyName()).orElseThrow(() -> new IllegalArgumentException("오류"));

        Mission mission = Mission.builder()
                .missionName(missionMakeDto.getMissionName())
                .familyId(familyId)
                .missionDescription(missionMakeDto.getMissionDescription())
                .missionPoint(missionMakeDto.getMissionPoint())
                .missionStatus(missionMakeDto.getMissionStatus())
                .missionTerminateDate(missionMakeDto.getMissionTerminateDate())
                .build();

        System.out.println(mission);

        missionRepository.save(mission);
    }

    public List<Mission> getMissionByFamilyId(User user) {
        System.out.println(user);
        System.out.println("유저야유저");
        if(user != null){
            Family family = familyRepository.findById(user.getFamily().getId())
                    .orElseThrow(() -> new IllegalArgumentException(" 오류 "));
            return missionRepository.findByFamilyId(family);
        }
        return Collections.emptyList();
    }

    public List<Mission> getMissionByChildNickName(String nickname) {
        User childUser = userRepository.findByNickname(nickname).
            orElseThrow(() -> new IllegalArgumentException("아이 닉네임에 해당되는 유저가 없습니다."));
        return missionRepository.findByChildUser(childUser);
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
}

