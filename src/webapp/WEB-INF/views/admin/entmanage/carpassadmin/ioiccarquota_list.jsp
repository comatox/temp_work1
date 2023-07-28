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
      url: "/api/entmanage/carPassAdmin/ioIcCarQuota/list"
      , ajaxMethod: 'post'
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "회사코드"
            , field: "deptDivId"
          },
          {
            headerName: "요청회사"
            , field: "deptDivNm"
          },
          {
            headerName: "방문쿼터"
            , field: "qtaTotal"
          },
          {
            headerName: "상시쿼터"
            , field: "regularQta"
          },
          {
            headerName: "일반 / VIP / 남은수"
            , field: "remainCnt"
          },
          {
            headerName: "쿼터변경"
            , field: "changeQta"
            , formatter: (cellValue, options, row) => {
              const {deptDivId} = row;
              return "<a href=\"javascript: fnModQta('" + deptDivId + "');\" class='btn_type01'><span>변경</span>";
            }
          },
          {
            headerName: "삭제"
            , field: "delete"
            , formatter: (cellValue, options, row) => {
              const {deptDivId} = row;
              return "<span onclick=\"javascript: fnDelQta('" + deptDivId + "');\"'><span>X</span>";
            }
          },
        ]
      },
      search: {
        formId: "formGrid",
        buttonId: "searchBtn",
      }
    });
  }

  const fnModQta = (deptDivId) => {
    $.esutils.openPopup({
      url: "/entmanage/carpassadmin/ioiccarquota/popup"
      , param: {deptDivId, type: "M"}
      , width: "600"
      , height: "250"
      , scroll: "no"
      , fnCallback: () => {
        gridUtil.refresh();
      }
    });
  }

  const fnDelQta = (deptDivId) => {
    const param = {
      deptDivId,
      userInfo : {
        empId: global.empId,
        acIp: global.acIp,
      },
    }

    $.esutils.postApi('/api/entmanage/carPassAdmin/ioIcCarQuota/delete', param, (response) => {
      const msg = response.message;
      if (msg === "OK") {
        alert("삭제 되었습니다.");
        gridUtil.refresh();
      } else {
        alert("오류가 발생하였습니다.");
      }
    });
  }

  /***************************************************************************
   * Document initializing
   ***************************************************************************/
  const initDocument = async () => {

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
                    <th><label>코드명</label></th>
                    <td>
                        <input type="text" id="searchDeptDivId" name="searchDeptDivId" style="width:235px;"/>
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