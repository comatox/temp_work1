package com.skshieldus.esecurity.service.entmanage.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.entmanage.EnvrEntmngRepository;
import com.skshieldus.esecurity.repository.entmanage.esecuritysi.EsecuritySiRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.EnvrEntmngService;
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
public class EnvrEntmngServiceImpl implements EnvrEntmngService {

    @Autowired
    private EnvrEntmngRepository repository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private EsecuritySiRepository esecuritySiRepository;

    @Autowired
    private Mailing mailing;

    @Autowired
    private Environment environment;

    @Autowired
    private ApprovalService approvalService;

    // 출입 차량 제한 관리 > 조회 - selectCarLimitsList
    @Override
    public List<Map<String, Object>> selectCarLimitsList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectCarLimitsList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
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
            //System.out.println("## offLimitsEmpCardList paramMap : " + paramMap.toString());
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
            //System.out.println("## offLimitsEmpCardListExcel paramMap : " + paramMap.toString());

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
                        Map<String, Object> procMap = new HashMap<String, Object>();
                        procMap.put("exprApplNo", 0); // 사원증 정지 경우 	EXPR_APPL_NO 를 '0' 값으로 던지기로 함 : 김강환대리, 윤여일 과장 협의 20201026
                        procMap.put("sCGbn", "S"); // 정지:S, 해제:C
                        procMap.put("empcardNo", "H00" + String.valueOf(objItem.get("empId"))); // H00+사번
                        procMap.put("exprCode", rDenyCode); // A0760001(코로나정지), A0770001(코로나정지해지)

                        // 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출
                        idcardVisitRepository.procedureOffLimitsEmpCardExprHist(procMap);
			    		/* 
			    		 @ASIS : 다중DB접속환경 설정 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출
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
                        Map<String, Object> procMap = new HashMap<String, Object>();
                        procMap.put("exprApplNo", 0); // 사원증 정지 경우 	EXPR_APPL_NO 를 '0' 값으로 던지기로 함 : 김강환대리, 윤여일 과장 협의 20201026
                        procMap.put("sCGbn", "C"); // 정지:S, 해제:C
                        procMap.put("empcardNo", "H00" + String.valueOf(objItem.get("empId"))); // H00+사번
                        procMap.put("exprCode", "A0770001"); // A0760001(코로나정지), A0770001(코로나정지해지)

                        // 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출
                        idcardVisitRepository.procedureOffLimitsEmpCardExprHist(procMap);
			    		/*
			    		 @ASIS- 다중DB접속환경 설정 - 하이스텍 정지/정지해지 신청 하는 I/F Procedure 호출
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

            //System.out.println("## offLimitsListExcel paramMap : " + paramMap.toString());

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

    // 방문객 관리(제한관리) > 상세 > 대표관리자초기화 - initRepresentAdmin
    @Override
    public int initRepresentAdmin(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.initRepresentAdmin(paramMap);
            // 외부DB - HS
            //			esecurityHsRepository.initRepresentAdmin(paramMap);
            // 외부DB - SI
            esecuritySiRepository.initRepresentAdmin(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 방문객 관리(제한관리) > 출입제한 등록 - offLimitsDenyMultiInsert
    @Override
    public int offLimitsDenyMultiInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;

        try {

            String rDenyStrtDt = ObjectUtils.isEmpty(paramMap.get("denyStrtDt"))
                ? ""
                : String.valueOf(paramMap.get("denyStrtDt")); // 제한기간(시작일)
            String rDenyCtgCd = ObjectUtils.isEmpty(paramMap.get("denyCtgCd"))
                ? ""
                : String.valueOf(paramMap.get("denyCtgCd")); // 제한구분코드

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");

            // Loop Start arrItems
            for (Map<String, Object> objItem : arrItems) {

                paramMap.put("ioEmpId", String.valueOf(objItem.get("ioEmpId")));
                paramMap.put("ioCompId", String.valueOf(objItem.get("ioCompId")));
                paramMap.put("compId", String.valueOf(objItem.get("compId")));
                paramMap.put("deptId", String.valueOf(objItem.get("deptId")));
                paramMap.put("jwId", String.valueOf(objItem.get("jwId")));


                /* 외부DB - HS ********************************************** */
                paramMap.put("siteType", "HS");
                // 출입제한 이력 Sequence  - EQ_IO_EMP_DENY.NEXTVAL
                //				String hsSeqIoEmpDeny =  esecurityHsRepository.offLimitsIoEmpDenySeq(paramMap);
                //				paramMap.put("seqIoEmpDeny", hsSeqIoEmpDeny); // 출입제한 이력 Sequence
                // 출입제한 이력 등록  - Insert IO_EMP_DENY
                //				updatedRows += esecurityHsRepository.offLimitsHistoryInsert(paramMap);

                /* 외부DB - SI ********************************************** */
                paramMap.put("siteType", "SI");
                // 출입제한 이력 Sequence  - EQ_IO_EMP_DENY.NEXTVAL
                String siSeqIoEmpDeny = esecuritySiRepository.offLimitsIoEmpDenySeq(paramMap);
                paramMap.put("seqIoEmpDeny", siSeqIoEmpDeny); // 출입제한 이력 Sequence
                // 출입제한 이력 등록  - Insert IO_EMP_DENY
                updatedRows += esecuritySiRepository.offLimitsHistoryInsert(paramMap);

                /* 본사DB - HN ********************************************** */
                paramMap.put("siteType", "HN");
                //				paramMap.put("hsDenyNo", hsSeqIoEmpDeny);
                paramMap.put("siDenyNo", siSeqIoEmpDeny);
                // 출입제한 이력 Sequence  - EQ_IO_EMP_DENY.NEXTVAL
                String seqIoEmpDeny = repository.offLimitsIoEmpDenySeq(paramMap);
                paramMap.put("seqIoEmpDeny", seqIoEmpDeny); // 출입제한 이력 Sequence
                // 출입제한 이력 등록  - Insert IO_EMP_DENY
                updatedRows += repository.offLimitsHistoryInsert(paramMap);
                /* ********************************************************* */

                //코로나에 의한 상시 출입증 정지 가 아니면, 본사만 수행함.*/
                /*방문예약은 3개사 모두 출입	하지 못하도록 해야함. */

