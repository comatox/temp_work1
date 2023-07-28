<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

  let gridUtil;
  $(document).ready(function () {

    gridUtil = new GridUtil({
      // grid Data 통신 URL >> 필수
      url: "/api/sample/list"

      // 그리드 영역 ID >> optional (기본값 : grid)
      //,gridId : "grid"

      // 페이징 여부 >> optional (기본값 : false)
      , isPaging: true

      // 초기 데이터 통신 Params >> optinal  (기본값 : {}})
      //,initSearchParams : {}

      // ajax 동기/비동기 >> optinal  (기본값 : true)
      //,ajaxAsync : true

      // ajax method >> optinal  (기본값 : get)
      //,ajaxMethod : "get"

      // 페이지당 컨텐츠 갯수 >> optinal  (기본값 : 10)
      , pageSize: 10

      /** Excel Url >> 엑셀다운로드시 필수 */
      , excelUrl: "/api/sample/data/download"
      /** Excel File Name >> optinal  (기본값 : esecurity_excelData) */
      , excelFileName: "sample_엑셀이름"

      // jqgrid option
      , gridOptions: {
        colData: [
          {
            headerName: "순번"
            , field: "rnum"
          },
          {
            headerName: "제목"
            , field: "title"
            , width: "20%"
            /** cell 클래스 옵션 */
            //, classes : "student es6"

            /** cell스타일 옵션 */
            , cellattr: function () {
              return "style='background-color:#58D3F7;'"
            }

            /** merge 옵션 */
            // ,colMerge : {colspan:3, headerName : "merge"}

            /** cell Click 옵션 */
            ,onCellClicked : function({rowData, originRowData}) {
              console.log("onCellClicked")
              console.log("rowData", rowData);
              console.log("originRowData", originRowData);
            }
            // , onCellClicked: onCellSelectCallback
          },
          {
            headerName: "서브제목"
            , field: "subTitle"
          },
          {
            headerName: "사용여부"
            , field: "useYn"
            , align: "center"
            , width: "20%"
            , formatter: function (cellValue, options, rowObject) {
              return cellValue === "Y" ? "사용" : "미사용";
            }
          },
          {
            headerName: "버튼컨텐츠"
            , field: "content"
            , align: "center"
            , formatter: function (cellValue, options, rowObject) {
              if (cellValue === "Y") {
                //return "<button type='button' class='buttons' onClick='javascript:onButtonClick(\""+rowObject+"\")'>샘플</button>";
                //return "<button type='button' class='buttons' onClick='javascript:ButtonClick(\"content\", \""+options.rowId+"\")'>샘플버튼</button>";
                return "<span class='button btn_add' style='margin-left:5px;vertical-align: middle;'>입문</span>";
              } else if (cellValue === "N") {
                return "<span class='button2 btn_add' style='margin-left:5px;vertical-align: middle;'>출문</span>";
              } else {
                return cellValue;
              }
            }
            , event : {className : 'btn_add', onClicked : ({rowData}) => console.log(rowData)}
          },
          {
            headerName: "체크박스"
            , field: "imageYn"
            , align: "center"
            , formatter: function (cellValue, options, rowObject) {
              if (cellValue === "Y") {
                return "<input type='checkbox' class='sampleCheck' name='cbDelYn_" + options.rowId + "' />";
              } else {
                return "<input type='checkbox' class='sampleCheck' name='cbDelYn_" + options.rowId + "' checked disabled />";
              }
            }
          },
          {
            headerName: "사진"
            , field: "imagePath"
            , align: "center"
            , formatter: function (cellValue, options, rowObject) {
              return "<img src='" + cellValue + "' />";
            }
          },
          {
            headerName: "컨텐츠"
            , field: "content"
            , align: "center"
            , formatter: function (cellValue, options, row) {
              if (cellValue === "Y") {
                return "<input type='radio' name='rdDelYn_" + options.rowId + "'/>";
              } else if (cellValue === "N") {
                return "<input type='radio' name='rdDelYn_" + options.rowId + "' checked/>";
              }
            }
          }
        ]
        /** row Data 기준 checkBox(optional : false) */
        , isRowDataCheckBox: true

        /** row Click callback function (rowCLick보다 cellClick이 우선시됨)*/
        , onRowClicked: function ({rowData, originRowData}) {
          console.log("onRowClicked")
          console.log("rowData", rowData);
          console.log("originRowData", originRowData);
        }
      },
      search: {
        formId: "groupManage"
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

  // function searchData() {
  //
  //   // 조회 시작
  //   gridUtil.searchData();
  // }

  // cell Click시 Callback Function
  function onCellSelectCallback(rowData) {
    //rowData
    console.log("cellClick rowData", rowData);

    //후처리 예시 페이지이동
    var $form = document.createElement("form");
    $form.setAttribute("method", "post");
    $form.setAttribute("action", global.contextPath + "/sample/view");

    var $input = null;

    for (var prop in rowData) {
      $input = document.createElement("input");
      $input.setAttribute("type", "hidden");
      $input.setAttribute("name", prop);
      $input.setAttribute("value", rowData[prop]);
      $form.appendChild($input);
    }
    document.body.appendChild($form);

    $form.submit();
  }

  function onButtonClick({originRowData, rowId, field, e}) {
    console.log("onButtonClick originRowData", originRowData);
    console.log("onButtonClick rowId", rowId);
    console.log("onButtonClick field", field);
    console.log("onButtonClick e", e);
    console.log("onButtonClick e.target", e.target);


    if (confirm("버튼 클릭 하시겠습니까?")) {


      /** reload 없이 cell 내용만 바꾸어야 한다면 */
      gridUtil.setCellContent({
        rowId: rowId
        , fieldName: field
        , content: "<div>완료</div>"
      });

      /** grid reload가 필요하다면 */
      //gridUtil.searchData();
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

</script>

<div id="content_area">
    <div id="contentsArea">
        <img src="/esecurity/assets/common/images/common/subTitle/envrSetup/tit_324.png"/>
        <!-- realContents start -->
        <div id="realContents">
            <input type="hidden" name="callback" value="asd"/>
            <!-- 검색영역 시작 -->
            <div id="search_area">
                <!-- 검색 테이블 시작 -->
                <div class="search_content">
                    <form name="groupManage" id="groupManage">
                        <table cellpadding="0" cellspacing="0" caption="조회화면입니다" border="0"
                               class="view_board01">
                            <tr>
                                <th>제목</th>
                                <td>
                                    <input type="text" id="title" name="title" style="width:280px;IME-MODE:disabled;">
                                </td>
                                <th>서브제목</th>
                                <td>
                                    <input type="text" id="subTitle" name="subTitle" style="width:280px;">
                                </td>
                            </tr>
                            <tr>
                                <th>라디오</th>
                                <td>
                                    <input type="radio" class="border_none" name="paramRadios" value="1" checked>조건1</input>
                                    <input type="radio" class="border_none" name="paramRadios" value="2">조건2</input>
                                    <input type="radio" class="border_none" name="paramRadios" value="3">조건3</input>
                                </td>
                                <th>셀렉트박스</th>
                                <td>
                                    <select id="paramSelect" name="paramSelect" style="width:150px;">
                                        <option value="A">A</option>
                                        <option value="B">B</option>
                                        <option value="C">C</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <!-- 버튼 시작 -->
                    <div class="searchGroup">
                        <div class="centerGroup">
                            <span class="button bt_l1">
                                <button type="button" id="searchBtn" style="width:80px;"  <%--onclick="javascript:searchData();"--%> />
                                    검색
                                </button>
                            </span>
                            <span class="button bt_l1">
                                <button type="button" style="width:80px;" onclick="javascript:getChkRowDatas();">
                                    getCheck
                                </button>
                            </span>
                        </div>
                    </div>
                    <!-- 버튼 종료 -->
                </div>
                <!-- 검색 테이블 종료 -->
            </div>
            <!-- 검색영역 종료 -->

            <!-- 그리드 영역 시작 -->
            <div id="grid"></div>
            <!-- 그리드 영역 종료 -->
        </div>
    </div>
</div>
<div style="padding-bottom: 50px; height : 30px;"></div>