package com.woowahanbank.backend.domain.family.service;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.family.domain.Invitation;
import com.woowahanbank.backend.domain.family.dto.FamilyInvitationDto;
import com.woowahanbank.backend.domain.family.dto.FamilyUserDto;
import com.woowahanbank.backend.domain.family.repository.FamilyRepository;
import com.woowahanbank.backend.domain.family.repository.InvitationRepository;
import com.woowahanbank.backend.domain.user.domain.Role;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    public List<FamilyUserDto> findFamilyMembers(User user) {
        List<User> users = userRepository.findByFamily(user.getFamily());
        return users.stream()
                .map(User::toFamilyUserDto)
                .collect(Collectors.toList());
    }

    public Long createFamily(String nickname, String familyName) {
        User user = userRepository.findByNickname(nickname)
                .filter(u -> u.getFamily() == null)
                .orElseThrow(() -> new IllegalArgumentException("가족이 이미 존재하거나 유저를 찾을 수 없습니다"));
        Family family = Family.builder()
                .familyName(familyName)
                .users(new ArrayList<>())
                .build();
        family.addUser(user);
        family = familyRepository.save(family);
        Long generatedId = family.getId();

        return generatedId;
    }

    public void updateFamily(String nickname, String familyName) {
        User user = userRepository.findByNickname(nickname)
                .filter(u -> u.getRoles() == Role.ROLE_PARENT)
                .orElseThrow(() -> new IllegalArgumentException("부모 회원만 가능한 기능이거나 유저를 찾을 수 없습니다"));
        user.getFamily().updateFamilyName(familyName);
    }

    public void deleteFamily(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .filter(u -> u.getRoles() == Role.ROLE_PARENT)
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

    public void inviteToFamily(String fromUserNickname, String toUserNickname) {
        User fromUser = findUserOrElseThrow(fromUserNickname);
        User toUser = findUserOrElseThrow(toUserNickname);

        if(invitationRepository.findByFromUserAndToUser(fromUser, toUser).isEmpty()){
            Invitation invitation = Invitation.builder()
                    .fromUser(fromUser)
                    .toUser(toUser)
                    .family(fromUser.getFamily())
                    .build();
            invitationRepository.save(invitation);
        }
    }

    public void rejectInvitation(String toNickname) {
        Invitation invitation = invitationRepository.findByToUser_Nickname(toNickname)
                .orElseThrow(() -> new IllegalArgumentException("초대 내용을 찾을 수 없습니다"));
        invitationRepository.delete(invitation);
    }

    public void acceptInvitation(String toNickname) {
        Invitation invitation = invitationRepository.findByToUser_Nickname(toNickname)
                .orElseThrow(() -> new IllegalArgumentException("초대 내용을 찾을 수 없습니다"));
        Family family = invitation.getFamily();
        family.addUser(invitation.getToUser());

        invitationRepository.delete(invitation);
    }

    public void deleteUserFromFamily(String parentNickname, String targetNickname) {
        User parentUser = findUserOrElseThrow(parentNickname);
        User targetUser = findUserOrElseThrow(targetNickname);

        Family family = parentUser.getFamily();
        if (!family.equals(targetUser.getFamily())) {
            throw new IllegalArgumentException("나의 가족만 추방할 수 있습니다");
        }

        family.removeUser(targetUser);
    }

    private User findUserOrElseThrow(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다"));
    }

    public FamilyInvitationDto findInvitationUser(String toUserNickname) {
        User toUser = userRepository.findByNickname(toUserNickname).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

        Invitation invitationUser = invitationRepository.findByToUser(toUser).orElseThrow(() -> new IllegalArgumentException("초대가 없습니다."));

        String familyName = invitationUser.getFamily().getFamilyName();
        String fromUserNickname = invitationUser.getFromUser().getNickname();

        FamilyInvitationDto familyInvitationDto = new FamilyInvitationDto();
        familyInvitationDto.setFamilyNickname(familyName);
        familyInvitationDto.setFromNickname(fromUserNickname);

        return familyInvitationDto;
    }

    public void acceptInv (String familyName, String userNickName) {
        Family byFamilyName = familyRepository.findByFamilyName(familyName).orElseThrow(() -> new IllegalArgumentException("가족을 찾을 수 없습니다."));
        User byNickname = userRepository.findByNickname(userNickName).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        byNickname.setFamily(byFamilyName);
    }

    public void deleteInv (String familyName, String userNickName) {
        System.out.println(userNickName+"이건 서비스 닉네임");
        Family byFamilyName = familyRepository.findByFamilyName(familyName).orElseThrow(() -> new IllegalArgumentException("가족이 없습니다."));
        User user = userRepository.findByNickname(userNickName).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        System.out.println(user+"이건 서비스 유저 마지막");
        invitationRepository.deleteByToUserAndFamily(user, byFamilyName);
    }
}
