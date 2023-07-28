<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    // const compId = "1101000001"; // 확인 필요
    // $(`[name=searchCompId][value=\${compId}]`).prop("checked", true);
    // renderBuildingTop(compId);
    renderBuildingTop();

    gridUtil = new GridUtil({
      url: "/api/sysmanage/buildingManage"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "사업장"
            , field: "compNm"
          },
          {
            headerName: "건물명"
            , field: "gateNm"
          },
          {
            headerName: "방문출입가능"
            , field: "vstrAvailYn"
            , formatter: cellValue => cellValue === "Y" ? "출입가능" : "출입불가"
          },
          {
            headerName: "청정교육여부"
            , field: "cleanEduYn"
            , formatter: cellValue => cellValue === "Y" ? "필요" : "불 필요"
          },
          {
            headerName: "통제구역여부"
            , field: "ctrlYn"
            , formatter: cellValue => cellValue === "Y" ? "통제" : "미 통제"
          },
          {
            headerName: "사용여부"
            , field: "delYn"
            , formatter: cellValue => cellValue === "Y" ? "미 사용" : "사용"
          },
        ]
        , onRowClicked: ({originRowData}) => {
          $.esutils.href("/sysmanage/sysentmanage/building/modify", {gateId: originRowData.gateId});
        }
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    //grid init
    gridUtil.init();

    $("[name=searchCompId]").on("change", function() {
      renderBuildingTop($(this).val());
      $("[name=searchUpGateId2]").empty();
    });
    $("[name=searchUpGateId]").on("change", function() {
      renderBuildingSub($("[name=searchCompId]:checked").val(), $(this).val());
    });
  });

  function renderBuildingTop(compId) {
    if(!compId) {
      $("[name=searchUpGateId]").html('<option value="">사업장을 먼저 선택하세요.</option>');
      $("[name=searchUpGateId2]").empty();
      return;
    }

    $.esutils.getApi("/api/sysmanage/buildingManage/top", {compId}, result => {
      let html = "";
      if (result.data) {
        html += '<option value="">선택하세요.</option>';
        html += result.data.map(d => `<option value="\${d.gateId}">\${d.gateNm}</option>`);
      } else {
        html += '<option value="">사업장을 먼저 선택하세요.</option>';
      }
      $("[name=searchUpGateId]").html(html);
    });
  }

  function renderBuildingSub(compId, upGateId) {
    $.esutils.getApi("/api/sysmanage/buildingManage/sub", {compId, upGateId}, result => {
      let html = "";
      if (result.data) {
        html += '<option value="">선택하세요.</option>';
        html += result.data.map(d => `<option value="\${d.gateId}">\${d.gateNm}</option>`);
      }
      $("[name=searchUpGateId2]").html(html);
    });
  }

  function fn_insert() {
    $.esutils.href("/sysmanage/sysentmanage/building/modify");
  }
</script>
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <colgroup>
                    <col width="13%"/>
                    <col width="37%"/>
                    <col width="13%"/>
                    <col width="37%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>사업장</label></th>
                    <td colspan="3">
                        <input type="radio" name="searchCompId" id="searchCompId" value="" checked="checked" class="border_none"/><label>전체</label>
                        <input type="radio" name="searchCompId" id="searchCompId_1101000001" value="1101000001" class="border_none"/><label>이천</label>
                        <input type="radio" name="searchCompId" id="searchCompId_1102000001" value="1102000001" class="border_none"/><label>청주1</label>
                        <input type="radio" name="searchCompId" id="searchCompId_1105000001" value="1105000001" class="border_none"/><label>청주2 (NBE)</label>
                        <input type="radio" name="searchCompId" id="searchCompId_1106000001" value="1106000001" class="border_none"/><label>청주3 (M11 : A-Project)</label>
                        <input type="radio" name="searchCompId" id="searchCompId_1103000001" value="1103000001" class="border_none"/><label>영동</label>
                        <input type="radio" name="searchCompId" id="searchCompId_1107000001" value="1107000001" class="border_none"/><label>분당사무소</label>
                    </td>
                </tr>
                <tr>
                    <th><label>최상위건물</label></th>
                    <td>
                        <select id="searchUpGateId" name="searchUpGateId" style="width:230px;">
                        </select>
                    <th><label>상위건물</label></th>
                    <td>
                        <select id="searchUpGateId2" name="searchUpGateId2" style="width:230px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label>방문출입가능</label></th>
                    <td>
                        <input type="radio" name="searchVstrAvailYn" value="" id="searchVstrAvailYn" checked="checked" class="border_none"/><label for="searchVstrAvailYn">전체</label>
                        <input type="radio" name="searchVstrAvailYn" value="Y" id="searchVstrAvailYnY" class="border_none"/><label for="searchVstrAvailYnY">출입가능</label>
                        <input type="radio" name="searchVstrAvailYn" value="N" id="searchVstrAvailYnN" class="border_none"/><label for="searchVstrAvailYnN">출입불가</label>
                    </td>
                    <th><label>청정교육여부</label></th>
                    <td>
                        <input type="radio" name="searchCleanEduYn" value="" id="searchCleanEduYn" checked="checked" class="border_none"/><label for="searchCleanEduYn">전체</label>
                        <input type="radio" name="searchCleanEduYn" value="Y" id="searchCleanEduYnY" class="border_none"/><label for="searchCleanEduYnY">필요</label>
                        <input type="radio" name="searchCleanEduYn" value="N" id="searchCleanEduYnN" class="border_none"/><label for="searchCleanEduYnN">불필요</label>
                    </td>
                </tr>
                <tr>
                    <th><label>통제구역여부</label></th>
                    <td>
                        <input type="radio" name="searchCtrlYn" id="searchCtrlYn" value="" checked="checked" class="border_none"/><label for="searchCtrlYn">전체</label>
                        <input type="radio" name="searchCtrlYn" id="searchCtrlYnY" value="Y" class="border_none"/><label for="searchCtrlYnY">통제</label>
                        <input type="radio" name="searchCtrlYn" id="searchCtrlYnN" value="N" class="border_none"/><label for="searchCtrlYnN">미 통제</label>
                    </td>
                    <th><label>건물명</label></th>
                    <td>
                        <input type="text" id="searchGateNm" name="searchGateNm" value="" style="width:230px;"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button type="button" id="searchBtn" style="width:50px;">검색</button></span>
                    <span class="button bt_l1"><button type="button" style="width:80px;" onclick="javascript : fn_insert();">건물 등록</button></span>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->