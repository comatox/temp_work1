<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@environment.getProperty('ifaccess.welcome.url')" var="welcomeUrl"/>
<script type="text/javascript">
  let esecurityInoutasset;
  const inoutApplNo = ${param.inoutApplNo};

  $(document).ready(function () {
    esecurityInoutasset = new EsecurityInoutasset(inoutApplNo);
    esecurityInoutasset.inoutwrite("inoutwriteArea", [
      {all: "compNm"},
      {left: "inoutserialno", right: "empNm"},
      {left: "articlekndnoNm", right: "articlegroupnm"},
      {left: "inoutkndname", right: "writedate"},
      {left: "companynoNm", right: "returncompanyareakndNm"},
      {left: "sendYn", right: "indate"},
      {left: "outdate", right: "realindate"},
      {left: "outcompanykndNm", right: "prno"},
      {left: "outreasonidNm", right: "outreasonsubkndNm"},
      {all: "inoutetc"},
    ]);
    esecurityInoutasset.article("articleArea");

    esecurityInoutasset.callback(function(data) {
      console.log(data);
      const documentmoveknd = data.viewInfo.documentmoveknd;
      if (!documentmoveknd || documentmoveknd === "0" || documentmoveknd === "4" || documentmoveknd === "5" || documentmoveknd === "2") {
        $("#documentmovekndBtn").show();
      } else {
        $("#documentmovekndBtn").hide();
      }

      const gridUtil = new GridUtil({
        userData: data.history.emp
        , gridId: "historyArea"
        , isPaging: false
        , gridOptions: {
          width: 900,
          colData: [
            {
              headerName: "순번"
              , field: "rowNum"
              , width: "5%"
            },
            {
              headerName: "기존 담당자"
              , field: "beforeEmpNm"
              , width: "10%"
            },
            {
              headerName: "변경 담당자"
              , field: "changeEmpNm"
              , width: "10%"
            },
            {
              headerName: "결재자"
              , field: "requestEmpNm"
              , width: "10%"
            },
            {
              headerName: "승인/부결 사유"
              , field: "etc"
              , width: "55%"
            },
            {
              headerName: "결재상태"
              , field: "approvalstateNm"
              , width: "10%"
            },
          ]
        }
      });
      gridUtil.init();
    });
  });

  function fn_list() {
    $.esutils.href("/inoutasset/inoutchange/empchange/list");
  }

  function fn_changeInDate() {
    $.esutils.href("/inoutasset/inoutchange/empchange/request", {inoutApplNo})
  }
</script>
<div id="inoutwriteArea"></div>
<div id="articleArea"></div>
<h1 class="txt_title01">담당자변경 신청이력</h1>
<div id="historyArea"></div>
<div class="buttonGroup">
    <div class="leftGroup">
        <span class="button bt_s1" id="documentmovekndBtn" style="display:none;"><button type="button" style="width: 150px;" onclick="javascript:fn_changeInDate();">담당자변경 신청</button></span>
        <span class="button bt_s2"><button type="button" style="width: 50px;" onclick="javascript:fn_list();">목록</button></span>
    </div>
</div>