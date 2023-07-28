<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    Map login = new HashMap();
    if (request.getSession().getAttribute("Login") != null) {
        login = (Map) request.getSession().getAttribute("Login");
    }
%>
<script type="text/javascript">

  let gridUtil;

  /***************************************************************************
   * 화면 onload 처리
   ***************************************************************************/
  $(document).ready(function () {

    initGrid();
    initDocument();
  });

  /***************************************************************************
   * Grid initializing
   ***************************************************************************/
  const initGrid = () => {
    gridUtil = new GridUtil({
      url: "/api/entmanage/carPassAdmin/safetyCarPassProgress/list"
      , ajaxMethod: 'post'
      , isPaging: true
      , excelUrl: "/api/entmanage/carPassAdmin/safetyCarPassProgress/excel"
      , excelFileName: "안전작업차량 출입현황_" + moment().format('YYYY-MM-DD')
      , gridOptions: {
        colData: [
          {headerName: "순번", field: "rowNum"},
          {headerName: "출입기간", field: "ioDt"},
          {headerName: "요청회사", field: "compNm"},
          {headerName: "신청자", field: "empNm"},
          {headerName: "처리상태", field: "apprResultNm"},
        ],
        onRowClicked
      },
      search: {
        formId: "formGrid",
        buttonId: "searchBtn",
        beforeSendCheck: () => {
          let result = false;
          const ioStrtDt = $("#searchStrtDt").val().replaceAll('-', '');
          const ioEndDt = $("#searchEndDt").val().replaceAll('-', '');
          if (!ioStrtDt) {
            alert('시작일을 선택해주세요.');
          } else if (!ioEndDt) {
            alert('종료일을 선택해주세요.');
          } else {
            result = true;
          }
          return result;
        },
        beforeSend: (formData) => {
          return {...formData, appRsn: "Z0361007"};
        }
      }
    });
  }

  /***************************************************************************
   * Grid callback function
   ***************************************************************************/
  const onRowClicked = ({originRowData}) => {
    const {seq, tmpcarAppNo} = originRowData;
    console.log("■■ originRowData ■■", originRowData);
    $.esutils.href("/entmanage/safetycarpass/view", {tmpcarAppNo, operateId: "ADMIN"});
  }

  /***************************************************************************
   * Document initializing
   ***************************************************************************/
  const initDocument = async () => {

    await fnGetCompList(); // 회사명 Select Option 조회
    await fnSetInitParams(); // 초기 파라미터 세팅
    await gridUtil.init(); // grid 조회
  }

  /***************************************************************************
   * 초기 검색조건 세팅
   ***************************************************************************/
  const fnSetInitParams = async () => {

    const divCd = global.divCd === "CP" ? "A028" : global.divCd === "YP" ? "A029" : global.divCd === "S2" ? "A041" : "";
    $("[name='searchDivCd'][value=" + divCd + "]")?.attr('checked', true);

    // 검색기간 설정
    $("#searchStrtDt").val($.esutils.getToday('-'));
    $("#searchEndDt").val($.esutils.getToday('-'));
    $.esutils.rangepicker([["[id=searchStrtDt]", "[id=searchEndDt]"]]);
  };

  /***************************************************************************
   * 요청회사 목록 조회
   ***************************************************************************/
  const fnGetCompList = async () => {

    const targetId = 'searchCompId';
    await $.esutils.renderCode([
      {
        targetId,
        url: "/api/common/organization/comp",
        nameProp: "compNm",
        valueProp: "compId",
        type: "select",
        filter: (d) => d.compId !== '1100000001'
      }
    ], () => {
      $("#" + targetId).prepend('<option value="" selected>전체</option>');
    });
  }

</script>
<!-- 검색영역 시작 -->
<div id="search_area">
    <!-- 검색 테이블 시작 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <caption>조회화면</caption>
                <colgroup>
                    <col width="14%"/>
                    <col width="36%"/>
                    <col width="14%"/>
                    <col width="36%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>요청회사</label></th>
                    <td name="searchComp">
                        <select id=searchCompId name="searchCompId" style="width:250px;">
                            <option value="">
                            </option>
                        </select>
                    </td>
                    <th><label>업체명(한글)</label></th>
                    <td>
                        <input type="text" id="ioCompKoNm" name="ioCompKoNm" style="width:235px;"/>
                    </td>
                </tr>
                <tr>

                </tr>
                <tr>
                    <th>신청자</th>
                    <td>
                        <input type="text" id="searchEmpNm" name="searchEmpNm" style="width:235px;"/>
                    </td>
                    <th>차량번호</th>
                    <td colspan="2">
                        <input type="text" id="searchCarNo" name="searchCarNo" style="width:235px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label>출입기간</label></th>
                    <td colspan="2">
                        <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:73px;"/>
                        &nbsp;~&nbsp;
                        <input type="text" id="searchEndDt" name="searchEndDt" style="width:73px;"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
        <!-- 버튼 시작 -->
        <div class="searchGroup">
            <div class="centerGroup">
                <span class="button bt_l1"><button type="button" id="searchBtn" style="width:80px;">검색</button></span>
            </div>
        </div>
        <!-- 버튼 종료 -->
    </div>
</div>
<!-- 검색영역 끝 -->
<div id="grid"></div>