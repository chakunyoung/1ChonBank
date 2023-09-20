package com.woowahanbank.backend.domain.mission.domain;

import java.util.Date;

import javax.persistence.*;
import com.sun.istack.NotNull;
import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mission")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Mission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mission_id")
	private Long missionId;

	@NotNull
	private String missionName;

	private Long missionFamilyId;

	private String childNickname;

	private String parentNickname;

	@NotNull
	private String missionDescription;

	@Column(name = "mission_status", columnDefinition = "VARCHAR(255) DEFAULT '시작전'")
	private String missionStatus;

	@NotNull
	private Integer missionPoint;

	private Date missionTerminateDate;

}
