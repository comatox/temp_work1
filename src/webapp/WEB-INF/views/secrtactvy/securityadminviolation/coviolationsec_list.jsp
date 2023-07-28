<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

  let gridUtil;
  $(document).ready(function () {

    // 기본값 설정
    defaultValueSet();

    /******************
     * grid 내용 설정
     ******************/
    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityAdminViolation/coViolation/sec/list"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "적발 사업장"
            , field: "actCompIdNm"
          },
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
            headerName: "위규자"
            , field: "empJwNm"
            , width: "20%"
            , formatter: function (_, __, row) {
              const compNm = row.compNm ?? "";
              const empJwNm = row.empJwNm ?? "";
              const deptNm = row.deptNm ?? "";
              return compNm + " " + deptNm + " " + empJwNm;
            }
          },
          {
            headerName: "위규 내용"
            , field: "ofendGbnNm"
            , width: "20%"
            , formatter: function (_, __, row) {
              const ofendGbnNm = row.ofendGbnNm ?? "";
              const ofendDetailGbnNm = row.ofendDetailGbnNm ?? "";
              return ofendGbnNm + " - " + ofendDetailGbnNm;
            }
          },
          {
            headerName: "위규 조치"
            , field: "actDoNm"
          },
          {
            headerName: "등록자"
            , field: "regEmpNm"
          },
        ]
        , onRowClicked: function ({originRowData}) {
          $.esutils.href("/secrtactvy/securityadminviolation/coviolationsec/detail", {scDocNo: originRowData.scDocNo});
        }
      },
      search: {
        formId: "securityForm"
        , buttonId: "searchBtn"
      }
    });

    /****************
     * [date picker]
     ****************/
    $.esutils.rangepicker([["[name=searchStrtDt]", "[name=searchEndDt]"]]);

    /*******************************
     * [위규구분 SelectBox Setting]
     *******************************/
    $.esutils.renderCode([{type: "select", grpCd: "C051", targetId: "searchOfendGbn"}], function () {
      $("#searchOfendGbn").prepend('<option value="" selected>::선택하세요::</option>');

      /** 위규구분 Change Event */
      onChangeSearchOfendGbn();

      /** grid init */
      gridUtil.init();
    })
  });
  /*************************************
   * [위규구분 Change Event]
   * 위규구분 값에 따라서 위규내용 값 변경
   *************************************/
  const onChangeSearchOfendGbn = () => {

    $("#searchOfendGbn").on("change", function () {

      // 위규내용 초기화
      $("#searchOfendDetailGbn").empty();

      let code = "";
      const selectValue = $(this).val();

      if (selectValue === "") {
        $("#searchOfendDetailGbn").append('<option value="">::선택하세요::</option>');
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
        $.esutils.renderCode([{type: "select", grpCd: code, targetId: "searchOfendDetailGbn", blankOption: true}]);
      }
    });
  }

  function defaultValueSet() {
    $("#searchEmpId").val(global.empId);
    $("#empId").val(global.empId);
  }


</script>
<img src="/esecurity/assets/common/images/common/subTitle/secrt/title_364.png" />
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="securityForm">
            <input type="hidden" id="empId" name="empId" value=""/>
            <input type="hidden" id="searchEmpId" name="searchEmpId" value=""/>
            <input type="hidden" id="adminYn" name="adminYn" value="N"/>
            <input type="hidden" id="tid" name="tid" value=""/>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <colgroup>
                    <col width="15%"/>
                    <col width="35%"/>
                    <col width="15%"/>
                    <col width="35%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>위규구분</label></th>
                    <td>
                        <select id="searchOfendGbn" name="searchOfendGbn" style="width:230px;">

                        </select>
                    </td>
                    <th><label>위규 내용</label></th>
                    <td>
                        <select id="searchOfendDetailGbn" name="searchOfendDetailGbn" style="width:230px;">
                            <option value="">::선택하세요::</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label>위규자</label></th>
                    <td>
                        <input type="text" id="searchEmpNm" name="searchEmpNm" value="" style="width:230px;"/>
                    </td>
                    <th>등록자</th>
                    <td colspan="2">
                        <input type="text" id='searchRegEmpNm' name='searchRegEmpNm' style='width: 242px;'/>
                    </td>
                </tr>
                <tr>
                    <th><label>조치시행여부</label></th>
                    <td>
                        <input type="radio" id="searchActGbn" name="searchActGbn" class="border_none" value="" checked/><label for="searchActGbn">전체</label>
                        <input type="radio" id="searchActGbnY" name="searchActGbn" class="border_none" value="Y"/><label for="searchActGbnY">예</label>
                        <input type="radio" id="searchActGbnN" name="searchActGbn" class="border_none" value="N"/><label for="searchActGbnN">아니오</label>
                    </td>
                    <th><label>지적일시</label></th>
                    <td>
                        <input type="text" id="searchStrtDt" name="searchStrtDt" style="width: 86px;" value=""/>
                        ~
                        <input type="text" id="searchEndDt" name="searchEndDt" style="width: 86px;" value=""/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button type="button" id="searchBtn" style="width:50px;">검색</button></span>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->