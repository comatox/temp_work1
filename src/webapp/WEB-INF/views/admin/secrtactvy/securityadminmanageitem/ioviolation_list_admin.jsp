<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

  let gridUtil;
  $(document).ready(function () {

    // 기본값 설정
    defaultValueSet();

    /****************
     * [date picker]
     * **************/
    $.esutils.rangepicker([["[name=searchStrtDt]", "[name=searchEndDt]"]]);

    /******************
     * grid 내용 설정
     ******************/
    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityAdminManageItem/ioEmpViolationList"
      , isPaging: true
      , excelUrl: "/api/secrtactvy/securityAdminManageItem/ioEmpViolationList/download"
      , excelFileName: "외부인보안위규자_" + $.esutils.getToday("")
      , gridOptions: {
        colData: [
          {
            headerName: "요청회사"
            , field: "compNm2"
            , align: "left"
          },
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
            headerName: "사업장"
            , field: "actCompIdNm"
          },
          {
            headerName: "협력업체"
            , field: "ofendCompNm"
          },
          {
            headerName: "위규자"
            , field: "ofendEmpNm"
            , align: "left"
          },
          {
            headerName: "적발횟수(9월~8월)"
            , field: "ct"
          },
          {
            headerName: "위규처리"
            , field: "actDoNm"
          },
          {
            headerName: "제출여부"
            , field: "corrPlanSendYnNm"
          },
          {
            headerName: "제출일시"
            , field: "propDt"
            , formatter: function (_, __, row) {
              const propDt = row.propDt ?? "";
              const propTm = row.propTm ?? "";
              return propDt + " " + propTm;
            }
          },
          {
            headerName: "처리여부"
            , field: "apprGbnNm"
          },
          {
            headerName: "출입정지기간"
            , field: "denyPeriod"
            , formatter: function (_, __, row) {
              const denyPeriod = row.denyPeriod ? row.denyPeriod.replaceAll("~", "<br>~") : "";
              return denyPeriod;
            }
          },
          {
            headerName: "위규내용"
            , field: "ofendGbnNm"
            , width: "15%"
            , formatter: function (_, __, row) {
              const ofendGbnNm = row.ofendGbnNm ?? "";
              const ofendDetailGbnNm = row.ofendDetailGbnNm ?? "";
              return ofendGbnNm + " - " + ofendDetailGbnNm;
            }
          },
        ]
        , onRowClicked: function ({originRowData: d}) {
          const param = {
            scIoDocNo: d.scIoDocNo
            , scIoCorrPlanNo: d.scIoCorrPlanNo ?? ""
            , corrPlanSendYn: d.corrPlanSendYn ?? ""
            , ofendEmpId: d.ofendEmpId
          };

          $.esutils.href("/admin/secrtactvy/securityadminmanageitem/ioviolation/detail", param);
        }
      },
      search: {
        formId: "securityForm"
        , buttonId: "searchBtn"
      }
    });

    // 검색 조건 데이터 세팅
    initSearchConditionDataSet(function () {

      // 위규 구분 change event
      onChangeOfendGbn();

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
        grpCd: "C052",
        targetId: "ofendGbn",
        blankOption: true
      },
      { // 처리상태
        type: "select",
        grpCd: "Z040",
        targetId: "applStatGbn",
        blankOption: true
      }
    ], function () {
      callbackFn();
    });
  }

  /*************************************
   * [위규구분 Change Event]
   * 위규구분 값에 따라서 위규내용 값 변경
   *************************************/
  const onChangeOfendGbn = () => {

    $("#ofendGbn").on("change", function () {

      // 위규내용 초기화
      $("#ofendDetailGbn").empty();

      let code = "";
      const selectValue = $(this).val();

      if (selectValue === "") {
        $("#ofendDetailGbn").append('<option value="">::선택하세요::</option>');
      } else {
        if (selectValue === "C0521001") { //출입보안
          code = 'C058';
        } else if (selectValue === "C0521002") { //관리보안
          code = 'C065';
        } else if (selectValue === "C0521003") { //문서보안
          code = 'C066';
        } else if (selectValue === "C0521004") { // 전산보안
          code = 'C067';
        } else if (selectValue === "C0521005") { // 기타사항
          code = 'C068';
        } else if (selectValue === "C0521006") { // 사내질서
          code = 'C075';
        }
        $.esutils.renderCode([{type: "select", grpCd: code, targetId: "ofendDetailGbn", blankOption: true}]);
      }
    });
  }

  /***************
   * [기본값 설정]
   ***************/
  function defaultValueSet() {
    $("#empId").val(global.empId);
  }


