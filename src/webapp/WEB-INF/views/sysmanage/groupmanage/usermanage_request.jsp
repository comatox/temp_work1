<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

  /***************************************************************************
   * 화면 onload 처리
   ***************************************************************************/
  $(document).ready(function () {

    initDocument();

  });

  /***************************************************************************
   * Document initializing
   ***************************************************************************/
  let initDocument = async () => {

    $.esutils.datepicker(["[id=joinDt]"]);
    $("#joinDt").val($.esutils.getToday());
    $("#acIp").val(global.acIp);
    $("#crtBy").val(global.empId);
    $("#modBy").val(global.empId);

    await fnGetDivList(); // 사업장 Select Option 조회
    await fnGetCompList(); // 회사명 Select Option 조회
    await fnGetDeptList(); // 부서명 Select Option 조회
    await fnGetJcList(); // 직책 Select Option 조회
    await fnGetJwList(); // 직위 Select Option 조회
  }

  const fnValidation = () => {

    const securityId = $("#securityId").val();
    const checkSecurityId = $("#checkSecurityId").val();
    const empId = $("#empId").val();
    const checkEmpId = $("#checkEmpId").val();

    const mail = $("#mail").val();
    const cMail = $("#cMail").val();

    const empNm = $("#empNm").val();
    const jwNm = $("#jwId").val();

    const divCd = $("#divCd").val();
    const deptNm = $("#deptId").val();

    const jumin = $("#juminNo").val();
    const cJumin1 = $("#cJuminNo1").val();
    const cJumin2 = $("#cJuminNo2").val();
    const cJumin = cJumin1 + cJumin2;

    const birthNo = $("#birthNo").val();

    const joinDt = $('#joinDt').val();

    const nation = $("#nation").val();

    const passport = $("#passportNo").val();
    const cPassport = $("#cPassportNo").val();

    const hpNo = $("#hpNo").val();

    const password = $("#password").val();
    const checkPassword = $("#checkPassword").val();

    const zip1 = $("#zip1").val();
    const zip2 = $("#zip1").val();
    const addr = $("#addr").val();
    const addr2 = $("#addr2").val();

    const filePhoto = $("#filePhoto").val();
    const filePassPort = $("#filePassport").val();

    if (checkSecurityId !== securityId) {
      alert("ID 중복확인을 해주십시오.");
      $("#securityId").focus();
      $("#checkSecurityIdFlag").val('');
      return false;
    }

    for (let i = 0; i < cMail.length; i++) {
      const checkVal = cMail.charAt(i);
      if (checkVal.search(/[0-9|a-z|A-Z|@|.|\-\|_]/) === -1) {
        alert("특수문자는 입력할 수 없습니다.");
        $("#cMail").focus();
        $("#checkEmailFlag").val('');
        return false;
      }
    }

    if (securityId === "") {
      alert("ID를 입력해주십시오.");
      $("#securityId").focus();
      return false;
    }

    console.log("■■■■■■■ checkSecurityIdFlag ■■■■■■■", $("#checkSecurityIdFlag").val());

    if ($("#checkSecurityIdFlag").val() !== "Y") {
      alert("ID 중복확인을 완료해 주십시오.");
      $("#securityId").focus();
      return false;
    }

    if (password === "") {
      alert("비밀번호를 입력해주십시오.");
      $("#password").focus();
      return false;
    }
    /*
    if(password.length < 10) {
        alert("비밀번호는 최소 10자리 이상 입력해야합니다.");
        $("#password").focus();
        return false;
    }
    */
    if (password === securityId) {
      alert("비밀번호는 ID와 같을 수 없습니다.");
      $("#password").focus();
      return false;
    }
    /*
    if(password.search(/[0-9]/) === -1 || password.search(/[A-z]/) === -1) {
        alert("비밀번호는 숫자와 알파벳의 조합으로 입력해야합니다.");
        $("#password").focus();
        return false;
    }
    */
    if (checkPassword === "") {
      alert("비밀번호 확인을 입력해주십시오.");
      $("#checkPassword").focus();
      return false;
    }
    if (password !== checkPassword) {
      alert("비밀번호와 비밀번호확인의 패스워드가 틀립니다.");
      return false;
    }

    if (divCd === "") {
      alert("사업장을 선택해주십시오.");
      $("#divCd").focus();
      return false;
    }

    if (deptNm === "") {
      alert("부서를 선택해주십시오.");
      $("#deptId").focus();
      return false;
    }

    if (empNm === "") {
      alert("성명을 입력해주십시오.");
      $("#empNm").focus();
      return false;
    }

    if (empId === "") {
      alert("사번을 입력해주십시오.");
      $("#empId").focus();
      return false;
    }

    if ($("#checkEmpIdFlag").val() === "N") {
      alert("사번 확인을 완료해 주십시오.");
      $("#empId").focus();
      return false;
    }

    if (jwNm === "") {
      alert("직위를 선택해주십시오.");
      $("#jwId").focus();
      return false;
    }

    if ($("#checkJuminNoFlag").val() === "Y") {
      if (birthNo === "") {
        alert("생년월일 입력 후 확인 버튼을 클릭해 주세요.\n ex) 1972년 7월 4일생의 경우 720704 입력");
        $("#BIRTH_NO").focus();
        return false;
      }
    } else {
      alert("생년월일 입력 후 확인 버튼을 클릭해 주세요.\n ex) 1972년 7월 4일생의 경우 720704 입력");
      $("#BIRTH_NO").focus();
      return false;
    }

    if (hpNo === "") {
      alert("휴대전화번호를 입력해주십시오.");
      $("#hpNo").focus();
      return false;
    }
    if (hpNo.length < 10) {
      alert("휴대전화번호는 10자리 이상 입력하셔야합니다.");
      $("#hpNo").focus();
      return false;
    }

    if ($.trim(cMail) === "") {
      alert("메일 주소를 입력해주십시오.");
      return false;
    }
    if (cMail.search(/(\S+)@(\S+)\.(\S+)/) === -1) {
      alert("이메일 주소 형식만 입력 가능합니다.");
      $("#cMail").focus();
      return false;
    }
    for (let i = 0; i < cMail.length; i++) {
      checkVal = cMail.charAt(i);
      if (checkVal.search(/[0-9|a-z|A-Z|@|.|\-\|_]/) === -1) {
        alert("특수문자는 입력할 수 없습니다.");
        $("#cMail").focus();
        $("#checkEmailFlag").val('');
        return false;
      }
    }

    if (joinDt === "") {
      alert("입사일을 입력해주십시오.");
      $("#joinDt").focus();
      return false;
    }

    if (zip1 === "" || zip2 === "") {
      alert("우편 번호를 입력해주십시오.");
      return false;
    }
    if (addr === "") {
      alert("기본 주소를 입력해주십시오.");
      $("#addr").focus();
      return false;
    }
    if (addr2 === "") {
      alert("상세 주소를 입력해주십시오.");
      $("#addr2").focus();
      return false;
    }
    return true;
  }

  const fnSave = () => {
    if (fnValidation()) {
      if (confirm("회원가입 하시겠습니까?")) {

        const formParam = $.esutils.getFieldsValue($("#userManageForm"));
        console.log("■■ formParam ■■", formParam);
        $.esutils.postApi('/api/sysmanage/groupManage/userManage/save', formParam, (response) => {

          if (response.message === "OK") {
            alert("사용자가 등록 되었습니다.\n");
            $.esutils.href("/sysmanage/groupmanage/usermanage/list");
          } else {
            alert("사용자 등록에 실패하였습니다.\n회원정보를 확인해주십시오.");
          }
        }, {});
      }
    }
  }

  const fnCheckBirth = () => {
    let birthNo = $("#birthNo").val();
    birthNo = birthNo.replace(/ /g, '');
    if (birthNo.length !== 6) {
      alert('생년월일 입력 후 확인 버튼을 클릭해 주세요.\n ex) 1972년 7월 4일생의 경우 720704 입력');
      return;
    }

    if (!(birthNo.substr(2, 1) === '0' || birthNo.substr(2, 1) === '1')) {
      alert('생년월일을 정확히 입력해 주세요');
      return;
    }

    if (!(birthNo.substr(4, 1) === '0' || birthNo.substr(4, 1) === '1' || birthNo.substr(4, 1) === '2' || birthNo.substr(4, 1) === '3')) {
      alert('생년월일을 정확히 입력해 주세요');
      return;
    }

    if (birthNo.substr(4, 1) === '3') {
      if (!(birthNo.substr(5, 1) === '0' || birthNo.substr(5, 1) === '1')) {
        alert('생년월일을 정확히 입력해 주세요');
        return;
      }
    }

    if (birthNo.substr(0, 1) === '0') birthNo = '20' + birthNo;
    else birthNo = '19' + birthNo;

    const adt = 19; // 성년 나이
    const adt_max = 100; // 100세

    let d = new Date();
    const y = d.getFullYear();
    let m = (d.getMonth() + 1);
    d = d.getDate();

    // 날짜 포맷 맞추기
    if (m < 10) m = '0' + m;
    if (d < 10) d = '0' + d;

    const birthday_y = parseInt(birthNo.substr(0, 4));
    const birthday_m = birthNo.substr(4, 2);
    const birthday_d = birthNo.substr(6, 2);
    const birthday_md = birthNo.substr(4, 4);

    if (y - birthday_y < 14) {
      alert('입주사-행복한 만남 사이트는 만14세 미만의 개인정보를 수집하지 않으며,\n회원으로 가입하실 수 없습니다');
      $("#checkJuminNoFlag").val("");
      $("#checkNationFlag").val("");
    } else {
      alert('만 14세 이상으로 회원가입 가능합니다');
      $("#checkJuminNoFlag").val("Y");
      $("#checkNationFlag").val("Y");
    }
  }

  const fnPress = (event, type) => {
    if (type === "numbers") {
      if (event.keyCode < 48 || event.keyCode > 57 || event.keyCode === 9) {
        return false;
      }
    }
  }

  const fnPressHan = (obj) => {
    obj.value = obj.value.replace(/[\a-zㄱ-ㅎ가-힣]/g, '');
  }

  const fnSearchZipCode = () => {
    $.esutils.openPopup({
      url: "/popup/searchzipcode",
      width: "650",
      height: "600",
      fnCallback
    });
  }

  const fnCallback = (response) => {
    const {originRowData} = response;
    const {zip1, zip2, addr} = originRowData;
    $("#zip1").val(zip1);
    $("#zip2").val(zip2);
    $("#addr").val(addr);
    $("#addr2").val("");
  }

  /* ID 중복 체크 */
  const fnCheckId = () => {

    const securityId = $("#securityId").val();
    if (securityId === '') {
      alert('ID를 입력해 주세요.');
      return;
    }

    $.esutils.getApi("/api/sysmanage/groupManage/userManage/checkId", {id: securityId}, (response) => {
      if (response && response.data) {
        if (response.message === "OK") {
          alert("사용가능한 ID 입니다.");
          $("#checkSecurityId").val(securityId);
          $("#checkSecurityIdFlag").val("Y");
        } else {
          $("#checkSecurityId").val('');
          $("#securityId").focus();
          alert("기등록되어 있는 ID 입니다.");
        }
      }
    });
  }

  /* 사번 중복 체크 */
  const fnCheckEmpId = () => {
    const empId = $("#empId").val();
    if (empId === '') {
      alert('사번을 입력해 주세요.');
      return;
    }

    $.esutils.getApi("/api/sysmanage/groupManage/userManage/checkEmpId", {empId}, (response) => {
      if (response && response.data) {
        if (response.message === "OK") {
          alert("사용가능한 사번 입니다.");
          $("#checkEmpId").val(empId);
          $("#checkEmpIdFlag").val("Y");
        } else {
          $("#checkEmpId").val('');
          $("#empId").focus();
          alert("기등록되어 있는 사번 입니다.");
        }
      }
    });
  }

  const fnChangeDivNm = () => {
    //alert($("#divCd option:selected").text());
    $("#divNm").val($("#divCd option:selected").text());
  }

  /***************************************************************************
   * 사업장 콤보 리스트
   ***************************************************************************/
  const fnGetDivList = async () => {

    const targetId = 'divCd';
    await $.esutils.renderCode([{type: "select", grpCd: "F003", targetId, valueProp: "etc1", blankOption: true,}]);
  }

  /***************************************************************************
   * 업체 콤보 리스트
   ***************************************************************************/
  const fnGetCompList = async () => {

    const targetId = 'compId';
    await $.esutils.renderCode([
      {
        targetId,
        url: "/api/common/organization/comp",
        nameProp: "compNm",
        valueProp: "compId",
        type: "select",
        filter: (d) => d.compId !== '1100000001'
      }
    ], () => {
      $("#" + targetId).prepend('<option value="" selected>전체</option>');
    });
  }

  /***************************************************************************
   * 부서 콤보 리스트
   ***************************************************************************/
  const fnGetDeptList = async () => {

    const targetId = 'deptId';
    await $.esutils.renderCode([
      {
        targetId,
        url: "/api/common/organization/dept/compDeptList",
        param: {compId: $("#compId").val()},
        nameProp: "deptNm",
        valueProp: "deptId",
        type: "select",
        blankOption: true,
      }
    ]);
  }

  /***************************************************************************
   * 직책 콤보 리스트
   ***************************************************************************/
  const fnGetJcList = async () => {

    const targetId = 'jcId';
    await $.esutils.renderCode([
      {
        targetId,
        url: "/api/sysmanage/groupManage/userManage/jcList",
        nameProp: "jcNm",
        valueProp: "jcId",
        type: "select",
        blankOption: true
      }
    ]);
  }

  /***************************************************************************
   * 직위 콤보 리스트
   ***************************************************************************/
  const fnGetJwList = async () => {

    const targetId = 'jwId';
    await $.esutils.renderCode([
      {
        targetId,
        url: "/api/sysmanage/groupManage/userManage/jwList",
        nameProp: "jwNm",
        valueProp: "jwId",
        type: "select",
        blankOption: true
      }
    ]);
  }

  const fnChangeCompNm = () => {
    fnGetDeptList();
  }

  /***************************************************************************
   * 목록 이동
   ***************************************************************************/
  const fnList = () => {
    $.esutils.href("/sysmanage/groupmanage/usermanage/list");
  }

