<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script>
  let esecurityApproval;
  const scCorrPlanNo = ${param.scCorrPlanNo};
  const docId = ${param.docId};
  const scDocNo = ${param.scDocNo};
  const docNm = "시정계획서";
  const cnfmUrl = "CorrPlan/corrPlan.jsp";
  const corrPlanSendYn = "C0101001";

  $(document).ready(function () {

    $.esutils.renderData("rectifyPlanForm",
        '/api/secrtactvy/securityRectifyPlan/view?scDocNo=' + scDocNo + '&scCorrPlanNo=' + scCorrPlanNo + '&docId=' + docId,
        (data) => {

          $("#ofendDt").text(data.ofendDt);
          $("#ofendTm").text(data.ofendTm);
          $("#ofendEmpId").text(data.ofendEmpId);
          $("#ofendEmpNm").text(data.ofendEmpNm);
          $("#ofendDeptNm").text(data.ofendDeptNm);
          $("#ofendDetailGbnNm").text(data.ofendDetailGbnNm);
          $("#ofendSubGbnNm").text(data.ofendSubGbnNm);
          $("#actCompNm").text(data.actCompNm);
          $("#actBldgNm").text(data.actBldgNm);
          $("#actLocateNm").text(data.actLocateNm);
          $("#actGate").text(data.actGate);
          $("#actDo").val(data.actDo);

          if (data.actDo === "C0280003") {
            $("#corrPlanTxt").html("경고장 개선계획");
            $("#corrContentTxt").html("구체적 개선내용<span class='necessary'></span>");
            $("#docNm").val("경고장 개선계획");

            $("#titleImg").html("<img src=\"/esecurity/assets/common/images/common/subTitle/secrt/title_357.png\"/>");
          } else if (data.actDo === "C0280004") {
            $("#corrPlanTxt").html("징계의뢰 계획서");
            $("#corrContentTxt").html("추진 계획<span class='necessary'></span>");
            $("#docNm").val("징계의뢰 계획서");

            $("#titleImg").html("<img src=\"/esecurity/assets/common/images/common/subTitle/secrt/title_357.png\"/>");
            addRequestApprLine(data.ofendCompId, data.aaDeptId, data.aaDeptNm, data.aaJwId, data.aaJwNm, data.aaEmpId, data.aaEmpNm);
          } else {
            $("#titleImg").html("<img src=\"/esecurity/assets/common/images/common/subTitle/secrt/title_357.png\"/>");
          }

          $("#ofendDetailGbn").val(data.ofendDetailGbn);
          $("#ofendCompId").val(data.ofendCompId);
          $("#actCompId").val(data.actCompId);
          $("#telNo").val(data.ofendTelNo);

          if (data.pointContent !== "") {
            $("#pointContent").val(data.pointContent);
          }
          if (data.etcContent !== "") {
            $("#etcContent").val(data.etcContent);
          }
          if (data.imprContent !== "") {
            $("#imprContent").val(data.imprContent);
          }

        }, {loading: true}
    )

    esecurityApproval = new EsecurityApproval("approvalWrap");
    esecurityApproval.form({schemaNm: "CORR_PLAN", menuId: "P03010402"});
  });

  function validationCheck() {
    let pointContent = $("#pointContent").val();
    let imprContent = $("#imprContent").val();
    let etcContent = $("#etcContent").val();

    if (pointContent === "") {
      alert("내용을 입력해주십시오.");
      $("#pointContent").focus();
      return false;
    }
    if (pointContent !== "") {
      if (pointContent.length > 600) {
        alert("내용을 600자 이하로 입력해주십시오.");
        $("#pointContent").focus();
        return false;
      }
    }
    if (imprContent === "") {
      alert("구체적 개선 내용을 입력해주십시오.");
      $("#imprContent").focus();
      return false;
    }
    if (imprContent !== "") {
      if (imprContent.length > 600) {
        alert("구체적 개선 내용을 600자 이하로 입력해주십시오.");
        $("#imprContent").focus();
        return false;
      }
    }
    if (etcContent === "") {
      alert("기타의견을 입력해주십시오.");
      $("#etcContent").focus();
      return false;
    }
    if (etcContent !== "") {
      if (etcContent.length > 600) {
        alert("기타의견을 600자 이하로 입력해주십시오.");
        $("#etcContent").focus();
        return false;
      }
    }
    return true;
  }

  function rectifyPlanSave() {
    let corrPlanTxt = "시정계획서";

    if ($("#actDo").val() === "C0280003") {
      corrPlanTxt = "경고장 개선계획";
    } else if ($("#actDo").val() === "C0280004") {
      corrPlanTxt = "징계의뢰 계획서";
    }

    if (validationCheck()) {
      const param = $.esutils.getFieldsValue($('#rectifyPlanForm'));
      const mergedParam = {
        ...param
        , ...esecurityApproval.getApproval()
        , acIp: global.acIp
        , empId: global.empId
        , scDocNo: scDocNo
        , scCorrPlanNo: scCorrPlanNo
        , docNm: docNm
        , cnfmUrl: cnfmUrl
        , corrPlanSendYn: corrPlanSendYn
      };
      $.esutils.postApi("/api/secrtactvy/securityRectifyPlan", mergedParam, function (result) {
        if (result && result.data) {
          alert(corrPlanTxt + "를 상신하였습니다.");
          //리스트로 돌아가기.
          rectifyplanList();
        } else {
          alert(corrPlanTxt + " 저장 중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      });
    }
  }

  // 징계의뢰 계획서(C0280004) 일 경우 지정된(각 회사 내부인원) 대표이사 또는 상급자가 결재 처리 될수 있게 수정
  function addRequestApprLine(compId, apprDeptId, apprDeptNm, apprJwId, apprJwNm, apprEmpId, apprEmpNm) {
    let appendData = [];

    //test code - 20230727
    appendData.push({
      apprDeptGbn: "1",
      compId: '1101000001',
      apprEmpId: '9211235',
      apprEmpNm: '이필민',
      apprJwId: 'TL',
      apprJwNm: 'TL',
      apprDeptId: '11010033',
      apprDeptNm: '경영관리팀',
      autoSign: '0',
      subcontYn: 'N'
    });

    //read code - 20230727
    /*appendData.push({
      apprDeptGbn: "1",
      compId: compId,
      apprEmpId: apprEmpId,
      apprEmpNm: apprEmpNm,
      apprJwId: apprJwId,
      apprJwNm: apprJwNm,
      apprDeptId: apprDeptId,
      apprDeptNm: apprDeptNm,
      autoSign: '0',
      subcontYn: 'N'
    });*/

    // 1 : 요청부서 / 2 : 허가부서
    esecurityApproval.renderApprLine("1", appendData, true);
    esecurityApproval.addRequest(appendData);
  }

  /********************************
   * 목록 이동
   ********************************/
  const rectifyplanList = function () {
    $.esutils.href('/secrtactvy/securityrectifyplan/rectifyplan/list')
  };

</script>

<!-- wrap start-->
<div class="wrap">
    <!-- contentWrap start-->
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <!-- <h1 class="title">시정계획서 작성 </h1> -->
                <table width="100%" border="0">
                    <tr>
                        <td align="left">
                            <div id="titleImg">
                                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_312.png"/>
                            </div>
                        </td>
                        <td align="right">
                            <div class="buttonGroup">
                                <div class="leftGroup">
                                    <span class="button bt_s2"><button type="button" style="width:100px;" onclick="rectifyplanList();">취소</button></span>
                                    <span class="button bt_s1"><button type="button" style="width:100px;" onclick="javascript: fn_approval('REPORT');">상신</button></span>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <img src="/esecurity/assets/common/images/common/line.png" width="100%" height="3"/>
                        </td>
                    </tr>
                </table>

                <!-- realContents start -->
                <div id="realContents">
                    <form id="rectifyPlanForm" name="rectifyPlanForm" method="post">
                        <h1 id="subCont_Title" class="txt_title01">위규정보</h1>
                        <table id="securityTable" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                            <colgroup>
                                <col width="151"/>
                                <col width="274"/>
                                <col width="151"/>
                                <col width="274"/>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>적발일</th>
                                <td id="ofendDt">
                                </td>
                                <th>적발시각</th>
                                <td id="ofendTm">
                                </td>
                            </tr>
                            <tr>
                                <th>성명</th>
                                <td id="ofendEmpNm"></td>
                                <th>연락처<span class="necessary">&nbsp;</span></th>
                                <td><input type='text' id='telNo' name='telNo' style='width:85%'/></td>
                            </tr>
                            <tr>
                                <th>사번</th>
                                <td id="ofendEmpId" style='width:213px;'></td>
                                <th>소속</th>
                                <td id="ofendDeptNm"></td>
                            </tr>
                            <tr>
                                <th>위규 내용</th>
                                <td colspan="3" id="ofendDetailGbnNm"></td>
                            </tr>
                            <tr>
                                <th>위규 내용 상세</th>
                                <td colspan="3" id="ofendSubGbnNm"></td>
                            </tr>
                            <tr>
                                <th>적발 사업장</th>
                                <td id="actCompNm"></td>
                                <th>적발 건물</th>
                                <td id="actBldgNm"></td>
                            </tr>
                            <tr>
                                <th>적발 건물 상세</th>
                                <td id="actLocateNm"></td>
                                <th><span>적발 GATE&nbsp;</span></th>
                                <td id="actGate"></td>
                            </tr>
                            </tbody>
                        </table>
                        <h1 class="txt_title01" id="corrPlanTxt">시정계획서</h1>
                        <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                            <colgroup>
                                <col width="151"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>내용<span class="necessary">&nbsp;</span></th>
                                <td>
                                    <textarea id="pointContent" name="pointContent" style="width: 95%; height: 70px; overflow: auto;"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th id="corrContentTxt">구체적 개선내용<span class="necessary">&nbsp;</span></th>
                                <td>
                                    <textarea id="imprContent" name="imprContent" style="width: 95%; height: 70px; overflow: auto;"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th>기타의견<span class="necessary">&nbsp;</span></th>
                                <td>
                                    <textarea id="etcContent" name="etcContent" style="width: 95%; height: 70px; overflow: auto;"></textarea>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div id="approvalWrap"></div>
                        <!-- 버튼 -->
                        <div class="buttonGroup">
                            <div class="leftGroup">
                                <span class="button bt_s2"><button type="button" style="width:100px;" onclick="rectifyplanList();">취소</button></span>
                                <span class="button bt_s1"><button type="button" style="width:100px;" onclick="rectifyPlanSave();">상신</button></span>
                            </div>
                        </div>
                        <!-- 버튼 끝 -->
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>