package com.woowahanbank.backend.domain.mission.service;

import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.mission.dto.MissionMakeDto;
import com.woowahanbank.backend.domain.mission.repository.MissionRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MissionService {


    private final MissionRepository missionRepository;

    public void createMission(MissionMakeDto missionMakeDto) {
        missionRepository.save(missionMakeDto.MissiontoEntity(missionMakeDto));
    }

    public List<Mission> getMissionByFamilyId(User user) {
        Long missionFamilyId = user.getFamily().getId();
        return missionRepository.findByFamilyId(missionFamilyId);
    }

    public List<Mission> getMissionByChildNickName(String nickname) {
        return missionRepository.findByChildNickname(nickname);
    }

    public Mission updateMission(MissionMakeDto updatedMission) {
        Long missionFamilyId = updatedMission.getMissionFamilyId();
        String missionName = updatedMission.getMissionName();

        Mission missionUpdated = missionRepository.findByMissionFamilyIdAndMissionName(missionFamilyId, missionName).orElseThrow(() -> new IllegalArgumentException("시발아 ㅋㅋ"));
        missionUpdated.solved();
        return missionRepository.save(missionUpdated);
    }

    public void deleteMissionById(MissionMakeDto deletedMission) {
        Long missionFamilyId = deletedMission.getMissionFamilyId();
        String missionName = deletedMission.getMissionName();

        Long missionId = missionRepository.findByMissionId(missionFamilyId, missionName);

        missionRepository.deleteById(missionId);
    }

}

