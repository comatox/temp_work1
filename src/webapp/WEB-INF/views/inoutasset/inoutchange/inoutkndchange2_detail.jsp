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
      const notinchangeknd = data.viewInfo.notinchangeknd;
      if (!notinchangeknd || notinchangeknd === "0" || notinchangeknd === "4" || notinchangeknd === "5" || notinchangeknd === "2") {
        $("#notinchangekndBtn").show();
      } else {
        $("#notinchangekndBtn").hide();
      }

      const gridUtil = new GridUtil({
        userData: data.history.inoutknd
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
              headerName: "기존 반입구분"
              , field: "beforeinoutkndNm"
              , width: "12%"
            },
            {
              headerName: "변경 반입구분"
              , field: "changeinoutkndNm"
              , width: "12%"
            },
            {
              headerName: "요청자"
              , field: "apprEmpNm"
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
              , width: "44%"
            },
            {
              headerName: "결재"
              , field: "approvalstateNm"
              , width: "7%"
            },
          ]
        }
      });
      gridUtil.init();
    });
  });

  function fn_list() {
    $.esutils.href("/inoutasset/inoutchange/inoutkndchange2/list");
  }

  function fn_changeInDate() {
    $.esutils.href("/inoutasset/inoutchange/inoutkndchange2/request", {inoutApplNo})
  }
</script>
<div id="inoutwriteArea"></div>
<div id="articleArea"></div>
<h1 class="txt_title01">반입불요전환 신청이력</h1>
<div id="historyArea"></div>
<div class="buttonGroup">
    <div class="leftGroup">
        <span class="button bt_s1" id="notinchangekndBtn" style="display:none;"><button type="button" style="width: 150px;" onclick="javascript:fn_changeInDate();">반입불요전환 신청</button></span>
        <span class="button bt_s2"><button type="button" style="width: 50px;" onclick="javascript:fn_list();">목록</button></span>
    </div>
</div>