
function commonValidate(formId) {
	var result = true;
	var $f = $("#" + formId);
	if($f.length == 0) {
		$f = $("form[name='" + formId + "']");
	}
	if($f.length == 0) {
		alert(cvMessage.missingForm);
		return false;
	}
	
	// 필수값 체크
	$f.find("input[notNull='Y'], select[notNull='Y'], textarea[notNull='Y']").each(function(){
		var tmpVal = $(this).val();
		//if($(this).val().trim() == "") { //ie8에서 적용안됨
		if($.trim(tmpVal) == "") {
			alert($(this).attr("title") + cvMessage.required);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
	});
	if(!result) return result;
	
	$f.find("input:radio[notNull='Y']").each(function(){
		var inputName = $(this).attr("name");
		if($f.find("input:radio[name='" + inputName + "'][notNull='Y']:checked").length == 0) {
			alert($(this).attr("title") + cvMessage.required);
			result = false;
			return false;
		}
	});
	if(!result) return result;
	
	$f.find("input:checkbox[notNull='Y']").each(function(){
		var inputName = $(this).attr("name");
		if($f.find("input:checkbox[name='" + inputName + "'][notNull='Y']:checked").length == 0) {
			alert($(this).attr("title") + cvMessage.required);
			result = false;
			return false;
		}
	});
	if(!result) return result;
	
	$f.find("input[dataType=int]").each(function(){
		var val = $(this).val();
		if($.trim(val) == "") return;
		//if(val.trim() == "") return; //ie8에서 적용이 안됨
		var iVal = parseInt(val, 10);
		if(isNaN(val) || val != iVal || val.indexOf(".") != -1) {
			alert($(this).attr("title") + cvMessage.notInt);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
		
		var minValue = $(this).attr("minValue");
		if(minValue == null || minValue == undefined || minValue == "") minValue = null;
		else minValue = parseInt($(this).attr("minValue"), 10);
		if(val != "" && minValue != null && iVal < minValue) {
			alert($(this).attr("title") + cvMessage.underMinValue);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
		
		var maxValue = $(this).attr("maxValue");
		if(maxValue == null || maxValue == undefined || maxValue == "") maxValue = null;
		else maxValue = parseInt($(this).attr("maxValue"), 10);
		if(maxValue != null && iVal > maxValue) {
			alert($(this).attr("title") + cvMessage.overMaxValue);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
	});
	if(!result) return result;
	
	$f.find("input[dataType=float]").each(function(){
		var val = $(this).val();
		//if(val.trim() == "") return;
		if($.trim(val) == "") return;
		var fVal = parseFloat(val, 10);
		if(isNaN(val) || val != fVal) {
			alert($(this).attr("title") + cvMessage.notFloat);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
		
		var fixed = $(this).attr("fixed");
		if(fixed == null || fixed == undefined || fixed == "") fixed = -1;
		else fixed = parseInt($(this).attr("fixed"), 10);
		if(fixed < 0) fixed = -1;
		if(fixed > -1 && fVal != fVal.toFixed(fixed)) {
			alert($(this).attr("title") + cvMessage.fixedOver1 + fixed + cvMessage.fixedOver2);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
		

		var minValue = $(this).attr("minValue");
		if(minValue == null || minValue == undefined || minValue == "") minValue = null;
		else minValue = parseInt($(this).attr("minValue"), 10);
		if(minValue != null && fVal < minValue) {
			alert($(this).attr("title") + cvMessage.underMinValue);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
		
		var maxValue = $(this).attr("maxValue");
		if(maxValue == null || maxValue == undefined || maxValue == "") maxValue = null;
		else maxValue = parseInt($(this).attr("maxValue"), 10);
		if(maxValue != null && fVal > maxValue) {
			alert($(this).attr("title") + cvMessage.overMaxValue);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
	});
	
	$f.find("input[dataType=email]").each(function(){
		var val = $(this).val();
		//if(val.trim() == "") return;
		if($.trim(val) == "") return;
		if(val.search(/(\S+)@(\S+)\.(\S+)/) == -1) {
			alert($(this).attr("title") + cvMessage.notEmail);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
	});
	if(!result) return result;
	
	$f.find("textarea[maxLength]").each(function(){
		var len = parseInt($(this).attr("maxLength"), 10);
		if($(this).val().length > len) {
			alert($(this).attr("title") + cvMessage.maxLength1 + len + cvMessage.maxLength2);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
	});
	
	$f.find("input[maxByte], textarea[maxByte]").each(function(){
		var len = parseInt($(this).attr("maxByte"), 10);
		
		if($(this).val().getBytes() > len) {
			alert($(this).attr("title") + cvMessage.maxByte1 + len + cvMessage.maxByte2);
			try{
				$(this).focus();
			}catch(e){}
			result = false;
			return false;
		}
	});
	
	if(!result) return result;
	
	$f.find("input[minByte], textarea[minByte]").each(function(){
		var len = parseInt($(this).attr("minByte"),10);
		if($(this).val().getBytes() < len) {
			alert($(this).attr("title") + cvMessage.minByte1 + len + cvMessage.minByte2);
			try{
				
			}catch(e){}
			result = false;
			return false;
		}
	});
	if(!result) return result;
	return result;
}
$(function(){
	try {
		$("input[dataType='date']").removeClass("hasDatepicker").datepicker({dateFormat:"yy-mm-dd"}).attr("readonly", "readonly");
		$("input[dataType='year']").yearpicker();
	}catch(e) {}
});

// 검색조건의 기간이 days일 이내인지 체크
function dateValidate(searchStartDt, searchEndDt, days){
	if(isNull(days)) days = 90;
	
	var $strDate = $("#"+searchStartDt);
	var $endDate = $("#"+searchEndDt);
	
	//console.log(getDaysBetweenMonths($strDate.val(), $endDate.val()));
	
	if(getDaysBetweenMonths($strDate.val(), $endDate.val()) > days) {
		alert(cvMessage.limitDate1+ days +cvMessage.limitDate2);
		return false;
	}
	
	if($strDate.val() > $endDate.val()){
		alert($endDate.attr("title")+ cvMessage.ennun + $strDate.attr("title") + cvMessage.gtValidation);
		//$(searchEndDt).focus();
		return false;
	}
	return true;
}

// 검색조건 시작일이 종료일보다 이전인지 체크
function dateValidate2(searchStartDt, searchEndDt){
	var $strDate = $("#"+searchStartDt);
	var $endDate = $("#"+searchEndDt);
	
	if($strDate.val() > $endDate.val()){
		alert($endDate.attr("title")+ cvMessage.ennun + $strDate.attr("title") + cvMessage.gtValidation);
		//$(searchEndDt).focus();
		return false;
	}
	return true;
}

// 값이 정수인지 확인
function isInt(val) {
	if($.trim(val) == "") return false;
	//if(val.trim() == "") return false;
	var iVal = parseInt(val, 10);
	if(isNaN(val) || val != iVal || val.indexOf(".") != -1) {
		return false;
	}
	
	return true;
}

// 값이 실수인지 확인
function isFloat(val) {
	if($.trim(val) == "") return false;
	//if(val.trim() == "") return false;
	
	var fVal = parseFloat(val, 10);
	if(isNaN(val) || val != fVal) {
		return false;
	}
	
	return true;
}

//특수문자 입력금지
function chkKey(obj){
	if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 96)) 
	{
       event.keyCode = null;
    }
}

//시작일이 종료일보다 이전인지 체크
function datePeriodVali(startDtId, endDtId, splitStr){
	var $strDate = $("#"+startDtId);
	var $endDate = $("#"+endDtId);
	var searchStartDt = $strDate.val().replace(splitStr,"");
	var searchEndDt = $endDate.val().replace(splitStr,"");
	
	if(searchStartDt > searchEndDt){
		alert($endDate.attr("title")+ cvMessage.ennun + $strDate.attr("title") + cvMessage.gtValidation);
		return false;
	}
	return true;
}
