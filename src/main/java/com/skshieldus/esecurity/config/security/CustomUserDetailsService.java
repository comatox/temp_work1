package com.skshieldus.esecurity.config.security;

import com.skshieldus.esecurity.model.common.CoEmpDTO;
import com.skshieldus.esecurity.repository.common.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CoEmpDTO coEmpDTO = authRepository.selectLoginUserAccountCheck(username);
        if (coEmpDTO == null) {
            throw new UsernameNotFoundException(username);
        }
        CustomUserDetails user = new CustomUserDetails();
        user.setId(coEmpDTO.getId());
        user.setName(coEmpDTO.getEmpNm());
        user.setAuthority("USER");
        return user;
    }

}
