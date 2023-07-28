<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script>
  $(document).ready(function () {

    // 기본값 세팅
    defaultValueSet();

    // 화면 조회
    renderContents();

    // 목록버튼 클릭시
    onClickListBtn();

    // 사원 찾기 팝업
    searchEmpPopup();

    // 저장버튼 클릭시
    onClickBtnSave();

  });

  /**************
   * [기본값 세팅]
   **************/
  function defaultValueSet() {
    $("#compId").val("${param.compId}");
    $("#detlCd").val("${param.detlCd}");
  }

  /****************
   * [화면 Render]
   ****************/
  function renderContents() {

    $.esutils.renderData("securityForm",
        "/api/secrtactvy/securityAdminManageItem/secDetlEmpView?"
        + new URLSearchParams({compId: $("#compId").val(), detlCd: $("#detlCd").val()}).toString(),
        (d) => {

          const searchOfendTxt = "${param.searchOfendTxt}";
          const searchOfendDetailTxt = "${param.searchOfendDetailTxt}";
          const searchCompNm = "${param.searchCompNm}";

          $("[view-data='searchOfendTxt']").html(searchOfendTxt);
          $("[view-data='searchOfendDetailTxt']").html(searchOfendDetailTxt);
          $("[view-data='searchCompNm']").html(searchCompNm);

          //주담당자
          $("#searchEmpNm").val(d.empNm);
          $("[view-data='telNo']").html(d.hpNo);
          $("[view-data='showEmpId']").html(d.empId);

          if (d.deptNm) {
            $("[view-data='showDeptCompNm']").html(d.deptNm + "(" + d.coCompNm + ")");
          }

          //부담당자
          $("#searchSubEmpNm").val(d.subEmpNm);
          $("[view-data='subTelNo']").html(d.subHpNo);
          $("[view-data='showSubEmpId']").html(d.subEmpId);

          if (d.subDeptNm && d.coSubCompNm) {
            $("[view-data='showSubDeptCompNm']").html(d.subDeptNm + "(" + d.coSubCompNm + ")");
          }
          $("#subEmpId").val(d.subEmpId);

          if (d.empId) {
            $("#checkFlagEmp").val("Y");
          }

        }
    , {loading : true, exclude: ["detlCd"]} )
  }

  /*******************
   * [목록 버튼 클릭시]
   *******************/
  function onClickListBtn() {
    $("#btnGoList1, #btnGoList2").on("click", function () {
      $.esutils.href("/admin/secrtactvy/securityadminmanageitem/secdetlemp/list");
    });
  }

  /******************
   * [방문객 찾기 팝업]
   ******************/
  function searchEmpPopup() {
    // keyPress Enter
    $("#searchEmpNm, #searchSubEmpNm").on("keypress", function (e) {
      if (e.keyCode === 13) {
        const type = $(this).attr("id") === "searchEmpNm" ? "M" : "S";
        getEmpInfo(type);
      }
    })

    // 사원 검색 버튼 클릭
    $("#_searchEmpNm, #_searchSubEmpNm").on("click", function () {
      const type = $(this).attr("id") === "_searchEmpNm" ? "M" : "S";
      getEmpInfo(type);
    });
  }

  function getEmpInfo(type) {

    //type M:정 담당자, S:부 담당자
    const searchEmpNm = type === "M"
        ? $("#searchEmpNm").val() : $("#searchSubEmpNm").val();

    if (searchEmpNm.length < 2) {
      alert("검색하실 구성원명을 두 글자 이상 입력해주십시오.");
      return;
    }

    $.esutils.openEmpPopup({
      empNm: searchEmpNm,
      fnCallback: (result) => {
        const d = result.originRowData;
        //정 담당자
        if(type === "M") {

          if($("#subEmpId").val() === d.empId) {
            alert("담당자(부)에 등록되어있습니다.");
            $("#searchEmpNm").val("");
          }
          else {
            $("#searchEmpNm").val(d.empNm);
            $("#empId").val(d.empId);
            $("[view-data='showEmpId']").html(d.empId);
            $("[view-data='showDeptCompNm']").html(d.deptNm+"("+d.compNm+")");
            $("[view-data='telNo']").html(d.hpNo);
            $("#checkFlagEmp").val("Y");
          }
        }

        //부 담당자
        if(type === "S") {

          if($("#empId").val() === d.empId) {
            alert("담당자(정)에 등록되어있습니다.");
            $("#searchSubEmpNm").val("");
          }
          else {
            $("#searchSubEmpNm").val(d.empNm);
            $("#subEmpId").val(d.empId);
            $("[view-data='showSubEmpId']").html(d.empId);
            $("[view-data='showSubDeptCompNm']").html(d.deptNm+"("+d.compNm+")");
            $("[view-data='subTelNo']").html(d.hpNo);
            $("#checkFlagEmp").val("Y");
          }
        }

      }
    });
  }

  /*******************
   * [저장버튼 클릭시]
   *******************/
  function onClickBtnSave() {
    $("#btnSave1, #btnSave2").on("click", function() {

      if(!validationCheck()) return;
      if(!confirm("보안담당자 저장을 하시겠습니까?")) return;



      const params = {...($.esutils.getFieldsValue($("#securityForm"))), acIp : global.acIp, crtBy : global.empId};

      $.esutils.postApi("/api/secrtactvy/securityAdminManageItem/secDetlEmpInsert", params, function(res) {
        if(res?.data) {
          alert("보안담당자 저장 되었습니다.");

          //목록으로 이동
          $("#btnGoList1").trigger("click");
        }
        else {
          alert("보안담당자 저장중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
        }
      })

    });
  }

  /**************
   * [데이터 검증]
   **************/
  function validationCheck() {

    //임직원 정보 공백여부 체크 - 추가
    if($("#checkFlagEmp").val() !== "Y") {
      alert("보안위규 담당자 정보를 입력해주십시오.");
      $("#searchEmpNm").focus();
      return false;
    }

    if(!$("#empId").val()){
      alert("보안위규 담당자(정)를 검색하세요");
      return false;
    }

    if(!$("#subEmpId").val()){
      alert("보안위규 담당자(부)를 검색하세요");
      return false;
    }

    if( $("#empId").val() === $("#subEmpId").val()){
      alert("담당자(정)과 담당자(부)가 같습니다.");
      return false;
    }

    return true;
  }
</script>


<div id="contentsArea">
    <table width="100%" border="0">
        <tr>
            <td align="left">
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/title_340.png"/>
            </td>
            <td align="right">
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s2" id="btnGoList1"><button type="button" style="width:100px;">목록</button></span>
                        <span class="button bt_s1" id="btnSave1" ><button type="button" style="width:100px;">저장</button></span>
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
        <form id="securityForm" name="securityForm">
            <input type="hidden" id="checkFlagEmp" name="checkFlagEmp"/>
            <input type="hidden" id="empId" name="empId"/>
            <input type="hidden" id="subEmpId" name="subEmpId"/>
            <input type="hidden" id="detlCd" name="detlCd" value=""/>
            <input type="hidden" id="compId" name="compId" value=""/>

            <h1 class="txt_title01">보안위규 담당자(정)</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="117"/>
                    <col width="213"/>
                    <col width="117"/>
                    <col width="213"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>위규 구분</th>
                    <td colspan="3" view-data="searchOfendTxt"></td>
                </tr>
                <tr>
                    <th>위규 내용</th>
                    <td colspan="3" view-data="searchOfendDetailTxt"></td>
                </tr>
                <tr>
                    <th>사업장</th>
                    <td colspan=3 view-data="searchCompNm"></td>
                </tr>
                <tr>
                    <th>성명<span class="necessary">&nbsp;</span></th>
                    <td>
                        <input type='text' id='searchEmpNm' name='searchEmpNm' style='width:75%'/>
                        <a href="javascript:void(0);" id="_searchEmpNm" class='btn_type01' style='margin-left: 5px'><span>검색</span></a>
                    </td>
                    <th>연락처</th>
                    <td view-data='telNo'>
                    </td>
                </tr>
                <tr>
                    <th>사번</th>
                    <td view-data='showEmpId' style='width:213px;'></td>
                    <th>소속</th>
                    <td view-data='showDeptCompNm'></td>
                </tr>
                </tbody>
            </table>

            <h1 class="txt_title01">보안위규 담당자(부)</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="117"/>
                    <col width="213"/>
                    <col width="117"/>
                    <col width="213"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>성명<span class="necessary">&nbsp;</span></th>
                    <td>
                        <input type='text' id='searchSubEmpNm' name='searchSubEmpNm' style='width:75%'/>
                        <a href="javascript:void(0);" id="_searchSubEmpNm" class='btn_type01' style='margin-left: 5px'><span>검색</span></a>
                    </td>
                    <th>연락처</th>
                    <td view-data='subTelNo'>
                    </td>
                </tr>
                <tr>
                    <th>사번</th>
                    <td view-data='showSubEmpId' style='width:213px;'></td>
                    <th>소속</th>
                    <td view-data='showSubDeptCompNm'></td>
                </tr>
                </tbody>
            </table>

            <!-- 버튼 -->
            <div class="buttonGroup">
                <div class="leftGroup">
                    <span class="button bt_s2" id="btnGoList2"><button type="button" style="width:100px;">목록</button></span>
                    <span class="button bt_s1" id="btnSave2"><button type="button" style="width:100px;">저장</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->

        </form>
    </div>
</div>