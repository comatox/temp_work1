<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

  let gridUtil;
  let esecurityApproval;

  let viewData = {};

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

    const {tmpcarAppNo} = $.esutils.getUrlParam();
    $("#tmpcarAppNo").val(tmpcarAppNo);

    $.esutils.getApi("/api/entmanage/safetyCarPass/view/${param.tmpcarAppNo}", {}, (response) => {
      console.log("■■ response ■■", response);
      const {safetyCarPassView = {}, safetyCarPassUserList = []} = response.data;
      viewData = safetyCarPassView;
      $.esutils.renderData("safetyCarPassForm", safetyCarPassView);

      initGrid(safetyCarPassUserList);
      gridUtil.init();

      esecurityApproval = new EsecurityApproval("approvalWrap");
      esecurityApproval.view(safetyCarPassView.docId);
    });
  }

  /***************************************************************************
   * Grid initializing
   ***************************************************************************/
  const initGrid = (data) => {
    gridUtil = new GridUtil({
      userData: data
      , isPaging: false
      , gridOptions: {
        colData: [
          {
            headerName: "소속/회사"
            , field: "compNm"
          },
          {
            headerName: "부서"
            , field: "deptNm"
          },
          {
            headerName: "직급"
            , field: "jwNm"
          },
          {
            headerName: "성명"
            , field: "empNm"
          },
          {
            headerName: "장비종류"
            , field: "carKnd"
          },
          {
            headerName: "차량번호"
            , field: "carNo"
          },
          {
            headerName: "전화번호"
            , field: "hpNo"
          },
        ]
      }
    });
  }

  /***************************************************************************
   * 파일 다운로드
   ***************************************************************************/
  const fnFileDown = () => {
    console.log("■■ viewData ■■", viewData);
    
    var tempId = row.IOEMPINFO.FILE_CAR_ADDR;
    var tempPath = tempId.substring(0,tempId.lastIndexOf("\\"));
    var tempRootPath = "<%=fileRootPath%>";
    tempRootePath = "data\\safetycar\\" + row.IOEMPINFO.CRT_DTM + "\\";

    $("#FILE_NAME").val(row.IOEMPINFO.FILE_CAR_NM);
    $("#FILE_ID").val(tempId.substring(tempId.lastIndexOf("\\")+1));
    $("#FILE_PATH").val(tempRootePath);


    const filePath = viewData.;
    const fileId = viewData.;
    window.open("/eSecurity/data/" + filePath + fileId, '사진정보보기', 'width=400,height=600,menubar=no,resizable=yes,location=no');
  };

  /***************************************************************************
   * 목록 이동
   ***************************************************************************/
  const fnList = () => {
    const operateId = "${param.empId}";

    console.log("■■ operateId ■■", operateId);
    if (operateId === "ADMIN" || operateId === "R_SKCAR") {
      $.esutils.href("/entmanage/carpassadmin/safetycarpassprogress/list");
    } else {
      $.esutils.href("/entmanage/safetycarpass/list");
    }
  }

  /***************************************************************************
   * 사용자 정보 수정
   ***************************************************************************/
  const fnModify = () => {
    const tmpcarAppNo = $("#tmpcarAppNo").val();
    $.esutils.href("/entmanage/safetycarpass/request", {tmpcarAppNo});
  }

