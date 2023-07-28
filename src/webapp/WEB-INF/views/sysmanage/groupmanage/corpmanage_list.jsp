<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    gridUtil = new GridUtil({
      url: "/api/sysmanage/groupManage/corpManage"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , field: "rowNum"
          },
          {
            headerName: "회사ID"
            , field: "compId"
          },
          {
            headerName: "회사명"
            , field: "compNm"
            , align: "left"
          },
          {
            headerName: "사용여부"
            , field: "delYn"
            , formatter: cellValue => cellValue === "Y" ? "미사용" : "사용"
          },
        ]
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    //grid init
    gridUtil.init();
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
                    <th>회사 ID</th>
                    <td>
                        <input type="text" id="searchCompCd" name="searchCompCd" style="width:280px;IME-MODE:disabled;text-transform:uppercase">
                    </td>
                    <th>회사명</th>
                    <td>
                        <input type="text" id="searchCompNm" name="searchCompNm" style="width:280px;">
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