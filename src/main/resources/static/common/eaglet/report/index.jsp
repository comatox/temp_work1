<%@ page contentType="text/html;charset=UTF-8"%>
<%

	String szTargetReport = request.getParameter("targetreport");
	//System.out.println(szTargetReport);

	if (szTargetReport != null)
	{
		String szHostUrl = request.getRequestURL().toString();
		int nFind = szHostUrl.indexOf(request.getRequestURI());
		szHostUrl = szHostUrl.substring(0, nFind);
		
		szTargetReport = szHostUrl + szTargetReport;
		
	}
	else
	{
		szTargetReport = "/eSecurity/common/eaglet/report/preview.jsp";
		out.print("출력할 문서가 존재하지 않습니다.");
	}
%>
<html>
<head>
<title>리포트 출력</title>
<script type="text/javascript">
<!--
    var szTargetReport = "<%=szTargetReport%>";
//-->
</script>
</head>
<frameset rows="50, 1*" border="0">
    <frame name="ReportControlFrame" scrolling="no" marginwidth="0" marginheight="0" src="/eSecurity/common/eaglet/report/control.jsp" noresize>
    <frame name="ReportViewFrame" scrolling="auto" marginwidth="0" marginheight="0" src="/eSecurity/common/eaglet/report/preview.jsp">
    <noframes>
    <body bgcolor="#FFFFFF" text="#000000" link="#0000FF" vlink="#800080" alink="#FF0000">
    <p>이 페이지를 보려면, 프레임을 볼 수 있는 브라우저가 필요합니다.</p>
    </body>
    </noframes>
</frameset>
</html>
