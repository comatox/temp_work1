var gdCtrl = new Object();
var gcGray = "#808080";
var gcToggle = "#FFFF00";
var gcBG = "#EFEFEF";

var gdCurDate = new Date();
var giYear = gdCurDate.getFullYear();
var giMonth = gdCurDate.getMonth() + 1;
var giDay = gdCurDate.getDate();
var VicPopCal = new Object();

var s_kind = "";
var S_DATE = null;
var S_YEAR = null;		//재배분 신청 시작 가능일(년)
var S_MONTH = null;	//재배분 신청 시작 가능일(월)
var S_DAY = null;		//재배분 신청 시작 가능일(일)
var E_DATE = null;
var E_YEAR = null;		//재배분 신청 종료 가능일(년)
var E_MONTH = null;	//재배분 신청 종료 가능일(월)
var E_DAY = null;			//재배분 신청 종료 가능일(일)

var gMonths = new Array ("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월");

function fPopCalendar (dateCtrl, popCal, kind, flag , autoDiv ) {
	gdCurDate = new Date();
	giYear = gdCurDate.getFullYear();
	giMonth = gdCurDate.getMonth() + 1;
	giDay = gdCurDate.getDate();
	VicPopCal = new Object();

	s_kind = "";
	S_DATE = null;
	S_YEAR = null;
	S_MONTH = null;
	S_DAY = null;
	E_DATE = null;
	E_YEAR = null;
	E_MONTH = null;
	E_DAY = null;
	VicPopCal = popCal;
	gdCtrl = dateCtrl;

	$('#pop_wrap').parent().hide();
	$('#pop_wrap').remove();
	//$('.modal').show();

	fWriteCalendar(popCal);
	var tbSelMonth = $("#tbSelMonth");
	var tbSelYear = $("#tbSelYear");
	//자동재배분 신청일 경우 신청 가능 날짜를 셋팅한다.
	if( typeof( autoDiv ) != "undefined" && autoDiv == "OK" ) {
		var FMDT = document.getElementById( "FMDT" ).value;
		var TODT = document.getElementById( "TODT" ).value;
		if( ( FMDT != "" && FMDT.length == 8 ) && ( TODT != "" && TODT.length == 8 ) ) {
			S_DATE = FMDT;
			S_YEAR = Number( FMDT.substring( 0 , 4 ) );
			S_MONTH = Number( FMDT.substring( 4 , 6 ) );
			S_DAY = Number( FMDT.substring( 6 , 8 ) );
			E_DATE = TODT;
			E_YEAR = Number( TODT.substring( 0 , 4 ) );
			E_MONTH = Number( TODT.substring( 4 , 6 ) );
			E_DAY = Number( TODT.substring( 6 , 8 ) );
		}
	}
    s_kind = kind;

	VicPopCal.style.display = "block";
	if( S_YEAR != null ) {

		for( var i = tbSelYear.length ; i >= 0 ; i--) {
       		tbSelYear.options[i] = null;
   		}
   		var no = 0;
   		for( var i = S_YEAR ; i <= E_YEAR ; i++ ) {
   			tbSelYear.options[ no++ ] = new Option( i , i );
   		}
		for( var i = tbSelMonth.length ; i >= 0 ; i--) {
       		tbSelMonth.options[i] = null;
   		}
   		no = 0;
   		for( var i = S_MONTH ; i <= 12 ; i++ ) {
   			tbSelMonth.options[ no++ ] = new Option( i + "월" , i );
   		}
		document.getElementById( "PrevMonth" ).disabled = false;
		document.getElementById( "NextMonth" ).disabled = false;

		fSetYearMon (S_YEAR, S_MONTH);
	}else fSetYearMon (giYear, giMonth);
}

function fSetDate (iYear, iMonth, iDay)
{
	if (parseInt (iMonth) < 10) iMonth = "0" + iMonth;
	if (parseInt (iDay) < 10) iDay = "0" + iDay;
	if( S_DATE != null ) {
		var temp = String( iYear ) + String( iMonth ) + String( iDay );
		if( S_DATE <= temp && E_DATE >= temp ) {
		    if(s_kind == "1") gdCtrl.value = iYear + "년" + iMonth + "월" + iDay + "일";
		    else if (s_kind == "2") gdCtrl.value = iYear + "-" + iMonth + "-" + iDay;
		    else if (s_kind == "3") gdCtrl.value = iYear + "." + iMonth + "." + iDay;
		    else if (s_kind == "4") { gdCtrl.value = iYear + "" + iMonth + "" + iDay;
		    gdCtrl.focus();
		    }
		    else if (s_kind == "5") gdCtrl.value = iYear + "-" + iMonth + "-" + iDay;

			VicPopCal.style.display = "none";
			VicPopCal.innerHTML ="";
			gdCtrl.focus();
		}
	}else {
	    if(s_kind == "1") gdCtrl.value = iYear + "년" + iMonth + "월" + iDay + "일";
	    else if (s_kind == "2") gdCtrl.value = iYear + "-" + iMonth + "-" + iDay;
	    else if (s_kind == "3") gdCtrl.value = iYear + "." + iMonth + "." + iDay;
	    else if (s_kind == "4") { gdCtrl.value = iYear + "" + iMonth + "" + iDay;
		  	gdCtrl.focus();
		    }
	    else if (s_kind == "5") gdCtrl.value = iYear + "-" + iMonth + "-" + iDay;

		VicPopCal.style.display = "none";
		VicPopCal.innerHTML ="";
		gdCtrl.focus();
	}
	$(gdCtrl).change();
}

