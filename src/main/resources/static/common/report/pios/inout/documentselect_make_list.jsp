<%@page import="java.sql.SQLException"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" session="false"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.File"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page import="esecurity.framework.report.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>


<%
request.setCharacterEncoding("UTF-8");
//out.flush();
Map login = new HashMap();
if(request.getSession().getAttribute("Login") != null) {
	login = (Map)request.getSession().getAttribute("Login");
} 
String emp_id = login != null ? login.get("EMP_ID").toString() : "";
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); 
response.setHeader("cache-control","no-cache"); //HTTP 1.1
%>
<div id="PROGRESSLAYER" style="width:195px; height:95px; position:absolute; left:1px; top:1px; z-index:2;display :none;">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="195" height="95" background="/eSecurity/common/report/images/progress/progressbg_report.gif">
  <tr>
    <td height="14">
      <p><img src="/eSecurity/common/report/images/progress/spacer.gif" width="1" height="1" border="0"></p>
    </td>
  </tr>
  <tr>
    <td >
      <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center">
            <p><span style="font-size:9pt;"><font face="굴림" color="#666666">처리중입니다<br>잠시 
            기다려주십시요<br>완료되면 화면 상단의 다음페이지 오른쪽에 페이지번호가 나옵니다.</font></span></p>
          </td>
        </tr>
        <tr>
          <td align="center">
            <p><img src="/eSecurity/common/report/images/progress/progressbar.gif" width="68" height="8" border="0"></p>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>

<SCRIPT LANGUAGE="JavaScript">
<!--
 function HideProgressLayer(){
    PROGRESSLAYER.style.display = "none";
}
function ShowProgressLayer(x,y){
    if(x==null){
        x=550 ;
        y=200 ;
    }
    PROGRESSLAYER.style.display = "inline";
    PROGRESSLAYER.style.posLeft  = x ;
    PROGRESSLAYER.style.posTop = y  + document.body.scrollTop ;
} 
//ShowProgressLayer();
//-->
</SCRIPT>



