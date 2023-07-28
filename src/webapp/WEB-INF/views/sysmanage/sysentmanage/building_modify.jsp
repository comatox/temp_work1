<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $(document).ready(function () {
    const gateId = "${param.gateId}";

    if (gateId) {
      $("[name=gateId]").val(gateId);
      $("[name=gateNm]").prop("readonly", true);

      $.esutils.renderData(
          "passForm",
          `/api/sysmanage/buildingManage/\${gateId}`,
          (data) => {
          }, {loading: true});
    }

    $("[name=crtBy]").val(global.empId);
    $("[name=modBy]").val(global.empId);
    renderBuildingTop($("[name=compId]").val());

    $("[name=compId]").on("change", function() {
      renderBuildingTop($(this).val());
      $("[name=upGateId2]").empty();
    });
    $("[name=upGateId]").on("change", function() {
      renderBuildingSub($("[name=compId]:checked").val(), $(this).val());
    });
  });

  function fn_list() {
    $.esutils.href("/sysmanage/sysentmanage/building/list");
  }

  function fn_save() {
    if (fn_validation()) {
      if (confirm("저장하시겠습니까?")) {
        const param = $.esutils.getFieldsValue($('#passForm'));
        const gateId = $("#gateId").val();

        let apiUrl = "/api/sysmanage/buildingManage";
        if (gateId) {
          apiUrl = `/api/sysmanage/buildingManage/\${gateId}`;
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
    if ($("#gateNm").val() == null || $("#gateNm").val() == "") {
      alert("건물명은 필수 항목입니다.");
      $("#gateNm").focus();
      return false;
    }

    if ($("#sortSeq").val() == null || $("#sortSeq").val() == "") {
      alert("정렬순서는 필수 항목입니다.");
      $("#sortSeq").focus();
      return false;
    }

    var delYn = "";
    $("input[name='delYn']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        delYn = $(this).attr("value");
      }
    });
    if (delYn == null || delYn == "") {
      alert("사용 유무는 필수 항목 입니다.");
      return false;
    }
    return true;
  }

  function fn_getCoEmp() {
    $.esutils.openEmpPopup({
      empNm: $("[name=apprEmpNm]").val(),
      fnCallback: data => {
        $("[name=apprEmpId]").val(data.rowData.empId);
        $("[name=apprEmpNm]").val(data.rowData.empNm);
      }
    });
  }

  function fn_delCoEmp() {
    $("[name=apprEmpId]").val("");
    $("[name=apprEmpNm]").val("");
  }

  function fn_getCoEmp2() {
    $.esutils.openEmpPopup({
      empNm: $("[name=apprEmpNm2]").val(),
      fnCallback: data => {
        $("[name=apprEmpId2]").val(data.rowData.empId);
        $("[name=apprEmpNm2]").val(data.rowData.empNm);
      }
    });
  }

  function fn_delCoEmp2() {
    $("[name=apprEmpId2]").val("");
    $("[name=apprEmpNm2]").val("");
  }

  function renderBuildingTop(compId) {
    $.esutils.getApi("/api/sysmanage/buildingManage/top", {compId}, result => {
      let html = "";
      if (result.data) {
        html += '<option value="">선택하세요.</option>';
        html += result.data.map(d => `<option value="\${d.gateId}">\${d.gateNm}</option>`);
      }
      $("[name=upGateId]").html(html);
    });
  }

  function renderBuildingSub(compId, upGateId) {
    $.esutils.getApi("/api/sysmanage/buildingManage/sub", {compId, upGateId}, result => {
      let html = "";
      if (result.data) {
        html += '<option value="">선택하세요.</option>';
        html += result.data.map(d => `<option value="\${d.gateId}">\${d.gateNm}</option>`);
      }
      $("[name=upGateId2]").html(html);
    });
  }
</script>
<form name="passForm" id="passForm" method="post">
    <input type="hidden" id="gateId" name="gateId" value=""/>
    <input type="hidden" id="crtBy" name="crtBy" value=""/>
    <input type="hidden" id="modBy" name="modBy" value=""/>
    <input type="hidden" id="pageNo" name="pageNo" value=""/>

    <table cellpadding="0" cellspacing="0" border="0" class="view_board">
        <colgroup>
            <col width="15%"/>
            <col width="35%"/>
            <col width="15%"/>
            <col width="35%"/>
        </colgroup>
        <tbody>
        <c:choose>
            <c:when test="${param.gateId != null}">
                <tr>
                    <th><label>사업장</label></th>
                    <td id="compNm" view-data="compNm"></td>
                    <th><label>상위건물</label></th>
                    <td id="upGateNm" view-data="upGateNm"></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <th>사업장</th>
                    <td colspan="3">
                        <input type="radio" name="compId" id="compId_1101000001" value="1101000001" checked="checked" class="border_none"/><label>이천</label>
                        <input type="radio" name="compId" id="compId_1102000001" value="1102000001" class="border_none"/><label>청주1</label>
                        <input type="radio" name="compId" id="compId_1105000001" value="1105000001" class="border_none"/><label>청주2 (NBE)</label>
                        <input type="radio" name="compId" id="compId_1106000001" value="1106000001" class="border_none"/><label>청주3 (M11 : A-Project)</label>
                        <input type="radio" name="compId" id="compId_1103000001" value="1103000001" class="border_none"/><label>영동</label>
                        <input type="radio" name="compId" id="compId_1107000001" value="1107000001" class="border_none"/><label>분당사무소</label>
                    </td>
                </tr>
                <tr>
                    <th>최상위건물</th>
                    <td>
                        <select id="upGateId" name="upGateId" style="width:230px;">
                        </select>
                    </td>
                    <th>상위건물</th>
                    <td>
                        <select id="upGateId2" name="upGateId2" style="width:230px;">
                        </select>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
        <tr>
            <th><label>건물명(한글)</label></th>
            <td colspan="3">
                <input type="text" id="gateNm" name="gateNm" value="" maxlength="50" style="width:430px;"/>
            </td>
        </tr>
        <tr>
            <th><label>건물명(영문)</label></th>
            <td colspan="3">
                <input type="text" id="gateEnNm" name="gateEnNm" value="" maxlength="50" style="width:430px;"/>
            </td>
        </tr>
        <tr>
            <th><label>방문객 출입가능</label></th>
            <td>
                <input type="checkbox" id="vstrAvailYn" name="vstrAvailYn" value="Y" class="border_none"/><label>가능</label>
            </td>
            <th><label>청정지역</label></th>
            <td>
                <input type="checkbox" id="cleanEduYn" name="cleanEduYn" value="Y" class="border_none"/><label>청정도 이수자만 출입 가능</label>
            </td>
        </tr>
        <tr>
            <th><label>통제구역</label></th>
            <td>
                <input type="checkbox" id="ctrlYn" name="ctrlYn" value="Y" class="border_none"/><label>통제구역 지정</label>
            </td>
            <th><label>TAGID</label></th>
            <td>
                <input type="checkbox" id="tagidknd" name="tagidknd" value="Y" class="border_none"/><label>TAGID 사용</label>
            </td>
        </tr>
        <tr>
            <th>보안 담당자</th>
            <td>
                <input type="text" name="apprEmpNm" readonly id="apprEmpNm" value="" style="width:120px;"/>
                <input type="hidden" name="apprEmpId" id="apprEmpId" value=""/>
                <a href="javascript:fn_getCoEmp();" class="btn_type01"><span>확인</span></a>
                <a href="javascript:fn_delCoEmp();" class="btn_type01"><span>삭제</span></a>
            </td>
            <th>보안 승인자</th>
            <td>
                <input type="text" name="apprEmpNm2" readonly id="apprEmpNm2" value="" style="width:120px;"/>
                <input type="hidden" name="apprEmpId2" id="apprEmpId2" value=""/>
                <a href="javascript:fn_getCoEmp2();" class="btn_type01"><span>확인</span></a>
                <a href="javascript:fn_delCoEmp2();" class="btn_type01"><span>삭제</span></a>
            </td>
        </tr>
        <tr>
            <th><label>정렬순서</label></th>
            <td>
                <input type="number" id="sortSeq" name="sortSeq"/>
            </td>
            <th><label>사용 여부</label></th>
            <td>
                <input type="radio" name="delYn" value="N" class="border_none"/><label>사용</label>
                <input type="radio" name="delYn" value="Y" class="border_none"/><label>미사용</label>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="searchGroup">
        <div class="centerGroup">
            <span class="button bt_l2"><button type="button" style="width:60px;" onclick="javascript : fn_list();">목록</button></span>
            <span class="button bt_l1"><button type="button" style="width:60px;" onclick="javascript : fn_save();">저장</button></span>
        </div>
    </div>
</form>