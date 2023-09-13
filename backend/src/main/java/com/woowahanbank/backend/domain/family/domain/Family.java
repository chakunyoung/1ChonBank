package com.woowahanbank.backend.domain.family.domain;

import com.woowahanbank.backend.domain.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Family Entity")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class Family {

    @ApiModelProperty(notes = "Primary Key")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "가족 이름")
    private String familyName;

    @ApiModelProperty(notes = "가족 구성원 리스트")
    @OneToMany(mappedBy = "family")
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
        user.setFamily(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.setFamily(null);
    }

    public void updateFamilyName(String familyName) {
        this.familyName = familyName;
    }
}