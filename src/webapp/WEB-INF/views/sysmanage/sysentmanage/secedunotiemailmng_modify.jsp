<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@environment.getProperty('ifaccess.welcome.url')" var="welcomeUrl"/>
<script type="text/javascript" src="/esecurity/assets/common/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
  $(document).ready(function () {
    $.esutils.renderCode([
      {type: "select", grpCd: "A030", targetId: "compId", valueProp: "etc1"},
    ], function () {
      const compId = "1101000001"; // 사용자 compId 확인 필요
      $("[name=compId]").val(compId);
      renderData(compId, $("[name=emailGbn]").val());
    });

    $("[name=crtBy]").val(global.empId);

    // change events
    $("[name=compId]").on("change", function () {
      renderData($(this).val(), $("[name=emailGbn]:checked").val());
    });
    $("[name=emailGbn]").on("change", function () {
      renderData($("[name=compId]").val(), $(this).val());
    });
  });

  function renderData(compId, emailGbn) {
    $.esutils.renderData(
        "formSecEduNotiEmailMng",
        `/api/sysmanage/secedumail/detail?compId=\${compId}&emailGbn=\${emailGbn}`,
        (data) => {
          const content = data ? data.content : "";
          $("[name=contents]").val(content);
          CKEDITOR.instances['ir1'].setData(content);
        }, {loading: true, exclude: ["emailGbn", "compId"]});
  }

  function fn_save() {
    const editorData = CKEDITOR.instances.ir1.getData();
    $("[name=content]").val(editorData);
    $("[name=contenturl]").val("${welcomeUrl}");

    const param = $.esutils.getFieldsValue($("#formSecEduNotiEmailMng"));
    console.log("param >>> ", param);

    if (!param.title) {
      alert("제목을 입력하세요.");
      $("#title").focus();
      return;
    }

    if (!confirm("저장 하시겠습니까?")) return;

    $.esutils.postApi("/api/sysmanage/secedumail/update", param, function (result) {
      if (result) {
        alert("저장 되었습니다.");
      } else {
        alert("저장중 에러가 발생하였습니다.");
      }
    });
  };
</script>
<form id="formSecEduNotiEmailMng" name="formSecEduNotiEmailMng" method="post">
    <input type="hidden" name="content" id="content"/>
    <input type="hidden" name="contents" value=""> <!-- 기존 컨텐트를 저장 -->
    <input type="hidden" name="contenturl" id="contenturl"/>
    <input type="hidden" name="crtBy" id="crtBy"/>

    <table cellpadding="0" cellspacing="0" caption="입력화면입니다." border="0" class="view_board" style="table-layout: fixed;">
        <caption>입력화면입니다</caption>
        <colgroup>
            <col style="width:12%;"/>
            <col style="width:38%;"/>
            <col style="width:12%;"/>
            <col style="width:38%;"/>
        </colgroup>
        <tbody>
        <tr>
            <th>사업장구분<span class="necessary">&nbsp;</span></th>
            <td>
                <select id="compId" name="compId" style="width: 150px;"></select>
            </td>
            <th>구분<span class="necessary">&nbsp;</span></th>
            <td>
                <input type="radio" id="emailGbnZ0481001" name="emailGbn" value="Z0481001" class="border_none" checked="checked"/><label for="emailGbnZ0481001">외부인</label>
                <input type="radio" id="emailGbnZ0481002" name="emailGbn" value="Z0481002" class="border_none"/><label for="emailGbnZ0481002">대표관리자</label>
                <input type="radio" id="emailGbnZ0481003" name="emailGbn" value="Z0481003" class="border_none"/><label for="emailGbnZ0481003">접수구성원</label>
                <%--<input type="radio" id="EMAIL_GBN_MAILSEND" name="EMAIL_GBN" value="MAILSEND" <%= EMAIL_GBN.equals("MAILSEND")
                        ? "checked='checked'"
                        : "" %> class="border_none" style="display:none;"/><label for="EMAIL_GBN_MAILSEND" style="display:none;">메일발송</label>
                <input type="radio" id="EMAIL_GBN_EDU_MAIL" name="EMAIL_GBN" value="EDU_MAIL" <%= EMAIL_GBN.equals("EDU_MAIL")
                        ? "checked='checked'"
                        : "" %> class="border_none" style="display:none;"/><label for="EMAIL_GBN_EDU_MAIL" style="display:none;">교육메일발송</label>--%>
            </td>
        </tr>
        <tr>
            <th>제목<span class="necessary">&nbsp;</span></th>
            <td colspan="3"><input type="text" id="title" name="title" maxlength="200" style="width: 98%;"/></td>
        </tr>
        <tr>
            <td colspan="4" height="450px">
                <!-- Active Designer를 실제로 추가하는 부분입니다. 반드시 API 문서를 읽어본 후, 적절히 설정하시기 바랍니다. -->
                <textarea name="ir1" id="ir1" rows="10" cols="100" style="width:850px; height:600px;display:none;"></textarea>
                <!-- CK에디터 -->
                <script>CKEDITOR.replace('ir1', {
                  toolbar: 'MyToolBar'

                });</script><!-- CK에디터 -->
                <!-- Active Designer 추가 끝 -->
            </td>
        </tr>
        </tbody>
    </table>

    <!-- 버튼 -->
    <div class="buttonGroup">
        <div class="leftGroup">
            <span class="button bt_l1"><button type="button" style="width:50px;" onclick="javascript: fn_save();">저장</button></span>
        </div>
    </div>

</form>