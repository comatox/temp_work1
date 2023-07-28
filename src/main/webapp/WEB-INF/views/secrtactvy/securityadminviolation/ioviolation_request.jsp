<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
  let interviewerGrid;
  $(document).ready(function (e) {

    /*****************
     * [날짜 피커 적용]
     *****************/
    $.esutils.datepicker(["[id=violationDate]"], {onSelect : onSelectDatePickerCallback});
    $("#violationDate").val($.esutils.getToday());

    // 입주사 접견자 grid Init(uiInit : true)
    interviewerGrid = new GridUtil({
      url : "/api/secrtactvy/securityAdminViolation/ioViolation/interviewer"
      ,uiInit : true
      , gridOptions: {
        width : "700"
        ,emptyText : "입주사 접견자 정보가 없습니다."
        ,colData: [
          {
            headerName: "선택"
            , field: ""
            , width: "5%"
            , formatter : (_,options, row) => {
                return '<input type="checkbox" class="border_none" name="interviewrChk"  value="'+row.empId+'" checked />';
            }
          },
          {
            headerName: "소속조직"
            , field: "deptNm"
            , width: "25%"
          },
          {
            headerName: "이름"
            , field: "empNm"
            , width: "10%"
          },
          {
            headerName: "직위"
            , field: "jwNm"
            , width: "10%"
          },
        ]
      }
      ,search : {
        beforeSend : () => {
          return {ioEmpId : $("#ofendEmpId").val(), vstDt : $("#violationDate").val()};
        }
      }
    });

    // grid init
    interviewerGrid.init();

    // default 값 세팅
    setDefulatValue();

    // 위규 구분 값 셋팅
    setOfendGbnValue();

    // 위규 구분 값 변경시
    onChangeOfendGbnValue();

    // 위규 내용 값 변경시
    onChangeOfendDetailGbnValue();

    // 적발 시각 세팅
    violationTimeSet();

    // 적발 건물 값 초기 이천 세팅
    setBlgCode("1101000001");

    // 적발 건물 변경시
    onChangeActBldg();

    // 적발 사업장 값 변경시
    onChangeActCompId();

    // 방문객 찾기
    searchEmpPopup();

    // 등록 버튼 클릭시
    saveIoviolation();

    // 취소 버튼 클릭시
    onClickCancelBtn();

  });

  /*************
   * [X사번 구분]
   *************/
  function isXSabunYn() {
    return global.empId.startsWith("X");
  }

  /*******************************
   * [위규구분 SelectBox Setting]
   *******************************/
  function setOfendGbnValue() {
    $.esutils.renderCode([
      {type: "select", grpCd: "C052", targetId: "ofendGbn", blankOption: true, filter: result => isXSabunYn() ? result.etc1 === "X" : true},
    ]);
  }

  /*********************
   * [날짜 피커 값 변경시]
   *********************/
  function onSelectDatePickerCallback() {
    //접견자 조회
    searchMeetList();
  }

  /********************
   * [default 값 세팅]
   ********************/
  function setDefulatValue() {

    $("#compId").val(global.compId);
    $("#deptId").val(global.deptId);
    $("#jwId").val(global.jwId);
    $("#empId").val(global.empId);

    $("#viewEmpNmJw").text(global.empNm + " " + global.jwNm);
    $("#viewCompNm").text(global.compNm);
    $("#viewDeptNm").text(global.deptNm);

  }

  /******************
   * [적발 시각 세팅]
   ******************/
  function violationTimeSet() {

    const today = new Date();

    let _html = "";
    // 현재 시간 Hours
    let ht = today.getHours().toString().length === 1 ? "0" + today.getHours().toString() : today.getHours().toString();

    // 현재 분 minutes 앞자리(1,2,3,4,5,6)
    let mt = today.getMinutes().toString().substr(0, 1);

    // 시간 세팅
    for (let i = 1; i <= 24; i++) {
      let selected = "";
      let v = i.toString().length === 1 ? "0" + i : i.toString();
      if (v === "24") v = "00";
      if (v === ht) selected = " selected";

      _html += "<option value=" + v + " " + selected + ">" + v + "시</option>";
    }
    $("#violationHour").html(_html);

    // 분 세팅
    _html = "";
    for (let k = 0; k <= 50; k = k + 10) {
      let selected = "";
      let v = k === 0 ? "00" : k;

      if (k.toString().substr(0, 1) === mt) selected = " selected";
      _html += "<option value=" + v + " " + selected + ">" + v + "분</option>";
    }
    $("#violationMin").html(_html);
  }

  /**********************
   * [적발 건물 조회]
   **********************/
  function setBlgCode(compVal) {
    $.esutils.renderCode([
      {type: "select", grpCd: "C063", targetId: "actBldg", blankOption: true, filter: result => result.etc4 === compVal},
    ]);

    $("#actLocate").empty();
  }

  /*******************
   * [적발 건물 변경시]
   ********************/
  function onChangeActBldg() {
    $("#actBldg").on("change", function () {
      $.esutils.renderCode([
        {type: "select", grpCd: "C064", targetId: "actLocate", blankOption: true, filter: result => result.etc4 === $(this).val()},
      ]);
    });
  }

  /**********************
   * [적발 사업장 변경시]
   **********************/
  function onChangeActCompId() {
    $("#actCompId").on("change", function () {
      setBlgCode($(this).val());
    });
  }

  /**********************
   * [위규 구분 값 변경시]
   **********************/
  function onChangeOfendGbnValue() {

    // 위규구분 값 변경시
    $("#ofendGbn").on("change", function () {

      const type = $(this).val();
      const ofendDetailId = "ofendDetailGbn";
      let subContentTitle1 = "";

      $("#securityTable").hide();
      $("#trMobile").hide();

      if (type === "C0521001") { //출입보안
        subContentTitle1 = "외부인 출입보안";
        $.esutils.renderCode([
          {type: "select", grpCd: "C058", targetId: ofendDetailId, blankOption: true, filter: result => isXSabunYn() ? result.etc1 === "X" : true},
        ]);
      } else if (type === "C0521002") { //관리보안
        subContentTitle1 = "외부인 관리보안";
        $.esutils.renderCode([
          {type: "select", grpCd: "C065", targetId: ofendDetailId, blankOption: true, filter: result => isXSabunYn() ? result.etc1 === "X" : true},
        ]);
      } else if (type === "C0521003") { //문서보안
        subContentTitle1 = "외부인 문서보안";
        $.esutils.renderCode([
          {type: "select", grpCd: "C066", targetId: ofendDetailId, blankOption: true, filter: result => isXSabunYn() ? result.etc1 === "X" : true},
        ]);
      } else if (type === "C0521004") { //전산보안
        subContentTitle1 = "외부인 전산보안";
        $.esutils.renderCode([
          {type: "select", grpCd: "C067", targetId: ofendDetailId, blankOption: true, filter: result => isXSabunYn() ? result.etc1 === "X" : true},
        ]);
      } else if (type === "C0521005") { //기타사항
        subContentTitle1 = "외부인 기타사항";
        $.esutils.renderCode([
          {type: "select", grpCd: "C068", targetId: ofendDetailId, blankOption: true, filter: result => isXSabunYn() ? result.etc1 === "X" : true},
        ]);
      } else if (type === "C0521006") { //사내질서
        subContentTitle1 = "외부인 사내질서";
        $.esutils.renderCode([
          {type: "select", grpCd: "C075", targetId: ofendDetailId, blankOption: true, filter: result => isXSabunYn() ? result.etc1 === "X" : true},
        ]);

      }

      if (subContentTitle1) {
        $("#subContTitle1").html(subContentTitle1);
        $("#securityTable").show();
      }

      $("#corrPlanSendYn").val("");

      //사내질서일 때만 변경되는 사항
      if (type === "C0521006") {
        $("#ActGateTitle").html("적발위치");
        $("#ActBldgTitle").html("적발 건물");
        $("#ActLocateTitle").html("적발 건물 상세");
      } else {
        $("#ActGateTitle").html("적발 GATE");
        $("#ActBldgTitle").html("적발 건물<span class='necessary'></span>");
        $("#ActLocateTitle").html("적발 건물 상세<span class='necessary'></span>");
      }

    });
  }

  /**********************
   * [위규 내용 변경시]
   **********************/
  function onChangeOfendDetailGbnValue() {

    $("#ofendDetailGbn").on("change", function (e) {
      const type = $(this).val();
      const $tr = $("#trMobile");

      $tr.hide();

      $.esutils.renderCode([
        {type: "select", grpCd: "C059", targetId: "ofendSubGbn", blankOption: true, filter: result => result.etc1 === type},
      ], function () {
        if(type === "C0581009") {
          $("#tr_mobile").show();

          //모바일 포렌직 추가 2016.-01.27 C0511001'//출입보안
          $.esutils.renderCode([
            {type: "select", grpCd: "C060", targetId: "mobileForensicsGbn", blankOption: true, filter: result => result.etc1 === "C0531010"},
          ]);
        }
        else {
          $("#mobileForensicsGbn").empty();
        }
      });

    });
  }

  /******************
   * [방문객 찾기 팝업]
   ******************/
  function searchEmpPopup() {
    // keyPress Enter
    $("#searchEmpNm").on("keypress", function (e) {
      if (e.keyCode === 13) {
        getEmpInfo();
      }
    })

    // 사원 검색 버튼 클릭
    $("#searchEmpNmBtn").on("click", function () {
      getEmpInfo();
    });
  }

  function getEmpInfo() {

    const searchEmpNm = $("#searchEmpNm").val();
    if (searchEmpNm.length < 2) {
      alert("검색하실 방문객명을 두 글자 이상 입력해주십시오.");
      return;
    }
    const param = {
      empNm: searchEmpNm,
      onedaySubcontYn : "Y"
    };

    $.esutils.openIoEmpSearchViolationPopup({
      ...param,
      fnCallback: function (result) {
        if (result?.originRowData) {
          const d = result.originRowData;

          $("#searchEmpNm").val(d.ioEmpNm);
          $("#ofendEmpId").val(d.ioEmpId);
          $("#showDeptCompNm").html(d.ioCompId + " (" + d.compKoNm + ")");
          $("#ofendCompId").val(d.ioCompId);
          $("#telNo").val(d.ioHpNo);
          $("#showEmpId").html(d.ioEmpId);
          $("#checkFlagEmp").val("Y");

          // 접견자 조회
          searchMeetList();
        }
      }
    });
  }

  /*********************
   * [접견자 리스트 조회]
   *********************/
  function searchMeetList() {
    interviewerGrid.searchData();
  }

  /*********************
   * [외부인 위규자 등록]
   *********************/
  function saveIoviolation() {

    $(".btnSave").on("click", function() {

      // 데이터 검증
      if(!formDataValidation()) {return;}

      if(!confirm("위규자 정보를 입력하시겠습니까?")) {
        return;
      }

      // 접견자 TotalList
      const interviewerTotalList = interviewerGrid.rowDataList;

      // 접견자 List
      let interviewerList = [];

      if(interviewerTotalList?.length > 0) {
        const meetEmpIdCheckList = [];
        $("input[name=interviewrChk]").each(function(i,d) {
          if($(this).is(":checked")) {
            meetEmpIdCheckList.push($(this).val());
          }
        });

        // 체크박스에 체크된 사원번호를 통해 접견자 정보를 얻음.
        if(meetEmpIdCheckList.length > 0) {
          interviewerTotalList.forEach((d) => {
            if(meetEmpIdCheckList.includes(d.empId)) {
              interviewerList.push({
                compId : d.compId
                ,deptId : d.deptId
                ,jwId : d.jwId
                ,empId : d.empId
              })
            }
          });
        }
      }

      const params = {
        ...($.esutils.getFieldsValue($("#securityForm")))
        ,interviewerList
        ,acIp : global.acIp
        ,crtBy : global.empId
      };

      $.esutils.postApi("/api/secrtactvy/securityAdminViolation/ioViolation/save", params, function(res) {
        if(res){
          alert("방문객 보안 위규자 정보가 등록 되었습니다.");

          //목록으로 이동
          goList();
        }
        else {
          alert("방문객 보안 위규자 정보 저장 중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      },{loading : true});

    });
  }

  /**********************
   * [등록 전 데이터 검증]
   **********************/
  function formDataValidation() {
    const checkFlagEmp = $("#checkFlagEmp").val();
    const ofendGbn = $("#ofendGbn").val();
    const telNo = $("#telNo").val();
    const ofendDetailGbn = $("#ofendDetailGbn").val();

    if(!ofendGbn) {
      alert("위규 구분을 선택해주십시오.");
      $("#ofendGbn").focus();
      return false;
    }
    if(checkFlagEmp !== "Y") {
      alert("임직원 정보를 입력해주십시오.");
      $("#searchEmpNm").focus();
      return false;
    }
    if(!telNo) {
      alert("연락처를 입력해주십시오.");
      $("#telNo").focus();
      return false;
    }
    if(!ofendDetailGbn) {
      alert("위규 내용을 선택해주십시오.");
      $("#empPassGbn").focus();
      return false;
    }

    // 위규 내용 상세이 있는 경우 Validation Check 추가
    if ( $("#ofendSubGbn option").size() > 1 ) {
      if(!$("#ofendSubGbn").val()) {
        alert("위규 내용 상세을 선택해주십시오.");
        $("#ofendSubGbn").focus();
        return false;
      }
    }

    // Mobile 내용이 있는 경우 Validation Check 추가
    if ( $("#mobileForensicsGbn option").size() > 1) {
      if(!$("#mobileForensicsGbn").val()) {
        alert("모바일 포렌직 위규 내용 상세을 선택해주십시오.");
        $("#mobileForensicsGbn").focus();
        return false;
      }
    }

    //사내질서는 적발건물 필수 아님
    if(ofendGbn !== "C0521006"){
      // 적발 건물이 있는 경우 Validation Check 추가
      if ( $("#actBldg option").size() > 1  ) {
        if(!$("#actBldg").val()) {
          alert("적발 건물을 선택해주십시오.");
          $("#actBldg").focus();
          return false;
        }
      }
    }

    //사내질서는 적발건물 필수 아님
    if(ofendGbn !== "C0521006"){
      // 적발 건물 상세가 있는 경우 Validation Check 추가
      if ( $("#actLocate option").size() > 1  ) {
        var ACT_LOCATE = $("#ACT_LOCATE").val();
        if(!$("#actLocate").val()) {
          alert("적발 건물 상세를 선택해주십시오.");
          $("#actLocate").focus();
          return false;
        }
      }
    }

    // 보안요원 성명성명
    if(!$("#secManNm").val()) {
      alert("보안요원 성명을 입력해주십시오.");
      $("#secManNm").focus();
      return false;
    }

    if($("#actGate").val() && $("#actGate").val().getBytes3() > 50) {
      alert("적발 GATE는 50자 이내로 작성하세요.");
      $("#actGate").focus();
      return false;
    }

    if($("#secManNm").val() && $("#secManNm").val().getBytes3() > 50) {
      alert("보안요원 성명은 50자 이내로 작성하세요.");
      $("#secManNm").focus();
      return false;
    }

    if($("#etcRsn").val() && $("#etcRsn").val().getBytes3() > 1000) {
      alert("보안요원 검토의견은 1000자 이내로 작성하세요.");
      $("#etcRsn").focus();
      return false;
    }
    return true;
  }

  /********************
   * [취소 버튼 클릭시]
   ********************/
  function onClickCancelBtn() {
    $(".btnCancel").on("click", function() {
      if(confirm("외부인 위규자 입력을 취소하시겠습니까?")) {
        goList();
      }
    });
  }

  /*********************************
   * [외부인 위규자 조회 목록으로 이동]
   *********************************/
  function goList() {
    // 외부인 위규자 조회
    $.esutils.href("/secrtactvy/securityadminviolation/ioviolation/list");
  }
</script>

<div id="contentsArea">
    <!-- <h1 class="title>외부인 보안 위규자 등록</h1> -->

    <table width="100%" border="0">
        <tr>
            <td align="left">
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_362.png"/>
            </td>
            <td align="right">
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s2"><button type="button" class="btnCancel" style="width:100px;">취소</button></span>
                        <span class="button bt_s1"><button type="button" class="btnSave" style="width:100px;">등록</button></span>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <img src="/esecurity/assets/common/images/common/line.png" width="100%" height="3"/>
            </td>
        </tr>
    </table>

    <!-- realContents start -->
    <div id="realContents">
        <form id="securityForm" name="securityForm" method="post">
            <input type="hidden" id="checkFlagEmp" name="checkFlagEmp"/>
            <input type="hidden" id="ofendCompId" name="ofendCompId"/>
            <input type="hidden" id="ofendEmpId" name="ofendEmpId"/>
            <input type="hidden" id="compId" name="compId" value=""/>
            <input type="hidden" id="deptId" name="deptId" value=""/>
            <input type="hidden" id="jwId" name="jwId" value=""/>
            <input type="hidden" id="empId" name="empId" value=""/>
            <input type="hidden" id="corrPlanSendYn" name="corrPlanSendYn" value=""/>
            <h1 class="txt_title01">등록자</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <tbody>
                <tr>
                    <th style="width:117px;">위규 구분<span class="necessary"></span></th>
                    <td style="width:213px;">
                        <select id="ofendGbn" name="ofendGbn" style="width: 242px;" />
                    </td>
                    <th style="width:117px;">성명/직위</th>
                    <td style="width:213px;" id="viewEmpNmJw"></td>
                </tr>
                <tr>
                    <th>회사명</th>
                    <td id="viewCompNm"></td>
                    <th>부서</th>
                    <td id="viewDeptNm"></td>
                </tr>
                <tr>
                    <th>적발일<span class="necessary"></span></th>
                    <td>
                        <input type="text" id="violationDate" name="violationDate" size="10" class="readonly" style="width:180px;" readonly/>
                        <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="img_violation_date"/>
                    </td>
                    <th>적발시각<span class="necessary"></span></th>
                    <td>
                        <select name="violationHour" id="violationHour" style="width:75px;"></select>
                        <select name="violationMin" id="violationMin" style="width:75px;"></select>
                    </td>
                </tr>
                </tbody>
            </table>
            <h1 id="subContTitle1" class="txt_title01"></h1>
            <table id="securityTable" cellpadding="0" cellspacing="0" border="0" class="view_board" style='display :none;'>
                <tbody>
                <tr>
                    <th style='width:117px;'>성명<span class=\"necessary\"></span></th>
                    <td style='width:213px;'>
                        <input type='text' id='searchEmpNm' name='searchEmpNm' style='width:75%'/>
                        <a href='javascript: void(0);' id="searchEmpNmBtn" class='btn_type01' style='margin-left: 5px'><span>검색</span></a>
                    </td>
                    <th style='width:117px;'>연락처</th>
                    <td style='width:213px;'>
                        <input type='text' id='telNo' name='telNo' style='width:85%' readonly/>
                    </td>
                </tr>

                <tr>
                    <th>아이디</th>
                    <td id='showEmpId' style='width:213px;'></td>
                    <th>회사</th>
                    <td id='showDeptCompNm'></td>
                </tr>

                <tr>
                    <th>위규 내용<span class="necessary"></span></th>
                    <td><select id='ofendDetailGbn' name='ofendDetailGbn' style='width: 242px;' /></td>
                    <th>위규 내용 상세<span class="necessary"></span></th>
                    <td><select id='ofendSubGbn' name='ofendSubGbn' style='width: 242px;'/></td>
                </tr>

                <tr id="tr_mobile" style='display :none;'>
                    <th>모바일 포렌직<span class="necessary"></span></th>
                    <td colspan=3><select id='mobileForensicsGbn' name='mobileForensicsGbn' style='width: 242px;'/></td>
                </tr>

                <tr>
                    <th>적발 사업장<span class="necessary"></span></th>
                    <td><select id='actCompId' name='actCompId' style='width: 242px;'>
                        <option value='1101000001' selected>이천</option>
                        <option value='1102000001'>청주</option>
                        <option value='1108000001'>분당</option>
                    </select>
                    </td>
                    <th id="ActBldgTitle">적발 건물<span class="necessary">&nbsp;</span></th>
                    <td><select id='actBldg' name='actBldg' style='width: 242px;'/>
                    </td>
                </tr>

                <tr>
                    <th id="ActLocateTitle">적발 건물 상세<span class="necessary"></span></th>
                    <td><select id='actLocate' name='actLocate' style='width: 242px;'/>
                    </td>
                    <th id="ActGateTitle">적발 GATE</th>
                    <td><input type='text' id='actGate' name='actGate' style='width:85%' maxlength=50/>
                    </td>
                </tr>

                <tr>
                    <th>보안요원 성명<span class="necessary"></span></th>
                    <td><input type='text' id='secManNm' name='secManNm' style='width:242px;' maxlength=50/>
                    </td>
                    <th>방문증 통합사번</th>
                    <td><input type='text' id='visitEmpno' name='visitEmpno' style='width:242px;' maxlength=50/>
                    </td>
                </tr>

                <tr>
                    <th>보안요원 검토의견</th>
                    <td colspan=3><textarea id='etcRsn' name='etcRsn' style="width: 95%; height: 70px; overflow: auto;"></textarea>
                    </td>
                </tr>

                <tr>
                    <th>입주사 접견자</th>
                    <td colspan=3>
                        <div id="grid" class="table_type2"></div>
                    </td>
                </tr>


                </tbody>
            </table>
            <!-- 버튼 -->
            <div>
                <div>
                    <h1 class="txt_title01">첨부파일</h1>
                    <table id="attachTable" cellpadding="0" cellspacing="0" border="0" class="view_board">
                        <tr>
                            <td colspan="2">
                                <input type="text" name="violation_files" id="violation_files" class="file_input_textbox" value=" " readonly="readonly"/>
                                <div class="file_input_div" style="float:left; margin-left:5px;">
                                    <span class="button bt2"><button style="width:80px;" type="button">파일첨부</button></span>
                                    <input type="file" class="file_input_hidden" id="notice_file" name="fileToUpload"
                                           onchange="javascript: document.getElementById('violation_files').value = this.value"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <!-- 버튼 끝 -->
            <!-- 버튼 -->
            <div class="buttonGroup">
                <div class="leftGroup">
                    <span class="button bt_s2"><button class="btnCancel" type="button" style="width:100px;">취소</button></span>
                    <span class="button bt_s1"><button class="btnSave" type="button" style="width:100px;">등록</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->
        </form>
    </div>
</div>