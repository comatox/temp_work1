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
      url: "/api/sysmanage/groupManage/hynixUser/list"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: ""
            , formatter: function (cellValue, options, row) {
              return "<input type='radio' name='selectedEmpId' value='" + row.empId + "@" + row.isRegist + "@" + row.htNm + "' onchange=fnSelect(this)>";
            }
          },
          {
            headerName: "순번"
            , field: "idx"
          },
          {
            headerName: "부서"
            , field: "deptNm"
          },
          {
            headerName: "성명"
            , field: "empNm"
          },
          {
            headerName: "사번"
            , field: "empId"
          },
          {
            headerName: "직책"
            , field: "jcNm"
          },
          {
            headerName: "직위"
            , field: "jwNm"
          },
          {
            headerName: "사업장"
            , field: "divNm"
          },
          {
            headerName: "퇴사여부"
            , field: "htNm"
          },
          {
            headerName: "등록여부"
            , field: "isRegist"
          },
          {
            headerName: "등록일"
            , field: "crtDtm"
          },
          {
            headerName: "수정일"
            , field: "modDtm"
          },
        ],
      },
      search: {
        formId: "formGrid",
        buttonId: "searchBtn"
      }
    });
  }

  /***************************************************************************
   * Document initializing
   ***************************************************************************/
  const initDocument = () => {
    gridUtil.init(); // grid 조회
  }

  /***************************************************************************
   * 하이닉스 사용자 등록
   ***************************************************************************/
  const fnSelect = (elem) => {
    const userData = $(elem).val().split("@");
    if (userData[1] === "등록") {
      alert("이미 등록된 사용자는 선택할 수 없습니다.");
      $("input:radio[name='selectedEmpId']").removeAttr("checked");
    } else if (userData[2] === "퇴사") {
      alert("퇴사한 사용자는 선택할 수 없습니다.");
      $("input:radio[name='selectedEmpId']").removeAttr("checked");
    }
  }

  /***************************************************************************
   * 하이닉스 사용자 등록
   ***************************************************************************/
  const fnRegist = () => {

    const empId = $("input:radio[name='selectedEmpId']:checked").val()?.split("@")[0] || '';
    if (!empId) {
      alert("등록할 하이닉스 사용자를 선택하세요.");
      return;
    }

    if (confirm("선택하신 사용자를 등록하시겠습니까?")) {
      $.esutils.postApi('/api/sysmanage/groupManage/hynixUser/save', {empId}, (response) => {

        const msg = response.message;
        if (msg === "OK") {
          alert('저장되었습니다.');
          gridUtil.searchData();

        } else {
          alert("저장 중 에러가 발생하였습니다.");
        }
      }, {});
    }
  };
</script>
<!-- 검색영역 시작 -->
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <caption>조회화면</caption>
                <tr>
                    <th><label>성명</label></th>
                    <td><input type="text" id="empNm" name="empNm" value="" style="width: 150px;"/></td>
                    <th><label>사번</label></th>
                    <td><input type="text" id="empId" name="empId" value="" style="width: 150px;"/></td>
                </tr>
                <tr>
                    <th>퇴사여부</th>
                    <td>
                        <input type="radio" id="searchHtCdAll" name="searchHtCd" value="ALL" checked class="border_none"/><label>전체</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="searchHtCdC" name="searchHtCd" value="C" class="border_none"/><label>재직중</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="searchHtCdT" name="searchHtCd" value="T" class="border_none"/><label>퇴사</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <th>등록여부</th>
                    <td>
                        <input type="radio" id="searchRegYnAll" name="searchRegYn" value="ALL" checked class="border_none"/><label>전체</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="searchRegYnN" name="searchRegYn" value="N" class="border_none"/><label>미등록</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="searchRegYnY" name="searchRegYn" value="Y" class="border_none"/><label>등록</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <!-- 버튼 -->
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button type="button" id="searchBtn" style="width:80px;">검색</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->
        </form>
    </div>
</div>
<!-- 검색영역 끝 -->
<div style="float:none;margin-top:-15px">
    <table cellpadding="0" cellspacing="0" width="850px" border="0">
        <caption>기능버튼</caption>
        <tbody>
        <tr>
            <td align="right"><span class="button bt_s1"><button type="button" style="width:80px;" onclick="javascript:fnRegist();" tabindex="6">사용자등록</button></span></td>
        </tr>
        </tbody>
    </table>
</div>
<div id="grid"></div>
