<%@page import="javax.naming.NamingException"%>
<%@page import="java.sql.Array"%>
<%@ page contentType="text/html;charset=UTF-8" session="false"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.SQLException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="esecurity.framework.report.InoutFn"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="esecurity.framework.report.ReportGen"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="esecurity.framework.report.DeleteFileAndDirUtil"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
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
            <p><span style="font-size:9pt;"><font face="굴림" color="#666666">처리중입니다<br>잠시  기다려주십시요</font></span></p>
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
<%!	
/*** 결재자 정보 추가를 위해 다시 연결함 20140411  start*/	
private String setLine_view(String[] line_ap, String[] line_app, String[] line_dept, String[] line_nm, String[] line_dt) {		
		StringBuilder builder = new StringBuilder();
				                                                                              
		builder.append("<tr>                                                          ");
		builder.append("	<th class='t_appfont t_padding' height='60'>결<br />재</th> ");
		builder.append("	<td class='t_appfont t_padding' width='85' align='center'>");
		builder.append("		<div><span style='color:red;'>"+line_ap[0]+"</span><span style='margin-left:7px;color:blue; '>"+line_app[0]+"</span> </div>");
		builder.append("		<div style='margin-top:5px;width:84px; display: !important; vertical-align: middle;'>"+line_dept[0]+"</div>");
		builder.append("		<div style='margin-top:5px;'> "+line_nm[0]+" </div>");		
		builder.append("	</td>");		
		builder.append("	<td class='t_appfont t_padding' width='85' align='center'>");
		builder.append("		<div><span style='color:red'>"+line_ap[1]+"</span><span style='margin-left:7px;color:blue'>"+line_app[1]+"</span> </div>");
		builder.append("		<div style='margin-top:5px;width:84px; display: !important; vertical-align: middle;'>"+line_dept[1]+"</div>");
		builder.append("		<div style='margin-top:5px;'> "+line_nm[1]+" </div>");	
		builder.append("	</td>");
		builder.append("	<td class='t_appfont t_padding' width='*' align='center'>");
		builder.append("		<div><span style='color:red'>"+line_ap[2]+"</span><span style='margin-left:7px;color:blue'>"+line_app[2]+"</span> </div>");
		builder.append("		<div style='margin-top:5px;width:83px; display: !important; vertical-align: middle;'>"+line_dept[2]+"</div>");
		builder.append("		<div style='margin-top:5px;'> "+line_nm[2]+" </div>");		
		builder.append("	</td>");
		builder.append("	<th class='t_appfont t_padding'>결<br />재</th>             ");
		builder.append("	<td class='t_appfont t_padding' width='85' align='center'>");
		builder.append("		<div><span style='color:red'>"+line_ap[3]+"</span><span style='margin-left:7px;color:blue'>"+line_app[3]+"</span> </div>");
		builder.append("		<div style='margin-top:5px;width:84px; display: !important; vertical-align: middle;'>"+line_dept[3]+"</div>");
		builder.append("		<div style='margin-top:5px;'> "+line_nm[3]+" </div>");		
		builder.append("	</td>");
		builder.append("	<td class='t_appfont t_padding' width='85' align='center'>");
		builder.append("		<div><span style='color:red'>"+line_ap[4]+"</span><span style='margin-left:7px;color:blue'>"+line_app[4]+"</span> </div>");
		builder.append("		<div style='margin-top:5px;width:84px; display: !important; vertical-align: middle;'>"+line_dept[4]+"</div>");
		builder.append("		<div style='margin-top:5px;'> "+line_nm[4]+" </div>");		
		builder.append("	</td>");
		builder.append("	<td class='t_appfont t_padding' width='*' align='center'>");
		builder.append("		<div><span style='color:red'>"+line_ap[5]+"</span><span style='margin-left:7px;color:blue'>"+line_app[5]+"</span> </div>");
		builder.append("		<div style='margin-top:5px;width:84px; display: !important; vertical-align: middle;'>"+line_dept[5]+"</div>");
		builder.append("		<div style='margin-top:5px;'> "+line_nm[5]+" </div>");		
		builder.append("	</td>");
		builder.append("</tr>                                                       ");		
		builder.append("<tr>                                                        ");
		builder.append("	<th class='t_appfont t_padding'>일자</th>                   ");
		builder.append("	<td class='t_appfont t_padding' align='center'>"+line_dt[0]+"</td>                    ");
		builder.append("	<td class='t_appfont t_padding' align='center'>"+line_dt[1]+"</td>                    ");
		builder.append("	<td class='t_appfont t_padding' align='center'>"+line_dt[2]+"</td>                    ");
		builder.append("	<th class='t_appfont t_padding'>일자</th>                   ");
		builder.append("	<td class='t_appfont t_padding' align='center'>"+line_dt[3]+"</td>                    ");
		builder.append("	<td class='t_appfont t_padding' align='center'>"+line_dt[4]+"</td>                    ");
		builder.append("	<td class='t_appfont t_padding' align='center'>"+line_dt[5]+"</td>                    ");
		builder.append("</tr>                                                         ");
			
		return builder.toString();
	} 