</script>

<div id="contentsArea">
    <!-- <h1 class="title>외부인 보안 위규자 현황</h1> -->
    <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_368.png"/>
    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm">
            <input type="hidden" id="empId" name="empId" value=""/>
            <!-- 검색영역 시작 -->
            <div id="search_area">
                <!-- 검색 테이블 시작 -->
                <div class="search_content">
                    <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01">
                        <tbody>
                        <colgroup>
                            <col width="83"/>
                            <col width="315"/>
                            <col width="83"/>
                            <col width="315"/>
                        </colgroup>

                        <tr>
                            <th><label for="id02">요청회사</label></th>
                            <td>
                                <select id=searchCompId name="searchCompId" style="width:242px;">
                                    <option value="">
                                    </option>
                                </select>
                            </td>
                            <th>위규 사업장</th>
                            <td>
                                <select id="actCompId" name="actCompId" style="width: 242px;"/>
                                <option value="">::선택하세요::</option>
                                <option value="1101000001">이천</option>
                                <option value="1102000001">청주</option>
                                <!--<option value="1107000001">분당사무소(서현)</option>-->
                                <option value="1108000001">분당사무소(정자)</option>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th>위규 구분</th>
                            <td>
                                <select id="ofendGbn" name="ofendGbn" style="width: 242px;"/>
                            </td>
                            <th>위규 내용</th>
                            <td>
                                <select id='ofendDetailGbn' name='ofendDetailGbn' style='width: 242px;'/>
                            </td>
                        </tr>
                        <tr>
                            <th>위규자</th>
                            <td>
                                <input type="text" id='searchEmpNm' name='searchEmpNm' style='width: 230px;'/>
                            </td>
                            <th><label for="id02">지적일시</label></th>
                            <td>
                                <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:73px;"/>
                                &nbsp;~&nbsp;
                                <input type="text" id="searchEndDt" name="searchEndDt" style="width:73px;"/>
                            </td>
                        </tr>
                        <tr>
                            <th>처리상태</th>
                            <td>
                                <select id="applStatGbn" name="applStatGbn" style="width: 242px;"/>
                            </td>
                            <th>제출여부</th>
                            <td>
                                <input type="radio" id="corrPlanSendYn_C0101002" name="corrPlanSendYn" value="C0101002" class="border_none"/><label for="corrPlanSendYn_C0101002">미제출</label>&nbsp;
                                <input type="radio" id="corrPlanSendYn_C0101001" name="corrPlanSendYn" value="C0101001" class="border_none"/><label for="corrPlanSendYn_C0101001">제출완료</label>&nbsp;
                                <input type="radio" id="corrPlanSendYn" name="corrPlanSendYn" value="" class="border_none" checked="checked"/><label for="corrPlanSendYn">전체</label>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <th>위규 담당자</th>
                            <td>
                                <select id="detlEmpId" name="detlEmpId" style="width: 242px;"/>
                            </td>
                            <th>업체명</th>
                            <td>
                                <input type="text" id="ofendCompNm" name="ofendCompNm" style="width:230px;"/>
                            </td>
                        </tr>
                        <tr>
                            <th colspan="4">
                                <label for="id02">적발횟수(9월~8월)</label>
                                <input type="text" id="searchCt" name="searchCt" style="width:73px;" maxlength="2"/>
                            </th>
                        </tr>
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
            <!-- 그리드 영역 시작 -->
            <div id="grid"></div>
            <!-- 그리드 영역 종료 -->
        </form>
    </div>
</div>
