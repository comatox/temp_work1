<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script type="text/javascript">
  $(document).ready(function () {
    /********************************
     * 카드번호 단말기 확인
     ********************************/
    $("#cardNoDevice").keypress(function (event) {
      if (event.keyCode == 13) {
        getIdcardIF($("#cardNoDevice").val());
        $("#cardNoDevice").focus();
      }
    });

    searchEmpPopup();
  });

  /********************************
   * 일일사원증 저장
   ********************************/
  function cardSave() {
    if (validationCheck()) {

      if (confirm("일일사원증을 등록 하시겠습니까?")) {
        let formParam = {};

        formParam.rcvCompId = $('#rcvCompId').val();
        formParam.applyGbn = $(":input:radio[name=applyGbn]:checked").val();
        formParam.cardNo = $('#cardNo').val();
        formParam.acIp = global.acIp;
        formParam.loginId = global.empId;
        formParam.empNum = $("#ioEmpId").val() === "" ? $("#empId").val() : $("#ioEmpId").val();
        formParam.empGbn = $(":input:radio[name=empGbn]:checked").val();

        $.ajax({
          url: global.contextPath + '/api/entmanage/empCard/admOnedayCard',
          type: 'post',
          data: JSON.stringify(formParam),
          dataType: "json",
          processData: false,
          cache: false,
          contentType: 'application/json',
          async: false,
          success: function (result) {
            if (result.status === 200) {
              if (result.data.dupYn === "Y") {
                alert("이미 등록된 카드번호입니다.\n확인후 다른 카드번호로 등록 하십시오.");
                $("#cardNoDevice").focus();
              } else if (result.data.idcardYn === "N") {
                alert("카드확인이 되지 않습니다.");
              } else {
                alert("일일사원증이 등록 되었습니다.");
                cardList();
              }
            } else {
              alert("일일사원증 등록중 오류가 발생하였습니다.");
            }
          },
          error: function (error) {
            alert("등록에 실패했습니다.");
          }
        });
      }
    }
  }

  /********************************
   * data validation check
   ********************************/
  function validationCheck() {
    if ($("#checkFlagEmp").val() == "" || $("#checkFlagEmp").val() == null) {
      alert("신청대상자를 입력하세요.");
      $("#searchEmpNm").focus();
      return false;
    }

    if ($("#cardNo").val() == "" || $("#cardNo").val() == null) {
      alert("카드번호가 입력되지 않았습니다.");
      $("#cardNoDevice").focus();
      return false;
    } else {
      if (($("#cardNo").val()).substring(0, 1) != "O") {
        alert("올바르지 않은 카드번호입니다.");
        $("#cardNoDevice").focus();
        return false;
      }
    }
    return true;
  }

  /********************************
   * 목록 이동
   ********************************/
  const cardList = function () {
    $.esutils.href('/secrtactvy/onedaycard/list')
  };

  /********************************
   * 사원 검색 팝업
   ********************************/
  function searchEmpPopup() {
    // keyPress Enter
    $("#searchEmpNm").on("keypress", function (e) {
      if (e.keyCode === 13) {
        getCoIoEmp();
      }
    })

    //검색 버튼 클릭
    $("#searchEmpNmBtn").on("click", function () {
      getCoIoEmp();
    });
  }

  /********************************
   * 구성원, 도급사 검색 팝업창 호출
   ********************************/
  function getCoIoEmp() {
    if ($(":input:radio[name=empGbn]:checked").val() == "M") {
      getCoEmp(); // 구성원
    } else {
      getIoEmp(); // 도급사
    }
  }

  /********************************
   * 구성원 검색 팝업
   ********************************/
  function getCoEmp() {
    $("#checkFlagEmp").val("N");
    let empNm = $("#searchEmpNm").val();

    if (empNm.length < 2) {
      alert("검색하실 구성원명을 두 글자 이상 입력해주십시오.");
    } else {
      $.esutils.openEmpPopup({
        empNm,
        fnCallback: function (result) {
          if (result?.originRowData) {
            const d = result.originRowData;

            $("#searchEmpNm").val(d.empNm);
            $("#empId").val(d.empId);
            $("#ioEmpId").val("");
            $("#showDeptCompNm").html(d.deptNm);
            $("#checkFlagEmp").val("Y");
            $("#cardNoDevice").focus();
          }
        }
      });
    }
  }

  /********************************
   * 도급사원 검색 팝업
   ********************************/
  function getIoEmp() {
    $("#checkFlagEmp").val("N");
    let empNm = $("#searchEmpNm").val();

    if (empNm.length < 2) {
      alert("검색하실 사원명을 두 글자 이상 입력해주십시오.");
    } else {
      const param = {
        empNm: empNm,
        onedaySubcontYn: "Y"
      };
      $.esutils.openIoEmpSearchViolationPopup({
        ...param,
        fnCallback: function (result) {
          if (result?.originRowData) {
            const d = result.originRowData;

            $("#searchEmpNm").val(d.ioEmpNm);
            $("#ioEmpId").val(d.ioEmpId);
            $("#empId").val("");
            $("#showDeptCompNm").html(d.compKoNm);
            $("#checkFlagEmp").val("Y");
          }
        }
      });
    }
  }

  /********************************
   * 소속구분 변경
   ********************************/
  function gbnChg() {
    $("#searchEmpNm").val("");
    $("#empId").val("");
    $("#ioEmpId").val("");
    $("#showDeptCompNm").html("");
    $("#checkFlagEmp").val("N");
  }

  /********************************
   * 카드번호 입력후 통합사번 조회
   ********************************/
  function getIdcardIF(strCardNo) {
    $.ajax({
      url: global.contextPath + '/api/entmanage/empCard/oneday/idCard?cardNo=' + strCardNo,
      type: 'get',
      processData: false,
      cache: false,
      contentType: 'application/json',
      async: false,
      success: function (result) {
        if (result.status === 200) {
          $("#cardNo").val(result.data.cardNo);
          $("#cardNoDevice").val(result.data.inempId);
        } else {
          $("#cardNo").val("");
        }
      },
      error: function (error) {
        alert("등록에 실패했습니다.");
      }
    });
  }
