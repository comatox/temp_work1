<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map login = (Map) request.getSession().getAttribute("Login");
%>
<script type
<script type="text/javascript">

  let gridUtil;
  $(document).ready(function () {

    /******************
     * grid 내용 설정
     ******************/
    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityAdminManageItem/coEmpViolationList"
      , isPaging: true
      , excelUrl : "/api/secrtactvy/securityAdminManageItem/coEmpViolationList/download"
      , excelFileName : "구성원보안위규자_" + $.esutils.getToday("")
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
              return actDt + "<br/>" + actDt;
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
            headerName: "위규 내용"
            , field: "ofendGbnNm"
          },
          {
            headerName: "위규 조치"
            , field: "actDoNm"
          },
          {
            headerName: "제출여부"
            , field: "corrPlanYn"
          },
          {
            headerName: "경과일"
            , field: "passDate"
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

  });


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

  /********************
   * [grid Row 클릭시]
   ********************/
  function onRowClicked({originRowData:d}) {

    const params = {
      scDocNo : d.scDocNo
      ,scCorrPlanNo : d.scCorrPlanNo ?? ""
      ,docId : d.docId ?? ""
      ,apprResult : d.apprResult ?? ""
      ,apprStat : d.apprStat ?? ""
    }

    // 상세이동
    $.esutils.href("/admin/secrtactvy/securityadminmanageitem/coviolation/detail", params);
  }

</script>

<div id="contentsArea">
    <!-- <h1 class="title>임직원 보안 위규자 현황</h1> -->
    <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_367.png" />
    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm">

            <input type="hidden" id="empId" name="empId" value="" />

            <!-- 검색영역 시작 -->
            <div id="search_area">
                <!-- 검색 테이블 시작 -->
                <div class="search_content">
                    <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01" style="table-layout: fixed;">
                        <tbody>
                        <colgroup>
                            <col width="83" />
                            <col width="315" />
                            <col width="83" />
                            <col width="315" />
                        </colgroup>
                        <tr>
                            <th><label for="id02">소속회사</label></th>
                            <td name = "searchComp">
                                <select id=searchCompId name="searchCompId" style="width:80%">
                                    <option value=""/>
                                </select>
                            </td>
                            <th>위규 담당자</th>
                            <td>
                                <select id="detlEmpId" name="detlEmpId" style="width: 80%" />
                            </td>
                        </tr>
                        <tr>
                            <th>위규 구분</th>
                            <td>
                                <select id="ofendGbn" name="ofendGbn" style="width: 80%" />
                            </td>
                            <th>상세 구분</th>
                            <td>
                                <select id='ofendDetailGbn' name='ofendDetailGbn' style='width: 80%' />
                            </td>
                        </tr>
                        <tr>
                            <th>제출여부</th>
                            <td>
                                <input type="radio" id="corrPlanSendYn_C0101002" name="corrPlanSendYn" value="C0101002" class="border_none" /><label for="corrPlanSendYn_C0101002">미제출</label>&nbsp;
                                <input type="radio" id="corrPlanSendYn_C0101001" name="corrPlanSendYn" value="C0101001" class="border_none" /><label for="corrPlanSendYn_C0101001">제출완료</label>&nbsp;
                                <input type="radio" id="corrPlanSendYn_C0101003" name="corrPlanSendYn" value="C0101003" class="border_none" /><label for="corrPlanSendYn_C0101003">해당없음</label>&nbsp;
                                <input type="radio" id="corrPlanSendYn" name="corrPlanSendYn" value="" class="border_none" checked="checked"/><label for="corrPlanSendYn">전체</label>&nbsp;
                            </td>
                            <th>처리상태</th>
                            <td>
                                <input type="radio" id="applStat_1003" name="applStat" value="1003" class="border_none" /><label for="applStat_1003">해당없음</label>&nbsp;
                                <input type="radio" id="applStat_10" name="applStat" value="10" class="border_none" /><label for="applStat_10">검토중</label>&nbsp;
                                <input type="radio" id="applStat_1" name="applStat" value="1" class="border_none" /><label for="applStat_1">승인</label>&nbsp;
                                <input type="radio" id="applStat_2" name="applStat" value="2" class="border_none" /><label for="applStat_2">부결</label>&nbsp;
                                <input type="radio" id="applStat_5" name="applStat" value="5" class="border_none" checked="checked"/><label for="applStat_5">전체</label>
                            </td>
                        </tr>
                        <tr>
                            <th>위규자</th>
                            <td>
                                <input type="text" id='searchEmpNm' name='searchEmpNm' style='width: 80%' />
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
                            <th>지적일시</th>
                            <td>
                                <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:85px;"/>
                                &nbsp;~&nbsp;
                                <input type="text" id="searchEndDt" name="searchEndDt" style="width:85px;"/>
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
        <!-- 그리드 영역 시작 -->
        <div id="grid"></div>
        <!-- 그리드 영역 종료 -->
    </div>
</div>
