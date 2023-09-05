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
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String familyName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "family")
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
        user.setFamily(this);
    }

    public void removeUser(User user) {
        this.users.add(user);
        user.setFamily(this);
    }
}
