package com.woowahanbank.backend.domain.mission.repository;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByFamilyId(Family familyId);

    List<Mission> findByChildUser(User childUser);

    void deleteByMissionName(String missionName);

    Optional<Mission> findByFamilyIdAndMissionName(Family family, String missionName);

    void deleteByFamilyIdAndMissionName(Family missionFamilyId, String missionName);

}
