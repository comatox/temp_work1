<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script>
  $(document).ready(function () {
    const scCorrPlanNo = "${param.scCorrPlanNo}" === "null" ? "" : "${param.scCorrPlanNo}";
    const docId = "${param.docId}" === "null" ? "" : "${param.docId}";
    const scDocNo = "${param.scDocNo}" === "null" ? "" : "${param.scDocNo}";
    const writeYn = "${param.writeYn}";

    $.esutils.renderData("rectifyPlanForm",
        '/api/secrtactvy/securityRectifyPlan/view?scDocNo=' + scDocNo + '&scCorrPlanNo=' + scCorrPlanNo + '&docId=' + docId,
        (data) => {
          if (data.actDo === "C0280003") {
            $("#corrPlanTxt").text("경고장 개선계획");
            $("#empNmReadTd").show();
          } else if (data.actDo === "C0280004") {
            if (writeYn === "N") {
              $("#corrPlanTxt").text("징계의뢰 계획서 팀장 작성 후 승인 요청");
            } else {
              $("#corrPlanTxt").text("징계의뢰 계획서");
            }
            $("#empNmReadTd").show();
          } else {
            $("#empNmReadTd").hide();
          }

          if (data.pointContent !== "") {
            let pointContent = data.pointContent;
            pointContent = pointContent.replace(/(\n|\r\n)/g, '<br/>');
            $("#pointContent").text(pointContent);
          }
          if (data.etcContent !== "") {
            let etcContent = data.etcContent;
            etcContent = etcContent.replace(/(\n|\r\n)/g, '<br/>');
            $("#etcContent").text(etcContent);
          }
          if (data.imprContent !== "") {
            let imprContent = data.imprContent;
            imprContent = imprContent.replace(/(\n|\r\n)/g, '<br/>');
            $("#imprContent").text(imprContent);
          }

        }, {loading: true}
    )

    esecurityApproval = new EsecurityApproval("approvalWrap");
    esecurityApproval.view(docId);
  });

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
                <table width="100%" border="0">
                    <tr>
                        <td align="left">
                            <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_312.png"/>
                        </td>
                        <td align="right">
                            <div class="buttonGroup">
                                <div class="leftGroup">
                                    <span class="button bt_s2"><button type="button" style="width:100px;" onclick="rectifyplanList();">목록</button></span>
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

                <div id="realContents">
                    <form id="rectifyPlanForm" name="rectifyPlanForm" method="post">
                        <h1 class="txt_title01">위규 정보</h1>
                        <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                            <colgroup>
                                <col width="151"/>
                                <col width="274"/>
                                <col width="151"/>
                                <col width="274"/>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>적발일</th>
                                <td id="ofendDt" view-data="ofendDt">
                                </td>
                                <th>적발시각</th>
                                <td id="ofendTm" view-data="ofendTm">
                                </td>
                            </tr>
                            <tr>
                                <th>성명</th>
                                <td id="ofendEmpNm" view-data="ofendEmpNm"></td>
                                <th>연락처</th>
                                <td id="ofendTelNo" view-data="ofendTelNo"></td>
                            </tr>
                            <tr>
                                <th>사번</th>
                                <td id="ofendEmpId" view-data="ofendEmpId" style='width:213px;'></td>
                                <th>소속</th>
                                <td id="ofendDeptNm" view-data="ofendDeptNm"></td>
                            </tr>
                            <tr>
                                <th>위규 내용</th>
                                <td colspan="3" id="ofendDetailGbnNm" view-data="ofendDetailGbnNm"></td>
                            </tr>
                            <tr>
                                <th>위규 내용 상세</th>
                                <td colspan="3" id="ofendSubGbnNm" view-data="ofendSubGbnNm"></td>
                            </tr>
                            <tr>
                                <th>적발 사업장</th>
                                <td id="actCompNm" view-data="actCompNm"></td>
                                <th>적발 건물</th>
                                <td id="actBldgNm" view-data="actBldgNm"></td>
                            </tr>
                            <tr>
                                <th>적발 건물 상세</th>
                                <td id="actLocateNm" view-data="actLocateNm"></td>
                                <th><span>적발 GATE&nbsp;</span></th>
                                <td id="actGate" view-data="actGate"></td>
                            </tr>
                            </tbody>
                        </table>
                        <h1 class="txt_title01" id="corrPlanTxt">시정계획서</h1>
                        <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
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
                                <td colspan="5" id="pointContent" view-data="pointContent">
                                </td>
                            </tr>
                            <tr>
                                <th>구체적 개선내용</th>
                                <td colspan="5" id="imprContent" view-data="imprContent">
                                </td>
                            </tr>
                            <tr>
                                <th>기타의견</th>
                                <td colspan="5" id="etcContent" view-data="etcContent">
                                </td>
                            </tr>
                            <tr id="empNmReadTd">
                                <th>팀장</th>
                                <td id="kaEmpNm" view-data="kaEmpNm"></td>
                                <th>사업부장</th>
                                <td id="gaEmpNm" view-data="gaEmpNm"></td>
                                <th>대표이사</th>
                                <td id="aaEmpNm" view-data="aaEmpNm"></td>
                            </tr>
                            </tbody>
                        </table>
                        <!-- 버튼 -->
                        <div class="buttonGroup">
                            <div class="leftGroup">
                                <span class="button bt_s2"><button type="button" style="width:100px;" onclick="rectifyplanList();">목록</button></span>
                            </div>
                        </div>
                        <!-- 버튼 끝 -->
                    </form>
                </div>
            </div>
            <div id="approvalWrap"></div>
        </div>
    </div>
</div>