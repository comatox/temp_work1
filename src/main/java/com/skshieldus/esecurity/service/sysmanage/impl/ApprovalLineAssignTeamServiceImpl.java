/**
 *
 */
package com.skshieldus.esecurity.service.sysmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.sysmanage.ApprovalLineAssignTeamRepository;
import com.skshieldus.esecurity.service.sysmanage.ApprovalLineAssignTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 결재선 팀장지정자
 * @author : X0115378 <jaehoon5.kim@partner.sk.com>
 * @since : 2022. 01. 17.
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApprovalLineAssignTeamServiceImpl implements ApprovalLineAssignTeamService {

    @Autowired
    private ApprovalLineAssignTeamRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    // 부서의 팀장지정자 목록 조회
    @Override
    public List<Map<String, Object>> selectApprLineAssignTeamList(String deptId) {
        return repository.selectApprLineAssignTeamList(deptId);
    }

    // 부서의 사원 목록 조회
    @Override
    public List<Map<String, Object>> selectApprEmpListByDeptTeam(String deptId) {
        return repository.selectApprEmpListByDeptTeam(deptId);
    }

    // 로그인 사용자의 부서 목록 조회
    @Override
    public List<Map<String, Object>> selectApprDeptTeamList(String empId) {
        return repository.selectApprDeptTeamList(empId);
    }

    // 팀장지정자 등록
    @Override
    public Integer saveApprLineAssig(Map<String, Object> data) {
        // TODO Auto-generated method stub
        Integer resultCount = null;
        try {
            String deptId = data.getOrDefault("deptId", "").toString();
            List<Map<String, Object>> listData = objectMapper.convertValue(data.get("listData"), List.class);

            if (StringUtils.isEmpty(deptId)) {
                throw new EsecurityException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "부서를 필수로 지정하여야 합니다.");
            }
            resultCount = repository.deleteApprLineAssignTeam(deptId);
            for (int i = 0; i < listData.size(); i++) {
                Map<String, Object> detailData = listData.get(i);
                resultCount += repository.insertApprLineAssignTeam(detailData);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return resultCount;
    }

}
