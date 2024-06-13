package com.example.commercepjt.security;

import com.example.commercepjt.common.enums.RoleStatus;
import com.example.commercepjt.common.utils.auth.MyUserDetails;
import com.example.commercepjt.domain.User;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockMyUserDetailsSecurityContextFactory implements
    WithSecurityContextFactory<WithMockMyUserDetails> {

    @Override
    public SecurityContext createSecurityContext(WithMockMyUserDetails customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // 구매자인 경우
        if (customUser.roleStatus() == RoleStatus.BUYER) {
            UserBuyer user = UserBuyer.builder().id(customUser.userId())
                .loginId(customUser.loginId()).loginPassword(
                    customUser.loginPassword()).nickName(customUser.nickName()).build();
            return createSecurityContext(user, context);
        }
        // 판매자인 경우
        UserSeller user = UserSeller.builder().id(customUser.userId())
            .loginId(customUser.nickName()).loginPassword(
                customUser.loginPassword()).nickName(customUser.nickName()).build();
        return createSecurityContext(user, context);
        //todo admin 인 경우 추가 필요
    }

    private static SecurityContext createSecurityContext(User user, SecurityContext context) {
        MyUserDetails principal = new MyUserDetails(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal,
            principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}

