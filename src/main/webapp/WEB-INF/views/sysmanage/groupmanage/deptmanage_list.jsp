<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    gridUtil = new GridUtil({
      url: "/api/sysmanage/groupManage/deptManage"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , field: "rowNum"
          },
          {
            headerName: "업체명"
            , field: "compNm"
          },
          {
            headerName: "부서코드"
            , field: "deptId"
          },
          {
            headerName: "부서명"
            , field: "deptNm"
            , align: "left"
          },
          {
            headerName: "사용여부"
            , field: "useYn"
            , formatter: cellValue => cellValue === "Y" ? "사용" : "미사용"
          },
        ]
        , onRowClicked: ({originRowData}) => {
          $.esutils.href("/sysmanage/groupmanage/deptmanage/modify", {deptId: originRowData.deptId});
        }
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    //grid init
    gridUtil.init();

    $.esutils.getApi("/api/common/organization/comp", null, function (result) {
      console.log(result);
      if (result && result.data) {
        let html = "<option value=\"\">전체</option>";
        html += result.data.filter(d => d.compId !== "1100000001")
        .map(d => `<option value="\${d.compId}">\${d.compNm}</option>`);
        $("[name=searchCompId]").html(html);
      }
    });
  });
</script>
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <colgroup>
                    <col width="18%"/>
                    <col width="43%"/>
                    <col width="15%"/>
                    <col width="24%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>업체명</th>
                    <td>
                        <select id=searchCompId name="searchCompId" style="width:250px;">
                        </select>
                    </td>
                    <th>부서명</th>
                    <td>
                        <input type="text" id="searchDeptNm" name="searchDeptNm" style="IME-MODE:enable;width:200px;" value=""/>
                    </td>
                </tr>
                <tr>
                    <th>사용유무</th>
                    <td colspan="3">
                        <select name="searchUseYn" id="searchUseYn" style="width:200px;">
                            <option value="" selected="selected">전체</option>
                            <option value="Y">사용</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button type="button" id="searchBtn" style="width:50px;">검색</button></span>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->