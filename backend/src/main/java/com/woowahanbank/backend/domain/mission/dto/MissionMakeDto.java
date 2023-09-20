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
    private String childNickname;
    private String parentNickname;
    private String missionDescription;
    private Integer missionPoint;
    private Date missionTermivateDate;

    public Mission MissiontoEntity(MissionMakeDto missionMakeDto) {
        return Mission.builder()
                .missionName(missionName)
                .missionFamilyId(missionFamilyId)
                .childNickname(childNickname)
                .parentNickname(parentNickname)
                .missionDescription(missionDescription)
                .missionPoint(missionPoint)
                .missionTerminateDate(missionTermivateDate)
                .build();
    }
}
