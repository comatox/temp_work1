<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

  let gridUtil;

  /***************************************************************************
   * 화면 onload 처리
   ***************************************************************************/
  $(document).ready(function () {
    initDocument();

  });

  /***************************************************************************
   * Document initializing
   ***************************************************************************/
  const initDocument = async () => {

    $.esutils.renderData("userManageForm", '/api/sysmanage/groupManage/userManage/view/${param.empId}',
        (data) => {
          if(data.zip1 && data.zip2){
            $("[view-data=zipCode]").text(`\${data.zip1} - \${data.zip2}`);
          }

          if(data.resideYn){
            $("[view-data=resideYn]").text(data.resideYn === 'Y' ? '상주' : '비상주');
          }
        });
  }

  /***************************************************************************
   * 목록 이동
   ***************************************************************************/
  const fnList = () => {
    $.esutils.href("/sysmanage/groupmanage/usermanage/list");
  }

  /***************************************************************************
   * 사용자 정보 수정
   ***************************************************************************/
  const fnModify = () => {
    const empId = $("#empId").val();
    $.esutils.href("/sysmanage/groupmanage/usermanage/request", {empId});
  }


</script>
<form name="userManageForm" id="userManageForm" method="post">
    <%--    <input type="hidden" id="CRT_BY" name="CRT_BY" value="<%= login != null ? login.get("EMP_ID") : "" %>"/>--%>
    <%--    <input type="hidden" id="MOD_BY" name="MOD_BY" value="<%= login != null ? login.get("EMP_ID") : "" %>"/>--%>

    <%--    <input type="hidden" id="EMP_ID" name="EMP_ID"/>--%>
    <%--    <input type="hidden" id="COMP_ID" name="COMP_ID" value=""/>--%>
    <%--    <input type="hidden" id="DIV_NM" name="DIV_NM" value=""/>--%>
    <%--    <input type="hidden" id="CHECK_S_EMP_ID" name="CHECK_S_EMP_ID" value=""/>--%>
    <%--    <input type="hidden" id="checksempid" name="checksempid" value="N"/>--%>
    <%--    <input type="hidden" id="checkIDFlag" name="checkIDFlag" value=""/>--%>
    <%--    <input type="hidden" id="checkJuminNoFlag" name="checkJuminNoFlag" value=""/>--%>
    <%--    <input type="hidden" id="checkNationFlag" name="checkNationFlag" value=""/>--%>
    <%--    <input type="hidden" id="checkCertiFlag" name="checkCertiFlag" value=""/>--%>
    <%--    <input type="hidden" id="checkBirthFlag" name="checkBirthFlag" value=""/>--%>
    <%--    <input type="hidden" id="fileUpload1Yn" value=""/>--%>
    <%--    <input type="hidden" id="fileUpload2Yn" value=""/>--%>
    <%--    <input type="hidden" id="CERTINO" value=""/>--%>

    <div id="search_area">
        <div class="search_content">
            <!--h1 class="txt_title01">회원정보 입력</h1-->
            <table cellpadding="0" cellspacing="0" border="0" class="view_board_type1">
                <caption>사용자 정보 상세보기</caption>
                <colgroup>
                    <col width="130px;"/>
                    <col width="220px;"/>
                    <col width="130px;"/>
                    <col width="220px;"/>
                </colgroup>
                <tbody>
                <tr>
                    <th scope="row">
                        <label>ID</label>
                    </th>
                    <td colspan="3" view-data="id">
                    </td>
                </tr>

                <tr>
                    <th scope="row" id="mem_pwd">
                        <label>비밀번호</label>
                    </th>
                    <td headers="memPwd" view-data="password">
                    </td>
                    <th scope="row" id="memPwdChk">
                        <label>비밀번호확인</label>
                    </th>
                    <td headers="memPwdChk" view-data="password">
                    </td>
                </tr>

                <tr>
                    <th scope="row">사업장</th>
                    <td view-data="divNm">
                    </td>
                    <th scope="row">퇴사여부</th>
                    <td view-data="htNm">
                    </td>
                </tr>

                <tr>
                    <th scope="row">업체명</th>
                    <td view-data="compNm">
                    </td>
                    <th scope="row">부서</th>
                    <td view-data="deptNm">
                    </td>
                </tr>

                <tr>
                    <th scope="row" id="memName">
                        <label>성명</label>
                    </th>
                    <td headers="memName" view-data="empNm">
                    </td>
                    <th scope="row">
                        <label>사번</label>
                    </th>
                    <td view-data="empId">
                    </td>
                </tr>

                <tr>
                    <th scope="row">
                        <label>직책</label>
                    </th>
                    <td view-data="jcNm">
                    </td>
                    <th scope="row" id="mem_position">
                        <label>직위</label>
                    </th>
                    <td view-data="jwNm">
                    </td>
                </tr>

                <tr>
                    <th id="setNation" scope="row">
                        <label>생년월일</label>
                    </th>
                    <td id="setNationBtn" headers="setNation" view-data="birthNo">
                    </td>
                    <th scope="row">
                        <label>상주/비상주</label>
                    </th>
                    <td headers="setNation" view-data="resideYn">
                    </td>
                </tr>

                <tr>
                    <th scope="row">
                        <label>휴대번호</label>
                    </th>
                    <td colspan="3" view-data="telNo1">
                    </td>
                </tr>
                <tr>
                    <th scope="row">
                        <label>사내번호</label>
                    </th>
                    <td colspan="3" view-data="telNo2">
                    </td>
                </tr>
                <tr>
                    <th scope="row">
                        <label>메일주소</label>
                    </th>
                    <td view-data="email">
                    </td>
                    <th>
                        <label>입사일</label>
                    </th>
                    <td view-data="joinDt">
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label>우편번호</label></th>
                    <td view-data="zipCode">
                    </td>
                    <th>
                        <label>퇴사일</label></th>
                    <td view-data="retireDt">
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label>기본 주소</label></th>
                    <td colspan="3" view-data="addr1">
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label>상세 주소</label></th>
                    <td colspan="3" view-data="addr2">
                    </td>
                </tr>
                </tbody>
            </table>

            <!-- 버튼 -->
            <div class="buttonGroup">
                <div class="leftGroup">
                    <span class="button bt_s2"><button type="button" style="width:50px;" onclick="javascript:fnList();" tabindex="8">목록</button></span>
                    <c:if test="${emp_id == crt_by}">
                        <span class="button bt_s1"><button type="button" style="width:50px;" onclick="javascript:fnModify();" tabindex="6">수정</button></span>
                    </c:if>
                </div>
            </div>
            <!-- 버튼 끝 -->

        </div>
    </div>
</form>
