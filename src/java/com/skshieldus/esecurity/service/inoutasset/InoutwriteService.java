package com.skshieldus.esecurity.service.inoutasset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface InoutwriteService {

    /**
     * 자산반출입 구분별 그룹정보 목록 조회
     *
     * @param paramMap
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> selectArticleGroupCodeList(HashMap<String, Object> paramMap);

    /**
     * 자산반출입 신청 저장/상신
     *
     * @param paramMap
     * @param fileToUpload
     */
    Map<String, Object> saveInoutwrite(HashMap<String, Object> paramMap, List<MultipartFile> fileToUpload);

}
