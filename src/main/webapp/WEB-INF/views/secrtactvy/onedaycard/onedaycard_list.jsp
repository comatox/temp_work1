<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    gridUtil = new GridUtil({
      url: "/api/entmanage/empCard/admOnedayCard"
      , isPaging: true
      , pageSize: 10
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
            headerName: "등록일자"
            , field: "applyDt"
            , align: "center"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "반납처리"
            , field: "returnDtm"
            , align: "center"
            , formatter: function (cellValue, options, rowObject) {
              console.log('rowObject', rowObject);
              if (rowObject.status === "I") {
                return "<span class=\"button bt_add\"><button type=\"button\" style=\"width:40px;\" onclick=\"cardReturn('" + rowObject.empcardApplNo + "');\">반납</button></span>";
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
   * 엔터처리시 조회처리
   ***************************************************************************/
  function fn_press(event) {
    if (event.keyCode == 13) {
      cardList();
    }
  }

  /***************************************************************************
   * 일일사원증 반납
   ***************************************************************************/
  const cardReturn = function (empcardApplNo) {
    $("#empcardApplNo").val(empcardApplNo);

    let formParam = {};

    if (!confirm("반납 하시겠습니까?")) return;

    formParam.empcardApplNo = empcardApplNo;
    formParam.modBy = global.empId;

    $.ajax({
      url: global.contextPath + '/api/entmanage/empCard/admOnedayCard/return',
      type: 'post',
      data: JSON.stringify(formParam),
      dataType: "json",
      processData: false,
      cache: false,
      contentType: 'application/json',
      async: false,
      success: function (result) {
        if (result.status === 200) {
          alert("반납 되었습니다.");
          cardList();
        } else {
          alert("반납 중 에러가 발생 하였습니다.");
        }
      },
      error: function (error) {
        alert("등록에 실패했습니다.");
      }
    });
  };

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
   * 일일사원증 발급
   ***************************************************************************/
  function cardRegist() {
    $.esutils.href('/secrtactvy/onedaycard/request')
  }

  /***************************************************************************
   * 목록
   ***************************************************************************/
  const cardList = function () {
    $.esutils.href('/secrtactvy/onedaycard/list')
  };

</script>
<!-- wrap start-->
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
                                        <th><label for="applyStrtDt">발급일자</label></th>
                                        <td>
                                            <input type="text" id="applyStrtDt" name="applyStrtDt" style="width: 70px;" value=""/>
                                            <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgSApplyStrtDt"/>
                                            &nbsp;&nbsp;~&nbsp;&nbsp;
                                            <input type="text" id="applyEndDt" name="applyEndDt" style="width: 70px;" value=""/>
                                            <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgSApplyEndDt"/>
                                        </td>
                                        <th><label for="empGbn0">소속구분</label></th>
                                        <td>
                                            <input type="radio" id="empGbn0" name="empGbn" value="" class="border_none" checked/>
                                            <label for="empGbn0">전체</label>&nbsp;
                                            <input type="radio" id="empGbn1" name="empGbn" value="M" class="border_none"/>
                                            <label for="empGbn1">구성원</label>&nbsp;
                                            <input type="radio" id="empGbn2" name="empGbn" value="P" class="border_none"/>
                                            <label for="empGbn2">도급사</label>&nbsp;
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
                        <div style="float: none;margin-top:-17px;">
                            <table cellpadding="0" cellspacing="0" width="850px" border="0" class="view_board03">
                                <caption>기능버튼</caption>
                                <tbody>
                                <tr>
                                    <td align="right">
                                        <span class="button bt_s2">
                                            <button tabindex="1" onclick="cardRegist();" style="width:70px;" type="button">등록</button>
                                        </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="grid"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>