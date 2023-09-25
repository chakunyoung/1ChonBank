package com.woowahanbank.backend.domain.mission.dto;

import com.woowahanbank.backend.domain.mission.domain.Mission;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.JoinDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionMakeDto {

    private Long missionId;
    private String missionName;
    private String missionFamilyName;
    private String selectedChild;
    private String missionDescription;
    private int missionPoint;
    private Date missionTerminateDate;
    private String missionStatus;
    private String userId;


}
