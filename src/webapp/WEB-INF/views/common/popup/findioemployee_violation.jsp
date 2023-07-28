<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession(false);
%>
<html>
<head>
    <title>(팝업)회원정보 검색</title>
    <script>
        let gridUtil;
        $(document).ready(function() {

          //기본 데이터 세팅
          defaultDataSet();

          let url = "/api/common/ioComp/ioEmp";
          if($("#onedaySubcontYn").val() === "Y"){url = "/api/common/ioComp/ioEmp/subcont"}

          gridUtil = new GridUtil({
            url: url
            , isPaging : true
            , pageSize : 5
            , gridOptions: {
              width: 630
              , colData: [
                {
                  headerName: "도급업체"
                  , field: "subContYn"
                  , width : "10%"
                },
                {
                  headerName: "소속업체"
                  , field: "compKoNm"
                  , width : "10%"
                },
                {
                  headerName: "직위"
                  , field: "ioJwNm"
                  , width : "10%"
                },
                {
                  headerName: "성명"
                  , field: "ioEmpNm"
                  , width : "10%"
                },
                {
                  headerName: "ID"
                  , field: "ioEmpId"
                  , width : "10%"
                },
                {
                  headerName: "휴대폰전화번호"
                  , field: "ioHpNo"
                  , width : "10%"
                },
              ]
              , onRowClicked
            }
            , search: {
              formId: "ioempnmSearch"
              ,buttonId : "searchBtn"
            }
          });


          // grid init
          gridUtil.init();
        });

        // Callback Function
        const onRowClicked = (response) => {
          window.fnParentCallback(response);
          window.close();
        }

        // get Param By QueryString
        const urlParam = (name) => {
          let results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
          if (results === null) {
            return null;
          } else {
            return decodeURI(results[1]) || 0;
          }
        }

        /*******************************
         * [기본 데이터 세팅]
         *******************************/
        function defaultDataSet() {
          $("#ioCompNm").val(urlParam("compNm") ?? "");
          $("#ioEmpNm").val(urlParam("empNm") ?? "");
          $("#onedaySubcontYn").val(urlParam("onedaySubcontYn") ?? "")
        }

    </script>
</head>

<body>
<!-- popup ::  START -->
<div id="popBody">
    <div id="popArea">
        <div class="pop_title"><h1>회원정보 검색</h1></div>
        <div class="close"><a href="javascript:window.close();">
            <img src="/esecurity/assets/common/images/common/pop_close.png" alt="회원정보 검색 창 닫기" /></a></div>

    </div>

    <div class="pop_content">

        <!-- 검색영역 시작 -->

        <div style="float: left; border: 0px solid black; width:700px;margin-left:10px;">
            <div id="search_area">
                <!-- 검색 -->
                <div class="search_content">
                    <form id="ioempnmSearch" name="ioempnmSearch">
                        <input type="hidden" name="callback" value = "" />
                        <input type="hidden" id="ioCompId" name="ioCompId" value="" />
                        <input type="hidden" id="ioEmpId" name="ioEmpId" value="" />
                        <input type="hidden" id="photoChk" name="photoChk" value="" />
                        <input type="hidden" id="planChk" name="planChk" value="" />
                        <input type="hidden" id="onedaySubcontYn" name="onedaySubcontYn" value="" />
                        <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01">
                            <caption>조회화면</caption>
                            <tr>
                                <th>업체명</th>
                                <td>
                                    <input type="text" name="ioCompNm" id="ioCompNm"/>
                                </td>
                                <th>성명</th>
                                <td>
                                    <input type="text" name="ioEmpNm" id="ioEmpNm" />
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
            <!-- 검색영역 끝 -->

            <div id="grid"></div>

        </div>
    </div>
</body>
</html>