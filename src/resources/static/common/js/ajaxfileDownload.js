
jQuery.extend({
	

    createIframe: function(id, uri)
	{
			//create frame
            var frameId = 'jFrame' + id;
            var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" src="' + uri + '" style="position:absolute; top:-9999px; left:-9999px"';
            if(window.ActiveXObject)
			{
                if(typeof uri== 'boolean'){
					iframeHtml += ' src="' + 'javascript:false' + '"';

                }
                else if(typeof uri== 'string'){
					iframeHtml += ' src="' + uri + '"';

                }	
			}
			iframeHtml += ' />';
			jQuery(iframeHtml).appendTo(document.body);

            return jQuery('#' + frameId).get(0);			
    },
    createForm: function(id, fileName, fileId, filePath, nc_oper)
	{
		//create form	
		var formId = 'jForm' + id;
		var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '"  enctype="multipart/form-data"></form>');	
		jQuery('<input type="hidden" id="fileName" name="fileName" value="' + fileName + '" />').appendTo(form);
		jQuery('<input type="hidden" id="fileId" name="fileId" value="' + fileId + '" />').appendTo(form);
		jQuery('<input type="hidden" id="filePath" name="filePath" value="' + filePath + '" />').appendTo(form);
		jQuery('<input type="hidden" id="nc_oper" name="nc_oper" value="' + nc_oper + '" />').appendTo(form);
		
		//set attributes
		jQuery(form).css('position', 'absolute');
		jQuery(form).css('top', '-1200px');
		jQuery(form).css('left', '-1200px');
		jQuery(form).appendTo('body');		
		return form;
    },

    ajaxfileDownload : function(s) {
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime();        
		var form = jQuery.createForm(id, s.fileName, s.fileId, s.filePath, s.nc_oper);
		var io = jQuery.createIframe(id, s.uri);
		var frameId = 'jFrame' + id;
		var formId = 'jForm' + id;		
        // Watch for a new set of requests
        if ( s.global && ! jQuery.active++ )
		{
			jQuery.event.trigger( "ajaxStart" );
		}            
        var requestDone = false;
        // Create the request object
        var xml = {};   
        if ( s.global )
            jQuery.event.trigger("ajaxSend", [xml, s]);
        // Wait for a response to come back
        var downloadCallback = function(isTimeout)
		{			
			var io = document.getElementById(frameId);
            try 
			{				
				if(io.contentWindow)
				{
					 xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                	 xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
                	 
                	 //alert(xml.responseText);
                	 //alert(xml.responseXML);
				}else if(io.contentDocument)
				{
					xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
				}
            }catch(e)
			{
				jQuery.handleError(s, xml, null, e);
			}
            if ( xml || isTimeout == "timeout") 
			{				
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" )
					{
                        // process the data (runs the xml through httpData regardless of callback)
                    	//alert(xml);
                    	//alert(s.dataType);
                    	// alert(data);
                        var data = jQuery.downloadHttpData( xml, s.dataType );   
                        
                        // If a local callback was specified, fire it and pass it the data
                        if ( s.success )
                            s.success( data, status );
    
                        // Fire the global callback
                        if( s.global )
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                        jQuery.handleError(s, xml, status);
                } catch(e) 
				{
                    status = "error";
                    jQuery.handleError(s, xml, status, e);
                }

                // The request was completed
                if( s.global )
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );

                // Process result
                if ( s.complete )
                    s.complete(xml, status);

                jQuery(io).unbind();

                setTimeout(function()
									{	try 
										{
											jQuery(io).remove();
											jQuery(form).remove();	
											
										} catch(e) 
										{
											jQuery.handleError(s, xml, null, e);
										}									

									}, 100);

                xml = null;

            }
        }
        // Timeout checker
        if ( s.timeout > 0 ) 
		{
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ) uploadCallback( "timeout" );
            }, s.timeout);
        }
        try 
		{

			var form = jQuery('#' + formId);
			jQuery(form).attr('action', s.url);
			jQuery(form).attr('method', 'POST');
			jQuery(form).attr('target', frameId);
	
            jQuery(form).submit();

        } catch(e) 
		{			
            jQuery.handleError(s, xml, null, e);
        }
		
		jQuery('#' + frameId).load( downloadCallback );
        return {abort: function () {}};	

    },

    downloadHttpData: function( r, type ) {
    	
        if(type == "xml") 
        	data = r.responseXML;
        
        if(type == "text") 
        	data = r.responseText;
        
        // If the type is "script", eval it in global context
        if ( type == "script" )
            jQuery.globalEval( data );
        // Get the JavaScript object, if JSON is used.
        if ( type == "json" )
            eval( "data = " + data );
        // evaluate scripts within html
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();

        return data;
    }
})

