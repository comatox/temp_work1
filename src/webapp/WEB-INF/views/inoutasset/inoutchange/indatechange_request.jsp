<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
  let esecurityInoutasset;
  let esecurityApproval;
  let viewInfo;
  const inoutApplNo = ${param.inoutApplNo};

  $(document).ready(function () {
    $.esutils.datepicker(["[name=requestindate]"]);

    esecurityInoutasset = new EsecurityInoutasset(inoutApplNo);
    esecurityInoutasset.inoutwrite("inoutwriteArea", [
      {all: "compNm"},
      {left: "inoutserialno", right: "empNm"},
      {left: "articlekndnoNm", right: "articlegroupnm"},
      {left: "inoutkndname", right: "writedate"},
      {left: "companynoNm", right: "returncompanyareakndNm"},
      {left: "sendYn", right: "indate"},
      {left: "outdate", right: "realindate"},
      {left: "outcompanykndNm", right: "prno"},
      {left: "outreasonidNm", right: "outreasonsubkndNm"},
      {all: "inoutetc"},
    ]);
    esecurityInoutasset.article("articleArea");

    esecurityInoutasset.callback(function (data) {
      console.log(data);
      viewInfo = data.viewInfo;
      const indatedelayknd = data.viewInfo.indatedelayknd;
      if (!indatedelayknd || indatedelayknd === "0" || indatedelayknd === "4" || indatedelayknd === "5" || indatedelayknd === "2") {
        $("#indatedelaykndBtn").show();
      } else {
        $("#indatedelaykndBtn").hide();
      }

      $("[name=beforeindate]").val(data.viewInfo.indate);

      const gridUtil = new GridUtil({
        userData: data.history.indate
        , gridId: "historyArea"
        , isPaging: false
        , gridOptions: {
          width: 900,
          colData: [
            {
              headerName: "순번"
              , field: "rowNum"
              , width: "5%"
            },
            {
              headerName: "기존 반입일"
              , field: "beforeindate"
              , width: "10%"
            },
            {
              headerName: "변경 반입일"
              , field: "requestindate"
              , width: "10%"
            },
            {
              headerName: "결재자"
              , field: "requestEmpNm"
              , width: "10%"
            },
            {
              headerName: "승인/부결 사유"
              , field: "etc"
              , width: "55%"
            },
            {
              headerName: "결재상태"
              , field: "approvalstateNm"
              , width: "10%"
            },
          ]
        }
      });
      gridUtil.init();
    });

    esecurityApproval = new EsecurityApproval("approvalArea");
    esecurityApproval.form({schemaNm: "INDATE_CHANGE"});
  });

  function fn_approval() {
    if (fn_validation()) {
      if (confirm("상신하시겠습니까?")) {
        let param = $.esutils.getFieldsValue($('#saveForm'));
        const mergedParam = {
          ...viewInfo
          , ...param
          , ...esecurityApproval.getApproval()
          , acIp: global.acIp
          , applEmpId: global.empId
        };
        $.esutils.postApi("/api/inoutasset/inoutchange/indatechange", mergedParam, function (result) {
          if (result && result.data) {
            alert("결재문서 작성이 완료되었습니다.");
            fn_list();
          } else {
            alert("반입일자 연장신청 중 오류가 발생하였습니다.");
          }
        });
      }
    }
  }

  function fn_validation() {
    var toDay = $.esutils.getToday("");
    var beforeindate = $("#beforeindate").val().replaceAll("-", "");
    var requestindate = $("#requestindate").val().replaceAll("-", "");

    // 요청반입일
    if ($("#requestindate").val() == null || $("#requestindate").val() == "") {
      alert("요청 반입일을 입력하세요.");
      $("#requestindate").focus();
      return false;
    }

    if (requestindate < toDay) {
      alert("요청 반입일은 오늘날짜 이후로 지정하세요.");
      $("#requestindate").val("");
      $("#requestindate").focus();
      return false;
    }
    if (requestindate <= beforeindate) {
      alert("요청 반입일은 기존반입일 이후로 지정하세요.");
      $("#requestindate").val("");
      $("#requestindate").focus();
      return false;
    }

    // 지연 사유
    if ($("#delayetc").val() == null || $("#delayetc").val() == "") {
      alert("지연 사유를 입력하세요.");
      $("#delayetc").focus();
      return false;
    }

    if ($("#delayetc").val().length > 500) {
      alert("지연 사유는 200자 이내로 작성하세요.");
      $("#delayetc").focus();
      return false;
    }

    return true;
  }

  function fn_list() {
    $.esutils.href("/inoutasset/inoutchange/indatechange/list");
  }
</script>
<div class="buttonGroup">
    <div class="leftGroup">
        <span class="button bt_s2"><button type="button" style="width: 50px;" onclick="javascript:fn_list();">목록</button></span>&nbsp;&nbsp;
        <span class="button bt_s1"><button type="button" style="width: 50px;" onclick="javascript:fn_approval('REPORT');">상신</button></span>
    </div>
</div>
<div id="inoutwriteArea"></div>
<div id="articleArea"></div>
<h1 class="txt_title01">반입일자변경 신청이력</h1>
<div id="historyArea"></div>
<h1 class="txt_title01">반입일자변경 신청</h1>
<form id="saveForm" name="saveForm" method="post">
    <table cellpadding="0" cellspacing="0" border="0" class="view_board">
        <colgroup>
            <col width="14%"/>
            <col width="36%"/>
            <col width="14%"/>
            <col width="36%"/>
        </colgroup>
        <tbody>
        <tr>
            <th>기존 반입일</th>
            <td>
                <input type="text" name="beforeindate" id="beforeindate" value="" readonly="readonly" style="width:120px;"/>
            </td>
            <th>요청 반입일<span class="necessary">&nbsp;</span></th>
            <td>
                <input type="text" name="requestindate" id="requestindate" value="" style="width:120px;"/>
            </td>
        </tr>
        <tr>
            <th>지연 사유<span class="necessary">&nbsp;</span></th>
            <td colspan="3">
                <textarea id="delayetc" name="delayetc" style="width: 90%; height : 85px;"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
</form>
<div id="approvalArea"></div>
<div class="buttonGroup">
    <div class="leftGroup">
        <span class="button bt_s2"><button type="button" style="width: 50px;" onclick="javascript:fn_list();">목록</button></span>&nbsp;&nbsp;
        <span class="button bt_s1"><button type="button" style="width: 50px;" onclick="javascript:fn_approval('REPORT');">상신</button></span>
    </div>
</div>