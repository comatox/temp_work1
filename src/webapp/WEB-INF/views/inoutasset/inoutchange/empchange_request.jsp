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

      $("#beforeempNoTd").text(data.viewInfo.empNm);
      $("[name=beforeempNo]").val(data.viewInfo.empNo);
      $("[name=beforedeptCd]").val(data.viewInfo.deptCd);

      const gridUtil = new GridUtil({
        userData: data.history.emp
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
              headerName: "기존 담당자"
              , field: "beforeEmpNm"
              , width: "10%"
            },
            {
              headerName: "변경 담당자"
              , field: "changeEmpNm"
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
    esecurityApproval.form({schemaNm: "INOUT_EMP_CHANGE"});

    $("#findEmpBtn").on("click", function () {
      $.esutils.openEmpPopup({
        empNm: $("[name=changeempNm]").val(),
        fnCallback: function ({rowData}) {
          $("[name=changeempNm]").val(rowData.empNm);
          $("[name=changeempNo]").val(rowData.empId);
          $("[name=changedeptCd]").val(rowData.deptId);
        }
      })
    });
    $("[name=changeempNm]").on("keypress", function (e) {
      e.preventDefault();
      if (e.keyCode === 13) {
        $("#findEmpBtn").trigger("click");
      }
    });
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
        $.esutils.postApi("/api/inoutasset/inoutchange/empchange", mergedParam, function (result) {
          if (result && result.data) {
            alert("결재문서 작성이 완료되었습니다.");
            fn_list();
          } else {
            alert("담당자 변경요청 중 오류가 발생하였습니다.");
          }
        });
      }
    }
  }

  function fn_validation() {
    if ($("#changeempNo").val() == null || $("#changeempNo").val() == "") {
      alert("변경할 담당자를 입력하세요.");
      $("#findEmpBtn").trigger("click");
      return false;
    }
    if ($("#changeempNo").val() == $("#beforeempNo").val()) {
      alert("변경하려는 담당자가 기존담당자와 같습니다.");
      $("#changeempNm").val("");
      $("#changeempNo").val("");
      $("#changedeptCd").val("");
      return false;
    }
    if ($("#dmchangeetc").val() == null || $("#dmchangeetc").val() == "") {
      alert("변경사유를입력하세요.");
      $("#dmchangeetc").focus();
      return false;
    }
    if ($("#dmchangeetc").val().length > 500) {
      alert("변경사유는 200자 이내로 작성하세요.");
      $("#dmchangeetc").focus();
      return false;
    }
    return true;
  }

  function fn_list() {
    $.esutils.href("/inoutasset/inoutchange/empchange/list");
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
<h1 class="txt_title01">담당자변경 신청이력</h1>
<div id="historyArea"></div>
<h1 class="txt_title01">담당자변경 신청</h1>
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
            <th>기존 담당자</th>
            <td id="beforeempNoTd"></td>
            <th>변경 담당자<span class="necessary">&nbsp;</span></th>
            <td>
                <input type="text" name="changeempNm" id="changeempNm" value="" style="width:220px;"/>
                <input type="hidden" name="changeempNo" id="changeempNo" value=""/>
                <input type="hidden" name="changedeptCd" id="changedeptCd" value=""/>
                <input type="hidden" name="beforeempNo" id="beforeempNo" value=""/>
                <input type="hidden" name="beforedeptCd" id="beforedeptCd" value=""/>
                <a href="javascript:void(0);" id="findEmpBtn" class="btn_type01"><span>확인</span></a>
            </td>
        </tr>
        <tr>
            <th>변경사유<span class="necessary">&nbsp;</span></th>
            <td colspan="3">
                <textarea id="dmchangeetc" name="dmchangeetc" style="width: 90%; height : 85px;"></textarea>
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