package com.skshieldus.esecurity.service.secrtactvy;

import java.util.List;
import java.util.Map;

public interface SecurityNationalCoreTechService {

    List<Map<String, Object>> selectSecurityNationalCoreTechChecklist(Map<String, Object> paramMap);

    Map<String, Object> selectSecurityNationalCoreTechChecklistView(Map<String, Object> paramMap);

    boolean insertNationalCoreTechChecklist(Map<String, Object> paramMap);

    List<Map<String, Object>> selectSecurityNationalCoreTechList(Map<String, Object> paramMap);

    Map<String, Object> selectSecurityNationalCoreTechView(Map<String, Object> paramMap);

    boolean insertNationalCoreTech(Map<String, Object> paramMap);

    List<Map<String, Object>> selectLastChecklist(Map<String, Object> paramMap);

    boolean updateCodeInfo(Map<String, Object> paramMap);

}
