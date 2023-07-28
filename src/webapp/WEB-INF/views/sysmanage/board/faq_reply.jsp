<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<style type="text/css">
  .file_input_textbox {
    float: left
  }

  .file_input_div {
    position: relative;
    width: 80px;
    height: 23px;
    overflow: hidden;
    margin-top: 0px;
  }

  .file_input_hidden {
    font-size: 25px;
    position: absolute;
    right: 0px;
    top: 0px;
    opacity: 0;
    filter: alpha(opacity=0);
    -ms-filter: "alpha(opacity=0)";
    -khtml-opacity: 0;
    -moz-opacity: 0;
  }
</style>

<!--CK에디터-->
<script type="text/javascript" src="/esecurity/assets/common/js/ckeditor/ckeditor.js"></script>
<!--CK에디터-->

<script type="text/javascript">
  let upBoardNo = '';
  let inoutGbn = '';
  let replyTitle = '';

  $(document).ready(function () {
    upBoardNo = "${param.boardNo}";

    if (upBoardNo) {
      $.esutils.renderData(
          "faqReplyForm",
          `/api/sysmanage/faq/detail/\${upBoardNo}`,
          (data) => {
            replyTitle = '[답글] ' + data.title;
            inoutGbn = data.inoutGbn;

            $('#title').val(replyTitle);
            $('#titleTd').text(replyTitle);
            $('#crtBy').text(global.empId);
          }, {loading: true});
    }
  });

  /***************************************************************************
   * 저장
   ***************************************************************************/
  const faqSave = function () {
    if (!validationCheck()) return;
    if (!confirm("저장 하시겠습니까?")) return;

    const formObject = document.getElementById('faqReplyForm');

    $.esutils.multipartSubmit(
        '/api/sysmanage/faq/reply',
        formObject,
        function (formData) {
          formData.set('title', replyTitle);
          formData.set('content', $('#content').val());
          formData.set('upBoardNo', upBoardNo);
          formData.set('acIp', global.acIp);
          formData.set('crtBy', global.empId);
          formData.set('inoutGbn', inoutGbn);
          return formData;
        },
        function () {
          boardList();
        }
    )
  };

  /***************************************************************************
   * 데이터 체크
   ***************************************************************************/
  const validationCheck = function () {
    if ($("#title").val() == '') {
      alert("제목을 입력하지 않았습니다.");
      $("#title").focus();
      return false;
    }

    document.faqReplyForm.content.value = CKEDITOR.instances.ir1.getData();
    document.faqReplyForm.contentUrl.value = "<%--<%=setUrl%>--%>";

    return true;
  };
  /***************************************************************************
   * 목록
   ***************************************************************************/
  const boardList = function () {
    $.esutils.href('/sysmanage/board/faq/list')
  };
</script>

<div class="wrap">
    <!-- contentWrap start-->
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <!-- <h1 class="title">산업보안 공지사항 답글</h1> -->
                <img src="/eSecurity/common/images/common/subTitle/board/tit_319.png"/>
                <!-- realContents start -->
                <div id="realContents">
                    <form id="target_form" name="target_form" method="post"></form>
                    <form id="faqReplyForm" name="faqReplyForm" method="post">
                        <input type="hidden" name="content" id="content"/>
                        <input type="hidden" name="contentUrl" id="contentUrl"/>
                        <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                            <tbody>
                            <tr>
                                <th width="127px">작성자명</th>
                                <td width="700px" id="crtBy">
                                </td>
                            </tr>
                            <tr>
                                <th width="127px">제목</th>
                                <td id="titleTd">
                                    <input type="hidden" id="title" name="title" value="">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">
                                    <textarea name="ir1" id="ir1" rows="10" cols="100" style="width:850px; height:570px;display:none;"></textarea>
                                    <!-- CK에디터 -->
                                    <script>CKEDITOR.replace('ir1');</script><!-- CK에디터 -->

                                </td>
                            </tr>
                            <tr>
                                <th width="127px">첨부파일</th>
                                <td colspan="3">
                                    <form id="fileUploadForm" name="fileUploadForm" method="post" enctype="multipart/form-data">
                                        <input type="text" name="noticeFiles" id="noticeFiles" class="file_input_textbox" value="" readonly="readonly"/>
                                        <div class="file_input_div" style="float: left;margin-left:5px;">
                                            <span class="button bt2">
                                                <button style="width:80px;" type="button">파일첨부</button>
                                            </span>
                                            <input type="file" class="file_input_hidden" id="noticeFile" name="fileToUpload"
                                                   onchange="document.getElementById('noticeFiles').value = this.value"/>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <!-- 버튼 -->
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s2"><button type="button" style="width:50px;" onclick="boardList();" tabindex="8">목록</button></span>
                        <span class="button bt_s1"><button type="button" style="width:50px;" onclick="faqSave();" tabindex="6">작성</button></span>
                    </div>
                </div>
                <!-- 버튼 끝 -->
            </div>
        </div>
    </div>
</div>