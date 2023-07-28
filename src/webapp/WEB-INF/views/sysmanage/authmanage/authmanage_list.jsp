<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

  let gridUtil;
  $(document).ready(function () {

    /******************
     * grid 내용 설정
     ******************/
    gridUtil = new GridUtil({
          url: "/api/sysmanage/authManage/authManageList"
          , isPaging: true
          , pageSize : 20
          , gridOptions: {
            colData: [
              {
                headerName: "권한코드"
                , field: "authId"
                , editable: true
              },
              {
                headerName: "순서"
                , field: "seq"
                , width: "5%"
                , editable: true
              },
              {
                headerName: "권한명"
                , field: "authNm"
                , width: "15%"
                , align: "left"
                , editable: true
                , editoptions:{ maxlength:"100"}
              },
              {
                headerName: "권한설명"
                , field: "authRmrk"
                , width: "30%"
                , align: "left"
                , editable: true
                , editoptions:{ maxlength:"500"}
              },
              {
                headerName: "사용유무"
                , field: "useYn"
                , align: "center"
                , editable:true
                , edittype:"select"
                , editoptions:{ value:{"Y":"사용중","N":"미사용"} }
                , formatter: GridUtils.selectFormatter
                , unformat: GridUtils.selectUnformatter
              },
              {
                headerName: "권한종류"
                , field: "authKnd"
                , align: "center"
                , editable:true
                , edittype:"select"
                , editoptions:{ value:{"1":"일반권한","2":"결재권한"} }
                , formatter: GridUtils.selectFormatter
                , unformat: GridUtils.selectUnformatter
              },
              {
                headerName: "관리구분"
                , field: "managementKnd"
                , align: "center"
                , editable: true
                , edittype: "select"
                , editoptions: {value: {"1": "최고관리자", "2": "회사관리자"}}
                , formatter: GridUtils.selectFormatter
                , unformat: GridUtils.selectUnformatter
              },
              {
                headerName: "등록일시"
                , field: "crtDtm"
                , align: "center"
              },
            ]
            , afterEditCell: function (rowId, cellname, value, iRow, iCol) {
              $(this).setSelectedCol(iCol);
              $(this).setSelectedRow(iRow);
            }
            ,afterSaveCell: function (rowId, cellname, value, iRow, iCol) {
                var row = $(this).getRowData(rowId);
                switch (cellname) {
                  case 'authId':
                    if (value.length > 1) {
                      $(this).setRowAdded(false);
                    }
                    if (row.authId.length > 0) {
                      alert("권한코드는 입력 할 수 없습니다.");
                      $(this).jqGrid("restoreCell", iRow, iCol);
                    } else {

                    }

                    break;
                }
            }
            ,autoencode:true
            ,cellEdit : true
            ,cellsubmit: "clientArray"
            ,useMultiRow:false
            ,isRowDataCheckBox: true
            ,onCellSelect: function(iRow, iCol, cellcontent) {
              if(iCol !== 0) { //체크박스 제외
                var s = $(this).getGridParam("selarrrow");
                var selected = $.inArray(iRow, s) != -1;
                if (!selected) {
                  $(this).jqGrid("setSelection", iRow);
                }
              }
            },
            onSelectRow: function(rowId)
            {	// row 선택시 발생하는 이벤트
              saveCell($(this));
            },
            afterInsertRow: function(rowid,  rowdata ,rowelem ){		// row 추가시 발생하는 이벤트
              if(rowid === 0) {
                $(this).setRowAdded(true);
              } else {
                $(this).setRowAdded(false);
              }
            },
            gridComplete: function() {
              $(this).jqGrid("setFrozenColumns");
              $(this).setSelectedRow(-1);
              $(this).setSelectedCol(-1);
              $(this).setRowIdx($(this).getGridParam("reccount") + 1);	//	신규 추가될 row에 부여할 id를 초기화
            }
      },
    search: {
      formId: "formGrid"
      , buttonId: "searchBtn"
    }
  });
    // grid init
    gridUtil.init();

    //추가 버튼 클릭시
    onAddRow();

    //저장 버튼 클릭시
    onSave();

  });


  /*******************
   * [추가 버튼 클릭시]
   *******************/
  function onAddRow() {
    $("#addRowBtn").on("click", function() {
      saveCell(gridUtil.getGrid());
      if( gridUtil.getGrid().getRowAdded()) return;

      let rowIdx = 0;
      const datarow = {"useYn":"Y"};
      const su = gridUtil.getGrid().jqGrid("addRowData", rowIdx, datarow, "first");
      if(su) {
        //$("#Jlist").editCell(1, 3, true);
        gridUtil.getGrid().jqGrid("setSelection", rowIdx);
      }
    });

  }

  function saveCell($grid) {
    if($grid.getSelectedCol() == -1 || $grid.getSelectedRow() == -1) return;
    $grid.saveCell($grid.getSelectedRow(), $grid.getSelectedCol());
    $grid.setSelectedRow(-1);
    $grid.setSelectedCol(-1);
  }

  /*******************
   * [저장 버튼 클릭시]
   *******************/
  function onSave() {

    $("#saveBtn").on("click", function() {
      if(confirm("저장하시겠습니까?")) {
        const $grid = gridUtil.getGrid();
        saveCell($grid);
        const rowSel = $grid.getGridParam("selarrrow").sort();

        const updateList = [];
        let isValidation = true;

        if(rowSel.length === 0) {
          alert("선택된 데이터가 없습니다.");
          return;
        }

        const empId = global.empId;
        const acIp = global.acIp;

        $.each(rowSel, function(index, rowId) {
          const data = gridUtil.getGrid().getRowData(rowId);
          for(key in data) {
            if(key !== "crtDtm" && key !== "authId" && !data[key]) {
              isValidation = false;
            }
          }
          updateList.push({...data, acIp, crtBy : empId, modBy : empId});
        });

        if(!isValidation) {
          alert("데이터를 입력해주세요.");
          return;
        }

        $.esutils.postApi("/api/sysmanage/authManage/authManage/upsertAuthManage", {acIp, empId, updateList}, function (result) {
          if (result) {
            alert("저장 되었습니다.");
            gridUtil.searchData();
          } else {
            alert("저장중 에러가 발생하였습니다.");
          }
        });

      }
    });



  }

