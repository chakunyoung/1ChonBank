package com.woowahanbank.backend.domain.mission.repository;

import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByFamilyId(Long familyId);

    //List<Mission> findByChildNickname(String ChildNickname);
    List<Mission> findByChildUser(User childUser);

    void deleteByMissionName(String missionName);

    void deleteByMissionTerminateDate(Date missionTerminateDate);

    Optional<Mission> findByFamilyIdAndMissionName(Long missionFamilyId, String missionName);

    void deleteByFamilyIdAndMissionName(Long missionFamilyId, String missionName);

}
