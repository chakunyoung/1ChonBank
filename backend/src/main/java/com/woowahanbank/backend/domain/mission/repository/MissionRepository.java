package com.woowahanbank.backend.domain.mission.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.mission.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

	List<Optional<Mission>> findByMissionFamilyId(Long missionFamilyId);

	List<Optional<Mission>> findByChildNickname(String nickname);

	void deleteByMissionId(Long missionId);

	void deleteByMissionTerminateDate(Date missionTermivateDate);

}
