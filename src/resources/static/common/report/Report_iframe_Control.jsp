<%@ page contentType="text/html;charset=UTF-8"%>
<%
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); 
response.setHeader("cache-control","no-cache"); //HTTP 1.1
%>

<%
    //리포트파일명 받기.콘트롤페이지를 모두 출력 후 리포트 파일을 출력한다.
    String TargetReportName = request.getParameter("targetreportname");
    if (TargetReportName == null)
    {
        TargetReportName = "/eSecurity/common/report/blank.htm";
        out.print("출력할 문서가 존재하지 않습니다.");
    }
    //TargetRepoetName = Fn.rplc(TargetRepoetName,"@","&");
%>
<html>
<head>
<script type="text/javascript">

function ActivateActiveX(ParentID, ObjectTag, ParamTag) 
{
    var ObjectElement = document.createElement(ObjectTag);

    if (ParamTag != null) 
    {
        for (var i = 0; i<ParamTag.length; i++) 
        {
            var ParamElement = document.createElement(ParamTag[i]);
            ObjectElement.appendChild(ParamElement);
        }
    }
    
    ParentID.appendChild(ObjectElement);
}

function MakeFlashString(source, id, width, height, wmode, align, param)
{   
    return "<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/pios/cabs/flash/swflash.cab#version=6,0,0,0\" width="+width+" height="+height+" id="+id+" align="+align+"><param name=wmode value="+wmode+" /><param name=movie value="+source+" /><param name=quality value=high />"+param+"<embed src="+source+" quality=high wmode="+wmode+" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?p1_prod_version=shockwaveflash\" width="+width+" height="+height+"></embed></object>";
}

// innerHTML Type
function SetInnerHTML(target, code)
{ 
    target.innerHTML = code; 
}

