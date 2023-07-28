<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    $("[name=fromWritedate]").val(moment().subtract(3, "months").add(1, 'days').format("YYYY-MM-DD"));
    $("[name=toWritedate]").val(moment().format("YYYY-MM-DD"));

    $.esutils.rangepicker([["[name=fromWritedate]", "[name=toWritedate]"], ["[name=fromIndate]", "[name=toIndate]"]]);

    $("[name=deptId]").val(global.deptId);

    $("[name=searchOutcompanyknd]").on("change", function () {
      const searchOutcompanyknd = $(this).val();
      if (searchOutcompanyknd == "1" || searchOutcompanyknd == "2" || searchOutcompanyknd == "") {
        $("[name=searchDeptNm]").show();
        $("[name=searchForeignComp]").hide();
      } else {
        $("[name=searchDeptNm]").hide();
        $("[name=searchForeignComp]").show();
      }
    });

    gridUtil = new GridUtil({
      url: "/api/inoutasset/inoutchange/inoutkndchange"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "반출입번호"
            , field: "inoutserialno"
            , onCellClicked: function ({originRowData}) {
              fn_view(originRowData);
            }
          },
          {
            headerName: "작성일"
            , field: "writedate"
          },
          {
            headerName: "반입예정일"
            , field: "indate"
          },
          {
            headerName: "작성자"
            , field: "empNm"
          },
          {
            headerName: "상대처"
            , field: "partnerorcompanyname"
          },
          {
            headerName: "반출사유"
            , field: "outreasonidNm"
          },
          {
            headerName: "승인"
            , field: "approvalstateName"
          },
        ]
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
        , beforeSend: data => ({...data, outcompanyknd: "2", searchPartnerNm: $("[name=searchOutcompanyknd]").val() === "3" ? $("[name=searchForeignComp]").val() : $("[name=searchDeptNm]").val()})
      }
    });

    //grid init
    gridUtil.init();
  });

  function fn_view({inoutApplNo}) {
    $.esutils.href("/inoutasset/inoutchange/inoutkndchange2/detail", {inoutApplNo});
  }
</script>
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <input type="hidden" name="deptId" value=""/>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <colgroup>
                    <col width="14%"/>
                    <col width="36%"/>
                    <col width="14%"/>
                    <col width="36%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>작성일</label></th>
                    <td>
                        <input type="text" id="fromWritedate" name="fromWritedate" style="width: 70px;"/>
                        ~
                        <input type="text" id="toWritedate" name="toWritedate" style="width: 70px;"/>
                    </td>
                    <th><label>반입예정일</label></th>
                    <td>
                        <input type="text" id="fromIndate" name="fromIndate" style="width: 70px;"/>
                        ~
                        <input type="text" id="toIndate" name="toIndate" style="width: 70px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label>반출입번호</label></th>
                    <td>
                        <input type="text" id="searchInoutserialno" name="searchInoutserialno" style="width:215px;"/>
                    </td>
                    <th><label>문서구분</label></th>
                    <td>
                        <select id="doctype" name="doctype" style="width:230px;">
                            <option value="1">내 문서</option>
                            <option value="2">부서 문서</option>
                            <option value="3">퇴사자 문서</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label>상대처</label></th>
                    <td>
                        <select id="searchOutcompanyknd" name="searchOutcompanyknd" style="width:230px;">
                            <option value="">전체</option>
                            <option value="2">외부업체</option>
                            <option value="3">해외법인</option>
                        </select>
                    </td>
                    <th><label>부서명(업체명)</label></th>
                    <td>
                        <input type="text" id="searchDeptNm" name="searchDeptNm" style="width:200px;"/>
                        <select id="searchForeignComp" name="searchForeignComp" style="width:230px;display:none;">
                            <option value="">선택하세요.</option>
                            <option value="A1">America San Jose</option>
                            <option value="A2">America Chicago</option>
                            <option value="A3">America Houston</option>
                            <option value="A4">America Austin</option>
                            <option value="A5">America Fishkill</option>
                            <option value="A6">America Raleigh</option>
                            <option value="H1">Deutschland</option>
                            <option value="H2">UK Limited</option>
                            <option value="H3">Japan</option>
                            <option value="H4">Singapore</option>
                            <option value="H5">Indian</option>
                            <option value="H6">Hong Kong</option>
                            <option value="H7">Taiwan</option>
                            <option value="S1">China 심천</option>
                            <option value="S2">China 상해</option>
                            <option value="S3">China 북경</option>
                            <option value="S4">China 중경</option>
                            <option value="S5">China 충칭</option>
                            <option value="T1">Italy</option>
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