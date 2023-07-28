<%@ page contentType="text/html;charset=UTF-8" import="org.apache.commons.lang.*"%>
<%
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); 
response.setHeader("cache-control","no-cache"); //HTTP 1.1
%>
<html>
<head>
<link rel="stylesheet" href="/eSecurity/common/eaglet/report/lib/style.css">
<script type="text/javascript">

function isInstalledActiveX () {	
	var isInstall = false;
	try {
		var obj = new ActiveXObject("EAGLETREPORT.EagletReportCtrl.1");
		
		if(obj) isInstall = true;
		else isInstall = false;
	} catch(e) {
		isInstall = false;		
	}
	if(isInstall) alert("ActiveX Control 설치");
	else alert("ActiveX Control 미설치");	
}
</script>
<style type="text/css">
.inputcolor {border: 1px solid #B6B6B6;
			   font-family:돋움;
			   color:#FFFFFF;
			   font-size:8pt;
			   background:#000000;
			   padding-top:2px;
			   height:20px;
			   }
</style>
</head>
<BODY text=black vLink=purple aLink=red link=blue bgColor=#999999 leftMargin=0 topMargin=0 marginheight="0" marginwidth="0" onload='MakeReport()'>
<FORM name='frm'>
    <object id="RPT" classid="clsid:BEBCBA61-7DC9-4E7C-B601-D5831808952E"  codebase="/eSecurity/common/eaglet/cabs/EagletReport.cab#version=1,6,3,7" width="0" height="0">
    </object>
   <TABLE cellSpacing=0 cellPadding=0 width="100%" background=/eSecurity/common/eaglet/report/images/top_bg.jpg border=0>  
    <TBODY>
      <TR>
        <TD height=46>
        <TABLE cellSpacing=0 cellPadding=0 border=0>
            <TBODY>
              <TR>
              	<TD width="10"></TD>
                <TD><DIV id=FirstButton><IMG height=33 src="/eSecurity/common/eaglet/report/images/first_off.gif"  border=0></DIV></TD>
                <TD width="10"></TD>
                <TD><DIV id=BackButton><IMG height=33 src="/eSecurity/common/eaglet/report/images/pre_off.gif"  border=0></DIV></TD>
                <TD width="10"></TD>
                <TD><DIV id=NextButton><IMG height=33 src="/eSecurity/common/eaglet/report/images/next_off.gif"  border=0></DIV></TD>
                <TD width="10"></TD>
				<TD><DIV id=LastButton><IMG height=33 src="/eSecurity/common/eaglet/report/images/last_off.gif"  border=0></DIV></TD>
				<TD width="10"></TD>
				<TD><IMG height=33 src="/eSecurity/common/eaglet/report/images/line.gif" width=5 border=0></TD>
                <TD><IMG height=33 src="/eSecurity/common/eaglet/report/images/move_page.gif"  border=0>&nbsp;</TD>
				<TD><DIV style="MARGIN-TOP: 3px; MARGIN-BOTTOM: 0px"><INPUT class=inputcolor onkeydown=MovePage() size=4 name=current_page></DIV></TD>
				<TD><DIV style="MARGIN-TOP: 3px; MARGIN-BOTTOM: 0px"><FONT color="#FFFFFF">&nbsp;/&nbsp;</FONT></DIV></TD>
				<TD><FONT color="#FFFFFF"><DIV id=TotalPageDiv style="MARGIN-TOP: 3px; MARGIN-BOTTOM: 0px"></DIV></FONT></TD>
				<TD><DIV style="MARGIN-TOP: 3px; MARGIN-BOTTOM: 0px"><FONT color=#c6c7ff>&nbsp; Page</FONT></DIV></TD>
				<TD width="10"></TD>
				<TD><IMG height=33 src="/eSecurity/common/eaglet/report/images/line.gif" width=5 border=0></TD>
				<TD width="10"></TD>				
				<TD><A href="javascript:PrintReport()"><IMG height=33 src="/eSecurity/common/eaglet/report/images/print.gif" border=0></A></TD>
				<TD width="10"></TD>
				<TD>
					<INPUT ID=all TYPE='radio' name='print_opt' value=A CHECKED /> <FONT COLOR="WHITE"><label for=all>All,</label> </FONT>
					<INPUT ID=now TYPE='radio' name='print_opt' value=N /> <FONT COLOR="WHITE"><label for=now>Now,</label> </FONT>
					<INPUT ID=range TYPE='radio' name='print_opt' value=R /> 
					<FONT COLOR="WHITE"><label for=range>Range</label> : </FONT> <INPUT class=inputcolor size=4 name=print_from> <FONT COLOR="WHITE">~</FONT> <INPUT class=inputcolor size=4 name=print_to>
				</TD>
				<TD width="10"></TD>
				<TD><IMG height=33 src="/eSecurity/common/eaglet/report/images/line.gif" width=5 border=0></TD>
				<TD width="10"></TD>
				<TD><a href="javascript:SaveExcel()"><IMG src="/eSecurity/common/eaglet/report/images/excel.jpg"  border=0></a></TD>
				<TD width="10"></TD>
				<TD><a href="javascript:SaveFile()"><IMG src="/eSecurity/common/eaglet/report/images/save_file.jpg"  border=0></a></TD>
				<TD width="7"></TD>
				<TD><IMG height=33 src="/eSecurity/common/eaglet/report/images/line.gif" width=5 border=0></TD>
				<TD width="10"></TD>
				<TD><input type=text class=textblank name=immmmm></TD>
			</TR>
			</TBODY>
		</TABLE>
		</TD>
        <TD align=right><A href="javascript:top.close();"><IMG height=33 src="/eSecurity/common/eaglet/report/images/close.gif"  border=0></A></TD>
      </TR>
    </TBODY>
  </TABLE>
