<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;

  $(document).ready(function () {
    gridUtil = new GridUtil({
      // grid Data 통신 URL >> 필수
      url: "/api/sysmanage/notice/list"
      , isPaging: true
      , pageSize: 10
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , align: "center"
            , field: "rowNum"
            , width: "5%"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "구분"
            , field: "inoutGbn"
            , formatter: function (cellValue, options, row) {
              if (cellValue === 'ALL') {
                return "전체"
              } else if (cellValue === 'IN') {
                return "내부망"
              } else if (cellValue === 'OUT') {
                return "외부망"
              } else if (cellValue === 'INOUT') {
                return "내외부공통"
              }
            }
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "제목"
            , field: "title"
            , width: "30%"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "작성자명"
            , field: "crtBy"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "작성일"
            , field: "crtDtm"
            , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "조회수"
            , field: "readCnt"
            , onCellClicked: onCellSelectCallback
          },
        ]
        /** row Click callback function (rowCLick보다 cellClick이 우선시됨)*/
        , onRowClicked: function (rowData) {
          console.log("onRowClick", rowData);
        }
      },
      search: {
        formId: "noticeListForm"
        , buttonId: "searchBtn"
        , beforeSend: function (formData) {
          return {...formData, sampleData: "TEST"};
        }
      }
    });

    //grid init
    gridUtil.init();

    // grid 동적 event
    onDocumentEvent();
  });

  // cell Click시 Callback Function
  const onCellSelectCallback = ({originRowData}) => {
    $.esutils.href("/sysmanage/board/notice/detail", {boardNo: originRowData.boardNo})
  }

  function onButtonClick(fieldName, rowId) {
    if (confirm("버튼 클릭 하시겠습니까?")) {

      /** reload 없이 cell 내용만 바꾸어야 한다면 */
      gridUtil.setCellContent({
        rowId: rowId
        , fieldName: fieldName
        , content: "<div>완료</div>"
      });
      /** grid reload가 필요하다면 */
    }
  }

  function getChkRowDatas() {
    //체크된 RowData Callback([rowDatas])
    console.log("getChkRowDatas", gridUtil.getChkCheckedDatas());
  }

  function onDocumentEvent() {
    $(document).on("click", ".sampleCheck", function () {
      const rowId = $(this).attr('name').split("_")[1];
      console.log("checkbox click", gridUtil.getGrid().getRowData(rowId));
    })
  }

  /***************************************************************************
   * 글쓰기
   ***************************************************************************/
  const boardInsert = function () {
    let formData = document.createElement('form');
    formData.setAttribute('method', 'post');
    formData.setAttribute('action', global.contextPath + '/sysmanage/board/notice/request');
    document.body.appendChild(formData);
    formData.submit();
  };
</script>

<!-- wrap start-->
<div class="wrap">
    <!-- contentWrap start-->
    <div class="contentWrap">
        <!-- contentsArea start -->
        <div id="content_area">
            <div id="contentsArea">
                <!-- <h1 class="title">산업보안 공지사항</h1> -->
                <img src="/eSecurity/common/images/common/subTitle/board/tit_319.png"/>
                <!-- realContents start -->
                <div id="realContents">
                    <form id="target_form" name="target_form" method="post"></form>
                    <form id="noticeListForm" name="noticeListForm" method="post"
                          onsubmit="return false;">
                        <input type="hidden" name="callback" value=""/>
                        <!-- 검색영역 시작 -->
                        <div id="search_area">
                            <!-- 검색 -->
                            <div class="search_content">
                                <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01">
                                    <caption>조회화면</caption>
                                    <tr id="titlesearch1">
                                        <th>제목</th>
                                        <td>
                                            <input type="text" id="searchTitle" name="searchTitle" value="" style="width:270px;"/>
                                        </td>
                                        <th>내/외부망</th>
                                        <td>
                                            <select id="inoutGbn" name="inoutGbn" style="width:180px;">
                                                <option value="ALL">전체</option>
                                                <option value="IN">내부망</option>
                                                <option value="OUT">외부망</option>
                                                <option value="INOUT">내외부공통</option>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                                <!-- 버튼 -->
                                <div class="searchGroup">
                                    <div class="centerGroup">
                                        <span class="button bt_l1">
                                            <button type="button" id="searchBtn" style="width:80px;">
                                                검색
                                            </button>
                                        </span>
                                    </div>
                                </div>
                                <!-- 버튼 끝 -->
                            </div>
                        </div>
                        <!-- 검색영역 끝 -->

                        <!-- 기능버튼 -->
                        <div style="float: none;">
                            <table cellpadding="0" cellspacing="0" width="850px" border="0" class="view_board03">
                                <caption>기능버튼</caption>
                                <tbody>
                                <tr>
                                    <td align="right">
                                        <%--                                        <c:if test="${adm_yn eq 'Y'}">--%>
                                        <%--                                            <span class="button bt_s2" id="insertButton"><button--%>
                                        <%--                                                    tabindex="1" onclick="javascript:boardInsert();"--%>
                                        <%--                                                    style="width:60px;" type="button">글쓰기 </button></span>--%>
                                        <%--                                        </c:if>--%>
                                        <span class="button bt_s2" id="insertButton">
                                                <button tabindex="1" onclick="boardInsert();" style="width:60px;" type="button">글쓰기 </button>
                                        </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- 기능버튼 끝 -->
                        <div id="grid"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>