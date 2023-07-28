const GridUtil = function (params) {
  console.log("GridUtil.params", params);
  const _this = this;

  /***********************************
   * Variable
   ***********************************/
  /** UI만 사용 */
  _this.uiInit = params.uiInit;
  /** 통신 URL */
  _this.ajaxUrl = params.url;
  /** root 영역 */
  _this.$root = params.gridId ? $("#" + params.gridId) : $("#grid");
  /** $grid */
  _this.$grid = null;
  /** $grid Id */
  _this.gridId = Math.random().toString(36).substr(2, 11);
  /** $pager */
  _this.$pager = null;
  /** $pager Id */
  _this.pagerId = Math.random().toString(36).substr(2, 11);
  /** page Index Class Name */
  _this.pageIndexClassName = Math.random().toString(36).substr(2, 11);
  /** excelBtn Id */
  _this.excelBtnId = Math.random().toString(36).substr(2, 11);
  /** button Area Class Id */
  _this.buttonAreaClassName = Math.random().toString(36).substr(2, 11);
  /** form Id */
  _this.$form = params.search?.formId ? $("#" + params.search?.formId) : null;
  /** form filter fucntion */
  _this.customSearchFormData = typeof (params.search?.beforeSend)
  == "function" ? params.search?.beforeSend : null;
  /** searchBtnId */
  _this.searchBtnId = params.search?.buttonId;
  /** 검색 전 validtion */
  _this.beforeSendCheck = typeof (params.search?.beforeSendCheck) === "function" ? params.search?.beforeSendCheck : null;
  /** 페이징 표시 여부 */
  _this.isPaging = (params.isPaging != null && params.isPaging != undefined)
      ? params.isPaging : false;
  /** ajax method */
  _this.ajaxMethod = params.ajaxMethod ? params.ajaxMethod : "get";
  /** ajax async (default 비동기 동작) */
  _this.ajaxAsync = (params.ajaxAsync != null && params.ajaxAsync
      != undefined) ? params.ajaxAsync : true;
  /** Checkbox 적용여부 */
  _this.multiselect = params.gridOptions.isRowDataCheckBox
      ? params.gridOptions.isRowDataCheckBox : false;
  /** col Data */
  _this.colData = params.gridOptions.colData;
  /** row Click */
  _this.onRowClicked = typeof (params.gridOptions.onRowClicked) == "function"
      ? params.gridOptions.onRowClicked : null;
  /** 페이지당 컨텐츠 갯수 */
  _this.pageSize = params.pageSize ? params.pageSize : 10;
  /** 페이지 목록 갯수 */
  _this.pageSizeList = params.pageSizeList?.length > 0 ? params.pageSizeList : [];
  /** pagination Info */
  _this.paginationInfo = _this.isPaging ? {pageIndex: 1, pageSize: _this.pageSize} : {};
  /** excel Url */
  _this.excelUrl = params.excelUrl;
  /** excel file Name */
  _this.excelFileName = params.excelFileName ? params.excelFileName
      : "esecurity_excelData";
  /** original Row Data List */
  _this.rowDataList = [];
  /** row Data Key Field Name */
  _this.rowDataFieldKey = "";
  /** empty Text */
  _this.emptyText = params.gridOptions.emptyText;
  /** col event */
  _this.colEventArray = [];
  /** data List */
  _this.userData = params.userData;
  /** data List Cnt */
  _this.userDataCnt = null;
  /** grid default options */
  _this.gridOptions = {
    width: window.outerWidth * 0.45
    , height: "auto"
    //,height : window.outerHeight * 0.34
    , datatype: "local"
    , multiselect: _this.multiselect
    , shrinkToFit: true
    , rowNum: _this.isPaging ? _this.pageSize : 9999
    , beforeSelectRow: function (rowId, e) {
      if (!$(e.target).closest('td')[0]) {
        return false;
      }
      let i = $.jgrid.getCellIndex($(e.target).closest('td')[0]);
      let cm = $(this).jqGrid('getGridParam', 'colModel');
      let rowData = $(this).getRowData(rowId);
      let originRowData = null;

      /**
       *  colData key 설정이 있다면 originDataList에서 filed명으로 rowData를 찾음
       *  key 설정이 없다면 Index(순서)로 찾음
       */
      if (_this.rowDataFieldKey) {
        originRowData = _this.rowDataList.filter((d) => d[_this.rowDataFieldKey] === rowId)[0];
      } else {
        originRowData = _this.rowDataList[parseInt(rowId) - 1];
      }

      /** cell Click 가 rowClick보다 우선 */
      if (typeof (cm[i].onCellClicked) === "function") {
        cm[i].onCellClicked({rowData, originRowData});
        return false;
      } else if (typeof (_this.onRowClicked) === "function" && $(e.target).is('td')) {
        _this.onRowClicked({rowData, originRowData});
        return false;
      }
      return true;
    }
  };

  /***********************************
   * Function
   ***********************************/

  /** Init */
  _this.init = function () {
    /** grid와 paging 그릴영역 확인 */
    if (_this.$root.length === 0) {
      console.error("grid Area Not Exist...");
      return;
    } else {
      _this.$root.empty();
    }

    if (!_this.ajaxUrl && !_this.userData) {
      console.error("required url or userData...");
      return;
    }

    /** grid 영역 생성 & 엑셀 버튼 생성 */
    _this.renderGridSection();

    /** Col Data 가공 */
    if (!_this.colData || _this.colData?.length === 0) {
      console.error("required colData...");
      return;
    }
    params.gridOptions.colNames = _this.getColNames(_this.colData);
    params.gridOptions.colModel = _this.getColModel(_this.colData);

    /** col Event Info 저장 */
    _this.colEventInfo(_this.colData);

    /** key 여부 확인 */
    _this.confirmColDataKey(_this.colData);

    /** 사용자 dataList 확인 */
    _this.makeUserData();

    /** 그리드 세팅 */
    _this.setGrid({..._this.gridOptions, ...params.gridOptions});

    /** 동적 생성 태그에 대한 이벤트 */
    _this.onDocumentEvent();

  }
  /** 그리드 영역 삽입 */
  _this.renderGridSection = function () {

    if (_this.excelUrl || _this.pageSizeList?.length > 0) {
      _this.$root.append('<div class="' + _this.buttonAreaClassName + '"style="text-align:right;"></div>');

      if (_this.excelUrl) {
        $("." + _this.buttonAreaClassName).append('<span class=\"button bt_excel\"><button id="' + _this.excelBtnId + '" style=\"width:90px;\" type=\"button\">엑셀저장</button></span>')
      }

      if (_this.pageSizeList?.length > 0) {
        if (!_this.pageSizeList.includes(_this.pageSize)) {
          _this.pageSizeList.push(_this.pageSize);
        }

        _this.pageSizeList = _this.pageSizeList.sort();

        let _html = "<select>";
        $.each(_this.pageSizeList, function (i, d) {
          _html += "<option value='" + d + "'>" + d + "</option>";
        });
        _html += "</select>";

        $("." + _this.buttonAreaClassName).append(_html);

        // 페이지 검색조건 유지일경우
        if (_this.isSearchConditionRestore() && _this.isPaging) {
          const {paginationInfo} = _this.getSearchConditionRestoreData();
          $("." + _this.buttonAreaClassName).find('select').val(paginationInfo.pageSize);
        }

      }
    }

    /** grid 영역 확인 및 동적 태그 생성 */
    _this.$root.append("<table id=" + _this.gridId + "></table>");
    if (_this.isPaging) {
      _this.$root.append("<div id=" + _this.pagerId + " class=\"paging\"></div>");
    }

    /** grid 영역 */
    _this.$grid = $("#" + _this.gridId);
    /** paging Content영역 */
    _this.$pager = $("#" + _this.pagerId);
  }
  /**그리드 세팅 */
  _this.setGrid = function (gridOptions) {
    /** grid init */
    _this.$grid.jqGrid(gridOptions);
    /** grid col Merge */
    _this.colMerge(_this.colData);

    // var patchWidth = $("[aria-labelledby='gbox_"+_this.$grid.prop("id")+"']").css("width");
    // var patchTarget = $("[aria-labelledby='gbox_"+_this.$grid.prop("id")+"']").parent();
    // $(patchTarget).css("width", patchWidth);

    /** 검색 데이터 세팅 */
    if (_this.isSearchConditionRestore()) {
      /** 검색 데이터 element Setting*/
      _this.setElementSearchCondtion();

      const {paginationInfo, formData} = _this.getSearchConditionRestoreData();
      _this.setData(paginationInfo, formData);

    } else {
      _this.setData(_this.paginationInfo, _this.getFormData());
    }

  }

  /** 데이터 통신 및 grid 데이터 Set */
  _this.setData = function (paginationInfo, params) {

    if (_this.uiInit) {
      return;
    }

    /** session storage 저장 */
    _this.setSearchCondition(params, paginationInfo);

    /** 검색 validation 확인 */
    if (typeof (_this.beforeSendCheck) === "function" && !_this.beforeSendCheck()) {
      return;
    }

    const ajaxParam = {...paginationInfo, ...params};

    /** clearGridData 사용해야 pageIndex 이동시 데이터가 맞게 표출  */
    _this.$grid.clearGridData();

    /** grid rowperPage 재설정 */
    if (_this.isPaging) {
      _this.$grid.setGridParam({rowNum: paginationInfo.pageSize});
    }

    /***********************************************************************************
     * [UuserData 일 경우]
     ***********************************************************************************/
    if (Array.isArray(_this.userData)) {
      if (_this.userData.length > 0) {
        let viewData = [];
        if (_this.isPaging) {
          const pageSize = paginationInfo.pageSize;
          const startIndex = (paginationInfo.pageIndex - 1) * paginationInfo.pageSize;
          const endIndex = ((paginationInfo.pageIndex - 1) * paginationInfo.pageSize) + paginationInfo.pageSize - 1;

          viewData = _this.userData.slice(startIndex, endIndex);
        } else {
          viewData = _this.userData;
        }

        /** rowDataList 저장 */
        _this.rowDataList = viewData;

        // grid 세팅
        _this.$grid.setGridParam({data: viewData}).trigger("reloadGrid");

        /** grid row style pointer */
        _this.styleCssEffect();

        /** 페이징 세팅 */
        if (_this.isPaging) {
          _this.renderPager(paginationInfo.pageSize, paginationInfo.pageIndex, _this.userDataCnt);
        }

        /** cell 사용자 이벤트 적용 */
        _this.onCellUserEvent();

      } else {
        _this.renderEmpty();
      }
    }
    /***********************************************************************************
     * [url 데이터 통신일 경우]
     ***********************************************************************************/
    else if (_this.ajaxUrl) {
      /** 데이터 통신 */
      $.esutils.callApi(_this.ajaxMethod, _this.ajaxUrl, ajaxParam, function (res) {

        /** grid Data set */
        if (res && res.data && res.data.length > 0) {

          _this.$grid.setGridParam(
              {data: res.data}).trigger("reloadGrid");

          /** rowDataList 저장 */
          _this.rowDataList = res.data;

          /** grid row style pointer */
          _this.styleCssEffect();

          /** 페이징 세팅 */
          if (_this.isPaging && res && res.dataCount) {
            _this.renderPager(paginationInfo.pageSize, paginationInfo.pageIndex, res.dataCount);
          }

          /** cell 사용자 이벤트 적용 */
          _this.onCellUserEvent();

        } else {
          _this.renderEmpty();
        }

        //loading
        $("#load_" + _this.gridId).hide();

      }, {
        loading: false,
        async: _this.ajaxAsync,
        beforeSend: function () {
          $("#load_" + _this.gridId).show();
        },
        error: function () {
          $("#load_" + _this.gridId).hide();
        }
      });
    }

  }

  /** 데이터가 없을경우 Render */
  _this.renderEmpty = function () {
    const checkboxCol = _this.multiselect ? 1 : 0;
    const colCount = _this.getColCnt(_this.colData)
        + checkboxCol;

    const emptyText = _this.emptyText ? _this.emptyText : "조회 결과가 없습니다.";

    _this.$grid.find("tbody").append(
        '<tr id=' + _this.gridId + '_emptyContent><td colspan="' + colCount
        + '"><div class="search_result2">' + emptyText + '</div></td></tr>');
    _this.renderPager(null, null, 0);
  }

  /** Render Pager */
  _this.renderPager = function (pageSize, pageIndex, totalCnt) {
    if (_this.$pager.length > 0) {
      if (totalCnt !== 0) {
        let pagingHtml = _this.makePagingHtml(pageSize,
            pageIndex, totalCnt, true);
        _this.$pager.html(pagingHtml);
        _this.$pager.show();
      } else {
        _this.$pager.empty();
        _this.$pager.hide();
      }
    }
  }

  /** 페이징 컨텐츠 Make */
  _this.makePagingHtml = function (pageSize, pageIndex, totalCnt,
      isShowPageInfo) {

    const startPage = parseInt((pageIndex - 1) / 10) * 10 + 1;
    const pageRecord = totalCnt % pageSize > 0 ? parseInt(
        totalCnt / pageSize) + 1 : totalCnt / pageSize;
    const endPage = pageRecord > (startPage + 9) ? startPage + 9 : pageRecord;
    const prevPage = startPage === 1 ? 1 : startPage - 1;
    const nextPage = endPage === pageRecord ? pageRecord : endPage + 1;
    let pagingHtml = "";
    let pageNo = 0;
    pagingHtml += "<a href='javascript:void(0);' data-pageindex='1' class='prev " + _this.pageIndexClassName + "'><img src='/esecurity/assets/common/images/common/prev_end.jpg' alt='맨처음' /></a>";
    pagingHtml += "<a href='javascript:void(0);' data-pageindex='"
        + prevPage
        + "' class='prev " + _this.pageIndexClassName + "'><img src='/esecurity/assets/common/images/common/prev.jpg' alt='이전' /></a>";
    for (let i = startPage; i < endPage + 1; i++) {
      pageNo = i;
      if (pageIndex === pageNo) {
        pagingHtml += "<a href='javascript:void(0);' title ='현재페이지' data-pageindex='"
            + pageNo + "' class='" + _this.pageIndexClassName + "'><b>" + pageNo
            + "</b></a>";
      } else {
        pagingHtml += "<a href='javascript:void(0);' class='" + _this.pageIndexClassName + "' data-pageindex='"
            + pageNo + "'>" + pageNo + "</a>";
      }
    }

    pagingHtml += "<a href='javascript:void(0);' data-pageindex='"
        + nextPage
        + "' class='next " + _this.pageIndexClassName + "'><img src='/esecurity/assets/common/images/common/next.jpg' alt='다음' /></a>";
    pagingHtml += "<a href='javascript:void(0);' data-pageindex='"
        + pageRecord
        + "' class='next " + _this.pageIndexClassName + "'><img src='/esecurity/assets/common/images/common/next_first.jpg' alt='맨마지막' /></a>";

    if (isShowPageInfo) {
      const totalPage = parseInt(totalCnt / pageSize) + (totalCnt
      % pageSize > 0 ? 1 : 0);
      pagingHtml += "<div style='float:left'>총 <span id='totalCount'>"
          + totalCnt + "</span>건 <span id='currPage'>" + pageIndex
          + "</span> / <span id='totalPage'>" + totalPage
          + "</span> 쪽</div>";
    }

    return pagingHtml;
  }

  /** pageIndex 이동 이벤트 */
  _this.onDocumentEvent = function () {
    if (_this.isPaging) {
      $(document).on("click", '.' + _this.pageIndexClassName, function (e) {
        if ($(this).parent().attr("id") == _this.$pager.attr("id")) {
          _this.paginationInfo = {..._this.paginationInfo, pageIndex: Number($(this).data("pageindex"))};
          _this.setData(_this.paginationInfo, _this.getFormData());
        }
      })
    }

    // 엑셀버튼
    if (_this.excelUrl) {
      $(document).on("click", '#' + _this.excelBtnId, function (e) {
        _this.excelDownload();
      });
    }

    // 검색버튼
    if (_this.searchBtnId) {
      $('#' + _this.searchBtnId).on("click", function (e) {
        _this.searchData();
      });

      if (_this.$form?.length > 0) {
        _this.$form.find(":input").on('keypress', function (e) {
          if (e.keyCode === 13) {
            e.preventDefault();
            _this.searchData();
            $(this).focus();
          }
        });
      }

    }

    // 페이지 사이즈 SelectBox
    if (_this.pageSizeList) {
      $("." + _this.buttonAreaClassName).find("select").on("change", function () {
        _this.searchData({pageSize: parseInt($(this).val())});
      })
    }
  }

  /** 사용자 데이터 확인 */
  _this.makeUserData = function () {
    if (_this.userData) {
      if (Array.isArray(_this.userData)) {
        _this.userDataCnt = _this.userData.length;
      } else {
        _this.userData = [_this.userData];
        _this.userDataCnt = _this.userData.length;
      }
    }
  }

  /** row Style css 적용 */
  _this.styleCssEffect = function () {

    const ids = _this.$grid.getDataIDs();

    const cssObj = {
      cursor: "pointer"
    };

    // row Clicked이라면 로우에 cursor css
    if (_this.onRowClicked) {
      $.each(
          ids, function (idx, rowId) {
            _this.$grid.setRowData(rowId, false, cssObj);
          }
      );
    } else {
      // row Click이 없고 onCellClicked 있을시.
      for (let col of _this.colData) {
        if (col['onCellClicked']) {
          $.each(ids, function (_, d) {
            _this.$grid.jqGrid('setCell', d, col['field'], '', cssObj);
          });
        }
      }
    }

  }
  /** make colNames function */
  _this.getColModel = function (colData) {

    let colModels = [];
    const defaultColModel = {
      name: ""
      , index: ""
      , width: "10%"
      , align: "center"
      , sortable: false
      , editable: false
      , title : false // grid td column title 제거
    }

    for (let col of colData) {
      colModels.push({...defaultColModel, ...col, name: col.field, index: col.field});
    }

    return colModels;

  }
  /** make colModel function */
  _this.getColNames = function (colData) {
    let colNames = [];
    for (let col of colData) {
      colNames.push(col['headerName']);
    }
    return colNames;
  }

  /** get col count */
  _this.getColCnt = function (colData) {
    let i = 0;
    for (let col of colData) {
      if (!col['hidden']) {
        i++;
      }
    }
    return i;
  }
  /** merge 함수 */
  _this.colMerge = function (colData) {

    let mergeInfoArray = [];
    for (let col of colData) {
      if (col['colMerge']) {

        mergeInfoArray.push(
            {
              startColumnName: col.field
              , numberOfColumns: col['colMerge']['colspan']
              , titleText: col['colMerge']['headerName']
            }
        );
      }
    }

    if (mergeInfoArray.length > 0) {
      _this.$grid.jqGrid('setGroupHeaders', {
        useColSpanStyle: true,
        groupHeaders: mergeInfoArray
      });
    }
  }

  /** get Form Data */
  _this.getFormData = function () {
    let _formData = {};
    if (_this.$form?.length > 0) {
      _formData = $.esutils.getFieldsValue(_this.$form);
    }
    if (_this.customSearchFormData) {
      _formData = _this.customSearchFormData(_formData);
    }
    return _formData;
  }

  /** colData내에 key 설정 확인 */
  _this.confirmColDataKey = function (colData) {
    const keyIndex = colData.findLastIndex((col) => !!col.key);
    if (keyIndex !== -1) {
      _this.rowDataFieldKey = colData[keyIndex].field;
    }
  }

  /** colData내에 Event 저장 */
  _this.colEventInfo = function (colData) {
    _this.colEventArray = colData.filter((d) => !!d.event).map((d) => {
      return {field: d.field, className: d.event.className, onClicked: d.event.onClicked};
    });
  }

  /** cell내에 사용자 지정 Element에 event 적용 */
  _this.onCellUserEvent = function () {
    if (_this.colEventArray.length > 0) {
      // for (colEvent of _this.colEventArray) {
      for (let i = 0; i < _this.colEventArray.length; i++) {
        const colEvent = _this.colEventArray[i];
        if (colEvent.className && colEvent.onClicked && typeof (colEvent.onClicked) === "function") {

          _this.$grid.find("[aria-describedby='" + _this.gridId + "_" + colEvent.field + "']").find("." + colEvent['className']).on("click", function (e) {
            const rowId = $(this).closest("tr").attr("id");
            let originRowData = {};
            if (_this.rowDataFieldKey) {
              originRowData = _this.rowDataList.filter((d) => d[_this.rowDataFieldKey] === rowId)[0];
            } else {
              originRowData = _this.rowDataList[parseInt(rowId) - 1];
            }
            colEvent.onClicked({originRowData, rowId, e, field: colEvent.field});
          });
        }
      }
    }
  }

  /** 검색 정보 Session Storage 저장 */
  _this.setSearchCondition = function (params, paginationInfo) {

    const pathName = window.location.pathname;
    const lastSlashIndex = pathName.lastIndexOf("/");
    const pageType = pathName.substring(lastSlashIndex + 1, pathName.length);

    if (!GridUtils.listTypeUrlArray.includes(pageType)) {
      return;
    }

    // form Data
    if (params && Object.keys(params).length > 0) {
      sessionStorage.setItem(GridUtils.sessionStorage.paramKey, JSON.stringify(params));
    } else {
      sessionStorage.removeItem(GridUtils.sessionStorage.paramKey);
    }

    // paging 정보
    if (paginationInfo && Object.keys(paginationInfo).length > 0) {
      sessionStorage.setItem(GridUtils.sessionStorage.pagingKey, JSON.stringify(paginationInfo));
    } else {
      sessionStorage.removeItem(GridUtils.sessionStorage.pagingKey);
    }

    if (_this.$form?.find("select").length > 0) {
      sessionStorage.setItem(GridUtils.sessionStorage.selectBoxHtmlKey, _this.getSelectBoxHtml(params));
    } else {
      sessionStorage.removeItem(GridUtils.sessionStorage.selectBoxHtmlKey);
    }
  }

  /** Form 내에 selectBox Html을 저장 */
  _this.getSelectBoxHtml = function (params) {
    const selectBoxHtmlArray = [];

    _this.$form.find("select").each(function (i, el) {
      if (el.id) {
        selectBoxHtmlArray.push({id: el.id, content: $(el).html()});
      }
    });

    return selectBoxHtmlArray.length > 0 ? JSON.stringify(selectBoxHtmlArray) : "";
  }

  /** 검색 조건을 복원 여부 판단 */
  _this.isSearchConditionRestore = function () {
    const pageInfo = sessionStorage.getItem(GridUtils.sessionStorage.pagePrevCurrentInfo);

    if (pageInfo.length === 0) {
      return false;
    }

    const pageInfoArray = JSON.parse(sessionStorage.getItem(GridUtils.sessionStorage.pagePrevCurrentInfo));

    // 페이지 스택이 2개 이상
    if (pageInfoArray.length > 1) {

      // 전페이지 PrefixUrl
      const prevPrefixUrl = pageInfoArray[0].prefixPageUrl;
      // 현재 페이지 prefixUrl
      const currentPrefixUrl = pageInfoArray[1].prefixPageUrl;

      // 전페이지 pageType
      const prevPageType = pageInfoArray[0].pageType;
      // 현재페이지 pageType
      const currentPageType = pageInfoArray[1].pageType;

      // pageType을 제외한 url이 이전 페이지와 현재 페이지가 같아야하며
      if (prevPrefixUrl === currentPrefixUrl) {
        // 현재페이지는 리스트타입(그리드페이지) 이며 이전페이지는 현재 그리드페이지의 상세여야 한다.
        if (GridUtils.detailTypeUrlArray.includes(prevPageType) && GridUtils.listTypeUrlArray.includes(currentPageType)) {
          return true;
        }
      }
      return false;
    } else {
      return false;
    }

  }

  /** 검색 조건을 복원할 데이터 리턴 */
  _this.getSearchConditionRestoreData = function () {

    let formData = {};
    let paginationInfo = {};
    try {
      if (sessionStorage.getItem(GridUtils.sessionStorage.paramKey)) {
        formData = JSON.parse(sessionStorage.getItem(GridUtils.sessionStorage.paramKey));
      }
    } catch (e) {
      console.error("JSON.parse GridUtils.sessionStorage.paramKey Error", e);
    }

    try {
      if (sessionStorage.getItem(GridUtils.sessionStorage.pagingKey)) {
        paginationInfo = JSON.parse(sessionStorage.getItem(GridUtils.sessionStorage.pagingKey));
      }
    } catch (e) {
      console.error("JSON.parse GridUtils.sessionStorage.pagingKey Error", e);
      paginationInfo = {..._this.paginationInfo};
    }

    return {paginationInfo, formData};
  }

  /** 검색조건을 Element에 세팅한다 */
  _this.setElementSearchCondtion = function () {

    try {
      if (sessionStorage.getItem(GridUtils.sessionStorage.selectBoxHtmlKey)) {
        const selectBoxHtmlInfo = JSON.parse(sessionStorage.getItem(GridUtils.sessionStorage.selectBoxHtmlKey));
        // select Box option Value Html
        if (selectBoxHtmlInfo.length > 0) {
          for (selectBox of selectBoxHtmlInfo) {
            $("#" + selectBox["id"]).html(selectBox["content"]);
          }
        }
      }
    } catch (e) {
      console.error(e);
    }

    try {
      if (sessionStorage.getItem(GridUtils.sessionStorage.paramKey)) {

        const formData = JSON.parse(sessionStorage.getItem(GridUtils.sessionStorage.paramKey));

        if (Object.keys(formData).length > 0) {
          for (key in formData) {

            if (_this.$form.find("[name=" + key + "]").is("input[type=text]") || _this.$form.find("[name=" + key + "]").is("select")) {
              _this.$form.find("[name=" + key + "]").val(formData[key]);
            } else if (_this.$form.find("[name=" + key + "]").is("input[type=radio]")) {
              _this.$form.find("input:radio[name=" + key + "][value=" + formData[key] + "]").prop("checked", true);
            } else if((_this.$form.find("[name=" + key + "]").is("input[type=checkbox]"))) {
              _this.$form.find("input:checkbox[name=" + key + "][value=" + formData[key] + "]").prop("checked",true);
            }
          }
        }
      }
    } catch (e) {
      console.error(e);
    }
  }

}//END

