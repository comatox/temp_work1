<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Enumeration<String> attributes = request.getSession().getAttributeNames();
    while (attributes.hasMoreElements()) {
        String attribute = (String) attributes.nextElement();
        System.err.println(attribute + " : " + request.getSession().getAttribute(attribute) + "\n");
    }

    request.setCharacterEncoding("UTF-8");
    Map login = new HashMap();
    if (request.getSession().getAttribute("Login") != null) {
        login = (Map) request.getSession().getAttribute("Login");
    }
    else {
        response.sendRedirect("/main");
        return;
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
      url: "/api/entmanage/carPassAdmin/carPassAdminList/list"
      , ajaxMethod: 'post'
      , isPaging: true
      , excelUrl: "/api/entmanage/carPassAdmin/carPassAdminList/excel"
      , excelFileName: "차량출입 신청관리_" + moment().format('YYYY-MM-DD')
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , field: "rowNum"
          },
          {
            headerName: "요청회사"
            , field: "compNm"
          },
          {
            headerName: "신청구분"
            , field: "gubun"
          },
          {
            headerName: "신청자"
            , field: "empJwNm"
          },
          {
            headerName: "출입시작일"
            , field: "strtDt"
          },
          {
            headerName: "출입종료일"
            , field: "endDt"
          },
          {
            headerName: "신청일"
            , field: "crtDtm"
          },
          {
            headerName: "진행상태"
            , field: "apprResultNm"
          },
        ],
        onRowClicked
      },
      search: {
        formId: "formGrid",
        buttonId: "searchBtn",
        beforeSendCheck : ()=>{
          let result = false;
          const ioStrtDt = $("#searchStrtDt").val().replaceAll('-', '');
          const ioEndDt = $("#searchEndDt").val().replaceAll('-', '');
          if(!ioStrtDt){
            alert('시작일을 선택해주세요.');
          } else if(!ioEndDt){
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
  const onRowClicked = ({originRowData}) => {
    console.log("■■ originRowData ■■", originRowData);
    // $.esutils.href("/sysmanage/groupmanage/usermanage/view", {empId: rowData.empId});
  }

  const onCellClicked = ({originRowData}) => {
    const {gubunNo, seq, docId} = originRowData;
    console.log("■■ gubunNo, seq, docId ■■", gubunNo, seq, docId);
    if (gubunNo === '1') {
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

    } else {
      const param = {
        seq,
        docId
      };
      $.esutils.openPopup({
        url: "/entmanage/carpassadmin/iocarpassprogress/tmpconcar/popup"
        , param
        , width: "900"
        , height: "600"
        , scroll: "no"
      });
    }
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

    // const divCd = global.divCd === "CP" ? "A028" : global.divCd === "YP" ? "A029" : global.divCd === "S2" ? "A041" : "";
    $("[name='searchDivCd'][value=" + global.divCd + "]")?.attr('checked', true);
    fnGetGateList(global.divCd);

    // 검색기간 설정
    $("#searchStrtDt").val(moment().format("YYYY-MM-DD"));
    $("#searchEndDt").val(moment().add(7,"d").format("YYYY-MM-DD"));
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

</script>
<!-- 검색영역 시작 -->
<div id="search_area">
    <!-- 검색 테이블 시작 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01">
                <colgroup>
                    <col width="14%" />
                    <col width="36%" />
                    <col width="14%" />
                    <col width="36%" />
                </colgroup>
                <tbody>
                <tr>
                    <th><label>요청회사</label></th>
                    <td name = "searchComp">
                        <select id=searchCompId name="searchCompId" style="width:235px;">
                            <option value="">
                            </option>
                        </select>
                    </td>
                    <th><label>업체명(한글)</label></th>
                    <td>
                        <input type="text" id="searchCompNm" name="searchCompNm" style="width:213px;" />
                    </td>
                </tr>
                <tr>
                    <select name="searchGateId" id="searchGateId" style="width:235px;display:none;"/>
                    <th><label>사업장</label></th>
                    <td colspasn="3">
                        <input type="radio" name="searchDivCd" value="YP" class="border_none" onchange="javascript:onChangeDivCd( 'A029' );"/><label>이천</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="searchDivCd" value="CP" class="border_none" onchange="javascript:onChangeDivCd( 'A028' );"/><label>청주</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="searchDivCd" value="S2" class="border_none" onchange="javascript:onChangeDivCd( 'A041' );"/><label>분당(정자)</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
                <tr>
                    <th>출입기간</th>
                    <td>
                        <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:73px;"/>
                        &nbsp;~&nbsp;
                        <input type="text" id="searchEndDt" name="searchEndDt" style="width:73px;"/>
                    </td>

                    <th>차량번호</th>
                    <td>
                        <input type="text" id="searchCarNo" name="searchCarNo" style="width:213px;"/>
                    </td>

                </tr>
                <tr>
                    <th>신청자</th>
                    <td>
                        <input type="text" id="searchEmpNm" name="searchEmpNm" style="width:213px;"/>
                    </td>
                    <th>출입자</th>
                    <td>
                        <input type="text" id="searchIoEmpNm" name="searchIoEmpNm" style="width:213px;"/>
                    </td>
                </tr>
                <tr>
                    <th>처리상태</th>
                    <td colspan="3">
                        <input type="radio" name="applStat" value="" class="border_none" /><label>임시보관</label>&nbsp;&nbsp;
                        <input type="radio" name="applStat" value="10" class="border_none" /><label>검토중</label>&nbsp;&nbsp;
                        <input type="radio" name="applStat" value="1" class="border_none" /><label>승인</label>&nbsp;&nbsp;
                        <input type="radio" name="applStat" value="2" class="border_none" /><label>부결</label>&nbsp;&nbsp;
                        <input type="radio" name="applStat" value="20" class="border_none" /><label>처리완료</label>&nbsp;&nbsp;
                        <input type="radio" name="applStat" value="5" class="border_none" checked="checked"/><label>전체</label>
                    </td>
                </tr>
                <tr>
                    <th>신청구분</th>
                    <td colspan="3">
                        <input type="radio" name="searchGubunCode" value="1" class="border_none" /><label>방문객</label>&nbsp;&nbsp;
                        <input type="radio" name="searchGubunCode" value="2" class="border_none" /><label>상시</label>&nbsp;&nbsp;
                        <input type="radio" name="searchGubunCode" value="3" class="border_none" /><label>휴무일</label>&nbsp;&nbsp;
                        <input type="radio" name="searchGubunCode" value="4" class="border_none" /><label>임시</label>&nbsp;&nbsp;
                        <input type="radio" name="searchGubunCode" value="5" class="border_none" /><label>출장자</label>&nbsp;&nbsp;
                        <input type="radio" name="searchGubunCode" value="6" class="border_none" /><label>납품</label>&nbsp;&nbsp;
                        <input type="radio" name="searchGubunCode" value="" class="border_none" checked="checked" /><label>전체</label>&nbsp;&nbsp;
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