</script>
<form name="safetyCarPassForm" id="safetyCarPassForm" method="post">
    <input type="hidden" id="compId" name="compId" view-data="compId"/>
    <input type="hidden" id="deptId" name="deptId" view-data="deptId"/>
    <input type="hidden" id="jwId" name="jwId" view-data="jwId"/>
    <input type="hidden" id="empId" name="empId" view-data="empId"/>
    <input type="hidden" id="applCompId" name="applCompId" view-data="applCompId"/>
    <input type="hidden" id="applEmpId" name="applEmpId" view-data="applEmpId"/>
    <input type="hidden" id="applEmpNm" name="applEmpNm" view-data="applEmpNm"/>
    <input type="hidden" id="applDeptId" name="applDeptId" view-data="applDeptId"/>
    <input type="hidden" id="applDeptNm" name="applDeptNm" view-data="applDeptNm"/>
    <input type="hidden" id="applJwNm" name="applJwNm" view-data="applJwNm"/>
    <input type="hidden" id="schemaNm" name="schemaNm" view-data="schemaNm" value="SAFETY_CAR_PASS"/>
    <input type="hidden" id="docNm" name="docNm" view-data="docNm" value="안전작업차량 출입신청"/>
    <input type="hidden" id="vstComp" name="vstComp" view-data="vstComp"/>
    <input type="hidden" id="searchFlag" name="searchFlag" view-data="searchFlag" value="N"/>
    <input type="hidden" id="tmpcarAppNo" name="tmpcarAppNo" view-data="tmpcarAppNo"/>
    <input type="hidden" id="docId" name="docId" view-data="docId"/>
    <input type="hidden" id="filePhoto" name="filePhoto" view-data="filePhoto"/>
    <input type="hidden" id="fileName" name="fileName" view-data="fileName"/>
    <input type="hidden" id="fileId" name="fileId" view-data="fileId"/>
    <input type="hidden" id="filePath" name="filePath" view-data="filePath"/>
    <div id="search_area">
        <div class="search_content">
            <h1 class="txt_title01">신청대상자</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="18%"/>
                    <col width="32%"/>
                    <col width="18%"/>
                    <col width="32%;"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>사업장명</th>
                    <td id="compNm" view-data="compNm">
                    </td>
                    <th>부서</th>
                    <td id="deptNm" view-data="deptNm">
                    </td>
                </tr>
                <tr>
                    <th>성명 / 직위</th>
                    <td id="empJwNm" view-data="empJwNm">
                    </td>
                    <th>전화번호</th>
                    <td id="telNo" view-data="telNo">
                    </td>
                </tr>
                </tbody>
            </table>
            <h1 class="txt_title01">예약일자</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="18%"/>
                    <col width="*"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>출입기간<span class="necessary">&nbsp;</span>
                    </label></th>
                    <td>
                        <input type="text" id="strtDt" name="strtDt" view-data="strtDt" size="10" readonly class="readonly"/>
                        &nbsp;&nbsp;~&nbsp;&nbsp;
                        <input type="text" id="endDt" name="endDt" view-data="endDt" size="10" readonly class="readonly"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <h1 class="txt_title01">작업내용</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="18%"/>
                    <col width="80%"/>
                    <col width="18%"/>
                    <col width="80%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><label>작업명 <span class="necessary">&nbsp;</span> </label></th>
                    <td colspan="3" id="applRsn" name="applRsn" view-data="applRsn"></td>
                </tr>
                <tr>
                    <th>작업 사유</th>
                    <td colspan="3" rowspan="3" id="applObj" name="applObj" view-data="applObj"></td>
                </tr>
                </tbody>
            </table>
            <h1 class="txt_title01">출입자</h1>
            <div id="grid" style="width:100%">
            </div>

            <%--            <% if (!OPERATE_ID.equals("R_SKCAR")) {%>--%>
            <h1 class="txt_title01">증빙서류</h1>
            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="18%"/>
                    <col width="82%"/>
                </colgroup>
                <tbody>
                <tr id="file1">
                    <th>작업증빙자료</th>
                    <td><span class="button bt_s1"><button type="button" style="width:80px;" onclick="javascript:fnFileDown();" title="사진보기">사진보기</button></span></td>
                </tr>
                </tbody>
            </table>
            <div id="approvalWrap"></div>
            <!-- 버튼 -->
            <div class="buttonGroup">
                <div class="leftGroup">
                    <span class="button bt_s2"><button type="button" style="width:50px;" onclick="javascript:fnList();" tabindex="8">목록</button></span>
                    <span class="button bt_s1"><button type="button" style="width:50px;" onclick="javascript:fnModify();" tabindex="6">수정</button></span>
                </div>
            </div>
            <!-- 버튼 끝 -->
        </div>
    </div>
</form>