/**********************************
 * [gridUitl 정의 함수]
 **********************************/

/** 체크박스 체크된 rowData 반환 */
GridUtil.prototype.getChkCheckedDatas = function () {
  let _this = this;
  const sel_rows = _this.getGrid().jqGrid("getGridParam", "selarrrow");
  let chkArray = [];

  if (sel_rows.length > 0) {
    $(sel_rows).each(function (idx, v) {
      chkArray.push(_this.getGrid().jqGrid('getRowData', v));
    });
    return chkArray;
  } else {
    return [];
  }
}

/**
 * 동적으로 Cell 내용 변경
 * param : {
 *          rowId : grid 로우 Id
 *          ,fieldName :  필드명
 *          ,content :  바꿀 내용
 *          }
 * */
GridUtil.prototype.setCellContent = function (datas) {
  this.getGrid().jqGrid("setCell", datas.rowId, datas.fieldName,
      datas.content);
}

/**
 * GridUtil의 grid를 반환
 */
GridUtil.prototype.getGrid = function () {
  return this.$grid;
}

/**
 * 사용자 검색 이벤트
 */
GridUtil.prototype.searchData = function (userParams) {
  let _this = this;
  // UI init false
  _this.uiInit = false;

  // 검색 validation 확인
  if (typeof (_this.beforeSendCheck) === "function" && !_this.beforeSendCheck()) {
    return;
  }
  /** pageIndex 1로 초기화*/
  _this.paginationInfo = _this.isPaging ? {pageSize: _this.pageSize, pageIndex: 1} : {};
  if (userParams && Object.keys(userParams).length > 0) {
    if (userParams.pageSize) {
      _this.paginationInfo = {..._this.paginationInfo, pageSize: userParams.pageSize};
    }
    if (userParams.pageIndex) {
      _this.paginationInfo = {..._this.paginationInfo, pageIndex: userParams.pageIndex};
    }
  }
  _this.setData(_this.paginationInfo, {..._this.getFormData(), ...userParams});
}

