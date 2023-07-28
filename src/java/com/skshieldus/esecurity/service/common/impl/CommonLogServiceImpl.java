package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.model.common.CoLogDTO;
import com.skshieldus.esecurity.model.common.CoSyAcLogDTO;
import com.skshieldus.esecurity.model.common.MailLogDTO;
import com.skshieldus.esecurity.repository.common.CommonLogRepository;
import com.skshieldus.esecurity.service.common.CommonLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonLogServiceImpl implements CommonLogService {

    @Autowired
    private CommonLogRepository commonLogRepository;

    // API 로그 등록
    @Override
    public boolean insertCommonLog(CoLogDTO coLogDTO) {
        String menuId = coLogDTO.getMenuId();
        if (menuId.length() > 9)
            coLogDTO.setMenuId(null);
        return Objects.equals(commonLogRepository.insertCommonLog(coLogDTO), 1);
    }

    // 메뉴접속 로그 등록
    @Override
    public boolean insertSyAcLog(CoSyAcLogDTO coSyAcLogDTO) {
        return Objects.equals(commonLogRepository.insertSyAcLog(coSyAcLogDTO), 1);
    }

    // 메일전송 로그 등록
    @Override
    public boolean insertMailLog(List<MailLogDTO> mailLogList) {
        boolean result = true;
        try {
            if (mailLogList != null && mailLogList.size() > 0) {
                mailLogList.stream().forEach(data -> commonLogRepository.insertMailLog(data));
            }
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

}