// Direct Write Type
function DocumentWrite(src)
{
    document.write(src);
}
</script>
<!-- 
<link rel="stylesheet" href="../../../pios/lib/style.css">
<script language="javascript1.1" src="../../../pios/lib/Form_Check.js"></script> -->
<style type="text/css">
.input              { background:#F2F2FA; border: 1px solid #FFFFFF; font-family:돋움; font-size:9pt;height: 21px;padding-top:4px;}
.break {word-break:break-all;}
</style>
</head>

<body bgcolor="#999999" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<!-- 변경된것(시작) -->
<div id="EmbedFactoryPos">
    <script LANGUAGE="Javascript">
    <!--    var axObject = '<object id="factory" viewastext style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="/eSecurity/common/report/cabs/ScriptX.cab#Version=6,1,431,8"></object>';  -->
    var axObject = '<object id="factory" viewastext style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="/eSecurity/common/report/cabs/smsx.cab#Version=7,2,0,36"></object>';    
    ActivateActiveX(EmbedFactoryPos, axObject); 
    </script>
</div>
<!-- 변경된것(끝) -->
<form name="frm" method="post">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" background="/eSecurity/common/report/images/top_bg.jpg">
    <tr>
      <td height="44">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td>
              <p id="BackButton"><img src="/eSecurity/common/report/images/back_off.gif" width="102" height="33" border="0"></a></p>
            </td>
            <td>
              <p id="NextButton"><img src="/eSecurity/common/report/images/next_off.gif" width="120" height="33" border="0"></a></p>
            </td>
            <td width="60" align="center">
              <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td><p id="NowPage"></p></td><td>/</td><td><p id="TotalPageDiv"></p></td>
                </tr>
              </table>
            </td>
            <td>
              <p><img src="/eSecurity/common/report/images/divide.gif" width="9" height="33" border="0"></p>
            </td>
            <td>
              <p><img src="/eSecurity/common/report/images/move_page.gif" width="115" height="33" border="0"></p>
            </td>
            <td>
              <p style="margin-top:3; margin-bottom:0;"><input type="text" name="current_page" onkeyup="Move_Page()" size=4 class=input>Page</p>
            </td>
            <td>
              <p><a href="javascript:RangePrint()"><img src="/eSecurity/common/report/images/print.gif" width="71" height="33" border="0"></a></p>
            </td>
            <td>
              <p><img src="/eSecurity/common/report/images/divide.gif" width="9" height="33" border="0"></p>
            </td>
            <!-- <td><input type=text class=textblank name=immmmm></td> -->
          </tr>
        </table>
      </td>
      <td align="right">
        <p><A HREF="javascript:top.close();" ><img src="/eSecurity/common/report/images/close.gif" width="96" height="33" border="0"></A></p>
      </td>
    </tr>
  </table>
</form>
<p>&nbsp;</p>
<script language="JavaScript">
<!--
var TotalPage=0;
var frm = document.frm;
var a_Page = new Array();
var nowIndex=0;
var margine=0;
var topmargine = 0;
var orientation="";

//페이지를 구분하여 출력하기 위한 CSS임
var PageBreak="<p class=break>";

function PrevClick() {
    if (nowIndex > 0)
    {
        nowIndex--;
    }
    for (i=0;i<a_Page.length;i++)
    {
        if (i == nowIndex)
        {
            eval("top.ReportViewFrame."+a_Page[i]).style.display = "inline";
        }
        else {
            eval("top.ReportViewFrame."+a_Page[i]).style.display = "none";
        }
    }
    NowPage.innerText = (nowIndex+1);
    MoveButtonChk();
}
function NextClick() {
    if (nowIndex < TotalPage-1)
    {
        nowIndex++;
    }
    for (i=0;i<a_Page.length;i++)
    {
        if (i == nowIndex)
        {
            eval("top.ReportViewFrame."+a_Page[i]).style.display = "inline";
        }
        else {
            eval("top.ReportViewFrame."+a_Page[i]).style.display = "none";
        }
    }
    NowPage.innerText = (nowIndex+1);
    MoveButtonChk();
}
// 특정Page로 이동
function Move_Page(){
    if (event.keyCode!=13)
        return false;
    var frm = self.document.frm;
    if(eval(frm.current_page.value) > TotalPage){
        frm.current_page.value = TotalPage;
    }
    if(eval(frm.current_page.value) < 1){
        frm.current_page.value = 1;
    }
    nowIndex = eval(frm.current_page.value)-1;

    eval("top.ReportViewFrame."+a_Page[eval(NowPage.innerText)-1]).style.display = "none";
    eval("top.ReportViewFrame."+a_Page[nowIndex]).style.display = "inline";
    NowPage.innerText = (nowIndex+1);
    MoveButtonChk();
}

function ReceiveTotalPage(totalpage,mg,ot,topmg) {
    TotalPage = totalpage;
    margine = mg;

    if(topmg != undefined){
        topmargine = topmg;
    }
    orientation = ot;

    if (TotalPage > 0)
    {
        for (i=1;i<TotalPage+1;i++)
        {
            a_Page[a_Page.length] = "Page_"+i;
            NowPage.innerText = (nowIndex+1);
        }
    }
    TotalPageDiv.innerText = TotalPage;
    MoveButtonChk();
}

function PrintAllPage() {
    PrintSetting();

    var PrintContent="";
    
    for (i=1;i<=TotalPage;i++)
    {
        PrintContent += eval("top.ReportViewFrame.Page_"+i).innerHTML;
        if (i != TotalPage)
        {
            PrintContent += PageBreak;
        }
    }
    top.PrintFrame.printpage.innerHTML = PrintContent;
    factory.printing.Print(true, top.PrintFrame)
}
function PrintSetting() {
  factory.printing.header = "";
  factory.printing.footer = "";
  if (margine == 0)
  {
      margine = 19
  }
  if(topmargine == 0){
      topmargine = 20;
  }
  if(topmargine == -1){
      topmargine = 0;
  }
  factory.printing.leftMargin = margine;
  factory.printing.rightMargin = 0;
  factory.printing.topMargin = topmargine;
  factory.printing.bottomMargin = 5;

  if (orientation == "SERO")
  {
      factory.printing.portrait = true;
  }
  else if (orientation == "GARO")
  {
      factory.printing.portrait = false;
  }
  // 아래부분은 이전 버전의 호환성을 위해
  else if (orientation == "PORTRAIT")
  {
      factory.printing.portrait = true;
  }
  else {
      factory.printing.portrait = false;
  }
}

//인쇄버튼을 클릭 후 처리 - 020709 이인배
function RangePrint() {
	var browse = "IE";
	var agent = navigator.userAgent.toLowerCase();
	if(agent.indexOf("chrome") != -1){browse="chrome";}
    if(browse=="IE"){
        PrintAllPage();
    }else{
    	var initBody = document.body.innerHTML;
        window.onbeforeprint = function(){
            document.body.innerHTML = eval("top.ReportViewFrame.Page_1").innerHTML;
        }
        window.onafterprint = function(){
            document.body.innerHTML = initBody;
        }
        window.print();
    }
}

//이동페이지 아이콘 체인지 - 020709 이인배
function MoveButtonChk() {
    if (eval(NowPage.innerHTML) <= 1)
    {
        BackButton.innerHTML = '<img src="/eSecurity/common/report/images/back_off.gif" width="102" height="33" border="0">';
    }
    else{
        BackButton.innerHTML = '<a href="javascript:PrevClick()"><img src="/eSecurity/common/report/images/back.gif" width="102" height="33" border="0">';
    }
//  alert(eval(NowPage.innerHTML)+"z"+TotalPage);
    if (eval(NowPage.innerHTML) >= TotalPage)
    {
        NextButton.innerHTML = '<img src="/eSecurity/common/report/images/next_off.gif" border="0">';
    }
    else{
        NextButton.innerHTML = '<a href="javascript:NextClick()"><img src="/eSecurity/common/report/images/next.gif" border="0">';
    }
}
//TargetReportName 의 리포트 파일을 하단 프레임에 출력한다.
//이유 - 콘트롤 페이지가 로드되기전 리포트파일이 먼저 나오면 콘트롤페이지로 Total 페이지 값을 넘겨 줄 수 없기 때문에
window.open("<%=TargetReportName%>","ReportViewFrame");
//-->
</script> 
</body>

</html>