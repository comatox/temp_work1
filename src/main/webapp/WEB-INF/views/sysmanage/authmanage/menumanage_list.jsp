<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

  let menuList = [];
  let maxMenuDepth = 0;
  let menuId = "";
  let oldClickMenuId = "";
  let isClicked = false;
  const listImage = "/esecurity/assets/common/images/EnvrSetup/MenuManage/list.gif";
  const closeImage = "/esecurity/assets/common/images/EnvrSetup/MenuManage/closed.gif";

  $(document).ready(() => {

    $.esutils.getApi('/api/sysmanage/authManage/menuManage/list', {}, fnCallback, {});

  });

  const fnCallback = (res) => {

    if (res?.data && Array.isArray(res.data.list)) {
      menuList = res.data.list;
      maxMenuDepth = res.data.maxDepth;

      fnBuildTree();
    }
  }

  const fnBuildTree = () => {
    let oldMenuDepth, newMenuDepth, subMenuCount = 0;
    let menuNm = "";
    let html = "";

    if (menuList !== null && menuList.length > 0) {
      for (let i = 0; i < menuList.length; i++) {
        newMenuDepth = Number(menuList[i]["depth"]);
        subMenuCount = Number(menuList[i]["subMenuCount"]);

        menuId = menuList[i]["menuId"];
        menuNm = menuList[i]["menuNm"];

        if (newMenuDepth < oldMenuDepth) {
          for (let j = 0; j < oldMenuDepth - newMenuDepth; j++) {
            html += "</ul>";
            html += "</li>";
          }
        }

        if (subMenuCount > 0) {
          html += "<li id='" + menuId + "'>";
          html += "<img src=\"" + closeImage + "\") align=\"top\" /><span id=\"" + menuId + "_span\" onclick='fnMenuClick(\"" + menuId + "\")'>" + menuNm + "</span>";
          html += "<ul id='" + menuId + "_ul'>";
        } else {
          html += "<li id='" + menuId + "'>";
          html += "<img src=\"" + listImage + "\" align=\"top\" id=\"" + menuId + "_radio\" /><span id=\"" + menuId + "_span\" onclick='fnMenuClick(\"" + menuId + "\")'>" + menuNm + "</span>";
          html += "</li>";
        }
        oldMenuDepth = newMenuDepth;
      }
      for (let i = 1; i < maxMenuDepth; i++) {
        html += "</ul>";
        html += "</li>";
      }
    }

    $("#P").append(html);
    ddtreemenu.createTree("P", true);
  }

  const fnMenuClick = (currentMenuId) => {

    isClicked = false;
    $("#" + currentMenuId + "_span").get(0).style.fontWeight = 'bold';
    if (oldClickMenuId !== currentMenuId && $("#" + oldClickMenuId + "_span").get(0)) {
      $("#" + oldClickMenuId + "_span").get(0).style.fontWeight = '';
    }
    oldClickMenuId = currentMenuId;

    $.esutils.getApi('/api/sysmanage/authManage/menuManage/detail', {menuId: currentMenuId}, (response) => {

      if (response) {
        const data = response.data;

        $('#menuId').val(data.menuId);
        $('#menuNm').val(data.menuNm);
        $('#url').val(data.url);
        $('#sortSeq').val(data.sortSeq);
        $('#depth').val(data.depth);
        $('#upMenuId').val(data.upMenuId);
        $('#upMenuNm').html(data.upMenuNm);
        $('#imgNm').val(data.imgNm);
        $('input:radio[name=useYn]:input[value=' + data.useYn + ']').attr("checked", true);
        $('input:radio[name=displayYn]:input[value=' + data.displayYn + ']').attr("checked", true);
        $('input:radio[name=approvalYn]:input[value=' + data.approvalYn + ']').attr("checked", true);
      }

    }, {});

  }

  const fnInsertForm = () => {
    let menuId = $('#menuId').val();
    let depth = $('#depth').val();

    if (menuId === null || menuId === "") {
      alert('왼쪽 메뉴트리에서 상위 메뉴를 먼저 선택하세요.');
    } else if (depth === "4") {
      alert('메뉴는 4레벨이 마지막입니다.');
    } else if (isClicked) {
      alert('현재 메뉴를 저장한 후에 하위 메뉴를 추가할 수 있습니다.');
    } else {

      $.esutils.getApi('/api/sysmanage/authManage/menuManage/list', {}, fnCallback, {});
      $.esutils.postApi('/api/sysmanage/authManage/menuManage/newCode', {upMenuId: $('#menuId').val()}, (response) => {
        let data = response.data;
        if (!data.menuId) {
          $('#menuId').val(menuId + "01");
          $('#sortSeq').val(1);
        } else {
          $('#menuId').val(data.menuId);
          $('#sortSeq').val(data.sortSeq);
        }

        let depth = Number($('#depth').val()) + 1;
        $('#depth').val(depth);

        $('#upMenuId').val(menuId);

        $('#upMenuNm').html($('#menuNm').val());

        $('#menuNm').val('');
        $('#url').val('');
        $('#imgNm').val('');

        isClicked = true;
      }, {});
    }
  }

  const fnSave = () => {

    let upMenuId = $("#upMenuId").val();
    let menuId = $('#menuId').val();
    let menuNm = $('#menuNm').val();
    let sortSeq = $('#sortSeq').val();
    let useYn = "";

    $("input[name='useYn']").each((index, value) => {
      if ($(value).attr("checked") === "checked") {
        useYn = $(value).attr("value");
      }
    });

    let displayYn = "";
    $("input[name='displayYn']").each((index, value) => {
      if ($(value).attr("checked") === "checked") {
        displayYn = $(value).attr("value");
      }
    });

    if (upMenuId === null || upMenuId === "") {
      alert("왼쪽 트리에서 상위 메뉴를 먼저 선택하세요.");
      return false;
    } else if (menuId === null || menuId === "") {
      alert("메뉴 ID는 필수 항목 입니다.");
      $("#menuId").focus();
      return false;
    } else if (menuNm === null || menuNm === "") {
      alert("메뉴 명은 필수 항목 입니다.");
      $("#menuNm").focus();
      return false;
    } else if (sortSeq === null || sortSeq === "") {
      alert("정렬순서는 필수 항목 입니다.");
      $("#sortSeq").focus();
      return false;
    } else if (useYn === null || useYn === "") {
      alert("사용 유무는 필수 항목 입니다.");
      return false;
    } else if (displayYn === null || displayYn === "") {
      alert("화면노출 여부는 필수 항목 입니다.");
      return false;
    } else {

      const formParam = $.esutils.getFieldsValue($('#menuForm'));
      $.esutils.postApi('/api/sysmanage/authManage/menuManage/save', formParam, (response) => {

        const msg = response.message;
        if (msg === "OK") {

          alert('저장되었습니다.');
          isClicked = false;
          fnMenuClick(menuId);

        } else {
          alert("저장 중 에러가 발생하였습니다.");
        }
      });
    }
  }

  function handlerNum(obj) {
    //숫자만 입력 받게끔 하는 함수.
    e = window.event; //윈도우의 event를 잡는것입니다.

    //입력 허용 키
    if ((e.keyCode >= 48 && e.keyCode <= 57) ||   //숫자열 0 ~ 9 : 48 ~ 57
        (e.keyCode >= 96 && e.keyCode <= 105) ||   //키패드 0 ~ 9 : 96 ~ 105
        e.keyCode === 8 ||    //BackSpace
        e.keyCode === 46 ||    //Delete
        //e.keyCode === 110 ||    //소수점(.) : 문자키배열
        //e.keyCode === 190 ||    //소수점(.) : 키패드
        e.keyCode === 37 ||    //좌 화살표
        e.keyCode === 39 ||    //우 화살표
        e.keyCode === 35 ||    //End 키
        e.keyCode === 36 ||    //Home 키
        e.keyCode === 9       //Tab 키
    ) {
      if (e.keyCode === 48 || e.keyCode === 96) { //0을 눌렀을경우
        if (obj.value === '0') //현재 값이 0일 경우에서 0을 눌렀을경우
          e.returnValue = false; //입력되지않는다.
        else //다른숫자뒤에오는 0은
          return;
      } else //0이 아닌숫자
        return;
    } else {
      e.returnValue = false;
    }
  }
