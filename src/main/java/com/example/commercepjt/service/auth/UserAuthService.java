package com.example.commercepjt.service.auth;

import com.example.commercepjt.common.enums.RoleStatus;
import com.example.commercepjt.domain.Role;
import com.example.commercepjt.domain.User;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.repository.RoleRepository;
import com.example.commercepjt.repository.UserBuyerRepository;
import com.example.commercepjt.repository.UserSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService {

    private final UserBuyerRepository userBuyerRepository;

    private final UserSellerRepository userSellerRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(String username, String password, RoleStatus roleStatus) {
        if (roleStatus.equals(RoleStatus.BUYER)
            && userBuyerRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (roleStatus.equals(RoleStatus.SELLER)
            && userSellerRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findRoleByRoleStatus(roleStatus);
        if (role == null) {
            role = new Role(roleStatus);
            roleRepository.save(role);
        }

        User user;
        if (RoleStatus.SELLER.equals(roleStatus)) {
            user = userSellerRepository.save(
                UserSeller.builder().loginId(username).nickName(username).loginPassword(password)
                    .role(role).build());
        } else if (RoleStatus.BUYER.equals(roleStatus)) {
            user = userBuyerRepository.save(
                UserBuyer.builder().loginId(username).nickName(username).loginPassword(password)
                    .role(role).build());
        } else {
            // todo admin 회원 가입 추가
            throw new IllegalArgumentException("Unsupported role");
        }

        return user;
    }

}