private String setLine_view1(String[] line_ap) {		
	StringBuilder builder = new StringBuilder();
			                                                                              
	builder.append("<tr>  ");
	builder.append("<td class='t_font t_padding' height='60'> ");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[0]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[1]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[2]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[3]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[4]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[5]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[6]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[7]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[8]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[9]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[10]+"</span></div>");
	builder.append("	<div><span style='margin-left:5px;'>"+line_ap[11]+"</span></div>");
	builder.append("</td> ");
	builder.append("</tr>                                                       ");	
	
		
	return builder.toString();
} 
%>
	
<%
	String pic_appl_no = "";
	String doc_no = "";
	
	Context ctx = null;     //Interface
	DataSource ds = null;   //Interface
	Connection conn = null;  //Interface
	PreparedStatement psmt = null;
	ResultSet rs = null;    //Interface
	
	//System.out.println("QueryString : "+request.getQueryString());
	String query = StringUtils.defaultIfEmpty(request.getParameter("query"),"");
	String[] parmeter = query.split("@");
	pic_appl_no = StringUtils.defaultIfEmpty(parmeter[0],"");	
	doc_no = StringUtils.defaultIfEmpty(parmeter[1],"");	

	// File Setting( ***** 화일이름을 반드시 설정할 것) *************************/
	String maskname = "";	
	String savePath = request.getRealPath("/eSecurity/temp/");
	maskname = "takePicture_template_" + emp_id + ".htm";
	File dataFile = new File(savePath +"\\" +maskname);
	String filename = maskname;
	PrintWriter outputStream = new PrintWriter(new FileOutputStream(dataFile));
	DeleteFileAndDirUtil.deleteFiles( request.getRealPath("/eSecurity/temp") , System.currentTimeMillis()-(3600000*24));//3600초(1시간)*24 하루동안 쌓인 임시파일은 삭제한다.
	//System.out.print(savePath +"\\" +maskname);
	ReportGen reportGen = new ReportGen();	

	reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/takePicture/takePicture_template.htm"));
	
	if (reportGen.SeperateTemplateDoc()) {	
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
		String appr_stat = "";	//결재상태
		//반입부서
		String app_dept = "";	
		String app_jw_nm = "";	
		String app_emp_nm = "";	
		
		String mod_yyyy = "";	
		String mod_mm = "";	
		String mod_dd = "";	
		
		try {			
			ctx = (Context)  new InitialContext();
			ds = (DataSource) ctx.lookup("jdbc/eSecurityDS");
			conn = ds.getConnection();
			conn.setAutoCommit(true);
			
			StringBuilder strSQL = new StringBuilder();
			strSQL.append(" SELECT PI.PIC_APPL_NO                                                                                                ");
			strSQL.append("\n   , PI.COMP_ID                                                                                                     ");
			strSQL.append("\n   , FN_GET_COMP_NM(PI.COMP_ID) AS COMP_NM                                                                          ");
			strSQL.append("\n   , DEPT_ID                                                                                                        ");
			strSQL.append("\n   , FN_GET_DEPT_NM(PI.DEPT_ID) AS DEPT_NM                                                                          ");
			strSQL.append("\n   , PI.JW_ID                                                                                                       ");
			strSQL.append("\n   , FN_GET_JW_NM(PI.JW_ID) AS JW_NM                                                                                ");
			strSQL.append("\n   , PI.EMP_ID                                                                                                      ");
			strSQL.append("\n   , FN_GET_EMP_NM(PI.EMP_ID) AS EMP_NM                                                                             ");
			strSQL.append("\n   , PI.INNO                                                                                                        ");
			strSQL.append("\n    , PI.PIC_PLACE                                                                                                  ");
	        strSQL.append("\n    , SUBSTR(PI.PIC_STRT_DT,1,4)||'-'||SUBSTR(PI.PIC_STRT_DT,5,2)||'-'||SUBSTR(PI.PIC_STRT_DT,7,2) AS PIC_STRT_DT   ");
	        strSQL.append("\n    , SUBSTR(PI.PIC_END_DT,1,4)||'-'||SUBSTR(PI.PIC_END_DT,5,2)||'-'||SUBSTR(PI.PIC_END_DT,7,2) AS PIC_END_DT       ");
	        strSQL.append("\n    , PI.PIC_OBJ                                                                                                    ");
	        strSQL.append("\n    , PI.PIC_BLDG                                                                                                   ");	
			strSQL.append("\n    , PI.PIC_EQMT                                                                                                   ");
	        strSQL.append("\n    , PI.PIC_CONTENT                                                                                                ");
	        strSQL.append("\n    , REPLACE(PI.PIC_CONTENT,chr(13)||chr(10),'<br />') AS PIC_CONTENT2                                             ");	        
			strSQL.append("\n    ,INOUT_GBN                                                                      ");
			strSQL.append("\n    ,PIC_EMP_ID                                                                     ");
			strSQL.append("\n    ,CASE WHEN INOUT_GBN = 'A0101002' THEN FN_GET_IO_EMP_NM2(PIC_EMP_ID)            ");
			strSQL.append("\n      ELSE FN_GET_EMP_NM(PIC_EMP_ID) END AS PIC_EMP_NM                            ");
			strSQL.append("\n    ,PIC_DEPTS_ID                                                                   ");
			strSQL.append("\n    ,CASE WHEN INOUT_GBN = 'A0101002' THEN FN_GET_IO_COMP_KO_NM(PIC_DEPTS_ID)       ");
			strSQL.append("\n      ELSE FN_GET_DEPT_NM(PIC_DEPTS_ID) END AS PIC_DEPTS_NM                       ");
	        strSQL.append("\n    , PI.PIC_HP_NO                                                                                              ");
	        strSQL.append("\n    , PI.COOP_DEPT_ID                                                                                               ");
	        strSQL.append("\n    , FN_GET_DEPT_NM(PI.COOP_DEPT_ID) AS COOP_DEPT_NM                                                               ");
	        strSQL.append("\n    , PI.COOP_EMP_ID                                                                                                ");
	        strSQL.append("\n    , FN_GET_EMP_NM(PI.COOP_EMP_ID) AS COOP_EMP_NM                                                                  ");
	        strSQL.append("\n    , PI.DOC_ID                                                                                                     ");
	        strSQL.append("\n    , PI.APPL_STAT                                                                                                  ");
	        strSQL.append("\n    , TO_CHAR(PI.CRT_DTM,'YYYY') AS CRT_YYYY                                                                                                  ");
	        strSQL.append("\n    , TO_CHAR(PI.CRT_DTM,'MM') AS CRT_MM                                                                                                  ");
	        strSQL.append("\n    , TO_CHAR(PI.CRT_DTM,'DD') AS CRT_DD                                                                                                  ");
	        strSQL.append("\n    , TO_CHAR(PI.MOD_DTM,'YYYY') AS MOD_YYYY                                                                                                  ");
	        strSQL.append("\n    , TO_CHAR(PI.MOD_DTM,'MM') AS MOD_MM                                                                                                  ");
	        strSQL.append("\n    , TO_CHAR(PI.MOD_DTM,'DD') AS MOD_DD                                                                                                  ");
			strSQL.append("\n FROM SV_PIC PI                                                                                                     ");
			strSQL.append("\n WHERE PI.PIC_APPL_NO = ?                                                                               ");
			
			int ps = 1;			
			psmt = conn.prepareStatement(strSQL.toString());
			psmt.setString(ps++, pic_appl_no); 			
			rs = psmt.executeQuery();
			psmt.clearParameters();
			strSQL.setLength(0);
			if(!rs.next()) {
			%>				
				<script>
					alert("데이타가 없습니다.");
				</script>
			<%
				return;
			}
			
			//페이지 뷰
			PageHeader = InoutFn.rplc(PageHeader,"{{co_dept_nm}}", InoutFn.csc(rs.getString("DEPT_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_jw_nm}}", InoutFn.csc(rs.getString("JW_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_emp_id}}", InoutFn.csc(rs.getString("EMP_ID")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_emp_nm}}", InoutFn.csc(rs.getString("EMP_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_inno}}", InoutFn.csc(rs.getString("INNO")));
			String inout = rs.getString("INOUT_GBN");
			/* if(inout.equals("A0101002")) {
				PageHeader = InoutFn.rplc(PageHeader,"{{co_depts}}", InoutFn.csc("소속업체명"));
			} else {
				PageHeader = InoutFn.rplc(PageHeader,"{{co_depts}}", InoutFn.csc("부서명"));
			} */
			
			PageHeader = InoutFn.rplc(PageHeader,"{{io_comp_nm}}", InoutFn.csc(rs.getString("PIC_DEPTS_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_emp_nm}}", InoutFn.csc(rs.getString("PIC_EMP_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_hp_no}}", InoutFn.csc(rs.getString("PIC_HP_NO")));
			PageHeader = InoutFn.rplc(PageHeader,"{{pic_dt}}", InoutFn.csc(rs.getString("PIC_STRT_DT")+" ~ "+rs.getString("PIC_END_DT")));
			PageHeader = InoutFn.rplc(PageHeader,"{{pic_place}}", InoutFn.csc(rs.getString("PIC_PLACE")));
			PageHeader = InoutFn.rplc(PageHeader,"{{pic_obj}}", InoutFn.csc(rs.getString("PIC_OBJ")));
			PageHeader = InoutFn.rplc(PageHeader,"{{pic_eqmt}}", InoutFn.csc(rs.getString("PIC_EQMT")));
			PageHeader = InoutFn.rplc(PageHeader,"{{pic_content}}", InoutFn.csc(rs.getString("PIC_CONTENT2")));
			PageHeader = InoutFn.rplc(PageHeader,"{{crt_yyyy}}", InoutFn.csc(rs.getString("CRT_YYYY")));
			PageHeader = InoutFn.rplc(PageHeader,"{{crt_mm}}", InoutFn.csc(rs.getString("CRT_MM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{crt_dd}}", InoutFn.csc(rs.getString("CRT_DD")));
			appr_stat = rs.getString("APPL_STAT");
			app_dept = rs.getString("DEPT_NM");
			app_jw_nm = rs.getString("JW_NM");
			app_emp_nm = rs.getString("EMP_NM");
			mod_yyyy = rs.getString("MOD_YYYY");
			mod_mm = rs.getString("MOD_MM");
			mod_dd = rs.getString("MOD_DD");
			
			rs.close();
			
			String Temp=ReportHeader;
			Temp += sPageViewStart + PageHeader;
			Temp = InoutFn.rplc(Temp, "{{page}}", "1");
			outputStream.print(Temp);			
			//outputStream.print(ReportHeader);
			//outputStream.print(PageHeader);			
			
			
			if(appr_stat.equals("Z0331005")) {	//결재완료일때만
				
				outputStream.print(TableHearder2);
			
				// 결재선				
				strSQL.append(" SELECT TO_CHAR(APPR_DTM, 'YYYY') AS APPR_YYYY ");
			    strSQL.append("\n        ,TO_CHAR(APPR_DTM,'MM') AS APPR_MM   ");
			    strSQL.append("\n        ,TO_CHAR(APPR_DTM,'DD') AS APPR_DD   ");
			    strSQL.append("\n FROM AP_DOC                                 ");
			    strSQL.append("\n     WHERE NVL(DEL_YN,'N') = 'N'             ");
			    strSQL.append("\n     AND APPR_STAT =  '20'                   ");
			    strSQL.append("\n     AND APPR_RESULT = '1'                   ");
			    strSQL.append("\n     AND DOC_ID = ?                          ");
				
				ps = 1;			
				psmt = conn.prepareStatement(strSQL.toString());
				psmt.setString(ps++, doc_no); 			
				rs = psmt.executeQuery();
				psmt.clearParameters();
				strSQL.setLength(0);
				if(!rs.next()) {
					TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_yyyy}}", InoutFn.csc(rs.getString("APPR_YYYY")));
					TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_mm}}", InoutFn.csc(rs.getString("APPR_MM")));
					TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_dd}}", InoutFn.csc(rs.getString("APPR_DD")));
				} else {
					//TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_yyyy}}", InoutFn.csc(dateString.substring(0,4)));
					//TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_mm}}", InoutFn.csc(dateString.substring(5,7)));
					//TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_dd}}", InoutFn.csc(dateString.substring(8,10)));
					
					TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_yyyy}}", InoutFn.csc(mod_yyyy));
					TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_mm}}", InoutFn.csc(mod_mm));
					TableDetail2 = InoutFn.rplc(TableDetail2,"{{sys_dd}}", InoutFn.csc(mod_dd));
				}
							
				outputStream.print(TableDetail2);
				
				TableDetail3 = InoutFn.rplc(TableDetail3,"{{app_dept}}", InoutFn.csc(app_dept));
				TableDetail3 = InoutFn.rplc(TableDetail3,"{{app_jw_nm}}", InoutFn.csc(app_jw_nm));
				TableDetail3 = InoutFn.rplc(TableDetail3,"{{app_emp_nm}}", InoutFn.csc(app_emp_nm));
				outputStream.print(TableDetail3);
			}
			/////////////////////////////////////////////////////////////////////
			/*      *********************************************** */                   
			 	strSQL.setLength(0);
				strSQL.append(" SELECT DECODE(APPR_DEPT_GBN,'1','요청부서','2','승인부서') || ' | ' || DECODE(APPR_RESULT,'1','승인','2','부결','3','장기미결','검토') || ' | 부서명 :' || NVL(FN_GET_DEPT_NM(DEPT_ID),' ')  || ' | 결재자 : ' || FN_GET_EMP_JW_NM(EMP_ID)|| ' | 결재일 : ' ||  TO_CHAR(MOD_DTM, 'YYYY-MM-DD')  AS APPR_INFO ");
			 	strSQL.append("\n FROM AP_APPR ");
			 	strSQL.append("\n WHERE DOC_ID = ? ");
				strSQL.append("\n ORDER BY APPR_DEPT_GBN, AP_SEQ   ");
				
				ps = 1;			
				psmt = conn.prepareStatement(strSQL.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				psmt.setString(ps++, doc_no); 			
				rs = psmt.executeQuery();
				psmt.clearParameters();
				strSQL.setLength(0);			
				int rs_cnt = 0;
				String[] line_ap = new String[12];
				
				while(rs.next()) {			
					line_ap[rs_cnt]	= rs.getString("APPR_INFO");		// 1 : 요청, 2: 허가		
					rs_cnt++;			
				}
				for(int in = rs_cnt; in < 12; in++)
				{
					line_ap[in] = "&nbsp;";	
				}
				
				TableDetail4 = InoutFn.rplc(TableDetail4,"{{appr_list1}}", InoutFn.csc(setLine_view1(line_ap)));
			
				outputStream.print(TableHearder4);
				outputStream.print(TableDetail4);
			/*******************************/
			////////////////////////////////////////////// 
/***********************************************************/			
/*/* 결재선정보 추가를 위해 다시 재 연결함 20140411 start 
			// 결재선
			strSQL.append(" SELECT  DOC_ID, AP_SEQ, APPR_DEPT_GBN,                    ");
			strSQL.append("\n    COMP_ID, DEPT_ID, JW_ID,                             ");
			strSQL.append("\n    EMP_ID, APPR_RESULT,                       ");
			strSQL.append("\n    CANCELETC, AUTO_SIGN, TRGFLAG,                       ");			
			strSQL.append("\n    CASE WHEN APPR_RESULT IN ('1','2') THEN TO_CHAR(APPR_DTM,'YYYY')||' / '||TO_CHAR(APPR_DTM,'MM')||' / '||TO_CHAR(APPR_DTM,'DD') ELSE '' END AS APPR_DTM,                       ");			
			strSQL.append("\n    TO_CHAR(CRT_DTM, 'YYYY-MM-DD HH24:MI') AS CRT_DTM,   ");			
			strSQL.append("\n    TO_CHAR(MOD_DTM, 'YYYY-MM-DD HH24:MI') AS MOD_DTM,   ");
			strSQL.append("\n    FN_GET_EMP_JW_NM(EMP_ID) AS EMP_NM,                     ");
			strSQL.append("\n    FN_GET_CONAME('DEPT', DEPT_ID, COMP_ID) AS DEPT_NM,  ");
			strSQL.append("\n    FN_GET_CONAME('JW', JW_ID, COMP_ID) AS JW_NM         ");
			strSQL.append("\n FROM SECURITYADM.AP_APPR                                ");
			strSQL.append("\n WHERE DOC_ID = ?                                 ");
			strSQL.append("\n ORDER BY APPR_DEPT_GBN, AP_SEQ ASC                      ");
			
			ps = 1;			
			psmt = conn.prepareStatement(strSQL.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			psmt.setString(ps++, doc_no); 			
			rs = psmt.executeQuery();
			psmt.clearParameters();
			strSQL.setLength(0);			
			int rs_cnt = 0, dept1_cnt = 0, dept2_cnt = 0;
			String[] line_ap = new String[6]; String[] line_app = new String[6]; 
			String[] line_dept = new String[6]; String[] line_nm = new String[6]; 
			String[] line_dt = new String[6];		
			
			while(rs.next()) {			
				if(rs.getString("APPR_DEPT_GBN").equals("1")) {		// 1 : 요청, 2: 허가		
					if(rs.next()) {
						if(rs.getString("APPR_DEPT_GBN").equals("2")) {
							if(rs.previous()) {
								String ap = "&nbsp;", app = "&nbsp;";
								if(rs.getString("APPR_RESULT").equals("0")) {
									ap = "&nbsp;";
									app = "미결";
								} else if(rs.getString("APPR_RESULT").equals("1")){
									ap = "&nbsp;";
									app = "승인";
								} else {
									ap = "부결";
									app = "&nbsp;";
								}
								
								line_ap[rs_cnt] = ap;
								line_app[rs_cnt] = app;
								line_dept[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("DEPT_NM").replace(" ","<br>"),"&nbsp;");							
								line_nm[rs_cnt] = rs.getString("EMP_NM");
								line_dt[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("APPR_DTM"),"&nbsp;");
								
								dept1_cnt++;
								rs_cnt++;
							}
							for(int j = dept1_cnt; j < 3; j++) {
								line_ap[rs_cnt] = "&nbsp;";
								line_app[rs_cnt] = "&nbsp;";
								line_dept[rs_cnt] = "&nbsp;";
								line_nm[rs_cnt] = "&nbsp;";
								line_dt[rs_cnt] = "&nbsp;";
								rs_cnt++;
							}
						} else {
							if(rs.previous()) {
								String ap = "&nbsp;", app = "&nbsp;";
								if(rs.getString("APPR_RESULT").equals("0")) {
									ap = "&nbsp;";
									app = "미결";
								} else if(rs.getString("APPR_RESULT").equals("1")){
									ap = "&nbsp;";
									app = "승인";
								} else {
									ap = "부결";
									app = "&nbsp;";
								}
								
								line_ap[rs_cnt] = ap;
								line_app[rs_cnt] = app;
							
								line_dept[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("DEPT_NM").replace(" ","<br>"),"&nbsp;");							
								line_nm[rs_cnt] = rs.getString("EMP_NM");
								line_dt[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("APPR_DTM"),"&nbsp;");
								
								dept1_cnt++;
								rs_cnt++;
							}
						}	
					} else {
						if(rs.previous()) {
							String ap = "&nbsp;", app = "&nbsp;";
							if(rs.getString("APPR_RESULT").equals("0")) {
								ap = "&nbsp;";
								app = "미결";
							} else if(rs.getString("APPR_RESULT").equals("1")){
								ap = "&nbsp;";
								app = "승인";
							} else {
								ap = "부결";
								app = "&nbsp;";
							}
							
							line_ap[rs_cnt] = ap;
							line_app[rs_cnt] = app;
							line_dept[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("DEPT_NM").replace(" ","<br>"),"&nbsp;");							
							line_nm[rs_cnt] = rs.getString("EMP_NM");
							line_dt[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("APPR_DTM"),"&nbsp;");
							
							dept1_cnt++;
							rs_cnt++;
						}
												
						for(int j = dept1_cnt; j < 6; j++) {
							line_ap[rs_cnt] = "&nbsp;";
							line_app[rs_cnt] = "&nbsp;";
							line_dept[rs_cnt] = "&nbsp;";
							line_nm[rs_cnt] = "&nbsp;";
							line_dt[rs_cnt] = "&nbsp;";
							rs_cnt++;
						}
					}									
				} else {
					if(rs.isLast()) {							
						String ap = "&nbsp;", app = "&nbsp;";
						if(rs.getString("APPR_RESULT").equals("0")) {
							ap = "&nbsp;";
							app = "미결";
						} else if(rs.getString("APPR_RESULT").equals("1")){
							ap = "&nbsp;";
							app = "승인";
						} else {
							ap = "부결";
							app = "&nbsp;";
						}
						
						line_ap[rs_cnt] = ap;
						line_app[rs_cnt] = app;
						line_dept[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("DEPT_NM").replace(" ","<br>"),"&nbsp;");	
						line_nm[rs_cnt] = rs.getString("EMP_NM");
						line_dt[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("APPR_DTM"),"&nbsp;");
						
						dept2_cnt++;
						rs_cnt++;
						
						for(int j = dept2_cnt; j < 3; j++) {
							line_ap[rs_cnt] = "&nbsp;";
							line_app[rs_cnt] = "&nbsp;";
							line_dept[rs_cnt] = "&nbsp;";
							line_nm[rs_cnt] = "&nbsp;";
							line_dt[rs_cnt] = "&nbsp;";
							rs_cnt++;
						}						
					} else {
						String ap = "&nbsp;", app = "&nbsp;";
						if(rs.getString("APPR_RESULT").equals("0")) {
							ap = "&nbsp;";
							app = "미결";
						} else if(rs.getString("APPR_RESULT").equals("1")){
							ap = "&nbsp;";
							app = "승인";
						} else {
							ap = "부결";
							app = "&nbsp;";
						}
						
						line_ap[rs_cnt] = ap;
						line_app[rs_cnt] = app;
						line_dept[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("DEPT_NM").replace(" ","<br>"),"&nbsp;");	
						line_nm[rs_cnt] = rs.getString("EMP_NM");
						line_dt[rs_cnt] = StringUtils.defaultIfEmpty(rs.getString("APPR_DTM"),"&nbsp;");
						
						dept2_cnt++;
						rs_cnt++;
					}					
				}
			}
			
			TableDetail4 = InoutFn.rplc(TableDetail4,"{{appr_list1}}", InoutFn.csc(setLine_view(line_ap, line_app, line_dept, line_nm, line_dt)));
		
			outputStream.print(TableHearder4);
			outputStream.print(TableDetail4);
 
************** 결재선정보 추가를 위해 다시 재 연결함 20140411 end */
/***********************************************************/			
			 
			 
			outputStream.print(PageFooter);
			outputStream.print(ReportFooter);
			outputStream.print(sPageViewEnd_Last);
		
		}catch(Exception e) {
			e.printStackTrace();		
		} finally {			    
			if(rs != null){try{ rs.close();}catch(SQLException ex){}}	
		    if(psmt != null){try{ psmt.close();}catch(SQLException ex){}}			   
		    if(conn != null){try{ conn.close();}catch(SQLException ex){}}	
		    if(ctx != null){try{ ctx.close();}catch(NamingException ex){}}			 
			
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
%>
<body id="printpage" bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" style="background-image:  url('/eSecurity/common/report/images/a4_bg_sero.gif');	background-repeat: no-repeat;	background-position: center top;	background-color: #999999;">
</body>