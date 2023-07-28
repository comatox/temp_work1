<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
  <!--
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

  -->
</style>

<!--CK에디터-->
<script type="text/javascript" src="/esecurity/assets/common/js/ckeditor/ckeditor.js"></script>
<!--CK에디터-->

<script type="text/javascript">
  /***************************************************************************
   * 내/외부망 코드처리
   ***************************************************************************/
  $.esutils.renderCode([{type: "select", grpCd: "Z037", targetId: "inoutGbn"}], function () {
  });

  $(document).ready(function () {
    $('#crtBy').text(global.empId);
    $('#acIp').val(global.acIp);
    $('#empId').val(global.empId);
  });

  /***************************************************************************
   * 저장
   ***************************************************************************/
  const noticeSave = function () {
    if (!validationCheck()) return;
    if (!confirm("저장 하시겠습니까?")) return;

    const formObject = document.getElementById('noticeRequestForm');

    $.esutils.multipartSubmit(
        '/api/sysmanage/notice/request',
        formObject,
        function (formData) {
          const inoutGbn = formData.get('inoutGbn');

          if (inoutGbn === 'Z0370003') {
            formData.set('inoutGbn', 'INOUT');
          } else if (inoutGbn === 'Z0370002') {
            formData.set('inoutGbn', 'OUT');
          } else {
            formData.set('inoutGbn', 'IN');
          }

          return formData;
        },
        function () {
          boardList();
        },
    )
  };

  /***************************************************************************
   * 데이터 체크
   ***************************************************************************/
  const validationCheck = function () {
    if ($('#title').val() === '') {
      alert("제목을 입력하지 않았습니다.");
      $('#title').focus();
      return false;
    }

    document.noticeRequestForm.content.value = CKEDITOR.instances.ir1.getData();

    if ($('#content').val().length === 0) {
      alert("내용을 작성하지 않았습니다.");
      $('#content').focus();
      return false;
    }

    return true;
  };

  /***************************************************************************
   * 목록
   ***************************************************************************/
  const boardList = function () {
    $.esutils.href("/sysmanage/board/notice/list");
  };

</script>

<div class="wrap">
    <!-- contentWrap start-->
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <!-- <h1 class="title">산업보안 공지사항 작성</h1> -->
                <img src="/eSecurity/common/images/common/subTitle/board/tit_319.png"/>
                <!-- realContents start -->
                <div id="realContents">
                    <form id="target_form" name="target_form" method="post"></form>
                    <form id="noticeRequestForm" name="noticeRequestForm" method="post" enctype="multipart/form-data">
                        <input type="hidden" id="content" name="content"/>
                        <input type="hidden" name="contenturl" id="contenturl"/>
                        <input type="hidden" id="acIp" name="acIp"/>
                        <input type="hidden" id="empId" name="empId"/>
                        <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                            <tbody>
                            <tr>
                                <th width="127px">작성자명</th>
                                <td width="263px" id="crtBy"></td>
                                <th width="127px">내/외부망</th>
                                <td width="263px">
                                    <select id="inoutGbn" name="inoutGbn" style="width:80px;"></select>
                                </td>
                            </tr>
                            <tr>
                                <th width="127px">제목<span class="necessary"></span></th>
                                <td colspan="3"><input type="text" id="title" name="title" value="" maxlength="100" style="width:605px;"/></td>
                            </tr>
                            <tr>
                                <!--<th width="127px">내용<span class="necessary">&nbsp;</span></th>-->
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
                                            <span class="button bt2"><button style="width:80px;" type="button">파일첨부</button></span>
                                            <input type="file" class="file_input_hidden" id="noticeFile" name="fileToUpload"
                                                   onchange="document.getElementById('noticeFiles').value = this.value"/>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <!-- 버튼 -->
                        <div class="buttonGroup">
                            <div class="leftGroup">
                                <span class="button bt_s2"><button type="button" style="width:50px;" onclick="boardList();" tabindex="8">목록</button></span>
                                <span class="button bt_s1"><button type="button" style="width:50px;" onclick="noticeSave();" tabindex="6">작성</button></span>
                            </div>
                        </div>
                        <!-- 버튼 끝 -->
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>