function fSetSelected (aCell)
{
	var iOffset = 0;
	var iMonth = parseInt($("#tbSelMonth").val());
	var iYear = parseInt($("#tbSelYear").val());

	var iDay = parseInt (aCell);

	fSetDate (iYear, iMonth, iDay);
    $('.modal').hide();
}

function fBuildCal (iYear, iMonth)
{
	var aMonth = new Array();
	for (var i = 1;i < 7; i++)
  	aMonth[i] = new Array (i);

	var dCalDate = new Date (iYear, iMonth - 1, 1);
	var iDayOfFirst = dCalDate.getDay();
	var iDaysInMonth = new Date (iYear, iMonth, 0).getDate();
	var iOffsetLast = new Date (iYear, iMonth - 1, 0).getDate() - iDayOfFirst + 1;
	var iDate = 1;
	var iNext = 1;

	for (var d = 0; d < 7; d++)
	aMonth[1][d] = (d < iDayOfFirst) ? -(iOffsetLast + d) : iDate++;
	for (var w = 2; w < 7; w++)
  	for (var d = 0; d < 7; d++)
		aMonth[w][d] = (iDate <= iDaysInMonth) ? iDate++ : -(iNext++);
	return aMonth;
}


function fUpdateCal (iYear, iMonth , flag )
{
	var tbSelMonth = $("#tbSelMonth");
	var tbSelYear = $("#tbSelYear");
	if( S_DATE != null ) {
		if( S_YEAR > iYear && E_YEAR < iYear ) {
			for( var i = tbSelMonth.length ; i >= 0 ; i--) {
	       		tbSelMonth.options[i] = null;
	   		}
	   		var no = 0;
	   		for( var i = 1 ; i <= 12 ; i++ ) {
	   			tbSelMonth.options[ no++ ] = new Option( i , i );
	   		}
	   		tbSelMonth.value = iMonth;
		}else {
			if( S_YEAR == iYear ) {
				for( var i = tbSelMonth.length ; i >= 0 ; i--) {
		       		tbSelMonth.options[i] = null;
		   		}
				var no = 0;
		   		for( var i = S_MONTH ; i <= 12 ; i++ ) {
		   			tbSelMonth.options[ no++ ] = new Option( i + "월" , i );
		   		}
				if( S_MONTH > iMonth ) {
					iMonth = S_MONTH ;
				}
				if( flag == 0 ) tbSelMonth.value = S_MONTH;
				else tbSelMonth.value = iMonth;
			}else if( E_YEAR == iYear ) {
				for( var i = tbSelMonth.length ; i >= 0 ; i--) {
		       		tbSelMonth.options[i] = null;
		   		}
		   		var no = 0;
		   		for( var i = 1 ; i <= E_MONTH ; i++ ) {
		   			tbSelMonth.options[ no++ ] = new Option( i + "월" , i );
		   		}
		   		if( E_MONTH < iMonth ) {
		   			iMonth = E_MONTH;
		   		}
				if( flag == 0 ) {
					tbSelMonth.value = 1;
					 iMonth = 1;
				}else tbSelMonth.value = iMonth;
			}
		}

	}

	myMonth = fBuildCal (iYear, iMonth);
	var i = 0;
	var cellText = $(".cellText");
	for (var w = 0; w < 6; w++)
	for (var d = 0; d < 7; d++)
		with (cellText[(7 * w) + d])
		{
			Victor = i++;
			if (myMonth[w+1][d] < 0 ) {
				color = gcGray;
				innerText = -myMonth[w + 1][d];
			} else {
				if( S_YEAR != null ) {
					if( S_YEAR == iYear && iMonth == S_MONTH ) {
						color = ( S_DAY <= myMonth[w + 1][d] ? ( d == 0 ? "red" : ( d == 6 ? "blue" : "black" ) ) : "#8A8A8A" );
					}else if( E_YEAR == iYear && iMonth == E_MONTH ) {
						color = ( E_DAY >= myMonth[w + 1][d] ? ( d == 0 ? "red" : ( d == 6 ? "blue" : "black" ) ) : "#8A8A8A" );
					}else {
						color = ( d == 0 ? "red" : ( d == 6 ? "blue" : "black" ) );
					}
					var linkText = "";
					linkText = (color == "#8A8A8A" ? myMonth[w + 1][d] : "<a style=\"color : "+color+"\" href=\"javascript:;\" onclick = \"fSetSelected (\'"+myMonth[w + 1][d]+"\')\">" + myMonth[w + 1][d] +"</a>");
					innerHTML = linkText;
				} else {
					color = ( d == 0 ? "red" : ( d == 6 ? "blue" : "black" ) );
					var linkText = "<a style=\"color : "+color+"\" href=\"javascript:;\" onclick = \"fSetSelected (\'"+myMonth[w + 1][d]+"\')\">" + myMonth[w + 1][d] +"</a>";
					innerHTML =  linkText;
				}
			}
		}
	return false;
}

