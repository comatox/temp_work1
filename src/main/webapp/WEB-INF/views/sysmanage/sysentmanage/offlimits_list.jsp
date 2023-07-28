<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    $.esutils.rangepicker([["[name=crtDtmFr]", "[name=crtDtmTo]"]]);

    gridUtil = new GridUtil({
      url: "/api/sysmanage/sysmanage/envrEntmng/offLimits/list"
      , isPaging: true
      , gridOptions: {
        colData: [
          {
            headerName: "상태"
            , field: "delYn"
            , formatter: cellValue => cellValue === "Y" ? "탈퇴" : cellValue === "H" ? "승인대기" : cellValue === "Y" ? "휴면" : ""
          },
          {
            headerName: "업체명"
            , field: "compKoNm"
            , align: "left"
          },
          {
            headerName: "성명"
            , field: "empNm"
            , align: "left"
            , formatter: (cellValue, _, row) => `\${cellValue} \${row.jwNm || ''}`
          },
          {
            headerName: "국적"
            , field: "nationNm"
          },
          {
            headerName: "생년월일"
            , field: "juminNo"
          },
          {
            headerName: "여권번호"
            , field: "passportNo"
          },
          {
            headerName: "입주사통합사번"
            , field: "smartIdcard"
          },
          {
            headerName: "출입제한기간"
            , field: "denyStrtDt"
            , formatter: (cellValue, _, row) => row.denyStrtDt && row.denyEndDt ? `\${row.denyStrtDt} ~ \${row.denyEndDt}` : ""
          },
          {
            headerName: "출입제한등록자"
            , field: "crtBy"
            , formatter: (cellValue, _, row) => row.denyStrtDt && row.denyEndDt ? cellValue : ""
          },
          {
            headerName: "탈퇴일자"
            , field: "withdrawDtm"
          },
          {
            headerName: "최초가입일"
            , field: "crtDtm"
          },
          {
            headerName: "최종로그인"
            , field: "loginDtm"
          },
          {
            headerName: "인증날짜"
            , field: "certiDt"
          },
        ]
        , onRowClicked: ({originRowData}) => {
          $.esutils.href("/sysmanage/sysentmanage/offlimits/view", {ioEmpId: originRowData.ioEmpId});
        }
      },
      search: {
        formId: "formGrid"
        , buttonId: "searchBtn"
      }
    });

    //grid init
    gridUtil.init();
  });
</script>
<div id="search_area">
    <!-- 검색 -->
    <div class="search_content">
        <form id="formGrid">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board01">
                <colgroup>
                    <col width="18%"/>
                    <col width="43%"/>
                    <col width="15%"/>
                    <col width="24%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>처리상태</label></th>
                    <td>
                        <input type="radio" name="limitsYn" id="limitsYnN" value="N" class="border_none"/><label for="limitsYnN">출입가능</label>
                        <input type="radio" name="limitsYn" id="limitsYnY" value="Y" class="border_none"/><label for="limitsYnY">출입불가</label>
                        <input type="radio" name="limitsYn" id="limitsYn" value="" class="border_none" checked/><label for="limitsYn">전체</label>
                    </td>
                    <th><label>업체명(한글)</label></th>
                    <td>
                        <input type="text" id="compKoNm" name="compKoNm" value="" style="width:230px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label>회원(성명)</label></th>
                    <td>
                        <input type="text" id="empNm" name="empNm" value="" style="width:230px;"/>
                    </td>
                    <th><label>생년월일</label></th>
                    <td>
                        <input type="text" id="juminNo" name="juminNo" value="" style="width:230px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label>회원가입일</label></th>
                    <td>
                        <input type="text" id="crtDtmFr" name="crtDtmFr" style="width: 80px;" value=""/>
                        <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgCrtStrtDt"/>
                        ~
                        <input type="text" id="crtDtmTo" name="crtDtmTo" style="width: 80px;" value=""/>
                        <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="imgCrtToDt"/>
                    </td>
                    <th><label>여권번호</label></th>
                    <td>
                        <input type="text" id="passportNo" name="passportNo" value="" style="width:230px;" onkeypress="javascript:fn_press(event);"/>
                    </td>
                </tr>
                <tr>
                    <th><label>회원상태</label></th>
                    <td>
                        <input type="radio" name="userDelYn" id="userDelYnN" value="N" class="border_none" checked/><label for="userDelYnN">사용중</label>
                        <input type="radio" name="userDelYn" id="userDelYnS" value="S" class="border_none"/><label for="userDelYnS">휴면</label>
                        <input type="radio" name="userDelYn" id="userDelYnY" value="Y" class="border_none"/><label for="userDelYnY">탈퇴</label>
                        <input type="radio" name="userDelYn" id="userDelYnH" value="H" class="border_none"/><label for="userDelYnH">승인대기</label>
                        <input type="radio" name="userDelYn" id="userDelYn" value="" class="border_none"/><label for="userDelYn">전체</label>
                    </td>
                    <th><label>핸드폰번호</label></th>
                    <td>
                        <input type="text" id="searchHpNo" name="searchHpNo" value="" style="width:230px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label>사업자등록번호</label></th>
                    <td colspan="1">
                        <input type="text" id="ioCompId" name="ioCompId" value="" style="width:230px;"/>
                    </td>
                    <th><label>입주사통합사번</label></th>
                    <td colspan="1">
                        <input type="text" id="smartIdcard" name="smartIdcard" value="" style="width:230px;"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l1"><button type="button" id="searchBtn" style="width:50px;">검색</button></span>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 그리드 영역 시작 -->
<div id="grid"></div>
<!-- 그리드 영역 종료 -->