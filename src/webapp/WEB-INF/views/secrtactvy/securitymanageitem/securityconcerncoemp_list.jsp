<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

  let gridUtil;
  $(document).ready(function () {

    /******************
     * grid 내용 설정
     ******************/
    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityManageItem/securityConcernCoEmpViolation"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "지적 일시"
            , field: "ofendDt"
            , formatter: function (_, __, row) {
              const ofendDt = row.ofendDt ?? "";
              const ofendTm = row.ofendTm ?? "";
              return ofendDt + "<br/>" + ofendTm;
            }
          },
          {
            headerName: "안내일시"
            , field: "actDt"
          },
          {
            headerName: "성명"
            , field: "empNm"
            , align: "center"
            , formatter: function (_, __, row) {
              const empNm = row.empNm ?? "";
              const ofendEmpId = row.ofendEmpId ?? "";
              return empNm + "(" + ofendEmpId + ")";
            }
          },
          {
            headerName: "위규 내용"
            , field: "ofendGbnNm"
            , align: "center"
            , formatter: function (_, __, row) {
              const ofendGbnNm = row.ofendGbnNm ?? "";
              const ofendDetailGbnNm = row.ofendDetailGbnNm ?? "";
              return ofendGbnNm + " - " + ofendDetailGbnNm;
            }
          },
          {
            headerName: "위규 조치"
            , field: "actDoNm"
            , align: "center"
          },
          {
            headerName: "제출여부"
            , field: "corrPlanYn"
            , align: "center"
          },
          {
            headerName: "경과일"
            , field: "passDate"
            , align: "center"
          },
          {
            headerName: "14일\n초과여부"
            , field: "over14Yn"
            , align: "center"
          },
          {
            headerName: "처리상태"
            , field: "apprResultNm"
            , align: "center"
          },
        ]
        ,onRowClicked : onRowClicked
      },
      excelUrl: "/api/secrtactvy/securityManageItem/securityConcernCoEmpViolation/download"
      ,excelFileName : "팀내구성원보안위규자_" + $.esutils.getToday("")
      , search: {
        formId: "securityForm"
        , buttonId: "searchBtn"
      }
    });

    /****************
     * [date picker]
     ****************/
    const [searchStrtDt, searchEndDt] = $.esutils.datepicker(["[id=searchStrtDt]", "[id=searchEndDt]"]);
    searchStrtDt.selectDate(new Date($.esutils.getToday().substr(0, 4) + "-01-01"));
    searchEndDt.selectDate(new Date($.esutils.getToday()));

    // 기본값 설정
    defaultValueSet();

    /*******************************
     * [위규구분 SelectBox Setting]
     *******************************/
    $.esutils.renderCode([{type: "select", grpCd: "C051", targetId: "ofendGbn", blankOption: true}], function () {

      /** 위규구분 Change Event */
      onChangeSearchOfendGbn();

      /** grid init */
      gridUtil.init();
    });

  });
  /*************************************
   * [위규구분 Change Event]
   * 위규구분 값에 따라서 위규내용 값 변경
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

  /***************
   * [기본값 세팅]
   ***************/
  function defaultValueSet() {

    $("#empId").val(global.empId);
    $("#deptId").val(global.deptId);
    $("#compId").val(global.compId);
  }

  /******************************
   * [그리드 Row 클릭시(상세이동]
   ******************************/
  function onRowClicked({originRowData:d}) {
    const params = {
      scDocNo : d.scDocNo
      ,scCorrPlanNo : d.scCorrPlanNo ?? ""
      ,docId : d.docId ?? ""
      ,apprResult : d.apprResult ?? ""
      ,apprStat : d.apprStat ?? ""
    }

    // 상세이동
    $.esutils.href("/secrtactvy/securitymanageitem/securityconcerncoemp/detail", params);
  }

