package com.woowahanbank.backend.domain.family.service;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.family.repository.FamilyRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.domain.UserType;
import com.woowahanbank.backend.domain.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    void createFamily(String email, String familyName) {
        User user = userRepository.findByEmail(email)
                .filter(u -> u.getType() == UserType.PARENT)
                .orElseThrow(() -> new IllegalArgumentException("부모 회원만 가능한 기능이거나 유저를 찾을 수 없습니다"));
        Family family = Family.builder().familyName(familyName).build();
        family.addUser(user);
        familyRepository.save(family);
    }

    void updateFamily(String email, String familyName) {
        User user = userRepository.findByEmail(email)
                .filter(u -> u.getType() == UserType.PARENT)
                .orElseThrow(() -> new IllegalArgumentException("부모 회원만 가능한 기능이거나 유저를 찾을 수 없습니다"));
        user.getFamily().updateFamilyName(familyName);
    }

    void deleteFamily(String email) {
        User user = userRepository.findByEmail(email)
                .filter(u -> u.getType() == UserType.PARENT)
                .orElseThrow(() -> new IllegalArgumentException("부모 회원만 가능한 기능이거나 유저를 찾을 수 없습니다"));
        Family family = user.getFamily();
        Optional.ofNullable(family.getUsers())
                .filter(users -> users.size() == 1)
                .ifPresentOrElse(
                        users -> {
                            family.removeUser(user);
                            familyRepository.deleteById(family.getId());
                        },
                        () -> {
                            throw new IllegalArgumentException("가족에 남은 회원이 본인만 있어야 합니다");
                        }
                );
    }
}
