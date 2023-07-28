package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.PartnerDTO;
import com.skshieldus.esecurity.model.common.PartnerSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PartnerRepository {

    // 목록 조회
    List<PartnerDTO> selectPartnerList(PartnerSearchDTO partnerSearchDTO);

    // 목록건수 조회
    Integer selectPartnerListCnt(PartnerSearchDTO partnerSearchDTO);

    // 상세 조회
    PartnerDTO selectPartner(String compId);

    /****
     * 외부업체 관리 기능(등록, 수정, 삭제) 추가
     * 2021-03-31 박대호
     */
    // 외부업체 등록
    int insertPartner(PartnerDTO partnerDTO);

    // 외부업체 수정
    int updatePartner(PartnerDTO partnerDTO);

    // 외부업체 삭제
    int deletePartner(String systempartnerid);

    // 사업자 번호 중복 여부 ( PART_CNT > 0 THEN 'Y' ELSE 'N' )
    String partnerLegChk(PartnerDTO partnerDTO);

}
