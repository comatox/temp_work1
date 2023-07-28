<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  let groupData = [];

  $(document).ready(function () {
    $.esutils.getApi("/api/sysmanage/codeManage/groupManage", {pageIndex: 1, pageSize: 500}, function (result) {
      groupData = result.data.reverse();
      const allGroupSearchValues = Object.fromEntries(groupData.map(d => [d.grpCd, `\${d.grpNm}`]));
      const allGroupEditValues = Object.fromEntries(groupData.map(d => [d.grpCd, `\${d.grpNm}[\${d.grpCd}]`]));

      gridUtil = new GridUtil({
        url: "/api/sysmanage/codeManage/detailManage"
        , isPaging: true
        , gridOptions: {
          colData: [
            {
              headerName: "분류"
              , field: "grp"
              , formatter: GridUtils.selectFormatter
              , unformat: GridUtils.selectUnformatter
              , editable: true
              , edittype: "select"
              , editoptions: {
                value: {"A": "출입관리[A]", "B": "서비스신청[B]", "C": "보안활동[C]", "D": "자산반출입[D]", "Z": "공통[Z]"}
                , dataEvents: [
                  {
                    type: "change", fn: function (e) {
                      const $grid = gridUtil.getGrid();
                      const filteredData = groupData.filter(d => d.grp === $(e.target).val());
                      const newValues = Object.fromEntries(filteredData.map(d => [d.grpCd, `\${d.grpNm}[\${d.grpCd}]`]));
                      const colProp = $grid.getColProp("grpCd");
                      $grid.setColProp("grpCd", {editoptions: {...colProp.editoptions, value: newValues}});
                      $grid.setRowData($(e.target).closest("tr.jqgrow").attr("id"), {grpCd: "", detlCd: ""});
                    }
                  }
                ]
              }
              , cellattr: (rowId, _, rowdata) =>
                  (rowdata.crtDtm || rowdata.modDtm) ? ' class="not-editable-cell"' : null
            },
            {
              headerName: "분류명"
              , field: "grpCd"
              , align: "left"
              , formatter: GridUtils.selectFormatter
              , unformat: GridUtils.selectUnformatter
              , editable: true
              , edittype: "select"
              , searchoptions: {
                value: allGroupSearchValues
              }
              , editoptions: {
                value: allGroupEditValues
                , dataEvents: [
                  {
                    type: 'change', fn: function (e) {
                      console.log("e >>> ", e)
                      const $grid = gridUtil.getGrid();
                      $grid.setRowData($(e.target).closest("tr.jqgrow").attr("id"), {detlCd: $(e.target).val()});
                    }
                  }
                ]
              },
            },
            {
              headerName: "세부코드"
              , field: "detlCd"
              , align: "left"
              , editable: true
            },
            {
              headerName: "세부명"
              , field: "detlNm"
              , editable: true
            },
            {
              headerName: "ETC1"
              , field: "etc1"
              , editable: true
            },
            {
              headerName: "ETC2"
              , field: "etc2"
              , editable: true
            },
            {
              headerName: "ETC3"
              , field: "etc3"
              , editable: true
            },
            {
              headerName: "ETC4"
              , field: "etc4"
              , editable: true
            },
            {
              headerName: "정렬순서"
              , field: "sortSeq"
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
          , formatCell: function (rowId, cellname, value, iRow, iCol, ee) {
            const $grid = gridUtil.getGrid();

            if (cellname === "grpCd") {
              const rowData = $grid.getRowData(rowId);
              const newLists = groupData.filter(d => !rowData.grp || d.grp === rowData.grp).map(d => [d.grpCd, `\${d.grpNm}[\${d.grpCd}]`]);
              const newValues = Object.fromEntries(newLists);
              const colProp = $grid.getColProp("grpCd");
              $grid.setColProp("grpCd", {editoptions: {...colProp.editoptions, value: newValues}});
            }
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

      $("[name=searchgrp]").on("change", function() {
        let html = `<option value="" selected="selected">::분류를 선택하세요::</option>`;
        html += groupData.filter(d => d.grp === $(this).val()).map(d => `<option value="\${d.grpCd}">\${d.grpNm}[\${d.grpCd}]</option>`)
        $("[name=searchgrpCd]").html(html);
      });
    });
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
    const $grid = gridUtil.getGrid();
    fn_saveCell($grid);

    const rowSel = $grid.getGridParam("selarrrow").sort();
    const dataList = new Array();

    if (rowSel.length === 0) {
      alert("세부코드를 선택하세요.")
      return false;
    }

    let check = true;
    $.each(rowSel, function (index, value) {
      const rowData = $grid.getRowData(value);
      if(!rowData.grp) {
        alert("분류를 선택하지 않았습니다.");
        check = false;
        return;
      }
      if(!rowData.grpCd) {
        alert("분류명을 선택하지 않았습니다.");
        check = false;
        return;
      }
      if(!rowData.detlCd) {
        alert("세부코드를 입력하지 않았습니다.");
        check = false;
        return;
      }
      if (rowData.detlCd.length !== 8) {
        alert("세부코드 길이는 8글자로 구성됩니다.");
        check = false;
        return;
      }
      if (!rowData.detlNm) {
        alert("세부명을 입력하지 않았습니다.");
        check = false;
        return;
      }

      dataList.push($grid.getRowData(value));
    });

    if(!check) {
      return;
    }

    if (confirm("저장하시겠습니까?")) {
      $.esutils.postApi("/api/sysmanage/codeManage/detailManage/update", {dataList, empId: global.empId, acIp: global.acIp}, function (result) {
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
    var datarow = {"grp": "", "useYn": "Y"};
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
                    <td width="300px;">
                        <select name="searchgrp" id="searchgrp" style="width:120px;">
                            <option value="" selected="selected">전체</option>
                            <option value="A">출입관리[A]</option>
                            <option value="B">서비스신청[B]</option>
                            <option value="C">보안활동[C]</option>
                            <option value="D">자산반출입[D]</option>
                            <option value="Z">공통[Z]</option>
                        </select>
                    </td>
                    <th>분류명</th>
                    <td>
                        <select name="searchgrpCd" id="searchgrpCd" style="width:210px;">
                            <option value="" selected="selected">::분류를 선택하세요::</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>사용유무</th>
                    <td>
                        <select name="searchuseYn" id="searchuseYn" style="width:120px;">
                            <option value="">전체</option>
                            <option value="Y" selected="selected">사용중</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                    <th>세부코드</th>
                    <td>
                        <input type="text" name="searchdetlCd" id="searchdetlCd" style="width:200px;" maxlength="8" value=""/>
                    </td>
                </tr>
                <tr>
                    <th>세부명</th>
                    <td colspan="3">
                        <input type="text" name="searchdetlNm" id="searchdetlNm" style="width:200px;" value="" />
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