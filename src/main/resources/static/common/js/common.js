/** 
 * 그리드 전역변수
 */
var grid = {
		_space : 0,	//그리드 여백
		_width : 850,
		_height : 'auto', //no edit
		_height2 : 'auto', //edit
		_rownum : 10,
		_sortable : false,
		_emptyrecords : "조회결과가 없습니다."
};

/**
 * 폼 파라메터 변수
 */
var fomParams  = function(form) {
	var forms = form.serializeArray();
	var params = {};
	$.each(forms, function(){
	    var jname;
	    $.each(this, function(i, val){
	        if (i=="name") {
	                jname = val;
	        } else if (i=="value") {
	                params[jname] = val;
	        }
	    });
	});
	
	return params;
};


/**
 * 현재 일
 */
var today = new Date();


/**
 * 달력 파라메터 변수
 */
var datePikerData = function() {
	
	var start_year = today.getFullYear() - 30;
	var end_year = today.getFullYear() + 10;
	//var end_year = "9999";
	
	return {
		//inline: true, 
		changeMonth: true, 
		changeYear: true, 
		yearRange: start_year+":"+end_year, 
		dateFormat: "yy-mm-dd", 	
		autoSize: false,
		locale : "ko", 		
 		monthNames: ['1월','2월','3월','4월','5월','6월',
 		    		'7월','8월','9월','10월','11월','12월'],
 		monthNamesShort: ['1월','2월','3월','4월','5월','6월',
 		    		'7월','8월','9월','10월','11월','12월'],
 		dayNames: ['일','월','화','수','목','금','토'],
 		dayNamesShort: ['일','월','화','수','목','금','토'],
 		dayNamesMin: ['일','월','화','수','목','금','토'],		    		
 		yearSuffix: '',
 		showMonthAfterYear: true,
 		showButtonPanel: true,
 		closeText: '닫기',
 		currentText : ''
	};
}();


/** 그리드 리사이징
 * @param $grid
 * @param auto : true, 셀리사이징, default : false 
 * @ex gridResize($("#jqgrid")); 
 */
function gridResize($grid, auto) {
	if (null == auto || auto == undefined || auto == false) {
		auto = false;
	} else {
		auto = true;
	}
	$grid.setGridWidth($(window).width() - grid._space, auto);
}

/** select box formatter 
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns {String}
 */
function jqgridSelectFormat(cellvalue, options, rowObject)
{
    var temp = '';
    $.each(options.colModel.editoptions.value, function (key, value)
    {
        if (cellvalue == key)
        {
            temp = value;
            return false;
        }
    });

    return temp;
}

/** select box unformatter 
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns {String}
 */
function jqgridSelectUnFormat(cellvalue, options, rowObject)
{
    var temp = '';
    $.each(options.colModel.editoptions.value, function (key, value)
    {
        if (cellvalue == value)
        {
            temp = key;
            return false;
        }
    });

    return temp;
}

/** jqgridUnformatPercent
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function jqgridUnformatPercent(cellvalue, options, rowObject)
{
    var val = cellvalue.toString();
    return val.replaceAll("%", "");
}

var getIColByNm = function(grid,columnName) {
    var cm = grid.jqGrid('getGridParam','colModel');
    for (var i=0,l=cm.length; i<l; i++) {
        if (cm[i].name===columnName) {
            return i; // return the index
        }
    }
    return -1;
}

/** htmlEscapeFormatter
 * @param cellvalue
 * @param options
 * @param rowObjcet
 * @returns
 */
function htmlEscapeFormatter(cellvalue, options, rowObjcet) {
	if(cellvalue == null || cellvalue == "") {
		return "";
	} else {
		return $("<div>").text(cellvalue).html();
	}
}

/** htmlEscapeUnformatter
 * @param cellvalue
 * @param options
 * @param rowObjcet
 * @returns
 */
function htmlEscapeUnformatter(cellvalue, options, rowObjcet) {
	return cellvalue;
}

/** 개행된 문자열을 한 줄로 표시 + 태그 제거
 * @param cellvalue
 * @param options
 * @param rowObjcet
 * @returns
 */
function removeCrFormatter(cellvalue, options, rowObjcet) {
	if(cellvalue == null || cellvalue == "") {
		return "";
	} else {
		return $("<div>").text(cellvalue).html().replace(/(\r\n|[\r\n])/g, " ");
	}
}

/** removeCrUnformatter
 * @param cellvalue
 * @param options
 * @param rowObjcet
 * @returns
 */
function removeCrUnformatter(cellvalue, options, rowObjcet) {
	return cellvalue;
}

//문자열 관련
/**
 * 빈값 체크
 */
function isNull(val) {
	if(val == null || val == undefined || val == "") {
		return true;
	} else {
		return false;
	}
}
/**
 * replaceAll
 */
/**
 * @author Muzeaing
 *
 */
String.prototype.replaceAll = function(oldStr, newStr) {
	if(!isNull(this)) {
		return this.split(oldStr).join(newStr);
	} else {
		return this;
	}
};

/** 공백제거
 * @param cellvalue
 * @param str : value
 * @returns
 */
function trim(str) {
       return str.replace(/^\s+|\s+$/g, "");   
}	

/** 배열의 요소제거
 * @param idx
 * @returns
 */
Array.prototype.remove = function(idx) {return (idx<0 || idx>this.length) ? this : this.slice(0, idx).concat(this.slice(idx+1, this.length)); }; 


/** 숫자 입력 체크
 * @param cellvalue
 * @param paramId : input box ID
 * @returns
 */
function isNumCheck(paramId)
{
	var objPattern = /^[0-9]+$/;
	var str = trim($("#"+paramId).val());
	if(str == "" || str == null) {
		return true;
	} else {
		if (!objPattern.test(str)) {
			$("#"+paramId).select();			
			alert("숫자로만 입력 가능합니다.");
			$("#"+paramId).val('');
			return false;		
		} else {
			return true;
		}
	}
}

