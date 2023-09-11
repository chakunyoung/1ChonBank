package com.woowahanbank.backend.domain.mission.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.mission.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

	List<Optional<Mission>> findByMissionName(String missionName);

<<<<<<< Updated upstream
	List<Optional<Mission>> findByMissionFamilyId(Long missionFamilyId);

	List<Optional<Mission>> findByMissionChildId(Long missionChildId);
=======
	List<Optional<Mission>> findByMissionFamilyId(long missionFamilyId);

	List<Optional<Mission>> findByMissionChildId(long missionChildId);
>>>>>>> Stashed changes

	List<Optional<Mission>> findByMissionStatus(String missionStatus);

	void deleteByMissionId(Long missionId);

<<<<<<< Updated upstream
	void deleteByMissionTerminateDate(Date missionTermivateDate);
=======
	void deleteByMissionTerminateDate(Date missionTerminateDate);
>>>>>>> Stashed changes

}
