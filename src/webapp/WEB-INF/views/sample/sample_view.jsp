<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">


  $(document).ready(function () {
    var num = <c:out value="${rnum}"/>
  });

  function fn_goList() {
    sessionStorage.setItem("sampleFromList", "Y");
    location.href = "${pageContext.request.contextPath}/sample/list";
  }

</script>

<div id="content_area">
    <div id="contentsArea">
        <img src="/esecurity/assets/common/images/common/subTitle/envrSetup/tit_324.png"/>
        <!-- realContents start -->
        <div id="realContents">
            <span>rnum : ${rnum}</span>
        </div>
        <span class="button bt_l1"><button type="button"
                                           style="width:80px;"
                                           onclick="javascript:fn_goList();">목록으로</button></span>
    </div>
    <input type="hidden" id="rnum" value="${rnum}">
</div>
<div style="padding-bottom: 50px; height : 30px;"></div>