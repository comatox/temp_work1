<%@ page contentType="text/html;charset=UTF-8" session="false"%>
<%@page import="java.sql.SQLException"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="javax.naming.NamingException"%>

<%
request.setCharacterEncoding("UTF-8");
//out.flush();
Map login = new HashMap();
if(request.getSession().getAttribute("Login") != null) {
	login = (Map)request.getSession().getAttribute("Login");
} 
//String emp_id = login != null ? login.get("EMP_ID").toString() : "";
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
	String inout_appl_no = "";
	String item_seq = "";
	String inout_pc_id = "";
	String doc_no = "";

	
	Context ctx = null;     //Interface
	DataSource ds = null;   //Interface
	Connection conn = null;  //Interface
	PreparedStatement psmt = null;
	ResultSet rs = null;    //Interface	
	
	System.out.println("query 1111111111111111");
	String query = StringUtils.defaultIfEmpty(request.getParameter("query"),"");
	System.out.println("query "+query);
	String[] parmeter = query.split("@");
	System.out.println("String[] "+parmeter.length);
	inout_appl_no = StringUtils.defaultIfEmpty(parmeter[0],"");	
	item_seq = StringUtils.defaultIfEmpty(parmeter[1],"");	
	inout_pc_id = StringUtils.defaultIfEmpty(parmeter[2],"");	
	//doc_no = StringUtils.defaultIfEmpty(parmeter[1],"");	

	//System.out.println("parmeter[0] : "+parmeter[0]+" :"+parmeter[1]+" :" );
	
	System.out.println("parmeter[0] : "+parmeter[0]);
	// File Setting( ***** 화일이름을 반드시 설정할 것) *************************/
	
	
	String maskname = "";	
	String savePath = request.getRealPath("/eSecurity/temp/");
	maskname = "inout_pc_template_" + parmeter[0] + ".htm";
	File dataFile = new File(savePath +"\\" +maskname);
	String filename = maskname;
	PrintWriter outputStream = new PrintWriter(new FileOutputStream(dataFile));
	DeleteFileAndDirUtil.deleteFiles( request.getRealPath("/eSecurity/temp") , System.currentTimeMillis()-(3600000*24));//3600초(1시간)*24 하루동안 쌓인 임시파일은 삭제한다.
	//System.out.print(savePath +"\\" +maskname);
	ReportGen reportGen = new ReportGen();	
	
	
	reportGen.setPathandfilename(request.getRealPath("/eSecurity/common/report/IoInout/inout_pc_template.htm"));
	
	System.out.println("inout_pc_template.jsp 2");

	System.out.println("ssss : "+reportGen.getPathandfilename());
	System.out.println("reportGen.SeperateTemplateDoc() : "+reportGen.SeperateTemplateDoc());
	
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

		//Top_HTML += "<div align='right'> ";
		//Top_HTML += "    <table cellpadding='0' cellspacing='0' width='100%'>";
		//Top_HTML += "        <tr height='20'>";
		//Top_HTML += "            <td></td>";
		//Top_HTML += "        </tr>";
		//Top_HTML += "        <tr>";
		//Top_HTML += "            <td width='100%' align='left'>";
		//Top_HTML += "                <p><img src='/eSecurity/common/eaglet/report/images/logo.jpg' width='140' height='40' border='0'></p>";
		Top_HTML += "                <p><img src='/eSecurity/common/eaglet/report/images/logo.jpg' ></p>";
		//Top_HTML += "            </td>";
		//Top_HTML += "        </tr>";
		//Top_HTML += "    </table>";
		//Top_HTML += "</div>";
	
		%>
		
		
		<%
		try {
		
			ctx = (Context)  new InitialContext();
			ds = (DataSource) ctx.lookup("jdbc/eSecurityDS");
			conn = ds.getConnection();
			conn.setAutoCommit(true); 

			StringBuilder strSQL = new StringBuilder();

			strSQL.append("     SELECT PC.INOUT_APPL_NO AS INOUT_APPL_NO                                                                                                                \n");
			strSQL.append("          , PC.ITEM_SEQ AS ITEM_SEQ                                                                                                                          \n");
			strSQL.append("          , IW.IO_COMP_ID AS IO_COMP_ID                                                                                                                      \n");
			strSQL.append("          , IW.IO_COMP_NM AS IO_COMP_NM                                                                                                                      \n");
			strSQL.append("          , IW.IO_EMP_ID AS IO_EMP_ID                                                                                                                        \n");
			strSQL.append("          , IW.IO_EMP_NM AS IO_EMP_NM                                                                                                                        \n");
			strSQL.append("          , IW.IO_TEL_NO AS IO_TEL_NO                                                                                                                        \n");
			strSQL.append("          , FN_GET_CODE('A030', IW.COMPANYNO, 'ETC1' ) COMPANY_NM                                                                                            \n");
			strSQL.append("          , IW.COMPANYNO AS COMPANYNO                                                                                                                        \n");
			strSQL.append("          , IW.COMP_ID AS CO_COMP_ID                                                                                                                         \n");
			strSQL.append("          , FN_GET_COMP_NM(IW.COMP_ID) AS CO_COMP_NM                                                                                                         \n");
			strSQL.append("          , IW.GATE_ID AS GATE_ID                                                                                                                            \n");
			strSQL.append("          , IW.GATE_NM AS GATE_NM                                                                                                                            \n");
			strSQL.append("          , FN_GET_CODE('A024', IW.IO_CAUSE, 'ETC1') AS IO_CAUSE                                                                                             \n");
			strSQL.append("          , DECODE(IW.INOUT_GBN, '1', '반입/반출', '2','반입/미반출') AS INOUT_GBN                                                                                  \n");
			strSQL.append("          , IW.DEPT_NM AS CO_DEPT_NM                                                                                                                         \n");
			strSQL.append("          , IW.EMP_ID AS CO_EMP_ID                                                                                                                           \n");
			strSQL.append("          , IW.EMP_NM AS CO_EMP_NM                                                                                                                           \n");
			strSQL.append("          , (SELECT CO.TEL_NO1 FROM CO_EMP CO WHERE CO.EMP_ID = IW.EMP_ID) CO_TEL_NO1                                                                        \n");
			strSQL.append("          , FN_GET_JW_NM((SELECT CO.JW_ID FROM CO_EMP CO WHERE CO.EMP_ID = IW.EMP_ID)) CO_JW_NM                                                              \n");
			strSQL.append("          , IW.TEL_NO CO_TEL_NO2                                                                                                                             \n");
			strSQL.append("          , IW.IO_CAUSE AS IO_CAUSE_CD                                                                                                                       \n");
			strSQL.append("          , IW.IO_CAUSE_DETAIL                                                                                                                               \n");
			strSQL.append("          , IW.INOUT_GBN AS INOUT_GBN_CD                                                                                                                     \n");
			strSQL.append("          , IW.APPL_STAT APPL_STAT                                                                                                                           \n");
			strSQL.append("          , IW.DOC_ID DOC_ID                                                                                                                                 \n");
			strSQL.append("          , IW.CANCEL_RSN                                                                                                                                    \n");
			strSQL.append("          , IW.IOINOUTSERIALNO                                                                                                                               \n");
			strSQL.append("          , TO_CHAR(TO_DATE(IW.IN_EXPT_DATE), 'YYYY-MM-DD') IN_EXPT_DATE                                                                                     \n");
			strSQL.append("          , TO_CHAR(TO_DATE(PC.OUT_EXPT_DATE), 'YYYY-MM-DD') OUT_EXPT_DATE                                                                                   \n");
			strSQL.append("          , PC.INOUT_PC_ID                                                                                                                                   \n");
			strSQL.append("          , PC.IN_COMP_ID                                                                                                                                    \n");
			strSQL.append("          , FN_GET_CODE('A030', PC.IN_COMP_ID, 'ETC1') IN_COMP_NM                                                                                            \n");
			strSQL.append("          , PC.IN_EXPT_GATE_ID                                                                                                                               \n");
			strSQL.append("          , PC.IN_EXPT_GATE_NM                                                                                                                               \n");
			strSQL.append("          , PC.LAST_IN_GATE_ID                                                                                                                               \n");
			strSQL.append("          , PC.LAST_IN_GATE_NM                                                                                                                               \n");
			strSQL.append("          , PC.USER_NM                                                                                                                                       \n");
			strSQL.append("          , PC.USER_ID                                                                                                                                       \n");
			strSQL.append("          , PC.USER_HP_NO                                                                                                                                    \n");
			strSQL.append("          , DECODE(PC.PC_TYPE, 1, '데스크탑', 2, '노트북') PC_TYPE                                                                                                 \n");
			strSQL.append("          , PC.MAKER                                                                                                                                         \n");
			strSQL.append("          , PC.SERIAL_NO                                                                                                                                     \n");
			strSQL.append("          , FN_GET_CODE('D007', PC.PC_OS, 'ETC1') PC_OS                                                                                                      \n");
			strSQL.append("          , PC.MAC_ADDR                                                                                                                                      \n");
			strSQL.append("          , DECODE(PC.SCRT_SLTN_INST_YN, 'Y', '가능', 'N', '불가능') SCRT_SLTN_INST_YN                                                                           \n");
			strSQL.append("          , PC.DPT_INSTALL_YN                                                                                                                                \n");
			strSQL.append("          , DECODE(PC.DPT_INSTALL_YN, 'Y', '설치', 'N', '미설치') DPT_INSTALL_YN_NM                                                                              \n");
			strSQL.append("          , PC.EP_IN_CHK_NEED_YN                                                                                                                             \n");
			strSQL.append("          , DECODE(PC.EP_IN_CHK_NEED_YN, 'Y', '필요', 'N', '불필요') EP_IN_CHK_NEED_YN_NM                                                                        \n");
			strSQL.append("          , PC.EP_IN_CHK_DONE_YN                                                                                                                             \n");
			strSQL.append("          , DECODE(PC.EP_IN_CHK_DONE_YN, 'Y', '완료', 'N', '미완료') EP_IN_CHK_DONE_YN_NM                                                                        \n");
			strSQL.append("          , PC.EP_OUT_CHK_DONE_YN                                                                                                                            \n");
			strSQL.append("          , DECODE(PC.EP_OUT_CHK_DONE_YN, 'Y', '완료', 'N', '미완료') EP_OUT_CHK_DONE_YN_NM                                                                      \n");
			strSQL.append("          , PC.IN_DENY_YN                                                                                                                                    \n");
			strSQL.append("          , PC.IN_DENY_RSN                                                                                                                                   \n");
			strSQL.append("          , PC.SELF_FRMT_DONE_YN                                                                                                                             \n");
			strSQL.append("          , NVL((SELECT MAX(IH.WRITECNT) FROM IO_INARTICLEHISTORY IH WHERE IH.INOUT_APPL_NO = ? AND IH.ITEM_SEQ = ?), 0) MAX_WRITE_CNT                       \n");
			strSQL.append("          , TO_CHAR(IW.CRT_DTM, 'YYYY-MM-DD') CRT_DTM                                                                                                        \n");
			strSQL.append("          , TO_CHAR(PC.MOD_DTM, 'YYYY-MM-DD') MOD_DTM                                                                                                        \n");
			strSQL.append("     FROM IO_INOUTWRITE IW                                                                                                                                   \n");
			strSQL.append("          INNER JOIN IO_INOUTPCLIST PC                                                                                                                       \n");
			strSQL.append("                      ON IW.INOUT_APPL_NO = PC.INOUT_APPL_NO                                                                                                 \n");
			strSQL.append("                     AND IW.INOUT_APPL_NO = ?                                                                                                                \n");
			strSQL.append("                     AND PC.ITEM_SEQ = ?                                                                                                                     \n");
		
			int ps = 1;			
			psmt = conn.prepareStatement(strSQL.toString());
			psmt.setString(ps++, inout_appl_no); 			
			psmt.setString(ps++, item_seq); 			
			psmt.setString(ps++, inout_appl_no); 			
			psmt.setString(ps++, item_seq); 			
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
			
			
			Date today = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			PageHeader = InoutFn.rplc(PageHeader,"{{inout_pc_id}}", InoutFn.csc(rs.getString("INOUT_PC_ID")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_emp_nm}}", InoutFn.csc(rs.getString("CO_EMP_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_comp_nm}}", InoutFn.csc(rs.getString("CO_COMP_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_dept_nm}}", InoutFn.csc(rs.getString("CO_DEPT_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_emp_id}}", InoutFn.csc(rs.getString("CO_EMP_ID")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_tel_no1}}", InoutFn.csc(rs.getString("CO_TEL_NO1")));
			PageHeader = InoutFn.rplc(PageHeader,"{{co_tel_no2}}", InoutFn.csc(rs.getString("CO_TEL_NO2")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_comp_id}}", InoutFn.csc(rs.getString("IO_COMP_ID")));			
			PageHeader = InoutFn.rplc(PageHeader,"{{io_comp_nm}}", InoutFn.csc(rs.getString("IO_COMP_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_tel_no}}", InoutFn.csc(rs.getString("IO_TEL_NO")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_emp_id}}", InoutFn.csc(rs.getString("IO_EMP_ID")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_emp_nm}}", InoutFn.csc(rs.getString("IO_EMP_NM")));
            PageHeader = InoutFn.rplc(PageHeader,"{{io_user_tel_no}}", InoutFn.csc(rs.getString("USER_HP_NO")));
            PageHeader = InoutFn.rplc(PageHeader,"{{io_user_emp_id}}", InoutFn.csc(rs.getString("USER_ID")));
            PageHeader = InoutFn.rplc(PageHeader,"{{io_user_emp_nm}}", InoutFn.csc(rs.getString("USER_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{appl_stat}}", InoutFn.csc(rs.getString("APPL_STAT")));
			PageHeader = InoutFn.rplc(PageHeader,"{{company_nm}}", InoutFn.csc(rs.getString("COMPANY_NM")));			
			PageHeader = InoutFn.rplc(PageHeader,"{{gate_nm}}", InoutFn.csc(rs.getString("GATE_NM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{doc_id}}", InoutFn.csc(rs.getString("DOC_ID")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_cause}}", InoutFn.csc(rs.getString("IO_CAUSE")));
			PageHeader = InoutFn.rplc(PageHeader,"{{inout_gbn}}", InoutFn.csc(rs.getString("INOUT_GBN")));			
			PageHeader = InoutFn.rplc(PageHeader,"{{in_expt_date}}", InoutFn.csc(rs.getString("IN_EXPT_DATE")));
			PageHeader = InoutFn.rplc(PageHeader,"{{out_expt_date}}", InoutFn.csc(rs.getString("OUT_EXPT_DATE")));
			PageHeader = InoutFn.rplc(PageHeader,"{{mod_dtm}}", InoutFn.csc(rs.getString("MOD_DTM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{cancel_rsn}}", InoutFn.csc(rs.getString("CANCEL_RSN")));			
			PageHeader = InoutFn.rplc(PageHeader,"{{ioinoutserialno}}", InoutFn.csc(rs.getString("IOINOUTSERIALNO")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_cause_detail}}", InoutFn.csc(rs.getString("IO_CAUSE_DETAIL")));
			PageHeader = InoutFn.rplc(PageHeader,"{{io_cause_cd}}", InoutFn.csc(rs.getString("IO_CAUSE_CD")));			
			PageHeader = InoutFn.rplc(PageHeader,"{{inout_gbn_cd}}", InoutFn.csc(rs.getString("INOUT_GBN_CD")));
			PageHeader = InoutFn.rplc(PageHeader,"{{pc_type}}", InoutFn.csc(rs.getString("PC_TYPE")));
			PageHeader = InoutFn.rplc(PageHeader,"{{marker}}", InoutFn.csc(rs.getString("MAKER")));
			PageHeader = InoutFn.rplc(PageHeader,"{{serial_no}}", InoutFn.csc(rs.getString("SERIAL_NO")));
			PageHeader = InoutFn.rplc(PageHeader,"{{pc_os}}", InoutFn.csc(rs.getString("PC_OS")));
			PageHeader = InoutFn.rplc(PageHeader,"{{mac_addr}}", InoutFn.csc(rs.getString("MAC_ADDR")));
			PageHeader = InoutFn.rplc(PageHeader,"{{scrt_sltn_inst_yn}}", InoutFn.csc(rs.getString("SCRT_SLTN_INST_YN")));
			PageHeader = InoutFn.rplc(PageHeader,"{{crt_dtm}}", InoutFn.csc(rs.getString("CRT_DTM")));
			PageHeader = InoutFn.rplc(PageHeader,"{{today}}", date.format(today));
			PageHeader = InoutFn.rplc(PageHeader,"{{pfc1}}",Top_HTML);
			
			
			String inoutDate ="";
			if(rs.getString("INOUT_GBN").equals("1")){
				inoutDate =rs.getString("IN_EXPT_DATE");
			}else{
				inoutDate =rs.getString("OUT_EXPT_DATE");
			}
			PageHeader = InoutFn.rplc(PageHeader,"{{inoutDate}}",inoutDate);
		
		
			//한 장의 사이즈에 맞게 출력을 장단위로 구분함.
			int NowPage=1;
			int TotalPage=1;
			String TempTableKnd="";
			String PageAll="";
			String TableBody=TableHearder1;
			String TableBody2=TableHearder2;
			String TableBody3=TableHearder3;
			String Temp=ReportHeader;

			Temp += sPageViewStart + PageHeader;
			Temp = InoutFn.rplc(Temp, "{{page}}", "1");
			outputStream.print(Temp);
			outputStream.print(TableDetail1);
			outputStream.print(TableFooter1);
			
			outputStream.print(TableBody2);
			outputStream.print(TableFooter2);
	
			outputStream.print(TableBody3);
			outputStream.print(TableFooter3);
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
	
	//System.out.println("documentselect_make_list.jsp 3");

%>
<body id="printpage" bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" style="background-image:  url('/eSecurity/common/report/images/a4_bg_sero.gif');	background-repeat: no-repeat;	background-position: center top;	background-color: #999999;">
</body>