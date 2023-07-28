<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

  /**
   * 기존 (apprResult === 1003 && apprStat === 1003) 상세페이지 2개로 분기처리 되었음.
   *
   * if(APPR_RESULT == "1003" && APPR_STAT == "1003") {
   * coEmpCorrPlanAdminView2.jsp?SC_DOC_NO="+SC_DOC_NO+"&SC_CORR_PLAN_NO="+SC_CORR_PLAN_NO+"&DOC_ID="+DOC_ID;
   * coEmpCorrPlanAdminView2.jsp >> 결재선 필요없음.
   * }
   * else {
   * coEmpCorrPlanAdminView.jsp?SC_DOC_NO="+SC_DOC_NO+"&SC_CORR_PLAN_NO="+SC_CORR_PLAN_NO+"&DOC_ID="+DOC_ID;
   * coEmpCorrPlanAdminView.jsp >> 결재선 필요.
   * }
   *
   * isAppr() 값으로 구분하여 한페이지로 통합.
   * isAppr() true >> coEmpCorrPlanAdminView.jsp
   * isAppr() false >> coEmpCorrPlanAdminView2.jsp
   */
  $(document).ready(function () {

    // 기본값 설정
    defaultValueSet();

    // 컨텐츠 그리기
    renderContents();

    // 목록버튼 클릭
    onClickBtnList();

    // 결제선 필요 조건일시에만
    if(isAppr()) {
      //결재선 요청부서, 허가부서 조회
      renderApprovalLine();
    }

  });

  /************************************************
   * [결제선 필요여부 체크]
   * @return Boolean
   * @description 결제선 필요시 true, 불필요시 false
   ************************************************/
  function isAppr() {
    return !("${param.apprResult}" === "1003" && "${param.apprStat}" === "1003");
  }

  /***************
   * [기본값 세팅]
   ***************/
  function defaultValueSet() {

    const scDocNo = "${param.scDocNo}";
    const scCorrPlanNo = "${param.scCorrPlanNo}";
    $("#scDocNo").val(scDocNo);
    $("#scCorrPlanNo").val(scCorrPlanNo);
  }

  /***************
   * [컨텐츠 조회]
   ***************/
  function renderContents() {

    const params = {...($.esutils.getFieldsValue($("#securityForm"))), acIp: global.acIp};
    $.esutils.renderData("securityForm",
        "/api/secrtactvy/securityManageItem/securityConcernCoEmpViolationView?" + new URLSearchParams(params).toString()
        , function (d) {
          /******************************
           * [data key 맞지 않아 수동세팅]
           *****************************/
          $("[view-data=dOfendEmpNm]").html(d.ofendEmpNm);
          $("[view-data=dOfendTelNo]").html(d.ofendTelNo);
          $("[view-data=dOfendEmpId]").html(d.ofendEmpId);
          $("[view-data=dOfendDeptNm]").html(d.ofendDeptNm);
          $("[view-data=dOfendDetailGbnNm]").html(d.ofendDetailGbnNm);
          $("[view-data=dOfendSubGbnNm]").html(d.ofendSubGbnNm);

          // 모바일 포렌식
          if (d.ofendDetailGbn === 'C0531010') {
            $("#trMobile").show();
            $("[view-data=dMobileForensicsGbnNm]").html(d.mobileForensicsGbnNm);
          } else {
            $("#trMobile").hide();
          }

          // 첨부파일
          $("[view-data=filePath]").html("첨부파일없음");
          if (d.NFilePath !== "N") {
            //$("[view-data=filePath]").html("<a href=javascript:fn_fileDown('" + d.NFilePathAddr + "','" + d.NFilePathId + "','" + d.NFilePathNm + "');>" + d.NFilePathNm + "</a>");
            //$("[view-data=filePath]").html("<a href=javascript:fileDownload('" + d.NFilePath + "','" + d.NFilePathNm + "');>" + d.NFilePathNm + "</a>");
            $("[view-data=filePath]").html("<a id='fileDownload' href=javascript:void(0);>" + d.NFilePathNm + "</a>");
            //file Download Event
            fileDownload(d.NFilePath);
          }


          /******************************
           * [결제선 필요 페이지일때만 UI]
           ******************************/
          if(isAppr()) {
            $("#corrPlanTbl, #corrPlanTxt").show();
            d.pointContent && $("[view-data=pointContent]").html(d.pointContent);
            d.scContent && $("[view-data=scContent]").html(d.scContent);
            d.etcContent && $("[view-data=etcContent]").html(d.etcContent);
            d.imprContent && $("[view-data=imprContent]").html(d.imprContent);

            if(d.actDo === "C0280003") {
              $("#corrPlanTxt").html("경고장 개선계획");
            }
          }

        },{loading: true, exclude: ["scDocNo"]}
    )

  }

  /****************
   * [파일다운로드]
   ***************/
  function fileDownload(filePath) {
    $("#fileDownload").on("click", function() {
      $.esutils.fileDownload(filePath.split(';')[0], filePath.split(';')[1]);
    });
  }

  /*******************
   * [목록 버튼 클릭시]
   *******************/
  function onClickBtnList() {
    $("#btnList").on("click", function () {
      $.esutils.href("/secrtactvy/securitymanageitem/securityconcerncoemp/list");
    });
  }

  /*******************
   * [결재선 라인 조회]
   ******************/
  function renderApprovalLine() {
    const docId = "${param.docId}";
    const esecurityApproval = new EsecurityApproval("approvalWrap");

    //docId가 없어도 결재절차 UI는 Render.
   esecurityApproval.view(docId);
  }

