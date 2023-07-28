<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ page import="org.apache.commons.lang3.math.NumberUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="<%= request.getContextPath() %>"></c:set>
<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession(false);
    //    if(session == null){ //세션이 null을 지니면 페이지전환
    //        System.out.println("##################### Session is null #####################");
    //        response.sendRedirect("/main");
    //        return;
    //    }
    //out.flush();
    Map login = (Map) request.getSession().getAttribute("Login");
    //    if(request.getSession().getAttribute("Login") != null) {
    //        login = (Map)request.getSession().getAttribute("Login");
    //        System.out.println("##################### login : "+login.get("EMP_ID")+" #####################");
    //
    //        Iterator<String> mapIter = login.keySet().iterator();
    //        while(mapIter.hasNext()){
    //            String key = mapIter.next();
    //        }
    //
    //    } else {
    //        System.out.println("##################### login is null #####################");
    //        response.sendRedirect("/main");
    //        return;
    //    }

    //    IDataSet responseData = (IDataSet)request.getAttribute(Constants.RESULT_DATASET);
    //    IResultMessage resultMessage = null;
    //    if(responseData != null){
    //        resultMessage = responseData.getResultMessage();
    //    }
    //
    //    int REQUEST_COUNT  = NumberUtils.toInt(responseData.getField("REQUEST_COUNT"));
    //    int APPROVAL_COUNT = NumberUtils.toInt(responseData.getField("APPROVAL_COUNT"));
    //    int DOC_PLEDGE_COUNT = NumberUtils.toInt(responseData.getField("DOC_PLEDGE_COUNT"));
    //    int DOC_PLEDGE_CT = NumberUtils.toInt(responseData.getField("DOC_PLEDGE_CT"));  // 남은 서명건수
    //    int REGULAR_EXCPT_COUNT = NumberUtils.toInt(responseData.getField("REGULAR_EXCPT_COUNT"));  // 사전정지예외
    //    int CERTI_COUNT = NumberUtils.toInt(responseData.getField("CERTI_COUNT"));  // 구성원인증
    //    int REGULAR_CANCEL_COUNT = NumberUtils.toInt(responseData.getField("REGULAR_CANCEL_COUNT"));    // 사후정지해지
    ////  int SUBCONTMOVE_COUNT = NumberUtils.toInt(responseData.getField("SUBCONTMOVE_COUNT"));  // 도급업체인력변경
    ////  int COORPVENDOR_CNT = NumberUtils.toInt(responseData.getField("COORPVENDOR_CNT"));  // 대표관리자 접수
    //    int CORRPLAN_CNT = NumberUtils.toInt(responseData.getField("CORRPLAN_CNT"));    // 시정계획서 접수
    //    int BASIC_ORDER_OATH_CNT = NumberUtils.toInt(responseData.getField("BASIC_ORDER_OATH_CNT")); //사내기초질서 준수 서약서
    //    int INOUT_RCV_CNT = NumberUtils.toInt(responseData.getField("INOUT_RCV_CNT")); // 업체물품접수
    //
    //    /* 접수함 추가  시작 */
    //    int RECEIVE_VST = NumberUtils.toInt(responseData.getField("RECEIVE_VST"));
    ////  int RECEIVE_TMP = NumberUtils.toInt(responseData.getField("RECEIVE_TMP"));
    //    int RECEIVE_REG = NumberUtils.toInt(responseData.getField("RECEIVE_REG"));
    ////  int RECEIVE_COMP = NumberUtils.toInt(responseData.getField("RECEIVE_COMP"));
    //    /* 접수함 추가  종료 */
    //
    //    IRecordSet QNA_LIST    = responseData.getRecordSet("QNA_LIST");
    //    IRecordSet NOTICE_LIST = responseData.getRecordSet("NOTICE_LIST");
    //    IRecordSet MENU_LIST   = responseData.getRecordSet("MENU_LIST");
    //
    //    String EMP_ID = StringUtils.defaultIfEmpty(login != null ? login.get("EMP_ID").toString() : "", "");
    //    String COMP_ID = StringUtils.defaultIfEmpty(login != null ? login.get("COMP_ID").toString() : "", "");
    //
    //    String schemaId = responseData.getField("SCHEMAID");
    //    if(schemaId == null) schemaId = "";
    //
    //    String userLoginId = StringUtils.defaultIfEmpty(login != null ? login.get("EMP_ID").toString() : "", "");
    //    if(userLoginId.equals("9111434")){
    //        System.out.println("#   request.getSession(false).getMaxInactiveInterval() : "+request.getSession(false).getMaxInactiveInterval()+"  #");
    //    }
