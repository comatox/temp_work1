<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map login = (Map) request.getSession().getAttribute("Login");
%>
<script type="text/javascript">

  let gridUtil;

  $(document).ready(function () {

    gridUtil = new GridUtil({
      url: "/api/sysmanage/authManage/userAuthList"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "회사명"
            , field: "compNm"
          },
          {
            headerName: "부서명"
            , field: "deptNm"
          },
          {
            headerName: "사원명"
            , field: "empNm"
          },
          {
            headerName: "사번"
            , field: "empId"
          },
          {
            headerName: "부여 권한 수"
            , field: "authCnt"
          },
        ]
        , onRowClicked: viewPopup
      }
      , search: {
        formId: "userAuthManageForm"
        , buttonId: "searchBtn"
      }
    });

    // 권한 확인
    const isAdminAuth = $.esutils.checkAuth("1", "<%=login.get("AUTH")%>");

    // 데이터 기본값 세팅
    defaultDataSet(isAdminAuth);

    // 회사명 리스트, 권한 리스트 조회
    renderSelectBox(isAdminAuth, function () {
      // grid Init
      gridUtil.init();
    });

  });

  /*********************************
   * [회사명, 권한 SelectBox Render]
   *********************************/
  function renderSelectBox(isAdminAuth, fnCallBack) {
    const authSelectTargetId = "searchAuth";
    const compSelectTargetId = "searchCompId";

    $.esutils.renderCode([
      {
        targetId: authSelectTargetId,
        url: "/api/sysmanage/authManage/authList",
        param: {authKnd: "1"},
        nameProp: "authNm",
        valueProp: "authId",
        type: "select"
      },
      {
        targetId: compSelectTargetId,
        url: "/api/common/organization/comp",
        nameProp: "compNm",
        valueProp: "compId",
        type: "select",
        blankOption: "전체",
        filter: (d) => d.compId !== '1100000001'
      }
    ], function () {
      // 권한 Select에 전체추가
      $("#" + authSelectTargetId).prepend('<option value="ALL" selected>전체</option>');

      // 관리자가 아닐시 회사명 선택 후 disabled
      if (!isAdminAuth) {
        $("#" + compSelectTargetId).val(global.compId).attr("selected", "selected");
        $("#" + compSelectTargetId).not(":selected").attr("disabled", "disabled");
      }

      fnCallBack();
    });
  }

  /*************************
   * [권한 부여 목록 popup]
   *************************/
  function viewPopup({originRowData}) {
    const param = {searchEmpId: originRowData.empId, managementKnd: $("#managementKnd").val(), authKnd: "1"};

    $.esutils.openPopup({
      url: "/sysmanage/authmanage/userauth/userauthpopup"
      , param
      , width: "650"
      , height: "600"
      , fnCallback: function () {
        // grid Reload
        gridUtil.searchData({pageIndex: 1});
      }
    });

  }

  /*******************
   * [데이터 기본값 저장]
   *******************/
  function defaultDataSet(isAdminAuth) {

    if (isAdminAuth) {
      $("#apprAuth").val("true");
      $("#managementKnd").val("1");
    } else {
      $("#apprAuth").val("false");
      $("#managementKnd").val("2");
    }

  }


</script>

<img src="/esecurity/assets/common/images/common/subTitle/envrSetup/tit_331.png"/>
<!-- realContents start -->
<div id="realContents">
    <form id="userAuthManageForm" name="userAuthManageForm">

        <input type="hidden" id="apprAuth" name="apprAuth" value=""/>
        <input type="hidden" id="managementKnd" name="managementKnd" value=""/>
        <input type="hidden" id="searchAuthKnd" name="searchAuthKnd" value="1"/>

        <!-- 검색영역 시작 -->
        <div id="search_area">
            <!-- 검색 -->
            <div class="search_content">
                <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                    <tr>
                        <th class="searchTitle">
                            사업장
                        </th>
                        <td class="searchText">
                            <select name="searchDivCd" id="searchDivCd" style="width:292px;">
                                <option value="" selected="selected">전체</option>
                                <option value="YP">이천</option>
                                <option value="CP">청주</option>
                                <option value="AP">서울</option>
                                <option value="S20">분당(서현)</option>
                                <option value="S2">분당(정자)</option>
                            </select>
                        </td>
                        <td colspan="2">
                        </td>
                    </tr>
                    <tr>
                        <th class="searchTitle">
                            회사명
                        </th>
                        <td class="searchText">
                            <select id=searchCompId name="searchCompId" style="width:292px;">
                            </select>
                        </td>
                        <th class="searchTitle">
                            사번
                        </th>
                        <td class="searchText">
                            <input type="text" name="searchEmpId" id="searchEmpId" style="width:280px;"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="searchTitle">
                            사원명
                        </th>
                        <td class="searchText">
                            <input type="text" name="searchEmpNm" id="searchEmpNm" style="width:280px;"/>
                        </td>
                        <th class="searchTitle">
                            부서명
                        </th>
                        <td class="searchText">
                            <input type="text" name="searchDeptNm" id="searchDeptNm" style="width:280px;"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="searchTitle">
                            권한
                        </th>
                        <td colspan="3" class="searchText">
                            <select name="searchAuth" id="searchAuth" style="width:292px;">
                                <option value="ALL" selected="selected">전체</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <!-- 검색영역 끝 -->
                <!-- 버튼 -->
                <div class="searchGroup">
                    <div class="centerGroup">
                        <span class="button bt_l1"><button id="searchBtn" type="button" style="width:80px;">검색</button></span>
                    </div>
                </div>
                <!-- 버튼 끝 -->
            </div>
        </div>
    </form>
</div>

<div id="grid"></div>