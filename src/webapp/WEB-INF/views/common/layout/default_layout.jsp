<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
    <tiles:insertAttribute name="constants"/>
    <tiles:insertAttribute name="include"/>
</head>
<body>
    <tiles:insertAttribute name="header"/>
    <div class="wrap">
        <div class="contentWrap">
            <div id="content_area">
                <tiles:insertAttribute name="sidemenu"/>
                <div id="contentsArea">
                    <tiles:insertAttribute name="body"/>
                </div>
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer"/>
</body>
</html>
