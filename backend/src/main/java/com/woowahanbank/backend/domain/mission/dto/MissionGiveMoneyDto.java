package com.woowahanbank.backend.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionGiveMoneyDto {
	private Long missionId;
	private Long money;
}
