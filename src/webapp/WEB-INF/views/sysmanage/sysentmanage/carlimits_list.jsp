<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    gridUtil = new GridUtil({
      url: "/api/sysmanage/sysmanage/envrEntmng/carLimits/list"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "차량번호"
            , field: "carNo"
          },
          {
            headerName: "출입제한기간"
            , field: "denyStrtDt"
          },
          {
            headerName: "등록자"
            , field: "crtBy"
          },
          {
            headerName: "등록일"
            , field: "crtDtm"
          },
          {
            headerName: "수정자"
            , field: "modBy"
          },
          {
            headerName: "수정일"
            , field: "modDtm"
          },
          {
            headerName: "사용여부"
            , field: "delYn"
            , formatter: (cellValue, _, data) => cellValue === "Y" ? `<span class='button bt_l2'><button onclick="fn_limitsDel('\${data.carDenyNo}')">해제</button></span>` : ""
          },
        ]
        , onRowClicked: ({originRowData}) => {
          $.esutils.href("/sysmanage/sysentmanage/carlimits/modify", {carDenyNo: originRowData.carDenyNo});
        }
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    //grid init
    gridUtil.init();

    $("#requestBtn").on("click", function() {
      $.esutils.href("/sysmanage/sysentmanage/carlimits/modify");
    })
  });

  function fn_limitsDel(carDenyNo) {
    if(confirm("출입제한을 해제하시겠습니까?")) {
      $.esutils.postApi(`/api/entmanage/envrEntmng/carLimits/\${carDenyNo}/delete`, {carDenyNo}, function (result) {
        if (result && result.data) {
          alert("출입제한을 해제하였습니다.");
          gridUtil.searchData();
        } else {
          alert("오류가 발생하였습니다.");
        }
      });
    }
  }
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
                    <th><label>처리상태</label></th>
                    <td>
                        <input type="radio" name="searchDelYn" id="searchDelYnN" value="N" class="border_none"/><label for="searchDelYnN">출입가능</label>
                        <input type="radio" name="searchDelYn" id="searchDelYnY" value="Y" class="border_none"/><label for="searchDelYnY">출입불가</label>
                        <input type="radio" name="searchDelYn" id="searchDelYn" value="" checked="checked" class="border_none"/><label for="searchDelYn">전체</label>
                    </td>
                    <th><label>차량번호</label></th>
                    <td>
                        <input type="text" id="SEARCH_CAR_NO" name="SEARCH_CAR_NO" style="width:230px;"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button type="button" id="searchBtn" style="width:50px;">검색</button></span>
                    <span class="button bt_l1"><button type="button" id="requestBtn" style="width:130px;">출입제한차량 등록</button></span>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->