<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@environment.getProperty('ifaccess.welcome.url')" var="welcomeUrl"/>
<script type="text/javascript">
  let gridUtil;
  $(document).ready(function () {
    $.esutils.rangepicker([["[name=denyStrtDt]", "[name=denyEndDt]"]]);

    $.esutils.renderData(
        "passForm",
        "/api/sysmanage/sysmanage/envrEntmng/offLimitsView/${param.ioEmpId}",
        (data) => {
          if (data.eshDt && data.eshDt !== "N") {
            const today = $.esutils.getToday("");
            console.log("today = ", today)
            const betweenDt = $.esutils.getDaysBetweenMonths(today, data.eshDt, '-');
            if (betweenDt >= 180) {
              $("[view-data=edu]").text("미이수(6개월경과)");
            } else {
              $("[view-data=edu]").text("이수");
            }
          } else {
            $("[view-data=edu]").text("미이수");
          }

          if (!data.denyStrtDt) {
            $(":input[name=denyStrtDt]").val($.esutils.getToday());
          }

          if (data.webPhotoAddr) {
            $("[view-data=ioEmpPic]").html(`<img src="${welcomeUrl}/\${data.webPhotoAddr}" style="width:110px; height:140px;">`);
          }
          $("[name=denyCtgCd]").val("TEMP_");

          // login 정보 설정
          $("[name=crtBy]").val(global.empId);
          $("[name=modBy]").val(global.empId);
        }, {loading: true});

    gridUtil = new GridUtil({
      url: `/api/sysmanage/sysmanage/envrEntmng/offLimitsHistoryList/${param.ioEmpId}`
      , isPaging: false
      , gridOptions: {
        colData: [
          {
            headerName: "출입 제한기간"
            , field: "denyDt"
            , formatter: (cellvalue, _, data) => `\${data.denyStrtDt} ~ \${data.denyEndDt}`
          },
          {
            headerName: "제한사유"
            , field: "denyRsn"
          },
          {
            headerName: "등록/수정일"
            , field: "crtDtm"
          },
          {
            headerName: "등록/수정자"
            , field: "crtBy"
          },
          {
            headerName: "해제"
            , field: "delYn"
            , formatter: (cellvalue, options, data) =>
                cellvalue === "Y" ? "해제" : options.rowId === '1' ? `<span class='button bt_l2'><button onclick="javascript: fn_limitsDel('\${data.denyNo}', event);">해제</button></span>` : "등록"
          },
        ]
      },
    });

    //grid init
    gridUtil.init();
  });

  function fn_list() {
    $.esutils.href("/sysmanage/sysentmanage/offlimits/list");
  }

  function fn_save() {
    if (fn_validation()) {
      if (confirm("출입제한을 신규등록 하시겠습니까?\n출입증이 있는 경우 사용이 중지됩니다.")) {
        $("[name=delYn]").val("N");

        const param = $.esutils.getFieldsValue($("#passForm"));
        console.log("param >>> ", param);
        $.esutils.postApi("/api/sysmanage/sysmanage/envrEntmng/offLimitsDenyInsert", param, function (result) {
          if (result.data) {
            alert("등록하였습니다.");
            fn_list();
          } else {
            alert("오류가 발생하였습니다.");
          }
        });
      }
    }
  }

  function fn_validation() {
    // 시작일자
    if ($("[name=denyStrtDt]").val() == null || $("[name=denyStrtDt]").val() == "") {
      alert("제한기간 시작일자는 필수 항목입니다.");
      $("[name=denyStrtDt]").focus();
      return false;
    }
    // 종료일자
    if ($("[name=denyEndDt]").val() == null || $("[name=denyEndDt]").val() == "") {
      alert("제한기간 종료일자는 필수 항목입니다.");
      $("[name=denyEndDt]").focus();
      return false;
    }

    const toDay = $.esutils.getToday("");
    const ioStrtDt = $("[name=denyStrtDt]").val().replaceAll("-", "");
    const ioEndDt = $("[name=denyEndDt]").val().replaceAll("-", "");

    if (ioStrtDt < toDay) {
      alert("시작일자는 오늘날짜 이후로 지정하세요.");
      $("[name=denyStrtDt]").val("");
      $("[name=denyStrtDt]").focus();
      return false;
    }
    if (ioStrtDt > ioEndDt) {
      alert("시작일자가 종료일자보다 큽니다.");
      $("[name=denyStrtDt]").val("");
      $("[name=denyEndDt]").val("");
      $("[name=denyStrtDt]").focus();
      return false;
    }

    let denyCtgCd = "";
    $("[name=denyCtgCd]").each(function (index, value) {
      if ($(this).attr("checked")) {
        denyCtgCd = $(this).attr("value");
      }
    });
    if (denyCtgCd == null || denyCtgCd == "") {
      alert("제한 구분은 필수 항목입니다.");
      return false;
    }
    if (!$("[name=denyRsn]").val()) {
      alert("제한 사유는 필수 항목입니다.");
      $("[name=denyRsn]").focus();
      return false;
    }
    if ($("[name=denyRsn]").val().length > 500) {
      alert("제한 사유는 500자이내로 작성하세요.");
      return false;
    }
    return true;
  }

  function fn_date() {
    if ($("[name=allLimit]").is(":checked")) {
      $("[name=denyStrtDt]").val(getToday());
      $("[name=denyEndDt]").val("9999-12-31");
    } else {
      $("[name=denyEndDt]").val("");
    }
  }

  function fn_limitsDel(denyNo, event) {
    event.preventDefault();

    if (confirm("출입제한을 해제하시겠습니까?")) {
      let param = $.esutils.getFieldsValue($("#passForm"));
      param = {...param, denyNo}
      console.log("param >>> ", param);
      $.esutils.postApi("/api/sysmanage/sysmanage/envrEntmng/offLimitsDenyDelete", param, function (result) {
        if (result.data) {
          alert("출입제한을 해제하였습니다.");
          fn_list();
        } else {
          alert("오류가 발생하였습니다.");
        }
      });
    }
  }
