<%@ page language="java" contentType="text/html; charset=UTF-8" session="false"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="esecurity.framework.eaglet.report.ER"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%> 
<%
//request.setCharacterEncoding("UTF-8");
/* HttpSession session = request.getSession(false);
if(session == null){ //세션이 null을 지니면 페이지전환
    response.sendRedirect("/eSecurity/insNet/login.jsp");
    return;
}
//out.flush();
Map login = new HashMap();
if(request.getSession().getAttribute("Login") != null) {
	login = (Map)request.getSession().getAttribute("Login");
} else {
	response.sendRedirect("/eSecurity/insNet/login.jsp");
    return;
}
 */
//IDataSet result = WebUtils.getResultDataSet(request);
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); 
response.setHeader("cache-control","no-cache"); //HTTP 1.1

String Top_HTML = "";
String Bottom_HTML = "";

Top_HTML += "";
%>
<%
//============================================================== 
// 리포트 초기화.
ER x = new ER(response, request);
// ==============================================================

Context ctx = null;     //Interface
DataSource ds = null;   //Interface

Connection con = null;  //Interface
PreparedStatement psmt = null;
ResultSet rs = null;    //Interface

PreparedStatement psmt2 = null;
ResultSet rs2 = null;    //Interface

//get parameter
String vsit_rsrv_id = StringUtils.defaultIfEmpty(request.getParameter("vsit_rsrv_id"),"");
String vsit_rsrv_man_id = StringUtils.defaultIfEmpty(request.getParameter("vsit_rsrv_man_id"),"");

//out.print("adsfsdf");

/***********************************************************************************/

