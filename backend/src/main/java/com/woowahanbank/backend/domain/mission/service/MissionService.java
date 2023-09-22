package com.woowahanbank.backend.domain.mission.service;

import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.mission.dto.MissionMakeDto;
import com.woowahanbank.backend.domain.mission.repository.MissionRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

    public void createMission(MissionMakeDto missionMakeDto) {
        missionRepository.save(missionMakeDto.MissiontoEntity(missionMakeDto));
    }

    public List<Mission> getMissionByFamilyId(User user) {
        if(user != null){
            Long missionFamilyId = user.getFamily().getId();
            return missionRepository.findByFamilyId(missionFamilyId);

        }
        return Collections.emptyList();
    }

    public List<Mission> getMissionByChildNickName(String nickname) {
        User childUser = userRepository.findByNickname(nickname).
            orElseThrow(() -> new IllegalArgumentException("아이 닉네임에 해당되는 유저가 없습니다."));
        return missionRepository.findByChildUser(childUser);
    }

    public Mission updateMission(MissionMakeDto updatedMission) {
        Long missionFamilyId = updatedMission.getMissionFamilyId();
        String missionName = updatedMission.getMissionName();

        Mission missionUpdated = missionRepository.findByFamilyIdAndMissionName(missionFamilyId, missionName)
            .orElseThrow(() -> new IllegalArgumentException("해당되는 미션이 없습니다."));
        missionUpdated.solved();
        return missionRepository.save(missionUpdated);
    }

    public void deleteMissionById(MissionMakeDto deletedMission) {
        Long missionFamilyId = deletedMission.getMissionFamilyId();
        String missionName = deletedMission.getMissionName();

        missionRepository.deleteByFamilyIdAndMissionName(missionFamilyId, missionName);
    }
}

