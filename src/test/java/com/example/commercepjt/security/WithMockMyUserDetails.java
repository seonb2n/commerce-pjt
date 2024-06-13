package com.example.commercepjt.security;

import com.example.commercepjt.common.enums.RoleStatus;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * 테스트에서 User 를 Mocking 하기 위한 annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@WithSecurityContext(factory = WithMockMyUserDetailsSecurityContextFactory.class)
public @interface WithMockMyUserDetails {
    String loginId() default "login_id";
    String loginPassword() default "login_password";
    String nickName() default "jake";
    long userId() default 1L;
    RoleStatus roleStatus() default RoleStatus.SELLER;
}
