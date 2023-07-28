<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script type="text/javascript">
  let gridUtil;

  $(document).ready(function () {
    gridUtil = new GridUtil({
      url: "/api/secrtactvy/securityRectifyPlan/list"
      , isPaging: true
      , pageSize: 10
      , gridOptions: {
        colData: [
          {
            headerName: "지적 일시"
            , field: "ofendDt"
            , align: "center"
            , formatter: function (cellValue, options, row) {
              return row.ofendDt + "\n" + row.ofendTm;
            }
          },
          {
            headerName: "안내 일시"
            , field: "actDt"
            , align: "center"
          },
          {
            headerName: "성명"
            , field: "ofendEmpNm"
            , align: "center"
          },
          {
            headerName: "위규 내용"
            , field: "ofendGbnNm"
            , align: "center"
            , formatter: function (cellValue, options, row) {
              return row.ofendGbnNm + " - " + row.ofendDetailGbnNm;
            }
          },
          {
            headerName: "위규 조치"
            , field: "actDoNm"
            , align: "center"
          },
          {
            headerName: "제출여부"
            , field: "corrPlanSendYnNm"
            , align: "center"
          },
          {
            headerName: "경과일"
            , field: "passDate"
            , align: "center"
          },
          {
            headerName: "14일\n초과여부"
            , field: "over14Yn"
            , align: "center"
          },
          {
            headerName: "처리상태"
            , field: "apprResultNm"
            , align: "center"
          }
        ]
        , onRowClicked: function ({originRowData}) {
          goView(originRowData);
        }
      },
      search: {
        formId: "rectifyPlanForm"
        , buttonId: "searchBtn"
      }
    });

    /****************
     * date picker
     ****************/
    $.esutils.rangepicker([["[name=searchStrtDt]", "[name=searchEndDt]"]]);
    gridUtil.init();
  });

  /********************************************
   * date validation check
   ********************************************/
  function dateValidationCheck() {
    let ioStrtDt = $("#searchStrtDt").val();
    let ioEndDt = $("#searchEndDt").val();

    if (ioStrtDt !== "" && ioEndDt !== "") {
      if (ioStrtDt > ioEndDt) {
        alert("시작일이 종료일보다 큽니다.");
        $("#searchStrtDt").val("");
        $("#searchEndDt").val("");
        return false;
      }
    }
  }

  function goView(data) {
    let scDocNo = data.scDocNo;
    let scCorrPlanNo = data.scCorrPlanNo;
    let apprResult = data.apprResult;
    let apprStat = data.apprStat;
    let docId = data.docId;
    let actDo = data.actDo;
    let ofendEmpId = data.ofendEmpId;

    /*
    C0280001	구성원 확인
    C0280002	시정계획서 징구
    C0280003	경고장 발송
    C0280004	인사징계 의뢰
    C0280005	타사 영업비밀 회수
    C0280006	시정공문 징구
    C0280007	영구 출입정지
    C0280008	1개월 출입정지
    C0280009	거래정지
    C0280010	형사고발
    C0280011	외부인 확인
    C0281001	미허가 장소 무단 출입
    C0281002	출입통제 및 보안시스템 우회 출입
    */

    if ("C0280002" === actDo || "C0280003" === actDo || "C0280004" === actDo) {
      if (apprResult === "99" && apprStat === "99") {
        //징계의뢰 계획서는 팀장이 작성
        if ("C0280004" === actDo && ofendEmpId === global.empId) {
          $.esutils.href("/secrtactvy/securityrectifyplan/rectifyplan/detail", {scDocNo, scCorrPlanNo, docId, writeYn: 'N'});
        } else {
          console.log('정재민');
          $.esutils.href("/secrtactvy/securityrectifyplan/rectifyplan/request", {scDocNo, scCorrPlanNo, docId});
        }
      } else if (apprResult === "2" && apprStat === "20") {
        if (actDo === "C0280002") {
          alert("부결 된 시정계획서 입니다.\n재상신 시 기존 결재 정보와 시정계획서는 삭제됩니다.");
        } else if (actDo === "C0280003") {
          alert("부결 된 경고장 개선계획서 입니다.\n재상신 시 기존 결재 정보와 경고장 개선계획서는 삭제됩니다.");
        } else if ("C0280004" === actDo) {
          alert("부결 된 징계의뢰 계획서 입니다.\n재상신 시 기존 결재 정보와 징계의뢰 계획서는 삭제됩니다.");
        }
        $.esutils.href("/secrtactvy/securityrectifyplan/rectifyplan/request", {scDocNo, scCorrPlanNo, docId});
      } else if (apprResult === "1003" && apprStat === "1003") {
        $.esutils.href("/secrtactvy/securityrectifyplan/rectifyplan/detail2", {scDocNo, scCorrPlanNo, docId});
      } else {
        $.esutils.href("/secrtactvy/securityrectifyplan/rectifyplan/detail", {scDocNo, scCorrPlanNo, docId});
      }
    } else {
      $.esutils.href("/secrtactvy/securityrectifyplan/rectifyplan/detail2", {scDocNo, scCorrPlanNo, docId});
    }
  }

