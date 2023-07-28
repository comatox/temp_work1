<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--스마트에디터-->
<script type="text/javascript" src="/eSecurity/common/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<!--스마트에디터-->

<script type="text/javascript">
  let boardNo = '';
  let title = '';
  let upBoardNoCnt = '';

  $(document).ready(function () {
    boardNo = "${param.boardNo}";
    if (boardNo) {
      $.esutils.renderData(
          "noticeDetailForm",
          `/api/sysmanage/notice/detail/\${boardNo}`,
          (data) => {
            title = data.title;
            upBoardNoCnt = data.upBoardNoCnt;

            $("#content > pre").html(data.content);
            if (data.filePath) {
              const filePath = data.filePath;
              const fileName = data.fileName;

              //파일이 있는 경우
              let html = "";
              html += '<span class="button bt_s2">';
              html += '<button onclick="fileDownload(\'' + filePath + ',' + fileName + '\')" style="width:80px;" type="button">첨부파일</button>';
              html += '</span>';
              $('#attachFile').append(html);
            } else {
              //파일이 없는 경우
              let html = "";
              html += '<p>첨부파일 없음.</p>';
              $('#attachFile').append(html);
            }
          }, {loading: true});
    }

    setRead(boardNo);
  });

  /***************************************************************************
   * 조회처리
   ***************************************************************************/
  const setRead = function (boardNo) {
    $.esutils.postApi('/api/sysmanage/notice/readUp', {boardNo}, (response) => {
      console.log("success", response);
    }, {});
  };

  /***************************************************************************
   * 목록
   ***************************************************************************/
  const boardList = function () {
    $.esutils.href('/sysmanage/board/notice/list')
  };

  /***************************************************************************
   * 답글
   ***************************************************************************/
  const insertReply = function () {
    $.esutils.href("/sysmanage/board/notice/reply", {boardNo});
  };

  /***************************************************************************
   * 수정
   ***************************************************************************/
  const boardModify = function () {
    console.log('boardNo', boardNo);
    $.esutils.href("/sysmanage/board/notice/modify", {boardNo});
  };

  /***************************************************************************
   * 삭제
   ***************************************************************************/
  const boardDelete = function () {
    if (upBoardNoCnt > 0) {
      alert('답글이 있습니다. 삭제할 수 없습니다.');
      return;
    }

    if (!confirm("삭제 하시겠습니까?")) return;

    $.esutils.postApi('/api/sysmanage/notice/delete', {boardNo}, (response) => {
      console.log("success", response);
      alert("삭제되었습니다.");
    }, {});

    boardList();
  };

  /***************************************************************************
   * 파일다운
   ***************************************************************************/
  const fileDownload = function (filePath) {
    $.esutils.fileDownload(filePath.split(',')[0], filePath.split(',')[1]);
  }
</script>

<div class="wrap">
    <!-- contentWrap start-->
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <!-- <h1 class="title">산업보안 공지사항 상세보기</h1> -->
                <img src="/eSecurity/common/images/common/subTitle/board/detail/title_054.png"/>
                <!-- realContents start -->
                <div id="realContents">
                    <form id="target_form" name="target_form" method="post"></form>
                    <form id="noticeDetailForm" name="noticeDetailForm" method="post">
                        <input type="hidden" name="contents" value="${result.content}"> <!-- 기존 컨텐트를 저장 -->
                        <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                            <tbody>
                            <tr>
                                <th width="127px">작성자</th>
                                <td id="crtNm" view-data="crtBy" width="263px">
                                </td>
                                <th width="127px">작성일</th>
                                <td id="crtDtm" view-data="crtDtm" width="263px">
                                </td>
                                <th width="127px">조회수</th>
                                <td id="readCnt" view-data="readCnt" width="263px">
                                </td>
                            </tr>
                            <tr>
                                <th width="127px">제목</th>
                                <td colspan="5" id="title" view-data="title">
                                </td>
                            </tr>
                            <tr>
                                <th width="127px">내용</th>
                                <td id="content" colspan="6" height="300px">
                                    <pre></pre>
                                </td>
                            </tr>
                            <tr>
                                <th width="127px">첨부파일</th>
                                <td colspan="5" id="attachFile"></td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <!-- 버튼 -->
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s2"><button type="button" style="width:50px;" onclick="boardList();" tabindex="8">목록</button></span>
                        <%--                        <c:if test="${admYn eq 'Y'}">--%>
                        <span class="button bt_s1" id="insertbutton1"><button type="button" style="width:50px;" onclick="insertReply();" tabindex="7">답글</button></span>
                        <span class="button bt_s1" id="insertbutton2"><button type="button" style="width:50px;" onclick="boardModify();" tabindex="6">수정</button></span>
                        <span class="button bt_s1" id="insertbutton3"><button type="button" style="width:50px;" onclick="boardDelete();" tabindex="9">삭제</button></span>
                        <%--                        </c:if>--%>
                    </div>
                </div>
                <!-- 버튼 끝 -->
                <!--  </form> -->
            </div>
        </div>
    </div>
</div>