<%	
//System.out.println("documentselect_make_list.jsp 1");
	String	WriteDate		= "";
	int		WriteSeq		= 0;
	int		CompanyKnd		= 0;
	int		CompanyAreaKnd	= 0;
	int		CompanyNo		= 0;
	int		ArticleKndNo			= 0;
	String		ArticleSelectKnd	= "";
	int		PrintKnd	= 0;
	String		docId	= "";
	
	Context ctx = null;     //Interface
	DataSource ds = null;   //Interface
	Connection conn = null;  //Interface

	//System.out.println("QueryString : "+request.getQueryString());
	String query = StringUtils.defaultIfEmpty(request.getParameter("query"),"");
	String[] parmeter = query.split("@");
    System.out.println(parmeter);
	//System.out.println(parmeter.length);
	WriteDate		= StringUtils.defaultIfEmpty(parmeter[0],"");	
	WriteSeq		= Integer.parseInt(StringUtils.defaultIfEmpty(parmeter[1],"0"));
	
	ArticleKndNo	 = Integer.parseInt(StringUtils.defaultIfEmpty(parmeter[2],"0"));
	ArticleSelectKnd = StringUtils.defaultIfEmpty(parmeter[3],"");
	if(parmeter.length > 4) {
		docId = StringUtils.defaultIfEmpty(parmeter[4],"");
	} else {
		docId = "";
	}
	//System.out.println("parmeter[0] : "+parmeter[0]+" :"+parmeter[1]+" : "+parmeter[2]+ " : " +parmeter[3]);
	
	
	// File Setting( ***** 화일이름을 반드시 설정할 것) *************************/
	
	
	String maskname = "";	
	String savePath = request.getRealPath("/eSecurity/temp/");
	maskname = "documentselect_template_" + emp_id + ".htm";
	File dataFile = new File(savePath +"\\" +maskname);
	String filename = maskname;
	PrintWriter outputStream = new PrintWriter(new FileOutputStream(dataFile));
	DeleteFileAndDirUtil.deleteFiles( request.getRealPath("/eSecurity/temp") , System.currentTimeMillis()-(3600000*24));//3600초(1시간)*24 하루동안 쌓인 임시파일은 삭제한다.
	//System.out.print(savePath +"\\" +maskname);
	ReportGen reportGen = new ReportGen();	

	if(ArticleKndNo == 1 && (ArticleSelectKnd.equals("1000000001") || ArticleSelectKnd.equals("1000000002") || ArticleSelectKnd.equals("1000000003") || ArticleSelectKnd.equals("1000000182"))){
	//물품이고 원자재 , 부자재, SparePart
	
   		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template1.htm"));
	}
	else if(ArticleKndNo ==  1 && ArticleSelectKnd.equals("1000000004")){
	//물품이고 자산인경우
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template2.htm"));
	}
	else if(ArticleKndNo ==  1 && ArticleSelectKnd.equals("1000000142")){
	//물품이고 완제품인경우
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template6.htm"));
	}	
	else if(ArticleKndNo ==  1 && ArticleSelectKnd.equals("1000000156")){
	//물품이고 부외자산인경우
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template7.htm"));
	}	
	
	else if(ArticleKndNo ==  1 && !ArticleSelectKnd.equals("1000000013")){ // && ArticleSelectKnd.equals("1000000013") 
	    //물품 - 기타(반입)
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template3.htm"));
	}
	else if(ArticleKndNo ==  1 && ArticleSelectKnd.equals("1000000013")){ // && ArticleSelectKnd.equals("1000000013") 
	    //물품 - 기타(반출)
	    reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template3out.htm"));
    }
	else if(ArticleKndNo ==  2 && ArticleSelectKnd.equals("1000000010") && WriteDate.replace("-","").compareTo("20090706") > 0 ){
	//노트북
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template4note.htm"));
	}
	else if(ArticleKndNo ==  2 && ArticleSelectKnd.equals("1000000056")){
	//전산저장장치
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template4etc.htm"));
	}
	else if(ArticleKndNo ==  2){
	//전산저장장치
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template4.htm"));
	}
	else if(ArticleKndNo ==  3){
	//문서
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template5.htm"));
	}
	else if(ArticleKndNo ==  4){
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template1.htm"));
	}
	else if(ArticleKndNo ==  5){
		reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/pios/inout/documentselect_template3.htm"));
	}