</script>
<div id="contentsArea">
    <!-- <h1 class="title">보안위규자 조회 권한 상세보기</h1> -->
    <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_328.png"/>
    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm">
            <input type="hidden" id="scDocNo" name="scDocNo" value=""/>
            <input type="hidden" id="scCorrPlanNo" name="scCorrPlanNo" value=""/>
            <input type="hidden" id="corrPlanSendYn" name="corrPlanSendYn"/>
            <input type="hidden" id="nightOfendSendYn" name="nightOfendSendYn"/>
            <input type="hidden" id="emailOfendSendYn" name="emailOfendSendYn"/>

            <h1 class="txt_title01">위규 정보</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="117px;"/>
                    <col width="213px;"/>
                    <col width="117px;"/>
                    <col width="213px;"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>적발일</th>
                    <td view-data="ofendDt">
                    </td>
                    <th>적발시각</th>
                    <td view-data="ofendTm">
                    </td>
                </tr>
                <tr>
                    <th>성명</th>
                    <td view-data="dOfendEmpNm"></td>
                    <th>연락처</th>
                    <td view-data="dOfendTelNo"></td>
                </tr>
                <tr>
                    <th>사번</th>
                    <td view-data="dOfendEmpId"></td>
                    <th>소속</th>
                    <td view-data="dOfendDeptNm"></td>
                </tr>
                <tr>
                    <th>위반내용</th>
                    <td view-data="dOfendDetailGbnNm"></td>
                    <th>상세내용</th>
                    <td view-data="dOfendSubGbnNm"></td>
                </tr>
                <tr id='trMobile' style='display :none;'>
                    <th>모바일 포렌직</th>
                    <td colspan=3 view-data="dMobileForensicsGbnNm"></td>
                </tr>
                <tr>
                    <th>적발사업장</th>
                    <td view-data="actCompNm"></td>
                    <th>적발건물</th>
                    <td view-data="actBldgNm"></td>
                </tr>

                <tr>
                    <th>적발위치</th>
                    <td view-data="actLocateNm"></td>
                    <th>적발GATE</th>
                    <td view-data="actGate"></td>
                </tr>

                <tr>
                    <th>첨부파일</th>
                    <td colspan=3 view-data="filePath"></td>
                </tr>
                </tbody>
            </table>
            <h1 id="corrPlanTxt" style="display:none;" class="txt_title01">시정계획서</h1>
            <table id="corrPlanTbl" style="display:none;" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="117px;"/>
                    <col width="213px;"/>
                    <col width="117px;"/>
                    <col width="213px;"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>내용</th>
                    <td colspan="3" view-data="pointContent"></td>
                </tr>
                <tr>
                    <th>구체적 개선내용</th>
                    <td colspan="3" view-data="imprContent"></td>
                </tr>
                <tr>
                    <th>기타의견</th>
                    <td colspan="3" view-data="etcContent"></td>
                </tr>
                <tr>
                    <th>산업보안팀<br/>지적사항 확인란</th>
                    <td colspan="3" view-data="scContent"></td>
                </tr>
                </tbody>
            </table>
            <div id="approvalWrap"></div>
            <!-- 버튼 -->
            <div class="buttonGroup">
                <div class="leftGroup">
                    <span class="button bt_s2" id="btnList"><button type="button" style="width:100px;">목록</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->
        </form>
    </div>
</div>
