<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession(false);
%>
<html>
<head>
    <script>
      let gridUtil;

      /***************************************************************************
       * 화면 onload 처리
       ***************************************************************************/
      $(document).ready(() => {

        initPopup();
        initGrid();

        //grid init
        gridUtil.init();
      });

      /***************************************************************************
       * Popup initializing
       ***************************************************************************/
      const initPopup = () => {
        let empId, empNm, compId, deptId = "";

        if (urlParam("empId") !== undefined) empId = urlParam("empId");
        if (urlParam("empNm") !== undefined) empNm = urlParam("empNm");
        if (urlParam("compId") !== undefined) compId = urlParam("compId");
        if (urlParam("deptId") !== undefined) deptId = urlParam("deptId");

        if (empId !== "") {
          $("#searchEmpId").val(empId);
        } else {
          $("#searchEmpId").val('');
        }

        if (empNm !== "") {
          $("#searchEmpNm").val(empNm);
          $("#searchEmpId").val('');
        }

        if (compId !== "") {
          $("#searchCompId").val(compId);
        }

        if (deptId !== "") {
          $("#searchDeptId").val(deptId);
        }

        $("#searchEmpId").keypress(() => {
          $(this).val($(this).val().toUpperCase());
        });
      }

      /***************************************************************************
       * Grid initializing
       ***************************************************************************/
      const initGrid = () => {
        gridUtil = new GridUtil({
          url: "/api/common/emp/coEmpExt"
          , gridId: "grid"
          , isPaging: true
          , ajaxMethod: "post"
          , pageSize: 10
          , gridOptions: {
            width: 630,
            // height: 800,
            colData: [
              {
                headerName: "순번"
                , field: "rowNum"
                , width: "10%"
              },
              {
                headerName: "사업장"
                , field: "divNm"
                , width: "10%"
              },
              {
                headerName: "회사명"
                , field: "compNm"
                , width: "10%"
              },
              {
                headerName: "부서코드"
                , field: "deptId"
                , width: "10%"
              },
              {
                headerName: "부서명"
                , field: "deptNm"
              },
              {
                headerName: "사용자명"
                , field: "empNm"
                , width: "10%"
              },
              {
                headerName: "사번"
                , field: "empId"
                , width: "10%"
              },
              {
                headerName: "E-mail",
                field: "email",
                width: "10%"
              },
            ]
            , onRowClicked
          },
          search: {
            formId: "findEmployee",
            buttonId: "searchBtn"
          }
        });
      }

      // Callback Function
      const onRowClicked = (response) => {
        if (window.fnParentCallback) {
          window.fnParentCallback(response);
          window.close();
        } else {
          alert("잘못된 접근입니다. 팝업화면을 새로 실행해주세요.");
        }
      }

      const urlParam = (name) => {
        let results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results === null) {
          return null;
        } else {
          return decodeURI(results[1]) || 0;
        }
      }
    </script>
</head>
<body>
<!-- popup ::  START -->
<div id="popBody">
    <div id="popArea">
        <div class="pop_title"><h1>사원정보</h1></div>
        <div class="close"><a href="javascript:window.close();"><img src="/esecurity/assets/common/images/common/pop_close.png" alt="창닫기"/></a></div>
    </div>

    <div class="pop_content" style="margin-top:0px;">
        <!-- popup 내용 ::  START -->
        <input type="hidden" name="callback" value=""/>
        <!-- 검색영역 시작 -->
        <input type="hidden" name="searchCompId" id="searchCompId" value=""/>
        <input type="hidden" name="searchDeptId" id="searchDeptId" value=""/>
        <div style="float: left; border: 0px solid black; width:620px;">
            <!-- 검색영역 시작 -->
            <div id="search_area">
                <!-- 검색 -->
                <div class="search_content">
                    <form name="findEmployee" id="findEmployee">
                        <table cellpadding="0" cellspacing="0" caption="조회화면입니다" border="0" class="view_board01">
                            <caption>조회화면</caption>
                            <tr>
                                <th>부서명</th>
                                <td>
                                    <input type="text" name="searchDeptNm" id="searchDeptNm" style="IME-MODE:enable;" value=""/>
                                </td>
                                <th>사원명</th>
                                <td>
                                    <input type="text" name="searchEmpNm" id="searchEmpNm" style="IME-MODE:enable;" value=""/>
                                </td>
                            </tr>
                            <tr>
                                <th>사번</th>
                                <td colspan="3">
                                    <input type="text" name="searchEmpId" id="searchEmpId" style="IME-MODE:disabled;text-transform:uppercase" maxlength="10"
                                           value=""/>
                                </td>
                            </tr>
                        </table>

                        <!-- 버튼 -->
                        <div class="searchGroup">
                            <div class="centerGroup">
                                <span class="button bt_l1"><button id="searchBtn" type="button" style="width:80px;">검색</button></span>
                            </div>
                        </div>
                        <!-- 버튼 끝 -->
                    </form>
                </div>
            </div>
        </div>
        <!-- 검색영역 종료 -->
        <div style="clear:left;"></div>
        <!-- 그리드 영역 시작 -->
        <div id="grid"></div>
        <!-- 그리드 영역 종료 -->
    </div>
</div>
</body>
</html>