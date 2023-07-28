<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $(document).ready(function () {
    const deptId = "${param.deptId}";

    $.esutils.getApi("/api/common/organization/comp", null, function (result) {
      if (result && result.data) {
        let html = "<option value=\"\">전체</option>";
        html += result.data
        .map(d => `<option value="\${d.compId}">\${d.compNm}</option>`);
        $("[name=compId]").html(html);

        if (deptId) {
          $("[name=deptId]").val(deptId);

          $.esutils.renderData(
              "passForm",
              `/api/common/organization/dept/\${deptId}`,
              (data) => {
              }, {loading: true});
        }
      }
    });

    $("[name=crtBy]").val(global.empId);
    $("[name=modBy]").val(global.empId);
  });

  function fn_list() {
    $.esutils.href("/sysmanage/groupmanage/deptmanage/list");
  }

  function fn_save() {
    if (fn_validation()) {
      if (confirm("저장하시겠습니까?")) {
        const param = $.esutils.getFieldsValue($('#passForm'));

        $.esutils.postApi("/api/sysmanage/groupManage/deptManage/save", param, function (result) {
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
    if ($("#deptNm").val() == null || $("#deptNm").val() == "") {
      alert("건물명(한글)은 필수 항목입니다.");
      $("#deptNm").focus();
      return false;
    }

    var useYn = "";
    $("input[name='useYn']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        useYn = $(this).attr("value");
      }
    });
    if (useYn == null || useYn == "") {
      alert("사용 유무는 필수 항목 입니다.");
      return false;
    }
    return true;
  }
</script>
<form name="passForm" id="passForm" method="post">
    <input type="hidden" id="crtBy" name="crtBy"/>
    <input type="hidden" id="modBy" name="modBy"/>

    <input type="hidden" id="divCd" name="divCd" value=''/>
    <input type="hidden" id="divNm" name="divNm" value=''/>
    <input type="hidden" id="deptId" name="deptId" value=''/>
    <input type="hidden" id="updeptId" name="updeptId" value=''/>

    <table cellpadding="0" cellspacing="0" border="0" class="view_board">
        <colgroup>
            <col width="15%"/>
            <col width="35%"/>
            <col width="15%"/>
            <col width="35%"/>
        </colgroup>
        <tbody>

        <tr>
            <th><label>업체명</label></th>
            <td colspan="3">
                <select id=compId name="compId" style="width:250px;">
                </select>
            </td>
        </tr>

        <tr>
            <th><label>부서명(한글)</label></th>
            <td colspan="3">
                <input type="text" id="deptNm" name="deptNm" value="" maxlength="50" style="width:430px;"/>
            </td>
        </tr>

        <tr>
            <th><label>사용 여부</label></th>
            <td colspan="3">
                <input type="radio" name="useYn" value="Y" class="border_none"/><label>사용</label>
                <input type="radio" name="useYn" value="N" class="border_none"/><label>미사용</label>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="searchGroup">
        <div class="centerGroup">
            <span class="button bt_l1"><button type="button" style="width:50px;" onclick="javascript : fn_list();">목록</button></span>&nbsp;&nbsp;
            <span class="button bt_l1"><button type="button" style="width:50px;" onclick="javascript : fn_save();">저장</button></span>&nbsp;&nbsp;
        </div>
    </div>
</form>