package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "결재상신정보 (AP_APPR_DOC_PRE 테이블에 대응됨)")
public class ApprovalDTO extends CommonDTO {

    /* FOR AP_APPR_PORTAL_PRE 결재포털 통합결재 연동 */
    @Schema(description = "신청번호 (각 업무별 업무번호)")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer lid;

    /* FOR AP_APPR_PORTAL_PRE 결재포털 통합결재 연동 업무화면 링크 */
    @Schema(description = "통합결재 연동 업무화면 링크 (각 업무별 화면 링크) 지정된 화면경로가 고정되어 있으므로 공통결재선 모듈의 메뉴별 상수에서 값을 지정해 넘겨준다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String cnfmUrl;

    /* FOR AP_APPR_PORTAL_PRE 결재포털 통합결재 연동 업무화면 */
    @Schema(description = "통합결재 연동 업무화면명 (각 업무별 화면) 지정된 화면이 고정되어 있으므로 공통결재선 모듈의 메뉴별 상수에서 값을 지정해 넘겨준다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docNm;

    /* FOR AP_APPR_HTML 통합결재 원문 */
    @Schema(description = "HTML Contents")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String htmlContents;

    /* FOR AP_APPR_HTML 통합결재 원문 Parameter JSON */
    @Schema(description = "AP_APPR_HTML Param")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String param;

    /* INOUT, IO_OUT_APPL 스키마에서만 필요 */
    @Schema(description = "반출입번호 (INOUT, IO_OUT_APPL 스키마에서만 필요)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutserialno;

    /* INOUT, IO_OUT_APPL 스키마에서만 필요 */
    @Schema(description = "작성일자 (INOUT, IO_OUT_APPL 스키마에서만 필요)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String writedate;

    /* INOUT, IO_OUT_APPL 스키마에서만 필요 */
    @Schema(description = "작성순번 (INOUT, IO_OUT_APPL 스키마에서만 필요)")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer writeseq;

    /* IO_INOUT 스키마에서만 필요 */
    @Schema(description = "처리상태 (IO_INOUT 스키마에서만 필요)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applStat;

    /* IO_INOUT 스키마에서만 필요 */
    @Schema(description = "전산기기 여부 (IO_INOUT 스키마에서만 필요)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String pcYn;

    @Schema(description = "저장된 요청부서 결재선 목록")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<SavedApproverLineDTO> savedRequestApproverLine;

    @Schema(description = "저장된 허가부서 결재선 목록")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<SavedApproverLineDTO> savedPermitApproverLine;

    @Schema(description = "결재문서ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer docId;

    @Schema(description = "스키마명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "결재순번")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer apSeq;

    @Schema(description = "업체ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "업체ID (결재선 저장할 때 참조부서 결재선 조회에서 사용)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applCompId;

    @Schema(description = "메뉴ID (결재선 저장할 때 참조부서 결재선 조회에서 사용)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    @Schema(description = "부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwId;

    @Schema(description = "결재직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "결재부서구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDeptGbn;

    @Schema(description = "승인일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDtm;

    @Schema(description = "결재상태 0대기 10진행 20완료")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprStat;

    @Schema(description = "결재결과 0결과전 1승인 2부결")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprResult;

    @Schema(description = "부결기타 (상신자의 경우 상신의견으로 이 필드가 매핑된다.)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String canceletc;

    @Schema(description = "신청직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applEmpId;

    @Schema(description = "신청직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applEmpNm;

    @Schema(description = "신청부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applDeptId;

    @Schema(description = "신청부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applDeptNm;

    @Schema(description = "신청직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applJwNm;

    @Schema(description = "상신 의견 (이 필드는 Table(AP_DOC, AP_DOC_PRE)에 존재하지만 사용되지 않는다. 상신 의견도 canceletc 필드 사용)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docEtc;

    @Schema(description = "삭제구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String delYn;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "방문예약구분 O:방문객 V:VIP F:가족")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String vipYn;

    @Schema(description = "파일첨부여부 Y/N")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileYn;

    @Schema(description = "기타1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String etc1;

    @Schema(description = "기타2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String etc2;

    @Schema(description = "최종 승인 여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String finishYn;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

    @Schema(description = "임시저장 여부 Y/N (Table에 존재하지 않고 처리로직에서만 사용) (자산반출입에는 없는 기능기지만 호환을 위해 추가함)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String isImsiYn;

    @Schema(description = "상신내용(html)")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private Map<String, Object> htmlMap;

    @Schema(description = "신규 결재상신")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String isNew = "N";

    @Schema(description = "state")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String state;

}
