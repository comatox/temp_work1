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
      url: "/api/entmanage/carPassAdmin/ioCarPassProgress/list"
      , ajaxMethod: 'post'
      , isPaging: true
      , excelUrl: "/api/entmanage/carPassAdmin/ioCarPassProgress/excel"
      , excelFileName: "방문객차량 출입현황_" + moment().format('YYYY-MM-DD')
      , gridOptions: {
        colData: [
          {
            headerName: "요청회사"
            , field: "compNm"
          },
          {
            headerName: "구분"
            , field: "gubun"
          },
          {
            headerName: "방문일"
            , field: "ioDt"
          },
          {
            headerName: "성명"
            , field: "empNm"
            , onCellClicked
          },
          {
            headerName: "차량번호"
            , field: "carNo"
          },
          {
            headerName: "주차장소"
            , field: "vstPlcNm"
          },
          {
            headerName: "방문목적"
            , field: "vstObjNm"
          },
          {
            headerName: "입문"
            , field: "vstObjNm"
            , formatter: (cellValue, options, row) => {
              const {apprResult, inDtm} = row;
              let elem = "";
              if (apprResult === "1") {
                if (!inDtm) {
                  if($.esutils.checkAuth("1", "<%=login.get("AUTH")%>")){
                    elem = "<span class='button bt_s2 event_onclick_in' style=width:100px;'>입문</span>";
                  }
                } else {
                  elem = inDtm;
                }
              }
              return elem;
            }
            , event: {
              className: "event_onclick_in",
              onClicked: ({originRowData}) => fnInOutDtm(originRowData, 'IN'),
            }
          },
          {
            headerName: "출문"
            , field: "vstObjNm2"
            , formatter: (cellValue, options, row) => {
              const {inDtm, outDtm} = row;
              let elem = "";
              if (inDtm && !outDtm) {
                if($.esutils.checkAuth("1", "<%=login.get("AUTH")%>")) {
                  elem = "<span class='button bt_s2 event_onclick_out' style=width:100px;'>출문</span>";
                }
              } else if (inDtm && outDtm) {
                elem = outDtm;
              }
              return elem;
            }
            , event: {
              className: "event_onclick_out",
              onClicked: ({originRowData}) => fnInOutDtm(originRowData, 'OT'),
            }
          },
          {
            headerName: "진행결과"
            , field: "apprResultNm"
          },
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
        }
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
    $("#searchStrtDt").val($.esutils.getToday('-'));
    $("#searchEndDt").val($.esutils.getToday('-'));
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
    // 방문목적 > A040: 청주, A013 : 이천, 분당(정자)
    const vstObjCd = compGbn === "CP" ? "A040" : compGbn === "YP" ? "A013" : compGbn === "S2" ? "A013" : "";

    $.esutils.renderCode([
      {type: "select", grpCd: gateIdCd, targetId: "searchGateId"},
      {type: "select", grpCd: vstObjCd, targetId: "searchVstObj", blankOption: true}
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

  function fnInOutDtm(row, type) {
    const {seq, ioEmpId, ioDt} = row;
    const param = {
      ioType: type,
      vstcarApplNo: seq,
      ioEmpId,
      ioDt,
      userInfo: {empId: global.empId}
    };

    $.esutils.postApi('/api/entmanage/carPassAdmin/updateInOut', param, (response) => {
      const msg = response.message;
      if (msg === "OK") {
        if (type === "IN") {
          alert("입문처리 되었습니다.");
        } else if (type === "IC") {
          alert("입문취소 되었습니다.");
        } else if (type === "OT") {
          alert("출문처리 되었습니다.");
        } else if (type === "OC") {
          alert("출문취소 되었습니다.");
        }
        gridUtil.refresh();

      } else {
        alert("처리 중 오류가 발생하였습니다.\n관리자에게 문의 바랍니다.")
      }
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
                    <th>주차장소</th>
                    <td>
                        <select name="searchGateId" id="searchGateId" style="width:250px;"/>
                    </td>
                    <th><label>사업장</label></th>
                    <td>
                        <input type="radio" name="searchDivCd" value="A029" class="border_none" onchange="javascript:onChangeDivCd( 'A029' );"/><label>이천</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="searchDivCd" value="A028" class="border_none" onchange="javascript:onChangeDivCd( 'A028' );"/><label>청주</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="searchDivCd" value="A041" class="border_none" onchange="javascript:onChangeDivCd( 'A041' );"/><label>분당(정자)</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
                <tr>
                    <th><label>방문기간</label></th>
                    <td>
                        <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:73px;"/>
                        &nbsp;~&nbsp;
                        <input type="text" id="searchEndDt" name="searchEndDt" style="width:73px;"/>
                    </td>
                    <th>성명</th>
                    <td>
                        <input type="text" id="searchEmpNm" name="searchEmpNm" style="width:235px;"/>
                    </td>
                </tr>
                <tr>
                    <th>차량번호</th>
                    <td>
                        <input type="text" id="searchCarNo" name="searchCarNo" style="width:235px;"/>
                    </td>
                    <th>방문목적</th>
                    <td>
                        <select name="searchVstObj" id="searchVstObj" style="width:235px;"/>
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