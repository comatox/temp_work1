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
      url: "/api/secrtactvy/securityAdminViolation/ioViolation/list"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "적발 사업장"
            , field: "actCompIdNm"
            , align: "center"
          },
          {
            headerName: "지적 일시"
            , field: "ofendDt"
            , align: "center"
            , formatter: function (_, __, row) {
              const ofendDt = row.ofendDt ?? "";
              const ofendTm = row.ofendTm ?? "";
              return ofendDt + "<br/>" + ofendTm;
            }
          },
          {
            headerName: "위규자"
            , field: "ofendEmpNm"
            , align: "center"

          },
          {
            headerName: "위규 내용"
            , field: "ofendGnmNm"
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
            headerName: "담당자"
            , field: "actEmpNm"
            , align: "center"
          },
        ]
        , onRowClicked: function ({originRowData}) {
          $.esutils.href("/secrtactvy/securityadminviolation/ioviolation/detail", {scIoDocNo: originRowData.scIoDocNo});
        }
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    /*******************************
     * [위규구분 SelectBox Setting]
     *******************************/
    $.esutils.renderCode([{type: "select", grpCd: "C052", targetId: "searchOfendGbn", blankOption: true}], function () {
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
        $.esutils.renderCode([{type: "select", grpCd: code, targetId: "searchOfendDetailGbn"}]);
      }
    });
  }

  /***************
   * [기본값 설정]
   ***************/
  function defaultValueSet() {
    $("#searchEmpId").val(global.empId);
    $("#empId").val(global.empId);
  }


</script>

<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <input type="hidden" id="empId" name="empId" value="" />
            <input type="hidden" id="searchEmpId" name="searchEmpId" value="" />
            <input type="hidden" id="adminYn" name="adminYn" value="N" />
            <input type="hidden" id="tid" name="tid" value="" />
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
                    <th><label>지적일시</label></th>
                    <td>
                        <input type="text" id="searchStrtDt" name="searchStrtDt" style="width: 86px;" value=""/>
                        ~
                        <input type="text" id="searchEndDt" name="searchEndDt" style="width: 86px;" value=""/>
                    </td>
                </tr>
                <tr>
                    <th><label>조치시행여부</label></th>
                    <td>
                        <input type="radio" id="searchActGbn" name="searchActGbn" class="border_none" value="" checked/><label for="searchActGbn">전체</label>
                        <input type="radio" id="searchActGbnY" name="searchActGbn" class="border_none" value="Y"/><label for="searchActGbnY">예</label>
                        <input type="radio" id="searchActGbnN" name="searchActGbn" class="border_none" value="N"/><label for="searchActGbnN">아니오</label>
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