<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

  /***************************************************************************
   * 화면 onload 처리
   ***************************************************************************/
  var agent = navigator.userAgent.toLowerCase();
  var browse = "IE";

  const fnKeyDown = (e) => {
    if (e.key === 'Enter') {
      fnGetCoPop();
    }
  }

  const fnGetCoPop = () => {

    if ($("#useEmpNm").val() == '') {
      alert('신청 대상자 성명을 기입해 주세요');
      return;
    }

    const param = {
      empNm: encodeURL($("#useEmpNm").val()),
      compId: global.compId,
    };

    $.esutils.openEmpPopup({
      ...param,
      fnCallback
    });

  };

  const fnCallback = (response) => {
    console.log("■■ test > fnCallback > response ■■", response);
  }

</script>
<div class="contentWrap">
    <div id="content_area">
        <table width="100%" border="0">
            <tr>
                <td align="left">
                    <img src="/esecurity/assets/common/images/common/subTitle/pass/title_083.png"/>
                </td>
                <td align="right">
                    <div class="buttonGroup">
                        <div class="leftGroup">
                            <span class="button bt_s2"><button type="button" style="width: 50px;" onclick="javascript : fn_list();">목록</button></span>&nbsp;&nbsp;
                            <span class="button bt_s1"><button type="button" style="width: 50px;" onclick="javascript : fn_approval_send('REPORT');">상신</button></span>
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
        <input type="hidden" id="S_TYPE" name="S_TYPE" value=""/>
        <!-- realContents start -->
        <div id="realContents">
            <form id="list_form" name="list_form" method="post">

            </form>
            <form id="pass_form" name="pass_form" method="post" onsubmit="return false;">
                <input type="hidden" id="DOC_ID" name="DOC_ID" value=""/>
                <input type="hidden" id="EMPCARD_APPL_NO" name="EMPCARD_APPL_NO" value=""/>
                <input type="hidden" id="AC_IP" name="AC_IP" value="<%= request.getRemoteAddr() %>"/>

                <input type="hidden" id="CRT_BY" name="CRT_BY" value="\${global.empId ?? ''}"/>
                <input type="hidden" id="MOD_BY" name="MOD_BY" value="\${global.empId ?? ''}"/>

                <input type="hidden" id="APPL_COMP_ID" name="APPL_COMP_ID" value=""/>
                <input type="hidden" id="APPL_EMP_ID" name="APPL_EMP_ID" value=""/>
                <input type="hidden" id="APPL_EMP_NM" name="APPL_EMP_NM" value=""/>
                <input type="hidden" id="APPL_DEPT_ID" name="APPL_DEPT_ID" value=""/>
                <input type="hidden" id="APPL_DEPT_NM" name="APPL_DEPT_NM" value=""/>
                <input type="hidden" id="APPL_JW_NM" name="APPL_JW_NM" value=""/>

                <input type="hidden" id="SCHEMA_NM" name="SCHEMA_NM" value="BUILD_EMPCARD_APPL"/>
                <input type="hidden" id="DOC_NM" name="DOC_NM" value="사원증 건물출입신청"/>
                <%--                    <input type="hidden" id="MENU_ID" 	name="MENU_ID" 	 value="<%= menuId %>" />--%>
                <input type="hidden" id="cnfmUrl" name="cnfmUrl" value="EmpCard/empCardBuildingReg.jsp"/>

                <input type="hidden" id="GATE" name="GATE" value=""/>
                <input type="hidden" id="SPE_GATE_ID" name="SPE_GATE_ID" value=""/>
                <input type="hidden" id="FULL_GATE_IC_ID" name="FULL_GATE_IC_ID" value=""/>
                <input type="hidden" id="FULL_GATE_ID" name="FULL_GATE_ID" value=""/>
                <input type="hidden" id="APPR_EMP_ID" name="APPR_EMP_ID" value=""/>
                <input type="hidden" id="CARD_NO" name="CARD_NO" value=""/>
                <input type="hidden" id="IDCARD_ID" name="IDCARD_ID" value=""/>

                <input type="hidden" ID="COMP_ID" name="COMP_ID" valeu="\${global.compId ?? ''}"/>

                <h1 class="txt_title01 fl">신청대상자</h1>
                <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                    <colgroup>
                        <col width="151"/>
                        <col width="274"/>
                        <col width="151"/>
                        <col width="274"/>
                    </colgroup>
                    <tbody>

                    <tr>
                        <th><label for="useEmpNm">성명</label></th>
                        <td colspan="3"><input type="text" id="useEmpNm" name="useEmpNm" value="" onkeydown="fnKeyDown(event)" style="width:80px;"/><input type="hidden" id="useEmpId" name="useEmpId"
                                                                                                                                                           value=""/><input type="hidden"
                                                                                                                                                                            id="use_comp_id"
                                                                                                                                                                            name="use_comp_id"
                                                                                                                                                                            value=""/><input
                                type="hidden" id="use_div_cd" name="use_div_cd" value=""/><a class="btn_type01" style="margin-left:5px" href="javascript:fnGetCoPop();"> <span>찾기</span> </a></td>
                    </tr>

                    <tr>
                        <th><label for="useCompNm">회사명</label></th>
                        <td colspan="3"><input type="text" id="useCompNm" name="useCompNm" value="회사명" style="width:200px;" readonly="readonly" class="readonly"/>
                    </tr>
                    <tr>
                        <th><label for="useDeptNm">부서명</label></th>
                        <td colspan="3"><input type="text" id="useDeptNm" name="useDeptNm" value="부서명" style="width:200px;" readonly="readonly" class="readonly"/><input type="hidden" id="useDeptId"
                                                                                                                                                                         name="useDeptId"/></td>
                    </tr>
                    </tbody>
                </table>

                <div>
                    <h1 class="txt_title01">결과</h1>
                </div>
                <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                    <colgroup>
                        <col width="151"/>
                        <col width="699"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <td colspan="3"><label for="result">
                            <textarea id="result" style="width: 100%"></textarea>
                        </label>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <br/><br/>
                <%
                    // 결재선  include
                %>

                <!-- 버튼 -->
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s2"><button type="button" style="width: 50px;" onclick="javascript : fn_list();">목록</button></span>&nbsp;&nbsp;
                        <span class="button bt_s1"><button type="button" style="width: 50px;" onclick="javascript : fn_approval_send('REPORT');">상신</button></span>
                    </div>
                </div>
                <!-- 버튼 끝 -->
            </form>
            <!-- realContents end -->
        </div>
    </div>
</div>