function fSetYearMon (iYear, iMon)
{
	$("#tbSelMonth").val(iMon);
	$("#tbSelYear").val(iYear);

	fUpdateCal (iYear, iMon , 3 );
}

function fPrevMonth()
{
	var iMon = $("#tbSelMonth").val();
	var iYear = $("#tbSelYear").val();

	if (--iMon < 1)
	{
		iMon = 12;
		iYear--;
	}

	fSetYearMon (iYear, iMon);

}

function fNextMonth()
{
    var iMon = $("#tbSelMonth").val();
    var iYear = $("#tbSelYear").val();

	if (++iMon > 12)
	{
		iMon = 1;
		iYear++;
	}

	fSetYearMon (iYear, iMon);

}

function fWriteCalendar(popCal){
	var CalendarHtml = "<div id='pop_wrap'><div class='calendar_wrap' style='background: #ffffff;'>";
		CalendarHtml += "<table id = 'poptable' border = '0'>";
		CalendarHtml += "<tr>";
		CalendarHtml += "<td class='top'><div class='btn_wrap'><button name = 'PrevMonth'  id = 'PrevMonth' onclick='fPrevMonth(); return false;' title='이전 달 보기' class='month'><img src='/esecurity/assets/common/images/common_user/calendar/btn_prev.gif' alt='이전 달'></button> ";
		CalendarHtml += "<SELECT title='연도' name = 'tbSelYear'  id = 'tbSelYear' onchange =  \"fUpdateCal (document.getElementById('tbSelYear').value, document.getElementById('tbSelMonth').value , 0 );\" >";
		for (var i = 1920; i < 2045; i++){
			CalendarHtml += "<option value = '" + i + "'>" + i + "</option>";
		}
		CalendarHtml += "</select>";
		CalendarHtml += " <select title='월' name = 'tbSelMonth'  id = 'tbSelMonth' onchange = \"fUpdateCal (document.getElementById('tbSelYear').value, document.getElementById('tbSelMonth').value , 1 );\">";
		for (var i = 0; i < 12; i++){
			CalendarHtml += "<option value = '" + (i + 1) + "'>" + gMonths[i] + "</OPTION>";
		}
		CalendarHtml += "</select>";
		CalendarHtml += " <button name = 'NextMonth'  id = 'NextMonth' onclick='fNextMonth();return false;' title='다음 달 보기' class='month'><img src='/esecurity/assets/common/images/common_user/calendar/btn_next.gif' alt='다음 달'></button> ";
		CalendarHtml += "</div></td>";
		CalendarHtml += "</tr><tr>";
		CalendarHtml += "<td align = 'center' class='calendar'>";
		CalendarHtml += "<table width = '100%' border = '0' cellpadding = '0' bordercolor = '#000000' summary='달력 선택 표입니다.' class='list'>";
		CalendarHtml += "<caption>달력</caption>";

		var WeekDay = new Array ("일","월","화","수","목","금","토");

		CalendarHtml += "<tr>";
		for (var i = 0; i < 7; i++){
			CalendarHtml +="<th scope='col' style='padding : 0px'>" + WeekDay[i] + "</th>";
		}
		CalendarHtml +="</tr>";

		for (var w = 1; w < 7; w++)
		{
		CalendarHtml += "<tr>";
		for (var d = 0; d < 7; d++)
		{
			CalendarHtml += "<td style='padding : 0px'>";
			CalendarHtml += "<span class ='cellText'> </span>";
			CalendarHtml += "</td>";
		}
		CalendarHtml +="</tr>";
		}
		CalendarHtml += "</table>";   
		CalendarHtml += "</td>";
		CalendarHtml += "</tr></table>";
		CalendarHtml += "<div class='btn_close'><a href='javascript:closeCalendar();'><img src='/esecurity/assets/common/images/common_user/calendar/btn_close.gif' alt='달력 닫기' /></a></div>";
		CalendarHtml += "</div><div>";
		VicPopCal.innerHTML = CalendarHtml;
}
function closeCalendar(){
	VicPopCal.style.display = "none";
	VicPopCal.innerHTML ="";
    $('.modal').hide();
	gdCtrl.focus();
}