%>
<%--<c:set var="EMP_ID" value="<%= EMP_ID %>"></c:set>
<c:set var="COMP_ID" value="<%= COMP_ID %>"></c:set>--%>
<c:set var="EMP_ID" value="2049952"></c:set>
<c:set var="COMP_ID" value="1010111101"></c:set>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
    <script>
      // topmenu



      var timer_main = "";

      function openNav(idx) {
        $(".depth2", idx).css("height", "30px").stop().slideDown(200);
        $(">a>img", idx).stop().animate({marginTop: '-25px'}, 200);
      }

      function closeNav(idx) {
        $(".depth2", idx).css("height", "0").stop().slideUp(200);
        $(">a>img", idx).stop().animate({marginTop: '0'}, 200);
      }

      function openNavSub(idx) {
        $(".depth3", idx).css("height", "29px").stop().slideDown(200);
        $(idx).parents(".depth2").find(".slogan").hide();
        $(">a>img", idx).stop().animate({marginTop: '-29px'}, 200);
      }

      function closeNavSub(idx) {
        $(".depth3", idx).css("height", "0").stop().slideUp(200);
        $(">a>img", idx).stop().animate({marginTop: '0'}, 200);
      }

      function onMenu_main() {
        $("#Mheader .nav>li.on").each(function () {
          openNav($(this));
          $("#Mheader .nav>li.on>.depth2 li.on").each(function () {
            openNavSub($(this));
          });
          $("#Mheader .depth2>ul>li.on").each(function () {
            if ($(".depth3", this).size() == 0) {
              $(this).parents(".depth2").find(".slogan").show();
            }
          });
        });
      }

      //$(function() {
      <%--$(document).ready(function(){--%>

      <%--    var oathCnt = <%=BASIC_ORDER_OATH_CNT %>--%>

      <%--    if(oathCnt == 0){--%>
      <%--        window.open('/eSecurity/insNet/common/popUp/InnerBasicOrderWrittenOath.jsp', '', 'width=600,height=780,menebar=no,location=no');--%>
      <%--    }--%>

      <%--    var n_param = {--%>
      <%--        "nc_trId"  : "fmHeader",--%>
      <%--        "callback" : "",--%>
      <%--        "CRR_AUTH_ID" : '<%= login != null ? login.get("AUTH_ID") : "" %>',--%>
      <%--        "CRR_EMP_ID" : '<%= login != null ? login.get("EMP_ID") : "" %>'--%>
      <%--    };--%>
      <%--    e_Util.callcommonByJson(n_param, function(callback) {--%>
      <%--        var jsonData = JSON.parse(callback);--%>
      <%--        var row = jsonData.MENU_LIST_recordSet;--%>
      <%--        var html = "";--%>
      <%--        for(var i = 0; i < row.length; i++) {--%>
      <%--            if(row[i].PARENTID == "P"){--%>
      <%--                html += "<li id='"+row[i].ID+"_DEPTH_1'><a href=#> <img src='/esecurity/assets/common/images/common/"+ row[i].IMG_NM+ "' alt='"+ row[i].TEXT +"'></a>";--%>
      <%--                html += "<div class='depth2'>";--%>
      <%--                // 출입관리--%>
      <%--                if(row[i].ID == "P01"){--%>
      <%--                    html += "<ul style='margin-left:0px'>";--%>

      <%--                    // 자산반출입--%>
      <%--                } else if(row[i].ID == "P08") {--%>
      <%--                    html += "<ul style='margin-left:80px'>";--%>
      <%--                    // 서비스 신청--%>
      <%--                } else if (row[i].ID == "P02") {--%>
      <%--                    html += "<ul style='margin-left:192px'>";--%>
      <%--                    // 보안활동--%>
      <%--                } else if (row[i].ID == "P03") {--%>
      <%--                    //html += "<ul style='margin-left:288px'>";--%>
      <%--                    html += "<ul style='margin-left:185px'>";--%>
      <%--                    // 헬프데스크--%>
      <%--                } else if (row[i].ID == "P04") {--%>
      <%--                    //html += "<ul style='margin-left:392px'>";--%>
      <%--                    html += "<ul style='margin-left:288px'>";--%>
      <%--                    // 통합결재--%>
      <%--                } else if (row[i].ID == "P05") {--%>
      <%--                    //html += "<ul style='margin-left:491px'>";--%>
      <%--                    html += "<ul style='margin-left:385px'>";--%>
      <%--                    // 환경설정--%>
      <%--                } else if (row[i].ID == "P06") {--%>
      <%--                    //html += "<ul style='margin-left:391px'>";--%>
      <%--                    html += "<ul style='margin-left:320px'>";--%>
      <%--                    // 통계--%>
      <%--                } else if (row[i].ID == "P07") {--%>
      <%--                    //html += "<ul style='margin-left:660px'>";--%>
      <%--                    html += "<ul style='margin-left:550px'>";--%>
      <%--                } else {--%>
      <%--                    html += "<ul style='margin-left:645px'>";--%>
      <%--                }--%>
      <%--                for(var j = 0; j < row.length; j++) {--%>
      <%--                    if(row[i].ID == row[j].PARENTID) {--%>
      <%--                        html += "<li id='"+row[j].ID+"_DEPTH_2'><a href=\"javascript:goMenu('" + row[j].ID + "', '" + row[j].URL + "', '')\"><img src='/esecurity/assets/common/images/common/"+ row[j].IMG_NM+ "' alt='"+ row[j].TEXT +"'></a></li>";--%>
      <%--                    }--%>
      <%--                }--%>
      <%--                html += "</ul>";--%>
      <%--                html += "</div>";--%>
      <%--                html += "</li>";--%>
      <%--            }--%>
      <%--        }--%>

      <%--        $(".nav").html(html);--%>


      <%--        var today = new Date();--%>
      <%--        var month = today.getMonth()+1;--%>
      <%--        var day = today.getDate();--%>
      <%--        var hour = today.getHours();--%>


      <%--        if(month < 10){--%>
      <%--            month = '0'+ month;--%>
      <%--        }--%>
      <%--        if(day < 10){--%>
      <%--            day = '0'+ day;--%>
      <%--        }--%>

      <%--        if(hour < 10){--%>
      <%--            hour = '0'+ hour;--%>
      <%--        }--%>

      <%--        var yyyymmhh = today.getFullYear()+''+month+''+day+''+hour;--%>
      <%--        var yyyymm = today.getFullYear()+''+month+''+day;--%>

      <%--        //if ( getCookie("security_notice9") != "done")--%>
      <%--        //{--%>
      <%--        //if( "20180801" <= yyyymm )--%>
      <%--        //{--%>
      <%--        //noticeWindow = window.open('/eSecurity/extNet/common/popUp/security_notice9.jsp', '', 'width=735,height=930,menebar=no,location=no, scroll=no');--%>
      <%--        //noticeWindow.opener = self;--%>
      <%--        //}--%>
      <%--        //}--%>
      <%--        if ( getCookie("secPopup") != "done")--%>
      <%--        {--%>
      <%--            noticeWindow = window.open('/eSecurity/insNet/common/popUp/security10_notice.jsp', '', 'width=600,height=1020,menebar=no,location=no,left=540,scrollbars=yes');--%>
      <%--        }--%>
      <%--        if ( getCookie("security20_notice") != "done") {--%>
      <%--            if( "20200721" >= yyyymm ){--%>
      <%--                window.open('/eSecurity/insNet/common/popUp/security20_notice.jsp', '', 'width=630,height=880,menebar=no,location=no, scroll=no');--%>
      <%--            }--%>
      <%--        }--%>
      <%--        if ( getCookie("TextNoticePopup") != "done")--%>
      <%--        {--%>
      <%--            if(yyyymm <= "20220707")--%>
      <%--            {--%>
      <%--                noticeWindow = window.open('/eSecurity/insNet/common/popUp/securityTextPopup.jsp', '', 'width=550,height=620,menebar=no,location=no,left=540');--%>
      <%--            }--%>
      <%--        }--%>
      <%--        /* 코로나 팝업 제거--%>
      <%--        if ( getCookie("security18_notice") != "done") {--%>
      <%--            window.open('/eSecurity/insNet/common/popUp/security18_notice.jsp', '', 'width=630,height=930,menebar=no,location=no, scroll=no');--%>
      <%--        }--%>
      <%--        */--%>
      <%--        //if ( getCookie("security21_notice") != "done") {--%>
      <%--        //    window.open('/eSecurity/insNet/common/popUp/security21_notice.jsp', '', 'width=640,height=950,menebar=no,location=no, scroll=no');--%>
      <%--        //}--%>

      <%--        // 보안교육 팝업--%>
      <%--        /*--%>
      <%--        if ( getCookie("security22_notice") != "done") {--%>
      <%--            window.open('/eSecurity/insNet/common/popUp/security22_notice.jsp', '', 'width=640,height=950,menebar=no,location=no, scroll=no');--%>
      <%--        }--%>
      <%--        */--%>
      <%--        /* 보안교육 팝업 제거--%>
      <%--        if ( getCookie("security20_education_notice") != "done") {--%>
      <%--            window.open('/eSecurity/insNet/common/popUp/security20_education_notice.jsp', '', 'width=630,height=930,menebar=no,location=no, scroll=no');--%>
      <%--        }--%>
      <%--        */--%>
      <%--        var DOC_PLEDGE_COUNT = '<%=DOC_PLEDGE_COUNT%>';--%>
      <%--        var DOC_PLEDGE_CT = '<%=DOC_PLEDGE_CT%>';--%>
      <%--        if(DOC_PLEDGE_COUNT > 0){--%>
      <%--            pledgeBanner.style.display = "";--%>
      <%--            if(DOC_PLEDGE_CT > 0){--%>
      <%--                //openDocPledge();--%>
      <%--            }--%>
      <%--        }--%>
      <%--        $("#Mheader .nav>li").each(function(){--%>
      <%--            $('.nav li').bind('click', function(){--%>
      <%--                /* openNav($(this));--%>
      <%--                   closeNav($(this).siblings("li")); */--%>
      <%--                if(!$(this).hasClass("on")){--%>
      <%--                    openNav($(this));--%>
      <%--                    closeNav($(this).siblings("li"));--%>
      <%--                } else {--%>
      <%--                    closeNav($(this));--%>

      <%--                }--%>
      <%--            });--%>
      <%--        });--%>

      <%--        $("#Mheader .nav .depth2>ul>li").each(function(){--%>
      <%--            $(this).mouseenter(function(){--%>
      <%--                closeNavSub($(this).siblings("li"));--%>
      <%--                openNavSub($(this));--%>
      <%--            });--%>
      <%--        });--%>
      <%--        $("#Mheader .nav .depth2").each(function(){--%>
      <%--            $(this).mouseleave(function(){--%>
      <%--                $(".depth3", this).css("height","0").stop().slideUp(200);--%>
      <%--                $(">ul>li>a>img", this).stop().animate({marginTop:'0'}, 200);--%>
      <%--            });--%>
      <%--        });--%>

      <%--        $("#Mheader .nav .depth3>ul>li").not(".on").each(function(){--%>
      <%--            $(this).mouseenter(function(){--%>
      <%--                openNavSub($(this));--%>
      <%--                closeNavSub($(this).siblings("li").not(".on"));--%>
      <%--            }).mouseleave(function(){--%>
      <%--                closeNavSub($(this).not(".on"));--%>
      <%--            });--%>
      <%--        });--%>
      <%--        $("#Mheader .nav").mouseleave(function(){--%>
      <%--            timer_main = setTimeout("onMenu_main()", 300);--%>
      <%--        });--%>
      <%--        $("#Mheader .nav").mouseenter(function(){--%>
      <%--            clearInterval(timer_main);--%>
      <%--        });--%>
      <%--        onMenu_main();--%>
      <%--    });--%>
      <%--});--%>

      function getCookie(name) {
        var nameOfCookie = name + "=";
        var x = 0;
        while (x <= document.cookie.length) {
          var y = (x + nameOfCookie.length);
          if (document.cookie.substring(x, y) == nameOfCookie) {
            if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
              endOfCookie = document.cookie.length;
            return unescape(document.cookie.substring(y, endOfCookie));
          }
          x = document.cookie.indexOf(" ", x) + 1;
          if (x == 0)
            break;
        }
        return "";
      }

      var goMenu = function (crrMenuId, menuUrl, depth3_menuId) {
        //alert(crrMenuId+'/'+menuUrl+'/'+depth3_menuId);
        // 메뉴에 대한 사용권한이 있는지 확인 : 필수
        $.ajax({
          url: "/envrSetup.json",
          dataType: "text",
          type: "POST",
          data: {
            "nc_trId": "pmMenuAuthCheckByUser",
            "EMP_ID": "<%= login != null ? login.get("EMP_ID") : "" %>",
            "MENU_ID": crrMenuId,
            "callback": ""
          },
          success: function (data) {
            var jsonData = eval('(' + data + ')');
            if (parseInt(jsonData.fields.COUNT) < 1) {
              alert("메뉴에 대한 사용 권한이 없습니다.");
            } else {
              var subMenuId = "";

              if (crrMenuId == 'P0401' && depth3_menuId == '') { // 헬프데스크-게시판
                depth3_menuId = 'P040101';
              }
              if (crrMenuId == 'P0402' && depth3_menuId == '') { // 헬프데스크-사이버신고
                depth3_menuId = 'P040202';
              }
              if (crrMenuId == 'P0501' && depth3_menuId == '') { // 결재함
                depth3_menuId = 'P0503';
              }
              if (crrMenuId == 'P0601') { // 출입 관리
                depth3_menuId = 'P060101';

                parent.frames[0].document.headerForm.CRR_UP_MENU_ID.value = crrMenuId;
                parent.frames[0].document.headerForm.MENU_DEPTH3.value = depth3_menuId;
                parent.frames[0].document.headerForm.CRR_MENU_ID.value = "";
                if (menuUrl != "") {
                  parent.frames[0].document.headerForm.nc_trId.value = "pmSecurityInfo_View";
                  parent.frames[0].document.headerForm.nc_target.value = menuUrl;
                  parent.frames[0].document.headerForm.action = "/envrSetup.cmd";
                  parent.frames[0].document.headerForm.target = "indexMain";
                  parent.frames[0].document.headerForm.submit();
                }
                return;

              }
              if (crrMenuId == 'P0602') { // 조직 관리
                depth3_menuId = 'P060203';
                subMenuId = 'P06020301';
              }
              if (crrMenuId == 'P0603') { // 권한 관리
                depth3_menuId = 'P060308';

              }
              if (crrMenuId == 'P0604' && depth3_menuId == '') { // 코드 관리
                depth3_menuId = 'P060401';
              }
              if (crrMenuId == 'P0201' && depth3_menuId == '') { // 서비스신청 - 일반보안
                depth3_menuId = 'P020101';
              }
              if (crrMenuId == 'P0202' && depth3_menuId == '') { // 서비스신청 - 전산보안
                depth3_menuId = 'P020208';
              }
              if (crrMenuId == 'P0801' && depth3_menuId == '') { // 자산반출입 - 자산반출입
                depth3_menuId = 'P080101';
              }
              parent.frames[0].document.headerForm.CRR_UP_MENU_ID.value = crrMenuId;
              parent.frames[0].document.headerForm.MENU_DEPTH3.value = depth3_menuId;
              parent.frames[0].document.headerForm.CRR_MENU_ID.value = subMenuId;
              if (menuUrl != "") {

                parent.frames[0].document.headerForm.action = menuUrl;
                parent.frames[0].document.headerForm.target = "indexMain";
                parent.frames[0].document.headerForm.submit();
              } else {
                alert("준비중입니다.");
              }
            }
          }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
          }
        });
      };

      function openDocPledge() {
        // 문서정리 확인서 팝업
        window.open('/eSecurity/insNet/Main/doc_pledge1.jsp', 'popup',
            'width=600,height=570,resizable=yes,scrollbars=no');
      }

      function openPledge() {
        // 영업비밀 보호 서약서 전자서명 팝업
        window.open('/eSecurity/insNet/Main/pledge_view.jsp', 'popup',
            'width=970,height=680,resizable=yes,scrollbars=no');
      }

      function noticeList() {
        parent.frames[0].document.headerForm.CRR_UP_MENU_ID.value = "P0401";
        parent.frames[0].document.headerForm.MENU_DEPTH3.value = "P040101";
        parent.frames[0].document.headerForm.CRR_MENU_ID.value = "";
        parent.frames[0].document.headerForm.action = "/eSecurity/insNet/Board/SecurityNoticeList.jsp";
        parent.frames[0].document.headerForm.target = "indexMain";
        parent.frames[0].document.headerForm.submit();
      }

      function noticeView(BOARD_NO) {
        parent.frames[0].document.headerForm.CRR_UP_MENU_ID.value = "P0401";
        parent.frames[0].document.headerForm.MENU_DEPTH3.value = "P040101";
        parent.frames[0].document.headerForm.CRR_MENU_ID.value = "";

        var url = "/eSecurity/insNet/Board/SecurityNoticeView.jsp";
        $("#nc_trId").val("pmSecurityNoticeViewSearch");
        $("#nc_target").val(url);
        $("#BOARD_NO").val(BOARD_NO);
        document.main_target.action = "/helpDesk.cmd";
        document.main_target.target = "_self";
        document.main_target.submit();
      }

      function qnaList() {
        parent.frames[0].document.headerForm.CRR_UP_MENU_ID.value = "P0401";
        parent.frames[0].document.headerForm.MENU_DEPTH3.value = "P040102";
        parent.frames[0].document.headerForm.CRR_MENU_ID.value = "";
        parent.frames[0].document.headerForm.action = "/eSecurity/insNet/Board/SecurityFaqList.jsp";
        parent.frames[0].document.headerForm.target = "indexMain";
        parent.frames[0].document.headerForm.submit();
      }

      function qnaView(BOARD_NO) {
        parent.frames[0].document.headerForm.CRR_UP_MENU_ID.value = "P0401";
        parent.frames[0].document.headerForm.MENU_DEPTH3.value = "P040102";
        parent.frames[0].document.headerForm.CRR_MENU_ID.value = "";

        var url = "/eSecurity/insNet/Board/SecurityFaqView.jsp";
        $("#nc_trId").val("pmSecurityFaqViewSearch");
        $("#nc_target").val(url);
        $("#BOARD_NO").val(BOARD_NO);
        document.main_target.action = "/helpDesk.cmd";
        document.main_target.target = "_self";
        document.main_target.submit();
      }

      var fn_logOut = function () {
        if (!confirm("로그아웃 하시겠습니까?")) return;
        sessionStorage.clear();
        parent.window.location = "/esecurity/logout";
      };

      var fn_home = function () {
        this.location = "/esecurity/main";
      };

      // 왼쪽메뉴 4Depth 클릭
      var goDetailMenu = function (crrUpMenuId, depth3_menuId, crrMenuId, menuUrl) {
        //alert(crrUpMenuId+':'+depth3_menuId+':'+crrMenuId+':'+menuUrl);
        $.ajax({
          url: "/envrSetup.json",
          dataType: "text",
          type: "POST",
          data: {
            "nc_trId": "pmMenuAuthCheckByUser",
            "EMP_ID": "<%= login != null ? login.get("EMP_ID") : "" %>",
            "MENU_ID": crrMenuId,
            "callback": ""
          },
          success: function (data) {
            var jsonData = eval('(' + data + ')');
            if (parseInt(jsonData.fields.COUNT) < 1) {
              alert("메뉴에 대한 사용 권한이 없습니다.");
            } else {
              parent.frames[0].document.headerForm.CRR_UP_MENU_ID.value = crrUpMenuId;
              parent.frames[0].document.headerForm.MENU_DEPTH3.value = depth3_menuId;
              parent.frames[0].document.headerForm.CRR_MENU_ID.value = crrMenuId;

              if (menuUrl != "") {
                parent.frames[0].document.headerForm.action = menuUrl;
                parent.frames[0].document.headerForm.target = "indexMain";
                parent.frames[0].document.headerForm.submit();
              } else {
                alert("준비중입니다.");
              }
            }
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
          }
        });
      };

      function fn_piPopupOpen() {
        window.open('/eSecurity/insNet/Main/pi_popup.jsp', '',
            'width=700px,height=650px,menubar=no,toolbar=no');
      }

      var fn_popup = function () {
        window.open('/eSecurity/insNet/common/popUp/security2_notice.jsp', '',
            'width=600,height=450,menebar=no,location=no');
      };

      <%--var schemaId = '<%=schemaId%>';--%>
      var schemaId = '1';
      if (schemaId == 'SCRT_VIEWUPPER') {   // 보안위규자 상위 결재자 (팀장 및 보안담당자)
        goDetailMenu('P0302', 'P030203', 'P03020301',
            '/eSecurity/insNet/SecurityActivity/SecurityConcern/SecurityManageItem/securityConcernCoEmp_ViolationList.jsp');
      } else if (schemaId == 'SCRT_VIEW') {  // 보안위규자 본인
        goDetailMenu('P0301', 'P030104', 'P03010403',
            '/eSecurity/insNet/SecurityActivity/SecurityDailyLife/SecurityCorrPlan/corrPlanList.jsp');
      } else if (schemaId == "SPY") {
        fn_spy();
      } else if (schemaId == "ENTRUSTREQ") { // 결재 위임 수락 페이지
        goDetailMenu('P0501', 'P050102', 'P05010202',
            '/eSecurity/insNet/EnvrSetup/Entrust/entrustAcceptList.jsp');
      } else if (schemaId == "APV") {    // 통합결재 연계 페이지
        goMenu('P0501',
            '/eSecurity/insNet/IntgrtApprv/intgrtMyApprovalList.jsp?SEARCH_APPR_RESULT=0', '');
      }

      function fn_spy() {
        goMenu('P0402', '/eSecurity/insNet/CyberSecurity/SpyList.jsp', 'P040201');
      }

      function fn_secure_movie() {

        goMenu('P0401', '/eSecurity/insNet/Board/SecurityMovie.jsp', 'P040104');
      }

      function fn_fileDownGuide(filePath, fileId, fileName) {
        fileId = 'D:\\NEXCORE\\runtime\\EarContent\\webapp\\web.war\\eSecurity\\data\\Security_Guidebook.ppt';
        fileName = 'Security_Guidebook.ppt';
        $.ajaxfileDownload
        (
            {
              url: '/common.file',  // 고정 값
              secureuri: false,            // 고정 값
              dataType: 'xml',            // 고정 값
              fileId: fileId,            // 서버에 저장되어 있는 파일 이름
              filePath: filePath,        // 서버에 저장된 풀 경로
              fileName: fileName,        // 파일 원본 이름
              nc_oper: "DOWNLOAD",       // 고정 값
              data: "",
              success: function (data, status) {
                var result = $(data).find("result").text();
                if (result == "SUCCESS") {

                } else {
                  alert($(data).find("message").text());
                }
              },
              error: function (data, status, e) {
                alert($(data).find("message").text());
              }
            }
        )
      }

      var fn_IoUserModify = function () {

        var param = {
          //em_id : encodeURL(emp_id),
          //em_nm : encodeURL($("#pic_emp_nm").val()),

        };

        var agentTmp = navigator.userAgent.toLowerCase();
        var browseTmp = "IE";
        if (agentTmp.indexOf("chrome") != -1) {
          browseTmp = "chrome";
        }
        if (navigator.appName == 'Netscape' && (navigator.userAgent.search('Trident') != -1
            || (agentTmp.indexOf("msie") != -1))) {
          browseTmp = "IE";
        }
        if (browseTmp == "IE") {
          e_Util.openModal("/eSecurity/insNet/common/popUp/pop_userManagerModify.jsp", param, "900",
              "730");
        } else {
          e_Util.openNewWin("/eSecurity/insNet/common/popUp/pop_userManagerModify.jsp", param,
              "popupTest", "900", "730");
        }

      };

      function fn_fileDown() {
        var fileName = "구성원_가이드_Security.pdf";
        //kirr
        document.main_target.action = "/eSecurity/extNet/common/fileDown.jsp?fileName=" + fileName;
        document.main_target.submit();
      }

      function fn_fileDown2(fileName) {
        document.main_target.action = "/eSecurity/extNet/common/fileDown.jsp?fileName=" + fileName;
        document.main_target.submit();
      }


    </script>