</script>

<!-- wrap start-->
<div class="wrap">
    <!-- contentWrap start-->
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <!-- <h1 class="title">시정계획서 작성 현황</h1> -->
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_357.png"/>
                <!-- realContents start -->
                <div id="realContents">
                    <form id="rectifyPlanForm" name="rectifyPlanForm" method="post">
                        <input type="hidden" id="adminYn" name="adminYn" value="N"/>
                        <!-- 검색영역 시작 -->
                        <div id="search_area">
                            <!-- 검색 테이블 시작 -->
                            <div class="search_content">
                                <table cellpadding="0" cellspacing="0" caption="조회화면입니다" border="0" class="view_board01">
                                    <tr>
                                        <th><label for="applStat5">처리상태</label></th>
                                        <td>
                                            <input type="radio" id="applStat1003" name="applStat" value="1003" class="border_none"/>
                                            <label for="applStat1003">해당없음</label>&nbsp;&nbsp;
                                            <input type="radio" id="applStat99" name="applStat" value="99" class="border_none"/>
                                            <label for="applStat99">미작성</label>&nbsp;&nbsp;
                                            <input type="radio" id="applStat10" name="applStat" value="10" class="border_none"/>
                                            <label for="applStat10">검토중</label>&nbsp;&nbsp;
                                            <input type="radio" id="applStat1" name="applStat" value="1" class="border_none"/>
                                            <label for="applStat1">승인</label>&nbsp;&nbsp;
                                            <input type="radio" id="applStat2" name="applStat" value="2" class="border_none"/>
                                            <label for="applStat2">부결</label>&nbsp;&nbsp;
                                            <input type="radio" id="applStat5" name="applStat" value="5" class="border_none" checked="checked"/>
                                            <label for="applStat5">전체</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><label for="searchStrtDt">지적일시</label></th>
                                        <td colspan="2">
                                            <input type="text" id="searchStrtDt" name="searchStrtDt" style="width:69px;"/>
                                            <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="searchStrtDtImg"/>
                                            ~
                                            <input type="text" id="searchEndDt" name="searchEndDt" style="width:69px;"/>
                                            <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="searchEndDtImg"/>
                                        </td>
                                    </tr>
                                </table>
                                <!-- 버튼 시작 -->
                                <div class="searchGroup">
                                    <div class="centerGroup">
                                        <span class="button bt_l1"><button type="button" id="searchBtn" style="width:80px;" onclick="dateValidationCheck();">검색</button></span>
                                    </div>
                                </div>
                                <!-- 버튼 종료 -->
                            </div>
                        </div>

                        <!-- 기능버튼 -->
                        <div style="float: none;margin-top:-17px;">
                            <table cellpadding="0" cellspacing="0" width="850px" border="0" class="view_board03">
                                <caption>기능버튼</caption>
                                <tbody>
                                <tr height="30px">
                                    <td align="left">
                                        <font color="blue"><b>※ 해당 위규내용을 클릭하여, 시정계획서 또는 개선계획서를 제출할 수 있음</b></font>
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