</script>
<form method="post" id="menuForm" name="menuForm">
    <input type="hidden" id="upMenuId" name="upMenuId" value=""/>
    <input type="hidden" id="depth" name="depth" value=""/>
    <input type="hidden" id="acIp" name="acIp" value="<%= request.getRemoteAddr() %>"/>

    <table cellpadding="0" cellspacing="0" border="0">
        <colgroup>
            <col width="500px"/>
            <col width="20px"/>
            <col/>
        </colgroup>
        <tr><!-- 수정반영2용 -->
            <td style="vertical-align: top; padding-top: 2px;">
                <div>
                    <span class="button bt1"><button type="button" style="width:100px;" onclick="javascript:ddtreemenu.flatten('P', 'expand');">모두 펼치기</button></span>
                    <span class="button bt2"><button type="button" style="width:100px;" onclick="javascript:ddtreemenu.flatten('P', 'contact');">모두 닫기</button></span>
                </div>
                <div style="border:1px solid #D8D8D8; padding:10px; width: 500px; height: 325px; overflow: auto;">
                    <ul id="P" class="treeview"></ul>
                </div>
            </td>
            <td>&nbsp;</td>
            <td valign="top">
                <table style="margin-top:33px;" cellpadding="0" cellspacing="0" border="0" class="view_board">
                    <colgroup>
                        <col width="25%"/>
                        <col width="75%"/>
                    </colgroup>
                    <tr>
                        <th>상위 메뉴</th>
                        <td id="upMenuNm"></td>
                    </tr>
                    <tr>
                        <th>메뉴 ID</th>
                        <td><input type="text" name="menuId" id="menuId" size="30" maxlength="9" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <th>메뉴명</th>
                        <td><input type="text" name="menuNm" id="menuNm" size="30" maxlength="40"/></td>
                    </tr>
                    <tr>
                        <th>연결URL</th>
                        <td><input type="text" name="url" id="url" size="30" maxlength="200" style="ime-mode:disabled;"/></td>
                    </tr>
                    <tr>
                        <th>정렬순서</th>
                        <td><input type="text" name="sortSeq" size="30" id="sortSeq" onKeydown='javascript:handlerNum(this)'/></td>
                    </tr>
                    <tr>
                        <th>이미지</th>
                        <td><input type="text" name="imgNm" size="30" id="imgNm"/></td>
                    </tr>
                    <tr>
                        <th>사용여부</th>
                        <td>
                            <input type="radio" name="useYn" value="Y" class="border_none"/>사용
                            <input type="radio" name="useYn" value="N" class="border_none"/>미사용
                        </td>
                    </tr>
                    <tr>
                        <th>화면노출</th>
                        <td>
                            <input type="radio" name="displayYn" value="Y" class="border_none"/>사용
                            <input type="radio" name="displayYn" value="N" class="border_none"/>미사용
                        </td>
                    </tr>
                    <tr>
                        <th>결재유무</th>
                        <td>
                            <input type="radio" name="approvalYn" value="Y" class="border_none"/>사용
                            <input type="radio" name="approvalYn" value="N" class="border_none"/>미사용
                        </td>
                    </tr>
                </table>
                <!-- 버튼 -->
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s2"><button type="button" style="width: 80px;" onclick="javascript : fnInsertForm();">추가</button></span>
                        <span class="button bt_s1"><button type="button" style="width: 80px;" onclick="javascript : fnSave();">저장</button></span>
                    </div>
                </div>
                <!-- 버튼 끝 -->
            </td>
        </tr>
    </table>
</form>
