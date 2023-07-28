<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map login = (Map) request.getSession().getAttribute("Login");
%>

<script>
  $(document).ready(function (e) {

    // 기본값 세팅
    defaultValueSet();

    // 목록 버튼 클릭시
    onClickGoListBtn();

    //삭제 버튼 클릭시
    onClickDeleteBtn();

    // 승인/반려 버튼 클릭시
    onClickApproveDenyBtn();

    // 출입제한 버튼 클릭시
    onClickOffLimitBtn();

    // 조치 내역 Select 세팅
    $.esutils.renderCode([{type: "select", grpCd: "C028", targetId: "actDo", blankOption: true}], function () {
      // 상세 화면 정보 그리기
      renderContents(function() {
        // 보안 위규  이력 조회
        setCorrPlanViolationList();
        // 접견자 리스트 조회
        setInterviewerList();
        // 조치현황 리스트 조회
        setActSumList();
      });
    });

  });

  /**************
   * [기본값 세팅]
   **************/
  function defaultValueSet() {
    $("#scIoDocNo").val("${param.scIoDocNo}");
    $("#scIoCorrPlanNo").val("${param.scIoCorrPlanNo}");
    $("#corrPlanSendYn").val("${param.corrPlanSendYn}");
    $("#ofendEmpId").val("${param.ofendEmpId}");
  }

  /***************
   * [컨텐츠 그리기]
   ***************/
  function renderContents(callback) {

    const param = {
      scIoCorrPlanNo: $("#scIoCorrPlanNo").val() ?? ""
      ,language :  $("#language").val()
    }

    const scIoDocNo = $("#scIoDocNo").val();

    $.esutils.renderData("securityForm",
        "/api/secrtactvy/securityAdminManageItem/ioEmpViolationView/" +scIoDocNo+"?"+ new URLSearchParams(param).toString(),
        (d) => {

          $("[view-data='empJwNm']").html(d.empNm ?? "" + " " + d.jwNm ?? "");
          $("[view-data='dOfendEmpId']").html(d.ofendEmpId);
          $("[view-data='dOfendEmpNm']").html(d.ofendEmpNm);
          $("[view-data='dOfendDeptNm']").html(d.ofendDeptNm);
          $("[view-data='dOfendTelNo']").html(d.ofendTelNo);

          $("[view-data='dOfendDetailGbnNm']").html(d.ofendDetailGbnNm);
          $("[view-data='dOfendSubGbnNm']").html(d.ofendSubGbnNm);
          $("[view-data='subContTitle']").html(d.ofendGbnNm);
          $("[view-data='visitEmpno']").html(d.visitEmpno);
          $("[view-data='etcRsn']").html(d.etcRsn?.replaceAll('\n', '<br />'));

          $("#actDo").attr("disabled", true);
          $("#rmrk").attr("readonly", true);


          //스티커 미부착
          if (d.ofendDetailGbn === 'C0581009')
          {
            $("#trMobile").show();
            $("[view-data=dMobileForensicsGbnNm]").html(d.mobileForensicsGbnNm);

          } else {
            $("#tr_mobile").hide();
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
          //대표관리자 시정공문 파일첨부 추가 : 2016-07-22 by JSH
          if(d.exprFilePath === "N" ){
            $("[view-data=exprFilePath]").html("첨부파일없음");
          }
          else {
            //$("[view-data=exprFilePath]").html("<a href=javascript:fn_fileDownWC('"+ jsonData.fields.EXPR_FILE_PATH_ADDR + "','" + jsonData.fields.EXPR_FILE_PATH_ID + "','" + jsonData.fields.EXPR_FILE_PATH_NM + "');>" + jsonData.fields.EXPR_FILE_PATH_NM + "</a>");
            $("[view-data=exprFilePath]").html("<a id='exprFileDownload' href=javascript:void(0);>" + d.exprFilePathNm + "</a>");
            //file Download Event
            exprFileDownload(d.exprFilePath);
          }

          //미승인이 아닐때
          if (d.apprGbn !== "Z0401001") {
            //승인 버튼
            $("#approveBtn1").remove();
            $("#approveBtn2").remove();

            //반려버튼
            $("#denyBtn1").remove();
            $("#denyBtn2").remove();

            $("#etcContent").attr("readonly", true);
            $("#cancelContent").attr("readonly", true);
          }

          // 기존 login ID if문
          //if ((loginId === "2047930") || (loginId === "2037179") || (loginId === "2049952") || (loginId === "I0100053") || (loginId === "2053412")) {
          const includeLoginIdArray = ["2047930", "2037179", "2049952", "I0100053", "2053412"];

          if (includeLoginIdArray.includes(global.empId)) {
            $("#corrPlanTable").append(
                "<tr>"
                + "<th>지적사항 확인란<span class=\"necessary\"></span></td>"
                + "<td colspan='3'>"
                + "<textarea id=\"scContent\" name=\"scContent\" style=\"width: 95%; height: 70px; overflow: auto;\"></textarea>"
                + "</td>"
                + "</tr>"
            );
            d.scContent && $("#scContent").val(d.scContent);
          }

          // 권한 확인(86번은 보안관리자)
          const isAuthCheck = $.esutils.checkAuth("86", "<%=login.get("AUTH")%>");

          if(!isAuthCheck) {
            //보안관리자가 아닌경우

            // 승인버튼
            $("#approveBtn1").remove();
            $("#approveBtn2").remove();

            // 반려버튼
            $("#denyBtn1").remove();
            $("#denyBtn2").remove();

            // 출입제한버튼
            $("#offLimitBtn1").remove();
            $("#offLimitBtn2").remove();
          }
          else {
            // 시정계획서 미제출 C0101002
            if(d.corrPlanSendYn === "C0101002" || d.corrPlanSendYn === "" ) {
              //삭제버튼
              $("#deleteBtn1, #deleteBtn2").show();
              $("#delTrId").show();
            }
          }

          if(d.actDo === "C0280006") { // C0280006 시정공문 징구
            $("#corrPlanTxt").html("대표이사 시정공문 징구");
            $("#exprCorrTr").show();
          }

          //call callback function
          callback();
        }, {loading: true, exclude: ["scIoDocNo", "scIoCorrPlanNo", "language"]}
    )
  }

  /****************
   * [파일다운로드]
   ***************/
  function fileDownload(filePath) {
    $("#fileDownload").on("click", function () {
      $.esutils.fileDownload(filePath.split(';')[0], filePath.split(';')[1]);
    });
  }

  /****************
   * [파일다운로드]
   ***************/
  function exprFileDownload(filePath) {
    $("#exprFileDownload").on("click", function () {
      $.esutils.fileDownload(filePath.split(';')[0], filePath.split(';')[1]);
    });
  }

  /*******************
   * [삭제 버튼 클릭시]
   *******************/
  function onClickDeleteBtn() {
    $("#deleteBtn1, #deleteBtn2").on("click", function() {
      const $delRsn = $("#delRsn");
      if($delRsn.val().length < 10) {
        alert("삭제사유를 10글자 이상으로 작성해주십시오.");
        $delRsn.focus();
        return;
      }

      if(!confirm("삭제하시겠습니까?")) {return;}

      const params = {
        acIp : global.acIp
        , empId : global.empId
        , scIoDocNo : $("#scIoDocNo").val()
        , scIoCorrPlanNo: $("#scIoCorrPlanNo").val()
        , delRsn : $delRsn.val()
      };

      const scIoDocNo = $("#scIoDocNo").val();

      $.esutils.postApi("/api/secrtactvy/securityAdminManageItem/secrtIoCorrPlanOfendDelete/" + scIoDocNo, params, function(res) {
        if(res?.data) {
          alert("보안위규정보가 삭제 되었습니다.");
          //목록으로 돌아가기
          $("#buttonGoList1").trigger("click");
        }
        else {
          alert("보안위규정보 삭제 중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      })

    });
  }

  /*******************
   * [승인/반려 버튼 클릭시]
   *******************/
  function onClickApproveDenyBtn() {
    $("#approveBtn1, #approveBtn2, #denyBtn1, #denyBtn2").on("click", function() {

        const isApprove = $(this).attr("id").indexOf("approve") > -1;

        // 지적사항 글자체크
        if(!scContentValidation()) return;

        let apprGbn = "";
        let corrPlanSendYn = "";
        let msg = "";
        let resultMsg = "";
        let scContent = "";

        if(isApprove) {
          //승인
          apprGbn = "Z0401003";
          corrPlanSendYn = "C0101001";
          msg = "시정계획서를 승인하시겠습니까?";
          resultMsg = "시정계획서가 승인 되었습니다.";
        }
        else {
          //반려
          apprGbn = "Z0401002";
          corrPlanSendYn = "C0101002";
          msg = "시정계획서를 반려하시겠습니까?";
          resultMsg = "시정계획서가 반려 되었습니다.";

          if(!$("#cancelContent").val()) {
            alert("반려사유를 입력하세요.");
            $("#cancelContent").focus();
            return;
          }
        }

        const includeLoginIdArray = ["2047930", "2037179", "I0100053"];
        if(includeLoginIdArray.includes(global.empId)) {
          scContent = $("#scContent").val();
        }

        if(!confirm(msg)) return;

        const params = {
          scIoDocNo : $("#scIoDocNo").val()
          , scIoCorrPlanNo : $("#scIoCorrPlanNo").val() ?? ""
          , empId : global.empId
          , apprGbn
          , corrPlanSendYn
          , scContent
          , cancelContent :  $("#cancelContent").val() ?? ""
          , acIp : global.acIp
          , modBy : global.empId
        }

        const scIoDocNo = $("#scIoDocNo").val();

        $.esutils.postApi("/api/secrtactvy/securityAdminManageItem/ioCorrPlanDocApprUpdate/" + scIoDocNo, params,
            function(res) {
                if(res.data) {
                  alert(resultMsg);

                  //목록 이동
                  //$("#buttonGoList1").trigger("click");
                }
                else {
                  alert("시정계획서 결재 진행 중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
                }
            }
        )


    });
  }


  /***********************
   * [출입제한 버튼 클릭시]
   ***********************/
  function onClickOffLimitBtn() {

  }

  /************************
   * [지적사항 validation]
   ************************/
  function scContentValidation() {
    /*사번 조직변경	if((loginId == "B47930") || (loginId == "B37179")){ */
    const includeLoginIdArray = ["2047930", "2037179", "I0100053"];

    if(includeLoginIdArray.includes(global.empId)){
      const scContentValue = $("#scContent").val();
      if(scContentValue) {
        if(scContentValue.length > 600) {
          alert("지적사항 확인란을 600자 이하로 작성해주십시오.");
          $("#scContent").focus();
          return false;
        }
      }
    }

    return true;
  }


  /*********************
   * [동일위규 이력조회]
   *********************/
  function setCorrPlanViolationList() {

    const ofendEmpId = $("#ofendEmpId").val();
    const param = {language : $("#language").val()};

    const grid = new GridUtil({
      gridId: "ofendHistList"
      , url: "/api/secrtactvy/securityAdminManageItem/ioCorrPlanViolationList/" + ofendEmpId +"?"+ new URLSearchParams(param).toString()
      , gridOptions: {
        colData: [
          {
            headerName: "지적일시"
            , field: "ofendDt"
            , formatter: (cellValue, _, row) => {
              const ofendDt = cellValue ?? "";
              const ofendTm = row.ofendTm ?? "";
              return ofendDt + "<br/>" + ofendTm;
            }
          },
          {
            headerName: "위규사업장"
            , field: "regCompNm"
          },
          {
            headerName: "등록자"
            , field: "regEmpNm"
          },
          {
            headerName: "내용"
            , field: "ofendGbnNm"
            , formatter: (cellValue, _, row) => {
              const ofendGbnNm = row.ofendGbnNm ?? "";
              const ofendDetailGbnNm = row.ofendDetailGbnNm ?? "";
              return ofendGbnNm + " - " + ofendDetailGbnNm;
            }
          },
          {
            headerName: "시정계획서"
            , field: "corrPlanSendYnNm"
          },
          {
            headerName: "분기"
            , field: "qt"
          },
          {
            headerName: "비고"
            , field: "bigo"
          },
        ]
        , loadComplete: function () {
          const $grid = $(this);
          const ids = $grid.getDataIDs();
          $.each(
              ids, function (idx, rowId) {
                let r = $grid.getRowData(rowId);
                // 당해년도 Row 색상 변경
                if (r.ofendDt.substr(0, 4) === new Date().getFullYear().toString()) {
                  $grid.setRowData(rowId, false, {background: "#F8F8F8"});
                }
              }
          );
        }
      }
    });

    // 보안이력 grid Init
    grid.init();
  }

  /*********************
   * [접견자 리스트 조회]
   ********************/
  function setInterviewerList() {

    // 입주사 접견자
    const interviewerGrid = new GridUtil({
      url : "/api/secrtactvy/securityAdminManageItem/ioEmpViolationViewMeetList/" + $("#scIoDocNo").val()
      , gridId: "interviewerGrid"
      , gridOptions: {
        width: "620"
        , emptyText: "입주사 접견자 정보가 없습니다."
        , colData: [
          {
            headerName: "소속조직"
            , field: "deptNm"
          },
          {
            headerName: "이름"
            , field: "empNm"
          },
          {
            headerName: "직위"
            , field: "jwNm"
          },
        ]
      }
      , search: {
        beforeSend: () => {
          return {scIoDocNo: $("#scIoDocNo").val()};
        }
      }
    });

    // grid init
    interviewerGrid.init();
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
      , url: '/api/secrtactvy/securityAdminManageItem/ioEmpViolationActSumList'
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
            headerName: "외부인 확인"
            , field: "cnt5"
          },
          {
            headerName: "시정계획서 징구"
            , field: "cnt1"
          },
          {
            headerName: "대표이사 시정공문 징구"
            , field: "cnt2"
          },
          {
            headerName: "1개월 출입정지"
            , field: "cnt3"
          },
          {
            headerName: "영구 출입정지"
            , field: "cnt4"
          },
        ]
      }
    });

    // grid init
    grid.init();

  }

  /********************
   * [목록 버튼 클릭시]
   ********************/
  function onClickGoListBtn() {

    $("#buttonGoList1, #buttonGoList2").on("click", function () {
      // 목록으로 이동
      $.esutils.href("/admin/secrtactvy/securityadminmanageitem/ioviolation/list", {});
    })

  }

</script>

<div id="contentsArea">
    <!-- <h1 class="title>외부인 시정계획서 작성</h1> -->
    <table width="100%" border="0">
        <tr>
            <td align="left">
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_331.png"/>
            </td>
            <td align="right">
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s1" id="approveBtn1"><button type="button" style="width:100px;">승인</button></span>
                        <span class="button bt_s2" id="denyBtn1"><button type="button" style="width:100px;">반려</button></span>
                        <br/>
                        <span class="button bt_s1" id="offLimitBtn1"><button type="button" style="width:100px;">출입제한</button></span>
                        <span class="button bt_s2" id="buttonGoList1"><button type="button" style="width:100px;">목록</button></span>
                        <span style="display:none;" class="button bt_s1" id="deleteBtn1"><button type="button" style="width:100px;">삭제</button></span>
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
        <form id="securityForm" name="securityForm" method="post">
            <input type="hidden" id="tableRow" name="tableRow"/>
            <input type="hidden" id="schemaNm" name="schemaNm" value="ioCorrPlan"/>
            <input type="hidden" id="docNm" name="docNm" value="방문객 시정계획서"/>
            <input type="hidden" id="cnfmUrl" name="cnfmUrl" value="CorrPlan/corrPlan.jsp"/>
            <input type="hidden" id="language" name="language" value="KOR"/>

            <input type="hidden" id="compId" name="compId"/>
            <input type="hidden" id="scIoDocNo" name="scIoDocNo" value=""/>
            <input type="hidden" id="scIoCorrPlanNo" name="scIoCorrPlanNo" value=""/>
            <input type="hidden" id="ofendEmpId" name="ofendEmpId" value=""/>
            <input type="hidden" id="operateId" name="operateId" value=""/>
            <input type="hidden" id="ofendDetailGbn" name="ofendDetailGbn" value=""/>

            <input type="hidden" id="filePathAddr" name="filePathAddr"/>
            <input type="hidden" id="filePathNm" name="filePathNm"/>
            <input type="hidden" id="filePathId" name="filePathId"/>

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
                    <th>아이디</th>
                    <td view-data="dOfendEmpId" style='width:213px;'></td>
                    <th>회사</th>
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
                <tr id='tr_mobile' style='display :none;'>
                    <th>모바일 포렌직</th>
                    <td colspan=3 view-data="dMobileForensicsGbnNm"></td>
                </tr>
                <tr>
                    <th>적발 사업장</th>
                    <td view-data="actCompNm"></td>
                    <th>적발 건물</th>
                    <td view-data="actBldgNm"></td>
                </tr>

                <tr>
                    <th>적발 건물 상세</th>
                    <td view-data="actLocateNm"></td>
                    <th>적발 GATE</th>
                    <td view-data="actGate"></td>
                </tr>

                <tr>
                    <th>보안요원 성명</th>
                    <td colspan=3 view-data="secManNm"></td>
                </tr>

                <tr>
                    <th>보안요원 검토의견</th>
                    <td view-data="etcRsn"></td>
                    <th>방문증 통합사번</th>
                    <td view-data="visitEmpno"></td>
                </tr>

                <tr>
                    <th>입주사 접견자</th>
                    <td colspan=3>
                        <div id="interviewerGrid"></div>
                    </td>
                </tr>

                <tr>
                    <th>첨부파일</th>
                    <td colspan=3 view-data="filePath">&nbsp;</td>
                </tr>
                </tbody>
            </table>

            <h1 class="txt_title01">위규조치</h1>
            <table id="offendTbl" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
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
                <tr id="delTrId" style="display:none;">
                    <th>삭제사유</th>
                    <td><textarea id='delRsn' name='delRsn' style="width: 97%; height: 70px; overflow: auto;"></textarea></td>
                </tr>
                </tbody>
            </table>

            <h1 class="txt_title01">조치현황</h1>
            <div style="width:100%;height:AUTO;" margin-top:10px;
            ">
            <div id="actSubGrid" class="table_type2">
                <table id="actSumList">
                    <colgroup>
                        <col width="120px;"/>
                        <col width="60px;"/>
                        <col width="90px;"/>
                        <col width="120px;"/>
                        <col width="100px;"/>
                        <col width="90px;"/>
                        <col width="90px;"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col">년/분기</th>
                        <th scope="col">구분</th>
                        <th scope="col">외부인 확인</th>
                        <th scope="col">시정계획서 징구</th>
                        <th scope="col">대표이사 시정공문 징구</th>
                        <th scope="col">1개월 출입정지</th>
                        <th scope="col">영구 출입정지</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
    </div>

    <h1 class="txt_title01" id="corrPlanTxt">시정계획서</h1>
    <table id="corrPlanTable" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
        <colgroup>
            <col width="151"/>
            <col width="699"/>
        </colgroup>
        <tbody>
        <tr>
            <th>내용</th>
            <td view-data="pointContent">
            </td>
        </tr>
        <tr>
            <th>구체적 개선내용</th>
            <td view-data="imprContent">
            </td>
        </tr>
        <tr>
            <th>기타의견</th>
            <td view-data="etcContent">
            </td>
        </tr>
        <tr id="exprCorrTr" style='display :none;'>
            <th>시정공문 제출</th>
            <td view-data="exprFilePath"></td>
        </tr>
        <tr>
            <th>반려사유</th>
            <td>
                <textarea style="ime-mode:active; width:97%; height: 70px;" name="cancelContent" id="cancelContent"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
    <h1 class="txt_title01" id="historyTitle">보안 위규 이력</h1>
    <div id="ofendHistList"></div>
    <!-- 버튼 -->
    <div class="buttonGroup">
        <div class="leftGroup">
            <span class="button bt_s1" id="approveBtn2"><button type="button" style="width:100px;" onclick="javascript:fn_corrPlanAppr('1');">승인</button></span>
            <span class="button bt_s2" id="denyBtn2"><button type="button" style="width:100px;" onclick="javascript:fn_corrPlanAppr('2');">반려</button></span>
            <br/>
            <span class="button bt_s1" id="offLimitBtn2"><button type="button" style="width:100px;">출입제한</button></span>
            <span class="button bt_s2" id="buttonGoList2"><button type="button" style="width:100px;">목록</button></span>
            <span style="display:none;" class="button bt_s1" id="deleteBtn2"><button type="button" style="width:100px;">삭제</button></span>
        </div>
    </div>
    <!-- 버튼 끝 -->
    </form>
</div>
</div>