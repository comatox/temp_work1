package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxSearchDTO;
import com.skshieldus.esecurity.repository.sysmanage.ApprovalBoxRepository;
import com.skshieldus.esecurity.service.sysmanage.ApprovalBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApprovalBoxServiceImpl implements ApprovalBoxService {

    @Autowired
    private ApprovalBoxRepository approvalBoxRepository;

    @Autowired
    private Mailing mailing;

    @Override
    public List<ApprovalBoxDTO> selectApprovalBoxRequestList(ApprovalBoxSearchDTO approvalBoxSearch) {

        log.info(">>>> selectApprovalBoxRequestList : " + approvalBoxSearch);

        return approvalBoxRepository.selectApprovalBoxRequestList(approvalBoxSearch);
    }

    @Override
    public List<ApprovalBoxDTO> selectApprovalBoxApprovalList(ApprovalBoxSearchDTO approvalBoxSearch) {

        log.info(">>>> selectApprovalBoxApprovalList : " + approvalBoxSearch);

        return approvalBoxRepository.selectApprovalBoxApprovalList(approvalBoxSearch);
    }

    @Override
    public List<ApprovalBoxDTO> selectApprovalBoxIdCardList(ApprovalBoxSearchDTO approvalBoxSearch) {

        log.info(">>>> selectApprovalBoxIdCardList : " + approvalBoxSearch);

        return approvalBoxRepository.selectApprovalBoxIdCardList(approvalBoxSearch);
    }

}