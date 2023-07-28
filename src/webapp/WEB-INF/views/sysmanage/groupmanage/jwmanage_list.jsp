<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    gridUtil = new GridUtil({
      url: "/api/sysmanage/groupManage/jwManage"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , field: "rowNum"
          },
          {
            headerName: "직위코드"
            , field: "jwId"
          },
          {
            headerName: "직위명"
            , field: "jwNm"
            , align: "left"
          },
          {
            headerName: "사용여부"
            , field: "useYn"
            , formatter: cellValue => cellValue === "Y" ? "사용" : "미사용"
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
                    <th>직위명</th>
                    <td>
                        <input type="text" id="searchJwNm" name="searchJwNm" style="width:280px;"/>
                    </td>
                    <th>사용유무</th>
                    <td>
                        <select name="searchUseYn" id="searchUseYn" style="width:280px;">
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