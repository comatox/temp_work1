let rowId = 0;
let esecurityApproval;

const Inoutwrite = {

  // 초기화
  init: function (props) {
    const {inoutApplNo} = props;

    // 결재 초기화
    esecurityApproval = new EsecurityApproval("approvalWrap");
    esecurityApproval.form({
      schemaNm: "INOUT",
      initFetch: false
    });

    // 반입예정일자
    $.esutils.datepicker(["[id=indate]"], {selectedDates: [new Date()]});
    $("#indate").hide();

    $("#writedateTxt").html($.esutils.getToday('-'));

    $("#empInfo").val(`${global.deptNm} ${global.empNm} ${global.jwNm}`);
    $("#empTel").val(global.telNo2);

    // 이벤트 등록
    this.fnAddEventListener();

    console.log('inoutApplNo >>> ', inoutApplNo);
    if (inoutApplNo) {
      this.fn_setInfo(inoutApplNo); // 기존 데이터 셋팅
    } else {
      this.fn_articleChk({articlekndno: "1"});

      // 사용자에 따른 반출캠퍼스/최종반입캠퍼스 선택
      const divCd = global.divCd;

      if (divCd === "YP") {
        // 반출캠퍼스
        $("#companyno").val("1101000001").attr("selected", "selected");
        // 최종반입캠퍼스
        $("#returncompanyareaknd").val("101").attr("selected", "selected");
      } else if (divCd === "CP") {
        // 반출캠퍼스
        $("#companyno").val("1102000001").attr("selected", "selected");
        // 최종반입캠퍼스
        $("#returncompanyareaknd").val("102").attr("selected", "selected");
      } else if (divCd === "AP") {
        // 반출캠퍼스
        $("#companyno").val("1103000001").attr("selected", "selected");
        // 최종반입캠퍼스
        $("#returncompanyareaknd").val("103").attr("selected", "selected");
      } else if (divCd === "SC") {
        // 반출캠퍼스
        $("#companyno").val("1601000001").attr("selected", "selected");
        // 최종반입캠퍼스
        $("#returncompanyareaknd").val("601").attr("selected", "selected");
      } else if (divCd === "A4" || divCd === "S2" || divCd === "S20" || divCd === "ET1" || divCd === "ET2" || divCd === "A41" || divCd === "A42") {
        // 반출캠퍼스
        $("#companyno").val("1107000001").attr("selected", "selected");
        // 최종반입캠퍼스
        $("#returncompanyareaknd").val("107").attr("selected", "selected");
      }

      this.fn_foreignCompList("FOREIGN_COMP", "", "선택하세요.", "");
    }
  },

  // 이벤트 등록
  fnAddEventListener: function () {

    // 부서 검색 Enter event
    $("#outcompanydeptNm").keypress(function (event) {
      if (event.keyCode == 13) {
        this.fn_dept();
      }
    });

    // 상대처(외부업체) 검색 Enter event
    $("#systempartnernm").keypress(function (event) {
      if (event.keyCode == 13) {
        this.fn_partner();
      }
    });

    // 목록 버튼 클릭
    $("button.btn_list").on("click", function () {
      location.href = global.contextPath + "/inoutasset/inout/list";
    });

    // 저장 버튼 클릭
    $("button.btn_save").on("click", function () {
      Inoutwrite.fn_approval('SAVE');
    });

    // 상신 버튼 클릭
    $("button.btn_report").on("click", function () {
      Inoutwrite.fn_approval('REPORT');
    });

    // 구분 선택
    $("input[name=articlekndno]").on("click", function () {
      Inoutwrite.fn_articleChk({articlekndno: $(this).val()});
    });

    // 내용등급 선택
    $("select[name=documentknd]").on("change", function () {
      Inoutwrite.fn_documentKnd();
    });

    // 그룹 선택
    $("select[name=articlegroupid]").on("change", function () {
      Inoutwrite.fn_inOutChange({});
    });

    // 반출사업장 선택
    $("#companyno").on("change", function () {
      Inoutwrite.fn_inOutKnd();
    });

    // 반출구분 선택
    $("#inoutknd").on("change", function () {
      Inoutwrite.fn_inOutKnd();
    });

    // 상대처 구분 선택
    $("#outcompanyknd").on("change", function () {
      Inoutwrite.fn_partnerChange();
    });

    // 반출사유 선택
    $("#outreasonid").on("change", function () {
      Inoutwrite.fn_outReasonId();
    });

    // 물품내역 헹 추가
    $("#btn_addRow").on("click", function () {
      const articlegroupid = $("#articlegroupid option:selected").val();
      if (articlegroupid === "" || articlegroupid === null) {
        alert("그룹을 먼저 선택하세요.");
        $("#articlegroupid").focus();
        return false;
      }

      Inoutwrite.fn_addArticleItemRow();
    });
  },

  fn_setInfo: function (inoutApplNo) {
    $.esutils.loading(true);
    const esecurityInoutasset = new EsecurityInoutasset(inoutApplNo);
    esecurityInoutasset.callback(function (data) {
      const field = data.viewInfo;

      $("#inoutApplNo").val(field.inoutApplNo);
      $("#writedate").val(field.writedate);
      $("#writeseq").val(field.writeseq);

      // 작성자
      $("#empInfo").html(field.empNm);

      $("#compNm").html(field.compNm);

      // 반출입번호
      $("#inoutserialnoTxt").html(field.inoutserialno);
      $("#inoutserialno").val(field.inoutserialno);

      // 작성일자
      $("#writedateTxt").html(field.writedate);

      // 사내번호
      $("#empTel").val(field.empTel);

      // 구분 선택
      const articlekndno = field.articlekndno;
      $("input:radio[name='articlekndno']").each(function (index, value) {
        if ($(this).attr("value") === articlekndno) {
          $(this).attr("checked", "checked");
        }
      });

      Inoutwrite.fn_articleChk({
        articlekndno,
        articlegroupid: field.articlegroupid,
        outreasonid: field.outreasonid
      });

      // 내용등급
      $("#documentkn").val(field.documentknd);

      // 그룹
      $("#articlegroupid").val(field.articlegroupid);

      // 상대처구분
      $("#outcompanyknd").val(field.outcompanyknd);

      // 반출구분
      $("#inoutknd").val(field.inoutknd);

      // PRNo, 문서번호
      $("#prno").val(field.prno);

      // 반출사업장
      $("#companyno").val(field.companyno);

      // 최종반입사업장
      // 사업장코드가 아님, 사업장 지역코드임 주의할 것.
      $("#returncompanyareaknd").val(field.returncompanyareaknd);

      // 상대처
      // 자사
      $("#mycompanyno").val(field.mycompanyno);
      $("#outcompanydeptNm").val(field.outcompanydeptNm);
      $("#outcompanydeptCd").val(field.outcompanydeptCd);

      // 상대처
      // 외부업체 및 해외법인
      $("#systempartnerid").val(field.systempartnerid);
      $("#systempartnernm").val(field.systempartnernm);

      $("#partnerEtcNm").val(field.partnerEtcNm);

      Inoutwrite.fn_foreignCompList("foreignComp", "", "선택하세요.", field.foreignComp);

      // 반입예정일자
      $("#indate").val(field.indate);

      // 반출사유
      $("#outreasonid").val(field.outreasonid);
      Inoutwrite.fn_outReasonId(field.outreasonid);

      // 반출상세사유
      $("#outreasonsubknd").val(field.outreasonsubknd);

      if (articlekndno === 4 || articlekndno === 5) {
        if (outcompanyknd === "2") {
          $("#copUrlTr").show();
          // CoP URL
          $("#copurl").val(field.copUrl);
        }
      } else {
        $("#copUrlTr").hide();
      }

      // 기타반출사유
      $("#inoutetc").val(field.inoutetc);

      const sendYn = field.sendYn;
      $("input:checkbox[name='sendYn']").each(function (index, value) {
        if ($(this).attr("value") === sendYn) {
          $(this).attr("checked", "checked");
        }
      });

      /*Inoutwrite.fn_inOutChange({
        articlekndno, articlegroupid, documentknd, inoutknd, outcompanyknd, attribute, 'INIT'
      });*/

      $.esutils.loading(false);
    });
  },

  // 자사캠퍼스 부서 조회
  fn_dept: function () {
    const param = {
      deptNm: $("#outcompanydeptNm").val(), compId: global.compId
    };

    // 팝업

  },

  // 외부업체 조회
  fn_partner: function () {
    const param = {
      partnerNm: $("#systempartnernm").val(),
    };

    // 팝업

  },

  // 해외법인 조회
  fn_foreignCompList: function (selectBoxId, allValue, allText, selectedValue) {
    // $.ajax({
    //   url: "/common.json",
    //   dataType : "text",
    //   type: "POST",
    //   data: {
    //     "nc_trId"			: "fmForeignCompList",
    //     "callback"			: ""
    //   },
    //   success: function(data) {
    //
    //     var jsonData = JSON.parse(data);
    //
    //     if(jsonData.LIST_recordSet != null) {
    //       $("#"+selectBoxId).get(0).options.length = 0;
    //       $("#"+selectBoxId).get(0).options[0] = new Option(allText, allValue);
    //
    //       for(var i=0; i<jsonData.LIST_recordSet.length; i++) {
    //         var record = jsonData.LIST_recordSet[i];
    //         var codeValue = record.COMP_ID;
    //         var codeName  = record.COMP_NM;
    //         $("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue);
    //       }
    //       if( selectedValue != undefined) {
    //         $("#"+selectBoxId).val(selectedValue);
    //       }
    //     }
    //   }, error: function(XMLHttpRequest, textStatus, errorThrown) {
    //     alert(textStatus);
    //   }
    // });
  },

  // 구분에 따른 그룹 목록 조회
  fn_articleGroupCodeList: function (selectBoxId, allValue, allText, selectedValue, articlegroupid, articlekndno, companyknd, companyareaknd, compId, articlenouseyn) {
    $.esutils.postApi('/api/inoutasset/inoutwrite/articleGroupCode', {
      companyknd, companyareaknd, compId, articlekndno, articlegroupid, articlenouseyn
    }, ({data}) => {
      if (data) {
        $("#" + selectBoxId).get(0).options.length = 0;
        $("#" + selectBoxId).get(0).options[0] = new Option(allText, allValue)

        for (let i = 0; i < data.length; i++) {
          const codeValue = data[i].articlegroupid;
          const codeName = data[i].articlegroupname;
          $("#" + selectBoxId).get(0).options[i + 1] = new Option(codeName, codeValue);
        }
        ;

        if (selectedValue !== undefined) {
          $("#" + selectBoxId).val(selectedValue);
        }
      }
    });
  },

  fn_OutReasonCodeList: function (selectBoxId, allValue, allText, selectedValue) {
    console.log('fn_OutReasonCodeList >>> ', selectedValue);
    $.esutils.getCode("D001", function (result) {
      reasonCode = result.filter(d => d.etc1 === '1101000001');
      reasonCode.sort(function (a, b) {
        return a.detlNm < b.detlNm ? -1 : a.detlNm > b.detlNm ? 1 : 0;
      });

      $("#" + selectBoxId).get(0).options.length = 0;
      $("#" + selectBoxId).get(0).options[0] = new Option(allText, allValue);

      for (let i = 0; i < reasonCode.length; i++) {
        const record = reasonCode[i];
        const codeValue = record.detlCd;
        const codeName = record.detlNm;
        $("#" + selectBoxId).get(0).options[i + 1] = new Option(codeName, codeValue);
      }
      if (selectedValue != undefined) {
        $("#" + selectBoxId).val(selectedValue);
      }
    });
  },

  // 반출사업장 & 반출구분 변경
  fn_inOutKnd: function () {
    const inoutknd = $("#inoutknd").val();

    if (inoutknd === "1") {
      $("#indate").show();
      $("#returncompanyareaknd").show();
    } else {
      $("#indate").hide();
      $("#indate").val("");

      $("#returncompanyareaknd").hide();
      $("#returncompanyareaknd option:eq(0)").attr("selected", "selected");
    }

    // 허가부서 결재자 설정
    Inoutwrite.fn_setPermitLine();
  },

  // 상대처 구분 선택
  fn_partnerChange: function () {
    // 물품 구분 코드
    let articlekndno = "";
    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    const outcompanyknd = $("#outcompanyknd").val();

    if (outcompanyknd === "1") {
      $("#mycompanyno").show();

      $("#outcompanydeptNm").show();
      $("#outcompanydeptBtn").show();

      $("#systempartnernm").hide();
      $("#systempartnernmBtn").hide();

      $("#systempartnerid").val("");
      $("#systempartnernm").val("");

      $("#foreignComp").hide();
      $("#foreignComp > option[value='']").attr("selected", "selected");

      $("#partnerEtcNm").val("");
      $("#partnerEtcNm").hide("");

      $("#outcompanydeptNmLabel").html("상대처 부서");
    } else if (outcompanyknd === "2") {
      $("#mycompanyno").hide();
      $("#mycompanyno > option[value='']").attr("selected", "selected");

      $("#outcompanydeptNm").hide();
      $("#outcompanydeptBtn").hide();
      $("#outcompanydeptNm").val("");
      $("#outcompanydeptCd").val("");

      $("#systempartnernm").show();
      $("#systempartnernmBtn").show();

      $("#partnerEtcNm").show();
      $("#outcompanydeptNmLabel").html("업체 상세 설명");

      $("#foreignComp").hide();
      $("#foreignComp > option[value='']").attr("selected", "selected");
    } else if (outcompanyknd === "3") {
      $("#mycompanyno").hide();
      $("#mycompanyno > option[value='']").attr("selected", "selected");

      $("#outcompanydeptNm").hide();
      $("#outcompanydeptBtn").hide();
      $("#outcompanydeptNm").val("");
      $("#outcompanydeptCd").val("");

      $("#systempartnernm").hide();
      $("#systempartnernmBtn").hide();

      $("#systempartnerid").val("");
      $("#systempartnernm").val("");

      $("#foreignComp").show();
      $("#foreignComp > option[value='']").attr("selected", "selected");

      $("#partnerEtcNm").val("");
      $("#partnerEtcNm").hide("");

      $("#outcompanydeptNmLabel").html("상대처 부서");
    }
    // 반출구분
    // 보세자산 반출입 20140512 from 윤석훈
    // 변경 내용 : ARTICLEKNDNO == "6" 추가
    if (articlekndno === "1" || articlekndno === "4" || articlekndno === "5" || articlekndno === "6") {
      // 자사사업장
      if (outcompanyknd === "1") {
        $("#inoutknd").get(0).options.length = 0;
        $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
        $("#inoutknd").get(0).options[1] = new Option("반입불요", "2");
      } else if (outcompanyknd === "2") {
        $("#inoutknd").get(0).options.length = 0;
        $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
        $("#inoutknd").get(0).options[1] = new Option("반입불요(무상)", "2");
        $("#inoutknd").get(0).options[2] = new Option("반입불요(유상)", "3");
      } else {
        $("#inoutknd").get(0).options.length = 0;
        $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
        $("#inoutknd").get(0).options[1] = new Option("반입불요", "2");
      }
    } else {
      $("#inoutknd").get(0).options.length = 0;
      $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
      $("#inoutknd").get(0).options[1] = new Option("반입불요", "2");
    }
    $("#inoutknd > option[value='1']").attr("selected", "selected");
    Inoutwrite.fn_inOutKnd();
    Inoutwrite.fn_showCop();
  },

  // 반출사유 선택
  fn_outReasonId: function (outreasonid = $("#outreasonid").val()) {
    // 물품 구분 코드
    let articlekndno = "";
    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    if (articlekndno === "1") {
      if (outreasonid === "D0000012" || outreasonid === "D0000195" || outreasonid === "D0000199") {
        $("#prno").show();
        $("#prno").val("");
      } else {
        $("#prno").hide();
        $("#prno").val("");
      }

      if (outreasonid === "D0000195") {
        $("#prno").attr("maxlength", "22");
      } else if (outreasonid === "D0000012" || outreasonid === "D0000199") {
        $("#prno").attr("maxlength", "10");
      }
    } else {
      $("#prno").hide();
      $("#prno").val("");
    }

    // 반출상세사유
    if (outreasonid === "D0000195") {
      $("#outreasonsubknd").show();
    } else {
      $("#outreasonsubknd").val("");
      $("#outreasonsubknd").hide();
    }
  },

  fn_showCop: function () {
    let articlekndno = "";

    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    const outcompanyknd = $("#outcompanyknd").val();
    if ((articlekndno === "4" || articlekndno === "5") && outcompanyknd === "2") {
      $("#copUrlTr").show();
    } else {
      $("#copUrlTr").hide();
    }
  },

  // 그룹 변경
  fn_partnerChange: function () {
    // 물품 구분 코드
    let articlekndno = "";

    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") === "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    const outcompanyknd = $("#outcompanyknd").val();

    if (outcompanyknd === "1") {
      $("#mycompanyno").show();

      $("#outcompanydeptNm").show();
      $("#outcompanydeptBtn").show();

      $("#systempartnernm").hide();
      $("#systempartnernmBtn").hide();

      $("#systempartnerid").val("");
      $("#systempartnernm").val("");

      $("#foreignComp").hide();
      $("#foreignComp > option[value='']").attr("selected", "selected");

      $("#partnerEtcNm").val("");
      $("#partnerEtcNm").hide("");

      $("#outcompanydeptNmLabel").html("상대처 부서");

    } else if (outcompanyknd === "2") {
      $("#mycompanyno").hide();
      $("#mycompanyno > option[value='']").attr("selected", "selected");

      $("#outcompanydeptNm").hide();
      $("#outcompanydeptBtn").hide();
      $("#outcompanydeptNm").val("");
      $("#outcompanydeptCd").val("");

      $("#systempartnernm").show();
      $("#systempartnernmBtn").show();

      $("#partnerEtcNm").show();
      $("#outcompanydeptNmLabel").html("업체 상세 설명");

      $("#foreignComp").hide();
      $("#foreignComp > option[value='']").attr("selected", "selected");

    } else if (outcompanyknd === "3") {
      $("#mycompanyno").hide();
      $("#mycompanyno > option[value='']").attr("selected", "selected");

      $("#outcompanydeptNm").hide();
      $("#outcompanydeptBtn").hide();
      $("#outcompanydeptNm").val("");
      $("#outcompanydeptCd").val("");

      $("#systempartnernm").hide();
      $("#systempartnernmBtn").hide();

      $("#systempartnerid").val("");
      $("#systempartnernm").val("");

      $("#foreignComp").show();
      $("#foreignComp > option[value='']").attr("selected", "selected");

      $("#partnerEtcNm").val("");
      $("#partnerEtcNm").hide("");

      $("#outcompanydeptNmLabel").html("상대처 부서");
    }

    // 반출구분
    // 보세자산 반출입 20140512 from 윤석훈
    // 변경 내용 : articlekndno == "6" 추가
    if (articlekndno === "1" || articlekndno === "4" || articlekndno === "5" || articlekndno === "6") {
      // 자사캠퍼스
      if (outcompanyknd === "1") {
        $("#inoutknd").get(0).options.length = 0;
        $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
        $("#inoutknd").get(0).options[1] = new Option("반입불요", "2");
      } else if (outcompanyknd === "2") {
        $("#inoutknd").get(0).options.length = 0;
        $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
        $("#inoutknd").get(0).options[1] = new Option("반입불요(무상)", "2");
        $("#inoutknd").get(0).options[2] = new Option("반입불요(유상)", "3");
      } else {
        $("#inoutknd").get(0).options.length = 0;
        $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
        $("#inoutknd").get(0).options[1] = new Option("반입불요", "2");
      }
    } else {
      $("#inoutknd").get(0).options.length = 0;
      $("#inoutknd").get(0).options[0] = new Option("반입필요", "1");
      $("#inoutknd").get(0).options[1] = new Option("반입불요", "2");
    }
    $("#inoutknd > option[value='1']").attr("selected", "selected");

    Inoutwrite.fn_inOutKnd();
    Inoutwrite.fn_showCop();
  },

  // 허가부서 결재자 불러오기
  fn_setPermitLine: function () {
    esecurityApproval.renderApprLine("2", []); // 허가부서 초기화

    // TODO 허가부서 항목 선택 영역 처리
    // $("#changeApproveDiv").hide();

    let articlegroupid = $("#articlegroupid option:selected").val();
    if (articlegroupid === null) {
      articlegroupid = "";
    }

    const inoutkndObj = $("#inoutknd option:selected");
    if (inoutkndObj.val() !== "2" && inoutkndObj.val() !== "3") {
      return false;
    }

    const compId = global.compId;
    const deptId = global.deptId;
    const empId = global.empId;

    const appendData = [];

    if (compId === "1211000001" && (articlegroupid === "1100000222" || articlegroupid === "1100000223" || articlegroupid === "1100000185" || articlegroupid === "1100000186" || articlegroupid
        === "1100000187" || articlegroupid === "1100000189" || articlegroupid === "1100000207" || articlegroupid === "1100000210" || articlegroupid === "1100000213" || articlegroupid
        === "1100000219")) {
      appendData.push({
        compId: '1211000001',
        apprEmpId: '501131',
        apprEmpNm: '김용기',
        apprJwId: '3F',
        apprJwNm: '차장',
        apprJcId: 'KA',
        apprJcNm: '팀장',
        apprDeptId: '12110001',
        apprDeptNm: 'GA Team',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: '3F',
        entrustJcId: 'KA',
        subcontYn: 'N'
      });
    } else if (compId === "1101000001" && articlegroupid === "1100000216" && deptId === "11010011") { // 하이이엔지 & 폐기물 & 환경안전팀
      appendData.push({
        compId: '1101000001',
        apprEmpId: '9210488', // 구사번: S00488
        apprEmpNm: '김성은',
        apprJwId: '3A',
        apprJwNm: '부장',
        apprJcId: 'KA',
        apprJcNm: '팀장',
        apprDeptId: '11010011',
        apprDeptNm: '환경안전팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: '3A',
        entrustJcId: 'KA',
        subcontYn: 'N'
      });
    } else if (compId === "1101000001" && articlegroupid === "1100000180" && (deptId === "11010019" || deptId === "11010002")) { // 하이이엔지 & 공사지원 || 건설사업팀
      appendData.push({
        compId: '1101000001',
        apprEmpId: '9211235',
        apprEmpNm: '이필민',
        apprJwId: 'TL',
        apprJwNm: 'TL',
        apprJcId: 'KA',
        apprJcNm: '팀장',
        apprDeptId: '11010033',
        apprDeptNm: '재무팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: 'TL',
        entrustJcId: 'KA',
        subcontYn: 'N'
      });
    } else if (compId === "1210000001" && articlegroupid === "1100000216") {
      appendData.push({
        compId: '1210000001',
        apprEmpId: '09810057',
        apprEmpNm: '정응근',
        apprJwId: '3A',
        apprJwNm: '부장',
        apprJcId: '',
        apprJcNm: '',
        apprDeptId: '12100006',
        apprDeptNm: 'Facility팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: '3A',
        entrustJcId: '',
        subcontYn: 'N'
      });
    } else if (compId === "1101000001" &&  //SKHyEng
        (articlegroupid === "1100000222" || articlegroupid === "1100000223" || articlegroupid === "1100000185" || articlegroupid === "1100000186" || articlegroupid === "1100000187" || articlegroupid
            === "1100000207" || articlegroupid === "1100000208" || articlegroupid === "1100000209" || articlegroupid === "1100000210" || articlegroupid === "1100000211")) {
      appendData.push({
        compId: '1101000001',
        apprEmpId: '9211786',
        apprEmpNm: '나영빈',
        apprJwId: 'TL',
        apprJwNm: 'TL',
        apprJcId: 'TL',
        apprJcNm: 'TL',
        apprDeptId: '11010035',
        apprDeptNm: 'HR팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: 'ED',
        entrustJcId: 'K0',
        subcontYn: 'N'
      });

      if (articlegroupid === "1100000185" || articlegroupid === "1100000187" || articlegroupid === "1100000210") {
        appendData.push({
          compId: '1101000001',
          apprEmpId: '9211482',
          apprEmpNm: '임창묵',
          apprJwId: 'TL',
          apprJwNm: 'TL',
          apprJcId: 'TL',
          apprJcNm: 'TL',
          apprDeptId: '11010035',
          apprDeptNm: 'HR팀',
          apprDivNm: '이천',
          autoSign: '0',
          entrustYn: 'N',
          entrustJwId: 'KA',
          entrustJcId: 'TL',
          subcontYn: 'N'
        });

        appendData.push({
          compId: '1101000001',
          apprEmpId: '9211881',
          apprEmpNm: '장재호',
          apprJwId: 'TL',
          apprJwNm: 'TL',
          apprJcId: 'TL',
          apprJcNm: 'TL',
          apprDeptId: '11010033',
          apprDeptNm: '재무',
          apprDivNm: '이천',
          autoSign: '0',
          entrustYn: 'N',
          entrustJwId: 'EA',
          entrustJcId: 'KE',
          subcontYn: 'N'
        });
      }

      appendData.push({
        compId: '1101000001',
        apprEmpId: '9211235',
        apprEmpNm: '이필민',
        apprJwId: 'TL',
        apprJwNm: 'TL',
        apprJcId: 'KA',
        apprJcNm: '팀장',
        apprDeptId: '11010033',
        apprDeptNm: '재무팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: 'TL',
        entrustJcId: 'KA',
        subcontYn: 'N'
      });
    } else if (compId === "1101000001" //sk하이이엔지
        && articlegroupid === "1100000180" //공구류
        && deptId === "11010007" 	//설비건설팀
    ) {
      appendData.push({
        compId: '1101000001',
        apprEmpId: '9210690',
        apprEmpNm: '최철웅',
        apprJwId: '3A',
        apprJwNm: '부장',
        apprJcId: 'KA',
        apprJcNm: '팀장',
        apprDeptId: '11010007',
        apprDeptNm: '설비건설팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: '3A',
        entrustJcId: 'KA',
        subcontYn: 'N'
      });
    } else if (compId === "1102000001" && articlegroupid === "1100000194" && inoutkndObj.text() === "반입불요(무상)" && (empId === "9111434" || empId === "9111418" || empId === "9111645" || empId
        === "9111785")) {  // 하이스택이면서 비품 항목, 그리고 특정인(정윤구,용궁,정재헌)인 경우에 한해 해당 로직으로 기능동작

      // TODO 허가부서 항목 선택 영역 처리
      // $("#changeApproveDiv").show();
      // $("input:radio[name='changeApprove']:input[value='1']").attr("checked", true);
      Inoutwrite.fn_changeApprove('1');
      return false;

    } else {
      if (compId === "1101000001" && (articlegroupid === "1100000216" || articlegroupid === "1100000217")) { // SK하이엔지 && 폐기물 || 폐수
        appendData.push({
          compId: '1101000001',
          apprEmpId: '9211287',
          apprEmpNm: '최정훈',
          apprJwId: 'EB',
          apprJwNm: '책임',
          apprJcId: 'KA',
          apprJcNm: '팀장',
          apprDeptId: '11010011',
          apprDeptNm: '환경안전팀',
          apprDivNm: '이천',
          autoSign: '0',
          entrustYn: 'N',
          entrustJwId: 'EB',
          entrustJcId: 'KA',
          subcontYn: 'N'
        });
      }
      esecurityApproval.renderApprLineByApi("2", {before: appendData});
    }

    if (appendData.length > 0) {
      esecurityApproval.renderApprLine("2", appendData);
    }
  },

  // 허가부서 결재자 선택 (하이스택이면서 비품 항목, 그리고 특정인(정윤구,용궁,정재헌)인 경우)
  fn_changeApprove: function (type) {
    esecurityApproval.clearPermit(); // 허가부서 초기화
    const appendData = [];

    if (type === '1') {
      appendData.push({
        compId: '1102000001',
        apprEmpId: '9111040',
        apprEmpNm: '김주섭',
        apprJwId: 'TL',
        apprJwNm: 'TL',
        apprJcId: 'KA',
        apprJcNm: '팀장',
        apprDeptId: '11020033',
        apprDeptNm: '청주 Wellness팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: 'TL',
        entrustJcId: 'KA',
        subcontYn: 'N'
      });
    } else {
      appendData.push({
        compId: '1102000001',
        apprEmpId: '9110714',
        apprEmpNm: '허재진',
        apprJwId: 'TL',
        apprJwNm: 'TL',
        apprJcId: '',
        apprJcNm: '',
        apprDeptId: '11020009',
        apprDeptNm: '재무팀',
        apprDivNm: '이천',
        autoSign: '0',
        entrustYn: 'N',
        entrustJwId: 'TL',
        entrustJcId: '',
        subcontYn: 'N'
      });
    }
    esecurityApproval.renderApprLine("2", appendData);
  },

  // 구분 선택
  fn_articleChk: function ({ articlekndno = "1", articlegroupid = "", outreasonid = "" }) {
    console.log(`articlekndno: ${articlekndno} || articlegroupid: ${articlegroupid} || outreasonid: ${outreasonid}`);

    // 그룹
    Inoutwrite.fn_articleGroupCodeList("articlegroupid", "", "선택하세요.", articlegroupid, "", articlekndno, global.compKnd, global.compAreaKnd, global.compId, "1");

    if (`${articlekndno}` === "1" || `${articlekndno}` === "4" || `${articlekndno}` === "5") {
      $("#inoutEtcTitle").html("기타상세<br />반출사유");

      // 내용등급
      $("#documentknd").hide();

      // 반출 사유 select box
      $("#outreasonidTr").show();

      Inoutwrite.fn_OutReasonCodeList("outreasonid", "", "선택하세요.", outreasonid);

    } else if (`${articlekndno}` === "2") {
      $("#inoutEtcTitle").html("반출사유 및<br />반출List(화일명)");

      // 내용등급
      $("#documentknd").show();
      $("#documentknd").get(0).options.length = 0;
      $("#documentknd").get(0).options[0] = new Option("선택하세요.", "");
      $("#documentknd").get(0).options[1] = new Option("평문", "1");
      $("#documentknd").get(0).options[2] = new Option("대외비", "2");
      $("#documentknd").get(0).options[3] = new Option("비밀", "3");

      // 반출 사유 select box
      $("#outreasonidTr").hide();
      $("#outreasonid").get(0).options.length = 0;

    } else if (`${articlekndno}` === "3") {
      $("#inoutEtcTitle").html("반출사유 및<br />문서List");

      // 내용등급
      $("#documentknd").show();
      $("#documentknd").get(0).options.length = 0;
      $("#documentknd").get(0).options[0] = new Option("선택하세요.", "");
      $("#documentknd").get(0).options[1] = new Option("평문", "1");
      $("#documentknd").get(0).options[2] = new Option("대외비", "2");
      $("#documentknd").get(0).options[3] = new Option("비밀", "3");
      $("#documentknd").get(0).options[4] = new Option("대량출력-업무(평문)", "4");
      $("#documentknd").get(0).options[5] = new Option("대량출력-업무(대외비)", "5");
      $("#documentknd").get(0).options[6] = new Option("대량출력-업무(비밀)", "6");
      $("#documentknd").get(0).options[7] = new Option("대량출력-업무외", "7");

      // 반출 사유 select box
      $("#outreasonidTr").hide();
      $("#outreasonid").get(0).options.length = 0;

    } else if (`${articlekndno}` === "6") { //
      $("#inoutEtcTitle").html("기타상세<br />반출사유");

      // 내용등급
      $("#documentknd").hide();

      // 반출 사유 select box
      $("#outreasonidTr").show();

      $("#outreasonid").show();
      $("#outreasonid").get(0).options.length = 0;
      $("#outreasonid").get(0).options[0] = new Option("선택하세요.", "");
      $("#outreasonid").get(0).options[1] = new Option("검사", "D0000010");
      $("#outreasonid").get(0).options[2] = new Option("Sample", "D0000013");
      $("#outreasonid").get(0).options[3] = new Option("교환", "D0000008");
      $("#outreasonid").get(0).options[4] = new Option("대여", "D0000011");
      $("#outreasonid").get(0).options[5] = new Option("반품", "D0000169");
      $("#outreasonid").get(0).options[6] = new Option("분석의뢰", "D0000203");
      $("#outreasonid").get(0).options[7] = new Option("산학협력", "D0000198");
      $("#outreasonid").get(0).options[8] = new Option("업체반납", "D0000197");
      $("#outreasonid").get(0).options[9] = new Option("이체", "D0000015");
      $("#outreasonid").get(0).options[10] = new Option("판매", "D0000012");
      $("#outreasonid").get(0).options[11] = new Option("해외법인", "D0000201");
      $("#outreasonid").get(0).options[12] = new Option("이관", "D0000171");
      $("#outreasonid").get(0).options[13] = new Option("기타", "D0000017");
    }

    if(documentknd) {
      $("#documentknd").val(documentknd);
      Inoutwrite.fn_documentKnd();
    }

    Inoutwrite.fn_partnerChange();
    esecurityApproval.clearPermit(); // 허가부서 초기화
    Inoutwrite.fn_showCop();

    $("#Jlist_1").hide();
    $("#Jlist_2").show();
  },

  // 내용등급 선택
  fn_documentKnd: function () {
    let articlekndno = "";
    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    const documentknd = $("#documentknd option:selected").val();

    if (articlekndno === "3") {
      if (documentknd === "4" || documentknd === "5" || documentknd === "6" || documentknd === "7") {
        // 허가부서 결재자 설정
        Inoutwrite.fn_setPermitLine();
      } else {
        esecurityApproval.clearPermit(); // 허가부서 초기화
      }
    }

    const articlegroupid = $("#articlegroupid option:selected").val();
    // console.log( articlekndno + " : " + articlegroupid + " : " + documentknd );

    // 설계공정 자료이면서 내용등급이 대량출력-업무(비밀)일 경우에만
    // articlekndno = '3' 은 없음으로 else문만 처리됨 by kwg. 230719
    if (articlekndno === "3" && articlegroupid === "1000000011" && documentknd === "6") {
      $("input:text[name='serialno']").each(function (index, value) {
        $(this).attr("readonly", "readonly");
      });
    } else {
      $("input:text[name='serialno']").each(function (index, value) {
        $(this).removeAttr("readonly");
      });
    }
  }
  ,

  fn_noteBookInfo: function () {
    // TODO 팝업 생성 필요 by kwg. 230719
    window.open('/esecurity/inoutasset/inoutwrite/popup/notebookNotice', '', 'width=720,height=727,menebar=no,location=no,scrollbars=yes');
  },

  // 그룹 선택
  fn_inOutChange: function ({
    articlekndno = "", articlegroupid = "", documentknd, inoutknd, outcompanyknd,attribute, load_type
  }) {

    if(articlekndno === "") {
      $("input:radio[name='articlekndno']").each(function (index, value) {
        if ($(this).attr("checked") == "checked") {
          articlekndno = $(this).attr("value");
        }
      });
    }

    if(articlegroupid === "") {
      articlegroupid = $("#articlegroupid option:selected").val();
      if (articlekndno === "1" && articlegroupid === "1000000013") { // 해당 조건은 존재하지 않음으로 동작하지 않음 by kwg. 230726
        alert(
            "※ 기타품목 작성시 주의사항\n\n -업체물품으로 반입증 미작성, 반입증 분실인 경우에만 작성가능\n  - 감사정보시스템에 자동 모니터링\n\n\n ※ 기타품목 작성안내 (팀장전결 必)\n\n - 반출사유 : 업체반납\n - 기타반출사유 : 업체물품 반입사유 + 반입증 분실 or 반입증 미작성\n   + 업체물품 업체에 반납 (하이스텍 물품아님)");
      }
    }

    if (articlekndno === "2" && articlegroupid === "1000000010") { // 휴대용전산저장장치 & 노트북
      Inoutwrite.fn_noteBookInfo();
    }

    // 허가부서 결재자 설정
    Inoutwrite.fn_setPermitLine();

    // 물품 내역 등록 테이블 변경
    let tHeadHtml = "";
    const tHead1 = $("#JHead_1");
    $("#Jlist_1").show();
    $("#Jlist_2").hide();

    $("#JHead_1").empty();
    $("#JBody_1").empty();

    if (articlekndno === "1") {

      // 물품 - 원자재, 부자재, 스페어파트, 설비자재
      // 원자재 : 1000000001
      // 부자재 : 1000000002
      // 스페어파트 : 1000000003
      // 설비자재 : 1000000182
      if (articlegroupid === "1000000001" || articlegroupid === "1000000002" || articlegroupid === "1000000003" || articlegroupid === "1000000182") {
        tHeadHtml += " <tr>";
        tHeadHtml += " 	<th class='center' width='15%'>자재코드</th>";
        // tHeadHtml += " 	<th class='center' width='5%'>이미지</th>";
        tHeadHtml += " 	<th class='center' width='15%'>품명<span class='necessary'>&nbsp;</span></th>";
        tHeadHtml += " 	<th class='center' width='14%'>Vendor p/n</th>";
        tHeadHtml += " 	<th class='center' width='10%'>단위</th>";
        tHeadHtml += " 	<th class='center' width='8%'>규격</th>";
        tHeadHtml += " 	<th class='center' width='8%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
        //물품 - 자산
      } else if (articlegroupid == "1000000004") {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='14%'>자산번호</th>";
        tHeadHtml += "	<th class='center' width='10%'>관리(OA)번호</th>";
        tHeadHtml += "	<th class='center' width='10%'>제조번호(S/N)</th>";
        // tHeadHtml += "	<th class='center' width='10%'>이미지</th>";
        tHeadHtml += "	<th class='center' width='11%'>자산명</th>";
        tHeadHtml += "	<th class='center' width='10%'>부서명</th>";
        tHeadHtml += "	<th class='center' width='6%'>단위</th>";
        tHeadHtml += "	<th class='center' width='6%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='18%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
        //물품 - 원재료/저장품
      } else if (articlegroupid == "1000000142") {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='20%'>SAP Code</th>";
        tHeadHtml += "	<th class='center' width='35%'>품명<span class='necessary'>&nbsp;</span></th>";
        tHeadHtml += "	<th class='center' width='10%'>단위</th>";
        tHeadHtml += "	<th class='center' width='10%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
        //물품 - 전기자재
      } else if (articlegroupid == "1000000156") {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='30%'>품명<span class='necessary'>&nbsp;</span></th>";
        tHeadHtml += "	<th class='center' width='25%'>Serial no.</th>";
        tHeadHtml += "	<th class='center' width='10%'>단위</th>";
        tHeadHtml += "	<th class='center' width='10%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
        //물품 - 기타
      } else if (articlegroupid == "1000000013") {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='35%'>품명<span class='necessary'>&nbsp;</span></th>";
        // tHeadHtml += "	<th class='center' width='10%'>이미지</th>";
        tHeadHtml += "	<th class='center' width='10%'>단위</th>";
        tHeadHtml += "	<th class='center' width='10%'>규격</th>";
        tHeadHtml += "	<th class='center' width='10%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
      } else {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='35%'>품명<span class='necessary'>&nbsp;</span></th>";
        // tHeadHtml += "	<th class='center' width='20%'>이미지</th>";
        tHeadHtml += "	<th class='center' width='10%'>단위</th>";
        tHeadHtml += "	<th class='center' width='10%'>규격</th>";
        tHeadHtml += "	<th class='center' width='10%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='10%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
      }

      // 휴대용 전산장치 시작
    } else if (articlekndno === "2") {
      // 노트북
      if (articlegroupid === "1000000010") {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='13%'>자산번호</th>";
        tHeadHtml += "	<th class='center' width='10%'>관리(OA)번호</th>";
        tHeadHtml += "	<th class='center' width='10%'>제조번호(S/N)</th>";
        // tHeadHtml += "	<th class='center' width='10%'>이미지</th>";
        tHeadHtml += "	<th class='center' width='10%'>물품명<span class='necessary'>&nbsp;</span></th>";
        tHeadHtml += "	<th class='center' width='10%'>부서명</th>";
        tHeadHtml += "	<th class='center' width='6%'>단위</th>";
        tHeadHtml += "	<th class='center' width='6%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
      } else if (articlegroupid === "1000000056" || articlegroupid === "1000000051" || articlegroupid === "1000000057") {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='30%'>품명<span class='necessary'>&nbsp;</span></th>";
        tHeadHtml += "	<th class='center' width='17%'>용량</th>";
        tHeadHtml += "	<th class='center' width='18%'>시리얼넘버</th>";
        tHeadHtml += "	<th class='center' width='10%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
      } else {
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='20%'>사용승인번호</th>";
        tHeadHtml += "	<th class='center' width='35%'>품명<span class='necessary'>&nbsp;</span></th>";
        tHeadHtml += "	<th class='center' width='10%'>용량</th>";
        tHeadHtml += "	<th class='center' width='10%'>비고</th>";
        tHeadHtml += "	<th class='center' width='10%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='10%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
      }

      // 문서
    } else if (articlekndno === "3") {
      tHeadHtml += " <tr>";
      //tHeadHtml += "	<th class='center' width='20%' style='display:none;'>문서번호</th>";
      tHeadHtml += "	<th class='center' width='50%'>문서제목</th>";
      tHeadHtml += "	<th class='center' width='20%'>페이지수</th>";
      tHeadHtml += " 	<th class='center' width='25%'>첨부</th>";
      tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
      tHeadHtml += " </tr>";

      // Wafer/Substrate (NOT USE)
    } else if (articlekndno === "4") {
      tHeadHtml += " <tr>";
      tHeadHtml += " 	<th class='center' width='15%'>자재코드</th>";
      // tHeadHtml += " 	<th class='center' width='5%'>이미지</th>";
      tHeadHtml += " 	<th class='center' width='15%'>품명<span class='necessary'>&nbsp;</span></th>";
      tHeadHtml += " 	<th class='center' width='14%'>Vendor p/n</th>";
      tHeadHtml += " 	<th class='center' width='10%'>단위</th>";
      tHeadHtml += " 	<th class='center' width='8%'>규격</th>";
      tHeadHtml += " 	<th class='center' width='8%'>수량</th>";
      tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
      tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
      tHeadHtml += " </tr>";
    } else if (articlekndno === "5") {
      tHeadHtml += " <tr>";
      tHeadHtml += "	<th class='center' width='35%'>품명<span class='necessary'>&nbsp;</span></th>";
      // tHeadHtml += "	<th class='center' width='20%'>이미지</th>";
      tHeadHtml += "	<th class='center' width='10%'>단위</th>";
      tHeadHtml += "	<th class='center' width='10%'>규격</th>";
      tHeadHtml += "	<th class='center' width='10%'>수량</th>";
      tHeadHtml += " 	<th class='center' width='10%'>첨부</th>";
      tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
      tHeadHtml += " </tr>";
    } else if (articlekndno === "6") {

      if (articlegroupid === "1000000001") { //원자재
        tHeadHtml += " <tr>";
        tHeadHtml += " 	<th class='center' width='15%'>자재코드</th>";
        // tHeadHtml += " 	<th class='center' width='5%'>이미지</th>";
        tHeadHtml += " 	<th class='center' width='15%'>품명<span class='necessary'>&nbsp;</span></th>";
        tHeadHtml += " 	<th class='center' width='14%'>Vendor p/n</th>";
        tHeadHtml += " 	<th class='center' width='10%'>단위</th>";
        tHeadHtml += " 	<th class='center' width='8%'>규격</th>";
        tHeadHtml += " 	<th class='center' width='8%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='20%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
      } else {	//완제품(PRODUCT) PKG CHIP_PKG물품(품질관련) Sample(Wafer/Substrate제외) MODULE_DRAM MODULE_SSD
        tHeadHtml += " <tr>";
        tHeadHtml += "	<th class='center' width='35%'>품명<span class='necessary'>&nbsp;</span></th>";
        // tHeadHtml += "	<th class='center' width='10%'>이미지</th>";
        tHeadHtml += "	<th class='center' width='10%'>단위</th>";
        tHeadHtml += "	<th class='center' width='10%'>규격</th>";
        tHeadHtml += "	<th class='center' width='10%'>수량</th>";
        tHeadHtml += " 	<th class='center' width='10%'>첨부</th>";
        tHeadHtml += " 	<th class='center' width='15%'>물품속성</th>";
        tHeadHtml += " 	<th class='center' width='5%'>삭제</th>";
        tHeadHtml += " </tr>";
      }
    }

    tHead1.html(tHeadHtml);
    Inoutwrite.fn_addArticleItemRow();
  },

  // 물품내역 행 삭제
  fn_removeAtricleItemRow: function (obj) {
    const articleTable = obj.parentNode.parentNode.parentNode;
    const totalTrCount = articleTable.rows.length;

    if (totalTrCount < 2) {
      alert("물품내역은 1개 이상 등록하셔야 합니다.");
      return false;
    }
    if (confirm("삭제하시겠습니까?")) {
      const trIndex = obj.parentNode.parentNode.rowIndex - 1;
      articleTable.deleteRow(trIndex);
    }
  },

  // 물품내역 행 추가
  fn_addArticleItemRow: function (props) {
    let articlekndno = "";
    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    const articlegroupid = $("#articlegroupid option:selected").val();

    // 물품 내역 등록 테이블 변경
    let tBodyHtml = "";
    const tBody1 = $("#JBody_1");

    if (articlekndno === "1") {
      // 물품 - 원자재, 부자재, 스페어파트, 설비자재
      // 원자재 : 1000000001
      // 부자재 : 1000000002
      // 스페어파트 : 1000000003
      // 설비자재 : 1000000182
      if (articlegroupid === "1000000001" || articlegroupid === "1000000002" || articlegroupid === "1000000003" || articlegroupid === "1000000182") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'>";
        tBodyHtml += " 		<input type='text' id='serialno" + rowId + "' name='serialno' value='' size='8' />";
        tBodyHtml += "	</td>";
        // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH'   value=''><div id='DivImagePath"+rowId+"'></div></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='14' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='venderpn" + rowId + "' 	name='venderpn'    value='' size='14' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='asize" + rowId + "' 	 name='asize' 	 value='' size='5' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='5' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "' 		    name='fileToUpload'           size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath'               value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename'               value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid'                 value='' />";
        tBodyHtml += " 		<input type='hidden' id='" + rowId + "' 			      name=''              value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId' 		 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid'              value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid'   value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	        value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				          value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				          value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			        value='' />";
        tBodyHtml += " 		<input type='hidden' id='" + rowId + "'            name='' 			        value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'              value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";

        //물품 - 자산
      } else if (articlegroupid === "1000000004") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'>";
        tBodyHtml += " 		<input type='text' id='jsno" + rowId + "' name='jsno' size='8' />";
        tBodyHtml += "	</td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='text' id='mmno" + rowId + "' name='mmno' size='8' />";
        tBodyHtml += "	</td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='text' id='serialno" + rowId + "' name='serialno' size='8' />";
        tBodyHtml += "	</td>";
        // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH' value=''><div id='DivImagePath"+rowId+"'></div></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='articlename" + rowId + "' name='articlename' size='10' value='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='deptname" + rowId + "' 	name='deptname'    size='9'  value='' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:40px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='4' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload'         size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
        tBodyHtml += " 		<input type='hidden' id='" + rowId + "' 			              name='writeaseq' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId'           value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept'          value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "' 			      name='venderpn' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='asize" + rowId + "' 				        name='asize' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";

        //물품 - 원재료/저장품
      } else if (articlegroupid === "1000000142") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'>";
        tBodyHtml += " 		<input type='text' id='materialId" + rowId + "' name='materialId' size='15' />";
        tBodyHtml += "	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='articlename" + rowId + "' name='articlename' size='40' value='' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='9' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload'         size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += "		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
        tBodyHtml += "		<input type='hidden' id='writeaseq" + rowId + "' 			      name='writeaseq' 			      value='' />";
        tBodyHtml += "		<input type='hidden' id='serialno" + rowId + "' 			      name='serialno' 			      value='' />";
        tBodyHtml += "		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			      value='0' />";
        tBodyHtml += "		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	    value='' />";
        tBodyHtml += "		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += "		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	    value='' />";
        tBodyHtml += "		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
        tBodyHtml += "		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
        tBodyHtml += "		<input type='hidden' id='venderpn" + rowId + "' 			      name='venderpn' 			      value='' />";
        tBodyHtml += "		<input type='hidden' id='asize" + rowId + "' 				        name='asize' 				        value='' />";
        tBodyHtml += "		<input type='hidden' id='imagepath" + rowId + "' 			      name='imagepath' 			      value='' />";
        // tBodyHtml += "		<div id='DivImagePath'></div>";
        tBodyHtml += "		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				        value='' />";
        tBodyHtml += "		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				        value='' />";
        tBodyHtml += "		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			      value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";

        //물품 - 전기자재
      } else if (articlegroupid === "1000000156") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'><input type='text' id='jasannoknd" + rowId + "' name='articlename' value='' size='50' maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='jasannoknd" + rowId + "' name='serialno' value='' size='30' maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='UNITNAME' style='width:50px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='jasannoknd" + rowId + "' name='inoutcnt' value='' size='9' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload'         size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='unitname" + rowId + "'             name='unitname' 		 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "'           name='jasannoknd' 		      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "'           name='materialId' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "'            name='writeaseq' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "'            name='articleid' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "'          name='articlecode' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "'          name='installdept' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "'           name='chargename' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "'              name='usename' 			        value='' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "'             name='venderpn' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='asize" + rowId + "'                name='asize' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "'                 name='jsno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "'                 name='mmno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "'             name='deptname' 			      value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";

        //물품 - 기타
      } else if (articlegroupid === "1000000013") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='35' maxlength='' />";
        // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH'  value=''><div id='DivImagePath'></div></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'>";
        tbodyhtml += " 		<input type='text' id='asize" + rowId + "' name='asize' value='' size='9' maxlength='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='9' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload'         size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "' 			      name='writeaseq' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "' 			      name='venderpn' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='serialno" + rowId + "' 			      name='serialno' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";
      } else {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='35' maxlength='' />";
        // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH' value=''><div id='DivImagePath'></div></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='asize" + rowId + "' name='asize' value='' size='9' maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='9' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload' size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "' 			      name='writeaseq' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "' 			      name='venderpn' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='serialno" + rowId + "' 			      name='serialno' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";
      }

      // 휴대용 전산장치 시작
    } else if (articlekndno === "2") {
      // 노트북
      if (articlegroupid === "1000000010") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'>";
        tBodyHtml += " 		<input type='text' id='jsno" + rowId + "' name='jsno' size='7' />";
        tBodyHtml += "	</td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='text' id='mmno" + rowId + "' name='mmno' size='7' />";
        tBodyHtml += "	</td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='text' id='serialno" + rowId + "' name='serialno' size='7' />";
        tBodyHtml += "	</td>";
        // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH' value=''><div id='DivImagePath'></div></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='10' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='deptname" + rowId + "' name='deptname' value='' size='9' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:40px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='4' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload' size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "'           name='jasannoknd' 			    value='0' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "'            name='writeaseq' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "'           name='materialId' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "'            name='articleid' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "'          name='articlecode' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "'          name='installdept' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "'           name='chargename' 			    value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "'              name='usename' 			        value='' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "'             name='venderpn' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='asize" + rowId + "'                name='asize' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";

        // 기타(장비용, 실장용 노트북) 1000000056
      } else if (articlegroupid === "1000000056" || articlegroupid === "1000000051" || articlegroupid === "1000000057") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='35' maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='unitname" + rowId + "' name='unitname' value='' size='15' maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='serialno" + rowId + "' name='serialno' value='' size='15' maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='9' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "' name='fileToUpload' size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			name='FILEPATH' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			name='FILENAME' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				name='FILEID' 			 	 value='' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "' name='writeaseq' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' name='materialId' 		 value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' name='articleid' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' name='articlecode' 		 value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' name='installdept' 		 value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' name='chargename' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' name='usename' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "' name='venderpn' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='JASANNOKND" + rowId + "' name='JASANNOKND' 			 value='0' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' name='jsno' 				 value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' name='mmno' 				 value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' name='deptname' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "' name='imagepath' 			 value='' />";
        tBodyHtml += " 		<input type='hidden' id='asize" + rowId + "' name='asize' 			 value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";
      } else {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'><input type='text' id='serialno" + rowId + "' name='serialno' size='15' value='' />";
        tBodyHtml += " 	<td class='center'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='43' maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='unitname" + rowId + "' name='unitname' 	 value='' size='9'  maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='asize" + rowId + "' name='asize' 		 value='' size='9'  maxlength='' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' 	 value='' size='9' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload' size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "'            name='writeaseq' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "'           name='materialId' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "'            name='articleid' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "'          name='articlecode' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "'          name='installdept' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "'           name='chargename' 			    value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "'              name='usename' 			        value='' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "'             name='venderpn' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "'           name='jasannoknd' 			    value='0' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "'                 name='jsno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "'                 name='mmno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "'             name='deptname' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='asize" + rowId + "' 				        name='asize' 				        value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";
      }

      // 문서
    } else if (articlekndno === "3") {
      tBodyHtml += " <tr>";
      // 설계공정 자료이면서 내용등급이 대량출력-업무(비밀)일 경우에만
      tBodyHtml += " 	<td class='first' style='display:none;'><input type='text' id='serialno" + rowId + "' name='serialno' value='' size='22' maxlength='' /></td>";
      tBodyHtml += " 	<td class='first'><input type='text' id='unitname" + rowId + "' name='unitname' value='' size='50' maxlength='' /></td>";
      tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='9' /></td>";
      tBodyHtml += " 	<td class='center'>";
      tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "'         name='fileToUpload'         size='5' />";
      tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "'             name='filepath' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
      tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "'            name='writeaseq' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "'           name='materialId' 		      value='' />";
      tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "'            name='articleid' 			      value='0' />";
      tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "'          name='articlecode' 		      value='' />";
      tBodyHtml += " 		<input type='hidden' id='articlename" + rowId + "'          name='articlename' 		      value='' />";
      tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
      tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "'          name='installdept'    		  value='' />";
      tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "'           name='chargename'     			value='' />";
      tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "'              name='usename' 		    	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "'             name='venderpn' 	    	    value='' />";
      tBodyHtml += " 		<inpu type='hidden' id='asize" + rowId + "'                name='asize' 			    	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath' 	    	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "'           name='jasannoknd'     	    value='0' />";
      tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "'                 name='jsno' 			    	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "'                 name='mmno' 			    	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "'             name='deptname' 	    	    value='' />";
      tBodyHtml += " 	</td>";
      tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
      tBodyHtml += " </tr>";

      Inoutwrite.fn_documentKnd();

      // Wafer/Substrate	(NO USE)
    } else if (articlekndno === "4") {
      tBodyHtml += " <tr>";
      tBodyHtml += " 	<td class='first'>";
      tBodyHtml += " 		<input type='text' id='serialno" + rowId + "' name='serialno' size='8' />";
      tBodyHtml += "	</td>";
      // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH'   value=''><div id='DivImagePath"+rowId+"'></div></td>";
      tBodyHtml += " 	<td class='center'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='14' /></td>";
      tBodyHtml += " 	<td class='center'><input type='text' id='venderpn" + rowId + "' 	name='venderpn'    value='' size='14' /></td>";
      tBodyHtml += " 	<td class='center'>";
      tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
      tBodyHtml += " 			<option value=''>-</option>";
      tBodyHtml += " 			<option value='EA'>EA</option>";
      tBodyHtml += " 			<option value='BOX'>BOX</option>";
      tBodyHtml += " 			<option value='SET'>SET</option>";
      tBodyHtml += " 			<option value='PALLET'>파레트</option>";
      tBodyHtml += " 			<option value='Kg'>Kg</option>";
      tBodyHtml += " 			<option value='L'>리터</option>";
      tBodyHtml += " 			<option value='M'>미터</option>";
      tBodyHtml += " 		</select>";
      tBodyHtml += " 	</td>";
      tBodyHtml += " 	<td class='center'><input type='text' id='asize" + rowId + "' 	 name='asize' 	 value='' size='5' /></td>";
      tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='5' /></td>";
      tBodyHtml += " 	<td class='center'>";
      tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "' 		    name='fileToUpload' size='5' />";
      tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
      tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "' 			      name='writeaseq' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId' 		 	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			      value='0' />";
      tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
      tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
      tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
      tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
      tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				        value='' />";
      tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				        value='' />";
      tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
      tBodyHtml += " 	</td>";
      tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
      tBodyHtml += " </tr>";
    } else if (articlekndno === "5") {
      tBodyHtml += " <tr>";
      tBodyHtml += " 	<td class='first'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='35' maxlength='' />";
      // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH' value=''><div id='DivImagePath'></div></td>";
      tBodyHtml += " 	<td class='center'>";
      tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
      tBodyHtml += " 			<option value=''>-</option>";
      tBodyHtml += " 			<option value='EA'>EA</option>";
      tBodyHtml += " 			<option value='BOX'>BOX</option>";
      tBodyHtml += " 			<option value='SET'>SET</option>";
      tBodyHtml += " 			<option value='PALLET'>파레트</option>";
      tBodyHtml += " 			<option value='Kg'>Kg</option>";
      tBodyHtml += " 			<option value='L'>리터</option>";
      tBodyHtml += " 			<option value='M'>미터</option>";
      tBodyHtml += " 		</select>";
      tBodyHtml += " 	</td>";
      tBodyHtml += " 	<td class='center'><input type='text' id='asize" + rowId + "' name='asize' value='' size='9' maxlength='' /></td>";
      tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='9' /></td>";
      tBodyHtml += " 	<td class='center'>";
      tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "' name='fileToUpload' size='5' />";
      tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			 value='' />";
      tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			 value='' />";
      tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	 value='' />";
      tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "' 			      name='writeaseq' 			 value='' />";
      tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId' 		 	 value=''  />";
      tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			 value='0' />";
      tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	 value=''  />";
      tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
      tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	    value='' />";
      tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
      tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
      tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "' 			      name='venderpn' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='serialno" + rowId + "' 			      name='serialno' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
      tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				        value='' />";
      tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				        value='' />";
      tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			      value='' />";
      tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
      tBodyHtml += " 	</td>";
      tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
      tBodyHtml += " </tr>";
    } else if (articlekndno === "6") {
      // 보세자산 반출입 20140512 from 윤석훈
      // 변경 내용 : ARTICLEKNDNO == "6" 추가
      // 물품 - 원자재/완제품/PKG CHIP
      // 원자재 : 1000000001
      // 완제품(PRODUCT) : 1000000172
      // PKG CHIP_PKG물품(품질관련) : 1000000175
      // Sample(Wafer/Substrate제외) : 10000184
      // MODULE_DRAM : 10000185
      // MODULE_SSD : 10000186

      if (articlegroupid === "1000000001") {
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'>";
        tBodyHtml += " 		<input type='text' id='serialno" + rowId + "' name='serialno' size='8' />";
        tBodyHtml += "	</td>";
        // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH'   value=''><div id='DivImagePath"+rowId+"'></div></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='14' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='venderpn" + rowId + "' 	name='venderpn'    value='' size='14' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='asize" + rowId + "' 	 name='asize' 	 value='' size='5' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='5' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "' 		    name='fileToUpload'   size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "' 			      name='writeaseq' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId' 		      value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";
      } else { //dh
        tBodyHtml += " <tr>";
        tBodyHtml += " 	<td class='first'><input type='text' id='articlename" + rowId + "' name='articlename' value='' size='35' maxlength='' />";
        // tBodyHtml += " 	<td class='center'><input type='hidden' id='IMAGEPATH"+rowId+"' name='IMAGEPATH' value=''><div id='DivImagePath'></div></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='unitname" + rowId + "' name='unitname' style='width:50px;'>";
        tBodyHtml += " 			<option value=''>-</option>";
        tBodyHtml += " 			<option value='EA'>EA</option>";
        tBodyHtml += " 			<option value='BOX'>BOX</option>";
        tBodyHtml += " 			<option value='SET'>SET</option>";
        tBodyHtml += " 			<option value='PALLET'>파레트</option>";
        tBodyHtml += " 			<option value='Kg'>Kg</option>";
        tBodyHtml += " 			<option value='L'>리터</option>";
        tBodyHtml += " 			<option value='M'>미터</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='asize" + rowId + "' 	 name='asize' 	 value='' size='5' /></td>";
        tBodyHtml += " 	<td class='center'><input type='text' id='inoutcnt" + rowId + "' name='inoutcnt' value='' size='5' /></td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<input type='file'   id='fileToUpload" + rowId + "' 		name='fileToUpload' size='5' />";
        tBodyHtml += " 		<input type='hidden' id='filepath" + rowId + "' 			      name='filepath' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='filename" + rowId + "' 			      name='filename' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='fileid" + rowId + "' 				      name='fileid' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='writeaseq" + rowId + "' 			      name='writeaseq' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='materialId" + rowId + "' 			    name='materialId' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articleid" + rowId + "' 			      name='articleid' 			      value='0' />";
        tBodyHtml += " 		<input type='hidden' id='articlecode" + rowId + "' 			    name='articlecode' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='articlecomputerizeid" + rowId + "' name='articlecomputerizeid' value='0' />";
        tBodyHtml += " 		<input type='hidden' id='installdept" + rowId + "' 			    name='installdept' 		 	    value='' />";
        tBodyHtml += " 		<input type='hidden' id='chargename" + rowId + "' 			    name='chargename' 			    value='' />";
        tBodyHtml += " 		<input type='hidden' id='usename" + rowId + "' 				      name='usename' 			 	      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jasannoknd" + rowId + "' 			    name='jasannoknd' 			    value='0' />";
        tBodyHtml += " 		<input type='hidden' id='venderpn" + rowId + "' 			      name='venderpn' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='serialno" + rowId + "' 			      name='serialno' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='jsno" + rowId + "' 				        name='jsno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='mmno" + rowId + "' 				        name='mmno' 				        value='' />";
        tBodyHtml += " 		<input type='hidden' id='deptname" + rowId + "' 			      name='deptname' 			      value='' />";
        tBodyHtml += " 		<input type='hidden' id='imagepath" + rowId + "'            name='imagepath'            value='' />";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'>";
        tBodyHtml += " 		<select id='attribute" + rowId + "' name='attribute' style='width:120px;'>";
        tBodyHtml += " 			<option value='select'>선택</option>";
        tBodyHtml += " 			<option value='DRAM'>DRAM-IC(PKG CHIP)</option>";
        tBodyHtml += " 			<option value='NAND'>NAND-IC(PKG CHIP)</option>";
        tBodyHtml += " 			<option value='MODULE'>MODULE</option>";
        tBodyHtml += " 			<option value='SSD'>SSD</option>";
        tBodyHtml += " 			<option value='ETC'>기타</option>";
        tBodyHtml += " 		</select>";
        tBodyHtml += " 	</td>";
        tBodyHtml += " 	<td class='center'><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' border='0' style='cursor:pointer;' onclick='Inoutwrite.fn_removeAtricleItemRow(this);' /></td>";
        tBodyHtml += " </tr>";
      }
    }
    tBody1.append(tBodyHtml);

    // set data
    if (props) {
      const {
        writeaseq = "",
        articlename = "",
        materialId = "",
        articleid = "",
        articlecomputerizeid = "",
        serialno = "",
        inoutcnt = "",
        unitname = "",
        asize = "",
        imagepath = "",
        installdept = "",
        chargename = "",
        usename = "",
        venderpn = "",
        jsno = "",
        mmno = "",
        deptname = "",
        filepath = "",
        filename = "",
        fileid = "",
        attribute = "",
      } = props1;

      tBody1.find("input[name=writeaseq]").val(writeaseq);
      tBody1.find("input[name=articlename]").val(articlename);
      tBody1.find("input[name=materialId]").val(materialId);
      tBody1.find("input[name=articleid]").val(articleid);
      tBody1.find("input[name=articlecomputerizeid]").val(articlecomputerizeid);
      tBody1.find("input[name=serialno]").val(serialno);
      tBody1.find("input[name=inoutcnt]").val($.esutils.setComma(inoutcnt));
      tBody1.find("select[name=unitname]").val(unitname);
      tBody1.find("input[name=asize]").val(asize);
      tBody1.find("input[name=imagepath]").val(imagepath);
      tBody1.find("input[name=installdept]").val(installdept);
      tBody1.find("input[name=chargename]").val(chargename);
      tBody1.find("input[name=usename]").val(usename);
      tBody1.find("input[name=venderpn]").val(venderpn);
      tBody1.find("input[name=jsno]").val(jsno);
      tBody1.find("input[name=mmno]").val(mmno);
      tBody1.find("input[name=deptname]").val(deptname);
      tBody1.find("input[name=filepath]").val(filepath);
      tBody1.find("input[name=filename]").val(filename);
      tBody1.find("input[name=fileid]").val(fileid);
      tBody1.find("select[name=attribute]").val(attribute);
    }

    $("input:text[name='inoutcnt']").keyup(function (event) {
      $(this).val($(this).val().replace(/[^0-9\.]/g, ''));
      const com_index = $(this).val().indexOf(".");
      const str_len = $(this).val().length;
      if (com_index === 0) {
        $(this).val("0" + $(this).val());
      }
      $(this).val($.esutils.setComma($(this).val()));
    }).blur(function (event) {
      const com_index = $(this).val().indexOf(".");
      const str_len = $(this).val().length;
      if (com_index > 0 && str_len === com_index + 1) {
        $(this).val($(this).val() + "0");
      }
    });

    rowId++;
  },

  // validation
  fn_validation: function () {

    if ($("textarea[name='canceletc']").val().getBytes3() > (250 * 3)) {
      alert("사유는 250자 이내로 작성하세요.");
      $("textarea[name='canceletc']").focus();
      return;
    }

    let articlekndno = "";
    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") === "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    if ($("#empTel").val() === null || $("#empTel") === "") {
      alert("사내번호를 입력주세요");
      $("#empTel").focus();
      return;
    }

    const articlegroupid = $("#articlegroupid option:selected").val();
    if (articlegroupid === null || articlegroupid === "") {
      alert("품목그룹을 선택하여 주세요");
      $("#articlegroupid").focus();
      return;
    }

    const outreasonid = $("#outreasonid").val();
    // 물품 OR Wafer/Substrate
    if (articlekndno === "1" || articlekndno === "4" || articlekndno === "5" || articlekndno === "6") {
      if (outreasonid === null || outreasonid === "") {
        alert("반출사유를 선택하여 주세요.");
        $("#outreasonid").focus();
        return;
      }

      const outreasonsubknd = $("#outreasonsubknd").val();
      // 수리(유상, 긴급반출):자재전표 문서번호 입력
      if (outreasonid === "D0000195") {
        if (outreasonsubknd === null || outreasonsubknd === "") {
          alert("반출상세사유를 선택하여 주세요.");
          $("#outreasonsubknd").focus();
          return;
        }
      }
    }

    const prno = $("#prno").val();
    if (articlekndno === "1" || articlekndno === "6") {
      // D0000012 : 판매
      // D0000195 : 수리(유상, 긴급반출):자재전표 문서번호 입력
      // D0000199 : 세정
      if (outreasonid === "D0000012" || outreasonid === "D0000195" || outreasonid === "D0000199") {
        /*
        if( prno == null || prno == "" ) {
          alert("PR번호나 그룹웨어 수리요청서 문서번호를 입력하세요.");
          $("#prno").focus();
          return;
        }
        */
      }
    }

    const outcompanyknd = $("#outcompanyknd").val();
    const mycompanyno = $("#mycompanyno").val();
    const companyno = $("#companyno").val();
    const systempartnerid = $("#systempartnerid").val();
    const foreignComp = $("#foreignComp").val();
    if (outcompanyknd === "1") {
      if (mycompanyno === null || mycompanyno === "") {
        alert("상대처를 선택하여 주세요.");
        $("#mycompanyno").focus();
        return;
      }
    } else if (outcompanyknd === "2") {
      if (systempartnerid === null || systempartnerid === "") {
        alert("상대처를 선택하여 주세요.");
        $("#systempartnernm").focus();
        return;
      }
    } else if (outcompanyknd === "3") {
      if (foreignComp === null || foreignComp === "") {
        alert("상대처를 선택하여 주세요.");
        $("#foreignComp").focus();
        return;
      }
    }

    if (companyno === mycompanyno) {
      //alert("반출사업장과 상대처가 같은 경우 등록이 불가능합니다.\n같은 경우에는 건물이동등록함을 이용하세요.");
      alert("반출사업장과 상대처가 같은 경우 등록이 불가능합니다.");
      $("#mycompanyno").focus();
      return;
    }

    const outcompanydeptCd = $("#outcompanydeptCd").val();
    if (outcompanyknd === "1") {
      //if(articlekndno != "4"){
      if (outcompanydeptCd == null || outcompanydeptCd == "") {
        alert("상대처부서를 선택하여 주세요.");
        $("#outcompanydeptNm").focus();
        return;
      }
      //}
    }

    const inoutknd = $("#inoutknd").val();
    if (inoutknd === null || inoutknd === "") {
      alert("반출구분을 선택하여 주세요.");
      $("#inoutknd").focus();
      return;
    }

    let indate = $("#indate").val();
    const toDay = $.esutils.getToday('-');

    if (inoutknd === "1") {
      const returncompanyareaknd = $("#returncompanyareaknd").val();
      if (returncompanyareaknd === "" || returncompanyareaknd === null) {
        alert("반출구분이 반입필요일 경우 최종반입사업장을 지정하세요.");
        $("#returncompanyareaknd").focus();
        return;
      }

      if (indate === null || indate === "") {
        alert("반출구분이 반입필요일 경우 반입예정일자를 넣어주세요");
        $("#indate").focus();
        return;
      }

      indate = replaceAll(indate, '-', '');
      if (toDay > indate) {
        alert("반입예정일자는 오늘날짜 이후로 지정하세요.");
        $("#indate").val("");
        $("#indate").focus();
        return;
      }

      const ioDateDiffMonth = $.esutils.getDaysBetweenMonths(toDay, indate, "-");
      let sendYn = "";
      $("input:checkbox[name='sendYn']").each(function (index, value) {
        if ($(this).attr("checked") == "checked") {
          sendYn = $(this).attr("value");
        }
      });

      if (sendYn === "Y") {
        /*
        if( $("#fileToUpload_send").val() == null || $("#fileToUpload_send").val() == "" ){
          alert("주재원 또는 파견일 경우는 증빙서류를 첨부하세요");
          $("#fileToUpload_send").focus();
          return;
        }
        */
        if (ioDateDiffMonth > 49) {
          alert("반입예정일자는 4년 이내로 설정하세요.");
          $("#indate").val("");
          $("#indate").focus();
          return;
        }
      } else {
        if (ioDateDiffMonth > 12) {
          alert("반입예정일자는 12개월 이내로 설정하세요.");
          $("#indate").val("");
          $("#indate").focus();
          return;
        }
      }
    }

    const inoutetc = $("#inoutetc").val();
    if (articlekndno === "1" || articlekndno === "4" || articlekndno === "5" || articlekndno === "6") {
      if (inoutetc === null || inoutetc === "" || $("#inoutetc").val().length < 10) {
        alert("기타상세반출사유를 10자이상 입력하세요.");
        $("#inoutetc").focus();
        return;
      }
      if ($("#inoutetc").val().length > 500) {
        alert("기타상세반출사유는 500자 이내로 작성하세요.");
        $("#inoutetc").focus();
        return false;
      }

    } else if (articlekndno === "2" || $("#inoutetc").val().length < 10) {
      if (inoutetc === null || inoutetc === "") {
        alert("반출사유 및 반출List(화일명)를 10자이상 입력하세요.");
        $("#inoutetc").focus();
        return;
      }
      if ($("#inoutetc").val().length > 500) {
        alert("반출사유 및 반출List(화일명)는500자 이내로 작성하세요.");
        $("#inoutetc").focus();
        return false;
      }

    } else if (articlekndno === "3") {
      if (inoutetc === null || inoutetc === "" || $("#inoutetc").val().length < 10) {
        alert("반출사유 및<br />문서List를 10자이상 입력하세요.");
        $("#inoutknd").focus();
        return;
      }
      if ($("#inoutetc").val().length > 500 || $("#inoutetc").val().length < 10) {
        alert("반출사유 및<br />문서List는500자 이내로 작성하세요.");
        $("#inoutetc").focus();
        return false;
      }
    }

    const copurl = $("#copurl").val();
    if (articlekndno === "4" || articlekndno === "5") {
      if (outcompanyknd === "2") {
        //URL 유효성 Check 2013.12.23
        //	if(checkDetailUrl(COPURL) == false){
        //		alert("Cop 등록정보가 유효하지 않은 URL 형식입니다. (http:// 형태로 입력해주세요.)")
        //		return;
        //	}
        if (copurl === null || copurl === "") {
          alert("CoP URL을 입력하세요.");
          $("#copurl").focus();
          return;
        }
        if ($("#copurl").val().length > 500) {
          alert("CoP URL은 500자 이내로 작성하세요.");
          $("#copurl").focus();
          return false;
        }
      }
    }
    const articlenameList = document.getElementsByName("articlename");
    const materialIdList = document.getElementsByName("materialId");
    const serialnoList = document.getElementsByName("serialno");
    const inoutcntList = document.getElementsByName("inoutcnt");
    const unitnameList = document.getElementsByName("unitname");
    const fileToUploadList = document.getElementsByName("fileToUpload");

    const writeaseqList = document.getElementsByName("writeaseq");

    const attributeList = document.getElementsByName("attribute");

    if (articlenameList != null) {
      let rowCount = 0;

      for (let i = 0; i < articlenameList.length; i++) {
        const materialId = materialIdList[i].value;
        const articlename = articlenameList[i].value;
        const serialno = serialnoList[i].value;
        const inoutcnt = inoutcntList[i].value;
        const unitname = unitnameList[i].value;
        const fileToUpload = fileToUploadList[i].value;
        let attribute = "";
        rowCount = i + 1;

        if (articlekndno === "1") {
          // 물품 - 원자재, 부자재, 스페어파트, 설비자재
          // 원자재 : 1000000001
          // 부자재 : 1000000002
          // 스페어파트 : 1000000003
          // 설비자재 : 1000000182
          if (articlegroupid === "1000000001" || articlegroupid === "1000000002" || articlegroupid === "1000000003") {
            /*
            if( materialId == null || materialId == "" ) {
              alert("["+rowCount+"]번째 줄에 자재코드를 입력하지 않으셨습니다.\n\n버튼을 클릭한 후 팝업창에서 자재코드를 지정하시거나 또는 그 줄을 삭제하십시요.");
              return;
            }
            */
          } else if (articlegroupid === "1000000182") {
            /*
            if( materialId === null || materialId === "" ) {
              alert("["+rowCount+"]번째 줄에 자산번호를 입력하지 않으셨습니다.\n\n버튼을 클릭한 후 팝업창에서 자산번호를 지정하시거나 또는 그 줄을 삭제하십시요.");
              return;
            }
            */
          } else {
            if (articlename === null || articlename === "") {
              alert("[" + rowCount + "]번째 줄에 품명을 입력하지 않으셨습니다. \n\n입력박스에 품명을 입력하고 [ENTER]를 치신 후 품명리스트가 조회되면 선택하여 사용하십시요. \n\n목록이 없을 시에는 품명만 입력하신 후 등록하십시요.");
              return;
            }
          }
          if (unitname == null || unitname == "") {
            alert("[" + rowCount + "]번째 줄에 단위를 선택하지 않으셨습니다.\n\n단위를 선택해 주세요");
            return;
          }
        } else if (articlekndno === "2") {
          // 노트북
          if (articlegroupid === "1000000010") {
            /*
            if( materialId === null || materialId === "" ) {
              alert("["+rowCount+"]번째 줄에 자산번호를 입력하지 않으셨습니다.\n\n버튼을 클릭한 후 팝업창에서 자산번호를 지정하시거나 또는 그 줄을 삭제하십시요.");
              return;
            }
            */
            if (unitname === null || unitname === "") {
              alert("[" + rowCount + "]번째 줄에 단위를 선택하지 않으셨습니다.\n\n단위를 선택해 주세요");
              return;
            }
            // 기타(장비용, 실장용 노트북) 1000000056
          } else if (articlegroupid === "1000000056" || articlegroupid === "1000000051") {
            if (articlename === null || articlename === "") {
              alert("[" + rowCount + "]번째 줄에 품명을 입력하지 않으셨습니다.\n\n품명을 입력하시거나 또는 그 줄을 삭제하십시요.");
              return;
            }
            if (serialno === null || serialno === "") {
              alert("[" + rowCount + "]번째 줄에 시리얼넘버를 지정하지 않으셨습니다.\n\n시리얼넘버를 입력하시거나 또는 그 줄을 삭제하십시요.");
              return;
            }
          } else {
            if (serialno === null || serialno === "") {
              alert("[" + rowCount + "]번째 줄에 [사용승인번호]를 지정하지 않으셨습니다. \n\n[ENTER]를 친 후 팝업창에서 관리번호를 지정하시거나 또는 그 줄을 삭제하십시요.");
              return;
            }
          }
        } else if (articlekndno === "3") {
          //if( serialno == null || serialno == "" ) {
          //alert("["+rowCount+"]번째 줄에 [문서번호]를 입력하지 않으셨습니다. \n\n[문서번호]를 입력하시거나 또는 그 줄을 삭제하십시요.");
          //return;
          //}
          if (unitname === null || unitname === "") {
            alert("[" + rowCount + "]번째 줄에 [문서제목]을 입력하지 않으셨습니다. \n\n[문서제목]을 입력하시거나 또는 그 줄을 삭제하십시요.");
            return;
          }
        } else if (articlekndno === "4") {
          if (materialId === null || materialId === "") {
            alert("[" + rowCount + "]번째 줄에 자재코드를 입력하지 않으셨습니다.\n\n버튼을 클릭한 후 팝업창에서 자재코드를 지정하시거나 또는 그 줄을 삭제하십시요.");
            return;
          }
          if (unitname === null || unitname === "") {
            alert("[" + rowCount + "]번째 줄에 단위를 선택하지 않으셨습니다.\n\n단위를 선택해 주세요");
            return;
          }
        } else if (articlekndno === "5") {
          /*
          if( articlename === null || articlename === "" ) {
            alert("["+rowCount+"]번째 줄에 품명을 입력하지 않으셨습니다. \n\n입력박스에 품명을 입력하고 [ENTER]를 치신 후 품명리스트가 조회되면 선택하여 사용하십시요. \n\n목록이 없을 시에는 품명만 입력하신 후 등록하십시요.");
            return;
          }
          */
          if (unitname === null || unitname === "") {
            alert("[" + rowCount + "]번째 줄에 단위를 선택하지 않으셨습니다.\n\n단위를 선택해 주세요");
            return;
          }
        } else if (articlekndno === "6") { //보세 자산 반출입 추가 20140512 from 윤석훈

          if (articlegroupid === "1000000001") {
            /*
              if( materialId === null || materialId === "" ) {
                alert("["+rowCount+"]번째 줄에 자재코드를 입력하지 않으셨습니다.\n\n버튼을 클릭한 후 팝업창에서 자재코드를 지정하시거나 또는 그 줄을 삭제하십시요.");
                return;
              }
            */
          }
          /*
          if( articlename === null || articlename === "" ) {
            alert("["+rowCount+"]번째 줄에 품명을 입력하지 않으셨습니다. \n\n입력박스에 품명을 입력하고 [ENTER]를 치신 후 품명리스트가 조회되면 선택하여 사용하십시요. \n\n목록이 없을 시에는 품명만 입력하신 후 등록하십시요.");
            return;
          }
          */
          if (articlegroupid != "1000000001") {
            attribute = attributeList[i].value;
            if (attribute === null || attribute === "" || attribute === "select") {
              alert("[" + rowCount + "]번째 줄에 물품 속성을 선택 하지 않으셨습니다. \n\n 해당 물품속성을 선택하여 사용하십시요..");
              return;
            }
          }
          if (unitname === null || unitname === "") {
            alert("[" + rowCount + "]번째 줄에 단위를 선택하지 않으셨습니다.\n\n단위를 선택해 주세요");
            return;
          }
        }

        if (inoutcnt === null || inoutcnt === "" || inoutcnt === "0") {
          alert("[" + rowCount + "]번째 줄에 수량을 입력하여 주십시오.");
          return;
        }
        if (unitname === 'SET') {
          if (fileToUpload === null || fileToUpload === "") {
            alert("[" + rowCount + "]번째 줄에 파일을 첨부하세요.\n\n단위가 'SET'인경우  Packing List가 꼭 첨부되어야 합니다.");
            return;
          }
        }
        if (articlekndno === "3") {
          if (fileToUpload === null || fileToUpload === "") {
            alert("[" + rowCount + "]번째 줄에 파일을 첨부하세요.\n\n반출하려는 문서가 첨부되어야 합니다.");
            return;
          }
        }
      }
    }

    return true;
  }
  ,

  // 저장/상신
  fn_approval: function (SAVE_TYPE) {
    // if (Inoutwrite.fn_validation()) {

    if (SAVE_TYPE === "REPORT") {
      const {savedRequestApproverLine, savedPermitApproverLine} = esecurityApproval.getApproval().approval;

      if (savedRequestApproverLine.length == 0) {
        alert("요청부서의 결재선을 지정하세요.");
        return false;
      }

      if ($("input[apprdeptgbn='2']").length > 0 && savedPermitApproverLine.length == 0) {
        alert("허가부서의 결재선을 지정하세요.");
        return false;
      }
    }

    let saveMessage = "";
    if (SAVE_TYPE === "SAVE") {
      saveMessage = "저장하시겠습니까?";
    } else {
      if (menuId === "P01010302" || menuId === "P01010202") {
        saveMessage = "※주의\n반드시 기준에 맞는 신청자만 승인하시기 바랍니다.\n출입자의 보안문제 발생 시 승인자에게 책임이 있습니다.\n\n상신하시겠습니까?";
      } else {
        saveMessage = "자산 반출을 신청하시겠습니까?\n\n※ 반입예정일 이내 반입하시기 바랍니다.\n\n※ 회사 자산이 미 반입된 경우 불이익이 발생할 수 있으니\n   주의하시기 바랍니다.";
      }
    }
    if (confirm(saveMessage)) {
      Inoutwrite.fn_work_save(SAVE_TYPE);
    }
    // }
  },

  fn_work_save: function (SAVE_TYPE) {
    if (SAVE_TYPE === "SAVE") {
      $("#approvalrequestyn").val("0");
      $("#indatedelayknd").val("0");
    } else {
      $("#approvalrequestyn").val("1");
      $("#indatedelayknd").val("0");
    }

    // form data
    const formData = new FormData(document.getElementById("frm"));

    let articlekndno = "";
    $("input:radio[name='articlekndno']").each(function (index, value) {
      if ($(this).attr("checked") == "checked") {
        articlekndno = $(this).attr("value");
      }
    });

    // 파일 정보
    const fileData = [];

    $("#jbody_1 tr").each(function (index, entity) {
      fileData.push({
        writeaseq: $(entity).find("input[name=writeaseq]").val(),
        articlename: $(entity).find("input[name=articlename]").val(),
        materialId: $(entity).find("input[name=materialId]").val(),
        articleid: $(entity).find("input[name=articleid]").val(),
        articlecomputerizeid: $(entity).find("input[name=articlecomputerizeid]").val(),
        serialno: $(entity).find("input[name=serialno]").val(),
        inoutcnt: $.esutils.unSetComma($(entity).find("input[name=inoutcnt]").val()),
        unitname: $(entity).find("select[name=unitname] option:selected").val(),
        asize: $(entity).find("input[name=asize]").val(),
        imagepath: $(entity).find("input[name=imagepath]").val(),
        installdept: $(entity).find("input[name=installdept]").val(),
        chargename: $(entity).find("input[name=chargename]").val(),
        usename: $(entity).find("input[name=usename]").val(),
        venderpn: $(entity).find("input[name=venderpn]").val(),
        jsno: $(entity).find("input[name=jsno]").val(),
        mmno: $(entity).find("input[name=mmno]").val(),
        deptname: $(entity).find("input[name=deptname]").val(),
        filepath: $(entity).find("input[name=filepath]").val(),
        filename: $(entity).find("input[name=filename]").val(),
        fileid: $(entity).find("input[name=fileid]").val(),
        prdAttr: articlekndno === "6" && articlegroupid !== "1000000001" ? $(entity).find("select[name=attribute] option:selected").val() : "" //보세자산 반출입 관련 20140512
      });
    });

    formData.append('saveType', SAVE_TYPE);
    formData.append('empNo', global.empId);
    formData.append('compId', global.compId);
    formData.append('deptCd', global.deptId);
    formData.append('acIp', global.acIp);
    formData.append('fileData', JSON.stringify(fileData));
    formData.append('approval', JSON.stringify(esecurityApproval.getApproval().approval));

    $.esutils.postApi('/api/inoutasset/inoutwrite/request', formData, (response) => {
      console.log('response >>> ', response);
    }, {
      contentType: false, processData: false, enctype: 'multipart/form-data'
    });
  }
};
