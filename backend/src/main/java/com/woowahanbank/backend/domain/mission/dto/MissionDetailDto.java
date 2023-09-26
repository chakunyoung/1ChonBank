package com.woowahanbank.backend.domain.mission.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionDetailDto {
	private Long missionId;
	private String missionName;
	private String childName;
	private String missionDescription;
	private int missionPoint;
	private Date missionTerminateDate;
	private String missionStatus;
}