//		(수정할곳 -- 끝)

	//System.out.println("documentselect_make_list.jsp 2");

	//System.out.println("ArticleKndNo : "+ArticleKndNo);
	//System.out.println("ArticleSelectKnd : "+ArticleSelectKnd);	
	//System.out.println("ssss : "+reportGen.getPathandfilename());
	
	if (reportGen.SeperateTemplateDoc())
	{	
		//헤더,디테일 등 각 리포트 부분을 분리한다.
		String ReportHeader="";
		String PageHeader="";
		String Detail="";
		String PageFooter="";
		String ReportFooter="";
		
		String LeftMargine="";
		String Orientation="";
		String TableHearder1="";
		String TableDetail1="";
		int    TableHeaderHeightSize1=0;
		int    TableDetailHeight1=0;
		String TableFooter1="";

		String TableHearder2="";
		String TableDetail2="";
		int    TableHeaderHeightSize2=0;
		int    TableDetailHeight2=0;
		String TableFooter2="";

		String TableHearder3="";
		String TableDetail3="";
		int    TableHeaderHeightSize3=0;
		int    TableDetailHeight3=0;
		String TableFooter3="";

		String TableHearder4="";
		String TableDetail4="";
		int    TableHeaderHeightSize4=0;
		int    TableDetailHeight4=0;
		String TableFooter4="";

		String TableHearder5="";
		String TableDetail5="";
		int    TableHeaderHeightSize5=0;
		int    TableDetailHeight5=0;
		String TableFooter5="";

		String TableHearder6="";
		String TableDetail6="";
		int    TableHeaderHeightSize6=0;
		int    TableDetailHeight6=0;
		String TableFooter6="";

		String TableHearder7="";
		String TableDetail7="";
		int    TableHeaderHeightSize7=0;
		int    TableDetailHeight7=0;
		String TableFooter7="";

		String TableHearder8="";
		String TableDetail8="";
		int    TableHeaderHeightSize8=0;
		int    TableDetailHeight8=0;
		String TableFooter8="";

		String TableHearder9="";
		String TableDetail9="";
		int    TableHeaderHeightSize9=0;
		int    TableDetailHeight9=0;
		String TableFooter9="";

		String TableHeader10="";
		String TableDetail10="";
		int    TableHeaderHeightSize10=0;
		int	   TableDetailHeight10=0;
		String TableFooter10="";

		int HeaderSize=0;
		int DetailSize=0;
//		수정할것(Portrait = 960, landscape = 650)
		int TotalSize=960;
//		int TotalSize=650;
		int marginsize=0;
		
		ReportHeader = reportGen.getReportheader();
		Orientation = reportGen.getOrientation();
		LeftMargine = reportGen.getLeftmargine();
		PageHeader = reportGen.getPageheader();

		Detail = reportGen.getDetail();
		PageFooter = reportGen.getPagefooter();
		ReportFooter = reportGen.getReportfooter();
		HeaderSize = reportGen.getPageheaderheight();
		DetailSize = reportGen.getDetailheight();

		TableHearder1 = reportGen.getTableheader1();
		TableDetail1 = reportGen.getTabledetail1();
		TableDetailHeight1 = reportGen.getTabledetailheight1();
		TableFooter1 = reportGen.getTablefooter1();
		TableHeaderHeightSize1 = reportGen.getTableheadersize1();

		TableHearder2 = reportGen.getTableheader2();
		TableDetail2 = reportGen.getTabledetail2();
		TableDetailHeight2 = reportGen.getTabledetailheight2();
		TableFooter2 = reportGen.getTablefooter2();
		TableHeaderHeightSize2 = reportGen.getTableheadersize2();

		TableHearder3 = reportGen.getTableheader3();
		TableDetail3 = reportGen.getTabledetail3();
		TableDetailHeight3 = reportGen.getTabledetailheight3();
		TableFooter3 = reportGen.getTablefooter3();
		TableHeaderHeightSize3 = reportGen.getTableheadersize3();

		TableHearder4 = reportGen.getTableheader4();
		TableDetail4 = reportGen.getTabledetail4();
		TableDetailHeight4 = reportGen.getTabledetailheight4();
		TableFooter4 = reportGen.getTablefooter4();
		TableHeaderHeightSize4 = reportGen.getTableheadersize4();

		TableHeader10 = reportGen.getTableheader10();
		TableDetail10 = reportGen.getTabledetail10();
		TableFooter10 = reportGen.getTablefooter10();

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
		java.util.Date currentTime_1 = new java.util.Date();
		String dateString = formatter.format(currentTime_1);

		//페이지 단위로 보이기 위한 DIV 구성
		String sPageViewStart="<div id='Page_{{page}}' style='width:100%; position:absolute; left:0px; z-index:1; display:inline;'>";
		String sPageViewEnd="</div><p style='page-break-before: always;'>";
		String sPageViewEnd_Last="</div>";

		//데이타 베이스에서 필요한 데이타를 읽어옴.
		Detail="";
		int Index = 0;
		int i = 0;
		String TableFooter = "";
		String condition = "";
		String Top_HTML = "";
		String D_HTML = "";
		int iErrorCode1 = 0;      
		int iErrorCode2 = 0;      
		int iErrorCode3 = 0;      
		int iErrorCode4 = 0;      
		Vector vlist = null;
		Enumeration enumeration = null;
		Vector vlist2 = null;
		Enumeration enumeration2 = null;

		Top_HTML += "<div align='right'> ";
		Top_HTML += "    <table cellpadding='0' cellspacing='0' width='100%'>";
		Top_HTML += "        <tr height='20'>";
		Top_HTML += "            <td></td>";
		Top_HTML += "        </tr>";
		Top_HTML += "        <tr>";
		Top_HTML += "            <td width='100%' align='right'>";
		//Top_HTML += "                <p><img src='/eSecurity/common/eaglet/report/images/logo.jpg' width='140' height='40' border='0'></p>";
		Top_HTML += "                <p><img src='/eSecurity/common/eaglet/report/images/logo2.jpg' ></p>";
		Top_HTML += "            </td>";
		Top_HTML += "        </tr>";
		Top_HTML += "    </table>";
		Top_HTML += "</div>";
	
		%>
		
		
		<%
		try {
		
		ctx = (Context)  new InitialContext();
		ds = (DataSource) ctx.lookup("jdbc/eSecurityDS");
		conn = ds.getConnection();
		 
		InOutWriteProc inoutwriteproc = new InOutWriteProc();
  
		inoutwriteproc.setConn(conn);
		
		//System.out.println("11111111111111111111111");
		
		if (!inoutwriteproc.inout_Load( WriteDate, WriteSeq, CompanyKnd, CompanyAreaKnd, CompanyNo )){
			iErrorCode1 = -1;
		}

		//System.out.println("2222222222222222222222");
		
		if (!inoutwriteproc.approval_Load( WriteDate, WriteSeq, CompanyKnd, CompanyAreaKnd, CompanyNo, docId )){
			iErrorCode2 = -1;
		}
  
		//System.out.println("3333333333333333333333333");
		
		if (!inoutwriteproc.article_Load( WriteDate, WriteSeq, CompanyKnd, CompanyAreaKnd, CompanyNo , ArticleKndNo, ArticleSelectKnd)){
			iErrorCode3 = -1;
		}
		
		//System.out.println("4444444444444444444444444");
		
		InOutWrite inoutwrite = new InOutWrite();
				
		if(iErrorCode1 == 0){				
			inoutwrite = inoutwriteproc.getInoutwrite();
			
			PageHeader = InoutFn.rplc(PageHeader,"{{COMP_NM}}", InoutFn.csc(inoutwrite.getCompnm()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{COMP_NM_TD}}", InoutFn.csc(inoutwrite.getComp_nm_td()).trim());
			//System.out.println("@@@@@@@@@@ : " + inoutwrite.getCompnm());
			PageHeader = InoutFn.rplc(PageHeader,"{{empname}}", InoutFn.csc(inoutwrite.getEmpname()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{writedate}}", InoutFn.csc(inoutwrite.getWritedate()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{inoutserialno}}", InoutFn.csc(inoutwrite.getInoutserialno()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{articlekndname}}", InoutFn.csc(inoutwrite.getArticlekndname()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{outdate}}", InoutFn.csc(inoutwrite.getOutdate()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{indate}}", InoutFn.csc(inoutwrite.getIndate()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{inoutkndname}}", InoutFn.csc(inoutwrite.getInoutkndname()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{yourcompanyname}}", InoutFn.csc(inoutwrite.getYourcompanyname()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{COMPANYNO_NM}}", InoutFn.csc(inoutwrite.getCompanyNm()).trim());
			if(InoutFn.csc(inoutwrite.getInoutkndname()).trim().equals("반입필요"))
				PageHeader = InoutFn.rplc(PageHeader,"{{returncompanyname}}", InoutFn.csc(inoutwrite.getReturncompanyname()).trim());
			else
				PageHeader = InoutFn.rplc(PageHeader,"{{returncompanyname}}", "");
			PageHeader = InoutFn.rplc(PageHeader,"{{prno}}", InoutFn.csc(inoutwrite.getPrno()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{outreasonname}}", InoutFn.csc(inoutwrite.getOutreasonname()).trim());
			PageHeader = InoutFn.rplc(PageHeader,"{{inoutetc}}", InoutFn.csc(inoutwrite.getInoutetc()).trim());		
			PageHeader = InoutFn.rplc(PageHeader,"{{pfc1}}",Top_HTML);
			if(PrintKnd == 1){
				PageHeader = InoutFn.rplc(PageHeader,"{{title}}","[정문인쇄]");
			}else{
				PageHeader = InoutFn.rplc(PageHeader,"{{title}}","");
			}
		}
		
		//System.out.println("5555555555555555555555555555"); 
		
/*
		if(iErrorCode4 == 0){				
			documentchange = documentchangeproc.getDocumentchange();

			if(documentchange.getChangecnt() > 0 || !documentchange.getApprovaldate().trim().equals("")){
				D_HTML += "<tr>";
				D_HTML += "<td width='100%' align='left' valign='bottom' colspan='3'>";
				D_HTML += "<table border='1' cellspacing='0' cellpadding='1' width='650' style='boder-style:solid'  align='center' style='border-collapse: collapse; border: thin solid #000000'>";
				D_HTML += "<tr height='20'>";
				D_HTML += "<td colspan='3' align='center' valign='middle'  width='50%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>반입일자변경</strong></p></td>";
				D_HTML += "<td colspan='3' align='center' valign='middle'  width='50%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>반입불요전환변경</strong></p></td>";
				D_HTML += "</tr>";
				D_HTML += "<tr height='20'>";
				D_HTML += "<td align='center' valign='middle'  width='15%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>최초반입일자</strong></p></td>";
				D_HTML += "<td align='center' valign='middle'  width='15%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>변경반입일자</strong></p></td>";
				D_HTML += "<td align='center' valign='middle'  width='15%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>변경신청수</strong></p></td>";
				D_HTML += "<td align='center' valign='middle'  width='15%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>승인일자</strong></p></td>";
				D_HTML += "<td align='center' valign='middle'  width='15%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>승인자</strong></p></td>";
				D_HTML += "<td align='center' valign='middle'  width='15%' style='font-size:12px; background-color:rgb(192,223,255); border-top-width:1px; border-color:black; border-style:solid;'><p style='font-size:12px'><strong>사내번호</strong></p></td>";
				D_HTML += "</tr>";
				D_HTML += "<tr height='20'>";
				D_HTML += "<td align='center' valign='middle'  style='font-size:12px; border-top-width:0px; border-color:black; border-style:solid;'><p style='font-size:12px'>"+documentchange.getBeforeindate()+"</p></td>";
				D_HTML += "<td align='center' valign='middle'  style='font-size:12px; border-top-width:0px; border-color:black; border-style:solid;'><p style='font-size:12px'>"+documentchange.getRequestindate()+"</p></td>";
				if(documentchange.getChangecnt()==0){
				D_HTML += "<td align='center' valign='middle'  style='font-size:12px; border-top-width:0px; border-color:black; border-style:solid;'><p style='font-size:12px'></p></td>";
				}else{
				D_HTML += "<td align='center' valign='middle'  style='font-size:12px; border-top-width:0px; border-color:black; border-style:solid;'><p style='font-size:12px'>"+documentchange.getChangecnt()+"</p></td>";
				}
				D_HTML += "<td align='center' valign='middle'  style='font-size:12px; border-top-width:0px; border-color:black; border-style:solid;'><p style='font-size:12px'>"+documentchange.getApprovaldate()+"</p></td>";
				D_HTML += "<td align='center' valign='middle'  style='font-size:12px; border-top-width:0px; border-color:black; border-style:solid;'><p style='font-size:12px'>"+documentchange.getEmpname()+"</p></td>";
				D_HTML += "<td align='center' valign='middle'  style='font-size:12px; border-top-width:0px; border-color:black; border-style:solid;'><p style='font-size:12px'>"+documentchange.getTel()+"</p></td>";
				D_HTML += "</tr>";
				D_HTML += "</table>";
				D_HTML += "</td>";
				D_HTML += "</tr>";
				D_HTML += "<tr>";
				D_HTML += "<td height=3 colspan='3'></td>";
				D_HTML += "</tr>";
			}
			PageHeader = InoutFn.rplc(PageHeader,"{{documentcnange}}", D_HTML);
		}
*/

		//한 장의 사이즈에 맞게 출력을 장단위로 구분함.
		int NowPage=1;
		int TotalPage=1;
		String TempTableKnd="";
		String PageAll="";
		String TableBody=TableHearder1;
		String TableBody2=TableHearder2;
		String TableBody3=TableHearder3;
		String Temp=ReportHeader;

		Approval approval = new Approval();
		//System.out.println("6666666666666666666666666");
		if(iErrorCode2 == 0){	
			vlist = inoutwriteproc.getApproval_vlist();
			//TotalPage= (vlist.size()+14) / 15;
			enumeration = vlist.elements();
			
			//System.out.println();
			//if(vlist != null) {
				while(enumeration.hasMoreElements()){
	                approval = (Approval)enumeration.nextElement();
		
					Detail = TableDetail1;
					//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
					Detail = InoutFn.rplc(Detail,"{{r1c1}}", InoutFn.csc(approval.getApprovalkndname().trim()) ); //결재구분
					Detail = InoutFn.rplc(Detail,"{{r1c2}}", InoutFn.csc(approval.getApprovalcntname().trim()) ); //결재순서
					Detail = InoutFn.rplc(Detail,"{{r1c3}}", InoutFn.csc(approval.getApprovalname().trim()) ); // 결재자
					Detail = InoutFn.rplc(Detail,"{{r1c4}}", InoutFn.csc(approval.getApprovalstatename().trim()) ); //결재상태
					Detail = InoutFn.rplc(Detail,"{{r1c5}}", InoutFn.csc(approval.getApprovaldate().trim()) ); //결재일시
					//Detail = InoutFn.rplc(Detail,"{{r1c6}}", InoutFn.csc(approval.getCanceletc().trim()) ); // 부결사유
					//System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb");
					TableBody += Detail ;				
				}
	
				Temp += sPageViewStart + PageHeader;
				Temp = InoutFn.rplc(Temp, "{{page}}", "1");
				outputStream.print(Temp);
				outputStream.print(TableBody);
				outputStream.print(TableFooter1);
			//}
		}

		InOutArticle inoutarticle = new InOutArticle();
		//System.out.println("777777777777777777777777777");
		if(iErrorCode3 == 0){	
			vlist2 = inoutwriteproc.getArticle_vlist();
			//TotalPage= (vlist.size()+14) / 15;
			enumeration2 = vlist2.elements();				
			while(enumeration2.hasMoreElements()){
                inoutarticle = (InOutArticle)enumeration2.nextElement();
				i++; // 열 카운트
	
				Detail = TableDetail2;
				
				String si = Integer.toString(i);
				Detail = InoutFn.rplc(Detail,"{{r2c1}}", si); //NO
				Detail = InoutFn.rplc(Detail,"{{r2c2}}", InoutFn.csc(inoutarticle.getAserialno()) ); // 제조번호
				Detail = InoutFn.rplc(Detail,"{{r2c3}}", InoutFn.csc(inoutarticle.getArticlename()) ); //제품명
				Detail = InoutFn.rplc(Detail,"{{r2c4}}", InoutFn.csc(inoutarticle.getUnitname()) ); //단위
				Detail = InoutFn.rplc(Detail,"{{r2c5}}", InoutFn.csc(inoutarticle.getAsize()) ); // 규격
				Detail = InoutFn.rplc(Detail,"{{r2c6}}", InoutFn.getDecimalFormat(inoutarticle.getSinoutcnt(), 15) ); //수량
				Detail = InoutFn.rplc(Detail,"{{r2c7}}", InoutFn.csc(inoutarticle.getVenderpn()) ); // Vendre p/n
				Detail = InoutFn.rplc(Detail,"{{r2c8}}", InoutFn.csc(inoutarticle.getJasannokndname()) ); // 자산 번호종류
				Detail = InoutFn.rplc(Detail,"{{r2c9}}", InoutFn.csc(inoutarticle.getJsno()) ); // 자산 번호
				Detail = InoutFn.rplc(Detail,"{{r2c10}}", InoutFn.csc(inoutarticle.getMmno()) ); // 관리번호
				Detail = InoutFn.rplc(Detail,"{{r2c11}}", InoutFn.csc(inoutarticle.getDeptname()) ); // 부서명

				TableBody2 += Detail ;				
			}
			
			outputStream.print(TableBody2);
			outputStream.print(TableFooter2);

		}
		//System.out.println("888888888888888888888888");
		Detail = TableDetail3;
		TableBody3 += Detail;

		if(inoutwrite.getInoutkndname().trim().equals("반입필요")){
	    TableBody3 = InoutFn.rplc(TableBody3,"{{styleid}}", "style='display:inline;'" );
		}else{
	    TableBody3 = InoutFn.rplc(TableBody3,"{{styleid}}", "style='display:none;'" );
		}

		//작성자보관용 추가된 값
		PageFooter = InoutFn.rplc(PageFooter,"{{empname}}", InoutFn.csc(inoutwrite.getEmpname()).trim());
		PageFooter = InoutFn.rplc(PageFooter,"{{inoutserialno}}", InoutFn.csc(inoutwrite.getInoutserialno()).trim());

		outputStream.print(TableBody3);
		outputStream.print(TableFooter3);
		outputStream.print(PageFooter);
		outputStream.print(ReportFooter);
		outputStream.print(sPageViewEnd_Last);

		if(i==0){
			%>
				<script>
					alert("데이타가 없습니다.");
				</script>
			<%
		}
		}catch(Exception e) {
			e.printStackTrace();		
		} finally {			   
		    if(conn != null){try{ conn.close();}catch(SQLException ex){}}	
		    outputStream.print("<SCRIPT LANGUAGE='JavaScript'>\n");
			outputStream.print("<!-- \n");
			outputStream.print("top.ReportControlFrame.ReceiveTotalPage(1,"+LeftMargine+",'"+Orientation+"',2,1);\n");
			//outputStream.print("top.ReportViewFrame.HideProgressLayer(); \n");
			outputStream.print(" //-->\n");
			outputStream.print("</SCRIPT>\n");
			

			//	저장파일불러와서 출력화면에 보여주기 시작
			outputStream.close();
		}
		
		
		java.util.Random FileRnd = new java.util.Random();  
		int iFileRnd = FileRnd.nextInt(31767);                                      //-31767 ~ 31767 ������ int ����
		iFileRnd = (iFileRnd<0)?-iFileRnd:iFileRnd;                                 //����� ��ȯ
		iFileRnd += 1000 ;  
		String ss_random = ""+InoutFn.rplc(request.getRemoteAddr(),".","")+iFileRnd+System.currentTimeMillis();
		%>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
			ShowProgressLayer(350,250);
			//top.PrintFrame.location.href="/eSecurity/temp/<%=maskname%>?<%=ss_random%>";
			top.ReportViewFrame.location.href="/eSecurity/temp/<%=maskname%>?<%=ss_random%>";
			
		//-->
		</SCRIPT>
		<%
    } else {
		out.println("Report 템플리트 분석중 에러가 발생했습니다.");
	}
	
	//System.out.println("documentselect_make_list.jsp 3");

%>
<body id="printpage" bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" style="background-image:  url('/eSecurity/common/report/images/a4_bg_sero.gif');	background-repeat: no-repeat;	background-position: center top;	background-color: #999999;">
</body>