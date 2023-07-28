<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
  $(document).ready(function (e) {
    const scDocNo = "${param.scDocNo}";
    const deadLIneYn = "${param.deadLIneYn}";

    // 기본값 세팅
    defaultValueSet({scDocNo, deadLIneYn});

    // 목록 버튼 클릭시
    onClickGoListBtn();

    // 위규조치 > 사원 찾기 팝업
    onSearchEmpPopup();

    // 조치 실행 버튼 클릭시
    onCiickActDo();

    // 2차 안내메일 전송
    onClickSendSecondaryMail();

    // 조치 내역 Select 세팅
    $.esutils.renderCode([{type: "select", grpCd: "C028", targetId: "actDo", blankOption: true}], function () {
      // 상세 화면 정보 그리기
      renderContents();
    });

  });

  /**************
   * [기본값 세팅]
   **************/
  function defaultValueSet({scDocNo, deadLIneYn}) {
    $("#scDocNo").val(scDocNo);
    $("#deadLIneYn").val(deadLIneYn);
  }

  /***************
   * [컨텐츠 그리기]
   ***************/
  function renderContents() {
    $.esutils.renderData("securityForm",
        "/api/secrtactvy/securityAdminViolation/coViolation/detail?" + new URLSearchParams({scDocNo: $("#scDocNo").val()}).toString(),
        (d) => {

          $("#actDo2chaMail1").hide(); // 2차 안내 메일 버튼 숨김
          $("#actDo2chaMail2").hide(); // 2차 안내 메일 버튼 숨김

          /*****************
           * [조건부 UI 제어]
           *****************/
          d.ofendDetailGbn === "C0531010" && $("#trMobile").show();

          if ($("#actDo").val()) {
            $("#actDo").prop("disabled", true);
            $("#actDoBtn1").hide(); // 조치실행 버튼 숨김
            $("#actDoBtn2").hide(); // 조치실행 버튼 숨김
            $("#actDo2chaMail1").hide(); // 2차 안내 메일 버튼 숨김
            $("#actDo2chaMail2").hide(); // 2차 안내 메일 버튼 숨김
            $("#rmrk").prop("readonly", true);
          }

          if ($("#actDo").val() === "C0280003" || $("#actDo").val() === "C0280004") {
            $("#empNmReadTd").show();
          } else {
            $("#empNmReadTd").hide();
          }

          if ($("#deadLIneYn").val() === "Y") {
            $("#actDo2chaMail1").show(); // 2차 안내 메일 버튼 노출
            $("#actDo2chaMail2").show(); // 2차 안내 메일 버튼 노출
            $("#buttonGoList1").hide(); // 목록 버튼 숨기기
            $("#buttonGoList2").hide(); // 목록 버튼 숨기기
          }

          // 첨부파일
          $("[view-data='filePath']").text("첨부파일없음");
          if (d.filePath !== "N") {
            //$("[view-data=filePath]").html("<a href=javascript:fileDownload('" + encodeURIComponent(d.filePath) + "');>" + d.filePathNm + "</a>");
            $("[view-data=filePath]").html("<a id='fileDownload' href=javascript:void(0);>" + d.filePathNm + "</a>");

            //file Download Event
            fileDownload(d.filePath);
          }

          /**********************************
           * [data prop 맞지 않아서 수동 세팅]
           **********************************/
          const dEmpNm = d.empNm ?? "";
          const dJwNm = d.jwNm ?? "";
          $("[view-data='empJwNm']").html(dEmpNm + " " + dJwNm);
          $("[view-data='dOfendEmpId']").html(d.ofendEmpId);
          $("[view-data='dOfendEmpNm']").html(d.ofendEmpNm);
          $("[view-data='dOfendDeptNm']").html(d.ofendDeptNm);
          $("[view-data='dOfendTelNo']").html(d.ofendTelNo);

          $("[view-data='dOfendDetailGbnNm']").html(d.ofendDetailGbnNm);
          $("[view-data='dOfendSubGbnNm']").html(d.ofendSubGbnNm);

          $("[view-data='subContTitle']").html(d.ofendGbnNm);

          $("[view-data='etcRsn']").html(d.etcRsn?.replaceAll('\n', '<br />'));

          $("[view-data='kaEmpNm1']").html(d.kaEmpNm);
          $("[view-data='gaEmpNm1']").html(d.gaEmpNm);
          $("[view-data='aaEmpNm1']").html(d.aaEmpNm);

          $("#ofendCompNm").val(d.ofendDeptNm);
          $("#ofendText").val(d.ofendDetailGbnNm);

          $("#empNmKaTd").hide();
          $("#empNmAaTd").hide();

          // 조치내역 필터처리리
          setActFilterCode(d.actFiltList);

          // 동일위규이력조회
          setOfendHistList();

          // 조치현황
          setActSumList();

          // 조치내역 SelectBox 변경시
          onChangeActDo();

        }, {loading: true, exclude: ["scDocNo", "deadLIneYn"]}
    )
  }

  /*********************
   * [조치내역 필터처리]
   *********************/
  function setActFilterCode(code) {
    if (!code) {
      $("select[name='actDo'] option").each(function () {
        if (code.indexOf($(this).val()) < 0) {
          $(this).remove();
        }
      });
    }
  }

  /****************
   * [파일다운로드]
   ***************/
  function fileDownload(filePath) {
    $("#fileDownload").on("click", function() {
      $.esutils.fileDownload(filePath.split(';')[0], filePath.split(';')[1]);
    });
  }

  /*********************
   * [동일위규 이력조회]
   *********************/
  function setOfendHistList() {
    const params = {
      scDocNo: $("#scDocNo").val()
      , ofendEmpId: $("#ofendEmpId").val()
      , ofendDetailGbn: $("#ofendDetailGbn").val()
    };

    const grid = new GridUtil({
      gridId: "ofendHistList"
      , url: '/api/secrtactvy/securityAdminViolation/coViolation/history/same'
      , emptyText: "동일 위규 이력정보가 없습니다."
      , search: {
        beforeSend: function (_) {
          return params;
        }
      }
      , gridOptions: {
        colData: [
          {
            headerName: "지적일시"
            , field: "ofendDt"
            , formatter: (cellValue, _, row) => {
              const ofendDt = cellValue ?? "";
              const ofendTm = row.ofendTm ?? "";
              const ofendDtm = row.ofendDtm ?? "";

              return ofendDtm.substr(0, 4) === new Date().getFullYear().toString() ?
                  ofendDt + "<br/>" + ofendTm
                  : ofendDtm;
            }
          },
          {
            headerName: "위규자"
            , field: "ofendEmpNm"
          },
          {
            headerName: "위규내용"
            , field: "ofendDetailGbnNm"
          },
          {
            headerName: "등록자"
            , field: "crtByNm"
          },
          {
            headerName: "위규조치"
            , field: "actDoNm"
          },
          {
            headerName: "조치자"
            , field: "actByNm"
          },
          {
            headerName: "조치 일시"
            , field: "actDt"
            , formatter: (cellValue, _, row) => {
              const actDt = cellValue ?? "";
              const actTm = row.actTm ?? "";
              return actDt + "<br/>" + actTm;
            }
          },
          {
            headerName: ""
            , field: "ofendDtm"
            , hidden: true
          }
        ]
        , loadComplete: function () {
          const $grid = $(this);
          const ids = $grid.getDataIDs();
          $.each(
              ids, function (idx, rowId) {
                let r = $grid.getRowData(rowId);
                // 당해년도 Row 색상변경
                if (r.ofendDtm.substr(0, 4) === new Date().getFullYear().toString()) {
                  $grid.setRowData(rowId, false, {background: "#F8F8F8"});
                }
              }
          );

        }
      }
    });

    //동일위규 grid Init
    grid.init();
  }

  /*********************
   * [조치현황 리스트 조회]
   *********************/
  function setActSumList() {

    const params = {
      ofendDt: $("[view-data=ofendDt]").text()
      , ofendEmpId: $("#ofendEmpId").val()
      , ofendDetailGbn: $("#ofendDetailGbn").val()
    };

    const grid = new GridUtil({
      gridId: "actSubGrid"
      , url: '/api/secrtactvy/securityAdminViolation/coViolation/history/act'
      , emptyText: "조치현황 정보가 없습니다."
      , search: {
        beforeSend: function (_) {
          return params;
        }
      }
      , gridOptions: {
        colData: [
          {
            headerName: "년/분기"
            , field: "yqGb"
            , cellattr: (rowId) => parseInt(rowId) % 2 !== 0 ? "rowspan=2" : "style='display:none;'"
          },
          {
            headerName: "구분"
            , field: "gb"
          },
          {
            headerName: "구성원 확인"
            , field: "cnt1"
          },
          {
            headerName: "시정계획서"
            , field: "cnt2"
          },
          {
            headerName: "경고장"
            , field: "cnt3"
          },
          {
            headerName: "인사징계 의뢰"
            , field: "cnt4"
          },
        ]
      }
    });

    // grid init
    grid.init();

  }

  /********************
   * [목록 버튼 클릭시]
   ********************/
  function onClickGoListBtn() {

    $("#buttonGoList1, #buttonGoList2").on("click", function () {
      const url = $("#deadLIneYn") === "Y" ?
          "/secrtactvy/securityadminviolation/coviolationdeadline/list"
          : "/secrtactvy/securityadminviolation/coviolation/list";

      // 목록으로 이동 (deadLIneYn==='Y'일시 목록버턴 hide이지만 구현.)
      $.esutils.href(url, {});
    })

  }

  /********************************
   * [조치내역 Select Value 변경시]
   ********************************/
  function onChangeActDo() {
    $("#actDo").on("change", function (e) {
      const value = $(this).val();

      $("#kaEmpId").val("");
      $("#aaEmpId").val("");
      $("#gaEmpId").val("");
      $("#kaDeptNm").val("");
      $("#aaDeptNm").val("");
      $("#gaDeptNm").val("");
      $("#kaEmpNm").val("");
      $("#aaEmpNm").val("");
      $("#gaEmpNm").val("");

      if (value === 'C0280003') {
        // 경고장 발송
        $("#empNmKaTd").show();
        $("#empNmAaTd").hide();
      } else if (value === 'C0280004') {
        // 인사징계 의뢰
        $("#empNmKaTd").show();
        $("#empNmAaTd").show();
      } else {
        $("#empNmKaTd").hide();
        $("#empNmAaTd").hide();
      }
    });
  }

  /******************
   * [사원 찾기 팝업]
   ******************/
  function onSearchEmpPopup() {
    // keyPress Enter
    $("#kaEmpNm, #aaEmpNm, #gaEmpNm").on("keypress", function (e) {
      if (e.keyCode === 13) {
        const type = $(this).attr("id").substr(0, 2);
        getEmpInfo(type);
      }
    })

    // 사원 검색 버튼 클릭
    $("#kaEmpSearchBtn, #aaEmpSearchBtn, #gaEmpSearchBtn").on("click", function () {
      const type = $(this).attr("id").substr(0, 2);
      getEmpInfo(type);
    });
  }

  function getEmpInfo(type) {

    const searchEmpNm = $("#" + type + "EmpNm").val();
    const compId = $("#ofendCompId").val();

    if (searchEmpNm.length < 2) {
      alert("검색하실 방문객명을 두 글자 이상 입력해주십시오.");
      return;
    }
    const param = {
      empNm: searchEmpNm,
      compId,
    };

    console.log(type);
    $.esutils.openEmpPopup({
      ...param,
      fnCallback: function (result) {
        if (result?.originRowData) {
          const d = result.originRowData;
          $("#" + type + "EmpId").val(d.empId);
          $("#" + type + "EmpNm").val(d.empNm);
          $("#" + type + "DeptNm").val(d.deptNm);
        }
      }
    });
  }

  /***********************
   * [조치 실행 버튼 클릭시]
   ***********************/
  function onCiickActDo() {
    $("#actDoBtn1, #actDoBtn2").on("click", function () {

      if (!confirm("조치실행을 처리하시겠습니까?")) {
        return;
      }

      // valition
      if (!actDoValidation()) {
        return;
      }

      const params = {
        ...($.esutils.getFieldsValue($("#securityForm")))
        , acIp: global.acIp
        , crtBy: global.empId
      }

      $.esutils.postApi("/api/secrtactvy/securityAdminViolation/coViolation/actDo", params, function (res) {
        if (res) {
          alert("조치실행 되었습니다.");

          //리스트로 돌아가기.
          $("#buttonGoList1").trigger("click");
        } else {
          alert("조치실행중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      });

    });
  }

  /*************************
   * [조치실행전 조치내역 검증]
   *************************/
  function actDoValidation() {

    //조치내역 선택
    if (!$("#actDo").val()) {
      alert("조치내역을 선택해주십시오.");
      $("#actDo").focus();
      return false;
    }
    return true;
  }

  /*****************************
   * [2차 안내메일 전송버튼 클릭시]
   ******************************/
  function onClickSendSecondaryMail() {

    $("#actDo2chaMail1, #actDo2chaMail2").on("click", function () {

      if (!confirm("2차 안내메일 발송 처리를 하시겠습니까?")) {
        return;
      }

      const params = {
        ...($.esutils.getFieldsValue($("#securityForm")))
        , acIp: global.acIp
        , crtBy: global.empId
      }

      $.esutils.postApi("/api/secrtactvy/securityAdminViolation/coViolation/secondaryMail", params, function (res) {
        if (res) {
          alert("처리되었습니다.");

          //리스트로 돌아가기.
          $("#buttonGoList1").trigger("click");
        } else {
          alert("처리중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      }, {loading: true});

    });
  }


</script>

<div id="contentsArea">
    <table width="100%" border="0">
        <tr>
            <td align="left">
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_312.png"/>
            </td>
            <td align="right">
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span id="buttonGoList1" class="button bt_s2"><button type="button" style="width:100px;">목록</button></span>
                        <span id="actDoBtn1" class="button bt_s1"><button type="button" style="width:100px;">조치실행</button></span>
                        <span id="actDo2chaMail1" class="button bt_s1"><button type="button" style="width:120px;">2차 안내메일 전송</button></span>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <img src="/eSecurity/common/images/common/line.png" width="100%" height="3"/>
            </td>
        </tr>
    </table>

    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm" method="post">
            <input type="hidden" id="scDocNo" name="scDocNo"/>
            <input type="hidden" id="scCorrPlanNo" name="scCorrPlanNo"/>
            <input type="hidden" id="corrPlanSendYn" name="corrPlanSendYn"/>

            <!--  시정계획서 입력시 사용 -->
            <input type="hidden" id="ofendCompId" name="ofendCompId" value=""/>
            <input type="hidden" id="ofendEmpId" name="ofendEmpId" value=""/>
            <input type="hidden" id="ofendDeptId" name="ofendDeptId" value=""/>
            <input type="hidden" id="ofendJwId" name="ofendJwId" value=""/>
            <input type="hidden" id="pointContent" name="pointContent" value=""/>
            <input type="hidden" id="scContent" name="scContent" value=""/>
            <input type="hidden" id="etcContent" name="etcContent" value=""/>
            <input type="hidden" id="docId" name="docId" value=""/>

            <!--  경고장 메일발송시 사용 -->
            <input type="hidden" id="ofendEmpEmail" name="ofendEmpEmail" value=""/>
            <input type="hidden" id="ofendEmpNm" name="ofendEmpNm" value=""/>
            <input type="hidden" id="actCompId" name="actCompId" value=""/>
            <input type="hidden" id="ofendCompNm" name="ofendCompNm" value=""/>
            <input type="hidden" id="ofendText" name="ofendText" value=""/>

            <input type="hidden" id="ofendDetailGbn" name="ofendDetailGbn" value=""/>

            <input type="hidden" id="deadLIneYn" name="deadLIneYn" value=""/>

            <h1 class="txt_title01">등록자</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="151"/>
                    <col width="274"/>
                    <col width="151"/>
                    <col width="274"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>위규 구분</th>
                    <td view-data="ofendGbnNm"></td>
                    <th>성명/직위</th>
                    <td view-data="empJwNm"></td>
                </tr>
                <tr>
                    <th>회사명</th>
                    <td view-data="compNm"></td>
                    <th>부서</th>
                    <td view-data="deptNm"></td>
                </tr>
                <tr>
                    <th>적발일</th>
                    <td view-data="ofendDt">
                    </td>
                    <th>적발시각</th>
                    <td view-data="ofendTm">
                    </td>
                </tr>
                </tbody>
            </table>
            <h1 view-data="subContTitle" class="txt_title01"></h1>
            <table id="securityTable" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="151"/>
                    <col width="274"/>
                    <col width="151"/>
                    <col width="274"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>성명</th>
                    <td view-data="dOfendEmpNm"></td>
                    <th>연락처</th>
                    <td view-data="dOfendTelNo"></td>
                </tr>
                <tr>
                    <th>사번</th>
                    <td view-data="dOfendEmpId" style='width:213px;'></td>
                    <th>소속</th>
                    <td view-data="dOfendDeptNm"></td>
                </tr>
                <tr>
                    <th>위규 내용</th>
                    <td colspan="3" view-data="dOfendDetailGbnNm"></td>
                </tr>
                <tr>
                    <th>위규 내용 상세</th>
                    <td colspan="3" view-data="dOfendSubGbnNm"></td>
                </tr>
                <tr id='trMobile' style='display :none;'>
                    <th>모바일 포렌직</th>
                    <td colspan=3 id="dMobileForensicsGbnNm"></td>
                </tr>
                <tr>
                    <th>적발 사업장</th>
                    <td view-data="actCompNm"></td>
                    <th>적발건물</th>
                    <td view-data="actBldgNm"></td>
                </tr>

                <tr>
                    <th>적발 건물 상세</th>
                    <td view-data="actLocateNm"></td>
                    <th>적발Gate</th>
                    <td view-data="actGate"></td>
                </tr>

                <tr>
                    <th>보안요원 성명</th>
                    <td colspan=3 view-data="secManNm"></td>
                </tr>

                <tr>
                    <th>보안요원 검토의견&nbsp</th>
                    <td colspan=3 view-data="etcRsn"></td>
                </tr>

                <tr>
                    <th>첨부파일&nbsp</th>
                    <td colspan=3 view-data="filePath">&nbsp;</td>
                </tr>
                </tbody>
            </table>

            <h1 class="txt_title01">위규조치</h1>
            <table id="securityTable" cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="151"/>
                    <col width="130"/>
                    <col width="144"/>
                    <col width="151"/>
                    <col width="130"/>
                    <col width="144"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>조치안내</th>
                    <td colspan="5" view-data="ofendRmrk"></td>
                </tr>
                <tr>
                    <th>조치내역</th>
                    <td colspan="5"><select id="actDo" name="actDo" style="width: 242px;"/></td>
                </tr>
                <tr>
                    <th>비고</th>
                    <td colspan="5"><textarea id='rmrk' name='rmrk' style="width: 95%; height: 70px; overflow: auto;"></textarea></td>
                </tr>
                <tr id="empNmKaTd">
                    <th>팀장</th>
                    <input type='hidden' id='kaEmpId' name='kaEmpId'/>
                    <td>
                        <input type='text' id='kaEmpNm' name='kaEmpNm' class='center' style='width:53px;' maxLength='15' maxByte='15'/>
                        <a href='javascript: void(0);' class='btn_type01' style='margin-left: 5px'>
                            <span id='kaEmpSearchBtn' name='kaEmpSearchBtn'>검색</span>
                        </a>
                    </td>
                    <td>
                        <input type='text' id='kaDeptNm' name='kaDeptNm' class='center readonly' readonly style='width:100px;'/>
                    </td>
                    <th>사업부장</th>
                    <input type='hidden' id='gaEmpId' name='gaEmpId'/>
                    <td>
                        <input type='text' id='gaEmpNm' name='gaEmpNm' class='center' style='width:53px;' maxLength='15' maxByte='15'/>
                        <a href='javascript: void(0);' class='btn_type01' style='margin-left: 5px'>
                            <span id='gaEmpSearchBtn' name='gaEmpSearchBtn'>검색</span>
                        </a>
                    </td>
                    <td>
                        <input type='text' id='gaDeptNm' name='gaDeptNm' class='center readonly' readonly style='width:100px;'/>
                    </td>
                </tr>
                <tr id="empNmAaTd">
                    <th>대표이사</th>
                    <input type='hidden' id='aaEmpId' name='aaEmpId'/>
                    <td><input type='text' id='aaEmpNm' name='aaEmpNm' class='center' style='width:53px;' maxLength='15' maxByte='15'/>
                        <a href='javascript: void(0);' class='btn_type01' style='margin-left: 5px'>
                            <span id='aaEmpSearchBtn' name='aaEmpSearchBtn'>검색</span>
                        </a>
                    </td>
                    <td colspan="4"><input type='text' id='aaDeptNm' name='aaDeptNm' class='center readonly' readonly style='width:100px;'/></td>
                </tr>

                <tr id="empNmReadTd">
                    <th>팀장</th>
                    <td view-data="kaEmpNm1"></td>
                    <th>사업부장</th>
                    <td view-data="gaEmpNm1"></td>
                    <th>대표이사</th>
                    <td view-data="aaEmpNm1"></td>
                </tr>

                </tbody>
            </table>


            <h1 class="txt_title01">조치현황</h1>
            <div style="width:100%;height:AUTO;" margin-top:10px;
            ">
            <div id="actSubGrid">
                <%--                <table id="actSumList">--%>
                <%--                    <colgroup>--%>
                <%--                        <col width="180px;"/>--%>
                <%--                        <col width="90px;"/>--%>
                <%--                        <col width="100px;"/>--%>
                <%--                        <col width="100px;"/>--%>
                <%--                        <col width="100px;"/>--%>
                <%--                        <col width="100px;"/>--%>
                <%--                    </colgroup>--%>
                <%--                    <thead>--%>
                <%--                    <tr>--%>
                <%--                        <th scope="col">년/분기</th>--%>
                <%--                        <th scope="col">구분</th>--%>
                <%--                        <th scope="col">구성원 확인</th>--%>
                <%--                        <th scope="col">시정계획서</th>--%>
                <%--                        <th scope="col">경고장</th>--%>
                <%--                        <th scope="col">인사징계 의뢰</th>--%>
                <%--                    </tr>--%>
                <%--                    </thead>--%>
                <%--                    <tbody>--%>
                <%--                    </tbody>--%>
                <%--                </table>--%>
            </div>
    </div>


    <h1 class="txt_title01">보안 위규 이력</h1>
    <div style="width:100%;height:AUTO;" margin-top:10px;">
    <div id="ofendHistList">
        <%--        <table id="ofendHistList">--%>
        <%--            <colgroup>--%>
        <%--                <col width="80px;"/>--%>
        <%--                <col width="80px;"/>--%>
        <%--                <col width="200px;"/>--%>
        <%--                <col width="80px;"/>--%>
        <%--                <col width="120px;"/>--%>
        <%--                <col width="80px;"/>--%>
        <%--                <col width="70px;"/>--%>
        <%--            </colgroup>--%>
        <%--            <thead>--%>
        <%--            <tr>--%>
        <%--                <th scope="col">지적 일시</th>--%>
        <%--                <th scope="col">위규자</th>--%>
        <%--                <th scope="col">위규 내용</th>--%>
        <%--                <th scope="col">등록자</th>--%>
        <%--                <th scope="col">위규 조치</th>--%>
        <%--                <th scope="col">조치자</th>--%>
        <%--                <th scope="col">조치 일시</th>--%>
        <%--            </tr>--%>
        <%--            </thead>--%>
        <%--            <tbody>--%>
        <%--            </tbody>--%>
        <%--        </table>--%>
    </div>
</div>

<!-- 버튼 -->
<div class="buttonGroup">
    <div class="leftGroup">
        <span id="buttonGoList2" class="button bt_s2"><button type="button" style="width:100px;">목록</button></span>
        <span id="actDoBtn2" class="button bt_s1"><button type="button" style="width:100px;">조치실행</button></span>
        <span id="actDo2chaMail2" class="button bt_s1"><button type="button" style="width:120px;">2차 안내메일 전송</button></span>
    </div>
</div>
<!-- 버튼 끝 -->

</form>
</div>
</div>