<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
  $(document).ready(function (e) {
    const scIoDocNo = "${param.scIoDocNo}";

    // 기본값 세팅
    defaultValueSet({scIoDocNo});

    // 목록 버튼 클릭시
    onClickGoListBtn();

    // 조치 실행 버튼 클릭시
    onCiickActDo();

    // 조치 내역 Select 세팅
    $.esutils.renderCode([{type: "select", grpCd: "C028", targetId: "actDo", blankOption: true}], function () {
      // 상세 화면 정보 그리기
      renderContents();
    });

  });

  /**************
   * [기본값 세팅]
   **************/
  function defaultValueSet({scIoDocNo}) {
    $("#scIoDocNo").val(scIoDocNo);
  }

  /***************
   * [컨텐츠 그리기]
   ***************/
  function renderContents() {
    $.esutils.renderData("securityForm",
        "/api/secrtactvy/securityAdminViolation/ioViolation/detail?" + new URLSearchParams({scIoDocNo: $("#scIoDocNo").val()}).toString(),
        (d) => {

          /*****************
           * [조건부 UI 제어]
           *****************/
          if (d.ofendDetailGbn === 'C0581009') {
            //스티커 미부착
            $("#trMobile").show();
            $("[view-data='dMobileForensicsGbnNm']").text(d.mobileForensicsGbnNm);
          } else {
            $("#trMobile").hide();
          }

          // 첨부파일
          $("[view-data='filePath']").text("첨부파일없음");
          if (d.filePath !== "N") {
            //$("[view-data=filePath]").html("<a href=javascript:fileDownload('" + encodeURIComponent(d.filePath) + "');>" + d.filePathNm + "</a>");
            $("[view-data=filePath]").html("<a id='fileDownload' href=javascript:void(0);>" + d.filePathNm + "</a>");

            //file Download Event
            fileDownload(d.filePath);
          }

          if ($("#actDo").val()) {
            $("#actDo").prop("disabled", true);
            $("#actDoBtn1").hide(); // 조치실행 버튼 숨김
            $("#actDoBtn2").hide(); // 조치실행 버튼 숨김
            $("#rmrk").prop("readonly", true);
          }

          /**********************************
           * [data prop 맞지 않아서 수동 세팅]
           **********************************/
          const dEmpNm = d.empNm ?? "";
          const dJwNm = d.jwNm ?? "";
          $("[view-data='empJwNm']").html(dEmpNm + " " + dJwNm);
          $("[view-data='dOfendEmpId']").html(d.ofendEmpId);
          $("[view-data='dOfendEmpNm']").html(d.ofendEmpNm);
          $("[view-data='dOfendDeptNm']").html(d.ofendCompNm);
          $("[view-data='dOfendTelNo']").html(d.ofendTelNo);

          $("[view-data='dOfendDetailGbnNm']").html(d.ofendDetailGbnNm);
          $("[view-data='dOfendSubGbnNm']").html(d.ofendSubGbnNm);

          $("[view-data='subContTitle']").html(d.ofendGbnNm);

          $("[view-data='etcRsn']").html(d.etcRsn?.replaceAll('\n', '<br />'));

          $("#ofendCompNm").val(d.ofendDeptNm);
          $("#ofendText").val(d.ofendDetailGbnNm);

          // 접견자 리스트 조회
          setInterviewerList();

          // 조치현황 리스트 조회
          setActSumList();

          // 조치내역 필터처리리
          setActFilterCode(d.actFiltList);

          // 동일위규이력조회
          setOfendHistList();

        }, {loading: true, exclude: ["scIoDocNo"]}
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
      scIoDocNo: $("#scIoDocNo").val()
      , ofendEmpId: $("#ofendEmpId").val()
      , ofendDetailGbn: $("#ofendDetailGbn").val()
    };

    const grid = new GridUtil({
      gridId: "ofendHistList"
      , url: '/api/secrtactvy/securityAdminViolation/ioViolation/history/same'
      , emptyText: "동일 위규 이력정보가 없습니다."
      , search: {
        beforeSend: function () {
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
              return ofendDt + "<br/>" + ofendTm;
            }
          },
          {
            headerName: "위규자"
            , field: "ofendEmpNm"
          },
          {
            headerName: "위규내용"
            , field: "ofendDetailGbnNm"
            , align: "left"
          },
          {
            headerName: "등록자"
            , field: "crtByNm"
          },
          {
            headerName: "위규조치"
            , field: "actDoNm"
            , align: "left"
          },
          {
            headerName: "조치자"
            , field: "actByNm"
          },
          {
            headerName: "조치 일시"
            , field: "actDt"
            , formatter: (cellValue, _, row) => {
              const actDt = row.actDt ?? "";
              const actTm = row.actTm ?? "";
              return actDt + "<br/>" + actTm;
            }
          },
          {
            headerName: "분기"
            , field: "qt"
          },
          {
            headerName: "비고"
            , field: "bigo"
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
                // 당해년도 Row 색상 변경
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
   * [접견자 리스트 조회]
   ********************/
  function setInterviewerList() {

    // 입주사 접견자
    const interviewerGrid = new GridUtil({
      url: "/api/secrtactvy/securityAdminViolation/ioViolation/detail/interviewer"
      , gridId: "interviewerGrid"
      , gridOptions: {
        width: "620"
        , emptyText: "입주사 접견자 정보가 없습니다."
        , colData: [
          {
            headerName: "소속조직"
            , field: "deptNm"
          },
          {
            headerName: "이름"
            , field: "empNm"
          },
          {
            headerName: "직위"
            , field: "jwNm"
          },
        ]
      }
      , search: {
        beforeSend: () => {
          return {scIoDocNo: $("#scIoDocNo").val()};
        }
      }
    });

    // grid init
    interviewerGrid.init();
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
      , url: '/api/secrtactvy/securityAdminViolation/ioViolation/history/act'
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
            headerName: "외부인 확인"
            , field: "cnt5"
          },
          {
            headerName: "시정계획서 징구"
            , field: "cnt1"
          },
          {
            headerName: "대표이사 시정공문 징구"
            , field: "cnt2"
          },
          {
            headerName: "1개월 출입정지"
            , field: "cnt3"
          },
          {
            headerName: "영구 출입정지"
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
      // 목록으로 이동
      $.esutils.href("/secrtactvy/securityadminviolation/ioviolation/list", {});
    })

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

      $.esutils.postApi("/api/secrtactvy/securityAdminViolation/ioViolation/actDo", params, function (res) {
        if (res.data) {
          alert("조치실행 되었습니다.");

          //리스트로 돌아가기.
          $("#buttonGoList1").trigger("click");
        } else {
          alert("조치실행중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      }, {loading: true});

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


</script>

<div id="contentsArea">
    <table width="100%" border="0">
        <tr>
            <td align="left">
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_331.png"/>
            </td>
            <td align="right">
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span id="buttonGoList1" class="button bt_s2"><button type="button" style="width:100px;">목록</button></span>
                        <span id="actDoBtn1" class="button bt_s1"><button type="button" style="width:100px;">조치실행</button></span>
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
            <input type="hidden" id="scIoDocNo" name="scIoDocNo" value=""/>
            <input type="hidden" id="scIoCorrPlanNo" name="scIoCorrPlanNo"/>
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
            <input type="hidden" id="ofendEmpNm2" name="ofendEmpNm2" value=""/>
            <input type="hidden" id="actCompId" name="actCompId" value=""/>
            <input type="hidden" id="ofendCompNm" name="ofendCompNm" value=""/>
            <input type="hidden" id="ofendText" name="ofendText" value=""/>

            <input type="hidden" id="ofendDetailGbn" name="ofendDetailGbn" value=""/>


            <h1 class="txt_title01">등록자</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <tbody>
                <tr>
                    <th style="width:117px;">위규 구분</th>
                    <td style="width:213px;" view-data="ofendGbnNm"></td>
                    <th style="width:117px;">성명/직위</th>
                    <td style="width:213px;" view-data="empJwNm"></td>
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
            <h1 id="subContTitle" class="txt_title01"></h1>
            <table id="securityTable" cellpadding="0" cellspacing="0" border="0" class="view_board">
                <tbody>
                <tr>
                    <th style='width:117px;'>성명</th>
                    <td style='width:213px;' view-data="dOfendEmpNm"></td>
                    <th style='width:117px;'>연락처</th>
                    <td style='width:213px;' view-data="dOfendTelNo"></td>
                </tr>
                <tr>
                    <th>아이디</th>
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
                    <th>모바일 포렌직&nbsp;</span></th>
                    <td colspan=3 view-data="dMobileForensicsGbnNm"></td>
                </tr>
                <tr>
                    <th>적발 사업장</th>
                    <td view-data="actCompNm"></td>
                    <th>적발 건물</th>
                    <td view-data
                    "actBldgNm"></td>
                </tr>

                <tr>
                    <th>적발 건물 상세</th>
                    <td view-data="actLocateNm"></td>
                    <th>적발 GATE&nbsp;</span></th>
                    <td view-data="actGate"></td>
                </tr>

                <tr>
                    <th>보안요원 성명</th>
                    <td colspan=3 view-data="secManNm"></td>
                </tr>

                <tr>
                    <th>보안요원 검토의견</th>
                    <td view-data="etcRsn"></td>
                    <th>방문증 통합사번</th>
                    <td view-data="visitEmpno"></td>
                </tr>

                <tr>
                    <th>입주사 접견자</th>
                    <td colspan=3>
                        <div id="interviewerGrid"></div>
                </td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan=3 view-data="filePath">&nbsp;</td>
                </tr>
                </tbody>
                </table>

                <h1 class="txt_title01">위규조치</h1>
                <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                    <tbody>
                    <tr>
                        <th style='width:117px;'>조치안내</th>
                        <td style='width:543px;' view-data="ofendRmrk"></td>
                    </tr>
                    <tr>
                        <th>조치내역</th>
                        <td><select id="actDo" name="actDo" style="width: 242px;"/></td>
                    </tr>
                    <tr>
                        <th>비고</th>
                        <td><textarea id='rmrk' name='rmrk' style="width: 95%; height: 70px; overflow: auto;"></textarea></td>
                    </tr>
                    </tbody>
                </table>

    <h1 class="txt_title01">조치현황</h1>
    <div style="width:100%;height:AUTO;" margin-top:10px;
    ">
    <div id="actSubGrid">
        <%--                <table id="actSumList">--%>
        <%--                    <colgroup>--%>
        <%--                        <col width="120px;" />--%>
        <%--                        <col width="60px;" />--%>
        <%--                        <col width="90px;" />--%>
        <%--                        <col width="100px;" />--%>
        <%--                        <col width="100px;" />--%>
        <%--                        <col width="100px;" />--%>
        <%--                        <col width="100px;" />--%>
        <%--                    </colgroup>--%>
        <%--                    <thead>--%>
        <%--                    <tr>--%>
        <%--                        <th scope="col">년/분기</th>--%>
        <%--                        <th scope="col">구분</th>--%>
        <%--                        <th scope="col">외부인 확인</th>--%>
        <%--                        <th scope="col">시정계획서 징구</th>--%>
        <%--                        <th scope="col">대표이사 시정공문 징구</th>--%>
        <%--                        <th scope="col">1개월 출입정지</th>--%>
        <%--                        <th scope="col">영구 출입정지</th>--%>
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
    <%--    <table id="ofendHistList">--%>
    <%--        <colgroup>--%>
    <%--            <col width="80px;"/>--%>
    <%--            <col width="80px;"/>--%>
    <%--            <col width="200px;"/>--%>
    <%--            <col width="75px;"/>--%>
    <%--            <col width="120px;"/>--%>
    <%--            <col width="75px;"/>--%>
    <%--            <col width="75px;"/>--%>
    <%--            <col width="40px;"/>--%>
    <%--            <col width="80px;"/>--%>
    <%--        </colgroup>--%>
    <%--        <thead>--%>
    <%--        <tr>--%>
    <%--            <th scope="col">지적 일시</th>--%>
    <%--            <th scope="col">위규자</th>--%>
    <%--            <th scope="col">위규 내용</th>--%>
    <%--            <th scope="col">등록자</th>--%>
    <%--            <th scope="col">위규 조치</th>--%>
    <%--            <th scope="col">조치자</th>--%>
    <%--            <th scope="col">조치 일시</th>--%>
    <%--            <th scope="col">분기</th>--%>
    <%--            <th scope="col">비고</th>--%>
    <%--        </tr>--%>
    <%--        </thead>--%>
    <%--        <tbody>--%>
    <%--        </tbody>--%>
    <%--    </table>--%>
</div>
</div>

<!-- 버튼 -->
<div class="buttonGroup">
    <div class="leftGroup">
        <span id="buttonGoList2" class="button bt_s2"><button type="button" style="width:100px;">목록</button></span>
        <span id="actDoBtn2" class="button bt_s1"><button type="button" style="width:100px;">조치실행</button></span>
    </div>
</div>
<!-- 버튼 끝 -->

</form>
</div>
</div>