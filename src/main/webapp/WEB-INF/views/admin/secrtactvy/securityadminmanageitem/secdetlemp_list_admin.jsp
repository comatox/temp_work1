<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {

    // grid 설정
    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityAdminManageItem/secDetlEmpList"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "상세구분"
            , field: "detlNm"
            , width : "25%"
          },
          {
            headerName: "사업장"
            , field: "compNm"
          },
          {
            headerName: "담당자(정)"
            , field: "empNm"
          },
          {
            headerName: "소속부서"
            , field: "deptNm"
          },
          {
            headerName: "담당자(부)"
            , field: "subEmpNm"
          },
          {
            headerName: "소속부서"
            , field: "subDeptNm"
          }
        ]
        ,onRowClicked : onRowClicked
      }
      , search: {
        formId: "securityForm"
        , buttonId: "searchBtn"
        , beforeSendCheck: function () {
          if (!$("#searchOfendGbn").val()) {
            alert("위규 구분을 선택하세요");
            return false;
          }
          return true;
        }
      }

    });

    // 초기 위규구분, 위규 내용 세팅
    defaultDataSet(function () {

      //grid init
      gridUtil.init();
    });

    // 구성원 onChange Event
    onChangeGb();

    // 위규 구분 onChange Event
    onChagneSearchOfendGbn();

    // 모바일 포렌식 체크박스 Check Event
    onCheckSearchMobileForGbn();
  });

  /*******************
   * [초기 데이터 세팅]
   ******************/
  function defaultDataSet(callback) {
    //초기 구성원 위규구분, 출입보간 위규내용 set
    $.esutils.renderCode([
      {type: "select", grpCd: "C051", targetId: "searchOfendGbn", blankOption: true}
      , {type: "select", grpCd: "C053", targetId: "searchOfendDetailGbn", blankOption: true}
    ], function () {

      // 초기 출입보안으로 설정
      $("#searchOfendGbn").val("C0511001");

      callback();
    });
  }

  /***************************
   * [구성원 Select 값 변경시]
   ***************************/
  function onChangeGb() {
    $("#searchGb").on("change", function () {
      const gbVal = $(this).val();
      // C051 : 구성원 위규 구분, C052 : 외부인 위규구분
      const grpCd = gbVal === "O" ? "C052" : "C051";

      // 위규구분 세팅
      setSearchOfendGbn(grpCd);
    })
  }

  /***********************
   * [위규구분 data set]
   ************************/
  function setSearchOfendGbn(grpCd) {
    // 위규구분 세팅
    // C051 : 구성원 위규 구분(default), C052 : 외부인 위규구분
    $.esutils.renderCode([{type: "select", grpCd, targetId: "searchOfendGbn", blankOption: true}]);

    // 위규내용 empty
    $("#searchOfendDetailGbn").empty();
  }

  /****************************
   * [위규구분 Select 값 변경시]
   ****************************/
  function onChagneSearchOfendGbn() {
    $("#searchOfendGbn").on("change", function () {
      // 위규내용 empty
      $("#searchOfendDetailGbn").empty();

      const selectValue = $(this).val();

      if (!selectValue) return;

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
      } else if (selectValue === "C0521001") { // 외부인 출입보안
        code = 'C058';
      } else if (selectValue === "C0521002") { // 외부인관리보안
        code = 'C065';
      } else if (selectValue === "C0521003") { // 외부인문서보안
        code = 'C066';
      } else if (selectValue === "C0521004") { // 외부인 전산보안
        code = 'C067';
      } else if (selectValue === "C0521005") { // 외부인 기타사항
        code = 'C068';
      } else if (selectValue === "C0521006") { // 외부인 사내질서
        code = 'C075';
      }

      // 위규내용 데이터 세팅
      setSearchOfendDetailGbn(code);

    })
  }

  /************************
   * [위규 내용 데이터 세팅]
   ************************/
  function setSearchOfendDetailGbn(code) {
    // 위규구분 세팅
    $.esutils.renderCode([{type: "select", grpCd: code, targetId: "searchOfendDetailGbn", blankOption: true}]);
  }

  /*******************************
   * [모바일 포렌식 체크박스 체크시]
   *******************************/
  function onCheckSearchMobileForGbn() {
    $("#searchMobileForGbn").on("click", function (e) {

      // grid 재검색
      gridUtil.searchData();
    });
  }

  /*************************
   * [로우 클릭시 상세 이동]
   *************************/
  function onRowClicked({originRowData}) {
    const {detlCd, compId} = originRowData;
    const searchOfendTxt = $("#searchOfendGbn option:selected").text();
    const searchOfendDetailTxt = originRowData.detlNm;
    const searchCompNm = originRowData.compNm;

    //상세 이동
    $.esutils.href("/admin/secrtactvy/securityadminmanageitem/secdetlemp/detail", {detlCd, compId, searchOfendTxt, searchOfendDetailTxt, searchCompNm});
  }

</script>

<div id="contentsArea">
    <!-- <h1 class="title>임직원 보안 위규자 현황</h1> -->
    <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_340.png"/>
    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm">
            <input type="hidden" id="detlCd" name="detlCd" value=""/>
            <input type="hidden" id="compId" name="compId" value=""/>
            <input type="hidden" id="empGbn" name="empGbn" value=""/>
            <input type="hidden" id="searchOfendTxt" name="searchOfendTxt" value=""/>
            <input type="hidden" id="searchOfendDetailTxt" name="searchOfendDetailTxt" value=""/>
<%--            <input type="hidden" id="searchMobileForGbn" name="searchMobileForGbn" value="N"/>--%>
            <input type="hidden" id="searchCompNm" name="searchCompNm" value=""/>
            <!-- 검색영역 시작 -->
            <div id="search_area">
                <!-- 검색 테이블 시작 -->
                <div class="search_content">
                    <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01">
                        <tbody>
                        <colgroup>
                            <col width="83px;"/>
                            <col width="127px;"/>
                            <col width="73px;"/>
                            <col width="83px;"/>
                            <col width="127px;"/>
                            <col width="73px;"/>
                        </colgroup>
                        <tr>
                            <th>구성원/외부인</th>
                            <td colspan="2">
                                <select id="searchGb" name="searchGb" style="width: 242px;">
                                    <option value="G">구성원</option>
                                    <option value="O">외부인</option>
                                </select>
                            </td>
                            <th>모바일 포렌직</th>
                            <td colspan="2">
                                <input type="checkbox" id="searchMobileForGbn" name="searchMobileForGbn" class="border_none" value="Y"/>
                            </td>
                        </tr>
                        <tr>
                            <th>위규 구분</th>
                            <td colspan="2">
                                <select id="searchOfendGbn" name="searchOfendGbn" style="width: 242px;"/>
                            </td>
                            <th>위규 내용</th>
                            <td colspan="2">
                                <select id='searchOfendDetailGbn' name='searchOfendDetailGbn' style='width: 242px;'/>
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
            </div>
            <!-- 검색 테이블 종료 -->
        </form>
        <div id="grid"></div>
    </div>
</div>