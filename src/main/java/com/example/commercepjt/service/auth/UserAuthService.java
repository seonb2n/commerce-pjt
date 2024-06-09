package com.example.commercepjt.service.auth;

import com.example.commercepjt.common.enums.RoleStatus;
import com.example.commercepjt.common.utils.auth.JwtUtil;
import com.example.commercepjt.domain.Role;
import com.example.commercepjt.domain.User;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.repository.RoleRepository;
import com.example.commercepjt.repository.UserBuyerRepository;
import com.example.commercepjt.repository.UserSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

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

        Role role = roleRepository.findFirstByRoleStatus(roleStatus);
        if (role == null) {
            role = new Role(roleStatus);
            roleRepository.save(role);
        }

        User user;
        if (RoleStatus.SELLER.equals(roleStatus)) {
            user = userSellerRepository.save(
                UserSeller.builder().loginId(username).nickName(username)
                    .loginPassword(passwordEncoder.encode(password))
                    .role(role).build());
        } else if (RoleStatus.BUYER.equals(roleStatus)) {
            user = userBuyerRepository.save(
                UserBuyer.builder().loginId(username).nickName(username)
                    .loginPassword(passwordEncoder.encode(password))
                    .role(role).build());
        } else {
            // todo admin 회원 가입 추가
            throw new IllegalArgumentException("Unsupported role");
        }

        return user;
    }

    public String authenticate(String loginId, String password)
        throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, password));
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