/** 숫자 콤마 추가
 * @param cellvalue
 * @returns
 */
function isSetcomma(val) {
	var reg = /(^[+-]?\d+)(\d{3})/;
	val += '';
	while(reg.test(val)) val = val.replace(reg, '$1' + ',' + '$2'); 
	return val;
}

/** 숫자 콤마 제거
 * @param cellvalue
 * @returns
 */
function isUnSetcomma(val) {	
	return Number(val.replace(/,/g,""));
}

/** 영문, 숫자 입력 체크
 * @param cellvalue
 * @param paramId : input box ID
 * @returns
 */
function isEngNumCheck(paramId)
{
	var objPattern = /^[0-9a-zA-Z]+$/;
	var str = trim($("#"+paramId).val());
	if(str == "" || str == null) {
		return true;
	} else {
		if (!objPattern.test(str)) {
			$("#"+paramId).select();			
			alert("영문 또는 숫자로만 입력 가능합니다.");			
			$("#"+paramId).val('');
			
			return false;		
		} else {
			return true;
		}
	}
}

/** 상세 코드 옵션 
 * @param cellvalue
 * @param grpCd : 분류코드
 * @param codeType : 상세코드 구분 DETL_CD, ETC1, ETC2, ETC3, ETC4
 * @param parentId : check box parent id
 * @param checkBoxName : check box name
 * @returns
 */
