package com.woowahanbank.backend.domain.mission.repository;

import com.woowahanbank.backend.domain.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByFamilyId(Long familyId);

    List<Mission> findByChildNickname(String ChildNickname);

    void deleteByMissionName(String missionName);

    void deleteByMissionTerminateDate(Date missionTerminateDate);

    Optional<Mission> findByMissionFamilyIdAndMissionName(Long missionFamilyId, String missionName);

    Long findByMissionId(Long missionFamilyId, String missionName);


}