</head>
<body>
<form id="main_target" name="main_target" method="post">
    <input type="hidden" id="nc_trId" name="nc_trId" value=""/>
    <input type="hidden" id="nc_target" name="nc_target" value=""/>
    <input type="hidden" id="BOARD_NO" name="BOARD_NO" value=""/>
    <input type="hidden" id="EMP_ID" name="EMP_ID" value="${EMP_ID}"/>
    <input type="hidden" id="COMP_ID" name="COMP_ID" value="${COMP_ID}"/>
</form>
<!-- Skip Navigation -->
<div id="skipNavi" title="스킵네비게이션">
    <ul>
        <li><a href="#contentsArea">본문으로 가기</a></li>
        <li><a href="#header">대메뉴로 가기</a></li>
        <li><a href="#leftarea">레프트 메뉴로 가기</a></li>
    </ul>
</div>
<hr/>


<!-- wrap start-->
<div class="wrap">

    <!-- index-->
    <div class="index">
        <!-- 1번째 단 -->
        <div class="approval">
            <h2>신청함</h2>
            <dl>
                <%--                <dt><a href="javascript:goMenu('P0501' , '/eSecurity/insNet/IntgrtApprv/intgrtMyRequestList.jsp?SEARCH_APPR_RESULT=0', 'P0502');">진행함(신청문서)</a></dt>--%>
                <%--                <dd><a href="javascript:goMenu('P0501' , '/eSecurity/insNet/IntgrtApprv/intgrtMyRequestList.jsp?SEARCH_APPR_RESULT=0', 'P0502');"><%= REQUEST_COUNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goMenu('P0501', '/eSecurity/insNet/IntgrtApprv/intgrtMyApprovalList.jsp?SEARCH_APPR_RESULT=0', '');">결재함(결재문서)</a></dt>--%>
                <%--                <dd><a href="javascript:goMenu('P0501', '/eSecurity/insNet/IntgrtApprv/intgrtMyApprovalList.jsp?SEARCH_APPR_RESULT=0', '');"><%= APPROVAL_COUNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0101' , 'P010102' , 'P01010203' , '/eSecurity/insNet/EntManage/Pass/regularPassList.jsp?APPL_STAT=Z0331001');">상시출입증 접수</a></dt>--%>
                <%--                <dd><a href="javascript:goDetailMenu('P0101' , 'P010102' , 'P01010203' , '/eSecurity/insNet/EntManage/Pass/regularPassList.jsp?APPL_STAT=Z0331001');"><%= RECEIVE_REG %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0102' , 'P010201' , 'P01020103' , '/eSecurity/insNet/EntManage/ReserveVisit/visitorProgress.jsp?appl_stat=2');">방문접수 </a></dt>--%>
                <%--                <dd><a href="javascript:goDetailMenu('P0102' , 'P010201' , 'P01020103' , '/eSecurity/insNet/EntManage/ReserveVisit/visitorProgress.jsp?appl_stat=2');"><%= RECEIVE_VST %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0101' , 'P010102' , 'P01010205' , '/eSecurity/insNet/EntManage/Pass/regularPassExcptList.jsp?APPL_STAT=Z0331001');">사전정지 예외신청</a></dt>--%>
                <%--                <dd><a href="javascript:goDetailMenu('P0101' , 'P010102' , 'P01010205' , '/eSecurity/insNet/EntManage/Pass/regularPassExcptList.jsp?APPL_STAT=Z0331001');"><%= REGULAR_EXCPT_COUNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0102' , 'P010201' , 'P01020104' , '/eSecurity/insNet/EnvrSetup/OffLimits/visitCertiList.jsp?SEARCH_TYPE=C');">방문객(구성원인증)</a></dt>--%>
                <%--                <dd><a href="javascript:goDetailMenu('P0102' , 'P010201' , 'P01020104' , '/eSecurity/insNet/EnvrSetup/OffLimits/visitCertiList.jsp?SEARCH_TYPE=C');"><%= CERTI_COUNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0101' , 'P010102' , 'P01010207' , '/eSecurity/insNet/EntManage/Pass/regularPassCancelList.jsp?APPL_STAT=Z0331001');">사후정지 해지신청</a></dt>--%>
                <%--                <dd><a href="javascript:goDetailMenu('P0101' , 'P010102' , 'P01010207' , '/eSecurity/insNet/EntManage/Pass/regularPassCancelList.jsp?APPL_STAT=Z0331001');"><%= REGULAR_CANCEL_COUNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0301' , 'P030104' , 'P03010401' , '/eSecurity/insNet/SecurityActivity/SecurityDailyLife/SecurityCorrPlan/corrPlanList.jsp?APPL_STAT=99');">시정계획서(구성원접수)</a></dt>--%>
                <%--                <dd><a href="javascript:goDetailMenu('P0301' , 'P030104' , 'P03010401' , '/eSecurity/insNet/SecurityActivity/SecurityDailyLife/SecurityCorrPlan/corrPlanList.jsp?APPL_STAT=99');"><%= CORRPLAN_CNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0801' , 'P080115' , 'P08011502' , '/eSecurity/insNet/EntManage/IoInoutRcv/ioInoutRcvRequestList.jsp?appl_stat=Z0331001');">업체물품접수</a></dt>--%>
                <%--                <dd><a href="javascript:goDetailMenu('P0801' , 'P080115' , 'P08011502' , '/eSecurity/insNet/EntManage/IoInoutRcv/ioInoutRcvRequestList.jsp?appl_stat=Z0331001');"><%= INOUT_RCV_CNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0101' , 'P010109' , 'P01010918' , '/eSecurity/insNet/EntManage/Pass/insSubcontMoveList.jsp?APPL_STAT=Z0331001');">도급업체 인력변경</a></dt> --%>
                <%--                    <dd><a href="javascript:goDetailMenu('P0101' , 'P010109' , 'P01010918' , '/eSecurity/insNet/EntManage/Pass/insSubcontMoveList.jsp?APPL_STAT=Z0331001');"><%= SUBCONTMOVE_COUNT %></a></dd>--%>
                <%--                <dt><a href="javascript:goDetailMenu('P0101' , 'P010104' , 'P01010401' , '/eSecurity/insNet/CoorpVendor/ioCompCoorpVendorList.jsp?APPL_STAT=Z0331001');">대표관리자 접수상신</a></dt>--%>
                <%--                    <dd><a href="javascript:goDetailMenu('P0101' , 'P010104' , 'P01010401' , '/eSecurity/insNet/CoorpVendor/ioCompCoorpVendorList.jsp?APPL_STAT=Z0331001');"><%= COORPVENDOR_CNT %></a></dd>--%>
            </dl>
        </div>

        <div class="bbs notice">
            <h2>산업보안 <strong>공지사항</strong></h2>
            <a href="javascript:noticeList();" class="more">더보기</a>
            <ul>
                <%--                <%--%>
                <%--                    if(NOTICE_LIST != null && NOTICE_LIST.getRecordCount() > 0){--%>
                <%--                        for(int i=0; i<6; i++) {--%>

                <%--                            String title = StringUtils.defaultIfEmpty(NOTICE_LIST.get(i, "TITLE"), "&nbsp;");--%>

                <%--                %>--%>
                <%--                <li><a href="javascript:noticeView('<%= NOTICE_LIST.get(i, "BOARD_NO") %>');"><%= title %></a><span><%= NOTICE_LIST.get(i, "CRT_DTM") %></span></li>--%>
                <%--                <%--%>
                <%--                        }--%>

                <%--                    }--%>

                <%--                %>--%>
            </ul>
        </div>

        <div class="bbs qna">
            <h2>산업보안 <strong>FAQ</strong></h2>
            <a href="javascript:qnaList();" class="more">더보기</a>
            <ul>
                <%--                <%--%>
                <%--                    if(QNA_LIST != null && QNA_LIST.getRecordCount() > 0){--%>
                <%--                        for(int i=0; i<6; i++) {--%>
                <%--                            String title = StringUtils.defaultIfEmpty(QNA_LIST.get(i, "TITLE"), "&nbsp;");--%>
                <%--                %>--%>
                <%--                <li><a href="javascript:qnaView('<%= QNA_LIST.get(i, "BOARD_NO") %>');"><%= title %></a><span><%= QNA_LIST.get(i, "CRT_DTM") %></span></li>--%>
                <%--                <%--%>
                <%--                        }--%>
                <%--                    }--%>
                <%--                %>--%>
            </ul>
        </div>

        <!-- //1번째 단 -->

        <!-- 2번째 단 -->
        <div class="mymenu">
            <div class="title">
                <h2><strong>자주 찾는</strong> 메뉴</h2>
                <p>e-Security에서 <br/>가장 많이 이용되는<br/> 메뉴를 보여드립니다</p>
            </div>
            <ul>
                <li>
                    <a href="javascript:goDetailMenu('P0102' , 'P010201' , 'P01020103' , '/eSecurity/insNet/EntManage/ReserveVisit/visitorProgress.jsp');"
                       class="visit">방문예약<br/>접수</a></li>
                <li>
                    <a href="javascript:goDetailMenu('P0104' , 'P010402' , 'P01040201' , '/eSecurity/insNet/EntManage/CarPassReqst/carPassReqstForm.jsp');"
                       class="visitcar">방문차량<br/>신청</a></li>
                <li>
                    <a href="javascript:goDetailMenu('P0101' , 'P010112' , 'P01011201' , '/eSecurity/insNet/EntManage/EmpCard/empCardBuildRegRequest.jsp');"
                       class="pass1">출입건물신청<br/>(구성원)</a></li>
                <li>
                    <a href="javascript:goDetailMenu('P0101' , 'P010108' , 'P01010801' , '/eSecurity/insNet/EntManage/Pass/passBuildingRegRequest.jsp');"
                       class="pass2">출입건물신청<br/>(협력사)</a></li>
                <li>
                    <a href="javascript:goDetailMenu('P0801' , 'P080101' , 'P08010108' , '/eSecurity/insNet/Pios/InOut/inOutInfo.jsp');"
                       class="inout">자산<br/>반출입</a></li>
                <li>
                    <a href="javascript:goDetailMenu('P0101' , 'P010101' , 'P01010102' , '/eSecurity/insNet/EntManage/EmpCard/empCardRequest.jsp');"
                       class="idcard">사원증<br/>재발급</a></li>
                <li>
                    <a href="javascript:goDetailMenu('P0101' , 'P010102' , 'P01010203' , '/eSecurity/insNet/EntManage/Pass/regularPassList.jsp');"
                       class="passcard">상시출입증<br/>접수</a></li>
                <li>
                    <a href="javascript:goDetailMenu('P0301' , 'P030104' , 'P03010401' , '/eSecurity/insNet/SecurityActivity/SecurityDailyLife/SecurityCorrPlan/corrPlanList.jsp');"
                       class="correct">시정계획서<br/>작성</a></li>
                <!-- 빈 항목의 경우
                <li><a href="#" class="null"></a></li>
                -->
            </ul>
        </div>
        <!-- //2번째 단 -->
        <script>
          //$(function() {
          $(document).ready(function () {

            $('.tab_type01 .tab_item').each(function () {
              $(this).click(function () {
                $('.tab_type01 .tab_item').not(this).removeClass("selected");
                $(this).addClass("selected");

                var showContent = $(this).attr("rel");
                var hideContent = $('.tab_type01 .tab_item').not(this).attr("rel");
                var aaa = document.getElementById(showContent);
                var bbb = document.getElementById(hideContent);
                bbb.style.display = "none";
                aaa.style.display = "block";

                return false;
              });
            });
          });

        </script>
        <div class="tell">
            <!-- Tab Type01 -->
            <div class="tab_type01" id="tab00">
                <ul class="tab_list">
                    <li class="tab_item selected" rel="tab_contents1">
                        <a class="tab_link">이천 / 분당</a>
                    </li>
                    <li class="tab_item" rel="tab_contents2">
                        <a class="tab_link">청주</a>
                    </li>
                </ul>
            </div>
            <!-- //Tab Type01 -->
            <!-- Tab Contents -->
            <div class="tab_contents_wrap" rel="tab00">
                <!-- 이천 -->
                <div id="tab_contents1" class="tab_contents" style="display:block;">
                    <ul>
                        <li class="wide">시스템 문의(인증)<span>031-5185-2294</span></li>
                        <li class="wide">보안위규 관리<span>031-8094-7279</span></li>
                        <li class="wide">주2회 예외/해지<span>031-8094-7862</span></li>
                        <li class="wide">정문 안내실<span>031-5185-4052</span></li>
                        <li class="wide">방문 예약<span>031-8094-7279</span></li>
                        <li class="wide">차량출입(임시/상시)<span>031-8094-7279</span></li>
                        <li class="wide">후문 안내실<span>031-5185-4132</span></li>
                        <li class="wide">사원증/상시출입증<span>031-5185-2299</span></li>
                        <li class="wide">차량출입(방문)<span>031-8094-7279</span></li>
                    </ul>
                </div>
                <!-- //이천 -->

                <!-- 청주 -->
                <div id="tab_contents2" class="tab_contents" style="display:none;">
                    <ul>
                        <li class="wide">시스템 문의(인증)<span>031-5185-2294</span></li>
                        <li class="wide">방문 예약<span>043-907-2152</span></li>
                        <li class="wide">주2회 예외/해지<span>031-8094-7862</span></li>
                        <li class="wide">1캠퍼스 정문<span>043-907-4444</span></li>
                        <li class="wide">사원증/상시출입증<span>043-907-3366</span></li>
                        <li class="wide">차량출입(임시)<span>043-907-2152</span></li>
                        <li class="wide">2캠퍼스 정문<span>043-907-7392</span></li>
                        <li class="wide">보안위규 관리<span>043-907-6765</span></li>
                        <li class="wide">차량출입(방문)<span>043-907-2152</span></li>
                        <li class="wide">3캠퍼스 정문<span>043-907-6012</span></li>
                        <li class="wide">　<span></span></li>
                        <li class="wide">　<span></span></li>
                        <li class="wide">4캠퍼스 남문<span>043-907-5555</span></li>
                    </ul>
                </div>
                <!-- //청주 -->
            </div>
            <!-- //Tab Contents -->
        </div>

        <div class="link">
            <ul>
                <li><a href="javascript:fn_piPopupOpen();" class="privacy"><span>개인정보</span>처리방침</a>
                </li>
                <li><a href="javascript:openDocPledge();" class="down"><span>보안담당자</span>교육자료모음집</a>
                </li>
                <li><a href="javascript: openPledge();" class="promise"><span>영업비밀보호</span>등 서약서</a>
                </li>
                <li><a href="javascript:fn_fileDown()" class="newwindow"><span>메뉴얼</span></a></li>
                <li><a href="javascript:fn_fileDown2('represecuroath.jpg');"
                       class="newwindow"><span>업체등록</span>서약서</a></li>
                <li><a href="javascript:fn_fileDown2('powerofattorney.jpg');"
                       class="newwindow"><span>대표자 보안</span>서약서/위임장</a></li>
            </ul>
            <a href="#" class="spy"><strong>산업스파이<span>신고</span></strong><span>1112(전사)</span></a>
        </div>
        <!-- //3번째 단 -->

    </div>
    <!-- //index-->

</div>
</body>
</html>
