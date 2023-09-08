package com.woowahanbank.backend.domain.mission.dto;

import java.util.Date;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MissionDto {

	private Long missionId;
	private String missionName;
	private Long missionFamilyId;
	private Long missionChildId;
	private Long missionParentId;
	private String missionDescription;
	private String missionStatus;
	private Integer missionPoint;
	private Date missionTermivateDate;
}