</script>
<form name="passForm" id="passForm" method="post">
    <input type="hidden" id="ioEmpId" name="ioEmpId"/>
    <input type="hidden" id="crtBy" name="crtBy"/>
    <input type="hidden" id="modBy" name="modBy"/>
    <input type="hidden" id="acIp" name="acIp"/>
    <input type="hidden" id="filePic" name="filePic"/>

    <input type="hidden" id="ioEmpNm" name="ioEmpNm" value=""/>
    <input type="hidden" id="hpNoVal" name="hpNoVal" value=""/>
    <input type="hidden" id="idcardId" name="idcardId"/>
    <input type="hidden" id="cardNo" name="cardNo"/>
    <input type="hidden" id="nation" name="nation"/>
    <input type="hidden" id="ioinoutYn" name="ioinoutYn" value=""/>
    <input type="hidden" id="admEmail" name="admEmail" value=""/>

    <input TYPE="hidden" id="limitsYn" name="limitsYn"/>
    <input type="hidden" id="compKoNm" name="compKoNm"/>
    <input type="hidden" id="empNm" name="empNm"/>
    <input type="hidden" id="juminNo" name="juminNo"/>
    <input type="hidden" id="passportNo" name="passportNo"/>
    <input type="hidden" id="userDelYn" name="userDelYn"/>
    <input type="hidden" id="hpNo" name="hpNo"/>
    <input type="hidden" id="ioCompId" name="ioCompId"/>
    <input type="hidden" id="crtDtmFr" name="crtDtmFr"/>
    <input type="hidden" id="crtDtmTo" name="crtDtmTo"/>
    <input type="hidden" id="pageIndex" name="pageIndex"/>

    <!-- KIRR MODIFY  -->
    <input type="hidden" id="delYn" name="delYn"/>

    <input type="hidden" id="filePhoto" name="filePhoto"/>

    <div id="search_area">
        <!-- 검색 -->
        <div class="search_content">
            <table cellpadding="0" cellspacing="0" border="0" class="view_board" style="table-layout: fixed;">
                <colgroup>
                    <col width="154"/>
                    <col width=""/>
                    <col width="154"/>
                    <col width=""/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>사진</label></th>
                    <td view-data="ioEmpPic" colspan="3"></td>
                </tr>
                <tr>
                    <th><label>방문객(성명)</label></th>
                    <td view-data="ioEmpNm"></td>
                    <th><label>인증날짜</label></th>
                    <td view-data="nameChkNm"></td>
                </tr>
                <tr>
                    <th><label>E-Mail</label></th>
                    <td view-data="email"></td>
                    <th><label>보안/안전교육</label></th>
                    <td view-data="edu"></td>
                </tr>
                <tr>
                    <th><label>사업자등록번호</label></th>
                    <td view-data="ioCompId"></td>
                    <th><label>업체명(한글)</label></th>
                    <td view-data="compKoNm"></td>
                </tr>
                <tr>
                    <th><label>생년월일</label></th>
                    <td view-data="juminNo"></td>
                    <td style="display:none;"><input type="text" view-data="juminNo" name="JUMIN_NO" value=""></input></td>
                    <th><label>연락처</label></th>
                    <td view-data="hpNo"></td>
                    <td style="display:none;"><input type="text" view-data="hpNo" name="HP_NO" value=""></input></td>
                </tr>
                <tr>
                    <th><label>여권번호</label></th>
                    <td view-data="passportNo"></td>
                    <td style="display:none;"><input type="text" view-data="passportNoTd" name="PASSPORT_NO_TD" value=""></input></td>
                    <th><label>제한 구분</label><span class="necessary">&nbsp;</span></th>
                    <td>
                        <input type="radio" name="denyCtgCd" checked="checked" class="border_none"/><label>전체</label>
                    </td>
                </tr>
                <tr>
                    <th><label>입주사통합사번</label></th>
                    <td view-data="smartIdcard"></td>
                </tr>
                <tr>
                    <th><label>제한 기간</label><span class="necessary">&nbsp;</span></th>
                    <td colspan="3">
                        <input type="text" name="denyStrtDt" style="width: 80px;" value=""/>
                        <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="IMG_DENY_STRT_DT"/>
                        &nbsp;&nbsp;~&nbsp;&nbsp;
                        <input type="text" name="denyEndDt" style="width: 80px;" value=""/>
                        <img src="/esecurity/assets/common/images/common/ico_calendar.gif" class="middle" alt="날짜" id="IMG_DENY_END_DT"/>
                        &nbsp;&nbsp;
                        <input type="checkbox" name="allLimit" class="border_none" onclick="javascript:fn_date();">영구 정지
                    </td>
                </tr>
                <tr>
                    <th><label>사유</label><span class="necessary">&nbsp;</span></th>
                    <td colspan="3"><textarea view-data="denyRsn" name="denyRsn" style="width:90%; height:60px; "></textarea></td>
                </tr>
                </tbody>
            </table>
            <div class="searchGroup">
                <div class="centerGroup">
                    <span class="button bt_l2"><button type="button" style="width:50px;" onclick="javascript: fn_list();">목록</button></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <span class="button bt_l1 DEL_HIDE_BTN"><button type="button" style="width:80px;" onclick="javascript : fn_save();">출입제한</button></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <span class="button bt_l1 DEL_HIDE_BTN"><button type="button" style="width:120px;" onclick="javascript : fn_IoPassReg();">발급이력 생성</button></span>

                </div>
            </div>
        </div>
    </div>

    <h1 class="txt_title01">출입제한 이력</h1>
    <div id="grid"></div>
</form>