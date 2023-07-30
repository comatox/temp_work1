<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
  let esecurityInoutasset;
  let esecurityApproval;
  let viewInfo;
  const inoutApplNo = ${param.inoutApplNo};

  $(document).ready(function () {
    $.esutils.datepicker(["[name=realindate]"]);

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
        userData: data.history.finish
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
              headerName: "구분"
              , field: "requestCodeNm"
              , width: "10%"
            },
            {
              headerName: "요청자"
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
    esecurityApproval.form({schemaNm: "FINISH_CHANGE"});

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
        $.esutils.multipartSubmit("/api/inoutasset/inoutchange/finishchange", $('#saveForm')[0], formData => {
          Object.entries(viewInfo).forEach(([k ,v]) => {
            formData.append(k, v);
          });
          formData.append("acIp", global.acIp);
          formData.append("applEmpId", global.empId);
          formData.append('approval', JSON.stringify(esecurityApproval.getApproval().approval));
          return formData;
        }, function(result) {
          if (result && result.data) {
            alert("결재문서 작성이 완료되었습니다.");
            fn_list();
          } else {
            alert("물품반입확인서 신청 중 오류가 발생하였습니다.");
          }
        });
      }
    }
  }

  function fn_validation() {
    if($("#requestCode").val() == null || $("#requestCode").val() == "") {
      alert("요청유형을 입력하세요.");
      $("#request_code").focus();
      return false;
    }

    if($("#realindate").val() == null || $("#realindate").val() == "") {
      alert("반입일자를 입력하세요.");
      return false;
    }

    var toDay = $.esutils.getToday("");
    var REALINDATE  = $("#realindate").val().replaceAll("-", "");
    if(REALINDATE > toDay) {
      alert("반입일자는 오늘날짜 이전으로 지정하세요.");
      $("#realindate").val("");
      return false;
    }

    if($("#dmchangeetc").val() == null || $("#dmchangeetc").val() == "") {
      alert("의뢰사유를 입력하세요.");
      $("#dmchangeetc").focus();
      return false;
    }

    if($("#dmchangeetc").val().length > 500) {
      alert("의뢰사유는 200자 이내로 작성하세요.");
      $("#dmchangeetc").focus();
      return false;
    }
    return true;
  }

  function fn_list() {
    $.esutils.href("/inoutasset/inoutchange/finishchange/list");
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
<h1 class="txt_title01">물품반입확인서 신청이력</h1>
<div id="historyArea"></div>
<h1 class="txt_title01">물품반입확인서 신청</h1>
<form id="saveForm" name="saveForm" method="post">
    <table cellpadding="0" cellspacing="0" border="0" class="view_board">
        <colgroup>
            <col width="16%"/>
            <col width="34%"/>
            <col width="16%"/>
            <col width="34%"/>
        </colgroup>
        <tbody>
        <tr>
            <th>요청유형<span class="necessary">&nbsp;</span></th>
            <td id="requestCodeNmTd">
                <select name="requestCode" id="requestCode">
                    <option value="">선택하세요.</option>
                    <option value="D0040001">입문 시 반입확인 미 실시(통문)</option>
                    <option value="D0040002">입문 시 물품접수소 확인 미 실시(자재)</option>
                    <option value="D0040003">반입증 분실</option>
                </select>
            </td>
            <th>반입일자<span class="necessary">&nbsp;</span></th>
            <td id="realindateTd">
                <input type="text" id="realindate" name="realindate" style="width: 70px;" value="" />
            </td>
        </tr>
        <tr>
            <th>의뢰사유<span class="necessary">&nbsp;</span></th>
            <td colspan="3">
                <textarea id="dmchangeetc" name="dmchangeetc" style="width: 90%; height : 85px;"></textarea>
            </td>
        </tr>
        <tr>
            <th>반입확인 증명사진</th>
            <td colspan="3">
                <input type="file" name="file1" id="file1" value="" />
                <%--<input type="hidden" name="filename1" id="filename1" value="" />
                <input type="hidden" name="filepath1" id="filepath1" value="" />
                <input type="hidden" name="fileid1" id="fileid1" value="" />--%>
            </td>
        </tr>
        <tr>
            <th>관련서류</th>
            <td colspan="3">
                <input type="file" name="file2" id="file2" value="" />
                <%--<input type="hidden" name="filename2" id="filename2" value="" />
                <input type="hidden" name="filepath2" id="filepath2" value="" />
                <input type="hidden" name="fileid2" id="fileid2" value="" />--%>
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