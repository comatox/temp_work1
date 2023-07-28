<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
  $(document).ready(function (e) {
    const scIoDocNo = "${param.scIoDocNo}";

    // 기본값 세팅
    defaultValueSet({scIoDocNo});

    // 목록 버튼 클릭시
    onClickGoListBtn();

    // 접견자 리스트 조회
    setInterviewerList();

    // 삭제 버튼 클릭시
    onClickDeleteBtn();

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
          $("[view-data='empJwNm']").html(d.empNm ?? "" + " " + d.jwNm ?? "");

          $("[view-data='dOfendEmpId']").html(d.ofendEmpId);
          $("[view-data='dOfendEmpNm']").html(d.ofendEmpNm);
          $("[view-data='dOfendDeptNm']").html(d.ofendCompNm);
          $("[view-data='dOfendTelNo']").html(d.ofendTelNo);

          if(d.ofendDetailGbn === 'C0581009' ) {
            //스티커 미부착
            $("#trMobile").show();
            $("[view-data='dMobileForensicsGbnNm']").html(d.mobileForensicsGbnNm);
          }
          else {
            $("#trMobile").hide();
          }

          $("[view-data='dOfendDetailGbnNm']").html(d.ofendDetailGbnNm);
          $("[view-data='dOfendSubGbnNm']").html(d.ofendSubGbnNm);


          $("[view-data='subContTitle']").html(d.ofendGbnNm);

          $("[view-data='etcRsn']").html(d.etcRsn?.replaceAll('\n', '<br />'));

          // 첨부파일
          $("[view-data='filePath']").html("첨부파일없음");
          if(d.filePath !== "N" ){
            //$("[view-data=filePath]").html("<a href=javascript:fileDownload('" + encodeURIComponent(d.filePath) + "');>" + d.filePathNm + "</a>");
            $("[view-data=filePath]").html("<a id='fileDownload' href=javascript:void(0);>" + d.filePathNm + "</a>");

            //file Download Event
            fileDownload(d.filePath);
          }

          $("#ofendText").val(d.ofendDetailGbnNm);

          $("#actDo").prop("disabled", true);
          $("#rmrk").prop("readonly", true);

          $("#regEmpId").val(d.empId);

          if((!$("#actDo").val()) && ($("#regEmpId").val() === global.empId)) {
            $("#actDoBtn1").show(); // 삭제 버튼 표시
            $("#actDoBtn2").show(); // 삭제 버튼 표시
            $("#delTrId").show();
          }
          else {
            $("#actDoBtn1").hide(); // 삭제 버튼 숨김
            $("#actDoBtn2").hide(); // 삭제 버튼 숨김
            $("#delTrId").hide();
          }

        }, {loading: true, exclude: ["scIoDocNo"]}
    )
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


  /********************
   * [목록 버튼 클릭시]
   ********************/
  function onClickGoListBtn() {

    $("#buttonGoList1, #buttonGoList2").on("click", function () {
      // 목록으로 이동
      $.esutils.href("/secrtactvy/securityadminviolation/ioviolationsec/list", {});
    })
  }

  /*******************
   * [삭제 버튼 클릭시]
   *******************/
  function onClickDeleteBtn() {
    $("#actDoBtn1, #actDoBtn2").on("click", function() {
      const $delRsn = $("#delRsn");
      if($delRsn.val().length < 10) {
        alert("삭제사유를 10글자 이상으로 작성해주십시오.");
        $delRsn.focus();
        return;
      }

      if(!confirm("삭제하시겠습니까?")) {return;}

      const params = {
        acIp : global.acIp
        ,empId : global.empId
        ,scIoDocNo : $("#scIoDocNo").val()
        ,delRsn : $delRsn.val()
      };

      $.esutils.postApi("/api/secrtactvy/securityAdminViolation/ioViolation/sec/delete", params, function(res) {
        if(res?.data) {
          alert("보안위규정보가 삭제 되었습니다.");
          //목록으로 돌아가기
          $("#buttonGoList1").trigger("click");
        }
        else {
          alert("보안위규정보 삭제 중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      })

    });
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
                        <span id="actDoBtn1" class="button bt_s1"><button type="button" style="width:100px;">삭제</button></span>
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

            <input type="hidden" id="ofendEmpEmail" name="ofendEmpEmail" value=""/>
            <input type="hidden" id="ofendEmpNm"    name="ofendEmpNm"    value=""/>
            <input type="hidden" id="ofendEmpNm2"   name="ofendEmpNm2"   value=""/>
            <input type="hidden" id="actCompId"     name="actCompId"     value=""/>
            <input type="hidden" id="ofendCompNm"   name="ofendCompNm"   value=""/>
            <input type="hidden" id="ofendText"      name="ofendText"      value=""/>

            <input type="hidden" id="ofendDetailGbn" name="ofendDetailGbn" value=""/>
            <input type="hidden" id="regEmpId" name="regEmpId" value=""/>


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
                    <th>모바일 포렌직&nbs</th>
                    <td colspan=3 view-data="dMobileForensicsGbnNm"></td>
                </tr>
                <tr>
                    <th>적발 사업장</th>
                    <td view-data="actCompNm"></td>
                    <th>적발 건물</th>
                    <td view-data="actBldgNm"></td>
                </tr>

                <tr>
                    <th>적발 건물 상세</th>
                    <td view-data="actLocateNm"></td>
                    <th>적발 GATE&nbsp;</th>
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
                    <tr id="delTrId">
                        <th>삭제사유</th>
                        <td><textarea id='delRsn' name='delRsn'	style="width: 95%; height: 70px; overflow: auto;"></textarea></td>
                    </tr>
                    </tbody>
                </table>

                <!-- 버튼 -->
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span id="buttonGoList2" class="button bt_s2"><button type="button" style="width:100px;">목록</button></span>
                        <span id="actDoBtn2" class="button bt_s1"><button type="button" style="width:100px;">삭제</button></span>
                    </div>
                </div>
                <!-- 버튼 끝 -->
            </form>
        </div>
    </div>