package com.example.commercepjt.service.auth;

import com.example.commercepjt.common.utils.auth.MyUserDetails;
import com.example.commercepjt.domain.User;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.repository.UserBuyerRepository;
import com.example.commercepjt.repository.UserSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserBuyerRepository userBuyerRepository;
    private final UserSellerRepository userSellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBuyer userBuyer = userBuyerRepository.findByUsername(username);
        if (userBuyer != null) {
            return new MyUserDetails(userBuyer);
        }

        UserSeller userSeller = userSellerRepository.findByUsername(username);
        if (userSeller != null) {
            return new MyUserDetails(userSeller);
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
