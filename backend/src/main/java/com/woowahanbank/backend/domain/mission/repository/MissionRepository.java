package com.woowahanbank.backend.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanbank.backend.domain.mission.domain.Mission;

import java.util.Date;
import java.util.List;
import java.util.Optional;
public interface MissionRepository extends JpaRepository<Mission, Long> {

	List<Optional<Mission>> findByMissionName(String missionName);
	List<Optional<Mission>> findByMissionFamilyId(Long missionFamilyId);
	List<Optional<Mission>> findByMissionChildId(Long missionChildId);
	List<Optional<Mission>> findByMissionStatus(String missionStatus);
	Mission save(Mission mission);
	void deleteByMissionId(Long missionId);
	void deleteByMissionTerminateDate(Date missionTermivateDate);

}