</script>

<div class="wrap">
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <table>
                    <tr>
                        <td>
                            <img src="/esecurity/assets/common/images/common/subTitle/pass/title_138.png" alt="일일사원증 발급"/>
                        </td>
                        <td>
                            <div class="buttonGroup">
                                <div class="leftGroup">
									<span class="button bt_s2">
										<button type="button" style="width: 50px;" onclick="cardList();">취소</button>
									</span>
                                    <span class="button bt_s1" id="apprSpan1">
										<button type="button" style="width: 50px;" onclick="cardSave();">등록</button>
									</span>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><img src="/esecurity/assets/common/images/common/line.png" width="100%" height="3"/></td>
                    </tr>
                </table>
                <div id="realContents">
                    <form id="cardRequestForm" name="cardRequestForm" method="post">
                        <input type="hidden" id="acIp" name="acIp" value=""/>
                        <input type="hidden" id="menuId" name="menuId" value=""/>
                        <input type="hidden" id="empId" name="empId"/>
                        <input type="hidden" id="ioEmpId" name="ioEmpId"/>
                        <input type="hidden" id="loginId" name="loginId" value=""/>
                        <input type="hidden" id="IDCARD_ID" name="IDCARD_ID"/>
                        <input type="hidden" id="CARDKEY_GBN" name="CARDKEY_GBN"/>

                        <input type="hidden" id="EMPCARD_APPL_NO" name="EMPCARD_APPL_NO" value=""/>
                        <input type="hidden" id="S_APPLY_STRT_DT" name="S_APPLY_STRT_DT" value=""/>
                        <input type="hidden" id="S_APPLY_END_DT" name="S_APPLY_END_DT" value=""/>
                        <input type="hidden" id="S_EMP_GBN" name="S_EMP_GBN" value=""/>
                        <input type="hidden" id="S_EMP_NM" name="S_EMP_NM" value=""/>
                        <input type="hidden" id="S_CARD_NO" name="S_CARD_NO" value=""/>
                        <input type="hidden" id="PAGE_NO" name="PAGE_NO" value=""/>
                        <input type="hidden" id="checkFlagEmp" name="checkFlagEmp"/>

                        <div id="apprContents">
                            <h1 class="txt_title01 fl">신청대상자</h1>
                            <table cellpadding="0" cellspacing="0" border="0"
                                   class="view_board">
                                <tbody>
                                <tr>
                                    <th>소속 구분</th>
                                    <td colspan=3>
                                        <input type="radio" id="empGbnM" name="empGbn" value="M" class="border_none" checked onChange="javascript:gbnChg();"/>
                                        <label for="empGbnM" id="empGbn1">구성원</label>&nbsp;
                                        <input type="radio" id="empGbnP" name="empGbn" value="P" class="border_none" onChange="javascript:gbnChg();"/>
                                        <label for="empGbnP" id="empGbn2">도급사</label>&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <th>성명</th>
                                    <td colspan=3>
                                        <input type='text' id='searchEmpNm' name='searchEmpNm' style='width:100px'/>
                                        <a href='javascript: void(0);' id="searchEmpNmBtn" class='btn_type01' style='margin-left: 5px'>
                                            <span>검색</span>
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <th>부서명/업체명</th>
                                    <td id=showDeptCompNm colspan=3></td>
                                    <th>출입 사업장</th>
                                    <td>
                                        <select id='rcvCompId' name='rcvCompId' style='width: 242px;'>
                                            <option value='1108000001'>분당</option>
                                            <option value='1101000001' selected>이천</option>
                                            <option value='1102000001'>청주</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>발급 구분</th>
                                    <td colspan=3>
                                        <input type="radio" id="applyGbnA0510001" name="applyGbn" value="A0510001" class="border_none" checked/>
                                        <label for="applyGbnA0510001" id="applyGbn1">단순미소지</label>
                                        <input type="radio" id="applyGbnA0510002" name="applyGbn" value="A0510002" class="border_none"/>
                                        <label for="applyGbnA0510002" id="applyGbn2">파손</label>
                                        <input type="radio" id="applyGbnA0510003" name="applyGbn" value="A0510003" class="border_none"/>
                                        <label for="applyGbnA0510003" id="applyGbn3">분실</label>
                                    </td>
                                </tr>
                                <tr>
                                    <th>카드 번호</th>
                                    <td colspan=3>
                                        <input type="text" id="cardNoDevice" name="cardNoDevice" style="width:100px;ime-mode:inactive;" maxlength=10/>
                                        <input type="hidden" id="cardNo" name="cardNo"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="buttonGroup">
                            <div class="leftGroup">
                                <span class="button bt_s2">
                                    <button type="button" style="width: 50px;" onclick="cardList();">취소</button>
                                </span>
                                <span class="button bt_s1">
                                    <button type="button" style="width: 50px;" onclick="cardSave();">등록</button>
                                </span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>