</FORM>

<script language="JavaScript">
<!--
// 인쇄를 위해 서버에 리포트 데이타 생성 요청
function MakeReport()
{	
	//isInstalledActiveX();
	var frm = document.frm;	
	try {		
		if(frm.RPT != null) {
		  // alert( " target = " +parent.szTargetReport);		 
            if (frm.RPT.MakeReport(parent.szTargetReport))
			{            	
				TotalPageDiv.innerText = frm.RPT.GetTotalPage();
				MoveButtonChk();
				
				frm.print_from.value = frm.RPT.GetTotalPage() > 0 ? 1 : 0;
				frm.print_to.value = frm.RPT.GetTotalPage();
			}
			else
			{				
				// 에러 처리
				alert("MakeReport else "+frm.RPT.GetErrorNo());
				alert("MakeReport else "+frm.RPT.GetErrorMessage());
				alert("MakeReport else "+frm.RPT.GetErrorLocalizedMessage());				
			}
		}
	} catch (e) 
	{	
		alert("MakeReport"+e.number +","+ e.description);		
	}
}

function SaveExcel()
{
	var frm = document.frm;
	try {
		if(frm.RPT != null) { 
            if (!frm.RPT.SaveExcel())
			{
				// 에러 처리
				//alert(frm.RPT.GetErrorNo());
				//alert(frm.RPT.GetErrorMessage());
				//alert(frm.RPT.GetErrorLocalizedMessage());				
			}
		}
	} catch (e) 
	{
	alert("SaveExcel"+e.number +","+ e.description);
	}
}

function SaveFile()
{
	var frm = document.frm;
	try {
		if(frm.RPT != null) { 
            if (!frm.RPT.SaveFile())
			{
				// 에러 처리
				//alert(frm.RPT.GetErrorNo());
				//alert(frm.RPT.GetErrorMessage());
				//alert(frm.RPT.GetErrorLocalizedMessage());				
			}
		}
	} catch (e) 
	{
	//alert(e.number +","+ e.description);
	}
}

// 처음 페이지로 이동
function FirstClick() 
{
	var frm = document.frm;
	try {
		if( frm.RPT != null) { 
			frm.RPT.MoveFirstPage();
			MoveButtonChk();
		} 
	} catch (e) 
	{
	alert("FirstClick"+e.number +","+ e.description);
	}
}

