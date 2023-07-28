package com.skshieldus.esecurity.config.security;

import com.skshieldus.esecurity.model.common.CoEmpAuthDTO;
import com.skshieldus.esecurity.model.common.CoEmpDTO;
import com.skshieldus.esecurity.repository.common.AuthRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        CoEmpDTO searchDTO = new CoEmpDTO();
        searchDTO.setId(username);
        searchDTO.setPassword(password);
        Map<String, Object> userInfo = authRepository.selectLoginUserView(searchDTO);

        // PASSWORD 불일치
        if (userInfo == null) {
            authRepository.updateUpdateLoginUserFailCount(username);
            log.info("login fail = {} / FAIL_PASSWORD", username);
            throw new BadCredentialsException("FAIL_PASSWORD");
        }
        // 로그인 실패 횟수 초과 (LOCK)
        if (Integer.parseInt(userInfo.get("LOGIN_FAIL_CNT").toString()) >= 10) {
            log.info("login fail = {} / FAIL_LOGINCOUNT", username);
            throw new LockedException("FAIL_LOGINCOUNT");
        }
        // 퇴사자 처리
        if ("T".equals(userInfo.get("HT_CD"))) {
            log.info("login fail = {} / FAIL_LEAVE", username);
            throw new DisabledException("FAIL_LEAVE");
        }

        log.info("login success = {}", username);
        // 실패 횟수 초기화
        authRepository.updateUpdateLoginUserFailResetCount(username);

        List<CoEmpAuthDTO> coEmpAuthList = authRepository.selectCoEmpAuthList(CoEmpAuthDTO.builder().empId(username).build());
        List<Integer> authList = coEmpAuthList.stream().map(CoEmpAuthDTO::getAuthId).collect(Collectors.toList());
        userInfo.put("AUTH", authList);

        return new UsernamePasswordAuthenticationToken(userInfo, username, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}