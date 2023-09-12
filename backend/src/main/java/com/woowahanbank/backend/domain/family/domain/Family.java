package com.woowahanbank.backend.domain.family.domain;

import com.woowahanbank.backend.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String familyName;

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
