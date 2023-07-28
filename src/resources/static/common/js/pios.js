function fn_articleGroupCodeList( selectBoxId, allValue, allText, selectedValue, ARTICLEGROUPID, ARTICLEKNDNO, COMPANYKND, COMPANYAREAKND, COMP_ID, ARTICLENOUSEYN ) {
	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		data: {
				"nc_trId"			: "fmPiosArticleGroupCodeList", 
				"COMPANYKND"		: COMPANYKND,
				"COMPANYAREAKND"	: COMPANYAREAKND,
				"COMP_ID"			: COMP_ID,
				"ARTICLEKNDNO"		: ARTICLEKNDNO,
				"ARTICLEGROUPID"	: ARTICLEGROUPID,
				"ARTICLENOUSEYN"	: ARTICLENOUSEYN,
				"callback"			: ""
		},
		success: function(data) { 

			var jsonData = JSON.parse(data);

			if(jsonData.LIST_recordSet != null) {
				$("#"+selectBoxId).get(0).options.length = 0;
				$("#"+selectBoxId).get(0).options[0] = new Option(allText, allValue);

				for(var i=0; i<jsonData.LIST_recordSet.length; i++) {	
					var record = jsonData.LIST_recordSet[i];
					var codeValue = record.ARTICLEGROUPID;
					var codeName  = record.ARTICLEGROUPNAME;
					$("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue); 
				}
				if( selectedValue != undefined) {
					$("#"+selectBoxId).val(selectedValue);
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

function fn_OutReasonCodeList( selectBoxId, allValue, allText, selectedValue ) {
	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		data: {
				"nc_trId"			: "fmOutReasonList", 
				"callback"			: ""
		},
		success: function(data) { 
			var jsonData = JSON.parse(data);

			if(jsonData.LIST_recordSet != null) {

				$("#"+selectBoxId).get(0).options.length = 0;
				$("#"+selectBoxId).get(0).options[0] = new Option(allText, allValue);

				for(var i=0; i<jsonData.LIST_recordSet.length; i++) {	
					var record = jsonData.LIST_recordSet[i];
					var codeValue = record.OUTREASONID;
					var codeName  = record.OUTREASONNAME;
					$("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue); 
				}
				if( selectedValue != undefined) {
					$("#"+selectBoxId).val(selectedValue);
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

function fn_SapNoPopup(rowId) {
	var SEARCH_MATERIAL_ID = $("#SERIALNO"+rowId).val();
	var param = { SEARCH_MATERIAL_ID : SEARCH_MATERIAL_ID };

	var returnValue = e_Util.openModal("/eSecurity/insNet/common/popUp/articleSapList.jsp", param, "690","680");
	if(returnValue != null && returnValue != undefined) {
		var MATERIAL_ID = decodeURIComponent(returnValue.MATERIAL_ID);
		var DESCRIPTION = decodeURIComponent(returnValue.DESCRIPTION);
		var BASE_UOM 	= decodeURIComponent(returnValue.BASE_UOM);
		var IMAGE_PATH 	= decodeURIComponent(returnValue.IMAGE_PATH);
		var MFR_PART_NO = decodeURIComponent(returnValue.MFR_PART_NO);
		var IMAGE_YN	= decodeURIComponent(returnValue.IMAGE_YN);
		
		$("#SERIALNO"+rowId).val(MATERIAL_ID);
		$("#ARTICLENAME"+rowId).val(DESCRIPTION);
		$("#UNITNAME"+rowId).val(BASE_UOM);
		$("#MATERIAL_ID"+rowId).val(MATERIAL_ID);
		$("#VENDERPN"+rowId).val(MFR_PART_NO);
		
		if(IMAGE_YN != "Y"){
			IMAGE_PATH = "/eSecurity/data/noimage.jpg";
		}
		var img = "<img src='"+IMAGE_PATH+"' width='20' height='20' class='cursor:pointer;margin-top:2px;' onclick=window.open('"+IMAGE_PATH+"','viewer','left=200,top=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=617,height=393')>";
		$("#DivImagePath"+rowId).html(img);
		$("#IMAGE_PATH"+rowId).val(IMAGE_PATH);
	}	
}


function fn_JasanNoPopup(rowId) {
	var SEARCH_MATERIAL_ID = $("#JSNO"+rowId).val();
	var param = { SEARCH_MATERIAL_ID : SEARCH_MATERIAL_ID };
	var returnValue = e_Util.openModal("/eSecurity/insNet/common/popUp/jasanSapList.jsp", param, "690","680");
	if(returnValue != null && returnValue != undefined) {
		var MATERIAL_ID = decodeURIComponent(returnValue.MATERIAL_ID);
		var DESCRIPTION = decodeURIComponent(returnValue.DESCRIPTION);
		var ASSET_MAIN_NO 	= decodeURIComponent(returnValue.ASSET_MAIN_NO);
		var INVENTORY_NUMBER 	= decodeURIComponent(returnValue.INVENTORY_NUMBER);
		var SERIAL_NO = decodeURIComponent(returnValue.SERIAL_NO);
		var RESP_COST_CENTER_NAME = decodeURIComponent(returnValue.RESP_COST_CENTER_NAME);
		var IMAGE_PATH = decodeURIComponent(returnValue.IMAGE_PATH);
		var IMAGE_YN = decodeURIComponent(returnValue.IMAGE_YN);

		$("#MATERIAL_ID"+rowId).val(MATERIAL_ID);
		$("#ARTICLENAME"+rowId).val(DESCRIPTION);
		$("#JSNO"+rowId).val(ASSET_MAIN_NO);
		$("#MMNO"+rowId).val(INVENTORY_NUMBER);
		$("#SERIALNO"+rowId).val(SERIAL_NO);
		$("#DEPTNAME"+rowId).val(RESP_COST_CENTER_NAME);
		

		if(IMAGE_YN != "Y"){
			IMAGE_PATH = "/eSecurity/data/noimage.jpg";
		}
		var img = "<img src='"+IMAGE_PATH+"' width='20' height='20' class='cursor:pointer;margin-top:2px;' onclick=window.open('"+IMAGE_PATH+"','viewer','left=200,top=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=617,height=393')>";
		$("#DivImagePath"+rowId).html(img);
		$("#IMAGE_PATH"+rowId).val(IMAGE_PATH);
	}		
}

function fn_SapNoPopup_product(rowId) {
	var SEARCH_MATERIAL_ID = $("#MATERIAL_ID"+rowId).val();
	var param = { SEARCH_MATERIAL_ID : SEARCH_MATERIAL_ID };
	var returnValue = e_Util.openModal("/eSecurity/insNet/common/popUp/productSapList.jsp", param, "500","680");
	if(returnValue != null && returnValue != undefined) {
		var BASE_UOM = decodeURIComponent(returnValue.BASE_UOM);
		var MATERIAL_ID = decodeURIComponent(returnValue.MATERIAL_ID);
		var DESCRIPTION = decodeURIComponent(returnValue.DESCRIPTION);
		
		$("#UNITNAME"+rowId).val(BASE_UOM);
		$("#MATERIAL_ID"+rowId).val(MATERIAL_ID);
		$("#ARTICLENAME"+rowId).val(DESCRIPTION);
	}	
}

function fn_articleComPopup(rowId, ARTICLEKNDNO, ARTICLEGROUPID) {
	var SEARCH_SERIALNO = $("#SERIALNO"+rowId).val();
	var param = { ARTICLEKNDNO:ARTICLEKNDNO, ARTICLEGROUPID:ARTICLEGROUPID, SEARCH_ACSERIALNO:SEARCH_SERIALNO };
	var returnValue = e_Util.openModal("/eSecurity/insNet/common/popUp/articleComList.jsp", param, "600","680");
	if(returnValue != null && returnValue != undefined) {
		var ARTICLECOMPUTERIZEID = decodeURIComponent(returnValue.ARTICLECOMPUTERIZEID);
		var SERIALNO = decodeURIComponent(returnValue.ACSERIALNO);
		var MODELNAME = decodeURIComponent(returnValue.MODELNAME);
		
		$("#SERIALNO"+rowId).val(SERIALNO);
		$("#ARTICLECOMPUTERIZEID"+rowId).val(ARTICLECOMPUTERIZEID);
		$("#ARTICLENAME"+rowId).val(MODELNAME);
	}	
}

function fn_article(rowId, ARTICLEKNDNO, ARTICLEGROUPID) {
	var SEARCH_ARTICLENAME = $("#ARTICLENAME"+rowId).val();
	var param = { ARTICLEKNDNO:ARTICLEKNDNO, ARTICLEGROUPID:ARTICLEGROUPID, SEARCH_ARTICLENAME : SEARCH_ARTICLENAME };
	var returnValue = e_Util.openModal("/eSecurity/insNet/common/popUp/articleList.jsp", param, "500","680");
	if(returnValue != null && returnValue != undefined) {
		var ARTICLEID 	= decodeURIComponent(returnValue.ARTICLEID);
		var ARTICLENAME = decodeURIComponent(returnValue.ARTICLENAME);

		$("#ARTICLEID"+rowId).val(ARTICLEID);
		$("#ARTICLENAME"+rowId).val(ARTICLENAME);
	}	
}

function FormatMoneyOnLostFocusObj(obj) {
	var strData = obj.value;
	var strDataChange = makeFormat(obj.value);

	if (strData != strDataChange)
	{
		obj.value = strDataChange;
	}
}
function makeFormat(obj) {
	if (obj == null || obj == "")
	{
		return 0;
	}

	var sDigit = obj;
	var sHighDigit="";	//소수점 상위
	var sLowDigit="";	//소수점 하위
//	alert(obj +"zz"+sDigit+"zz"+obj.lastIndexOf("."));
	
	if (sDigit.lastIndexOf(".") == -1)
	{
		sHighDigit = sDigit.substring(0,sDigit.length);
		sLowDigit = "0";
	}
	else {
		sHighDigit = sDigit.substr(0,sDigit.lastIndexOf("."));
		sLowDigit = sDigit.substring(sDigit.lastIndexOf(".")+1,sDigit.length);
	}
	if (eval(sLowDigit) != "0")
	{
		return reformatMoneyType(makeDigitStream(sHighDigit)) + "." + sLowDigit;
	}
	else {
		return reformatMoneyType(makeDigitStream(sHighDigit));
	}
}

function makeDigitStream(obj) {
	var tempobj = "";
	var sobj = obj;
	var sMinus="";

	if(sobj == "."){
					return "0";
	}

	if (sobj == "0-")
	{
		sobj = "-";
	}
	if (sobj.charAt(0) == "-" && sobj.length > 1)
	{
		sMinus = "-";
		sobj = sobj.substr(1,sobj.length);
	}
	sobj = replace(sobj, "-", "");

	//숫자를 제외한 문자제거
	for (i=0;i<sobj.length;i++)
	{
        var c = sobj.charAt(i);
        if (isDigit(c)) {
			tempobj += c;
		}
	}
	//무효한 0제거
	var izerocnt=0;
	var isfirstzero=true;
	var isFirstPoint=false;

	for (i=0;i<tempobj.length;i++)
	{
        var c = tempobj.charAt(i);

		if (isfirstzero && c == 0)
		{
			izerocnt++;
		}
		else {
			isfirstzero = false;
			break;
		}
	}
	if (tempobj.length == izerocnt && isfirstzero)
	{
		if(tempobj.length == 0){
				tempobj = "0";
		}
		return sMinus+tempobj;
	}
	else {
		if (tempobj == "-")
		{
			return "-0";
		}
		else {
			return sMinus+tempobj.substring(izerocnt,tempobj.length);
		}
	}
}

function reformatMoneyType(obj) {
	var a_obj = new Array();
	var sMinus="";

	if (obj.charAt(0) == "-")
	{
		sMinus = "-";
		obj = obj.substr(1,obj.length);
	}
	while (obj.length > 3)
	{
		var ss1 = obj.substr(obj.length-3,3);
		var ss2 = obj.substr(0,obj.length-3);
		a_obj[a_obj.length] = ss1;
		obj = ss2;
	}
	a_obj[a_obj.length] = obj;
	a_obj.reverse();
	return sMinus+a_obj.join(",");
}

function replace(source, searchstring, replacestring) {
	if (source.length == 0)
	{
		return source;
	}
	var tempstr="";
	var returnstr = "";
	var ilen = source.length;
	var ilensearch =  searchstring.length;
	
	for (i=0; i<(ilen); i++)
	{
		tempstr = source.substring(i, i + 1);
		if (source.substring(i, i + ilensearch) == searchstring)
		{
			returnstr += replacestring;
			i += (ilensearch-1);
		}
		else{
			returnstr += tempstr;
		}
	}
	return returnstr;
}

function isDigit (c)
{
	return (((c >= "0") && (c <= "9")) || (c == ".")  || (c == "-"))
}

function onlyNumDecimalInput(){ 
	var code = window.event.keyCode; 
	  
	if ((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 110 || code == 190 || code == 8 || code == 9 || code == 13 || code == 46) { 
		window.event.returnValue = true; 
		return; 
	} 
	alert("숫자만 입력 가능 합니다!"); 
	window.event.returnValue = false; 
} 

// 해외법인 불러오기
function fn_foreignCompList( selectBoxId, allValue, allText, selectedValue ) {
	$.ajax({
		url: "/common.json",
		dataType : "text",
		type: "POST",
		data: {
				"nc_trId"			: "fmForeignCompList", 
				"callback"			: ""
		},
		success: function(data) { 

			var jsonData = JSON.parse(data);

			if(jsonData.LIST_recordSet != null) {
				$("#"+selectBoxId).get(0).options.length = 0;
				$("#"+selectBoxId).get(0).options[0] = new Option(allText, allValue);

				for(var i=0; i<jsonData.LIST_recordSet.length; i++) {	
					var record = jsonData.LIST_recordSet[i];
					var codeValue = record.COMP_ID;
					var codeName  = record.COMP_NM;
					$("#"+selectBoxId).get(0).options[i+1] = new Option(codeName, codeValue); 
				}
				if( selectedValue != undefined) {
					$("#"+selectBoxId).val(selectedValue);
				}
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}
