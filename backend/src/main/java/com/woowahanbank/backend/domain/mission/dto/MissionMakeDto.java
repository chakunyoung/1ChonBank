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

    private String missionName;
    private Long missionFamilyId;
    private String missionDescription;
    private Integer missionPoint;
    private Date missionTerminateDate;

    public Mission MissiontoEntity(MissionMakeDto missionMakeDto) {


        return Mission.builder()
                .missionName(missionName)
                .missionDescription(missionDescription)
                .missionPoint(missionPoint)
                .missionTerminateDate(missionTerminateDate)
                .build();
    }
}
