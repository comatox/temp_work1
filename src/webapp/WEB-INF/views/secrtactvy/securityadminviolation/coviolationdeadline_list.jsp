<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map login = (Map) request.getSession().getAttribute("Login");
%>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {

    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityAdminManageItem/coEmpViolationList"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "지적일시"
            , field: "ofendDt"
            , formatter: function (_, __, row) {
              const ofendDt = row.ofendDt ?? "";
              const ofendTm = row.ofendTm ?? "";
              return ofendDt + " " + ofendTm;
            }
          },
          {
            headerName: "안내일시"
            , field: "actTm"
            , formatter: function (_, __, row) {
              const actDt = row.actDt ?? "";
              const actTm = row.actTm ?? "";
              return actDt + " " + actDt;
            }
          },
          {
            headerName: "위규자"
            , field: "empJwNm"
            , align: "left"
            , formatter: function (_, __, row) {
              const deptNm = row.deptNm ?? "";
              const empJwNm = row.empJwNm ?? "";
              const ofendEmpId = row.ofendEmpId ?? "";
              return deptNm + " " + empJwNm + "(" + ofendEmpId + ")";
            }
          },
          {
            headerName: "위규내용"
            , field: "ofendGbnNm"
          },
          {
            headerName: "제출여부"
            , field: "corrPlanYn"
          },
          {
            headerName: "경과일"
            , field: "passDate"
          },
          {
            headerName: "2차메일전송여부"
            , field: "exceedMailSendYn"
          }
        ]
        , onRowClicked: onRowClicked
      }
      , search: {
        formId: "securityForm"
        , buttonId: "searchBtn"
      }
    });

    /********************
     * [date picker 설정]
     ********************/
    $.esutils.datepicker(["[name=searchStrtDt]"], {startDate: new Date(getPrevious30Date($.esutils.getToday()))});
    $.esutils.datepicker(["[name=searchEndDt]"], {startDate: new Date($.esutils.getToday())});
    $("[name=searchStrtDt]").val(getPrevious30Date($.esutils.getToday()));
    $("[name=searchEndDt]").val($.esutils.getToday());

    // 기본값 설정
    defaultValueSet();

    // 검색 조건 데이터 세팅
    initSearchConditionDataSet(function () {

      // 위규 구분 change event
      onChangeSearchOfendGbn();

      //grid Init
      gridUtil.init();
    });

  })

  /*************************
   * [검색 조건 데이터 세팅]
   *************************/
  function initSearchConditionDataSet(callbackFn) {

    $.esutils.renderCode([
      { // 회사 리스트
        targetId: "searchCompId",
        url: "/api/common/organization/comp",
        nameProp: "compNm",
        valueProp: "compId",
        type: "select",
        blankOption: "전체",
        filter: (d) => d.compId !== '1100000001'
      },
      { // 위규 담당자 조회
        targetId: "detlEmpId",
        url: "/api/secrtactvy/securityAdminManageItem/scDetlEmpList",
        type: "select",
        blankOption: true
      },
      { // 위규 구분 조회
        type: "select",
        grpCd: "C051",
        targetId: "ofendGbn",
        blankOption: true
      }
    ], function () {
      // 관리자 권한
      const isAdminAuth = $.esutils.checkAuth("1", "<%=login.get("AUTH")%>");

      // 관리자가 아닐시 회사명 선택 후 disabled
      if (!isAdminAuth) {
        $("#searchCompId").val(global.compId).attr("selected", "selected");
        $("#searchCompId").not(":selected").attr("disabled", "disabled");
      }

      callbackFn();
    });
  }

  /**************
   * [기본값 세팅]
   **************/
  function defaultValueSet() {
    $("#empId").val(global.empId);
  }

  /********************
   * [30일전 날짜 세팅]
   ********************/
  function getPrevious30Date(dateStr) {
    dateStr = dateStr.replaceAll("-", "");

    var yyyy = dateStr.substring(0, 4);
    var mm = parseInt(dateStr.substring(4, 6), 10);
    var dd = parseInt(dateStr.substring(6), 10);

    var date = new Date(yyyy, mm - 1, dd);
    date.setDate(date.getDate() - 30);
    yyyy = date.getFullYear();
    mm = date.getMonth() + 1;
    dd = date.getDate();

    if (mm < 10)
      mm = "0" + mm;
    if (dd < 10)
      dd = "0" + dd;

    return yyyy + "-" + mm + "-" + dd;
  }

  /*************************************
   * [위규구분 Change Event]
   * 위규구분 값에 따라서 상세구분 값 변경
   *************************************/
  const onChangeSearchOfendGbn = () => {

    $("#ofendGbn").on("change", function () {

      let code = "";
      const selectValue = $(this).val();

      if (!selectValue) {
        // 위규내용 초기화
        $("#ofendDetailGbn").empty();
      } else {
        if (selectValue === "C0511001") { //출입보안
          code = 'C053';
        } else if (selectValue === "C0511002") { //관리보안
          code = 'C054';
        } else if (selectValue === "C0511003") { //문서보안
          code = 'C055';
        } else if (selectValue === "C0511004") { // 전산보안
          code = 'C056';
        } else if (selectValue === "C0511005") { // 기타사항
          code = 'C057';
        } else if (selectValue === "C0511006") { // 사내질서
          code = 'C074';
        }
        $.esutils.renderCode([{type: "select", grpCd: code, targetId: "ofendDetailGbn", blankOption: true}]);
      }
    });
  }

  /********************
   * [grid Row 클릭시]
   ********************/
  function onRowClicked({originRowData}) {
    // 구성원 상세와 같은페이지를 사용.
    // 뒤로가기시 검색조건 회복조건에 맞게하기 위해 url 생성하여 사용.
    $.esutils.href("/secrtactvy/securityadminviolation/coviolationdeadline/detail", {scDocNo: originRowData.scDocNo, deadLIneYn: $("#deadLIneYn").val()});
  }


