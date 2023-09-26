package com.woowahanbank.backend.domain.mission.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.user.domain.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionChangeStatusDto {
	private Long id;
	private String missionStatus;
	public void start() {
		this.missionStatus = "진행중";
	}
	public void solved(){
		this.missionStatus = "완료";
	}
}
