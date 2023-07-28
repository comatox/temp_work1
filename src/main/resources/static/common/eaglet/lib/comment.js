// FileName - comment.js

// ================================================================================================================================================================================================
// ActiveX Activating Script
// ------------------------------------------------------------------------------------------------
// Make Date : 2006.08.29
// Developer : Hyuk, Yang
// ------------------------------------------------------------------------------------------------
// Example   :
// <script language="Javascript" src="/lib/comment.js"></script>
// <comment id='__cid__'>
// <object classid="clsid:DC18BB59-CAC7-43DB-BCA7-748B1508AED8" codebase="/cabs/IMPIXupload.cab#version=1,0,0,1" width="722" height="685" id="CtlABM"></object>
// </comment>
// <script language="Javascript">WriteControl(__cid__);</script>
// ================================================================================================================================================================================================

function WriteControl(target)
{

        document.write(target.innerHTML);
        target.id="";
}