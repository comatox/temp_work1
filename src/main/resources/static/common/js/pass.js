function isLeapYear(year) {   
    // parameter가 숫자가 아니면 false   
    if (isNaN(year))   
        return false;   
    else        {   
        var nYear = eval(year);   
  
        // 4로 나누어지고 100으로 나누어지지 않으며 400으로는 나눠지면 true(윤년)   
        if (nYear % 4 == 0 && nYear % 100 != 0 || nYear % 400 == 0)   
            return true;   
        else  
            return false;   
    }   
}  

// start, end format: yyyymmdd
function getDifMonths(start, end) {
    var startYear = start.substring(0, 4);
    var endYear = end.substring(0, 4);
    var startMonth = start.substring(4, 6) - 1;
    var endMonth = end.substring(4, 6) - 1;
    var startDay = start.substring(6, 8);
    var endDay = end.substring(6, 8);

    // 연도 차이가 나는 경우
    if (eval(startYear) < eval(endYear)) {
        // 종료일 월이 시작일 월보다 수치로 빠른 경우
        if (eval(startMonth) > eval(endMonth))   {
            var newEnd = startYear + "1231";
            var newStart = endYear + "0101";

            return (eval(getDifMonths(start, newEnd)) + eval(getDifMonths(newStart, end))).toFixed(2);
        // 종료일 월이 시작일 월보다 수치로 같거나 늦은 경우
        } else                                  {
            var formMonth = eval(startMonth) + 1;
            if (eval(formMonth) < 10)    formMonth = "0" + formMonth;

            var newStart = endYear + "" + formMonth + "" + startDay;
            var addMonths = (eval(endYear) - eval(startYear)) * 12;

            return (eval(addMonths) + eval(getDifMonths(newStart, end))).toFixed(2);
        }
    } else                                  {
        // 월별 일수차 (30일 기준 차이 일수)
        var difDaysOnMonth = new Array(1, -2, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1);
        var difDaysTotal = getDifDays(start, end)+1;

        for (i = startMonth; i < endMonth; i++)  {
            if (i == 1 && isLeapYear(startYear))    difDaysTotal -= (difDaysOnMonth[i] + 1);
            else                                    difDaysTotal -= difDaysOnMonth[i];
        }

        // because view this function
        // window.alert("- run getDifMonths()\n- " + start + " ~ " + end + " => " + (difDaysTotal / 30).toFixed(2));

        return (difDaysTotal / 30).toFixed(2);
     }
}

// start, end format: yyyymmdd
function getDifDays(start, end) {
    var dateStart = new Date(start.substring(0, 4), start.substring(4, 6) - 1, start.substring(6, 8));
    var dateEnd = new Date(end.substring(0, 4), end.substring(4, 6) - 1, end.substring(6, 8));
    var difDays = (dateEnd.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000);

    return Math.ceil(difDays);
}

function replaceAll(str, orgStr, repStr) {
	if(str != undefined && str != null && str != "") {
		return str.split(orgStr).join(repStr);
	} else {
		return "";
	}
}