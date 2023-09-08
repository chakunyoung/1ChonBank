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
	@Column(name = "mission_name")
	private String missionName;

	@ManyToOne
	@JoinColumn(name="mission_family_id")
	private Family missionFamilyId;

	@ManyToOne
	@JoinColumn(name="mission_child_id")
	private User missionChildId;

	@ManyToOne
	@JoinColumn(name="mission_parent_id")
	private User missionParentId;

	@NotNull
	@Column(name = "mission_description")
	private String missionDescription;

	@Column(name = "mission_status", columnDefinition = "VARCHAR(255) DEFAULT '시작전'")
	private String missionStatus;

	@NotNull
	@Column(name = "mission_point")
	private Integer missionPoint;

	@NotNull
	@Column(name = "mission_terminate_date")
	private Date missionTerminateDate;

}
