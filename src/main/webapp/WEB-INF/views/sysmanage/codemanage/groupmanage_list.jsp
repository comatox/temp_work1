<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    gridUtil = new GridUtil({
      url: "/api/sysmanage/codeManage/groupManage"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "분류코드"
            , field: "grpCd"
            , editable: true
            , cellattr: (rowId, _, rowdata) =>
                (rowdata.crtDtm || rowdata.modDtm) ? ' class="not-editable-cell"' : null
          },
          {
            headerName: "분류명"
            , field: "grpNm"
            , align: "left"
            , editable: true
          },
          {
            headerName: "비고"
            , field: "rmrk"
            , editable: true
          },
          {
            headerName: "사용유무"
            , field: "useYn"
            , formatter: GridUtils.selectFormatter
            , unformat: GridUtils.selectUnformatter
            , editable: true
            , edittype: "select"
            , editoptions: {value: {"Y": "사용중", "N": "미사용"}}
          },
          {
            headerName: "등록일시"
            , field: "crtDtm"
          },
        ]
        , multiselect: true
        , cellEdit: true
        , cellsubmit: "clientArray"
        , useMultiRow: false
        , afterEditCell: function (rowId, cellname, value, iRow, iCol) {
          gridUtil.getGrid().setSelectedCol(iCol);
          gridUtil.getGrid().setSelectedRow(iRow);
        }
        , onCellSelect: function (iRow, iCol) {
          if (iCol != 0) {
            var s = $(this).getGridParam("selarrrow");
            var selected = $.inArray(iRow, s) != -1;
            if (!selected) {
              $(this).jqGrid("setSelection", iRow);
            }
          }
        }
        , onSelectRow: function () {
          fn_saveCell();
        }
        , afterInsertRow: function (rowid) {		// row 추가시 발생하는 이벤트
          if (rowid == 0) {
            $(this).setRowAdded(true);
          } else {
            $(this).setRowAdded(false);
          }
        },
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    //grid init
    gridUtil.init();
  });

  /***************************************************************************
   * 편집중인 셀 저장 (row 변경시 수정중인 cell의 input태그 제거용)
   ***************************************************************************/
  function fn_saveCell() {
    const $grid = gridUtil.getGrid();
    if ($grid.getSelectedCol() == -1 || $grid.getSelectedRow() == -1) return;
    $grid.saveCell($grid.getSelectedRow(), $grid.getSelectedCol());
    $grid.setSelectedRow(-1);
    $grid.setSelectedCol(-1);
  }

  /***************************************************************************
   * 저장처리
   ***************************************************************************/
  var fn_Save = function () {
    var $grid = gridUtil.getGrid();
    fn_saveCell($grid);

    var rowSel = $grid.getGridParam("selarrrow").sort();
    var dataList = new Array();
    $.each(rowSel, function (index, value) {
      dataList.push($grid.getRowData(value));
    });

    if (dataList.length === 0) return false;

    if (confirm("저장하시겠습니까?")) {
      $.esutils.postApi("/api/sysmanage/codeManage/groupManage/update", {dataList, empId: global.empId, acIp: global.acIp}, function (result) {
        if (result && result.data) {
          alert("저장 되었습니다.");
          gridUtil.searchData();
        } else {
          alert("저장중 에러가 발생하였습니다.");
        }
      });
    }
  }

  /***************************************************************************
   * 추가
   ***************************************************************************/
  var fn_Add = function () {
    fn_saveCell(gridUtil.getGrid());
    if (gridUtil.getGrid().getRowAdded()) return;

    var rowIdx = 0;
    var datarow = {"useYn": "Y"};
    var su = gridUtil.getGrid().jqGrid("addRowData", rowIdx, datarow, "first");
    if (su) {
      gridUtil.getGrid().editCell(1, 3, true);
      gridUtil.getGrid().jqGrid("setSelection", rowIdx);
    } else {
      // alert("Fail!");
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
                    <th>분류</th>
                    <td>
                        <select name="searchgrp" id="searchgrp" style="width:100px;">
                            <option value="" selected="selected">전체</option>
                            <option value="A">출입관리[A]</option>
                            <option value="B">서비스신청[B]</option>
                            <option value="C">보안활동[C]</option>
                            <option value="D">자산반출입[D]</option>
                            <option value="Z">공통[Z]</option>
                        </select>
                    </td>
                    <th>사용유무</th>
                    <td>
                        <select name="searchuseYn" id="searchuseYn" style="width:100px;">
                            <option value="">전체</option>
                            <option value="Y" selected="selected">사용중</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>분류코드</th>
                    <td>
                        <input type="text" name="searchgrpCd" id="searchgrpCd" style="IME-MODE:disabled;text-transform:uppercase;width:200px;" value="" maxlength="4"/>
                    </td>
                    <th>분류명</th>
                    <td>
                        <input type="text" name="searchgrpNm" id="searchgrpNm" style="IME-MODE:enable;width:200px;" value=""/>
                    </td>
                </tr>
                <tr>
                    <th>비고</th>
                    <td colspan="3">
                        <input type="text" name="searchrmrk" id="searchrmrk" style="IME-MODE:enable;width:200px;" value=""/>
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

<!-- 기능버튼 -->
<div style="float: none;">
    <table cellpadding="0" cellspacing="0" width="850px" border="0" class="view_board03">
        <caption>기능버튼</caption>
        <tbody>
        <tr>
            <td width="258px"><span id="gate_list"></span></td>
            <td align="right">
                <span class="button bt_s2"><button tabindex="1" onclick="javascript:fn_Add();" style="width:80px;" type="button">추가</button></span>
                <span class="button bt_s2"><button tabindex="2" onclick="javascript:fn_Save();" style="width:80px;" type="button">저장</button></span>
                <!-- <span class="button bt_s2"><button tabindex="3" onclick="javascript:fn_Delete();" style="width:80px;" type="button">삭제</button></span> -->
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!-- 기능버튼 끝 -->

<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->