function fn_detailCodeCheckBoxList(grpCd, columnName, parentId, checkBoxName, string, code) {
	if(trim(columnName) == "" || columnName == null) {
		columnName = "DETL_CD";
	}
	
	columnName = columnName.toUpperCase();
	
	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		async: false,
		data: {
			"nc_trId":"fmDetailCodeList", 
			"GRP_CD":grpCd,
			"COLUMN_NAME":columnName,
			"callback":""
		},
		success: function(data) { 
			var jsonData = JSON.parse(data);
			
			if(jsonData.DETAIL_CODE_LIST_recordSet != null) {
				
				$("#"+parentId).html("");
				
				var parentHtml = "";
				
				for(var i=0; i<jsonData.DETAIL_CODE_LIST_recordSet.length; i++) {	
					
					var codeValue = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_CD;
					var codeName = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_NM;
					
					var checkboxHtml = "<input type=\"checkbox\" name=\"" + checkBoxName + "\" id=\"" + checkBoxName + "_" + codeValue + "\" value=\"" + codeValue + "\"/>" 
					                 + "<label for=\"" + checkBoxName + "_" + codeValue + "\">" + codeName + "</label>";
					
					
					if(parentHtml != undefined && parentHtml != "") {
						parentHtml += "&nbsp;&nbsp;";
					}
					
					parentHtml += checkboxHtml; 
				}

				$("#"+parentId).html(parentHtml);
				
//				fn_goPage('1');
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

/** 상세 코드 옵션 
 * @param cellvalue
 * @param grpCd : 분류코드
 * @param codeType : 상세코드 구분 DETL_CD, ETC1, ETC2, ETC3, ETC4
 * @param selectBoxId : select box id
 * @param alladd : dafault : true
 * @param string : 내용을 직접기입
 * @returns
 */
function fn_detailCodeList(grpCd, columnName, selectBoxId, alladd, string, code) {
	if(trim(columnName) == "" || columnName == null) {
		columnName = "DETL_CD";
	}
	if( (alladd == "" || alladd == undefined) && alladd != false) {		
		alladd = true;		
	} 
	if( (alladd == "" || alladd == undefined) && alladd == false) {
		if( string == "" || string == undefined ) {
			string = "::선택하세요::";
		} 
	}
	
	columnName = columnName.toUpperCase();

	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		async: false,
		data: {
				"nc_trId":"fmDetailCodeList", 
				"GRP_CD":grpCd,
				"COLUMN_NAME":columnName,
				"callback":""
		},
		success: function(data) { 
			var jsonData = JSON.parse(data);

			if(jsonData.DETAIL_CODE_LIST_recordSet != null) {

				$("#"+selectBoxId).get(0).options.length = 0;
				if(alladd) {
					$("#"+selectBoxId).get(0).options[0] = new Option("전체", "ALL");
				} else {
					if(string != 'exception') $("#"+selectBoxId).get(0).options[0] = new Option(string, "");
				}

				for(var i=0; i<jsonData.DETAIL_CODE_LIST_recordSet.length; i++) {	

					var codeValue = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_CD;
					var codeName = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_NM;
					
					if(string == 'exception') $("#"+selectBoxId).get(0).options[i] = new Option(codeName, codeValue); 
					else $("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue);  
				}
				if( code != undefined) {
					$("#"+selectBoxId).val(code);
				}
				
				if(string == 'exception') fn_goPage('1');
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

/** 상세 코드 옵션2 
 * @param cellvalue
 * @param grpCd : 분류코드 "A001,A002"
 * @param selectBoxId : select box id
 * @param nc_trId : 거래ID [ dmDetailCodeListExt, dmDetailCodeListExt2 : 상세명[비고내용]  ]
 * @param alladd : dafault : true
 * @returns
 */
function fn_detailCodeListExt(grpCd, selectBoxId, nc_trId, alladd) {
	
	if( (alladd == "" || alladd == undefined) && alladd != false) {		
		alladd = true;		
	}
	
	var grpCd = grpCd.split(",");
	var grpArr = new Array();
	$.each(grpCd, function(index,value) {
		grpArr.push(value);
		//console.log(index +" "+value);
	});
	
	$.ajaxSettings.traditional = true;	
	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		async: false,
		data: {
				"nc_trId":nc_trId, 
				"GRP_CD":grpArr,
				"COLUMN_NAME":"DETL_CD",
				"callback":""
		},
		success: function(data) { 
			var jsonData = JSON.parse(data);

			if(jsonData.DETAIL_CODE_LIST_recordSet != null) {

				$("#"+selectBoxId).get(0).options.length = 0;
				
				if(alladd) {
					$("#"+selectBoxId).get(0).options[0] = new Option("::선택하세요::", "");
				} 
				
				for(var i=0; i<jsonData.DETAIL_CODE_LIST_recordSet.length; i++) {	

					var codeValue = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_CD;
					var codeName = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_NM;
					
					$("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue); 
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

/** 상세 코드 옵션_우편번호
 * @param cellvalue
 * @param grpCd : 분류코드
 * @param codeType : 상세코드 구분 DETL_CD
 * @param selectBoxId : select box id
 * @param alladd : dafault : true
 * @param string : 내용을 직접기입
 * @returns
 */
function fn_detailCodeList_ZipCode(grpCd, columnName, etc1, selectBoxId, alladd, string, code) {
	if(trim(columnName) == "" || columnName == null) {
		columnName = "DETL_CD";
	}
	if( (alladd == "" || alladd == undefined) && alladd != false) {		
		alladd = true;		
	} 
	if( (alladd == "" || alladd == undefined) && alladd == false) {
		if( string == "" || string == undefined ) {
			string = "::선택하세요::";
		} 
	}
	
	columnName = columnName.toUpperCase();

	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		async: false,
		data: {
				"nc_trId":"fmDetailCodeList_ZipCode", 
				"GRP_CD":grpCd,
				"COLUMN_NAME":columnName,
				"ETC1" : etc1,
				"callback":""
		},
		success: function(data) { 
			var jsonData = JSON.parse(data);

			if(jsonData.DETAIL_CODE_LIST_recordSet != null) {

				$("#"+selectBoxId).get(0).options.length = 0;
				if(alladd) {
					$("#"+selectBoxId).get(0).options[0] = new Option("전체", "ALL");
				} else {
					$("#"+selectBoxId).get(0).options[0] = new Option(string, "");
				}

				for(var i=0; i<jsonData.DETAIL_CODE_LIST_recordSet.length; i++) {	

					var codeValue = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_CD;
					var codeName = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_NM;
					
					$("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue); 
				}
				if( code != undefined) {
					$("#"+selectBoxId).val(code);
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

/**
 * 문자열의 바이트수 리턴
 */
String.prototype.getBytes = function() {
	var l= 0;
	 
	for(var idx=0; idx < this.length; idx++) {
		var c = escape(this.charAt(idx));
		 
		if( c.length==1 ) l ++;
		else if( c.indexOf("%u")!=-1 ) l += 2;
		else if( c.indexOf("%")!=-1 ) l += c.length/3;
	}
	 
	return l;
};

/**
 * 문자열의 바이트수 리턴 : DB내의 한글Byte를 3Byte로 계산해서 적용함
 */
String.prototype.getBytes3 = function() {
	var l= 0;
	 
	for(var idx=0; idx < this.length; idx++) {
		var c = escape(this.charAt(idx));
		 
		if( c.length==1 ) l ++;
		else if( c.indexOf("%u")!=-1 ) l += 3;
		else if( c.indexOf("%")!=-1 ) l += c.length/3+1;
	}
	 
	return l;
}

/*
문자열을 인코딩 할때 사용한다. 다음과 같이 디코딩 하여 사용한다.
JAVA : URLEncoder.decode(str, "UTF-8")
JS : decodeURL(str)
*/
function encodeURL(str){
   if(str == undefined || str == '') return '';
   var s0, i, s, u;
   s0 = "";                // encoded str
   for (i = 0; i < str.length; i++){   // scan the source
       s = str.charAt(i);
       u = str.charCodeAt(i);          // get unicode of the char
       if (s == " "){s0 += "+";}       // SP should be converted to "+"
       else {
           if ( u == 0x2a || u == 0x2d || u == 0x2e || u == 0x5f || ((u >= 0x30) && (u <= 0x39)) || ((u >= 0x41) && (u <= 0x5a)) || ((u >= 0x61) && (u <= 0x7a))){       // check for escape
               s0 = s0 + s;            // don't escape
           }
           else {                  // escape
               if ((u >= 0x0) && (u <= 0x7f)){     // single byte format
                   s = "0"+u.toString(16);
                   s0 += "%"+ s.substr(s.length-2);
               }
               else if (u > 0x1fffff){     // quaternary byte format (extended)
                   s0 += "%" + (0xf0 + ((u & 0x1c0000) >> 18)).toString(16);
                   s0 += "%" + (0x80 + ((u & 0x3f000) >> 12)).toString(16);
                   s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);
                   s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
               }
               else if (u > 0x7ff){        // triple byte format
                   s0 += "%" + (0xe0 + ((u & 0xf000) >> 12)).toString(16);
                   s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);
                   s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
               }
               else {                      // double byte format
                   s0 += "%" + (0xc0 + ((u & 0x7c0) >> 6)).toString(16);
                   s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
               }
           }
       }
   }
   return s0;
}

/*
문자열을 디코딩 할때 사용한다. 다음과 같이 인코딩 하여 사용한다.
JAVA : URLEncoder.encode(str, "UTF-8")
JS : encodeURL(str)
*/
function decodeURL(str)
{
   if(str == undefined || str == '') return '';
   var s0, i, j, s, ss, u, n, f;
   s0 = "";                // decoded str
   for (i = 0; i < str.length; i++){   // scan the source str
       s = str.charAt(i);
       if (s == "+"){s0 += " ";}       // "+" should be changed to SP
       else {
           if (s != "%"){s0 += s;}     // add an unescaped char
           else{               // escape sequence decoding
               u = 0;          // unicode of the character
               f = 1;          // escape flag, zero means end of this sequence
               while (true) {
                   ss = "";        // local str to parse as int
                       for (j = 0; j < 2; j++ ) {  // get two maximum hex characters for parse
                           sss = str.charAt(++i);
                           if (((sss >= "0") && (sss <= "9")) || ((sss >= "a") && (sss <= "f"))  || ((sss >= "A") && (sss <= "F"))) {
                               ss += sss;      // if hex, add the hex character
                           } else {--i; break;}    // not a hex char., exit the loop
                       }
                   n = parseInt(ss, 16);           // parse the hex str as byte
                   if (n <= 0x7f){u = n; f = 1;}   // single byte format
                   if ((n >= 0xc0) && (n <= 0xdf)){u = n & 0x1f; f = 2;}   // double byte format
                   if ((n >= 0xe0) && (n <= 0xef)){u = n & 0x0f; f = 3;}   // triple byte format
                   if ((n >= 0xf0) && (n <= 0xf7)){u = n & 0x07; f = 4;}   // quaternary byte format (extended)
                   if ((n >= 0x80) && (n <= 0xbf)){u = (u << 6) + (n & 0x3f); --f;}         // not a first, shift and add 6 lower bits
                   if (f <= 1){break;}         // end of the utf byte sequence
                   if (str.charAt(i + 1) == "%"){ i++ ;}                   // test for the next shift byte
                   else {break;}                   // abnormal, format error
               }
           s0 += String.fromCharCode(u);           // add the escaped character
           }
       }
   }
   return s0;
}

/*
 메뉴 별 권한 체크
*/
function fn_menuAuthCheck(menuId, empId) {	
	$.ajax({
		url: "/envrSetup.json",
		dataType : "text",
		type: "POST",
		data: {
				"nc_trId"  : "pmMenuAuthCheckByUser", 
				"EMP_ID"   : empId, 
				"MENU_ID"  : menuId,
				"callback" : ""
		},
		success: function(data) {
			var jsonData = eval('('+data+')');
			
			if(parseInt(jsonData.fields.COUNT) < 1) {
				//alert("메뉴에 대한 사용 권한이 없습니다.");
				document.location.href = "/eSecurity/insNet/noAuth.jsp";
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

//날짜 관련
/*
 * 시간구함
*/ 

function getHour(){
	var date = new Date();
	hour = date.getHours();
	return hour;
}

/*
 * 오늘 날짜 구함
 * 
 */
function getToday(split) {
	if(split == null || split == undefined) split = "-";
	
	var date = new Date();
	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	var dd = date.getDate();

	if (mm < 10)
		mm = "0" + mm;
	if (dd < 10)
		dd = "0" + dd;

	return yyyy + split + mm + split + dd;
}

/*
 * 날짜 일 연산(날짜 문자열, 추가할 일수, 날짜 구분자)
 */
function getAddDate(dateStr, addDay, split) {
	if(split == null) split = "-";
	dateStr = dateStr.replaceAll(split, "");
		
	var yyyy = dateStr.substring(0,4);
	var mm = parseInt(dateStr.substring(4,6), 10);
	var dd = parseInt(dateStr.substring(6), 10);

	var date = new Date(yyyy, mm-1, dd);
	date.setDate(date.getDate() + addDay);
	yyyy = date.getFullYear();
	mm = date.getMonth()+1;
	dd = date.getDate();

	if (mm < 10)
		mm = "0" + mm;
	if (dd < 10)
		dd = "0" + dd;

	return yyyy + split + mm + split + dd;
}

/*
 * 날짜 월 연산(날짜 문자열, 추가할 월수, 날짜 구분자)
 */
function getAddMonth(dateStr, addMonth, split) {
	if(split == null) split = "-";
	dateStr = dateStr.replaceAll(split, "");
		
	var yyyy = dateStr.substring(0,4);
	var mm = parseInt(dateStr.substring(4,6), 10);
	var dd = parseInt(dateStr.substring(6), 10);

	var date = new Date(yyyy, mm-1, dd);
	date.setMonth(date.getMonth() + addMonth);
	yyyy = date.getFullYear();
	mm = date.getMonth()+1;
	dd = date.getDate();
	
	if (mm < 10)
		mm = "0" + mm;
	if (dd < 10)
		dd = "0" + dd;

	return yyyy + split + mm + split + dd;
}

/*
 * 두 날짜 사이의 일 계산
 */
function getDaysBetweenMonths(startDtStr, endDtStr, split) {
	if(isNull(split)) split = "-";
	startDtStr = startDtStr.replaceAll(split, "");
	endDtStr = endDtStr.replaceAll(split, "");
	
	var startDtArr = new Array();
	startDtArr[0] = parseInt(startDtStr.substring(0,4), 10);
	startDtArr[1] = parseInt(startDtStr.substring(4,6), 10)-1;
	startDtArr[2] = parseInt(startDtStr.substring(6), 10);
	var endDtArr = new Array();
	endDtArr[0] = parseInt(endDtStr.substring(0,4), 10);
	endDtArr[1] = parseInt(endDtStr.substring(4,6), 10)-1;
	endDtArr[2] = parseInt(endDtStr.substring(6), 10);

	var startDt = new Date(startDtArr[0], startDtArr[1], startDtArr[2]);
	var endDt = new Date(endDtArr[0], endDtArr[1], endDtArr[2]);
	
	var days = (endDt.getTime() - startDt.getTime())/1000/60/60/24;

	return Math.abs(days);
}

/*
 * 검색 시작, 종료일간의 날짜 차이가 days(일 수) 이내면 false, days를 초과하면 true
 */
function checkSearchPeriodOver(startDtStr, endDtStr, days) {
	if(isNull(days)) days = 90;
	if(getDaysBetweenMonths(startDtStr, endDtStr) > days) {
		return true;
	} else {
		return false;
	}
}

/*
 * 날짜 형식 체크 yyyy-mm-dd  
 */
function checkDate(str){
    regExp = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
    if(!regExp.test(str)){
        return false;
    }else{
        return true;
    }
}

/*
 * 년도 select option list 2012~현재년도  
 */
function fn_getYearList(appendNm){	
	var today = new Date();
    var startYy = '2012';//2012년 부터 시작
	var endYy   = today.getFullYear();
		
	var $year  = $("#"+appendNm);
	var option = "";	
	$year.append(option);
	for(var yyyy = startYy ; yyyy <= endYy ; yyyy++){
		if(yyyy == endYy){
			option +='<option value="'+yyyy+'" selected>'+yyyy+'년</option>';
		}else{
			option +='<option value="'+yyyy+'">'+yyyy+'년</option>';
		}
			
	}
	$year.append(option);
}

$(function(){

    $("#sign").click(function () {
        if ($("#high_select").css("display") == "none") {
            $("#high_select").fadeIn('');
        } else {
            $("#high_select").slideUp('fast');

        }
    });

    $('.all_open').click(function () {
        $("#high_select").slideUp('fast');
    });


});


	function rollIcon() {
		$("a.BasicOver img[src*='_off.gif']").hover(function() {
			this.src = this.src.replace(/_off\.gif$/, '_on.gif');
		}, function() {
			this.src = this.src.replace(/_on\.gif$/, '_off.gif');
		});
	}
	function rollIcon2() {
		$('a.BasicOver2 img').hover(function() {
			this.src = this.src.replace(/_off\.gif$/, '_on.gif');
		}, function() {
			this.src = this.src.replace(/_on\.gif$/, '_off.gif');
		});
	}

	$(function() {
		rollIcon();
		rollIcon2();
	});



// LNB
(function($) {

	$(function() {

		/* ie6 png 이미지처리*/
		if($.browser.className=="msie6"){
			DD_belatedPNG.fix('.png');
		}

		/* LNB */
		$lnb = $(".lnb");
		
		//lnb 초기설정
		$lnb.find("ul").hide();
		$lnb.find("li.on").parent("ul").show();
		$lnb.find(">ul>li").each(function(){
			if($(this).find("ul").length) $(this).find(">a").addClass("sub");
		});

		//현재페이지 스타일 변경
		$lnb_thispage = $lnb.find(".on");
		$lnb_thispage.find(">a img").attr("src", function(){return $(this).attr("src").replace("_off","_on")});

		//1차카테고리 클릭이벤트
		$lnb.find("h2:not(.on)").bind("click",function(){
			if($(this).find(">a").attr("href")!="#none"){
				return true;
			}
			else{
				$lnb
					.find("h2:not(.on)")
						.removeClass("active")
						.removeClass("hover")
						.next("ul").slideUp()
						.end()
					.find("img").attr("src", function(){return $(this).attr("src").replace("_on","_off")});

				if($(this).next("ul:visible").length){
					$(this).next("ul").slideUp();
					$(this).find("img").attr("src", function(){return $(this).attr("src").replace("_on","_off")});
					$(this).removeClass("active");
				}
				else{
					$(this).next("ul").slideDown();
					$(this).find("img").attr("src", function(){return $(this).attr("src").replace("_off","_on")});
					$(this).addClass("active");
				}
			}
		});

		//2차카테고리 클릭이벤트
		$lnb.find(">ul>li:not(.on)>a").bind("click",function(){
			if($(this).attr("href")!="#none"){
				return true;
			}else{
				$lnb.find("ul>li:not(.on)").find("ul").slideUp();
				if($(this).parent().find("ul:visible").length) $(this).find("ul").slideUp();
				else  $(this).parent().find("ul").slideDown();
				return false;
			}
		});

		// 오버설정
		$lnb.find("h2").bind("focus mouseover",function(){
			$(this).find("img").attr("src", function(){return $(this).attr("src").replace("_off","_on")});
			$(this).addClass("hover");
		});

		$lnb.find("h2:not(.on)").bind("blur mouseout",function(){
			if(!$(this).hasClass("active")){
				$(this).find("img").attr("src", function(){return $(this).attr("src").replace("_on","_off")});
			}
			$(this).removeClass("hover");
		});

		$lnb.find("ul li:not(.on) a").hover(function(){
			$(this).addClass('hover');
			$(this).find("img").attr("src", function(){return $(this).attr("src").replace("_off","_on")});
		},function(){
			$(this).removeClass('hover');
			$(this).find("img").attr("src", function(){return $(this).attr("src").replace("_on","_off")});
		});

	});

})(jQuery);



// topmenu

var timer = "";

	/*function openNav(idx){
		$(".depth2", idx).css("height","30px").stop().slideDown(200);
		$(">a>img", idx).stop().animate({marginTop:'-25px'}, 200);
	}
	function closeNav(idx){
		$(".depth2", idx).css("height","0").stop().slideUp(200);
		$(">a>img", idx).stop().animate({marginTop:'0'}, 200);
	}
	function openNavSub(idx){
		$(".depth3", idx).css("height","29px").stop().slideDown(200);
		$(idx).parents(".depth2").find(".slogan").hide();
		$(">a>img", idx).stop().animate({marginTop:'-29px'}, 200);
	}
	function closeNavSub(idx){
		$(".depth3", idx).css("height","0").stop().slideUp(200);
		$(">a>img", idx).stop().animate({marginTop:'0'}, 200);
	}
	function onMenu(){
		$("#header .nav>li.on").each(function(){
			openNav($(this));
			$("#header .nav>li.on>.depth2 li.on").each(function(){
				openNavSub($(this));	
			});
			$("#header .depth2>ul>li.on").each(function(){	
				if($(".depth3", this).size() == 0){
					$(this).parents(".depth2").find(".slogan").show();	
				}
			});
		});
	}*/

	$(function(){
		/*$("#header .nav>li").each(function(){
			$(this)
			.mouseenter(function(){
				if(0 == 0){
					$(".headerWrap #header").css("background","url('/esecurity/assets/common/images/common/top_area_bg.png') repeat-x 0 0");
				}
				openNav($(this));
				closeNav($(this).siblings("li"));
			})
			.mouseleave(function(){
				if(!$(this).hasClass("on")){
					closeNav($(this));
				}
			});
		});*/
		/*if(0 == 0){
			$("#header .nav").mouseleave(function(){
				//$(".headerWrap #header").css("background","url('/esecurity/assets/common/images/common/top_area_bg.jpg') repeat-x 0 0;");
			});		
		}
		
		$("#header .nav .depth2>ul>li").each(function(){
			$(this)
			.mouseenter(function(){
				closeNavSub($(this).siblings("li"));
				openNavSub($(this));
			})
			.mouseleave(function(){
				//closeNavSub($(this));
			});
		});$("#header .nav .depth2").each(function(){
			$(this)
			.mouseleave(function(){
				$(".depth3", this).css("height","0").stop().slideUp(200);
				$(">ul>li>a>img", this).stop().animate({marginTop:'0'}, 200);

			});
		});
		
		$("#header .nav .depth3>ul>li").not(".on").each(function(){
			$(this)
			.mouseenter(function(){
				openNavSub($(this));
				closeNavSub($(this).siblings("li").not(".on"));
			})
			.mouseleave(function(){
				closeNavSub($(this).not(".on"));
			});
		});
		$("#header .nav").mouseleave(function(){
			timer = setTimeout("onMenu()", 300);
		});
		$("#header .nav").mouseenter(function(){
			clearInterval(timer);
		});
		onMenu();*/
	});

function getDay_Date(date){
	var strYear = "";
	var strMonth = "";
	var strDay = "";
	var aDate = "";
	
	if(date.indexOf("-") > 0 ) {
		aDate = date.split("-");
		strYear = aDate[0];
		strMonth = aDate[1];
		strDay = aDate[2];
	} else {
		strYear = date.slice(0,4);
		strMonth = date.slice(4,6);
		strDay = date.slice(6,8);
	}
	
	strDay = parseFloat(strDay);
	var sDate = strYear + "/" + strMonth + "/" + strDay;
	var tmpDate = new Date(sDate);
	
	return tmpDate.getDay();
}
function checkWeekEnd(date){
	
	var strYear = "";
	var strMonth = "";
	var strDay = "";
	var aDate = "";
	if(date.indexOf("-") > 0 ) {
		aDate = date.split("-");
		strYear = aDate[0];
		strMonth = aDate[1];
		strDay = aDate[2];
	} else {
		strYear = date.slice(0,4);
		strMonth = date.slice(4,6);
		strDay = date.slice(6,8);
	}
	strDay = parseFloat(strDay);
	var sDate = strYear + "/" + strMonth + "/" + strDay;
	var tmpDate = new Date(sDate);
	if(tmpDate.getDay() == "0" || tmpDate.getDay() == "6"){
	}
}


function fn_juminchk (value) {
	if (value == "" || value == null || value.length != 13) {
		return false;
	}
	value = value.replace("-", "");

	
	var yy = value.substr(0, 2);        // 년도
	var mm = value.substr(2, 2);        // 월
	var dd = value.substr(4, 2);        // 일
	var genda = value.substr(6, 1);        // 성별
	var msg, ss, cc;

	// 첫번째 자료에서 연월일(YYMMDD) 형식 중 기본 구성 검사
	if (yy < "00" || yy > "99" || mm < "01" || mm > "12" ||	dd < "01" || dd > "31") {
		alert("정확한 주민등록번호를 입력하세요.");
		return false;
	}

	// 성별부분이 1 ~ 4 가 아닌 경우
	if (genda < "1" || genda > "4") {
		alert("정확한 주민등록번호를 입력하세요.");
		return false;
	}

	// 연도 계산 - 1 또는 2: 1900년대, 3 또는 4: 2000년대
	cc = (genda == "1" || genda == "2") ? "19" : "20";
	// 첫번째 자료에서 연월일(YYMMDD) 형식 중 날짜 형식 검사
	if (isYYYYMMDD(parseInt(cc + yy) , parseInt(mm) ,parseInt(dd)) == false) {
		alert("정확한 주민등록번호를 입력하세요.");
		return false;
	}

	if (/[^0-9-]+/.test(value)) {
		alert("정확한 주민등록번호를 입력하세요.");
		return false;
	}

	var ssnCheck = 0;
	for (var i = 0; i < 12; i++) {
		ssnCheck += (i % 8 + 2) * value.charAt(i);
	}
	ssnCheck = (11 - ssnCheck % 11) % 10;

	if (ssnCheck != value.charAt(12)) {
		alert("정확한 주민등록번호를 입력하세요.");
		return false;
	}

	return true;
}

// 날짜체크
function isYYYYMMDD(y, m, d) {
	switch (m) {
		case 2:        // 2월의 경우
			if (d > 29) return false;
			if (d == 29) {
				// 2월 29의 경우 당해가 윤년인지를 확인
				if ((y % 4 != 0) || (y % 100 == 0) && (y % 400 != 0))
					return false;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
		if (d == 31) {
			return false;
		}
	}
	return true;
}

function numbersonly(e, decimal) {
    var key;
    var keychar;

    if (window.event) {
        key = window.event.keyCode;
    } else if (e) {
        key = e.which;
    } else {
        return true;
    }
    keychar = String.fromCharCode(key);

    if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)
            || (key == 27)) {
        return true;
    } else if ((("0123456789").indexOf(keychar) > -1)) {
        return true;
    } else if (decimal && (keychar == ".")) {
        return true;
    } else
        return false;
}

/*
 * 회사 리스트 콤보 박스
 * 
 * compId : Login 사용자의 회사코드
 * authId : 권한아이디
 * selectObj : 사번(로그인 아이디)
 * 
 */
var geCommonCompList = function(compId, authId, selectObj, defaultSelectValue, refreshYn){
	$.ajax({
		url: "/entManage.json",
		dataType : "text",
		type: "post",
		async: false,
		data: {
				"nc_trId"    : "pmCompList", 
				"COMP_ID"    : compId,
				"callback"   : ""
		},
		success: function(data) {  
			var jsonData = JSON.parse(data);
			if(jsonData.rows_recordSet != null) {
				if(authId != '1'){
					for(var i=0; i<jsonData.rows_recordSet.length; i++) {	
						if(compId == jsonData.rows_recordSet[i].COMP_ID){
							selectObj.get(0).options[0] = new Option(jsonData.rows_recordSet[i].COMP_NM, jsonData.rows_recordSet[i].COMP_ID);
						}
					}
				}else{
					selectObj.get(0).options.length = 0;
					selectObj.get(0).options[0] = new Option("전체", "");
					for(var i=0; i<jsonData.rows_recordSet.length; i++) {	
						var codeValue = jsonData.rows_recordSet[i].COMP_ID;
						var codeName  = jsonData.rows_recordSet[i].COMP_NM;
						selectObj.get(0).options[i+1] = new Option(codeName, codeValue);
					}
				}
				selectObj.val(defaultSelectValue).attr("selected", "selected");
				
				// 목록 가지고 오기
				if(refreshYn){
					fn_goPage('1');	
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
};


/*
 * 사용자별 승인 권한 체크
 * 
 * sessionCompId : Login 사용자의 회사코드
 * authId : 권한아이디
 * empId : 사번(로그인 아이디)
 * 
 */
var getCommonMenuApprAuthCheck = function(sessionCompId, authId, empId, selectboxName, defaultSelectValue, refreshYn){
	$.ajax({
		url: "/envrSetup.json",
		dataType : "text",
		async: false,
		type: "POST",
		data: {
				"nc_trId"  : "pmMenuApprAuthCheckByUser", 
				"EMP_ID"   : empId, 
				"AUTH_ID"  : authId,
				"AUTH_ID_IN" : "SEARCH_AUTH_ID_IN",
				"SEARCH_AUTH_ID_IN" : "SEARCH_AUTH_1",
				"callback" : ""
		},
		success: function(data) {
			var jsonData = eval("(" + data + ")");
			if(parseInt(jsonData.fields.COUNT) < 1) {
				geCommonCompList(sessionCompId, '', $("#" + selectboxName), sessionCompId, refreshYn);						
			} else {
				geCommonCompList('', '1', $("#" + selectboxName), defaultSelectValue, refreshYn);		
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
};

/*
 * 페이징 정보 생성
 * 
 * jsonData : 조회된 Json Data
 * pageIndex : 현재 페이지
 * isShowPageInfo : 전체 건수 및 페이지 정보 보여줄지 여부
 * 
 */
var makePagingConetnt = function(jsonData, pageIndex, isShowPageInfo){
	
	var startPage = parseInt((pageIndex - 1) / 10) *10 + 1;
	var endPage   = jsonData.fields.PAGERECORD > (startPage +9) ? startPage + 9 : jsonData.fields.PAGERECORD;
	var prevPage  = startPage == 1 ? 1 : startPage-1;
	var nextPage  = endPage == jsonData.fields.PAGERECORD ? jsonData.fields.PAGERECORD : endPage + 1;
	var pagingContent = "";
	var pageNo = 0;
	
	pagingContent += "<a href='javascript:fn_goPage(\"1\")' class='prev'><img src='/esecurity/assets/common/images/common/prev_end.jpg' alt='맨처음' /></a>";
    pagingContent += "<a href='javascript:fn_goPage(\""+prevPage+"\")' class='prev'><img src='/esecurity/assets/common/images/common/prev.jpg' alt='이전' /></a>";

    for(var i=startPage; i<endPage+1; i++) {
    	pageNo = i;
    	if(pageIndex == pageNo) {
    		pagingContent += "<a href='javascript:fn_goPage(\"" + pageNo + "\")' title ='현재페이지'><b>" + pageNo + "</b></a>";
    	} else {
    		pagingContent += "<a href='javascript:fn_goPage(\"" + pageNo + "\")'>" + pageNo + "</a>";
    	}
    }
    
    pagingContent += "<a href='javascript:fn_goPage(\"" + nextPage + "\")' class='next'><img src='/esecurity/assets/common/images/common/next.jpg' alt='다음' /></a>";
    pagingContent += "<a href='javascript:fn_goPage(\"" + jsonData.fields.PAGERECORD + "\")' class='next'><img src='/esecurity/assets/common/images/common/next_first.jpg' alt='맨마지막' /></a>";
    
    if(isShowPageInfo){
    	var totalPage = parseInt(jsonData.fields.TOTAL / jsonData.fields.PAGESIZE) + (jsonData.fields.TOTAL % jsonData.fields.PAGESIZE > 0 ? 1 : 0);
    	pagingContent += "<div style='float:left'>총 <span id='totalCount'>" + jsonData.fields.TOTAL + "</span>건 <span id='currPage'>" + pageIndex + "</span> / <span id='totalPage'>" + totalPage + "</span> 쪽</div>";
    }
    
    return pagingContent;
    
};

/*
 * 팝업 페이징 정보 생성
 * 
 * jsonData : 조회된 Json Data
 * pageIndex : 현재 페이지
 * isShowPageInfo : 전체 건수 및 페이지 정보 보여줄지 여부
 * 
 */
var makePagingConetnt2 = function(jsonData, pageIndex, isShowPageInfo){
	
	var startPage = parseInt((pageIndex - 1) / 5) *5 + 1;
	var endPage   = jsonData.fields.PAGERECORD > (startPage +4) ? startPage + 4 : jsonData.fields.PAGERECORD;
	var prevPage  = startPage == 1 ? 1 : startPage-1;
	var nextPage  = endPage == jsonData.fields.PAGERECORD ? jsonData.fields.PAGERECORD : endPage + 1;
	var pagingContent = "";
	var pageNo = 0;
	
	pagingContent += "<a href='javascript:fn_goPage(\"1\")' class='prev'><img src='/esecurity/assets/common/images/common/prev_end.jpg' alt='맨처음' /></a>";
    pagingContent += "<a href='javascript:fn_goPage(\""+prevPage+"\")' class='prev'><img src='/esecurity/assets/common/images/common/prev.jpg' alt='이전' /></a>";

    for(var i=startPage; i<endPage+1; i++) {
    	pageNo = i;
    	if(pageIndex == pageNo) {
    		pagingContent += "<a href='javascript:fn_goPage(\"" + pageNo + "\")' title ='현재페이지'><b>" + pageNo + "</b></a>";
    	} else {
    		pagingContent += "<a href='javascript:fn_goPage(\"" + pageNo + "\")'>" + pageNo + "</a>";
    	}
    }
    
    pagingContent += "<a href='javascript:fn_goPage(\"" + nextPage + "\")' class='next'><img src='/esecurity/assets/common/images/common/next.jpg' alt='다음' /></a>";
    pagingContent += "<a href='javascript:fn_goPage(\"" + jsonData.fields.PAGERECORD + "\")' class='next'><img src='/esecurity/assets/common/images/common/next_first.jpg' alt='맨마지막' /></a>";
    
    if(isShowPageInfo){
    	var totalPage = parseInt(jsonData.fields.TOTAL / jsonData.fields.PAGESIZE) + (jsonData.fields.TOTAL % jsonData.fields.PAGESIZE > 0 ? 1 : 0);
    	pagingContent += "<div style='float:left'>총 <span id='totalCount'>" + jsonData.fields.TOTAL + "</span>건 <span id='currPage'>" + pageIndex + "</span> / <span id='totalPage'>" + totalPage + "</span> 쪽</div>";
    }
    
    return pagingContent;
    
};

/** 상세 코드 ETC4 
 * @param cellvalue
 * @param grpCd : 분류코드 "C063"
 * @param etc4Cd : ETC4 코드 "1101000001"
 * @param selectBoxId : select box id
 * @param nc_trId : 거래ID [ dmDetailCodeListEtc4 : 상세명[비고내용]  ]
 * @param alladd : dafault : true
 * @returns
 */
function fn_detailCodeListEtc4(grpCd, etc4Cd, selectBoxId, nc_trId, alladd) {
	
	if( (alladd == "" || alladd == undefined) && alladd != false) {		
		alladd = true;		
	}
	
	var grpCd = grpCd.split(",");
	var grpArr = new Array();
	$.each(grpCd, function(index,value) {
		grpArr.push(value);
		//console.log(index +" "+value);
	});
	
	$.ajaxSettings.traditional = true;	
	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		async: false,
		data: {
				"nc_trId": nc_trId, 
				"GRP_CD":grpArr,
				"ETC4":etc4Cd,
				"COLUMN_NAME":"DETL_CD",
				"callback":""
		},
		success: function(data) { 
			var jsonData = JSON.parse(data);

			if(jsonData.DETAIL_CODE_LIST_recordSet != null) {

				$("#"+selectBoxId).get(0).options.length = 0;
				
				if(alladd) {
					$("#"+selectBoxId).get(0).options[0] = new Option("::선택하세요::", "");
				} 
				
				for(var i=0; i<jsonData.DETAIL_CODE_LIST_recordSet.length; i++) {	

					var codeValue = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_CD;
					var codeName = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_NM;
					
					$("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue); 
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}



/** ECT4 -> DETL_NM
 * @param cellvalue
 * @param grpCd : 분류코드 "A001,A002"
 * @param selectBoxId : select box id
 * @param nc_trId : 거래ID [ dmDetailCodeListExt, dmDetailCodeListExt2 : 상세명[비고내용]  ]
 * @param alladd : dafault : true
 * @returns
 */
function fn_detailCodeListEtc4Nm(grpCd, selectBoxId, alladd, code) {
	
	if( (alladd == "" || alladd == undefined) && alladd != false) {		
		alladd = true;		
	}
	
	var grpCd = grpCd.split(",");
	var grpArr = new Array();
	$.each(grpCd, function(index,value) {
		grpArr.push(value);
		//console.log(index +" "+value);
	});
	
	$.ajaxSettings.traditional = true;	
	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		async: false,
		data: {
				"nc_trId":"fmDetailCodeList_Etc4Nm", 
				"GRP_CD":grpArr,
				"COLUMN_NAME":"DETL_CD",
				"callback":""
		},
		success: function(data) { 
			var jsonData = JSON.parse(data);

			if(jsonData.DETAIL_CODE_LIST_recordSet != null) {

				$("#"+selectBoxId).get(0).options.length = 0;
				
				if(alladd) {
					$("#"+selectBoxId).get(0).options[0] = new Option("::선택하세요::", "");
				} 
				
				for(var i=0; i<jsonData.DETAIL_CODE_LIST_recordSet.length; i++) {	

					var codeValue = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_CD;
					var codeName = jsonData.DETAIL_CODE_LIST_recordSet[i].DETL_NM;
					
					$("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue); 
				}
				
				if( code != undefined) {
					$("#"+selectBoxId).val(code);
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

/*
 * ctrl+N, ctrl+R, F5 키 방지
 * 
 * jsonData : 조회된 Json Data
 * pageIndex : 현재 페이지
 * isShowPageInfo : 전체 건수 및 페이지 정보 보여줄지 여부
 * 
 */

window.onkeydown = function(){
	if((event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || (event.keyCode == 116)){
		event.keyCode = 0;
		event.cancelBubble = true;
		event.returnValue = false;
		top.tryingToReload = false;
	}
};

/*
 * 마우스 오른쪽 클릭 방지
 * 
 * jsonData : 조회된 Json Data
 * pageIndex : 현재 페이지
 * isShowPageInfo : 전체 건수 및 페이지 정보 보여줄지 여부
 * 
 */
/*
oncontextmenu = function(e){
	if(e){
		e.preventDefault();
	}else{
		event.keyCode = 0;
		event.returnValue = false;
	}
};
*/