try {
	
	
	ctx = (Context)  new InitialContext();
	ds = (DataSource) ctx.lookup("jdbc/eSecurityDS");
	con = ds.getConnection();

	con.setAutoCommit(true);
		
	StringBuilder strSQL = new StringBuilder();
	strSQL.append("SELECT VS.DOC_ID,  FN_GET_COMP_NM(VS.COMP_ID)AS COMPANYNAME, FN_GET_DEPT_NM(VS.DEPT_ID) AS DEPT_NAME")
		  .append("		,FN_GET_IO_COMP_KO_NM(IO.IO_COMP_ID) AS VSIT_COMP_NM, FN_GET_EMP_NM(VS.EMP_ID) AS EMP_NAME")
		  .append("		,'' AS  OFTL, IO.EMP_NM AS VISITOR_NAME, VS.EMP_ID AS EMP_NO, SUBSTR(IO.JUMIN_NO,1,6) AS SSN")
		  .append("		,'' AS SSN_NO, VS.INNO, IO.HP_NO AS CNTC_NO, '' AS QTY, ")
	      .append("     SUBSTR(VST_STRT_DT,1,4)||'-'||SUBSTR(VST_STRT_DT,5,2)||'-'||SUBSTR(VST_STRT_DT,7,2) AS RSRV_STRT_DT, ")
	      .append("     CASE WHEN DC.DOC_ID IS NULL THEN FN_GET_EMP_NM(VS.EMP_ID) ")
	      .append("          ELSE FN_GET_EMP_NM(DC.EMP_ID) ")
	      .append("      END AS D_EMP_NAME, ")
	      .append("     CASE WHEN DC.DOC_ID IS NULL THEN VS.EMP_ID ")
	      .append("          ELSE DC.EMP_ID ")
	      .append("      END AS D_EMP_NO, ")
	      .append("     CASE WHEN DC.DOC_ID IS NULL THEN FN_GET_JW_NM ((SELECT C.JW_ID FROM CO_EMP C WHERE C.EMP_ID = VS.EMP_ID)) ")
	      .append("          ELSE FN_GET_JW_NM(DC.JW_ID) ")
	      .append("      END AS D_JW_NAME ")
		  .append("			FROM  IO_VST_MAN MA, IO_VST VS")
		  .append("				 ,IO_EMP IO,  AP_DOC DC")
		  .append("			WHERE VS.VST_APPL_NO = MA.VST_APPL_NO")
		  .append("			AND MA.IO_EMP_ID = IO.IO_EMP_ID")
		  .append("			AND VS.DOC_ID = DC.DOC_ID(+)")
		  .append("	   		AND  MA.VST_APPL_NO = ?")
		  .append("	    	AND  MA.IO_EMP_ID = ?");

	
	int i = 1;
	//System.out.print(strSQL.toString());
	
	psmt = con.prepareStatement(strSQL.toString());
	psmt.setString(i++, vsit_rsrv_id); 
	psmt.setString(i++, vsit_rsrv_man_id); 
	rs = psmt.executeQuery();
	
	
	StringBuilder strSQL2 = new StringBuilder();
	strSQL2.append("SELECT VST_APPL_NO, IO_EMP_ID, ITEQMT_SEQ, MODEL_NM, QTY, ITM_NM")
    .append(", FN_GET_CODE('A023',ITEQMT_GBN, 'DETL_CD') AS DETL_CD_A023 , FN_GET_CODE('A024',USE_GBN, 'DETL_CD') AS A024_DETL_CD, FN_GET_CODE('A025',OPEN_GBN, 'DETL_CD')  AS DETL_CD_A025, WIRE_MAC_ADDR")
    .append(",WIRELESS_MAC_ADDR, OPEN_BIT, OPEN_RSN, OPEN_GBN")
    .append(", CASE WHEN  BITAND(OPEN_BIT,4096) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_13")
    .append(", CASE WHEN  BITAND(OPEN_BIT,2048) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_12")
    .append(", CASE WHEN  BITAND(OPEN_BIT,1024) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_11")
    .append(", CASE WHEN  BITAND(OPEN_BIT,512) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_10")
    .append(", CASE WHEN  BITAND(OPEN_BIT,256) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_9")
    .append(", CASE WHEN  BITAND(OPEN_BIT,128) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_8")
    .append(", CASE WHEN  BITAND(OPEN_BIT,64) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_7")
    .append(", CASE WHEN  BITAND(OPEN_BIT,32) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_6")
    .append(", CASE WHEN  BITAND(OPEN_BIT,16) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_5")
    .append(", CASE WHEN  BITAND(OPEN_BIT,8) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_4")
    .append(", CASE WHEN  BITAND(OPEN_BIT,4) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_3")
    .append(", CASE WHEN  BITAND(OPEN_BIT,2) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_2")
    .append(", CASE WHEN  BITAND(OPEN_BIT,1) > 0 THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />' ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />' END AS OPEN_BIT_1")
    .append(", CASE ITEQMT_GBN WHEN 'A0231001' THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />노트북 => ' || TO_CHAR(QTY) ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />노트북' END AS NOTEBOOK")
    .append(", CASE ITEQMT_GBN WHEN 'A0231002' THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />메모리스틱(카드 등) => ' || TO_CHAR(QTY)  ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />메모리스틱(카드 등)' END AS MEMORY")
    .append(", CASE ITEQMT_GBN WHEN 'A0231008' THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />HDD류 => ' || TO_CHAR(QTY) ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />HDD류' END AS CAMERA")
    .append(", CASE ITEQMT_GBN WHEN 'A0231007' THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />CD류 => ' || TO_CHAR(QTY) ELSE  '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />CD류' END AS CD")
    .append(", CASE ITEQMT_GBN WHEN 'A0231005' THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />디스켓류(FDD,MO디스켓,ZIP디스켓 등) => ' || TO_CHAR(QTY) ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />디스켓류(FDD,MO디스켓,ZIP디스켓 등)' END AS FDD")
    .append(", CASE ITEQMT_GBN WHEN 'A0231006' THEN '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' checked='||'''checked'''||' />기타 => ' || TO_CHAR(QTY) ELSE '<input type='||'''checkbox'''||' disabled='||'''disabled'''||' />기타' END AS ETC")
	.append(",'' AS OPEN_RSN2, SUBSTR(OUT_SCHD_DT,1,4)||'-'||SUBSTR(OUT_SCHD_DT,5,2)||'-'||SUBSTR(OUT_SCHD_DT,7,2) AS OUT_DT")
    .append("		FROM IO_VST_MAN_ITEQMT")
    .append("		WHERE VST_APPL_NO = ?")
    .append("		AND IO_EMP_ID = ?")
    .append("		ORDER BY ITEQMT_SEQ ASC");
	
	int j = 1;
	psmt2 = con.prepareStatement(strSQL2.toString());
	psmt2.setString(j++, vsit_rsrv_id); 
	psmt2.setString(j++, vsit_rsrv_man_id); 
	rs2 = psmt2.executeQuery();
	 
	//System.out.print(strSQL2.toString());
	String itm_nm="";
	String model_nm="";
	String qty ="";
	String out_dt = "";
	String detl_cd_a023 = "";
	String a024_detl_cd = "";
	String detl_cd_a025 = "";
	String open_rsn = "";
	String open_rsn1 = "";
	String open_rsn2 = "";
	String open_bit_1 = "";
	String open_bit_2 = "";
	String open_bit_3 = "";
	String open_bit_4 = "";
	String open_bit_5 = "";
	String open_bit_6 = "";
	String open_bit_7 = "";
	String open_bit_8 = "";
	String open_bit_9 = "";
	String open_bit_10 = "";
	String open_bit_11 = "";
	String open_bit_12 = "";
	String open_bit_13 = "";
	String notebook = "";
	String memory = "";
	String camera = "";
	String cd = "";
	String fdd = "";
	String etc = "";
	String open_gbn = "";
	
	if(rs2.next()){
		out_dt = rs2.getString("OUT_DT");
		detl_cd_a023 = rs2.getString("DETL_CD_A023");
		a024_detl_cd = rs2.getString("A024_DETL_CD");
		detl_cd_a025 = rs2.getString("DETL_CD_A025");
		open_gbn = rs2.getString("OPEN_GBN");
		open_rsn = rs2.getString("OPEN_RSN");
		open_bit_1 = rs2.getString("OPEN_BIT_1");
		open_bit_2 = rs2.getString("OPEN_BIT_2");
		open_bit_3 = rs2.getString("OPEN_BIT_3");
		open_bit_4 = rs2.getString("OPEN_BIT_4");
		open_bit_5 = rs2.getString("OPEN_BIT_5");
		open_bit_6 = rs2.getString("OPEN_BIT_6");
		open_bit_7 = rs2.getString("OPEN_BIT_7");
		open_bit_8 = rs2.getString("OPEN_BIT_8");
		open_bit_9 = rs2.getString("OPEN_BIT_9");
		open_bit_10 = rs2.getString("OPEN_BIT_10");
		open_bit_11 = rs2.getString("OPEN_BIT_11");
		open_bit_12 = rs2.getString("OPEN_BIT_12");
		open_bit_13 = rs2.getString("OPEN_BIT_13");
		notebook = rs2.getString("NOTEBOOK");
		memory = rs2.getString("MEMORY");
		camera = rs2.getString("CAMERA");
		cd = rs2.getString("CD");
		fdd = rs2.getString("FDD");
		etc = rs2.getString("ETC");
		qty = rs2.getString("QTY");
		model_nm = rs2.getString("MODEL_NM");
		itm_nm=rs2.getString("ITM_NM");
		//System.out.println("recordset="+out_dt);
	}
	 
	if(open_gbn.equals("A0251011")){
	  open_rsn1 = open_rsn;
	}else{ 
		//if(open_gbn.equals("A0251001")){
	  open_rsn1 = "";
	  open_rsn2 = "";
	}
	
	
	// ============================================================== 
	// 리포트 인쇄 및  페이지 설정.
	// ==============================================================
	x.SetPrintPage("VSIT_RSRV Print", "", "", 0.5, 0.5, 0.3, 0.3, ER.PORTRAIT, 0); //LANDSCAPE 가로인쇄 & PORTRAIT 세로인쇄
	x.SetTemplate("/eSecurity/common/eaglet/report/mk/inout/vsit_rsrv/vsit_rsrv_template.htm");
	//x.SetTemplate("vsit_rsrv_template.htm");
	
	x.SetReportTitle(0);
	x.SetPageHeader(105);
	x.SetTable(1, 25, 25);
	x.SetTable(2, 25, 25);
	x.SetTable(3, 25, 25);

	// ============================================================== 
	// 리포트 변수선언, 쿼리결과.
	// ==============================================================

	// resultset
	x.AddResultSet(rs, "TII");

	// define variables
	x.VarString("Top_HTML", Top_HTML);

	x.VarString("PageBody");
	x.VarString("Temp");
	x.VarString("DOC_ID");
	x.VarString("COMPANYNAME");
	x.VarString("DEPT_NAME");
	x.VarString("VSIT_COMP_NM");
	x.VarString("EMP_NAME");
	x.VarString("OFTL");
	x.VarString("VISITOR_NAME");
	x.VarString("EMP_NO");
	x.VarString("SSN_NO");
	x.VarString("SSN");
	x.VarString("INNO");
	x.VarString("CNTC_NO");
	x.VarString("QTY",qty);
	x.VarString("RSRV_STRT_DT");
	x.VarString("D_EMP_NAME");
	x.VarString("D_EMP_NO");
	x.VarString("D_JW_NAME");
	x.VarString("OPEN_BIT_1",open_bit_1);
	x.VarString("OPEN_BIT_2",open_bit_2);
	x.VarString("OPEN_BIT_3",open_bit_3);
	x.VarString("OPEN_BIT_4",open_bit_4);
	x.VarString("OPEN_BIT_5",open_bit_5);
	x.VarString("OPEN_BIT_6",open_bit_6);
	x.VarString("OPEN_BIT_7",open_bit_7);
	x.VarString("OPEN_BIT_8",open_bit_8);
	x.VarString("OPEN_BIT_9",open_bit_9);
	x.VarString("OPEN_BIT_10",open_bit_10);
	x.VarString("OPEN_BIT_11",open_bit_11);
	x.VarString("OPEN_BIT_12",open_bit_12);
	x.VarString("OPEN_BIT_13",open_bit_13);
	x.VarString("OPEN_RSN",open_rsn1);
	x.VarString("OPEN_RSN2",open_rsn2);
	x.VarString("OPEN_GBN",open_gbn);
	x.VarString("OUT_DT",out_dt);
	x.VarString("DETL_CD_A023",detl_cd_a023);
	x.VarString("A024_DETL_CD",a024_detl_cd);
	x.VarString("DETL_CD_A025",detl_cd_a025);
	x.VarString("NOTEBOOK",notebook);
	x.VarString("MEMORY",memory);
	x.VarString("CAMERA",camera);
	x.VarString("CD",cd);
	x.VarString("FDD",fdd);
	x.VarString("ETC",etc);
	x.VarString("MODEL_NM",model_nm);
	x.VarString("ITM_NM",itm_nm);
	
	
	
	// ==============================================================
	// 리포트 작성 : AREA_INIT
	// ==============================================================
	x.OpTable(ER.AREA_INIT, "PageBody", "=", 1, ER.TABLE_HEADER);
	
	x.ReplaceVar(ER.AREA_INIT, "VAR_ReportHeader", "{{ER_EagletReportUrl}}", "ER_EagletReportUrl");
	x.ReplaceVar(ER.AREA_INIT, "VAR_ReportHeader", "{{ER_EagletReportUrl}}", "ER_EagletReportUrl");
	x.ReplaceVar(ER.AREA_INIT, "VAR_PageHeader", "{{ER_EagletReportUrl}}", "ER_EagletReportUrl", ER.REPLACE_ALL);
	x.ReplaceVar(ER.AREA_INIT, "VAR_PageHeader", "{{ER_ReportMakeTime}}", "ER_ReportMakeTime");
	x.ReplaceVar(ER.AREA_INIT, "VAR_PageHeader", "[[DOC_ID]]", "DOC_ID");
	x.ReplaceVar(ER.AREA_INIT, "VAR_PageHeader", "{{pfc1}}", "Top_HTML" );
	x.ReplaceVar(ER.AREA_INIT, "VAR_PageHeader", "{{ER_HostUrl}}", "ER_HostUrl", ER.REPLACE_ALL);

	//String DATE_TERM = "", PARTNER_NM = "", GRP_NM = "", DEPT_NM = "", A001_DETL_NM = "";
	


	// ============================================================== 
	// 리포트 작성 : AREA_PAGE
	// ==============================================================
	x.ReplaceVar(ER.AREA_PAGE, "VAR_Page", "{{ER_Page}}", "VAR_NowPage", ER.REPLACE_ALL);

	x.OpVar(ER.AREA_PAGE, "VAR_Page", "<<", "PageBody");
	x.OpTable(ER.AREA_PAGE, "VAR_Page", "<<", 1, ER.TABLE_FOOTER);
	x.OpVar(ER.AREA_PAGE, "VAR_Page", "<<", "VAR_PageFooter");

	x.OpTable(ER.AREA_PAGE, "PageBody", "=", 1, ER.TABLE_HEADER);
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "{{image}}", "ER_EagletReportUrl" );
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "{{image2}}", "ER_EagletReportUrl" );
			
	// ============================================================== 
	// 리포트 작성 : AREA_MAKE
	// ==============================================================

	//x.PrintSum(ER.AREA_MAKE, "PageBody", "PARTNER_NM", ER.OPT_PRINT_CALC, 2);		// sub sum
	//x.PrintSum(ER.AREA_MAKE, "PageBody", "", ER.OPT_CALC_ONLY, 3);			// total sum

	x.OpResultSet(ER.AREA_MAKE, "DOC_ID", "=", "DOC_ID");
	x.OpResultSet(ER.AREA_MAKE, "COMPANYNAME", "=", "COMPANYNAME");
	x.OpResultSet(ER.AREA_MAKE, "DEPT_NAME", "=", "DEPT_NAME");
	x.OpResultSet(ER.AREA_MAKE, "VSIT_COMP_NM", "=", "VSIT_COMP_NM");
	x.OpResultSet(ER.AREA_MAKE, "EMP_NAME", "=", "EMP_NAME");
	x.OpResultSet(ER.AREA_MAKE, "VISITOR_NAME", "=", "VISITOR_NAME");
	x.OpResultSet(ER.AREA_MAKE, "EMP_NO", "=", "EMP_NO");
	x.OpResultSet(ER.AREA_MAKE, "SSN_NO", "=", "SSN_NO");
	x.OpResultSet(ER.AREA_MAKE, "SSN", "=", "SSN");
	x.OpResultSet(ER.AREA_MAKE, "INNO", "=", "INNO");
	x.OpResultSet(ER.AREA_MAKE, "CNTC_NO", "=", "CNTC_NO");
	x.OpResultSet(ER.AREA_MAKE, "RSRV_STRT_DT", "=", "RSRV_STRT_DT");
	x.OpResultSet(ER.AREA_MAKE, "D_EMP_NAME", "=", "D_EMP_NAME");
	x.OpResultSet(ER.AREA_MAKE, "D_EMP_NO", "=", "D_EMP_NO");
	x.OpResultSet(ER.AREA_MAKE, "D_JW_NAME", "=", "D_JW_NAME");


	//x.Begin(ER.AREA_MAKE, ER.LOGIC_AND, "VAR_RowPos", ">", 1);
	/*x.BeginVar(ER.AREA_MAKE, ER.LOGIC_END, "DOC_ID", "!=", "DOC_ID");
	{				
		x.MakePage(ER.AREA_MAKE);		//강제페이지넘김					
	}
	x.End(ER.AREA_MAKE);*/

	//x.OpVar(ER.AREA_MAKE, "DOC_ID", "=", "DOC_ID");

	x.PrintRecord(ER.AREA_MAKE, "PageBody", 1);

	x.ReplaceVar(ER.AREA_MAKE, "VAR_PageHeader", "[[DOC_ID]]", "DOC_ID");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[COMPANYNAME]]", "COMPANYNAME");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DEPT_NAME]]", "DEPT_NAME");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[VSIT_COMP_NM]]", "VSIT_COMP_NM");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[EMP_NAME]]", "EMP_NAME");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[VISITOR_NAME]]", "VISITOR_NAME");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[EMP_NO]]", "EMP_NO");
	//x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[SSN_NO]]", "SSN_NO");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[SSN_NO]]", "SSN");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[INNO]]", "INNO");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[CNTC_NO]]", "CNTC_NO");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[A024_DETL_CD]]", "A024_DETL_CD");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[RSRV_STRT_DT]]", "RSRV_STRT_DT");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OUT_DT]]", "OUT_DT");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[D_EMP_NAME]]", "D_EMP_NAME");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[D_EMP_NO]]", "D_EMP_NO");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[D_JW_NAME]]", "D_JW_NAME");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DETL_CD_A023]]", "DETL_CD_A023");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[QTY]]", "QTY");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_1]]", "OPEN_BIT_1");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_2]]", "OPEN_BIT_2");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_3]]", "OPEN_BIT_3");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_4]]", "OPEN_BIT_4");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_5]]", "OPEN_BIT_5");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_6]]", "OPEN_BIT_6");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_7]]", "OPEN_BIT_7");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_8]]", "OPEN_BIT_8");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_9]]", "OPEN_BIT_9");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_10]]", "OPEN_BIT_10");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_11]]", "OPEN_BIT_11");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_12]]", "OPEN_BIT_12");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_13]]", "OPEN_BIT_13");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DETL_CD_A025]]", "DETL_CD_A025");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_RSN]]", "OPEN_RSN");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_RSN2]]", "OPEN_RSN2");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_GBN]]", "OPEN_GBN");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[NOTEBOOK]]", "NOTEBOOK");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[MEMORY]]", "MEMORY");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[CAMERA]]", "CAMERA");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[CD]]", "CD");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[FDD]]", "FDD");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[ETC]]", "ETC");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "{{ER_ReportMakeTime}}", "ER_ReportMakeTime");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DOC_ID]]", "DOC_ID");
	
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[ITM_NM]]", "ITM_NM");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[MODEL_NM_1]]", "MODEL_NM");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OUT_DT_1]]", "OUT_DT");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[A024_DETL_CD_1]]", "A024_DETL_CD");
	x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[QTY_1]]", "QTY");
	//x.ReplaceVar(ER.AREA_MAKE, "PageBody", "{{DOC_ID}}", "DOC_ID");
	// ============================================================== 
	// 리포트 작성 : AREA_LAST
	// ==============================================================

	//x.PrintSum(ER.AREA_LAST, "PageBody", "PARTNER_NM", ER.OPT_PRINT_ONLY, 2);		// sub sum
	//x.PrintSum(ER.AREA_LAST, "PageBody", "", ER.OPT_PRINT_ONLY, 3);				// total sum

