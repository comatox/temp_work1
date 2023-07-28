<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    $.esutils.rangepicker([["[name=crtDtmFr]", "[name=crtDtmTo]"]]);

    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityEduPledge/admList"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "보안교육내용"
            , field: "secEduTitle"
            // , formatter: cellValue => cellValue === "Y" ? "탈퇴" : cellValue === "H" ? "승인대기" : cellValue === "Y" ? "휴면" : ""
          },
          {
            headerName: "사번"
            , field: "empId"
          },
          {
            headerName: "성명"
            , field: "empNm"
          },
          {
            headerName: "업체명"
            , field: "compNm"
          },
          {
            headerName: "진도율"
            , field: "finishRate"
          },
          {
            headerName: "이수 상태"
            , field: "finishYn"
          },
          {
            headerName: "수기 이수"
            , field: "manualYn"
          },
          {
            headerName: "이수일자"
            , field: "finishDt"
          },
          {
            headerName: "최초 발신일자"
            , field: "secEduFirstSendDt"
          },
          {
            headerName: "최종 발신일자"
            , field: "secEduLastSendDt"
          },
        ]
        , multiselect: true
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    //grid init
    gridUtil.init();
  });

  function fnInCompleteSendMail(type) {
    <%--var empNmArr = [];--%>
    <%--var emailArr = [];--%>
    <%--var eduNmArr = [];--%>
    <%--var empIdArr = [];--%>
    <%--var jwNmArr = [];--%>
    <%--var compNmArr = [];--%>
    <%--var tempCnt = 0;--%>
    <%--var cMsg = confirm("미이수자에게 메일을 전송하시겠습니까?");--%>
    <%--if (!cMsg) {--%>
    <%--  return;--%>
    <%--}--%>
    <%--if (type == "P") {--%>
    <%--  if ($("#educationTable tbody input[type='checkbox']:checked").length < 1) {--%>
    <%--    alert("메일을 발송하는 대상을 선택해주세요.");--%>
    <%--    return;--%>
    <%--  }--%>
    <%--  $("#educationTable tbody input[type='checkbox']:checked").each(function () {--%>
    <%--    empNmArr.push($(this).attr("empNm"));--%>
    <%--    emailArr.push($(this).attr("email"));--%>
    <%--    //emailArr.push((tempCnt==0?"rhckehf@amitok.com":"lsjgma@skhystec.com"));--%>
    <%--    eduNmArr.push($(this).attr("eduNm"));--%>
    <%--    jwNmArr.push($(this).attr("jwNm"));--%>
    <%--    empIdArr.push($(this).attr("empId"));--%>
    <%--    compNmArr.push($(this).attr("compNm"));--%>
    <%--    tempCnt++;--%>
    <%--  });--%>
    <%--} else {--%>
    <%--  if ($("#COMP_NM").val() == "SK하이이엔지(주)") {--%>
    <%--    alert("SK하이이엔지(주)는 개별 발송으로 처리하여주세요.");--%>
    <%--    return;--%>
    <%--  }--%>
    <%--  if ($("#COMP_NM").val() == "") {--%>
    <%--    alert("전체 발송은 업체를 선택 후 가능합니다.");--%>
    <%--    return;--%>
    <%--  }--%>
    <%--}--%>

    <%--var Params = {--%>
    <%--  nc_trId: "fmSecurityEducationIncompleteSendMail",--%>
    <%--  callback: "",--%>
    <%--  EMA IL: emailArr,--%>
    <%--  EMP_NM: empNmArr,--%>
    <%--  EDU_NM: eduNmArr,--%>
    <%--  EMP_ID_ARR: empIdArr,--%>
    <%--  COMP_NM: compNmArr,--%>
    <%--  JW_NM: jwNmArr,--%>
    <%--  SCHEMA_NM: "SEC_EDU",--%>
    <%--  EMP_ID: "9210913",--%>
    <%--  sendFromId: "9111434",--%>
    <%--  title: "[입주사-Security]보안교육 미이수 안내메일",--%>
    <%--  SEND_TYPE: type,--%>
    <%--  EDU_TITLE: $("#SEC_EDU_TITLE").val(),--%>
    <%--  COMPANY: $("#COMP_NM").val()--%>
    <%--};--%>

    <%--$.ajax({--%>
    <%--  url: "${root}/main.json",--%>
    <%--  data: JSON.parse(JSON.stringify(Params)),--%>
    <%--  type: "post",--%>
    <%--  dataType: "text",--%>
    <%--  success: function (jsondata, stat) {--%>
    <%--    var obj = JSON.parse(jsondata);--%>
    <%--    if (obj.fields.STATUS == "success") {--%>
    <%--      alert("발송되었습니다.");--%>
    <%--    } else {--%>
    <%--      alert("에러가 발생하였습니다.");--%>
    <%--    }--%>
    <%--  }--%>
    <%--});--%>
  }

  function fnInCompleteManualComplete() {
    <%--var empNmArr = [];--%>
    <%--var emailArr = [];--%>
    <%--var eduNmArr = [];--%>
    <%--var empIdArr = [];--%>
    <%--var jwNmArr = [];--%>
    <%--var compNmArr = [];--%>
    <%--var tempCnt = 0;--%>
    <%--var eduTitle = "";--%>
    <%--var cMsg = confirm("교육 수기이수로 처리 하시겠습니까?");--%>
    <%--var finishFlag = false;--%>
    <%--if (!cMsg) {--%>
    <%--  return;--%>
    <%--}--%>

    <%--if ($("#educationTable tbody input[type='checkbox']:checked").length < 1) {--%>
    <%--  alert("수기이수 대상을 선택해주세요.");--%>
    <%--  return;--%>
    <%--}--%>
    <%--$("#educationTable tbody input[type='checkbox']:checked").each(function () {--%>
    <%--  empNmArr.push($(this).attr("empNm"));--%>
    <%--  emailArr.push($(this).attr("email"));--%>
    <%--  eduNmArr.push($(this).attr("eduNm"));--%>
    <%--  jwNmArr.push($(this).attr("jwNm"));--%>
    <%--  empIdArr.push($(this).attr("empId"));--%>
    <%--  compNmArr.push($(this).attr("compNm"));--%>
    <%--  eduTitle = $(this).attr("eduTitle");--%>

    <%--  if ($(this).attr("finishYn") == "Y") {--%>
    <%--    alert("교육 수료 대상이 포함되어 있습니다.");--%>
    <%--    finishFlag = true;--%>
    <%--    return;--%>
    <%--  }--%>
    <%--});--%>

    <%--if (finishFlag) {--%>
    <%--  return;--%>
    <%--}--%>
    <%--var Params = {--%>
    <%--  nc_trId: "fmSecurityEducationIncompleteManualComplete",--%>
    <%--  callback: "",--%>
    <%--  EMAIL: emailArr,--%>
    <%--  EMP_NM: empNmArr,--%>
    <%--  EDU_NM: eduNmArr,--%>
    <%--  EMP_ID_ARR: empIdArr,--%>
    <%--  COMP_NM: compNmArr,--%>
    <%--  JW_NM: jwNmArr,--%>
    <%--  SCHEMA_NM: "SEC_EDU",--%>
    <%--  EMP_ID: "9210913",--%>
    <%--  sendFromId: "9111434",--%>
    <%--  title: "[입주사-Security]보안교육 미이수 안내메일",--%>
    <%--  EDU_TITLE: eduTitle,--%>
    <%--  COMPANY: $("#COMP_NM").val()--%>
    <%--};--%>

    <%--$.ajax({--%>
    <%--  url: "${root}/main.json",--%>
    <%--  data: JSON.parse(JSON.stringify(Params)),--%>
    <%--  type: "post",--%>
    <%--  dataType: "text",--%>
    <%--  success: function (jsondata, stat) {--%>
    <%--    var obj = JSON.parse(jsondata);--%>
    <%--    if (obj.fields.STATUS == "success") {--%>
    <%--      alert("완료되었습니다.");--%>
    <%--    } else {--%>
    <%--      alert("에러가 발생하였습니다.");--%>
    <%--    }--%>
    <%--  }--%>
    <%--});--%>
  }
