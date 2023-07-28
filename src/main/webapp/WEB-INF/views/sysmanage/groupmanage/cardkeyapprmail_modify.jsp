<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@environment.getProperty('ifaccess.welcome.url')" var="welcomeUrl"/>
<script type="text/javascript" src="/esecurity/assets/common/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
  $(document).ready(function () {
      renderData();
  });

  function renderData() {
    const compId = $("[name=compId]").val();
    const emailGbn = $("[name=emailGbn]").val();

    $.esutils.renderData(
        "secEduNotiEmailMng",
        "/api/sysmanage/secedumail/detail?compId="+compId+"&emailGbn="+emailGbn,
        (data) => {
          const content = data ? data.content : "";
          $("[name=contents]").val(content);
          CKEDITOR.instances['ir1'].setData(content);
        }, {loading: true});
  }

  function fn_save() {
    const editorData = CKEDITOR.instances.ir1.getData();
    $("[name=content]").val(editorData);
    $("[name=contenturl]").val("${welcomeUrl}");

    const param = $.esutils.getFieldsValue($("#secEduNotiEmailMng"));
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
<img src="/esecurity/assets/common/images/common/subTitle/envrSetup/tit_336.png"/>
<form id="secEduNotiEmailMng" name="secEduNotiEmailMng" method="post">
    <input type="hidden" name="callback" value = "" />
    <input type="hidden" name="compId" value = "SYSTEMMAI3" />
    <input type="hidden" name="emailGbn" value = "Z0481006" />
    <input type="hidden" name="content" id="content"/>
    <input type="hidden" name="contents" value=""> <!-- 기존 컨텐트를 저장 -->
    <input type="hidden" name="contenturl" id="contenturl"/>


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