</script>

<div id="contentsArea">
    <!-- <h1 class="title>임직원 보안 위규자 현황</h1> -->
    <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_367.png"/>
    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm">

            <input type="hidden" id="empId" name="empId" value=""/>
            <input type="hidden" id="deadLIneYn" name="deadLIneYn" value="Y"/>

            <!-- 검색영역 시작 -->
            <div id="search_area">
                <!-- 검색 테이블 시작 -->
                <div class="search_content">
                    <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01" style="table-layout: fixed;">
                        <tbody>
                        <colgroup>
                            <col width="83"/>
                            <col width="315"/>
                            <col width="83"/>
                            <col width="315"/>
                        </colgroup>
                        <tr>
                            <th><label for="id02">소속회사</label></th>
                            <td>
                                <select id=searchCompId name="searchCompId" style="width:80%">
                                </select>
                            </td>
                            <th>위규 담당자</th>
                            <td>
                                <select id="detlEmpId" name="detlEmpId" style="width: 80%"/>
                            </td>
                        </tr>
                        <tr>
                            <th>위규 구분</th>
                            <td>
                                <select id="ofendGbn" name="ofendGbn" style="width: 80%"/>
                            </td>
                            <th>상세 구분</th>
                            <td>
                                <select id='ofendDetailGbn' name='ofendDetailGbn' style='width: 80%'/>
                            </td>
                        </tr>

                        <tr>
                            <th>위규자</th>
                            <td>
                                <input type="text" id='searchEmpNm' name='searchEmpNm' style='width: 80%'/>
                            </td>
                            <th>위규부서</th>
                            <td>
                                <input type="text" id='searchDeptNm' name='searchDeptNm' style='width: 80%'/>
                            </td>
                        </tr>
                        <tr>
                            <th>등록자</th>
                            <td>
                                <input type="text" id='searchInputEmpNm' name='searchInputEmpNm' style='width: 80%'/>
                            </td>
                            <th><label for="id02">지적일시</label></th>
                            <td>
                                <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:83px;"/>
                                &nbsp;~&nbsp;
                                <input type="text" id="searchEndDt" name="searchEndDt" style="width:83px;"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <!-- 버튼 시작 -->
                    <div class="searchGroup">
                        <div class="centerGroup">
                            <span class="button bt_l1" id="searchBtn"><button type="button" style="width:80px;">검색</button></span>
                        </div>
                    </div>
                    <!-- 버튼 종료 -->
                </div>
            </div>
        </form>
        <div id="grid"></div>
    </div>
</div>