                String[] siteList = { "HN", "HS", "SI" };
                // Loop Start
                for (int i = 0; i < siteList.length; i++) {
                    String siteType = siteList[i];
                    paramMap.put("siteType", siteType);

                    // 출입증 여부체크  - passMst2Check (SELECT CARD_NO, SMART_IDCARD FROM VW_IO_PASS_MST2)
                    Map<String, Object> retMap = dmPassMst2Check(paramMap);

                    if (retMap != null && ObjectUtils.isNotEmpty(retMap)) {

                        String smartIdcard = ObjectUtils.isEmpty(retMap.get("smartIdcard"))
                            ? ""
                            : String.valueOf(retMap.get("smartIdcard"));
                        String cardNo = ObjectUtils.isEmpty(retMap.get("cardNo"))
                            ? ""
                            : String.valueOf(retMap.get("cardNo"));

                        if (!"".equals(smartIdcard)) {

                            paramMap.put("smartIdcard", smartIdcard); // 스마트ID카드
                            paramMap.put("cardNo", cardNo); // 스마트ID카드
                            paramMap.put("sCGbn", "S"); // S_C_GBN : 'S'
                            paramMap.put("exprGbn", rDenyCtgCd); // 제한구분코드

                            // Step_1) 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
                            // Step_2) 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
                            String exprApplNo = dmOffLimitsExprHistoryInsert(paramMap);
                            paramMap.put("exprApplNo", exprApplNo); // 출입 외부 제한 이력 Sequence

                            boolean isCallProc = false;  // 하이스텍 정지신청 하는 I/F Procedure 호출여부

                            // 운영일 경우
                            if (updatedRows > 0 && environment.acceptsProfiles(Profiles.of("prd"))) {
                                // 코로나정지(A0460015) 일 경우
                                if ("A0460015".equals(rDenyCtgCd)) {
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    String sf = format.format(new Date());
                                    if (sf.toString().equals(rDenyStrtDt)) {
                                        isCallProc = true;
                                    }
                                }
                                else {
                                    isCallProc = true;
                                }
                            }

                            if (isCallProc) {

                                Map<String, Object> procMap = new HashMap<String, Object>();
                                procMap.put("exprApplNo", exprApplNo); //  출입 외부 제한 이력 Sequence
                                procMap.put("sCGbn", "S"); // 정지:S, 해제:C
                                procMap.put("idcardId", smartIdcard); // idCardId
                                procMap.put("exprCode", rDenyCtgCd); // 제한구분코드
                                procMap.put("siteType", siteType); // 사이트구분
					    		
								/* 환경설정 > 방문객 관리(제한관리) > 출입제한  - procedureOffLimitsIoPassExprHist (ASIS : dmOffLimits_IoPassExprHist)
								exec [dbo].[uSP_SK_IF_IO_PASS_EXPR_HIST] 
										  @EXPR_APPL_NO = #{exprApplNo} 
										, @S_C_GBN = #{sCGbn}
										, @IDCARD_ID = #{idcardId}
										, @EXPR_CODE = #{exprCode}
										<if test='"SI".equals(siteType)'>
										, @PTYPE='8'
										</if>
								 */
                                // Step_3) 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                                idcardVisitRepository.procedureOffLimitsIoPassExprHist(procMap);
                            } // End if (isCallProc)
                        } // End if (!"".equals(smartIdcard))
                    } // End if (retMap != null && ObjectUtils.isNotEmpty(retMap))

                    // 코로나정지일 경우 HN만 수행처리 ??
                    if ("HN".equals(siteType) && "A0460015".equals(rDenyCtgCd)) {
                        break;
                    }
                } // End For Loop siteTypes
            } // End For Loop  arrItems
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // Step_1) 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
    // Step_2) 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
    private String dmOffLimitsExprHistoryInsert(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<String, Object>();

        String siteType = StringUtils.defaultIfEmpty((String) paramMap.get("siteType"), "");
        String exprApplNo = "";
        int resultInt = 0;
        //System.out.println("## dmOffLimitsExprHistoryInsert paramMap:" + paramMap.toString());
        //System.out.println("## dmOffLimitsExprHistoryInsert siteType:" + siteType);

        /*외부DB연동 부분 */
        if ("HS".equals(siteType)) {

            // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
            //	    	exprApplNo = esecurityHsRepository.offLimitsIoPassExprHistSeq(paramMap);

            paramMap.put("exprApplNo", exprApplNo);
            result.put("exprApplNo", exprApplNo);

            // 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
            //			resultInt += esecurityHsRepository.offLimitsExprHistoryInsert(paramMap);

        }
        else if ("SI".equals(siteType)) {

            // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
            exprApplNo = esecuritySiRepository.offLimitsIoPassExprHistSeq(paramMap);

            paramMap.put("exprApplNo", exprApplNo);
            result.put("exprApplNo", exprApplNo);

            // 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
            resultInt += esecuritySiRepository.offLimitsExprHistoryInsert(paramMap);
        }
        else {
            // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
            exprApplNo = repository.offLimitsIoPassExprHistSeq(paramMap);

            paramMap.put("exprApplNo", exprApplNo);
            result.put("exprApplNo", exprApplNo);

            // 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
            resultInt += repository.offLimitsExprHistoryInsert(paramMap);
        }
        return exprApplNo;
    }

    // 이전 출입증 여부체크 - passMst2Check (Select VW_IO_PASS_MST2)
    public Map<String, Object> dmPassMst2Check(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = null;
        try {
            String siteType = StringUtils.defaultIfEmpty((String) paramMap.get("siteType"), "");
            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				resultMap = esecurityHsRepository.passMst2Check(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                resultMap = esecuritySiRepository.passMst2Check(paramMap);
            }
            else {
                resultMap = repository.passMst2Check(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    // 방문객 관리(제한관리) > 날짜변경 - offLimitsDenyDateMultiUpdate
    @Override
    public int offLimitsDenyDateMultiUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String rDenyEndDt = ObjectUtils.isEmpty(paramMap.get("denyEndDt"))
                ? ""
                : String.valueOf(paramMap.get("denyEndDt")); // 제한기간(종료일)
            String rDenyRsn = ObjectUtils.isEmpty(paramMap.get("denyRsn"))
                ? ""
                : String.valueOf(paramMap.get("denyRsn")); // 제한사유
            String rModBy = ObjectUtils.isEmpty(paramMap.get("modBy"))
                ? ""
                : String.valueOf(paramMap.get("modBy"));

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
			/*
			UPDATE IO_EMP_DENY
			   SET DEL_YN = 'Y'
			     , DENY_END_DT = #{denyEndDt}
			     , DENY_RSN = #{denyRsn}
			     , MOD_BY = #{modBy}
			     , MOD_DTM = SYSDATE
			 WHERE IO_EMP_ID = #{ioEmpId}
			   AND DENY_NO = #{denyNo}
			 */

            // Loop Start
            for (Map<String, Object> objItem : arrItems) {

                //System.out.println("## offLimitsDenyDateMultiUpdate paramMap:" + paramMap.toString());
                //System.out.println("## offLimitsDenyDateMultiUpdate objItem:" + objItem.toString());

                Map<String, Object> objMap = new HashMap<String, Object>();

                String rIoEmpId = ObjectUtils.isEmpty(objItem.get("ioEmpId"))
                    ? ""
                    : String.valueOf(objItem.get("ioEmpId"));
                String rDenyNo = ObjectUtils.isEmpty(objItem.get("denyNo"))
                    ? ""
                    : String.valueOf(objItem.get("denyNo"));
                String rHsDenyNo = ObjectUtils.isEmpty(objItem.get("hsDenyNo"))
                    ? ""
                    : String.valueOf(objItem.get("hsDenyNo"));
                String rSiDenyNo = ObjectUtils.isEmpty(objItem.get("siDenyNo"))
                    ? ""
                    : String.valueOf(objItem.get("siDenyNo"));

                objMap.put("ioEmpId", rIoEmpId); // io_emp_id
                objMap.put("denyEndDt", rDenyEndDt); // 제한기간(종료일)
                objMap.put("denyRsn", rDenyRsn); // 제한사유
                objMap.put("modBy", rModBy); // 수정자ID

                /* 본사DB - HN ********************************************** */
                if (!"".equals(rDenyNo)) {
                    objMap.put("denyNo", rDenyNo); // 순번
                    // 출입날짜 변경 - UPDATE IO_EMP_DENY
                    updatedRows += repository.offLimitsDenyDateUpdate(objMap);
                }

                /* 외부DB - HS ********************************************** */
                if (!"".equals(rHsDenyNo)) {
                    objMap.put("denyNo", rHsDenyNo); // 순번
                    // 출입날짜 변경 - UPDATE IO_EMP_DENY
                    //					updatedRows += esecurityHsRepository.offLimitsDenyDateUpdate(objMap);
                }

                /* 외부DB - SI ********************************************** */
                if (!"".equals(rSiDenyNo)) {
                    objMap.put("denyNo", rSiDenyNo); // 순번
                    // 출입날짜 변경 - UPDATE IO_EMP_DENY
                    updatedRows += esecuritySiRepository.offLimitsDenyDateUpdate(objMap);
                }
            }// End Loop
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 방문객 관리(제한관리) > 가입승인/전산탈퇴(delYn: N.가입승인, Y.전산탈퇴) - changeUserStatus
    @Override
    public int changeUserStatus(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
			  
			/*
			 UPDATE IO_EMP 
			   SET DEL_YN = #{delYn},
			       MOD_BY = #{modBy},
			       MOD_DTM = SYSDATE
		     WHERE IO_EMP_ID = #{ioEmpId}
			 */
            /* 본사DB - HN ********************************************** */
            // UPDATE IO_EMP (delYn: N.가입승인, Y.전산탈퇴)
            updatedRows = repository.changeUserStatus(paramMap);

            /* 외부DB - HS ********************************************** */
            // UPDATE IO_EMP (delYn: N.가입승인, Y.전산탈퇴)
            //			updatedRows += esecurityHsRepository.changeUserStatus(paramMap);

            /* 외부DB - SI ********************************************** */
            // UPDATE IO_EMP (delYn: N.가입승인, Y.전산탈퇴)
            updatedRows += esecuritySiRepository.changeUserStatus(paramMap);

            if (updatedRows > 0) {
                // delYn: N.가입승인, Y.전산탈퇴
                String rDelYn = ObjectUtils.isEmpty(paramMap.get("delYn"))
                    ? ""
                    : String.valueOf(paramMap.get("delYn"));

                if ("N".equals(rDelYn)) {
                    /* N.가입승인 ********************************************* */
                    String emailAddr = ObjectUtils.isEmpty(paramMap.get("emailAddr"))
                        ? ""
                        : String.valueOf(paramMap.get("emailAddr"));
                    String denyRsn = ObjectUtils.isEmpty(paramMap.get("denyRsn"))
                        ? ""
                        : String.valueOf(paramMap.get("denyRsn"));

                    Map<String, Object> mailParam = new HashMap<String, Object>();
                    mailParam.put("email", emailAddr); // 메일주소
                    mailParam.put("delYn", "N"); // N.가입승인, Y.전산탈퇴
                    mailParam.put("msg", denyRsn); // 사유

                    /* 가입승인 메일발송 */
                    ApprovalEnterMailSend(mailParam);
                }
                else if ("Y".equals(rDelYn)) {
                    /* Y.전산탈퇴 ********************************************* */
					
					/*
					 * /* 방문객 관리(제한관리) > 출입증 Count  - ioInOutCount
							<![CDATA[
							SELECT COUNT(IO.INOUT_APPL_NO) AS INOUT_CNT 
							  FROM IO_INOUTWRITE IO
							 WHERE IO.IO_EMP_ID = #{ioEmpId} 
							   AND IO.IO_COMP_ID = #{ioCompId} 
							   AND IO.FINISHKND <> 1
							]]>
					 */

                    String ioEmpId = ObjectUtils.isEmpty(paramMap.get("ioEmpId"))
                        ? ""
                        : String.valueOf(paramMap.get("ioEmpId"));
                    String ioCompId = ObjectUtils.isEmpty(paramMap.get("ioCompId"))
                        ? ""
                        : String.valueOf(paramMap.get("ioCompId"));

                    String[] siteList = { "HN", "HS", "SI" };
                    for (int i = 0; i < siteList.length; i++) {

                        String siteType = siteList[i];
                        Map<String, Object> inOutParam = new HashMap<String, Object>();
                        inOutParam.put("siteType", siteType);
                        inOutParam.put("ioEmpId", ioEmpId);
                        inOutParam.put("ioCompId", ioCompId);

                        // 이천 출입증이 있는지 확인 후 있으면
                        String ioInOutCnt = IoInOutCount(inOutParam);

                        if (!"0".equals(ioInOutCnt)) {

                            String admEmail = ObjectUtils.isEmpty(paramMap.get("admEmail"))
                                ? ""
                                : String.valueOf(paramMap.get("admEmail"));

                            Map<String, Object> mailParam = new HashMap<String, Object>();
                            mailParam.put("siteType", siteType);
                            mailParam.put("ioEmpId", ioEmpId);
                            mailParam.put("ioCompId", ioCompId);
                            mailParam.put("email", admEmail); // 메일주소

                            /* 업체물품 대표관리자 이관 메일발송 */
                            IoEmpExistIoinoutChgMailSend(mailParam);

                            /* 신청자ID -> 대표관리자ID로 변경 (UPDATE IO_INOUTWRITE) */
                            int updatedCnt = InIoEmpInfoIoInOutChg(inOutParam);
                        }  // End if
                    } // End For

                    String emailAddr = ObjectUtils.isEmpty(paramMap.get("emailAddr"))
                        ? ""
                        : String.valueOf(paramMap.get("emailAddr"));
                    Map<String, Object> mailParam = new HashMap<String, Object>();
                    mailParam.put("email", emailAddr); // 메일주소
                    mailParam.put("delYn", "Y"); // N.가입승인, Y.전산탈퇴
                    mailParam.put("msg", "회원님의 가입 정보가 탈퇴처리 되었습니다."); // message

                    /* 전산탈퇴 메일발송 */
                    ApprovalEnterMailSend(mailParam);
                } // End if
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 방문객 관리(제한관리) > 업체물품 반인신청 List - inIoEmpExistIoinoutChgItemList
    public List<Map<String, Object>> InIoEmpExistIoinoutChgItemList(Map<String, Object> paramMap) {

        List<Map<String, Object>> retItemList = null;

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				retItemList = esecurityHsRepository.inIoEmpExistIoinoutChgItemList(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                retItemList = esecuritySiRepository.inIoEmpExistIoinoutChgItemList(paramMap);
            }
            else {
                retItemList = repository.inIoEmpExistIoinoutChgItemList(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return retItemList;
    }

    // 방문객 관리(제한관리) > 출입증 Count  - ioInOutCount
    public String IoInOutCount(Map<String, Object> paramMap) {

        String retInoutCnt = "0";

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				retInoutCnt = esecurityHsRepository.ioInOutCount(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                retInoutCnt = esecuritySiRepository.ioInOutCount(paramMap);
            }
            else {
                retInoutCnt = repository.ioInOutCount(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return retInoutCnt;
    }

    // 방문객 관리(제한관리) > 신청자ID -> 대표관리자ID로 변경
    // UPDATE IO_INOUTWRITE
    public int InIoEmpInfoIoInOutChg(Map<String, Object> paramMap) {

        int result = 0;

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				result = esecurityHsRepository.inIoEmpInfoIoInOutChg(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                result = esecuritySiRepository.inIoEmpInfoIoInOutChg(paramMap);
            }
            else {
                result = repository.inIoEmpInfoIoInOutChg(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    // 방문객 관리(제한관리) > 회원탈퇴  - holdUserDelete
    @Override
    public int holdUserDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // 회원탈퇴 처리 - DELETE FROM IO_EMP
            updatedRows = repository.holdUserDelete(paramMap);

            if (updatedRows > 0) {

                String emailAddr = ObjectUtils.isEmpty(paramMap.get("emailAddr"))
                    ? ""
                    : String.valueOf(paramMap.get("emailAddr"));
                String denyRsn = ObjectUtils.isEmpty(paramMap.get("denyRsn"))
                    ? ""
                    : String.valueOf(paramMap.get("denyRsn"));

                Map<String, Object> mailParam = new HashMap<String, Object>();
                mailParam.put("email", emailAddr); // 메일주소
                mailParam.put("delYn", "Y"); // N.가입승인, Y.전산탈퇴
                mailParam.put("msg", denyRsn); // 사유
                /* 회원탈퇴 메일발송 */
                ApprovalEnterMailSend(mailParam);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 방문객 관리(제한관리) >  상시출입증 강제만료 -  offLimitsReceiptMngExpire
    @Override
    public int offLimitsReceiptMngExpire(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String rDenyCtgCd = ObjectUtils.isEmpty(paramMap.get("denyCtgCd"))
                ? ""
                : String.valueOf(paramMap.get("denyCtgCd")); // 제한구분코드
            String rAcIp = ObjectUtils.isEmpty(paramMap.get("acIp"))
                ? ""
                : String.valueOf(paramMap.get("acIp"));

            /* 출입증번호로 강제만료하기 처리 : 2015-11-03 by JSH */
            // 카드정보 Select  - offLimitsReceiptMngExpireInfoSelect
            Map<String, Object> retMap = repository.offLimitsReceiptMngExpireInfoSelect(paramMap);

            if (retMap != null && ObjectUtils.isNotEmpty(retMap)) {

                String idcardId = ObjectUtils.isEmpty(retMap.get("idcardId"))
                    ? ""
                    : String.valueOf(retMap.get("idcardId"));

                if (!"".equals(idcardId)) {
                    paramMap.put("sCGbn", "S");
                    paramMap.put("denyRsn", "방문객관리(제한관리)_상시출입증 강제만료");
                    paramMap.put("isPass", "Y");

                    // IO_PASS 테이블에 강제만료 필드 Update 처리함
                    // dU_OffLimits.dmOffLimitsReceiptMngExpireUpdate(requestData, onlineCtx);
					/*
					UPDATE IO_PASS
					SET EXPR_YN        = 'Y'
					    ,EXPR_EMP_ID   = #{crtBy}
					    ,EXPR_DT       = TO_CHAR(SYSDATE, 'yyyyMMdd')
					    ,EXPR_RSN      = #{denyRsn}
					WHERE PASS_APPL_NO = #{passApplNo}
					*/
                    updatedRows = repository.offLimitsReceiptMngExpireUpdate(paramMap);

                    paramMap.put("exprGbn", rDenyCtgCd);
                    // Step_1) 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
                    // Step_2) 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
                    String exprApplNo = dmOffLimitsExprHistoryInsert(paramMap);

                    // 운영일 경우
                    if (environment.acceptsProfiles(Profiles.of("prd"))) {

                        Map<String, Object> procMap = new HashMap<String, Object>();
                        procMap.put("exprApplNo", exprApplNo); //  출입 외부 제한 이력 Sequence
                        procMap.put("sCGbn", "S"); // 정지:S, 해제:C
                        procMap.put("idcardId", idcardId); // idCardId
                        procMap.put("exprCode", rDenyCtgCd); // 제한구분코드
                        procMap.put("siteType", ""); // 사이트구분
			    		
						/* 환경설정 > 방문객 관리(제한관리) > 출입제한  - procedureOffLimitsIoPassExprHist (ASIS : dmOffLimits_IoPassExprHist)
						exec [dbo].[uSP_SK_IF_IO_PASS_EXPR_HIST] 
								  @EXPR_APPL_NO = #{exprApplNo} 
								, @S_C_GBN = #{sCGbn}
								, @IDCARD_ID = #{idcardId}
								, @EXPR_CODE = #{exprCode}
								<if test='"SI".equals(siteType)'>
								, @PTYPE='8'
								</if>
						 */
                        // Step_3) 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                        idcardVisitRepository.procedureOffLimitsIoPassExprHist(procMap);
                    }

                    if (updatedRows > 0) {

                        String emailAddr = ObjectUtils.isEmpty(retMap.get("emailAddr"))
                            ? ""
                            : String.valueOf(retMap.get("emailAddr"));
                        String empNm = ObjectUtils.isEmpty(retMap.get("empNm"))
                            ? ""
                            : String.valueOf(retMap.get("empNm"));

                        Map<String, Object> mailParam = new HashMap<String, Object>();
                        mailParam.put("email", emailAddr); // 메일주소
                        mailParam.put("toName", empNm); // 이름
                        mailParam.put("acIp", rAcIp); // ip

                        /* 상시출입증 강제만료 메일발송 */
                        PassExpireMailSend(mailParam);
                    }
                } // End if(!"".equals(idcardId)) {
            } // End if (retMap != null
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 방문객 관리(제한관리) > 전산탈퇴 > 정보 Select - ioEmpAdminAuhSelect
    @Override
    public Map<String, Object> ioEmpAdminAuhSelect(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = new HashMap<String, Object>();

            String isTargetMultiple = ObjectUtils.isEmpty(paramMap.get("isTargetMultiple"))
                ? ""
                : String.valueOf(paramMap.get("isTargetMultiple"));
            String ioEmpId = ObjectUtils.isEmpty(paramMap.get("ioEmpId"))
                ? ""
                : String.valueOf(paramMap.get("ioEmpId"));
            String ioCompId = ObjectUtils.isEmpty(paramMap.get("ioCompId"))
                ? ""
                : String.valueOf(paramMap.get("ioCompId"));

            int violationCnt = 0, gateInCnt = 0, ioCnt = 0;

            // 권한정보 Select
            Map<String, Object> retMap = repository.ioEmpAdminAuhSelect(paramMap);
            if (retMap != null && ObjectUtils.isNotEmpty(retMap)) {
                String compAuth = ObjectUtils.isEmpty(retMap.get("compAuth"))
                    ? ""
                    : String.valueOf(retMap.get("compAuth"));
                String compAuthYn = ObjectUtils.isEmpty(retMap.get("compAuthYn"))
                    ? ""
                    : String.valueOf(retMap.get("compAuthYn"));
                String tempCompAuth = ObjectUtils.isEmpty(retMap.get("tempCompAuth"))
                    ? ""
                    : String.valueOf(retMap.get("tempCompAuth"));
                String apprYn = ObjectUtils.isEmpty(retMap.get("apprYn"))
                    ? ""
                    : String.valueOf(retMap.get("apprYn")); //
                String admEmail = ObjectUtils.isEmpty(retMap.get("admEmail"))
                    ? ""
                    : String.valueOf(retMap.get("admEmail"));

                /**
                 * 3사 조회 항목.
                 * 위규 사항, 반입 물품 상황, 게이트 내부 여부.
                 */
                String[] siteTypeList = { "HN", "HS", "SI" };
                for (String siteType : siteTypeList) {

                    Map<String, Object> cntParamMap = new HashMap<String, Object>();
                    cntParamMap.put("siteType", siteType);
                    cntParamMap.put("ioEmpId", ioEmpId);
                    cntParamMap.put("ioCompId", ioCompId);

                    // 탈퇴 전 보안위규 건수 중 종결되지 않는 건이 있는지 체크한다.
                    // 방문객 관리(제한관리) > 전산탈퇴 > violationCount - ioEmpViolationCount
                    violationCnt += dmIoEmpViolationCount(cntParamMap);

                    // 탈퇴 전 입문 상태인지 체크한다. 출문하기 전엔 탈퇴를 하지 못하게 한다.
                    // 방문객 관리(제한관리) > 전산탈퇴 > gateInCount - ioEmpGateOutCheck
                    gateInCnt += dmIoEmpGateOutCheck(cntParamMap);

                    // 탈퇴 전 3사 반입물품 현황 여부 조회.
                    // 방문객 관리(제한관리) > 전산탈퇴 > ioCount - ioEmpInOutCount
                    ioCnt += dmIoInOutCount(cntParamMap);
                } // End for

                Map<String, Object> passParamMap = new HashMap<String, Object>();
                passParamMap.put("ioEmpId", ioEmpId);
                // 방문객 관리(제한관리) > 전산탈퇴 > constPassCount
                int contPassCnt = repository.constPassCount(passParamMap);

                // Return Map Setting
                resultView.put("compAuth", compAuth);
                resultView.put("compAuthYn", compAuthYn);
                resultView.put("tempCompAuth", tempCompAuth);
                resultView.put("apprYn", apprYn);
                resultView.put("admEmail", admEmail);
                resultView.put("violationCnt", violationCnt);
                resultView.put("gateInCnt", gateInCnt);
                resultView.put("ioCnt", ioCnt);
                resultView.put("ioinoutYn", ioCnt > 1
                    ? "Y"
                    : "N");
                resultView.put("contPassCnt", contPassCnt);
            } // End if (retMap != null
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 방문객 관리(제한관리) > 전산탈퇴 > violationCount - ioEmpViolationCount
    public int dmIoEmpViolationCount(Map<String, Object> paramMap) {

        int result = 0;

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				result = esecurityHsRepository.ioEmpViolationCount(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                result = esecuritySiRepository.ioEmpViolationCount(paramMap);
            }
            else {
                result = repository.ioEmpViolationCount(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    // 방문객 관리(제한관리) > 전산탈퇴 > gateInCount - ioEmpGateOutCheck
    public int dmIoEmpGateOutCheck(Map<String, Object> paramMap) {

        int result = 0;

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				result = esecurityHsRepository.ioEmpGateOutCheck(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                result = esecuritySiRepository.ioEmpGateOutCheck(paramMap);
            }
            else {
                result = repository.ioEmpGateOutCheck(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    // 방문객 관리(제한관리) > 전산탈퇴 > ioCount - ioEmpInOutCount
    public int dmIoInOutCount(Map<String, Object> paramMap) {

        int result = 0;

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				result = esecurityHsRepository.ioEmpInOutCount(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                result = esecuritySiRepository.ioEmpInOutCount(paramMap);
            }
            else {
                result = repository.ioEmpInOutCount(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    // 방문객 관리(제한관리) > 임시비밀번호발송 - tempPasswordSend
    @Override
    public int tempPasswordSend(Map<String, Object> paramMap) {

        int updatedRows = 0;

        try {

            String searchType = ObjectUtils.isEmpty(paramMap.get("searchType"))
                ? ""
                : String.valueOf(paramMap.get("searchType"));
            String hpNo = ObjectUtils.isEmpty(paramMap.get("hpNo"))
                ? ""
                : String.valueOf(paramMap.get("hpNo"));
            String ioEmpId = ObjectUtils.isEmpty(paramMap.get("ioEmpId"))
                ? ""
                : String.valueOf(paramMap.get("ioEmpId"));

            // 사용자정보 Select - ioEmpFindInfo
            Map<String, Object> retInfo = repository.ioEmpFindInfo(paramMap);

            if (retInfo != null && ObjectUtils.isNotEmpty(retInfo)) {

                // 난수 생성 이후 메일 발송
                String randomStr = "";

                for (int i = 1; i <= 3; i++) {
                    char ch = (char) ((Math.random() * 26) + 65);
                    randomStr += ch;
                }

                for (int i = 1; i <= 4; i++) {
                    int n = (int) (Math.random() * 10) + 1;
                    randomStr += n;
                }

                // 소문자 a-z 랜덤 알파벳 생성
                for (int i = 1; i <= 3; i++) {
                    char ch = (char) ((Math.random() * 26) + 97);
                    randomStr += ch;
                }

                // Set temp password
                paramMap.put("nPassword", randomStr);

                //  임시비밀번호 Update
                /* 외부DB - HS */
                //				updatedRows += esecurityHsRepository.ioEmpPasswordUpdate(paramMap);
                /* 외부DB - SI */
                updatedRows += esecuritySiRepository.ioEmpPasswordUpdate(paramMap);
                /* 본사DB - HN */
                updatedRows = repository.ioEmpPasswordUpdate(paramMap);

                /* @TODO - kakao send */
				/*
				IDataSet kakaoReq = new DataSet();
	    		kakaoReq.putField("HP_NO", 	   	   hpNo.replaceAll("-", ""));
	    		kakaoReq.putField("KAKAO_TITLE",   "행복한만남 임시비밀번호");
	    		kakaoReq.putField("KAKAO_MSG",     "임시 비밀번호입니다. ["+randomStr+"] -SK hynix-");
	    		kakaoReq.putField("SMS_MSG",       "임시 비밀번호입니다. ["+randomStr+"] -SK hynix-");
	    		kakaoReq.putField("TEMPLATE_CODE", "SJT_048091");
	    		kakaoReq.putField("CALLBACK_TEL",  "03151854114");
	    		kakaoReq.putField("K_ATTACH",      "");
	    		kakaoReq.putField("CRT_BY",    	   requestData.getField("IO_EMP_ID"));
	    		callSharedBizComponentByDirect("common.SendKakao", "fmSendKakao", kakaoReq, onlineCtx);
	    		*/
                // ================= NOTE: SMS(kakao) 발송 시작 =======================
                // 주석처리 2023-06-09
                //				if (!"".equals(hpNo)) {
                //					String message = "임시 비밀번호입니다. ["+randomStr+"] -SK hynix-";
                //
                //					// TODO - 운영계 이행시 주석처리 - 수신번호(chabeomchul)
                //					// hpNo = "01031658087";
                //
                //					RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                //					KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                //					kakaoMessageDTO.setKTemplateCode("SJT_048091");
                //					kakaoMessageDTO.setSubject("행복한만남 임시비밀번호");
                //					kakaoMessageDTO.setDstaddr(hpNo.replaceAll("-", ""));
                //					kakaoMessageDTO.setCallback("03151854114");
                //					kakaoMessageDTO.setText(message);
                //					kakaoMessageDTO.setText2(message);
                //					kakaoMessageDTO.setKAttach("");
                //					kakaoMessageDTO.setEmpId(ioEmpId);
                //
                //					wrapParams.setParams(kakaoMessageDTO);
                //					commonApiClient.sendKakaoMessage(wrapParams);
                //				}
                // ================= NOTE: SMS(kakao) 발송 종료 =======================
            } // End if (retInfo != null
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 방문객 관리(제한관리) > 출입정지 - passReqStop
    @Override
    public Map<String, Object> passReqStop(Map<String, Object> paramMap) {

        Map<String, Object> resultView = null;
        int updatedRows = 0;

        try {
            resultView = new HashMap<String, Object>();

            // 방문객 관리(제한관리) > 출입정지 Count - passReqStopCnt
            int stopCnt = repository.passReqStopCnt(paramMap);

            if (stopCnt > 0) {
                resultView.put("alreadyStop", "Y");
                resultView.put("result", "FAIL");
            }
            else {
                resultView.put("alreadyStop", "N");
                //System.out.println("passReqStop paramMap:" + paramMap.toString());
                //방문객 관리(제한관리) > 출입정지 Insert - passReqStop
                updatedRows = repository.passReqStop(paramMap);

                if (updatedRows > 0) {
                    resultView.put("result", "OK");
                }
                else {
                    resultView.put("result", "FAIL");
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultView;
    }

    // 방문객 관리(제한관리) > 출입제한 해제(다건)   - offLimitsDenyMultiDelete
    @Override
    public int offLimitsDenyMultiDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String rDenyStrtDt = ObjectUtils.isEmpty(paramMap.get("denyStrtDt"))
                ? ""
                : String.valueOf(paramMap.get("denyStrtDt")); // 제한기간(시작일)
            String rDenyEndDt = ObjectUtils.isEmpty(paramMap.get("denyEndDt"))
                ? ""
                : String.valueOf(paramMap.get("denyEndDt")); // 제한기간(종료일)
            String rDenyRsn = ObjectUtils.isEmpty(paramMap.get("denyRsn"))
                ? ""
                : String.valueOf(paramMap.get("denyRsn")); // 제한사유
            String rDenyCode = ObjectUtils.isEmpty(paramMap.get("denyCode"))
                ? ""
                : String.valueOf(paramMap.get("denyCode")); // 제한사유
            String rModBy = ObjectUtils.isEmpty(paramMap.get("modBy"))
                ? ""
                : String.valueOf(paramMap.get("modBy"));
            String rCrtBy = ObjectUtils.isEmpty(paramMap.get("crtBy"))
                ? ""
                : String.valueOf(paramMap.get("crtBy"));
            String rAcIp = ObjectUtils.isEmpty(paramMap.get("acIp"))
                ? ""
                : String.valueOf(paramMap.get("acIp"));

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
            // Loop Start
            for (Map<String, Object> objItem : arrItems) {

                //Map<String, Object> objMap = new HashMap<String, Object>();

                //				String rIoEmpId = ObjectUtils.isEmpty(objItem.get("ioEmpId")) ? "" : String.valueOf(objItem.get("ioEmpId"));
                //				String ioCompId = ObjectUtils.isEmpty(objItem.get("ioCompId")) ? "" : String.valueOf(objItem.get("ioCompId"));
                String rDenyNo = ObjectUtils.isEmpty(objItem.get("denyNo"))
                    ? ""
                    : String.valueOf(objItem.get("denyNo"));
                String rHsDenyNo = ObjectUtils.isEmpty(objItem.get("hsDenyNo"))
                    ? ""
                    : String.valueOf(objItem.get("hsDenyNo"));
                String rSiDenyNo = ObjectUtils.isEmpty(objItem.get("siDenyNo"))
                    ? ""
                    : String.valueOf(objItem.get("siDenyNo"));
                String rIdcardId = ObjectUtils.isEmpty(objItem.get("idcardId"))
                    ? ""
                    : String.valueOf(objItem.get("idcardId"));
                //				String rCardNo  = ObjectUtils.isEmpty(objItem.get("cardNo")) ? "" : String.valueOf(objItem.get("cardNo"));

                //				objMap.put("ioEmpId", rIoEmpId); // io_emp_id
                //				objMap.put("ioCompId", ioCompId); // io_emp_id
                objItem.put("denyRsn", rDenyRsn); // 제한사유
                objItem.put("modBy", rModBy); // 수정자ID

                //System.out.println("## offLimitsDenyMultiDelete objItem1:" + objItem.toString());

                // 방문객 관리(제한관리) > 출입제한 해제  Update - offLimitsHistoryUpdate
				/*
				 UPDATE IO_EMP_DENY
		   SET DEL_YN = 'Y'
		     , MOD_BY = #{modBy}
		     , MOD_DTM = SYSDATE
		     , DENY_RSN = #{denyRsn}
		 WHERE IO_EMP_ID = #{ioEmpId}
		   AND DENY_NO = #{denyNo}
				 */
                /* 본사DB - HN ********************************************** */
                if (!"".equals(rDenyNo)) {
                    objItem.put("denyNo", rDenyNo); // 순번
                    //  - UPDATE IO_EMP_DENY
                    updatedRows += repository.offLimitsHistoryUpdate(objItem);
                }
                /* 외부DB - HS ********************************************** */
                if (!"".equals(rHsDenyNo)) {
                    objItem.put("denyNo", rHsDenyNo); // 순번
                    // 출입날짜 변경 - UPDATE IO_EMP_DENY
                    //					updatedRows += esecurityHsRepository.offLimitsHistoryUpdate(objItem);
                }
                /* 외부DB - SI ********************************************** */
                if (!"".equals(rSiDenyNo)) {
                    objItem.put("denyNo", rSiDenyNo); // 순번
                    // 출입날짜 변경 - UPDATE IO_EMP_DENY
                    updatedRows += esecuritySiRepository.offLimitsHistoryUpdate(objItem);
                }

                if (!"".equals(rIdcardId)) {

                    // 출입제한 상시출입증 정지 이력 등록 - dmOffLimitsExprHistoryInsert
                    // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
                    String exprApplNo = repository.offLimitsIoPassExprHistSeq(objItem);

                    objItem.put("exprApplNo", exprApplNo);
                    objItem.put("exprGbn", rDenyCode);
                    objItem.put("smartIdcard", rIdcardId);
                    objItem.put("sCGbn", "C"); // 정지 해제
                    objItem.put("crtBy", rCrtBy);
                    objItem.put("acIp", rAcIp);
                    objItem.put("denyStrtDt", rDenyStrtDt);
                    objItem.put("denyEntDt", rDenyEndDt);
                    objItem.put("denyRsn", rDenyRsn);

                    //System.out.println("## offLimitsDenyMultiDelete objItem2:" + objItem.toString());
                    // 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
                    /* 방문객 관리(제한관리) > 출입 외부 제한 이력 등록  - offLimitsExprHistoryInsert */
                    updatedRows += repository.offLimitsExprHistoryInsert(objItem);

                    /*2021-03-08 채수억 출입해제 종료일이 오늘보다 적으면 무조건 인터페이스 태운다.*/
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String sf = format.format(new Date());
                    if (format.parse(rDenyEndDt).before(format.parse(format.format(new Date())))) {
                        if ("A0460015".equals(rDenyCode)) {
                            if (environment.acceptsProfiles(Profiles.of("prd"))) {
                                Map<String, Object> procMap = new HashMap<String, Object>();
                                procMap.put("exprApplNo", exprApplNo); //  출입 외부 제한 이력 Sequence
                                procMap.put("sCGbn", "C"); // 정지:S, 해제:C
                                procMap.put("idcardId", rIdcardId); // idCardId
                                procMap.put("exprCode", rDenyCode); // 제한구분코드
                                //procMap.put("siteType", siteType); // 사이트구분
                                //System.out.println("## offLimitsDenyMultiDelete procMap:" + procMap.toString());
					    		
								/* 환경설정 > 방문객 관리(제한관리) > 출입제한  - procedureOffLimitsIoPassExprHist (ASIS : dmOffLimits_IoPassExprHist)
								exec [dbo].[uSP_SK_IF_IO_PASS_EXPR_HIST] 
										  @EXPR_APPL_NO = #{exprApplNo} 
										, @S_C_GBN = #{sCGbn}
										, @IDCARD_ID = #{idcardId}
										, @EXPR_CODE = #{exprCode}
										<if test='"SI".equals(siteType)'>
										, @PTYPE='8'
										</if>
								 */
                                // Step_3) 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                                idcardVisitRepository.procedureOffLimitsIoPassExprHist(procMap);
                            }
                        }
                    }
                } // End if (!"".equals(rIdcardId))
            } // End for
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 방문객 관리(제한관리) > 출입제한이력 해제   - offLimitsDelete
    @Override
    public int offLimitsDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String rDenyCtgCd = ObjectUtils.isEmpty(paramMap.get("denyCtgCd"))
                ? ""
                : String.valueOf(paramMap.get("denyCtgCd")); // 제한구분코드

            String[] siteTypeList = { "HN", "HS", "SI" };
            for (String siteType : siteTypeList) {

                paramMap.put("siteType", siteType);

                // 방문객 관리(제한관리) > 출입제한이력 해제  Update - offLimitsHistoryUpdate
                updatedRows = dmOffLimitsHistoryUpdate(paramMap);

                // 방문객 관리(제한관리) > 출입제한이력 카드ID - offLimitsGetCardNo
                String idCardNo = dmOffLimitsGetCardNo(paramMap);

                /** 2021-02-02 채수억 : 출입관리 > 방문객관리(제한관리) > 상세페이지 > 코로나 해지시 IDCARD와 인터페이스 하도록 추가  **/
                if ("A0460015".equals(rDenyCtgCd) && StringUtils.isNotEmpty(idCardNo)) {

                    // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
                    String exprApplNo = dmOffLimitsIoPassExprHistSeq(paramMap);
                    //---------- 하이스텍 정지 하는 I/F Procedure 호출해야 함 ----------//
					/*
	    			 if(operate.equals("R")) {	 //20200922
	    				 requestData.putField("EXPR_APPL_NO", resDs.getField("EXPR_APPL_NO"));
	    				 requestData.putField("S_C_GBN", "C"); // 정지	해제
	    				 requestData.putField("DENY_RSN",  "상세화면에서 코로나 19 출입제한 해제");
	    				 requestData.putField("IDCARD_ID", idCardNo);
	    				 requestData.putField("EXPR_CODE", "A0750015");
	    				 requestData.putField("SITE_TYPE", siteType);
	    				 dU_OffLimits.dmOffLimits_IoPassExprHist(requestData, onlineCtx);
				     }
				     */
                    if (environment.acceptsProfiles(Profiles.of("prd"))) {
                        Map<String, Object> procMap = new HashMap<String, Object>();
                        procMap.put("exprApplNo", exprApplNo); //  출입 외부 제한 이력 Sequence
                        procMap.put("sCGbn", "C"); // 정지:S, 해제:C
                        procMap.put("idcardId", idCardNo); // idCardId
                        procMap.put("exprCode", "A0750015"); // 제한구분코드
                        procMap.put("siteType", siteType); // 사이트구분
			    		
						/* 환경설정 > 방문객 관리(제한관리) > 출입제한  - procedureOffLimitsIoPassExprHist (ASIS : dmOffLimits_IoPassExprHist)
						exec [dbo].[uSP_SK_IF_IO_PASS_EXPR_HIST] 
								  @EXPR_APPL_NO = #{exprApplNo} 
								, @S_C_GBN = #{sCGbn}
								, @IDCARD_ID = #{idcardId}
								, @EXPR_CODE = #{exprCode}
								<if test='"SI".equals(siteType)'>
								, @PTYPE='8'
								</if>
						 */
                        // Step_3) 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                        idcardVisitRepository.procedureOffLimitsIoPassExprHist(procMap);
                    }

                    //---------- 하이스텍 정지 하는 I/F Procedure 호출해야 함 ----------//

                } // End if("A0460015".equals(rDenyCtgCd)
            } // End for
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
    public String dmOffLimitsIoPassExprHistSeq(Map<String, Object> paramMap) {

        String retExprApplNo = "";

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				retExprApplNo = esecurityHsRepository.offLimitsIoPassExprHistSeq(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                retExprApplNo = esecuritySiRepository.offLimitsIoPassExprHistSeq(paramMap);
            }
            else {
                retExprApplNo = repository.offLimitsIoPassExprHistSeq(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return retExprApplNo;
    }

    // 방문객 관리(제한관리) > 출입제한이력 카드ID - offLimitsGetCardNo
    public String dmOffLimitsGetCardNo(Map<String, Object> paramMap) {

        String retIdCardNo = "";

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                //				retIdCardNo = esecurityHsRepository.offLimitsGetCardNo(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                retIdCardNo = esecuritySiRepository.offLimitsGetCardNo(paramMap);
            }
            else {
                retIdCardNo = repository.offLimitsGetCardNo(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return retIdCardNo;
    }

    // 방문객 관리(제한관리) > 출입제한이력 해제  Update - offLimitsHistoryUpdate
    public int dmOffLimitsHistoryUpdate(Map<String, Object> paramMap) {

        int result = 0;

        try {
            String siteType = ObjectUtils.isEmpty(paramMap.get("siteType"))
                ? ""
                : String.valueOf(paramMap.get("siteType"));
            String rDenyNo = ObjectUtils.isEmpty(paramMap.get("denyNo"))
                ? ""
                : String.valueOf(paramMap.get("denyNo")); // deny no
            String rHsDenyNo = ObjectUtils.isEmpty(paramMap.get("hsDenyNo"))
                ? ""
                : String.valueOf(paramMap.get("hsDenyNo")); // hs deny no
            String rSiDenyNo = ObjectUtils.isEmpty(paramMap.get("siDenyNo"))
                ? ""
                : String.valueOf(paramMap.get("siDenyNo")); // si deny no

            if ("HS".equals(siteType)) {
                /* 외부DB - HS */
                paramMap.put("denyNo", rHsDenyNo);
                //				result = esecurityHsRepository.offLimitsHistoryUpdate(paramMap);
            }
            else if ("SI".equals(siteType)) {
                /* 외부DB - SI */
                paramMap.put("denyNo", rSiDenyNo);
                result = esecuritySiRepository.offLimitsHistoryUpdate(paramMap);
            }
            else {
                paramMap.put("denyNo", rDenyNo);
                result = repository.offLimitsHistoryUpdate(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    // Mail Send :  방문객 관리(제한관리) > 상시출입증 강제만료 메일발송
    @SuppressWarnings("unchecked")
    private void PassExpireMailSend(Map<String, Object> paramMap) {

        try {

            String sendToEmail = StringUtils.defaultIfEmpty((String) paramMap.get("email"), "");
            String toName = StringUtils.defaultIfEmpty((String) paramMap.get("toName"), "");

            String title = "[행복한 만남, SK 하이닉스] 상시출입증이 정지되었습니다. 반납해주시기 바랍니다.";
            String message = "";
            message = toName + "님의 상시출입증이 최근 출입횟수가 부족 or 출입 목적이 종료되어 정지되었습니다<br>";
            message += "※ 보안서약서 내용 中<br>";
            message += "\"10. 기간이 만료된 경우 즉시 반납하겠습니다.\"라는 조항을 준수해주시기 바랍니다<br>";

            String empId = "SKhynix";
            String schemaNm = "PASS_APPL";
            String acIp = StringUtils.defaultIfBlank((String) paramMap.get("acIp"), "SYSTEM");
            // (String title, String content, String to, String empNo, String schemaNm, String docId, String acIp)
            // Call SendMail
            boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, message), sendToEmail, empId, schemaNm, "", acIp);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    // Mail Send :  방문객 관리(제한관리) > (delYn: N.가입승인, Y.전산탈퇴) 메일발송
    @SuppressWarnings("unchecked")
    private void ApprovalEnterMailSend(Map<String, Object> paramMap) {

        try {

            String rDelYn = StringUtils.defaultIfEmpty((String) paramMap.get("delYn"), "");
            String rMsg = StringUtils.defaultIfEmpty((String) paramMap.get("msg"), "");

            String title = "";
            // delYn: N.가입승인, Y.전산탈퇴
            if ("N".equals(rDelYn)) {
                title = "[행복한 만남, SK 하이닉스]회원가입이 승인되었습니다.";
            }
            else if ("Y".equals(rDelYn)) {
                title = "[행복한 만남, SK 하이닉스]회원탈퇴 처리가 완료 되었습니다.";
            }
            else {
                return;
            }

            String sendToEmail = StringUtils.defaultIfEmpty((String) paramMap.get("email"), "");
            String empId = "SKhynix";
            String schemaNm = "CHGUSRSTATUS";
            String acIp = StringUtils.defaultIfBlank((String) paramMap.get("acIp"), "SYSTEM");
            // (String title, String content, String to, String empNo, String schemaNm, String docId, String acIp)
            // Call SendMail
            boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, rMsg), sendToEmail, empId, schemaNm, "", acIp);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    // Mail Send :  방문객 관리(제한관리) > 전산탈퇴 메일발송
    @SuppressWarnings("unchecked")
    private void IoEmpExistIoinoutChgMailSend(Map<String, Object> paramMap) {

        try {

            // 업체물품 내역 테이블
            StringBuffer sbItemContents = new StringBuffer();
            sbItemContents.append("</br><font style='font-size:10pt;margin-top:9px'>");
            sbItemContents.append("아래 진행중인 업체물품의 신청자가 대표관리자로 변경 되었음을 알려드립니다.</ br></font>");
            sbItemContents.append("<table width='100%' cellpadding='0' cellspacing='0' summary='입력화면입니다.' border='1' >");
            sbItemContents.append("<tr>");
            sbItemContents.append("<th  width='15%'  bgcolor='#F2CB61' style='text-align: center;'>업체명</th>");
            sbItemContents.append("<th  width='10%'  bgcolor='#F2CB61' style='text-align: center;'>신청자명</th>");
            sbItemContents.append("<th  width='13%'  bgcolor='#F2CB61' style='text-align: center;'>반입증번호</th>");
            sbItemContents.append("<th  width='10%'  bgcolor='#F2CB61' style='text-align: center;'>물품번호</th>");
            sbItemContents.append("<th  width='10%'  bgcolor='#F2CB61' style='text-align: center;'>물품명</th>");
            sbItemContents.append("<th  width='10%'  bgcolor='#F2CB61' style='text-align: center;'>Serial번호</th>");
            sbItemContents.append("<th  width='10%'  bgcolor='#F2CB61' style='text-align: center;'>모델명</th>");
            sbItemContents.append("<th  width='7%'  bgcolor='#F2CB61' style='text-align: center;'>입고수량</th>");
            sbItemContents.append("<th  width='7%'  bgcolor='#F2CB61' style='text-align: center;'>출고수량</th>");
            sbItemContents.append("<th  width='7%'  bgcolor='#F2CB61' style='text-align: center;'>단위</th>");
            sbItemContents.append("</tr>");

            // 방문객 관리(제한관리) > 업체물품 반인신청 List - inIoEmpExistIoinoutChgItemList
            List<Map<String, Object>> arrItemList = InIoEmpExistIoinoutChgItemList(paramMap);

            if (arrItemList.size() > 0) {
                // Loop Start
                for (Map<String, Object> objItem : arrItemList) {

                    String ioCompNm = ObjectUtils.isEmpty(objItem.get("ioCompNm"))
                        ? ""
                        : String.valueOf(objItem.get("ioCompNm"));
                    String ioEmpNm = ObjectUtils.isEmpty(objItem.get("ioEmpNm"))
                        ? ""
                        : String.valueOf(objItem.get("ioEmpNm"));
                    String ioinoutserialno = ObjectUtils.isEmpty(objItem.get("ioinoutserialno"))
                        ? ""
                        : String.valueOf(objItem.get("ioinoutserialno"));
                    String itemSeq = ObjectUtils.isEmpty(objItem.get("itemSeq"))
                        ? ""
                        : String.valueOf(objItem.get("itemSeq"));
                    String itemNm = ObjectUtils.isEmpty(objItem.get("itemNm"))
                        ? ""
                        : String.valueOf(objItem.get("itemNm"));
                    String serialNo = ObjectUtils.isEmpty(objItem.get("serialNo"))
                        ? ""
                        : String.valueOf(objItem.get("serialNo"));
                    String modelNm = ObjectUtils.isEmpty(objItem.get("modelNm"))
                        ? ""
                        : String.valueOf(objItem.get("modelNm"));
                    String inQty = ObjectUtils.isEmpty(objItem.get("inQty"))
                        ? ""
                        : String.valueOf(objItem.get("inQty"));
                    String outQty = ObjectUtils.isEmpty(objItem.get("outQty"))
                        ? ""
                        : String.valueOf(objItem.get("outQty"));
                    String unitCd = ObjectUtils.isEmpty(objItem.get("unitCd"))
                        ? ""
                        : String.valueOf(objItem.get("unitCd"));

                    sbItemContents.append("<tr>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + ioCompNm + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + ioEmpNm + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + ioinoutserialno + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + itemSeq + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + itemNm + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + serialNo + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + modelNm + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + inQty + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + outQty + "</td>");
                    sbItemContents.append("<td style='text-align: center;font-size:10pt;'>" + unitCd + "</td>");
                    sbItemContents.append("</tr>");
                } // End for
            } // End if (arrItemList.size() > 0)
            sbItemContents.append("</table>");

            String title = "[행복한 만남, SK 하이닉스]회원탈퇴로 인한 진행중인 업체물품이 대표관리자에게 이관되었습니다.";
            String sendToEmail = StringUtils.defaultIfEmpty((String) paramMap.get("email"), "");
            String contents = sbItemContents.toString();

            String empId = "SKhynix";
            String schemaNm = "IOINOUT_CHG";
            String acIp = StringUtils.defaultIfBlank((String) paramMap.get("acIp"), "SYSTEM");
            // (String title, String content, String to, String empNo, String schemaNm, String docId, String acIp)
            // Call SendMail
            boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, contents), sendToEmail, empId, schemaNm, "", acIp);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

}
