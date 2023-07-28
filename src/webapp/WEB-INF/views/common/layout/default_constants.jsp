<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map login = (Map)request.getSession().getAttribute("Login");
%>
<script>
  /**
   * global variables (js)
   */
  var global = {
    contextPath: "<%=request.getContextPath()%>",
    empId: "<%= login.get("EMP_ID") %>",
    empNm: "<%= login.get("EMP_NM") %>",
    deptId: "<%= login.get("DEPT_ID") %>",
    deptNm: "<%= login.get("DEPT_NM") %>",
    jwNm: "<%= login.get("JW_NM") %>",
    jwId: "<%= login.get("JW_ID") %>",
    jcCd: "<%= login.get("JC_CD") %>",
    compId: "<%= login.get("COMP_ID") %>",
    compNm: "<%= login.get("COMP_NM") %>",
    divCd: "<%= login.get("DIV_CD") %>",
    acIp: "<%= request.getRemoteAddr() %>",
    telNo1: "<%= login.get("TEL_NO1") %>",
    telNo2: "<%= login.get("TEL_NO2") %>",
    compKnd: "<%= login.get("COMP_KND") %>",
    compAreaKnd: "<%= login.get("COMP_AREA_KND") %>",
    menuInfo: {},
  }
</script>
