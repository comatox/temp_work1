<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="esecurity.framework.report.*"%>

<%
	String TargetRepoetName = request.getParameter("targetreportname");
	if (TargetRepoetName == null)
	{
		TargetRepoetName = "/eSecurity/common/report/blank.htm";
		out.print("출력할 문서가 존재하지 않습니다.");
	}
	//TargetRepoetName = TargetRepoetName.replaceAll("@","&");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<title>리포트 출력</title>
</head>

<frameset rows="50, 1*, 0" border="0">
    <frame name="ReportControlFrame" scrolling="no" marginwidth="0" marginheight="0" src="/eSecurity/common/report/Report_iframe_Control.jsp?targetreportname=<%=TargetRepoetName%>" noresize>
    <frame name="ReportViewFrame" scrolling="auto" marginwidth="0" marginheight="0" src="/eSecurity/common/report/blank.htm">
    <frame name="PrintFrame" scrolling="no" marginwidth="0" marginheight="0" src="/eSecurity/common/report/print.htm">
    <noframes>
    <body bgcolor="#FFFFFF" text="#000000" link="#0000FF" vlink="#800080" alink="#FF0000">
    <p>이 페이지를 보려면, 프레임을 볼 수 있는 브라우저가 필요합니다.</p>
    </body>
    </noframes>
</frameset>
</html>
