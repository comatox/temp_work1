/* 외부망 Login */
ext_Login = function() {
	return {
		login : function(param, fncallback) {
			var n_param = {
					callback : ""
			};
			$.extend(n_param, param);
			this.callServiceByJson(n_param, function(call) {
				//	var passchk = false;
					var result = JSON.parse(call);				
					if(result.message.result == "OK" && result.fields.RESULT == "OK") {					
						//passchk = true;
					} else {
						alert("Please Check for your login Email address or login password.");
						//passchk = false;
					}
					return fncallback(result.fields);
				});
		},
		callServiceByJson : function(param, fncallback) {
			$.ajax({
	  			url:"/login.json",             
				data: param,    
				type:"post",
				dataType:"text",				
				success : fncallback,			
				error : function(XMLHttpRequest, textStatus, errorThrown) {				
					alert(result.message.messageName);
				}
	         });
		},
		setCook :  function(name, id, expiredays) {
			var today = new Date();
	         today.setDate(today.getDate() + expiredays);
	         document.cookie = name + '=' + escape(id) + '; path=/; expires='+ today.toGMTString() + ';';	         
		},
		getCook : function(name) {
			// 페이지 접속시 최종 쿠키값 불러오기
			var cook = document.cookie+";";
			var idx = cook.indexOf(name, 0);
			var val = "";
			
			if (idx != -1) {
	             cook = cook.substring(idx, cook.length);
	             begin = cook.indexOf("=", 0) + 1;
	             end = cook.indexOf(";", begin);
	             val = unescape(cook.substring(begin, end));
	         }
	 
	        // 가져온 쿠키값이 있으면 id값 세팅
	         if (val != "") {
	             return {"lang":val, "save":true};	        	 
	         } else {
	        	 return {"save":false};
	         }
		},
		getLocation : function(param, fncallback) {
			//alert(JSON.stringify(param));
			
			var p_string = "";
			var p_row = 0;
			$.each(param, function(index, value){
				if(p_row > 0) p_string += "&";
				p_string += index+"="+value;
				p_row++;
			});
			//return;
			var url = "/eSecurity/extNet/loginSession_en.jsp?"+p_string;
			if(param.RESULT == "OK") return fncallback(url);
			else return fncallback("/eSecurity/extNet/login_en.jsp");
		},
		login2 : function(param, fncallback) {
			var n_param = {
					nc_trId : "pmLoginUsert",
					callback : ""
			};			
			$.extend(n_param, param);
			
			this.callServiceByJson(n_param, function(call) {
				var passchk = false;
				var result = JSON.parse(call);				
				if(result.message.result == "OK" && result.fields.RESULT == "OK") {					
					passchk = true;
				} else {
					alert(result.message.messageName);
					passchk = false;
				}
				return fncallback(passchk);
			});
		}
	}
}();