</script>
<form name="userManageForm" id="userManageForm" method="post">
    <input type="hidden" id="acIp" name="acIp" value=""/>
    <input type="hidden" id="crtBy" name="crtBy" value=""/>
    <input type="hidden" id="modBy" name="modBy" value=""/>
    <input type="hidden" id="checkSecurityId" name="checkSecurityId"/>
    <input type="hidden" id="checkEmpId" name="checkEmpId"/>
    <input type="hidden" id="divNm" name="divNm" value=""/>
    <input type="hidden" id="useYn" name="useYn" value="Y"/>
    <input type="hidden" id="checkSecurityIdFlag" name="checkSecurityIdFlag" value="N"/>
    <input type="hidden" id="checkEmpIdFlag" name="checkEmpIdFlag" value="N"/>
    <input type="hidden" id="checkJuminNoFlag" name="checkJuminNoFlag" value=""/>
    <input type="hidden" id="checkNationFlag" name="checkNationFlag" value=""/>
    <input type="hidden" id="checkCertiFlag" name="checkCertiFlag" value=""/>
    <input type="hidden" id="checkBirthFlag" name="checkBirthFlag" value=""/>
    <input type="hidden" id="fileUpload1Yn" value=""/>
    <input type="hidden" id="fileUpload2Yn" value=""/>
    <input type="hidden" id="certino" value=""/>
    <div id="search_area">
        <div class="search_content">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board_type1">
                <caption>사용자 정보 입력</caption>
                <colgroup>
                    <col width="130px;"/>
                    <col width="220px;"/>
                    <col width="130px;"/>
                    <col width="220px;"/>
                </colgroup>
                <tbody>
                <tr>
                    <th scope="row" class="line">
                        <label>ID<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td class="line" colspan="3">
                        <input type="text" id="securityId" name="securityId" notNull="Y" style="width:180px;IME-MODE:disabled;" maxLength="50" maxByte="50"/>&nbsp;&nbsp;<a
                            href="javascript:fnCheckId();"
                            title="ID 중복 확인" class="btn_type01"><span>중복확인</span></a>
                    </td>
                </tr>

                <tr>
                    <th scope="row" id="memPwd">
                        <label>비밀번호<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td headers="memPwd">
                        <input type="password" id="password" name="password" maxLength="20" maxByte="20" style="width:180px"/>
                    </td>
                    <th scope="row" id="mem_pwd_check">
                        <label>비밀번호확인<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td headers="mem_pwd_check">
                        <input type="password" id="checkPassword" name="checkPassword" maxLength="20" maxByte="20" style="width:180px"/>
                    </td>
                </tr>

                <tr>
                    <th scope="row">
                        사업장<span class="necessary">(필수입력사항)</span>
                    </th>
                    <td>
                        <select id="divCd" name="divCd" style="width:150px;" onchange="javascript:fnChangeDivNm();">
                            <option value=""></option>
                        </select>
                    </td>
                    <th scope="row">
                        상주/비상주<span class="necessary">(필수입력사항)</span>
                    </th>
                    <td>
                        <input type="radio" name="resideYn" value="Y" checked="checked">상주
                        <input type="radio" name="resideYn" value="N">비상주
                        <div>
                            <font color="red"><span class="descript_1">※ 비상주 처리 시 출입정지 되오니<br/>방문예약으로 출입하시기 바랍니다.</span></font>
                        </div>
                    </td>
                </tr>

                <tr>
                    <th scope="row">
                        업체명<span class="necessary">(필수입력사항)</span>
                    </th>
                    <td>
                        <select id=compId name="compId" style="width:180px;" onchange="javascript:fnChangeCompNm();">
                            <option value="">
                            </option>
                        </select>
                    </td>
                    <th scope="row">
                        부서<span class="necessary">(필수입력사항)</span>
                    </th>
                    <td>
                        <select id="deptId" name="deptId" style="width:230px;">
                            <option value=""></option>
                        </select>
                    </td>
                </tr>

                <tr>
                    <th scope="row" id="memName">
                        <label>성명<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td headers="memName">
                        <input type="text" id="empNm" name="empNm" notNull="Y" maxLength="20" maxByte="20"/>
                    </td>
                    <th scope="row">
                        <label>사번<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td>
                        <input type="text" id="empId" name="empId" notNull="Y" style="width:150px;IME-MODE:disabled;"/>&nbsp;&nbsp;<a href="javascript:fnCheckEmpId();" title="사번 중복 확인"
                                                                                                                                      class="btn_type01"><span>확인</span></a>
                    </td>
                </tr>

                <tr>
                    <th scope="row">
                        <label>직책</label>
                    </th>
                    <td>
                        <select id="jcId" name="jcId" style="width:170px;">
                            <option value=""></option>
                        </select>
                    </td>
                    <th scope="row" id="mem_position">
                        <label>직위<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td>
                        <select id="jwId" name="jwId" style="width:170px;">
                            <option value=""></option>
                        </select>
                    </td>
                </tr>

                <tr>
                    <th id="setNation" scope="row">생년월일<span class="necessary">(필수 입력 사항)</span></th>
                    <td id="setNationBtn" headers="setNation" colspan="3"><input type="text" id="birthNo" name="birthNo" style="IME-MODE:disabled;" onfocus="this.select();"
                                                                                 onkeypress="return fnPress(event, 'numbers');" onkeyup="javascript:fnPressHan(this);" size="7" maxlength="6"
                                                                                 maxbyte="6" title="생년월일">&nbsp;&nbsp;<a href="javascript:fnCheckBirth();" class="btn_type01"
                                                                                                                         title="생년월일 확인"><span>확인</span></a>&nbsp;&nbsp;입력 예)1972년 7월 4일생의 경우 720704
                        입력<font color="red">(주민번호 앞자리 형식)</font></td>
                </tr>

                <tr>
                    <th scope="row">
                        <label>휴대번호<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td colspan="3">
                        <input type="text" id="hpNo" name="hpNo" style="IME-MODE:disabled;" onkeypress="return fnPress(event, 'numbers');" onkeyup="fnPressHan(this);" maxLength="15" maxByte="15"
                               dataType="int"/>
                        <span class="descript_1">- 표시 없이 숫자만 입력하십시오.</span>
                    </td>
                </tr>
                <tr>
                    <th scope="row">
                        <label>사내번호</label>
                    </th>
                    <td colspan="3">
                        <input type="text" id="telNo" name="telNo" style="IME-MODE:disabled;" maxLength="15" maxByte="15" dataType="int"/>
                        <span class="descript_1">- 표시 없이 숫자만 입력하십시오.</span>
                    </td>
                </tr>
                <tr>
                    <th scope="row">
                        <label>메일주소<span class="necessary">(필수입력사항)</span></label>
                    </th>
                    <td>
                        <input type="text" id="cMail" name="cMail" notNull="Y" dataType="email" style="width:180px;IME-MODE:disabled;" maxLength="50" maxByte="50"/>
                    </td>
                    <th>
                        <label>입사일</label><span class="necessary">&nbsp;</span>
                    </th>
                    <td>
                        <input type="text" id="joinDt" name="joinDt" style="width: 80px;" maxlength="10"/>
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label>우편번호<span class="necessary">필수 입력사항</span></label></th>
                    <td colspan="3">
                        <input type="text" id="zip1" name="zip1" maxLength="3" maxByte="3" size="3" class="readonly" readonly/> - <input type="text" id="zip2" name="zip2" notNull="Y" maxLength="3"
                                                                                                                                         maxByte="3" size="3" reaonly class="readonly" readonly
                                                                                                                                         title="우편번호 뒷자리"/>
                        <a href="javascript:fnSearchZipCode();" class="btn_type01" style="margin-left:5px" id="searchZipCodeBtn"><span>검색</span>
                        </a>
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label>기본 주소<span class="necessary">필수 입력사항</span></label></th>
                    <td colspan="3">
                        <input type="text" id="addr" name="addr" maxLength="200" maxByte="200" class="readonly" readonly style="width:470px;"/>
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label>상세 주소<span class="necessary">필수 입력사항</span></label></th>
                    <td colspan="3">
                        <input type="text" id="addr2" name="addr2" maxLength="200" maxByte="200" style="width:470px;">
                    </td>
                </tr>
                </tbody>
            </table>

            <!-- 버튼 -->
            <div class="buttonGroup">
                <div class="leftGroup">
                    <span class="button bt_s2"><button type="button" style="width:50px;" onclick="fnList();">목록</button></span>
                    <span class="button bt_s1" id="registBtn"><button type="button" style="width:50px" onclick="fnSave();">등록</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->
        </div>
    </div>
</form>