//	x.ReplaceVar(ER.AREA_LAST, "PageBody", "[[DOC_ID]]", "DOC_ID");


	// make last page
	x.OpVar(ER.AREA_LAST, "VAR_Page", "<<", "VAR_PageHeader");
	x.ReplaceVar(ER.AREA_LAST, "VAR_Page", "{{ER_Page}}", "VAR_NowPage", ER.REPLACE_ALL);

	x.OpVar(ER.AREA_LAST, "VAR_Page", "<<", "PageBody");
	x.OpTable(ER.AREA_LAST, "VAR_Page", "<<", 1, ER.TABLE_FOOTER);
	x.OpVar(ER.AREA_LAST, "VAR_Page", "<<", "VAR_PageFooter");		
	

	

	int pageno = 1;
	while(rs2.next()){
		pageno++;

		out_dt = rs2.getString("OUT_DT");
		detl_cd_a023 = rs2.getString("DETL_CD_A023");
		a024_detl_cd = rs2.getString("A024_DETL_CD");
		detl_cd_a025 = rs2.getString("DETL_CD_A025");
		open_rsn = rs2.getString("OPEN_RSN");
		open_gbn = rs2.getString("OPEN_GBN");
		open_bit_1 = rs2.getString("OPEN_BIT_1");
		open_bit_2 = rs2.getString("OPEN_BIT_2");
		open_bit_3 = rs2.getString("OPEN_BIT_3");
		open_bit_4 = rs2.getString("OPEN_BIT_4");
		open_bit_5 = rs2.getString("OPEN_BIT_5");
		open_bit_6 = rs2.getString("OPEN_BIT_6");
		open_bit_7 = rs2.getString("OPEN_BIT_7");
		open_bit_8 = rs2.getString("OPEN_BIT_8");
		open_bit_9 = rs2.getString("OPEN_BIT_9");
		open_bit_10 = rs2.getString("OPEN_BIT_10");
		open_bit_11 = rs2.getString("OPEN_BIT_11");
		open_bit_12 = rs2.getString("OPEN_BIT_12");
		open_bit_13 = rs2.getString("OPEN_BIT_13");
		notebook = rs2.getString("NOTEBOOK");
		memory = rs2.getString("MEMORY");
		camera = rs2.getString("CAMERA");
		cd = rs2.getString("CD");
		fdd = rs2.getString("FDD");
		etc = rs2.getString("ETC");
		qty = rs2.getString("QTY");
		model_nm = rs2.getString("MODEL_NM");
		itm_nm = rs2.getString("ITM_NM");

		if(open_gbn.equals("A0251011")){
		  open_rsn1 = open_rsn;
		}else{ 
			//if(open_gbn.equals("A0251001")){
		  open_rsn1 = "";
		  open_rsn2 = "";
		}
	
		x.MakePage(ER.AREA_MAKE);		//강제페이지넘김					

		x.VarString("OPEN_BIT_1_"+pageno,open_bit_1);
		x.VarString("OPEN_BIT_2_"+pageno,open_bit_2);
		x.VarString("OPEN_BIT_3_"+pageno,open_bit_3);
		x.VarString("OPEN_BIT_4_"+pageno,open_bit_4);
		x.VarString("OPEN_BIT_5_"+pageno,open_bit_5);
		x.VarString("OPEN_BIT_6_"+pageno,open_bit_6);
		x.VarString("OPEN_BIT_7_"+pageno,open_bit_7);
		x.VarString("OPEN_BIT_8_"+pageno,open_bit_8);
		x.VarString("OPEN_BIT_9_"+pageno,open_bit_9);
		x.VarString("OPEN_BIT_10_"+pageno,open_bit_10);
		x.VarString("OPEN_BIT_11_"+pageno ,open_bit_11);
		x.VarString("OPEN_BIT_12_"+pageno ,open_bit_12);
		x.VarString("OPEN_BIT_13_"+pageno ,open_bit_13);
		x.VarString("OPEN_RSN_"+pageno ,open_rsn1);
		x.VarString("OPEN_RSN2_"+pageno ,open_rsn2);
		x.VarString("OPEN_GBN_"+pageno ,open_gbn);
		x.VarString("OUT_DT_"+pageno ,out_dt);
		x.VarString("DETL_CD_A023_"+pageno ,detl_cd_a023);
		x.VarString("A024_DETL_CD_"+pageno ,a024_detl_cd);
		x.VarString("DETL_CD_A025_"+pageno ,detl_cd_a025);
		x.VarString("NOTEBOOK_"+pageno ,notebook);
		x.VarString("MEMORY_"+pageno ,memory);
		x.VarString("CAMERA_"+pageno ,camera);
		x.VarString("CD_"+pageno ,cd);
		x.VarString("FDD_"+pageno ,fdd);
		x.VarString("ETC_"+pageno ,etc);
		x.VarString("MODEL_NM"+pageno ,model_nm);
		x.VarString("ITM_NM"+pageno ,itm_nm);
		
		x.VarString("QTY"+pageno ,qty);
		
		x.ReplaceVar(ER.AREA_MAKE, "VAR_PageHeader", "[[DOC_ID]]", "DOC_ID");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[COMPANYNAME]]", "COMPANYNAME");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DEPT_NAME]]", "DEPT_NAME");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[VSIT_COMP_NM]]", "VSIT_COMP_NM");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[EMP_NAME]]", "EMP_NAME");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[VISITOR_NAME]]", "VISITOR_NAME");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[EMP_NO]]", "EMP_NO");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[SSN_NO]]", "SSN");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[INNO]]", "INNO");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[CNTC_NO]]", "CNTC_NO");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[A024_DETL_CD]]", "A024_DETL_CD_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[RSRV_STRT_DT]]", "RSRV_STRT_DT");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OUT_DT]]", "OUT_DT_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[D_EMP_NAME]]", "D_EMP_NAME");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[D_EMP_NO]]", "D_EMP_NO");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[D_JW_NAME]]", "D_JW_NAME");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DETL_CD_A023]]", "DETL_CD_A023_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[QTY]]", "QTY");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_1]]", "OPEN_BIT_1_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_2]]", "OPEN_BIT_2_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_3]]", "OPEN_BIT_3_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_4]]", "OPEN_BIT_4_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_5]]", "OPEN_BIT_5_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_6]]", "OPEN_BIT_6_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_7]]", "OPEN_BIT_7_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_8]]", "OPEN_BIT_8_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_9]]", "OPEN_BIT_9_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_10]]", "OPEN_BIT_10_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_11]]", "OPEN_BIT_11_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_12]]", "OPEN_BIT_12_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_BIT_13]]", "OPEN_BIT_13_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DETL_CD_A025]]", "DETL_CD_A025_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_RSN]]", "OPEN_RSN_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_RSN2]]", "OPEN_RSN2_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OPEN_GBN]]", "OPEN_GBN_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[NOTEBOOK]]", "NOTEBOOK_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[MEMORY]]", "MEMORY_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[CAMERA]]", "CAMERA_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[CD]]", "CD_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[FDD]]", "FDD_"+pageno );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[ETC]]", "ETC_"+pageno);
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "{{ER_ReportMakeTime}}", "ER_ReportMakeTime");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[DOC_ID]]", "DOC_ID");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "{{image}}", "ER_EagletReportUrl" );
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "{{image2}}", "ER_EagletReportUrl" );
		
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[MODEL_NM_1]]", "MODEL_NM"+pageno);
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[OUT_DT_1]]", "OUT_DT");
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[A024_DETL_CD_1]]", "A024_DETL_CD_"+pageno);
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[QTY_1]]", "QTY"+pageno);
		x.ReplaceVar(ER.AREA_MAKE, "PageBody", "[[ITM_NM]]", "ITM_NM"+pageno);
		
		
		
	}
	
} catch (Exception e) {
	
	x.SetError(-2000, e.getMessage(), e.getLocalizedMessage());
	e.printStackTrace();
} finally {
	if(rs != null || rs2 != null){try{ rs.close(); rs2.close();}catch(SQLException ex){}}
    if(psmt != null || psmt2 != null){try{ psmt.close(); psmt2.close();}catch(SQLException ex){}}
    if(con != null){try{ con.close();}catch(SQLException ex){}}
    
	x.close();
}
%>