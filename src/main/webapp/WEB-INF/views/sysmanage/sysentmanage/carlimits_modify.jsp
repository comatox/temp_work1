<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $(document).ready(function () {
    $.esutils.rangepicker([["[name=denyStrtDt]", "[name=denyEndDt]"]]);

    const carDenyNo = "${param.carDenyNo}";
    if (carDenyNo) {
      $("[name=carDenyNo]").val(carDenyNo);

      $.esutils.renderData(
          "passForm",
          `/api/sysmanage/sysmanage/envrEntmng/carLimits/view/\${carDenyNo}`,
          (data) => {
            if (data.denyEndDt === "9999-12-31") {
              $("#allLimit").prop("checked", true);
            }
          }, {loading: true});
    }

    $("[name=crtBy]").val(global.empId);
    $("[name=modBy]").val(global.empId);
  });

  function fn_list() {
    $.esutils.href("/sysmanage/sysentmanage/carlimits/list");
  }

  function fn_save() {
    if (fn_validation()) {
      if (confirm("저장하시겠습니까?")) {
        const param = $.esutils.getFieldsValue($('#passForm'));
        const carDenyNo = $("#carDenyNo").val();

        let apiUrl = "/api/entmanage/envrEntmng/carLimits";
        if (carDenyNo) {
          apiUrl = `/api/entmanage/envrEntmng/carLimits/\${carDenyNo}`;
        }

        $.esutils.postApi(apiUrl, param, function (result) {
          if (result && result.data) {
            alert("저장하였습니다.");
            fn_list();
          } else {
            alert("오류가 발생하였습니다.");
          }
        });
      }
    }
  }

  function fn_validation() {
    if ($("#carNo").val() == null || $("#carNo").val() == "") {
      alert("차량번호는 필수 항목입니다.");
      $("#carNo").focus();
      return false;
    }

    // 시작일자
    if ($("#denyStrtDt").val() == null || $("#denyStrtDt").val() == "") {
      alert("제한기간 시작일자는 필수 항목입니다.");
      $("#denyStrtDt").focus();
      return false;
    }

    // 종료일자
    if ($("#denyEndDt").val() == null || $("#denyEndDt").val() == "") {
      alert("제한기간 종료일자는 필수 항목입니다.");
      $("#denyEndDt").focus();
      return false;
    }

    var toDay = getToday(""); // yyyymmdd

    var ioStrtDt = $("#denyStrtDt").val().replaceAll("-", "");
    var ioEndDt = $("#denyEndDt").val().replaceAll("-", "");

    if (ioStrtDt < toDay) {
      alert("시작일자는 오늘날짜 이후로 지정하세요.");
      $("#denyStrtDt").val("");
      $("#denyStrtDt").focus();
      return false;
    }
    if (ioStrtDt > ioEndDt) {
      alert("시작일자가 종료일자보다 큽니다.");
      $("#denyStrtDt").val("");
      $("#denyEndDt").val("");
      $("#denyStrtDt").focus();
      return false;
    }

    var delYn = "";
    $("input:radio[name=delYn]").each(function (index, value) {
      if ($(this).attr("checked")) {
        delYn = $(this).attr("value");
      }
    });
    if (delYn == null || delYn == "") {
      alert("제한 구분은 필수 항목입니다.");
      return false;
    }

    if ($("#denyRsn").val() == null || $("#denyRsn").val() == "") {
      alert("제한 사유는 필수 항목입니다.");
      $("#denyRsn").focus();
      return false;
    }

    if ($("#denyRsn").val().length > 500) {
      alert("제한 사유는 500자이내로 작성하세요.");
      return false;
    }
    return true;
  }

  function fn_date() {
    if ($("#allLimit").attr("checked") == "checked") {
      $("#denyStrtDt").val(getToday("-"));
      $("#denyEndDt").val("9999-12-31");
    } else {
      $("#denyStrtDt").val("");
      $("#denyEndDt").val("");
    }
  }
</script>
<form name="passForm" id="passForm" method="post">
    <input type="hidden" id="carDenyNo" name="carDenyNo"/>
    <input type="hidden" id="crtBy" name="crtBy"/>
    <input type="hidden" id="modBy" name="modBy"/>
    <input type="hidden" id="acIp" name="acIp" value="<%= request.getRemoteAddr() %>"/>

    <table cellpadding="0" cellspacing="0" border="0" class="view_board">
        <colgroup>
            <col width="13%"/>
            <col width="37%"/>
            <col width="13%"/>
            <col width="37%"/>
        </colgroup>
        <tbody>
        <tr>
            <th><label>차량번호</label><span class="necessary"></span></th>
            <td colspan="3"><input type="text" name="carNo" id="carNo" value="" maxlength="20" onblur="javascript:fn_trimCarNo();"/>
                차량번호는 공백없이 입력해주세요.
            </td>
        </tr>
        <tr>
            <th><label>제한여부</label><span class="necessary"></span></th>
            <td colspan="3">
                <span id="delYnY"><input type="radio" name="delYn" value="Y" class="border_none" checked="checked"/><label>출입불가</label></span>
            </td>
        </tr>
        <tr>
            <th><label>제한 기간</label><span class="necessary"></span></th>
            <td colspan="3">
                <input type="text" id="denyStrtDt" name="denyStrtDt" style="width: 80px;"/>
                <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgDenyStrtDt"/>
                ~
                <input type="text" id="denyEndDt" name="denyEndDt" style="width: 80px;"/>
                <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgDenyEndDt"/>

                <input type="checkbox" id="allLimit" name="allLimit" class="border_none" onclick="javascript:fn_date();">영구 정지
            </td>
        </tr>

        <tr>
            <th><label>제한 사유</label><span class="necessary"></span></th>
            <td colspan="3"><textarea id="denyRsn" name="denyRsn" style="width:90%; height:60px; "></textarea></td>
        </tr>
        </tbody>
    </table>
    <div class="searchGroup">
        <div class="centerGroup">
            <span class="button bt_l1"><button type="button" style="width:50px;" onclick="javascript : fn_list();">목록</button></span>
            <span class="button bt_l1"><button type="button" style="width:50px;" onclick="javascript : fn_save();">저장</button></span>
        </div>
    </div>
</form>