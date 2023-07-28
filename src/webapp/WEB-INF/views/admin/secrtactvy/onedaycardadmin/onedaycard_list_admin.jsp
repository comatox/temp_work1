<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script type="text/javascript">
  let gridUtil;

  $(document).ready(function () {
    const date = new Date();
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const dateStr = year + month + day;

    gridUtil = new GridUtil({
      url: "/api/entmanage/empCard/admOnedayCard"
      , isPaging: true
      , pageSize: 10
      , excelUrl: "/api/entmanage/empCard/admOnedayCard/excel"
      , excelFileName: "일일사원증 발급현황_" + dateStr
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , field: "rnum"
            , align: "center"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "발급일자"
            , field: "applyDt"
            , align: "center"
            , onCellClicked: onCellSelectCallback
            , formatter: function (cellValue, options, row) {
              return cellValue.split(" ")[0];
            }
          },
          {
            headerName: "소속구분"
            , field: "empGbn"
            , align: "center"
            , formatter: function (cellValue, options, row) {
              if (cellValue === 'M') {
                return "구성원"
              } else if (cellValue === 'P') {
                return "도급사"
              }
            }
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "성명 (사번/아이디)"
            , field: "mpEmpNm"
            , align: "center"
            , onCellClicked: onCellSelectCallback
            , formatter: function (cellValue, options, row) {
              return row.mpEmpNm + "\n(" + row.mpEmpId + ")";
            }
          },
          {
            headerName: "소속업체/부서"
            , field: "mpCompNm"
            , align: "center"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "전화번호"
            , field: "mpTelNo"
            , align: "center"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "카드번호"
            , field: "cardNo"
            , align: "center"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "반납일자"
            , field: "returnDtm"
            , align: "center"
            , formatter: function (cellValue, options, rowObject) {
              console.log('rowObject', rowObject);
              if (rowObject.status === "I") {
                return "-";
              } else {
                return rowObject.returnDtm;
              }
            },
          }
        ]
        , onRowClicked: function ({originRowData}) {
          console.log("onRowClick", rowData);
        }
      },
      search: {
        formId: "oneDayCardForm"
        , buttonId: "searchBtn"
      }
    });

    compList();

    /****************
     * date picker
     ****************/
    $.esutils.rangepicker([["[name=applyStrtDt]", "[name=applyEndDt]"]]);
    gridUtil.init();
  });

  const onCellSelectCallback = ({originRowData}) => {
    $.esutils.href("/secrtactvy/onedaycard/detail", {empcardApplNo: originRowData.empcardApplNo});
  }

  /***************************************************************************
   * 날짜 검증
   ***************************************************************************/
  function dateValidationCheck() {
    let applyStrtDt = $("#applyStrtDt").val();
    let applyEndDt = $("#applyEndDt").val();

    if (applyStrtDt !== "" && applyEndDt !== "") {
      if (applyStrtDt > applyEndDt) {
        alert("시작일이 종료일보다 큽니다.");
        $("#applyStrtDt").val("");
        $("#applyEndDt").val("");
        return false;
      }
    }
  }

  /***************************************************************************
   * 엔터처리시 조회처리
   ***************************************************************************/
  function fn_press(event) {
    if (event.keyCode == 13) {
      fn_goPage(1);
    }
  }

  /***************************************************************************
   * 전체 목록
   ***************************************************************************/
  function searchAll() {
    $("#applyStrtDt").val("");
    $("#applyEndDt").val("");
    $("#empGbn0").prop("checked", true);
    $("#empNm").val("");
    $("#cardNo").val("");

    cardList();
  }

  /***************************************************************************
   * 업체 콤보 리스트
   ***************************************************************************/
  const compList = function () {
    $.esutils.getApi("/api/common/organization/comp", null, function (result) {
      console.log(result);
      if (result && result.data) {
        let html = "<option value=\"\">전체</option>";
        html += result.data.filter(d => d.compId !== "1100000001")
        .map(d => `<option value="\${d.compId}">\${d.compNm}</option>`);
        $("[name=compId]").html(html);
      }
    });
  }

  /***************************************************************************
   * 목록
   ***************************************************************************/
  const cardList = function () {
    $.esutils.href('/secrtactvy/onedaycardadmin/list')

    compList();
  };
</script>

<div class="wrap">
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <img src="/esecurity/assets/common/images/common/subTitle/pass/title_139.png" alt="일일사원증 발급 및 현황"/>
                <!-- realContents start -->
                <div id="realContents">
                    <form name="oneDayCardForm" id="oneDayCardForm" method="post">
                        <input type="hidden" id="empcardApplNo" name="empcardApplNo" value=""/>
                        <input type="hidden" id="empId" name="empId" value=""/>
                        <input type="hidden" name="callback" value=""/>
                        <div id="search_area">
                            <!-- 검색 -->
                            <div class="search_content">
                                <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                                    <colgroup>
                                        <col width="14%"/>
                                        <col width="36%"/>
                                        <col width="14%"/>
                                        <col width="36%"/>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th><label for="compId">회사명</label></th>
                                        <td>
                                            <select id="compId" name="compId" style="width:250px;"></select>
                                        </td>
                                        <th><label for="applyStrtDt">발급일자</label></th>
                                        <td>
                                            <input type="text" id="applyStrtDt" name="applyStrtDt" style="width: 70px;" value=""/>
                                            <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgSApplyStrtDt"/>
                                            &nbsp;&nbsp;~&nbsp;&nbsp;
                                            <input type="text" id="applyEndDt" name="applyEndDt" style="width: 70px;" value=""/>
                                            <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgSApplyEndDt"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><label for="empNm">대상자</label></th>
                                        <td>
                                            <input type="text" id="empNm" name="empNm" value="" style="width:230px;"/>
                                        </td>
                                        <th><label for="cardNo">카드번호</label></th>
                                        <td>
                                            <input type="text" id="cardNo" name="cardNo" value="" style="width:230px;"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="searchGroup">
                                    <div class="centerGroup">
                                        <span class="button bt_l2"><button type="button" style="width:80px;" onclick="searchAll();">전체 목록</button></span>
                                        <span class="button bt_l1"><button type="button" id="searchBtn" style="width:50px;" onclick="dateValidationCheck();">검색</button></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="grid"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
