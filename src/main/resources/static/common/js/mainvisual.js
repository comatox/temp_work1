
$(document).ready(function(){
    var _size=0;
	var eventsize=0;
	var prevevent = 0;
	var _time =6000;
	var _intervalId=0;
	var _eventintervalId =0;
	var _eventTime;

	var eventcur=0;

	var menuId=0;

	var maxW=362;
	var minW=146;
	 var currentevnum =0;
    
	var evtchk=0;
	var chk=0;

	init();
    addEvents();

	onOver(0);
	timerHandler('start');
	eventtimerHandler('start');
	
   
    function init(){
		_size =  $("#visual>li").size();

		
    }
    function addEvents(){
       $("#visual>li").bind("mouseenter",function(evt){
           var idx = $(this).index(); 
           onOver(idx);
       });  
		$("#visual>li").bind("mouseleave",function(evt){
			 var idx = $(this).index();
			 onOut(idx);
		});


    }



	function onOver(idx){      
		
		timerHandler('stop');
		eventtimerHandler('stop');
		
		
		onMove(idx);
	}
	function onOut(idx){      

		menuId = idx;

		if(eventsize>1){
			if( idx != 0 ){
				timerHandler('start');
			}else{
				// eventtimerHandler('start');
			}
		}
		else{
			timerHandler('start');
		}

	}

	function onMove(idx){
		for(var i=0; i<_size; i++){
			if(i==idx){
				onItemOver(i);
			}
			else{
				onItemOut(i);
			}
		}
	}
    
    function onItemOver(idx){

		
		var $this = $("ul#visual>li").eq(idx);
		var prop = $this.attr("class");
		$this.stop();

		var p_prop = "p."+prop+"_txt";
		var div_prop = "div."+prop + "_box";

		$this.find(p_prop).fadeOut("fast");
		$this.find(div_prop).fadeIn("fast");
		
		

		$this.animate({width:maxW},400,function(){
			$(this).css("overflow","visible");
		});
		
    }

    function onItemOut(idx){
	
      var $this = $("ul#visual>li").eq(idx);
      var prop = $this.attr("class");
	
	  var p_prop = "p."+prop+"_txt";
	
		var div_prop = "div."+prop + "_box";
        $this.stop();
		$this.animate({width:minW},400);

		$this.find(p_prop).fadeIn("fast");
		$this.find(div_prop).fadeOut("fast");
		
	}
	

	function timerHandler(state){
		if(state == 'start'){
			
			_intervalId = setInterval(function(){
				
				menuId++;
				if(menuId>=3){
					menuId = 0;
				}

				if(eventsize>1){
					if(menuId==0){
						timerHandler('stop');
						clearInterval(_intervalId);
						onMove(menuId);
						eventtimerHandler('start');
					}
					else{
						
						onMove(menuId);
					}

				}
				else{
					onMove(menuId);
				}
				
			}, _time);
		} 
		else {
			clearInterval(_intervalId);
		}
	}

	function eventtimerHandler(state){

		if(state == 'start'){
			eventtimerHandler( "stop" );
			evtchk = currentevnum;

			_eventintervalId = setInterval(function(){
				
				evtchk++;
				
				if(evtchk == eventsize){
					evtchk = 0;
					eventtimerHandler('stop');
					timerHandler('start');
					menuId++;
					onMove( menuId );
				}
				//onSlide(evtchk);
				
			
			}, _time);
		} 
		else {
			clearInterval(_eventintervalId);
		}
	}

});