<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    Map login = new HashMap();
    if (request.getSession().getAttribute("Login") != null) {
        login = (Map) request.getSession().getAttribute("Login");
    }
%>
<script type="text/javascript">

  let gridUtil;

  /***************************************************************************
   * 화면 onload 처리
   ***************************************************************************/
  $(document).ready(function () {

    initGrid();
    initDocument();
  });

  /***************************************************************************
   * Grid initializing
   ***************************************************************************/
  const initGrid = () => {
    gridUtil = new GridUtil({
      url: "/api/entmanage/carPassAdmin/ioCarPassInOutStts/list"
      , ajaxMethod: 'post'
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "요청회사"
            , field: "deptDivNm"
          },
          {
            headerName: "회사그룹코드"
            , field: "deptDivId"
          },
          {
            headerName: "사용"
            , field: "useCnt"
          },
          {
            headerName: "승인대기"
            , field: "waitCnt"
          },
          {
            headerName: "VIP 사용"
            , field: "vipUseCnt"
          },
          {
            headerName: "VIP 승인대기"
            , field: "vipWaitCnt"
          },
          {
            headerName: "임시저장"
            , field: "imsiCnt"
          },
          {
            headerName: "예약가능"
            , field: "availCnt"
          },
          {
            headerName: "할당쿼터"
            , field: "carQta"
          },
          {
            headerName: "총쿼터"
            , field: "totQta"
          },
        ]
      },
      search: {
        formId: "formGrid",
        buttonId: "searchBtn",
      }
    });
  }

  /***************************************************************************
   * Document initializing
   ***************************************************************************/
  const initDocument = async () => {

    // 검색기간 설정
    $("#searchStrtDt").val($.esutils.getToday('-'));
    $.esutils.datepicker(["[id=searchStrtDt]"]);
    gridUtil.init(); // grid 조회
  }


</script>
<!-- 검색영역 시작 -->
<div id="search_area">
    <!-- 검색 테이블 시작 -->
    <div class="search_content">
        <form id="formGrid" name="formGrid" method="post">
            <table cellpadding="0" cellspacing="0" caption="조회화면입니다" border="0" class="view_board01">
                <tbody>
                <tr>
                    <th style="width:73px;">예약일시</th>
                    <td>
                        <input type="text" id="searchStrtDt" name="searchStrtDt"/>
                    </td>

                    <th><label>요청회사</label></th>
                    <td>
                        <input type="text" id="searchDeptNm" name="searchDeptNm" style="width:235px;"/>
                    </td>

                </tr>
                </tbody>
            </table>
        </form>
        <!-- 버튼 시작 -->
        <div class="searchGroup">
            <div class="centerGroup">
                <span class="button bt_l1"><button type="button" id="searchBtn" style="width:80px;">검색</button></span>
            </div>
        </div>
        <!-- 버튼 종료 -->
    </div>
</div>
<!-- 검색영역 끝 -->
<div id="grid"></div>