</script>
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <colgroup>
                    <col width="18%"/>
                    <col width="43%"/>
                    <col width="15%"/>
                    <col width="24%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>보안교육내용</label></th>
                    <td name="secEduTitle">
                        <select id="secEduTitle" name="secEduTitle" style="width:250px;">
                            <option value="">
                            </option>
                        </select>
                    </td>
                    <th><label>상주구분</label></th>
                    <td name="RESIDE_YN">
                        <input type="radio" name="resideYn" value="" class="border_none" checked="checked"/><label>전체</label>
                        <input type="radio" name="resideYn" value="Y" class="border_none"/><label>상주</label>
                        <input type="radio" name="resideYn" value="N" class="border_none"/><label>비상주</label>
                    </td>
                </tr>
                <tr>
                    <th><label>성명</label></th>
                    <td name="EMP_NM">
                        <input type="text" id="empNm" name="empNm" style="width: 235px;" value="" onkeypress="javascript: fn_keypress(event);"/>
                    </td>
                    <th><label>재직구분</label></th>
                    <td name="HT_CD">
                        <input type="radio" name="htCd" value="" class="border_none" checked="checked"/><label>전체</label>
                        <input type="radio" name="htCd" value="C" class="border_none"/><label>재직</label>
                        <input type="radio" name="htCd" value="H" class="border_none"/><label>휴직</label>
                    </td>
                </tr>

                <tr>
                    <th><label>업체명</label></th>
                    <td name="searchComp">
                        <!--                                                    <input type="text" id="COMP_NM" name="COMP_NM" style="width: 235px;" value="" onkeypress="javascript: fn_keypress(event);"/>-->
                        <select id="compNm" name="compNm" style="width:250px;">
                            <option value="">
                            </option>
                        </select>
                    </td>
                    <th><label>이수일자</label></th>
                    <td>
                        <input type="text" id="finishDtSt" name="finishDtSt" style="width: 70px;" value=""/>
                        <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgApplyStrtDt"/>
                        ~
                        <input type="text" id="finishDtEd" name="finishDtEd" style="width: 70px;" value=""/>
                        <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgApplyEndDt"/>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td></td>
                    <th><label>이수 상태</label></th>
                    <td name="finishYn">
                        <input type="radio" name="finishYn" value="" class="border_none" checked="checked"/><label>전체</label>
                        <input type="radio" name="finishYn" value="Y" class="border_none"/><label>이수</label>
                        <input type="radio" name="finishYn" value="N" class="border_none"/><label>미이수</label>
                        <input type="radio" name="finishYn" value="M" class="border_none"/><label>수기이수</label>
                    </td>
                </tr>
                <tr>
                    <th><label>미이수자안내메일 개별발송</label></th>
                    <td name="SEARCH_COMP">
                        <span class="button bt_l1"><button type="button" style="width:50px;" onclick="fnInCompleteSendMail('P');">발송</button></span>
                    </td>
                    <th><label>미이수자안내메일 전체발송</label></th>
                    <td>
                        <span class="button bt_l1"><button type="button" style="width:50px;" onclick="fnInCompleteSendMail('A');">발송</button></span>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button type="button" id="searchBtn" style="width:50px;">검색</button></span>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->