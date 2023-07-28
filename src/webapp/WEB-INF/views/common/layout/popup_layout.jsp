<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
    <tiles:insertAttribute name="constants"/>
    <tiles:insertAttribute name="include"/>
</head>
<body>
    <tiles:insertAttribute name="body" />
</body>
</html>
