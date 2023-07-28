package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.repository.entmanage.EmergencyRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.EmergencyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class EmergencyServiceImpl implements EmergencyService {

    @Autowired
    private EmergencyRepository repository;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment environment;

    @Override
    public boolean insertEmergencyReg(Map<String, Object> paramMap) {
        boolean result = true;
        boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

        int count = repository.insertEmergencyReg(paramMap);

        // if success
        if (count == 1) {
            try {
                if (isProd) {
                    String vstType = paramMap.get("vstGbn").equals("1")
                        ? "공무"
                        : "VIP";
                    String msg = "긴급(" + vstType + ") 출입 안내드립니다. \r\n";
                    msg += "- 업체명 : " + ObjectUtils.defaultIfNull(paramMap.get("firstOrgNm"), "") + "\r\n";
                    msg += "- 성 명 : " + ObjectUtils.defaultIfNull(paramMap.get("firstVstr"), "") + "\r\n";

                    // 카카오 전송 => 1 주 담당직원
                    // 주석처리 2023-06-09
                    //					if (!StringUtils.isEmpty(ObjectUtils.defaultIfNull(paramMap.get("empHp"), "").toString())) {
                    //						// ================= NOTE: SMS(kakao) 발송 시작 =======================
                    //						RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                    //						KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                    //						kakaoMessageDTO.setKTemplateCode("SJT_048086");
                    //						kakaoMessageDTO.setSubject("긴급출입안내");
                    //						kakaoMessageDTO.setDstaddr(paramMap.get("empHp").toString());
                    //						kakaoMessageDTO.setCallback("");
                    //						kakaoMessageDTO.setText(msg);
                    //						kakaoMessageDTO.setText2(msg);
                    //						kakaoMessageDTO.setKAttach("");
                    //
                    //						wrapParams.setParams(kakaoMessageDTO);
                    //						commonApiClient.sendKakaoMessage(wrapParams);
                    //						// ================= NOTE: SMS(kakao) 발송 종료 =======================
                    //					}

                    // 카카오 전송 => 2 주 담당직원
                    // 주석처리 2023-06-09
                    //					if (!StringUtils.isEmpty(ObjectUtils.defaultIfNull(paramMap.get("empSecHp"), "").toString())) {
                    //						// ================= NOTE: SMS(kakao) 발송 시작 =======================
                    //						RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                    //						KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                    //						kakaoMessageDTO.setKTemplateCode("SJT_048086");
                    //						kakaoMessageDTO.setSubject("긴급출입안내");
                    //						kakaoMessageDTO.setDstaddr(paramMap.get("empSecHp").toString());
                    //						kakaoMessageDTO.setCallback("");
                    //						kakaoMessageDTO.setText(msg);
                    //						kakaoMessageDTO.setText2(msg);
                    //						kakaoMessageDTO.setKAttach("");
                    //
                    //						wrapParams.setParams(kakaoMessageDTO);
                    //						commonApiClient.sendKakaoMessage(wrapParams);
                    //						// ================= NOTE: SMS(kakao) 발송 종료 =======================
                    //					}
                }
            } catch (Exception e) {
                log.error("긴급출입 등록 카카오 메시지 발송 실패 = {}", e.getMessage());
            }
        }
        else {
            result = false;
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> selectEmergencyList(Map<String, Object> paramMap) {
        return repository.selectEmergencyList(paramMap);
    }

    @Override
    public boolean updateEmergency(Map<String, Object> paramMap) {
        return Objects.equals(repository.updateEmergency(paramMap), 1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean deleteEmergency(Map<String, Object> paramMap) {
        List<Integer> emergencyNoList = objectMapper.convertValue(paramMap.get("emergencyNoList"), List.class);
        return emergencyNoList.stream().allMatch(emergencyNo -> {
            paramMap.put("emergencyNo", emergencyNo);
            return Objects.equals(repository.deleteEmergency(paramMap), 1);
        });
    }

    @Override
    public Map<String, Object> selectEmerencyView(Integer emergencyNo) {
        return repository.selectEmerencyView(emergencyNo);
    }

    @Override
    public Integer selectEmerencyVipCnt(Map<String, Object> paramMap) {
        return repository.selectEmerencyVipCnt(paramMap);
    }

}
