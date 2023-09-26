package com.woowahanbank.backend.domain.mission.domain;

import java.util.Date;

import javax.persistence.*;
import com.sun.istack.NotNull;
import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.user.domain.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;



@ApiModel(description = "Mission Entity")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Mission {

	@Id
	@ApiModelProperty(notes = "Primary Key")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;


	@ApiModelProperty(notes = "미션 이름")
	private String missionName;

	@ApiModelProperty(notes = "가족 아이디")
	@ManyToOne(fetch=FetchType.LAZY)
	private Family familyId;

	@ApiModelProperty(notes = "아이 닉네임")
	@ManyToOne(fetch=FetchType.EAGER)
	private User childUser;

	@ApiModelProperty(notes = "부모님 닉네임")
	@ManyToOne(fetch=FetchType.LAZY)
	private User parentUser;

	@ApiModelProperty(notes = "미션 설명")
	private String missionDescription;

	@ApiModelProperty(notes = "미션 상황")
	@Column(columnDefinition = "VARCHAR(255) DEFAULT '시작전'")
	private String missionStatus;

	@ApiModelProperty(notes = "미션 포인트")
	private Integer missionPoint;

	@ApiModelProperty(notes = "미션 종료시간")
	private Date missionTerminateDate;

	public void start() {
		this.missionStatus = "진행중";
	}
	public void solved(){
		this.missionStatus = "완료";
	}

}