</script>

<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <colgroup>
                    <col width="15%"/>
                    <col width="35%"/>
                    <col width="15%"/>
                    <col width="35%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>사용유무</th>
                    <td>
                        <select name="searchUseYn" id="searchUseYn" style="width:180px;">
                            <option value="ALL">전체</option>
                            <option value="Y" selected="selected">사용중</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                    <th>권한종류</th>
                    <td>
                        <select name="authKnd" id="authKnd" style="width:180px;">
                            <option value="" selected="selected">전체</option>
                            <option value="1">일반권한</option>
                            <option value="2">결제권한</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>관리구분</th>
                    <td>
                        <select name="managementKnd" id="managementKnd" style="width:180px;">
                            <option value="" selected="selected">전체</option>
                            <option value="1">최고관리자</option>
                            <option value="2">회사관리자</option>
                        </select>
                    </td>
                    <th>권한명</th>
                    <td>
                        <input type="text" name="searchAuthNm" id="searchAuthNm" style="IME-MODE:enable;width:250px;" value=""/>
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
<div style="float: none;">
    <table cellpadding="0" cellspacing="0" width="850px" border="0" class="view_board03">
        <caption>기능버튼</caption>
        <tbody>
        <tr>
            <td width="258px"><span id="gate_list"></span></td>
            <td align="right">
                <span class="button bt_s2"><button tabindex="1" id="addRowBtn"  style="width:80px;" type="button">추가</button></span>
                <span class="button bt_s2"><button tabindex="2" id="saveBtn" type="button">저장</button></span>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->