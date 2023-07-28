package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.repository.sysmanage.SysmanageEnvrEntmngRepository;
import com.skshieldus.esecurity.service.sysmanage.SysmanageEnvrEntmngService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysmanageEnvrEntmngServiceImpl implements SysmanageEnvrEntmngService {

    @Autowired
    private SysmanageEnvrEntmngRepository repository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private Environment environment;

    // 출입 차량 제한 관리 > 조회 - selectCarLimitsList
    @Override
    public ListDTO<Map<String, Object>> selectCarLimitsList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer count = 0;

        try {
            resultList = repository.selectCarLimitsList(paramMap);
            count = repository.selectCarLimitsCount(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, count);
    }

    // 출입 차량 제한 관리 > 엑셀다운 - selectCarLimitsListExcel
    @Override
    public CommonXlsViewDTO selectCarLimitsListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("xlsname" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.selectCarLimitsListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    ;

    // 출입 차량 제한 관리 > 상세 - selectCarLimitsView
    @Override
    public Map<String, Object> selectCarLimitsView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            String operateId = "R_VIOLATION"; // 운영
            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) { //로컬, 개발
                operateId = "D_VIOLATION";
            }
            paramMap.put("operateId", operateId);
            resultView = repository.selectCarLimitsView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 출입 차량 제한 관리 > 입력 - insertCarLimits
    @Override
    public int insertCarLimits(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            //
            updatedRows = repository.insertCarLimits(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 출입 차량 제한 관리 > 수정 - updateCarLimits
    @Override
    public int updateCarLimits(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            //
            updatedRows = repository.updateCarLimits(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 출입 차량 제한 관리 > 삭제 - deleteCarLimits
    @Override
    public int deleteCarLimits(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            //
            updatedRows = repository.deleteCarLimits(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 구성원 관리(제한관리) > 조회 - offLimitsEmpCardList
    @Override
    public List<Map<String, Object>> offLimitsEmpCardList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            String searchEmpId = ObjectUtils.isEmpty(paramMap.get("searchEmpId"))
                ? ""
                : String.valueOf(paramMap.get("searchEmpId")).trim().replaceAll(" ", "");
            String searchEmpNm = ObjectUtils.isEmpty(paramMap.get("searchEmpNm"))
                ? ""
                : String.valueOf(paramMap.get("searchEmpNm")).trim().replaceAll(" ", "");
            String[] searchEmpIdList = StringUtils.isEmpty(searchEmpId)
                ? null
                : searchEmpId.split(",");
            String[] searchEmpNmList = StringUtils.isEmpty(searchEmpNm)
                ? null
                : searchEmpNm.split(",");
            paramMap.put("searchEmpIdList", searchEmpIdList);
            paramMap.put("searchEmpNmList", searchEmpNmList);
            System.out.println("## offLimitsEmpCardList paramMap : " + paramMap.toString());
            resultList = repository.offLimitsEmpCardList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 구성원 관리(제한관리) > 엑셀다운 - offLimitsEmpCardListExcel
    @Override
    public CommonXlsViewDTO offLimitsEmpCardListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        //commonXlsViewDTO.setFileName("CO_EMPCARD_DENY_LIST" + "_"  + sdf.format(new Date()));
        commonXlsViewDTO.setFileName("사원증 출입 제한자 목록");

        try {
            // set header names
            String[] headerNameArr = { "번호", "정지순번", "사번", "성명", "부서코드"
                , "부서명", "출입정지시작일자", "출입정지종료일자", "생성자", "정지코드"
                , "정지코드명", "정지사유", "해제일자", "해제코드", "해제코드명"
                , "해제사유", "제한여부", "삭제여부", "최종수정자" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "rowNum", "denyNo", "empId", "empNm", "deptId"
                , "deptNm", "denyStrtDt", "denyEndDt", "crtBy", "denyCode"
                , "denyCodeNm", "denyRsn", "denyCancelDt", "denyCancelCode", "denyCancelCodeNm"
                , "denyCancelRsn", "denyYn", "delYn", "modBy" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            String searchEmpId = ObjectUtils.isEmpty(paramMap.get("searchEmpId"))
                ? ""
                : String.valueOf(paramMap.get("searchEmpId")).trim().replaceAll(" ", "");
            String searchEmpNm = ObjectUtils.isEmpty(paramMap.get("searchEmpNm"))
                ? ""
                : String.valueOf(paramMap.get("searchEmpNm")).trim().replaceAll(" ", "");
            String[] searchEmpIdList = StringUtils.isEmpty(searchEmpId)
                ? null
                : searchEmpId.split(",");
            String[] searchEmpNmList = StringUtils.isEmpty(searchEmpNm)
                ? null
                : searchEmpNm.split(",");
            paramMap.put("searchEmpIdList", searchEmpIdList);
            paramMap.put("searchEmpNmList", searchEmpNmList);
            System.out.println("## offLimitsEmpCardListExcel paramMap : " + paramMap.toString());

            List<Map<String, Object>> list = repository.offLimitsEmpCardListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    ;

    // 구성원 관리(제한관리) > 출입제한 등록(다건) - offLimitsEmpCardDenyMultipleInsert
    @Override
    public int offLimitsEmpCardDenyMultipleInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;

        try {

            String rDenyStrtDt = ObjectUtils.isEmpty(paramMap.get("denyStrtDt"))
                ? ""
                : String.valueOf(paramMap.get("denyStrtDt")); // 제한기간(시작일)
            String rDenyEndDt = ObjectUtils.isEmpty(paramMap.get("denyEndDt"))
                ? ""
                : String.valueOf(paramMap.get("denyEndDt")); // 제한기간(종료일)
            String rDenyCode = ObjectUtils.isEmpty(paramMap.get("denyCode"))
                ? ""
                : String.valueOf(paramMap.get("denyCode")); // 제한구분코드
            String rDenyRsn = ObjectUtils.isEmpty(paramMap.get("denyRsn"))
                ? ""
                : String.valueOf(paramMap.get("denyRsn")); // 제한사유
            String rCrtBy = ObjectUtils.isEmpty(paramMap.get("crtBy"))
                ? ""
                : String.valueOf(paramMap.get("crtBy"));

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
			/*
			INSERT INTO CO_EMPCARD_DENY (
				      DENY_NO, EMP_ID, EMPCARD_NO, DEPT_ID 
				      , DENY_RSN, DENY_STRT_DT, DENY_END_DT
				      , DENY_CODE,  DENY_YN
				      , CRT_BY, CRT_DTM, MOD_BY, MOD_DTM
				) VALUES (
				      SEQ_EMPCARD_DENY_NO.NEXTVAL
				    , #{empId}
				    , #{empcardNo}
				    , #{deptId}
				    , #{denyRsn}
				    , #{denyStrtDt}
				    , #{denyEndDt}
				    , #{denyCode}
				    , 'Y'
				    , #{crtBy}
				    , SYSDATE
				    , #{crtBy}
				    , SYSDATE
				)
			*/
            // Loop Start
            for (Map<String, Object> objItem : arrItems) {

                Map<String, Object> objMap = new HashMap<String, Object>();

                objMap.put("empId", String.valueOf(objItem.get("empId")));
                objMap.put("empcardNo", "H00" + String.valueOf(objItem.get("empId")));
                objMap.put("deptId", String.valueOf(objItem.get("deptId")));

                objMap.put("denyRsn", rDenyRsn);
                objMap.put("denyStrtDt", rDenyStrtDt);
                objMap.put("denyEndDt", rDenyEndDt);
                objMap.put("denyCode", rDenyCode);
                objMap.put("crtBy", rCrtBy);

                // Step01. 출입제한 등록 - INSERT INTO CO_EMPCARD_DENY
                updatedRows += repository.offLimitsEmpCardDenyInsert(objMap);

                /* Step02. SK 하이스텍 IDCARD 관리 시스템에 등록 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출  Start */
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String sf = format.format(new Date());
                if (sf.toString().equals(rDenyStrtDt)) {
                    if (environment.acceptsProfiles(Profiles.of("prod"))) {
			    		/*
			    		 @TODO- 다중DB접속환경 설정 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출
			    		  - EXPR_APPL_NO : 0
			    		  - S_C_GBN : 'S'
			    		  - EMPCARD_NO : "H00" + String.valueOf(objItem.get("empId"))
			    		  - EXPR_CODE : rDenyCode
			    		 <procedure id="dmOffLimits_EmpCardExprHist" resultClass="hmap" >
			             exec [dbo].[uSP_SK_IF_IO_PASS_EXPR_HIST] 
			             		@EXPR_APPL_NO = #EXPR_APPL_NO# 
			             		,@S_C_GBN=#S_C_GBN#     -- 정지:S, 해제:C 
			             		,@IDCARD_ID=#EMPCARD_NO# -- H00+사번
			             		,@EXPR_CODE=#EXPR_CODE#  -- A0760001(코로나정지), A0770001(코로나정지해지)
			             		,@PTYPE='H' -- 본사:H, 시스템IC :8, 입주사:N
			             </procedure>
			            */
                    }
                }
                /* SK 하이스텍 IDCARD 관리 시스템에 등록 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출  End */

            } // End Loop
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 구성원 관리(제한관리) > 출입제한 해제(다건) - offLimitsEmpCardDenyMultipleUpdate
    @Override
    public int offLimitsEmpCardDenyMultipleUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String rDenyCancelDt = ObjectUtils.isEmpty(paramMap.get("denyCancelDt"))
                ? ""
                : String.valueOf(paramMap.get("denyCancelDt")); // 제한해제 일자
            String rDenyCancelCode = ObjectUtils.isEmpty(paramMap.get("denyCancelCode"))
                ? ""
                : String.valueOf(paramMap.get("denyCancelCode")); // 제한해제구분코드
            String rDenyCancelRsn = ObjectUtils.isEmpty(paramMap.get("denyCancelRsn"))
                ? ""
                : String.valueOf(paramMap.get("denyCancelRsn")); // 제한해제 사유
            String rModBy = ObjectUtils.isEmpty(paramMap.get("modBy"))
                ? ""
                : String.valueOf(paramMap.get("modBy"));

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
			
			/*
			 UPDATE CO_EMPCARD_DENY 
			   SET DENY_CANCEL_DT = TO_CHAR(SYSDATE,'YYYY-MM-DD')
				 , DENY_CANCEL_CODE = 'A0770001'
				 , DENY_CANCEL_RSN = #{denyCancelRsn}
			     , DENY_YN = 'N' 
			     , MOD_BY = #{crtBy}
			     , MOD_DTM = SYSDATE
			WHERE 
			    DENY_NO = #{denyNo}
			 */

            // Loop Start
            for (Map<String, Object> objItem : arrItems) {

                Map<String, Object> objMap = new HashMap<String, Object>();

                objMap.put("denyNo", String.valueOf(objItem.get("denyNo"))); // 순번Key
                objMap.put("denyCancelRsn", rDenyCancelRsn); // 제한해제 사유
                objMap.put("denyCancelDt", rDenyCancelDt); // 제한해제 일자
                objMap.put("denyCancelCode", rDenyCancelCode); // 제한해제구분코드
                objMap.put("modBy", rModBy); // 수정자ID

                // Step01. 출입제한 해제 - UPDATE CO_EMPCARD_DENY
                updatedRows += repository.offLimitsEmpCardDenyUpdate(objMap);

                /* Step02. SK 하이스텍 IDCARD 관리 시스템에 등록 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출  Start */
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (format.parse(rDenyCancelDt).before(format.parse(format.format(new Date())))) {
                    if (environment.acceptsProfiles(Profiles.of("prod"))) {
			    		/*
			    		 @TODO- 다중DB접속환경 설정 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출
			    		  - EXPR_APPL_NO : 0
			    		  - S_C_GBN : 'C'
			    		  - EMPCARD_NO : "H00" + String.valueOf(objItem.get("empId"))
			    		  - EXPR_CODE : "A0770001"
			    		 <procedure id="dmOffLimits_EmpCardExprHist" resultClass="hmap" >
			             exec [dbo].[uSP_SK_IF_IO_PASS_EXPR_HIST] 
			             		@EXPR_APPL_NO = #EXPR_APPL_NO# 
			             		,@S_C_GBN=#S_C_GBN#     -- 정지:S, 해제:C 
			             		,@IDCARD_ID=#EMPCARD_NO# -- H00+사번
			             		,@EXPR_CODE=#EXPR_CODE#  -- A0760001(코로나정지), A0770001(코로나정지해지)
			             		,@PTYPE='H' -- 본사:H, 시스템IC :8, 입주사:N
			             </procedure>
			            */
                    }
                }
                /* SK 하이스텍 IDCARD 관리 시스템에 등록 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출  End */

            }
            // End Loop

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 구성원 관리(제한관리) > 출입날짜 변경(다건) - offLimitsEmpCardDenyDateMultipleUpdate
    @Override
    public int offLimitsEmpCardDenyDateMultipleUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            String rDenyStrtDt = ObjectUtils.isEmpty(paramMap.get("denyStrtDt"))
                ? ""
                : String.valueOf(paramMap.get("denyStrtDt")); // 제한기간(시작일)
            String rDenyEndDt = ObjectUtils.isEmpty(paramMap.get("denyEndDt"))
                ? ""
                : String.valueOf(paramMap.get("denyEndDt")); // 제한기간(종료일)
            String rDenyCode = ObjectUtils.isEmpty(paramMap.get("denyCode"))
                ? ""
                : String.valueOf(paramMap.get("denyCode")); // 제한구분코드
            String rDenyRsn = ObjectUtils.isEmpty(paramMap.get("denyRsn"))
                ? ""
                : String.valueOf(paramMap.get("denyRsn")); // 제한사유
            String rModBy = ObjectUtils.isEmpty(paramMap.get("modBy"))
                ? ""
                : String.valueOf(paramMap.get("modBy"));

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
			
			/*
			UPDATE CO_EMPCARD_DENY 
			   SET DENY_END_DT = #{denyEndDt}
				 , MOD_BY = #{modBy}
				 , MOD_DTM = SYSDATE
			WHERE 
			    DENY_NO = #{denyNo}
			    AND EMP_ID = #{empId}
			 */

            // Loop Start
            for (Map<String, Object> objItem : arrItems) {

                Map<String, Object> objMap = new HashMap<String, Object>();

                objMap.put("denyNo", String.valueOf(objItem.get("denyNo"))); // 순번Key
                objMap.put("empId", String.valueOf(objItem.get("empId"))); // emp_id
                objMap.put("denyEndDt", rDenyEndDt); // 제한기간(종료일)
                objMap.put("modBy", rModBy); // 수정자ID

                // 출입날짜 변경 - UPDATE CO_EMPCARD_DENY
                updatedRows = repository.offLimitsEmpCardDenyDateUpdate(objMap);
            }
            // End Loop

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 구성원 관리(제한관리) > 상세 - offLimitsEmpCardView
    @Override
    public Map<String, Object> offLimitsEmpCardView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.offLimitsEmpCardView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 구성원 관리(제한관리) > 출입제한이력 - offLimitsEmpCardHistory
    @Override
    public List<Map<String, Object>> offLimitsEmpCardHistory(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.offLimitsEmpCardHistory(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 방문객 관리(제한관리) > 조회 - offLimitsList
    @Override
    public ListDTO<Map<String, Object>> offLimitsList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.offLimitsList(paramMap);
            totalCount = repository.offLimitsCount(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    // 방문객 관리(제한관리) > 엑셀다운 - offLimitsListExcel
    @Override
    public CommonXlsViewDTO offLimitsListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        //commonXlsViewDTO.setFileName("CO_EMPCARD_DENY_LIST" + "_"  + sdf.format(new Date()));
        commonXlsViewDTO.setFileName("방문객 상시출입증 제한자 목록");

        try {
            // set header names
            String[] headerNameArr = { "번호", "성명", "ID", "직윈", "국적"
                , "생년월일", "연락처", "삭제여부", "등록자", "출입제한 시작일"
                , "출입제한 종료일", "카드번호", "대표관리자", "본사제한정보번호", "하이스텍제한정보번호"
                , "시스템IC제한정보번호", "휴면여부", "소속회사번호", "소속회사명", "통합사번" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "rowNum", "empNm", "ioEmpId", "jwNm", "nationNm"
                , "juminNo", "hpNo", "delYn", "crtBy", "denyStrtDt"
                , "denyEndDt", "cardNo", "admNm", "denyNo", "hsDenyNo"
                , "siDenyNo", "sleepYn", "ioCompId", "compKoNm", "idcardId" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            String searchPassNo = ObjectUtils.isEmpty(paramMap.get("searchPassNo"))
                ? ""
                : String.valueOf(paramMap.get("searchPassNo")).trim().replaceAll(" ", "");
            String[] searchPassNoList = StringUtils.isEmpty(searchPassNo)
                ? null
                : searchPassNo.split(",");
            paramMap.put("searchPassNoList", searchPassNoList); // 통합사번 List

            System.out.println("## offLimitsListExcel paramMap : " + paramMap.toString());

            List<Map<String, Object>> list = repository.offLimitsListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    ;

    // 방문객 관리(제한관리) > 상세 - offLimitsView
    @Override
    public Map<String, Object> offLimitsView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.offLimitsView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 방문객 관리(제한관리) > 출입제한이력 - offLimitsHistoryList
    @Override
    public List<Map<String, Object>> offLimitsHistoryList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.offLimitsHistoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 방문객 관리(제한관리) > 보안위규이력 - offLimitSecViolationHist
    @Override
    public List<Map<String, Object>> offLimitSecViolationHist(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.offLimitSecViolationHist(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 방문객 관리(제한관리) > 출입제한 등록
    @Override
    public void insertOffLimitsDeny(Map<String, Object> paramMap) {
        try {
            // 출입제한 이력 등록
            repository.insertOffLimitsHistory(paramMap);

            // 이천 출입증이 있는지 확인 후 있으면
            Map<String, Object> passCheck = repository.selectPassCheck(paramMap);
            if (passCheck != null && !ObjectUtils.isEmpty(passCheck.get("idcardId"))) {
                paramMap.put("sCGbn", "S"); // 정지:S, 해제:C
                repository.insertOffLimitsExprHistory(paramMap);

                if (environment.acceptsProfiles(Profiles.of("prd"))) {
                    paramMap.put("exprCode", "A0460009");
                    // Step_3) 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                    idcardVisitRepository.procedureOffLimitsIoPassExprHist(paramMap);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    // 방문객 관리(제한관리) > 출입제한 해제
    @Override
    public void deleteOffLimitsDeny(Map<String, Object> paramMap) {
        try {
            // 출입제한 이력 등록
            repository.updateOffLimitsHistory(paramMap);

            // 이천 출입증이 있는지 확인 후 있으면
            Map<String, Object> passCheck = repository.selectPassCheck(paramMap);
            if (passCheck != null && !ObjectUtils.isEmpty(passCheck.get("idcardId"))) {
                paramMap.put("sCGbn", "C"); // 정지:S, 해제:C
                paramMap.put("denyRsn", "방문객관리(제한관리) 해제");
                paramMap.put("denyStrtDt", "00000000");
                paramMap.put("denyEndDt", "00000000");
                repository.insertOffLimitsExprHistory(paramMap);

                if (environment.acceptsProfiles(Profiles.of("prd"))) {
                    paramMap.put("exprCode", "A0460009");
                    // Step_3) 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                    idcardVisitRepository.procedurePassExcptIFIoPassExprHist(paramMap);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

}
