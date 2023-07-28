<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

  let gridUtil;

  /***************************************************************************
   * 화면 onload 처리
   ***************************************************************************/
  $(document).ready(function () {

    initGrid();
    initDocument();

  });

  /***************************************************************************
   * Grid initializing
   ***************************************************************************/
  const initGrid = () => {
    gridUtil = new GridUtil({
      url: "/api/sysmanage/groupManage/userManage/list"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , field: "rowNum"
          },
          {
            headerName: "사원번호"
            , field: "empId"
          },
          {
            headerName: "사원명 / 직위"
            , field: "fullName"
          },
          {
            headerName: "업체명 / 근무지명"
            , field: "fullComp"
          },
          {
            headerName: "부서명"
            , field: "deptNm"
          },
        ]
        , onRowClicked
      },
      search: {
        formId: "formGrid",
        buttonId: "searchBtn"
      }
    });
  }

  /***************************************************************************
   * Grid callback function
   ***************************************************************************/
  const onRowClicked = ({rowData}) => {
    $.esutils.href("/sysmanage/groupmanage/usermanage/view", {empId: rowData.empId});
  }

  /***************************************************************************
   * Document initializing
   ***************************************************************************/
  const initDocument = async () => {
    await fnGetCompList(); // 회사명 Select Option 조회
    await gridUtil.init(); // grid 조회
  }

  /***************************************************************************
   * 등록
   ***************************************************************************/
  const fnAdd = () => {
    $.esutils.href("/sysmanage/groupmanage/usermanage/request", {empId: rowData.empId});
  };

  /***************************************************************************
   * 업체 콤보 리스트
   ***************************************************************************/
  const fnGetCompList = async () => {

    const targetId = 'searchCompId';
    await $.esutils.renderCode([
      {
        targetId,
        url: "/api/common/organization/comp",
        nameProp: "compNm",
        valueProp: "compId",
        type: "select",
        filter: (d) => d.compId !== '1100000001'
      }
    ], () => {
      $("#" + targetId).prepend('<option value="" selected>전체</option>');
    });
  }
</script>
<!-- 검색영역 시작 -->
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" caption="조회화면입니다" border="0" class="view_board01">
                <tr>
                    <th>회사명</th>
                    <td>
                        <select id="searchCompId" name="searchCompId" style="width:250px;">
                        </select>
                    </td>
                    <th>근무지명</th>
                    <td>
                        <input type="text" name="searchKndNm" id="searchKndNm" style="IME-MODE:enable;width:200px;" value=""/>
                    </td>
                </tr>
                <tr>
                    <th>사원명</th>
                    <td>
                        <input type="text" name="searchEmpNm" id="searchEmpNm" style="IME-MODE:enable;width:200px;" value=""/>
                    </td>
                    <th>부서명</th>
                    <td>
                        <input type="text" name="searchDeptNm" id="searchDeptNm" style="IME-MODE:enable;width:200px;" value=""/>
                    </td>
                </tr>
                <tr>
                    <th>사원번호</th>
                    <td>
                        <input type="text" id="searchEmpId" name="searchEmpId" value="" style="width:200px;"/>
                    </td>
                </tr>
            </table>

            <!-- 버튼 -->
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button id="searchBtn" type="button" style="width:80px;">검색</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->
        </form>
    </div>
</div>
<!-- 검색영역 끝 -->

<!-- 기능버튼 -->
<div style="float: none;">
    <table cellpadding="0" cellspacing="0" width="850px" border="0" class="view_board03">
        <caption>기능버튼</caption>
        <tbody>
        <tr>
            <td width="258px"><span id="gate_list"></span></td>
            <td align="right">
                <span class="button bt_s2"><button tabindex="1" onclick="javascript:fnAdd();" style="width:80px;" type="button">작성</button></span>
            </td>
        </tr>
        </tbody>
    </table>

</div>
<!-- 기능버튼 끝 -->
<div id="grid"></div>
