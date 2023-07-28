package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "인사정보")
public class CoEmpDTO extends CommonDTO {

    @Schema(description = "사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "회사(위치)ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "회사(위치)명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "업체지역구분코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compKnd;

    @Schema(description = "업체구분코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compAreaKnd;

    @Schema(description = "부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "부서명(단일)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String orgNam;

    @Schema(description = "직책코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jcCd;

    @Schema(description = "직책명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jcNm;

    @Schema(description = "직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwId;

    @Schema(description = "직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwNm;

    @Schema(description = "ID(사번)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String id;

    @Schema(description = "Session ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String sessionId;

    @Schema(description = "비밀번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String password;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String divCd;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String divNm;

    @Schema(description = "주민번호앞자리(생년월일)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String juminNo;

    @Schema(description = "이메일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String email;

    @Schema(description = "휴대폰번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String telNo1;

    @Schema(description = "전화번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String telNo2;

    @Schema(description = "우편번호 앞자리")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String zip1;

    @Schema(description = "우편번호 뒷자리")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String zip2;

    @Schema(description = "주소")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String addr;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String htCd;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String joinDt;

    @Schema(description = "퇴사일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String retireDt;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer gateId;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer gateKnd;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String adminKnd;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String joblocat;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String wgrpCd;

    @Schema(description = "", allowableValues = "Y,N")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String vstMngYn;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String userTypeCd;

    @Schema(description = "사용여부", allowableValues = "Y,N")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

    @Schema(description = "트리거플래그")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer trgflag;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp modDtm;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jmLvl;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer sortNum;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptDivNm;

    @Schema(description = "구성원여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empYn;

    @Schema(description = "로그인 실패 횟수")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer loginFailCnt;

    //	@Schema(description = "권한정보")
    //	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
    //	private List<Integer> authIds;
    //
    //	@Schema(description = "접속가능 메뉴정보")
    //	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
    //	private List<String> menuIds;

    @Schema(description = "순번")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer rowNum;

    @Schema(description = "출입증ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String idcardId;

    @Schema(description = "직책아이디")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jcId;
}
