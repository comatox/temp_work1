<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map login = (Map) request.getSession().getAttribute("Login");
%>
<title>입주사-Security</title>
<meta name="title" content="입주사-Security" />
<meta name="Author" content="SK SHIELDUS" />
<meta name="description" content="입주사-Security" />
<meta name="keywords" content="입주사-Security" />
<script>
  $(document).ready(function () {
    // set menu list (async = false)
    $.esutils.setMenuList(global.empId, "${displayMenuId}", "${menuId}");

    function openNav(idx) {
      $(".depth2", idx).css("height", "30px").stop().slideDown(200);
      $(">a>img", idx).stop().animate({marginTop: '-25px'}, 200);
    }

    function closeNav(idx) {
      $(".depth2", idx).css("height", "0").stop().slideUp(200);
      $(">a>img", idx).stop().animate({marginTop: '0'}, 200);
    }

    if (global.menuInfo && global.menuInfo.list) {
      const depth1Menus = global.menuInfo.list.filter(d => d.depth === 1);
      $("#headerMenu").html(depth1Menus.map(d => {
            return `<li id="\${d.menuId}"><a href="#">\${d.menuNm}</a><div class="depth2"><ul>\${global.menuInfo.list.filter(d2 => d2.depth === 2 && d2.upMenuId === d.menuId).map(d2 => "<li><a href=" + d2.url + ">" + d2.menuNm + "</a></li>").join("")}</ul></div></li>`
          }).join("")
      );
      $('.nav li').bind('click', function () {
        openNav($(this));
        closeNav($(this).siblings("li"));
      });
    }

    // $.esutils.loading(true);

    /** example: getCode */
    // $.esutils.getCode("C009", function (result) {
    //   console.log(result);
    // });
    // $.esutils.getCode(["C009", "C010", "C011"], function (result) {
    //   console.log(result);
    // });

    /** example: renderCode (Select, Radio) */
    // $.esutils.renderCode([
    //   {type: "select", grpCd: "C009", targetId: "test1"},
    //   {type: "radio", grpCd: "C012", targetId: "test2", name: "testType2"},
    //   {type: "select", grpCd: "C014", targetId: "test3"}
    // ], function () {
    //   console.log("renderCode finish")
    //
    //   $("#test3").val("C0141011");
    //   // grid init
    //
    //   // event binding
    //   $("input[name=testType2]").on("change", function () {
    //   });
    // });
  });

  function fn_home() {
    $.esutils.href("/main");
  }
</script>
<div class="headerWrap">
    <div id="Mheader">
        <div class="header_area">
            <h1 class="top_logo security">
                <a href="javascript:window.location.replace(`\${global.contextPath}/main`);"><span style="font-size:11pt;">입주사</span><span style="font-size:11pt;">통합보안시스템</span></a>
            </h1>
            <!-- topMenu start-->
            <ul class="nav" id="headerMenu"></ul>
            <!-- topMenu end -->
            <div class="top_mu01">
                <p style="color:#FFFFFF">[${menuId}] <%= login.get("DEPT_NM")%>[<%= login.get("EMP_ID") %>] <%= login.get("EMP_NM") %> <%=login.get("JW_NM") %>님</p>
                <ul>
                    <li><a href="javascript:fn_home();" class="BasicOver">home</a></li>
                    <!--
                    <li><a href="#" class="BasicOver"><img src="/esecurity/assets/common/images/common/top_mu03_off.gif" alt="사이트맵" /></a></li>
                    -->
                    <li><a href="javascript:fn_IoUserModify();" class="BasicOver">개인정보수정</a></li>

                    <li><a href="javascript:fn_logOut();" class="BasicOver">로그아웃</a></li>
                </ul>
            </div>
        </div>
    </div>
    <%--<select id="test1"></select>
    <span id="test2"></span>
    <select id="test3"></select>--%>
</div>