</script>
<div class="wrap">
    <div class="contentWrap" style="padding-bottom: 10px;">
        <div id="contentsArea">
            <!-- <h1 class="title">보안위규자 현황</h1> -->
            <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_366.png"/>
            <!-- realContents start -->
            <div id="realContents">
                <%--                <form id="securityExcelForm" name="securityExcelForm" method="post" style="display:none">--%>
                <%--                    <input type="hidden" id="pOfendGbn" name="pOfendGbn" value=""/>--%>
                <%--                    <input type="hidden" id="pOfendDetailGbn" name="pOfendDetailGbn" value=""/>--%>
                <%--                    <input type="hidden" id="pSearchEmpnm" name="pSearchEmpnm" value=""/>--%>
                <%--                    <input type="hidden" id="pSearchStrtdt" name="pSearchStrtdt" value=""/>--%>
                <%--                    <input type="hidden" id="pSearchEnddt" name="pSearchEnddt" value=""/>--%>
                <%--                    <input type="hidden" id="pSearchInputempnm" name="pSearchInputempnm" value=""/>--%>
                <%--                    <input type="hidden" id="pSearchDeptnm" name="pSearchDeptnm" value=""/>--%>
                <%--                    <input type="hidden" id="pSearchDeptid" name="pSearchDeptid"/>--%>
                <%--                    <input type="hidden" id="pSearchEmpid" name="pSearchEmpid"/>--%>
                <%--                    <input type="hidden" id="pSearchCompid" name="pSearchCompid"/>--%>
                <%--                    <input type="hidden" id="pApplStat" name="pApplStat" value=""/>--%>
                <%--                    <input type="hidden" id="pCorrPlanSendYn" name="pCorrPlanSendYn" value=""/>--%>
                <%--                    <input type="hidden" id="pOfendauth" name="pOfendauth" value="Y"/>--%>
                <%--                    <input type="hidden" id="actDtm" name="actDtm" value="client"/>--%>
                <%--                </form>--%>

                <form id="securityForm" name="securityForm">
                    <input type="hidden" id="empId" name="empId"/>
                    <input type="hidden" id="deptId" name="deptId"/>
                    <input type="hidden" id="compId" name="compId"/>
                    <input type="hidden" id="ofendAuth" name="ofendAuth" value="Y"/>

                    <!-- 검색영역 시작 -->
                    <div id="search_area">
                        <!-- 검색 테이블 시작 -->
                        <div class="search_content">
                            <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01">
                                <tbody>
                                <colgroup>
                                    <col width="73px;"/>
                                    <col width="137px;"/>
                                    <col width="73px;"/>
                                    <col width="73px;"/>
                                    <col width="137px;"/>
                                    <col width="73px;"/>
                                </colgroup>
                                <tr>
                                    <th>위규 구분</th>
                                    <td colspan="2">
                                        <select id="ofendGbn" name="ofendGbn" style="width: 242px;"/>
                                    </td>
                                    <th>위규 내용</th>
                                    <td colspan="2">
                                        <select id='ofendDetailGbn' name='ofendDetailGbn' style='width: 242px;'/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>위규자</th>
                                    <td colspan="2">
                                        <input type="text" id='searchEmpNm' name='searchEmpNm' style='width: 242px;'/>
                                    </td>
                                    <th><label>지적일시</label></th>
                                    <td colspan="2">
                                        <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:80px;"/><img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle"
                                                                                                                           alt="날짜" id="searchStrtDtImg"/>
                                        &nbsp;~&nbsp;
                                        <input type="text" id="searchEndDt" name="searchEndDt" style="width:80px;"/><img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle"
                                                                                                                         alt="날짜" id="searchEndDtImg"/>
                                    </td>
                                </tr>
                                <th>부서명</th>
                                <td colspan="4">
                                    <input type="text" id='searchDeptNm' name='searchDeptNm' style='width: 242px;'/>
                                </td>
                                <tr>
                                </tbody>
                            </table>
                            <!-- 버튼 시작 -->
                            <div class="searchGroup">
                                <div class="centerGroup">
                                    <span id="searchBtn" class="button bt_l1"><button type="button" style="width:80px;">검색</button></span>
                                </div>
                            </div>
                            <!-- 버튼 종료 -->
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 그리드 영역 시작 -->
    <div id="grid"></div>
    <!-- 그리드 영역 종료 -->
</div>
