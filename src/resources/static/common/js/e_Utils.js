/**
 *	Login 관련 ajax
 */
e_Util = function () {	
	return {
		/****************************************************************
		 * 로그인
		 * param : 파라미터
		 * fncallback : 리턴함수
		 ****************************************************************/
		login : function(param, fncallback) {
			var n_param = {
					nc_trId : "pmLoginUserView",
					async: false,
					callback : ""
			};
			$.extend(n_param, param);
			//$.ajaxSetup({ async: false });

			this.callcommonByJson(n_param, function(call) {
				var result = JSON.parse(call);
				if(result.message.result == "OK" && result.fields.RESULT == "OK" ) {

				/*} else if(result.fields.RESULT == 'I' || result.fields.RESULT == 'Z'){

				}*/
				} else {
					if(result.fields.ACC == "PW_FAIL"){
						alert("사용자 비밀번호가 일치하지 않습니다.");
					}else if(result.fields.ACC == "LOGIN_CNT_FAIL"){
						alert("로그인 실패 횟수가 10회를 초과하여 로그인 하실 수 없습니다.");
					}else if(result.fields.ACC == "ID_FAIL"){
						alert("사용자 아이디가 등록되지 않았습니다.");
					}else if(result.fields.ACC == "LEAVE"){
						alert("로그인 할 수 없는 사용자입니다.");
					}else{
						alert(result.message.messageName);
					}
				}
				return fncallback(result.fields);
			});
		},
		/****************************************************************
		 * 로그인 SSO
		 * param : 파라미터
		 * fncallback : 리턴함수
		 ****************************************************************/
		loginSSO : function(param, fncallback) {
			var n_param = {
					nc_trId : "pmLoginUserView_SSO",
					async: false,
					callback : ""
			};
			$.extend(n_param, param);
			//$.ajaxSetup({ async: false });
			this.callcommonByJson(n_param, function(call) {
				var result = JSON.parse(call);
				if(result.message.result == "OK" && result.fields.RESULT == "OK") {

				} else {
					alert(result.message.messageName);
				}
				return fncallback(result.fields);
			});
		},
		/****************************************************************
		 * ajax 서비스 공통
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callcommonByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/common.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 공지
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callnoticeByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/notice.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 환경설정
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callenvrSetupByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/envrSetup.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 출입신청
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callEntReqstByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/entReqst.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 결재함
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callIntgrtApprvByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/intgrtApprv.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 출입신청
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callEntManageByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/entManage.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 방문객출입
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callvisitRsrvByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/visitRsrv.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		callvisitRsrvByJson2 : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/visitRsrv.json",
				data: param,
				type:"post",
				dataType:"text",
				async: false,
				cache: false,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 헬프데스크
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callhelpDeskByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/helpDesk.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 자산반출입
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callasetBrngByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/asetBrng.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 서비스신청
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callsrvceReqstByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/srvceReqst.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * ajax 서비스 통계
		 * param : 파라미터 [ async : false 동기, true : 비동기, loading : 로딩 이미지 표시여부(동기방식일경우 안됨) ]
		 * fncallback : 리턴함수
		 ****************************************************************/
		callStatsByJson : function(param, fncallback) {
			var sync = true, loading = false;
			if(param.async != undefined && !param.async) {
				sync = false;
			}
			if(param.loading != undefined && param.loading) {
				loading = true;
			}
			$.ajax({
	  			url:"/stats.json",
				data: param,
				type:"post",
				dataType:"text",
				async:sync,
				traditional : true,
				beforeSend : function() {
					if(loading) e_Util.loading("처리 중 입니다.");
				},
				success : function(data) {
					if(loading) e_Util.loadingD();
					return fncallback(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(loading) e_Util.loadingD();
					alert(textStatus);
				}
	         });
		},
		/****************************************************************
		 * 쿠기생성
		 * name : 쿠키명
		 * id : 값
		 * expiredays : 보관일 (1일단위)
		 ****************************************************************/ 
		setCook :  function(name, id, expiredays) {
			var today = new Date();
	         today.setDate(today.getDate() + expiredays);
	         document.cookie = name + '=' + escape(id) + '; path=/; expires='+ today.toGMTString() + ';';	         
		},
		/**************************************************************** 
		 * 쿠기값 불러오기
		 * name : 쿠키명		
		 ****************************************************************/ 
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
	             return {"id":val, "save":true};	        	 
	         } else {
	        	 return {"save":false};
	         }
		},
		/**************************************************************** 
		 * 로그인 후 url 지정 - 미사용중
		 * param : 파라미터
		 * fncallback : 리턴함수
		 ****************************************************************/ 
		getLocation : function(param, fncallback) {		
			var p_string = "";
			var p_row = 0;
			$.each(param, function(index, value){
				if(p_row > 0) p_string += "&";
				p_string += index+"="+value;
				p_row++;
			});			
			var url = "/eSecurity/insNet/Main/loginSession.jsp?"+p_string;
			if(param.RESULT == "Y") {
				return fncallback(url);
			} else if(param.RESULT == 'I' || param.RESULT == 'Z'){ //임시
				return fncallback(url);
			} else return fncallback("/eSecurity/insNet/login.jsp");
		},
		/**************************************************************** 
		 * 로그인 후 url 지정 - 미사용중
		 * param : 파라미터
		 * fncallback : 리턴함수
		 ****************************************************************/ 
		getLocationSSO : function(param, fncallback) {		
			var p_string = "";
			var p_row = 0;
			$.each(param, function(index, value){
				if(p_row > 0) p_string += "&";
				p_string += index+"="+value;
				p_row++;
			});		
						
			var url = "/eSecurity/insNet/Main/loginSession.jsp?"+p_string;			
			if(param.TARGETURL != undefined && param.TARGETURL != '') {				
				url = "/eSecurity/insNet/Main/reqSession.jsp?"+p_string;				
			}
			
			if(param.RESULT == "OK") return fncallback(url);
			else return fncallback("/eSecurity/insNet/login.jsp");
		},
		/**************************************************************** 
		 * 모달팝업 생성
		 * url : 새 윈도우에 열 경로
		 * param : 파라메터
		 * width : width
		 * height : height
		 * scroll : scroll 여부( yes/no )
		 * resizable : resizable 여부( yes/no )
		 ****************************************************************/ 
		openModal : function(url, param, width, height, scroll, resizable) {
			if(scroll == "" || scroll == undefined) {
				scroll = "no";
			}			
			var style = "dialogWidth:" + width + "px;"
						+ "dialogHeight:" + height + "px;"
						+ "center:yes;status:no;help:no;scroll:"+scroll+";resizable:" + resizable + ";";

			return window.showModalDialog(url, param, style);
		},
		/**************************************************************** 
		 * 일반팝업 생성
		 * url : 새 윈도우에 열 경로
		 * wname : 새 윈도우 이름
		 * w : width
		 * h : height
		 * scroll : scroll 여부( true/false )
		 ****************************************************************/ 
		openNewWin : function(url, param, wname, w, h, scroll, fncallback) {
			
			if(param != null || param != ""){
				var page = url+"?";
				var cnt = 0;
				$.each(param, function(index,value){
					if(cnt > 0 ) page += "&";
					if(value != "") {
						page += index + "=" + value;
						cnt++;
					}
					
				});
			}else{
				var page = url;
			}			
			var winl = (screen.width - w) / 2;
			var wint = (screen.height - h) / 2;
			var winprops = 'height='+h+',width='+w+',top='+wint+',left='+winl+',scrollbars='+scroll+',resizable=no,toolbar=no,location=no,status=no';			
			var win = window.open(page, wname, winprops);			
			if (parseInt(navigator.appVersion) >= 4) { win.window.focus(); };
			
			
		},
		alert : function(title, msg, elHtml) {
			   var $elHtml = $(elHtml);
			   var alertMsgHtml = "<table width=100% height=100%><tr><td>"+msg+"</td></tr></table>";
			   var alertHtml;
			   if ($elHtml.length == 0) {
				    elHtml = "#_alert";
				    alertHtml = "<div id='_alert' title='"+title+"'></div>";
				    $elHtml = $(alertHtml);
				    $(document).append($elHtml);
			   } else {
				   $elHtml.attr("title", title);
			   }
			   $elHtml.html(alertMsgHtml);			  
			   $elHtml.dialog({
				     modal: true, 
				     resizable: false,
				     width:300,
				     height:100, 
				     show: {effect:'fade', speed: 800}, 
				     hide: {effect:'fade', speed: 800},     
				     buttons: [
			                  {
			                      text: "확인",
			                     // className: 'cancelButtonClass',			                     
			                      click: function() {			                        
			                    	  $( this ).dialog( "close" );
			                      }
			                  }
			              ],
			         create: function () {
			           var widget = $(this).dialog('widget');                   
	                   var dialog = $(this);   
	                   $('.ui-dialog-titlebar',widget).remove() ;
	                  // widget.css('padding', 3).find('.ui-dialog-titlebar').css('padding', 0);
	                   $('.ui-dialog-buttonpane',widget).css('padding',0);
	                   $('.ui-button-text',dialog).css('padding',7);
	                   $('.ui-dialog-buttonpane button',widget).css('margin',5); 	                 
			        },
			        open: function(e,ui) { }
			    }).keyup(function(e){   if( e.keyCode == 13 ){    $(this).parent().find('button:nth-child(1)').trigger("click");   } });				  
		},
		confirm : function (title, confirmMsg, func, elHtml) {
			   var $elHtml = $(elHtml);
			   var confirmHtml;
			   //title = '';
			   var confirmMsgHtml = "<table width=100% height=100%><tr><td>"+confirmMsg+"</td></tr></table>";			   
			   if ($elHtml.length == 0) {
				    elHtml = "#_confirm";
				    confirmHtml = "<div id='_confirm' title='"+title+"'></div>";
				    $elHtml = $(confirmHtml);
				    $(document).append($elHtml);
			   } else {
				   $elHtml.attr("title", title);
			   }			   
			   $elHtml.html(confirmMsgHtml);			   
			   $elHtml.dialog({ 
				    resizable: false,
				    height:100,
				    modal: true,
				    width:300,    
				    show: {effect:'fade', speed: 800}, 
				    hide: {effect:'fade', speed: 800},          
				    buttons: {
				        "확인" : function() { eval(func) ; $( this ).dialog( "close" ); }, //항목 수정
				        "취소" : function() { $( this ).dialog( "close" ); }
				    },
				    create: function () {
				           var widget = $(this).dialog('widget');                   
		                   var dialog = $(this);                   
		                   $('.ui-dialog-titlebar',widget).remove() ;
		                   $('.ui-dialog-buttonpane',widget).css('padding',0);
		                   $('.ui-button-text',dialog).css('padding',7);
		                   $('.ui-dialog-buttonpane button',widget).css('margin',5);                   
				        }
			  });
		},
		loading : function(msg, elHtml) {
			   var $elHtml = $(elHtml);
			   var title = '';
			   var alertMsgHtml = "<table width=100% height=100% style='margin-top:5px;'><tr><td align='center'>"+msg+"</td></tr></table>";
			   var alertHtml;
			   if ($elHtml.length == 0) {
				    elHtml = "#_loading";
				    alertHtml = "<div id='_loading' title='"+title+"'></div>";
				    $elHtml = $(alertHtml);
				    $(document).append($elHtml);
			   } else {
				   $elHtml.attr("title", title);
			   }
			   $elHtml.html(alertMsgHtml);			  
			   $elHtml.dialog({
				     modal: true, 
				     resizable: false,
				     width:200,
				     height:80, 
				     show: {effect:'fade', speed: 800}, 
				     hide: {effect:'fade', speed: 800},				    
			         create: function () {
			           var widget = $(this).dialog('widget');                   
	                   var dialog = $(this);   
	                   $('.ui-dialog-titlebar',widget).remove();	                 
	                   $('.ui-dialog-buttonpane',widget).remove();
	                   $('.ui-button-text',dialog).css('padding',7);
	                   $('.ui-dialog-buttonpane button',widget).css('margin',5); 	                 
			        }			        
			    });				  
		},
		loadingD : function() {
			var $elHtml = $("#_loading");
			$elHtml.remove();
			/*$elHtml.fadeOut('0', function() {
				$(this).remove();
			});*/
		},
		building : function(img, elHtml) {
			   var $elHtml = $(elHtml);
			   var title = '';
			   var alertMsgHtml = "<img src='"+img+"' style='margin-left:-3px;' />";
			   var alertHtml;
			   if ($elHtml.length == 0) {
				    elHtml = "#_building";
				    alertHtml = "<div id='_building' title='"+title+"' style='border: 1px solid #000000;'></div>";
				    $elHtml = $(alertHtml);
				    $(document).append($elHtml);
			   } else {
				   $elHtml.attr("title", title);
			   }
			   $elHtml.html(alertMsgHtml);			  
			   $elHtml.dialog({
				     modal: true, 
				     resizable: false,
				     width:700,
				     height:510, 
				     show: {effect:'fade', speed: 800}, 
				     hide: {effect:'fade', speed: 800},	
				     buttons: [
				                  {
				                      text: "닫기",
				                     // className: 'cancelButtonClass',			                     
				                      click: function() {			                        
				                    	  $( this ).dialog( "close" );
				                      }
				                  }
				              ],
			         create: function () {
			           var widget = $(this).dialog('widget');                   
	                   var dialog = $(this);   
	                   $('.ui-dialog-titlebar',widget).remove();	                 
	                   $('.ui-dialog-buttonpane',widget).css('padding',0);
	                   $('.ui-button-text',dialog).css('padding',7);
	                   $('.ui-dialog-buttonpane button',widget).css('margin',2); 	                 
			        }			       
			    });				  
		},
		buildings : function(img, elHtml) {
			   var $elHtml = $(elHtml);
			   var title = '';
			   var alertMsgHtml = "<img src='"+img+"' style='margin-left:-3px;' />";
			   var alertHtml;
			   if ($elHtml.length == 0) {
				    elHtml = "#_building";
				    alertHtml = "<div id='_building' title='"+title+"' style='border: 1px solid #000000;'></div>";
				    $elHtml = $(alertHtml);
				    $(document).append($elHtml);
			   } else {
				   $elHtml.attr("title", title);
			   }
			   $elHtml.html(alertMsgHtml);			  
			   $elHtml.dialog({
				     modal: true, 
				     resizable: false,
				     width:700,
				     height:510, 
				     show: {effect:'fade', speed: 800}, 
				     hide: {effect:'fade', speed: 800},	
				     buttons: [
				                  {
				                      text: "Close",
				                     // className: 'cancelButtonClass',			                     
				                      click: function() {			                        
				                    	  $( this ).dialog( "close" );
				                      }
				                  }
				              ],
			         create: function () {
			           var widget = $(this).dialog('widget');                   
	                   var dialog = $(this);   
	                   $('.ui-dialog-titlebar',widget).remove();	                 
	                   $('.ui-dialog-buttonpane',widget).css('padding',0);
	                   $('.ui-button-text',dialog).css('padding',7);
	                   $('.ui-dialog-buttonpane button',widget).css('margin',2); 	                 
			        }			       
			    });				  
		},
		message : function(widths, heights, msg, elHtml) {
			   var $elHtml = $(elHtml);		
			   var alertMsgHtml = "<table width=100% height=100%><tr><td>"+msg+"</td></tr></table>";
			   var alertHtml, title="";
			   if ($elHtml.length == 0) {
				    elHtml = "#_alert";
				    alertHtml = "<div id='_alert' title='"+title+"'></div>";
				    $elHtml = $(alertHtml);
				    $(document).append($elHtml);
			   } else {
				   $elHtml.attr("title", title);
			   }
			   $elHtml.html(alertMsgHtml);
			   $elHtml.dialog({
				     modal: true, 
				     resizable: false,
				     width:widths,
				     height:heights, 
				     show: {effect:'fade', speed: 800}, 
				     hide: {effect:'fade', speed: 800},     
				     buttons: [
			                  {
			                      text: "확인",
			                     // className: 'cancelButtonClass',			                     
			                      click: function() {			                        
			                    	  $( this ).dialog( "close" );
			                      }
			                  }
			              ],
			         create: function () {
			           var widget = $(this).dialog('widget');                   
	                   var dialog = $(this);   
	                   $('.ui-dialog-titlebar',widget).remove() ;
	                  // widget.css('padding', 3).find('.ui-dialog-titlebar').css('padding', 0);
	                   $('.ui-dialog-buttonpane',widget).css('padding',0).css("background","none"); 
	                   $('.ui-button-text',dialog).css('padding',7);	                 
	                   $('.ui-dialog-buttonpane button',widget).css('margin',5);	                  
			        },
			        open: function(e,ui) { }
			    }).keyup(function(e){   if( e.keyCode == 13 ){    $(this).parent().find('button:nth-child(1)').trigger("click");   } });				  
		},
	}}();