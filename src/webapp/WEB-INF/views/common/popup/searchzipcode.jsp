<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script>
      let gridUtil;
      let initFinished = false;
      let validationFlag = false;

      /***************************************************************************
       * 화면 onload 처리
       ***************************************************************************/
      $(document).ready(() => {

        initPopup();
        initGrid();

        //grid init
        gridUtil.init();
        initFinished = true;
      });

      /***************************************************************************
       * Popup initializing
       ***************************************************************************/
      const initPopup = () => {

        $(document).keydown(function (e) {
          // F5, ctrl + F5, ctrl + r 새로고침 막기
          if (e.which === 116) {
            if (typeof event == "object") {
              event.keyCode = 0;
            }
            return false;
          } else if (e.which === 82 && e.ctrlKey) {
            return false;
          }
        });

        fnGetSidoList(); // 사업장 Select Option 조회

        // $("#searchBtn").on('click', (e) => {
        //   validationFlag = fnValidation($.esutils.getFieldsValue($("#searchZipCodeForm")));
        //   if (!validationFlag) {
        //     console.log('e', e);
        //     e.preventDefault();
        //   }
        // });

        $("#roadAddr").hide();
        $("#addrGbn").change(() => {
          const addrGbn = $("#addrGbn").val();
          const sido = $("#sido").val();
          const dong = $("#dong").val();
          if (addrGbn === "1") {
            $("#jibunAddr").show();
            $("#roadAddr").hide();
            $("#addrDtl1").html("읍/면/동");
            $("#addrDtl2").html("번지");

          } else {
            $("#jibunAddr").hide();
            $("#roadAddr").show();
            $("#addrDtl1").html("도로명");
            $("#addrDtl2").html("건물명");
          }
          /* 검색이 되었을 시 재검색 */
          if (sido !== "" && dong !== "") {
            gridUtil.searchData();
          }
        });

        $("#sido").change(() => {
          $("select#gugun > option").remove();
          fnGetGugunList();
        });
      }

      /***************************************************************************
       * Grid initializing
       ***************************************************************************/
      const initGrid = () => {
        gridUtil = new GridUtil({
          url: "/api/common/emp/searchZipCode"
          , gridId: "grid"
          , isPaging: true
          , ajaxMethod: "post"
          , pageSize: 10
          , gridOptions: {
            colData: [
              {
                headerName: "우편번호"
                , field: "zipCode"
              },
              {
                headerName: "시/도"
                , field: "sido"
              },
              {
                headerName: "시/군/구"
                , field: "gugun"
              },
              {
                headerName: "읍/면/동"
                , field: "dong"
              },
              {
                headerName: "번지"
                , field: "bunji"
              },
            ]
            , onRowClicked
          },
          search: {
            formId: "searchZipCodeForm",
            buttonId: "searchBtn",
            beforeSearch: fnValidation,
            // beforeSend: (formData) => {
            //   if (!initFinished) return;
            //   // fnValidation(formData);
            //   return formData;
            // }
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

      const fnGetSidoList = () => {
        const targetId = 'sido';
        $.esutils.renderCode([{type: "select", grpCd: "Z038", targetId}], () => {
          fnGetGugunList();
        });
      }

      const fnGetGugunList = () => {
        const targetId = 'gugun';
        const sido = $("#sido").val();
        $.esutils.renderCode([{type: "select", grpCd: "Z039", targetId, blankOption: true, filter: (d) => d.etc1 === sido}]);
      }

      const fnValidation = () => {
        console.log("BEFORE SEARCH");
        const formData = $.esutils.getFieldsValue($('#searchZipCodeForm'));
        const {sido, dong, gugun, addrGbn} = formData;

        if (sido === "") {
          alert("시도를 선택해주십시오");
          $("#sido").focus(); //접근성: 오류영역 초점이동
          return;
        }
        if (gugun === "") {
          alert("시군구를 선택해주십시오.");
          $("#gugun").focus(); //접근성: 오류영역으로 초점이동
          return;
        }
        if (dong === "") {
          if (addrGbn === "1") {
            alert("읍/면/동을 입력해주십시오.");
          } else {
            alert("도로명을 입력해주십시오.");
          }
          $("#dong").focus(); //접근성: 오류영역으로 초점이동
          return;
        }
        if (dong.length < 2) {
          if (addrGbn === "1") {
            alert("읍/면/동을 두 글자 이상 입력해주십시오.");
          } else {
            alert("도로명을 두 글자 이상 입력해주십시오.");
          }
          $("#dong").focus(); //접근성: 오류영역으로 초점이동
          return;
        }

        return true;
      }
    </script>
</head>
<body>
<!-- popup ::  START -->
<div id="popBody">
    <form id="searchZipCodeForm" name="searchZipCodeForm" onsubmit="return false;" method="post">
        <input type="hidden" id="compType" name="compType"/>
        <div id="popArea">
            <div class="pop_title"><h1>우편번호 검색</h1></div>
            <div class="close"><a href="javascript:window.close();"><img src="/eSecurity/common/images/common/pop_close.png" alt="우편번호 검색 창닫기"/></a></div>
        </div>
        <div class="pop_content">
            <!-- 검색영역 시작 -->
            <div id="search_area">
                <!-- 검색 테이블 시작 -->
                <div class="search_content">
                    <input type="hidden" id="searchEmpId" name="searchEmpId"/>
                    <input type="hidden" id="searchType" name="searchType" value="1"/>

                    <table cellpadding="0" cellspacing="0" summary="주소구분, 시도명, 시군구, 읍/명/동으로 구성된 우편번호 검색(표)입니다. " border="0" class="view_board01">
                        <caption>우편번호 검색(표)</caption>
                        <colgroup>
                            <col width="127px"/>
                            <col width="213px"/>
                            <col width="127px"/>
                            <col width="213px"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row" id="add_cate">주소구분</th>
                            <td headers="add_cate">
                                <select id="addrGbn" name="addrGbn" title="주소구분을 선택하세요">
                                    <option value="1" selected>기존주소</option>
                                    <option value="2">도로명주소</option>
                                </select>
                            </td>
                            <th scope="row" id="add_city">시도명</th>
                            <td headers="add_city">
                                <select id="sido" name="sido" title="시도명을 선택하세요">
                                    <option value="" selected>::: 선택하세요 :::</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" id="add_county">시군구</th>
                            <td headers="add_county">
                                <select id="gugun" name="gugun" title="시군구를 선택하세요">
                                    <option value="" selected>::: 선택하세요 :::</option>
                                </select>
                            </td>
                            <th scope="row" id="jibunAddr">읍/면/동</th>
                            <th scope="row" id="roadAddr">도로명</th>
                            <td headers="jibunAddr">
                                <input type="text" id="dong" name="dong" maxLength="15" maxByte="15" style="width:150px;" title="읍/면/동 또는 도로명"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <!-- 버튼 시작 -->
                    <div class="searchGroup">
                        <div class="centerGroup">
                            <span class="button bt_l1"><button id="searchBtn" type="button" style="width:80px;">검색</button></span>
                        </div>
                    </div>
                    <!-- 버튼 종료 -->
                </div>
                <!-- 검색 테이블 종료 -->
            </div>
            <!-- 검색영역 종료 -->
            <!-- 그리드 영역 시작 -->
            <div id="grid">
            </div>
        </div>
    </form>
</div>
</body>
</html>