package com.woowahanbank.backend.domain.user.domain;

import com.sun.istack.NotNull;
import com.woowahanbank.backend.domain.family.domain.Family;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Column(name = "money", nullable = false, columnDefinition = "bigint default 0")
    private Long money;

    @NotNull
    private String name;

    private Long age;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;

    private Long quiz;

    private Long score;

    public void setFamily(Family family) {
        this.family = family;
    }
}