// 이전 페이지로 이동
function PrevClick() 
{
	var frm = document.frm;
	try {
		if( frm.RPT != null) { 
			frm.RPT.MovePrevPage();
			MoveButtonChk();
		} 
	} catch (e) 
	{
	//alert(e.number +","+ e.description);
	}
}

// 다음 페이지로 이동
function NextClick() 
{
	var frm = document.frm;
	try {
		if( frm.RPT != null) { 
			frm.RPT.MoveNextPage();
			MoveButtonChk();
		} 
	} catch (e) 
	{
	//alert(e.number +","+ e.description);
	}
}

// 마지막 페이지로 이동
function LastClick() 
{
	var frm = document.frm;
	try {
		if( frm.RPT != null) { 
			frm.RPT.MoveLastPage();
			MoveButtonChk();
		} 
	} catch (e) 
	{
	//alert(e.number +","+ e.description);
	}
}

// 입력한 페이지로 이동
function MovePage() 
{
	var frm = document.frm;
	if (event.keyCode!=13)
		return false;
	
	try {
		var nPage = frm.current_page.value;
		if( frm.RPT != null && !isNaN(nPage)) { 
			frm.current_page.value = frm.RPT.MovePage(nPage);
			MoveButtonChk();
		} 
		else
		{
			frm.current_page.value = frm.RPT.GetNowPage();
		}
	} catch (e) 
	{
		alert(e.number +","+ e.description);
	}
}

// 리포트 출력
function PrintReport() 
{
	var frm = document.frm;
	try {
		if( frm.RPT != null) {
			if (frm.RPT.GetTotalPage() <= 0)
				return;

			if (frm.print_opt[0].checked == true)
			{
				frm.RPT.PrintReport();
			}
			else if (frm.print_opt[1].checked == true)
			{
				frm.RPT.PrintNowPage();
			}
			else if (frm.print_opt[2].checked == true)
			{
				var nFrom = frm.print_from.value;
				var nTo = frm.print_to.value;

				if(isNaN(nTo) || isNaN(nFrom)) 
				{ 
					frm.print_from.value = frm.RPT.GetTotalPage() > 0 ? 1 : 0;
					frm.print_to.value = frm.RPT.GetTotalPage();
					return;
				}
				frm.RPT.PrintRange(	nFrom, nTo );
			}
		} 
	} catch (e) 
	{
	//alert(e.number +","+ e.description);
	}
}

// 페이지 이동 동작 후 화면 상태 표시
function MoveButtonChk() 
{
	var frm = document.frm;
	try {
		if( frm.RPT != null) { 
			var NowPage = frm.RPT.GetNowPage();
			var TotalPage = frm.RPT.GetTotalPage();
			frm.current_page.value = NowPage;

			if (NowPage <= 1) {
				FirstButton.innerHTML = '<IMG height=33 src="/eSecurity/common/eaglet/report/images/first_off.gif"   border=0>';
				BackButton.innerHTML = '<IMG height=33 src="/eSecurity/common/eaglet/report/images/pre_off.gif"  border=0>';
			} else {
				FirstButton.innerHTML = '<a href="javascript:FirstClick()"><IMG height=33 src="/eSecurity/common/eaglet/report/images/first_on.gif" border=0></a>';
				BackButton.innerHTML = '<a href="javascript:PrevClick()"><IMG height=33 src="/eSecurity/common/eaglet/report/images/pre_on.gif" border=0></a>';
			}

			if (NowPage >= TotalPage) {
				NextButton.innerHTML = '<IMG height=33 src="/eSecurity/common/eaglet/report/images/next_off.gif" border=0>';
				LastButton.innerHTML = '<IMG height=33 src="/eSecurity/common/eaglet/report/images/last_off.gif" border=0>';
			} else {
				NextButton.innerHTML = '<a href="javascript:NextClick()"><IMG height=33 src="/eSecurity/common/eaglet/report/images/next_on.gif" border=0></a>';
				LastButton.innerHTML = '<a href="javascript:LastClick()"><IMG height=33 src="/eSecurity/common/eaglet/report/images/last_on.gif" border=0></a>';
			}

		} 
	} catch (e) 
	{
	//alert(e.number +","+ e.description);
	}
}
//-->
</script> 
</body>
</html>