/**
 * 사용자 reload 이벤트
 * @description 검색조건을 유지한채 data refresh
 */
GridUtil.prototype.refresh = function () {
  let _this = this;
  _this.setData(_this.paginationInfo, {..._this.getFormData()});
}

/**
 * Excel download 함수
 * gridUtil.setSearchParam의 검색조건을 통해 Excel Download 실행
 */
GridUtil.prototype.excelDownload = function () {
  try {
    let _this = this;

    if (!_this.excelUrl) {
      return;
    }

    let param = _this.getFormData();
    delete param.pageSize;
    delete param.pageIndex;

    const fileName = _this.excelFileName;

    const url = global.contextPath + _this.excelUrl + "?" + new URLSearchParams(
        param).toString();
    const req = new XMLHttpRequest();

    req.open("GET", url);
    req.responseType = "blob";
    req.onload = function () {
      const arrayBuffer = req.response;
      if (arrayBuffer) {
        const blob = new Blob([arrayBuffer],
            {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = fileName + ".xlsx";
        link.click();
      }
    };

    req.send();
  } catch (e) {
    console.error(e);
  }

}

/**
 * grid 영역의 Button영역의 div class명을 조회.
 */
GridUtil.prototype.getBtnAreaClassNm = function () {
  return this.buttonAreaClassName;
}

const GridUtils = {};
(function (_) {
  /** select(editable mode) formatter */
  _.selectFormatter = function (cellvalue, options) {
    let temp = '';
    $.each(options.colModel.editoptions.value, function (key, value) {
      if (cellvalue == key) {
        temp = value;
        return false;
      }
    });
    return temp;
  }
  /** select(editable mode) unformatter */
  _.selectUnformatter = function (cellvalue, options) {
    let temp = '';
    $.each(options.colModel.editoptions.value, function (key, value) {
      if (cellvalue == value) {
        temp = key;
        return false;
      }
    });
    return temp;
  }

  /** 뒤로가기 검색조건 분류 타입 */
  _.detailTypeUrlArray = ["detail", "view", "detail1", "detail2"];
  _.listTypeUrlArray = ["list"];
  /** 검색 조건 복원 세션스토리지 키 */
  _.sessionStorage = { // 뒤로가기 검색조건 유지 관련 세션트로지ㅣ key 정보
    paramKey: "esecurityParams" // 검색조건 저장
    , pagingKey: "esecurityPaging" // 페이징 정보 저장
    , selectBoxHtmlKey: "esecuritySelectBox" // selebxBoxHtml 저장
    , pagePrevCurrentInfo: "esecurityPageInfo" // page 방문 순서 저장
  }

})(GridUtils);

/**
 * 뒤로가기, 상세화면에서 리스트(목록)화면 으로 돌아갈 때 검색 조건 복원을 위한 정보 저장
 * 2개의 크기를 가진 배열에 저장하며 pathName을 기준으로 마지막 '/' 기준으로 앞과 뒤의 내용을 저장한다
 *
 */
(function () {
  try {
    const pathName = window.location.pathname;
    const lastSlashIndex = pathName.lastIndexOf("/");
    const pageType = pathName.substring(lastSlashIndex + 1, pathName.length);
    const prefixPageUrl = pathName.substring(0, lastSlashIndex);

    const pageInfo = sessionStorage.getItem(GridUtils.sessionStorage.pagePrevCurrentInfo);
    let pageInfoArray = [];

    if (!pageInfo) {
      pageInfoArray.push({prefixPageUrl, pageType});
      sessionStorage.setItem(GridUtils.sessionStorage.pagePrevCurrentInfo, JSON.stringify(pageInfoArray));
      return;
    }

    pageInfoArray = JSON.parse(sessionStorage.getItem(GridUtils.sessionStorage.pagePrevCurrentInfo));

    // 페이지 2개 일시 shift
    if (pageInfoArray.length === 2) {
      pageInfoArray.shift();
    }

    pageInfoArray.push({prefixPageUrl, pageType});
    sessionStorage.setItem(GridUtils.sessionStorage.pagePrevCurrentInfo, JSON.stringify(pageInfoArray));
  } catch (e) {
  }
}());