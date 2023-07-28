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
      url: "/api/entmanage/carPassAdmin/tmpcarPassProgress/list"
      , ajaxMethod: 'post'
      , isPaging: true
      , excelUrl: "/api/entmanage/carPassAdmin/tmpcarPassProgress/excel"
      , excelFileName: "임시차량 출입현황_" + moment().format('YYYY-MM-DD')
      , gridOptions: {
        colData: [
          {headerName: "순번", field: "rowNum"},
          {headerName: "요청회사", field: "compNm"},
          {headerName: "신청일자", field: "crtDtm"},
          {headerName: "신청자", field: "empNm"},
          {headerName: "출입기간", field: "ioDt"},
          {headerName: "주차장소", field: "gateNm"},
          {headerName: "출입목적", field: "appNm"},
          {headerName: "처리상태", field: "apprResultNm"},
        ]
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
      }
    });
  }

  /***************************************************************************
   * Grid callback function
   ***************************************************************************/
  const onCellClicked = ({originRowData}) => {
    const {seq, docId} = originRowData;
    const param = {
      vstcarApplNo: seq,
      docId
    };
    $.esutils.openPopup({
      url: "/entmanage/carpassadmin/iocarpassprogress/carpassreqst/popup"
      , param
      , width: "900"
      , height: "600"
      , scroll: "no"
    });
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
    fnGetGateList(global.divCd);

    // 검색기간 설정
    $("#searchStrtDt").val(moment().add(-15,"d").format("YYYY-MM-DD"));
    $("#searchEndDt").val(moment().add(15,"d").format("YYYY-MM-DD"));
    $.esutils.rangepicker([["[id=searchStrtDt]", "[id=searchEndDt]"]]);
  };

  /***************************************************************************
   * 사업장 onChange
   ***************************************************************************/
  const onChangeDivCd = (searchDivCd) => {
    const compGbn = searchDivCd === "A028" ? "CP" : searchDivCd === "A029" ? "YP" : searchDivCd === "A041" ? "S2" : "";
    fnGetGateList(compGbn, true)
  };

  /***************************************************************************
   * 사업장 onChange
   ***************************************************************************/
  const fnGetGateList = (compGbn, reload) => {

    // 주차장소 > A028: 청주, A029 : 이천, A041: 분당(정자)
    const gateIdCd = compGbn === "CP" ? "A028" : compGbn === "YP" ? "A029" : compGbn === "S2" ? "A041" : "";

    $.esutils.renderCode([
      {type: "select", grpCd: gateIdCd, targetId: "searchGateId"},
    ], function () {
      $("#searchGateId").prepend('<option value="" selected>전체</option>');
      if (reload) {
        gridUtil.searchData();
      }
    });
  }

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
            <table cellpadding="0" cellspacing="0" caption="조회화면입니다" border="0" class="view_board01">
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
                    <th><label>신청기간</label></th>
                    <td>
                        <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:73px;"/>
                        &nbsp;~&nbsp;
                        <input type="text" id="searchEndDt" name="searchEndDt" style="width:73px;"/>
                    </td>
                    <th>주차장소</th>
                    <td>
                        <select name="searchGateId" id="searchGateId" style="width:235px;"/>
                    </td>
                </tr>
                <tr>
                    <th>신청자</th>
                    <td>
                        <input type="text" id="searchEmpNm" name="searchEmpNm" style="width:235px;"/>
                    </td>
                    <th>차량번호</th>
                    <td>
                        <input type="text" id="searchCarNo" name="searchCarNo" style="width:235px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label>사업장</label></th>
                    <td>
                        <input type="radio" name="searchDivCd" value="A029" class="border_none" onchange="javascript:onChangeDivCd( 'A029' );"/><label>이천</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="searchDivCd" value="A028" class="border_none" onchange="javascript:onChangeDivCd( 'A028' );"/><label>청주</label>&nbsp;&nbsp;&nbsp;&nbsp;
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