<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
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

  $(document).ready(function (e) {
    // 기본값 세팅
    defaultValueSet();

    // 목록 버튼 클릭시
    onClickGoListBtn();

    // 삭제 버튼 클릭시
    onClickBtnDelete();

    // 조치 내역 Select 세팅
    $.esutils.renderCode([{type: "select", grpCd: "C028", targetId: "actDo", blankOption: true}], function () {
      // 상세 화면 정보 그리기
      renderContents(function () {
        //조치현황 조회
        setActSumList();
      });
    });

    // 결제선 필요 조건일시에만
    if (isAppr()) {
      //결재선 요청부서, 허가부서 조회
      renderApprovalLine();
    }

  });

  /**************
   * [기본값 세팅]
   **************/
  function defaultValueSet() {
    $("#scDocNo").val("${param.scDocNo}");
    $("#scCorrPlanNo").val("${param.scCorrPlanNo}");
  }

  /************************************************
   * [결제선 필요여부 체크]
   * @return Boolean
   * @description 결제선 필요시 true, 불필요시 false
   ************************************************/
  function isAppr() {
    return !("${param.apprResult}" === "1003" && "${param.apprStat}" === "1003");
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

  /***************
   * [컨텐츠 그리기]
   ***************/
  function renderContents(callback) {
    $.esutils.renderData("securityForm",
        "/api/secrtactvy/securityAdminManageItem/coEmpViolationView?" + new URLSearchParams({scDocNo: $("#scDocNo").val()}).toString(),
        (d) => {

          $("[view-data='empJwNm']").html(d.empNm ?? "" + " " + d.jwNm ?? "");
          $("[view-data='dOfendEmpId']").html(d.ofendEmpId);
          $("[view-data='dOfendEmpNm']").html(d.ofendEmpNm);
          $("[view-data='dOfendDeptNm']").html(d.ofendDeptNm);
          $("[view-data='dOfendTelNo']").html(d.ofendTelNo);
          $("[view-data='dOfendDetailGbnNm']").html(d.ofendDetailGbnNm);
          $("[view-data='dOfendSubGbnNm']").html(d.ofendSubGbnNm);
          $("[view-data='subContTitle']").html(d.ofendGbnNm);
          $("[view-data='etcRsn']").html(d.etcRsn?.replaceAll('\n', '<br />'));

          // 첨부파일
          $("[view-data=filePath]").html("첨부파일없음");
          if (d.NFilePath !== "N") {
            //$("[view-data=filePath]").html("<a href=javascript:fn_fileDown('" + d.NFilePathAddr + "','" + d.NFilePathId + "','" + d.NFilePathNm + "');>" + d.NFilePathNm + "</a>");
            //$("[view-data=filePath]").html("<a href=javascript:fileDownload('" + d.NFilePath + "','" + d.NFilePathNm + "');>" + d.NFilePathNm + "</a>");
            $("[view-data=filePath]").html("<a id='fileDownload' href=javascript:void(0);>" + d.NFilePathNm + "</a>");
            //file Download Event
            fileDownload(d.NFilePath);
          }

          $("#actDo").prop("disabled", true);
          $("#rmrk").prop("readonly", true);

          if (d.ofendDetailGbn === "C0531010") {
            $("#trMobile").show();
            $("[view-data='dMobileForensicsGbnNm']").html(d.mobileForensicsGbnNm);

          } else {
            $("#trMobile").hide();
          }

          /******************************
           * [결제선 필요 페이지일때만 UI]
           ******************************/
          if (isAppr()) {

            //시정계획서 영역 표출
            $("#corrPlanTbl, #corrPlanTxt").show();
            // 삭제 관련 숨김
            $("#sigungTr, #jingTr, #delTrId, #btnDelete1, #btnDelete2").hide();

            // 조건 만족시 삭제버튼 표출
            if (d.corrPlanSendYn === "C0101002" || d.corrPlanSendYn === "") {
              $("#btnDelete1, #btnDelete2, #delTrId").show();
            }

            $("[view-data=pointContent]").html(d.pointContent);
            $("[view-data=scContent]").html(d.scContent);
            $("[view-data=etcContent]").html(d.etcContent);
            $("[view-data=imprContent]").html(d.imprContent);

            if (d.actDo === "C0280003") {
              $("#corrPlanTxt").html("경고장 개선계획");
              $("#jingTr").hide();
              $("#sigungTr").show();
              $("[view-data='siKaEmpNm']").html(d.kaEmpNm);
              $("[view-data='siGaEmpNm']").html(d.gaEmpNm);
            } else if (d.actDo === "C0280004") {
              $("#corrPlanTxt").html("징계의뢰 계획서");
              $("#sigungTr").hide();
              $("#jingTr").show();
              $("[view-data='kaEmpNm']").html(d.kaEmpNm);
              $("[view-data='gaEmpNm']").html(d.gaEmpNm);
              $("[view-data='aaEmpNm']").html(d.aaEmpNm);
            }
          }

          callback();

        }, {loading: true, exclude: ["scDocNo"]}
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

  /*********************
   * [조치현황 리스트 조회]
   *********************/
  function setActSumList() {

    const params = {
      ofendDt: $("[view-data=ofendDt]").text()
      , ofendEmpId: $("#ofendEmpId").val()
      , ofendDetailGbn: $("#ofendDetailGbn").val()
    };

    const grid = new GridUtil({
      gridId: "actSubGrid"
      , url: '/api/secrtactvy/securityAdminManageItem/coEmpViolationActSumList'
      , emptyText: "조치현황 정보가 없습니다."
      , search: {
        beforeSend: function (_) {
          return params;
        }
      }
      , gridOptions: {
        colData: [
          {
            headerName: "년/분기"
            , field: "yqGb"
            , cellattr: (rowId) => parseInt(rowId) % 2 !== 0 ? "rowspan=2" : "style='display:none;'"
          },
          {
            headerName: "구분"
            , field: "gb"
          },
          {
            headerName: "구성원 확인"
            , field: "cnt1"
          },
          {
            headerName: "시정계획서"
            , field: "cnt2"
          },
          {
            headerName: "경고장"
            , field: "cnt3"
          },
          {
            headerName: "인사징계 의뢰"
            , field: "cnt4"
          },
        ]
      }
    });

    // grid init
    grid.init();

  }

  /********************
   * [삭제 버튼 클릭시]
   ********************/
  function onClickBtnDelete() {
    $("#btnDelete1, #btnDelete2").on("click", function () {
      const $delRsn = $("#delRsn");
      if ($delRsn.val().length < 10) {
        alert("삭제사유를 10글자 이상으로 작성해주십시오.");
        $delRsn.focus();
        return;
      }

      if (!confirm("삭제하시겠습니까?")) {
        return;
      }

      const scDocNo = $("#scDocNo").val();
      const params = {
        acIp: global.acIp
        , empId: global.empId
        , scCorrPlanNo: $("#scCorrPlanNo").val()
        , delRsn: $delRsn.val()
      };

      const title = $("#corrPlanSendYn").val() ? "시정계획서" : "보안위규정보";

      $.esutils.postApi(`/api/secrtactvy/securityAdminManageItem/secrtCorrPlanOfendDelete/\${scDocNo}`, params, function (res) {
        if (res?.data) {
          alert(`\${title}가 삭제 되었습니다.`);
          //목록으로 돌아가기
          //$("#buttonGoList1").trigger("click");
        } else {
          alert(`\${title}가 삭제 중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.`);
        }
      })

    });
  }

  /********************
   * [목록 버튼 클릭시]
   ********************/
  function onClickGoListBtn() {
    $("#buttonGoList1, #buttonGoList2").on("click", function () {
      $.esutils.href("/admin/secrtactvy/securityadminmanageitem/coviolation/list");
    })
  }

</script>

<div id="contentsArea">
    <!-- <h1 class="title>임직원 시정계획서 상세보기</h1> -->
    <table width="100%" border="0">
        <tr>
            <td align="left">
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_312.png"/>
            </td>
            <td align="right">
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <!-- <span class="button bt_s2"><button type="button" style="width:100px;" onclick="javascript:fn_getUrl('P03020704');">목록</button></span> -->
                        <span class="button bt_s2" id="buttonGoList1"><button type="button" style="width:100px;">목록</button></span>
                        <span class="button bt_s1" id="btnDelete1"><button type="button" style="width:100px;">삭제</button></span>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <img src="/eSecurity/common/images/common/line.png" width="100%" height="3"/>
            </td>
        </tr>
    </table>


    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm">
            <input type="hidden" id="scDocNo" name="scDocNo" value=""/>
            <input type="hidden" id="scCorrPlanNo" name="scCorrPlanNo" value=""/>
            <input type="hidden" id="corrPlanSendYn" name="corrPlanSendYn"/>

            <input type="hidden" id="ofendEmpId" name="ofendEmpId" value=""/>
            <input type="hidden" id="ofendDetailGbn" name="ofendDetailGbn" value=""/>

            <h1 class="txt_title01">등록자</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="151"/>
                    <col width="274"/>
                    <col width="151"/>
                    <col width="274"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>위규 구분</th>
                    <td view-data="ofendGbnNm"></td>
                    <th>성명/직위</th>
                    <td view-data="empJwNm"></td>
                </tr>
                <tr>
                    <th>회사명</th>
                    <td view-data="compNm"></td>
                    <th>부서</th>
                    <td view-data="deptNm"></td>
                </tr>
                <tr>
                    <th>적발일</th>
                    <td view-data="ofendDt">
                    </td>
                    <th>적발시각</th>
                    <td view-data="ofendTm">
                    </td>
                </tr>
                </tbody>
            </table>
            <h1 id="subContTitle" class="txt_title01"></h1>
            <table id="securityTable" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="151"/>
                    <col width="274"/>
                    <col width="151"/>
                    <col width="274"/>
                </colgroup>
                <tbody>
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
                    <th>위규 내용</th>
                    <td colspan="3" view-data="dOfendDetailGbnNm"></td>
                </tr>
                <tr>
                    <th>위규 내용 상세</th>
                    <td colspan="3" view-data="dOfendSubGbnNm"></td>
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
                    <th>적발GATE&nbsp;</th>
                    <td view-data="actGate"></td>
                </tr>

                <tr>
                    <th>보안요원</th>
                    <td colspan=3 view-data="secManNm"></td>
                </tr>

                <tr>
                    <th>보안요원의견&nbsp</th>
                    <td colspan=3 view-data="etcRsn"></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan=3 view-data="filePath">&nbsp;</td>
                </tr>
                </tbody>
            </table>

            <h1 class="txt_title01">위규조치</h1>
            <table id="securityTable2" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="151"/>
                    <col width="699"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>조치안내</th>
                    <td view-data="ofendRmrk"></td>
                </tr>
                <tr>
                    <th>조치내역</th>
                    <td><select id="actDo" name="actDo" style="width: 242px;"/></td>
                </tr>
                <tr>
                    <th>비고</th>
                    <td><textarea id='rmrk' name='rmrk' style="width: 95%; height: 70px; overflow: auto;"></textarea></td>
                </tr>
                <tr id="delTrId">
                    <th>삭제사유</th>
                    <td><textarea id='delRsn' name='delRsn' style="width: 95%; height: 70px; overflow: auto;"></textarea></td>
                </tr>
                </tbody>
            </table>

            <h1 class="txt_title01">조치현황</h1>
            <div style="width:100%;height:AUTO;margin-top:10px;">
                <div id="actSubGrid"></div>
            </div>

            <h1 class="txt_title01" style="display:none;" id="corrPlanTxt">시정계획서</h1>
            <table id="corrPlanTbl" style="display:none;" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="151"/>
                    <col width="132"/>
                    <col width="151"/>
                    <col width="132"/>
                    <col width="151"/>
                    <col width="132"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>내용</th>
                    <td colspan="5" view-data="pointContent"></td>
                </tr>
                <tr>
                    <th>구체적 개선내용</th>
                    <td colspan="5" view-data="imprContent"></td>
                </tr>
                <tr>
                    <th>기타의견</th>
                    <td colspan="5" view-data="etcContent"></td>
                </tr>
                <tr>
                    <th>산업보안팀<br/>지적사항 확인란</th>
                    <td colspan="5" view-data="scContent"></td>
                </tr>
                <tr id="sigungTr">
                    <th>팀장</th>
                    <td view-data="siKaEmpNm"></td>
                    <th>사업부장</th>
                    <td colspan="3" view-data="siGaEmpNm"></td>
                </tr>
                <tr id="jingTr">
                    <th>팀장</th>
                    <td view-data="kaEmpNm"></td>
                    <th>사업부장</th>
                    <td view-data="gaEmpNm"></td>
                    <th>대표이사</th>
                    <td view-data="aaEmpNm"></td>
                </tr>
                </tbody>
            </table>
            <!-- 결재절차 영역 -->
            <div id="approvalWrap"></div>
            <!-- 결재절차 영역 -->

            <!-- 버튼 -->
            <div class="buttonGroup">
                <div class="leftGroup">
                    <!-- <span class="button bt_s2"><button type="button" style="width:100px;" onclick="javascript:fn_getUrl('P03020704');">목록</button></span> -->
                    <span class="button bt_s2" id="buttonGoList2"><button type="button" style="width:100px;">목록</button></span>
                    <span class="button bt_s1" id="btnDelete2"><button type="button" style="width:100px;">삭제</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->

        </form>
    </div>
