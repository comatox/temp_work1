<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
  const menuIdArray = [];
  $(document).ready(function () {

    // 기본 값 세팅
    defaultValueSet();

    // 좌측 권한 리스트
    $.esutils.getApi('/api/sysmanage/authManage/authList', {authKnd : "1", useYn : "Y"}, function(result) {
      // render
      renderAuthList(result?.data);
    });

    // 우측 메뉴리스트 트리
    $.esutils.getApi('/api/sysmanage/authManage/authMenuList', {}, function (result) {
      const {authMenuList, maxMenuDepth} = result?.data;
      // render
      renderTreeMenuList(authMenuList, maxMenuDepth);

      //메뉴 ID 저장
      if (authMenuList?.length > 0) {
        for (let i = 0; i < authMenuList.length; i++) {
          menuIdArray.push(authMenuList[i].menuId);
        }
      }
    });

  });

  /**********************
   * [권한 리스트 그리기]
   **********************/
  function renderAuthList(authList) {

    let _html = "";
    if (authList?.length > 0) {
      // seq sort
      authList.sort((a,b) => {
        return a.seq - b.seq;
      })

      for (let i = 0; i < authList.length; i++) {
        _html += '<tr>';
        _html +=    '<td class="first center">';
        _html +=        '<input type="radio" name="authBtn" class="border_none" onclick="onClickAuthMenu(\'' + authList[i].authId + '\')"  />';
        _html +=    '</td>';
        _html +=    '<td class="left">' + authList[i].authNm + '</td>';
        _html +=    '<td class="left">' + authList[i].authRmrk + '</td>';
        _html += '<tr>';
      }
    }

    $("#authListContent").append(_html);
  }

  /**********************
   * [메뉴 리스트 그리기]
   **********************/
  function renderTreeMenuList(authMenuList, maxMenuDepth) {

    const listImage = "/esecurity/assets/common/images/EnvrSetup/MenuManage/list.gif";
    const closeImage = "/esecurity/assets/common/images/EnvrSetup/MenuManage/closed.gif";

    let oldMenuDepth = 0;
    let newMenuDepth = 0;
    let subMenuCount = 0;

    let menuId = "";
    let menuNm = "";

    let _html = "";

    if(authMenuList?.length > 0) {
      for(let i=0; i<authMenuList.length; i++) {
        newMenuDepth = authMenuList[i].depth;
        subMenuCount = authMenuList[i].subMenuCount;
        menuId = authMenuList[i].menuId;
        menuNm = authMenuList[i].menuNm;

        if(newMenuDepth < oldMenuDepth) {
          for(let j=0;j<oldMenuDepth - newMenuDepth;j++) {
            _html +="</ul>";
            _html +="</li>";
          }
        }

        if(subMenuCount > 0) {
          _html += "<li id='" + menuId + "'>";
          _html +=      "<input type=\"checkbox\" class=\"border_none\" onclick=\"javascript:onClickMenuCheckBox('"+menuId+"')\" id=\"authCheck_"+menuId+"\" name=\"authCheck\" value=\""+menuId+"\" />";
          _html +=      "<img src=\"" + closeImage + "\" align='top' />";
          _html +=      menuNm;
          _html +=          "<ul>";
        }
        else {
          _html += "<li id='" + menuId + "'>";
          _html +=      "<input type=\"checkbox\" class=\"border_none\" onclick=\"javascript:onClickMenuCheckBox('"+menuId+"')\" id=\"authCheck_"+menuId+"\" name=\"authCheck\" value=\""+menuId+"\" />";
          _html +=      "<img src=\"" + listImage + "\" align='top' />";
          _html +=      menuNm;
          _html += "</li>";
        }
        oldMenuDepth = newMenuDepth;
      }
      for(let l=1;l<maxMenuDepth;l++) {
        _html += "</ul>";
        _html += "</li>";
      }
    }

    $("#P").append(_html);
    ddtreemenu.createTree("P", true);
  }

  /************************
   * [메뉴 트리 체크박스 클릭]
   ************************/
  function onClickMenuCheckBox(menuId) {
    //$('#menuId').val(menuId);
    if (menuIdArray.length > 0) {
      let menuIdLength = menuId.length;
      var checkBoxId = "#authCheck_" + menuId;
      var checked = $(checkBoxId).attr('checked');
      for ( var i = 0; i < menuIdArray.length; i++) {
        if (menuId == menuIdArray[i].substring(0, menuIdLength)) {
          checkBoxId = "#authCheck_" + menuIdArray[i];
          if (checked) {
            $(checkBoxId).attr('checked', true);
          } else {
            $(checkBoxId).attr('checked', false);
          }
        }
      }
    }
  }

  /*****************
   * [저장버튼 클릭]
   *****************/
  function save() {
    const authId = $('#authId').val().replace(/^\s+|\s+$/g, "");
    const crtBy = $('#crtBy').val();
    const modBy = $('#modBy').val();
    const acIp = $('#acIp').val();

    if (!authId) {
      alert('저장할 권한을 먼저 선택하세요.');
      return;
    }

    if (!confirm("저장하시겠습니까?")) {
      return;
    }

    const updateList = [];
    if (menuIdArray?.length > 0) {
      for (let i = 0; i < menuIdArray.length; i++) {
        if ($("#authCheck_" + menuIdArray[i]).is(':checked')) {
          updateList.push({authId, crtBy, modBy, acIp, menuId : menuIdArray[i]})
        }
      }
    }

    $.esutils.postApi("/api/sysmanage/authManage/saveAuthMenuManage", {authId, updateList, empId : crtBy}, function(res) {
      if(res) {
        alert("저장하였습니다");
      }
      else {
        alert("저장 중 오류가 발생하였습니다.");
      }
    });

  }

  /*****************************
   * [좌측 권한 라디오버튼 체크시]
   *****************************/
  function onClickAuthMenu(authId) {
    $('#authId').val(authId);
    $.esutils.getApi('/api/sysmanage/authManage/authMenuList', {authId}, function (result) {
      const {authMenuList} = result?.data;
      if (authMenuList?.length > 0) {
        for (let i = 0; i < authMenuList.length; i++) {
          let checkBoxId = "#authCheck_" + authMenuList[i].menuId;
          let menuAuthYn = authMenuList[i].menuAuthYn;
          if (menuAuthYn === "Y") {
            $(checkBoxId).attr('checked', true);
          }
          else {
            $(checkBoxId).attr('checked', false);
          }
        }
      }
    });
  }

  /***************
   * [기본값 세팅]
   ***************/
  function defaultValueSet() {
    $("#acIp").val(global.acIp);
    $("#crtBy").val(global.empId);
    $("#modBy").val(global.empId);
  }

</script>

<div id="contentsArea">
    <!-- <h1 class="title">권한 메뉴 관리</h1> -->
    <img src="/esecurity/assets/common/images/common/subTitle/envrSetup/tit_330.png"/>
    <!-- realContents start -->
    <div id="realContents">
        <form method="post" id="menuForm" name="menuForm">
            <input type="hidden" id="authId" name="authId" value=""/>
            <input type="hidden" id="acIp" name="acIp" value=""/>
            <input type="hidden" id="crtBy" name="crtBy" value=""/>
            <input type="hidden" id="modBy" name="modBy" value=""/>
            <table cellpadding="0" cellspacing="0" border="0">
                <colgroup>
                    <col/>
                    <col width="20px"/>
                    <col width="500px"/>
                </colgroup>
                <tr>
                    <td style="vertical-align: top; padding-top: 2px;">
                        <div class="table_type1 cb">
                            <table cellpadding="0" cellspacing="0" border="0" style="width:420px;">
                                <colgroup>
                                    <col width="10%"/>
                                    <col width="45%"/>
                                    <col width="45%"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th scope="col">선택</th>
                                    <th scope="col">권한명</th>
                                    <th scope="col">권한설명</th>
                                </tr>
                                </thead>
                                <tbody id="authListContent"></tbody>
                            </table>
                            <!-- 버튼 -->
                            <div class="buttonGroup">
                                <div class="leftGroup">
                                    <span class="button bt_s1">
                                        <button type="button" style="width: 50px;" onclick="javascript:save();">저장</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </td>
                    <td valign="top">
                        <div style="margin-left: 25px;">
                            <span class="button bt1">
                                <button type="button" style="width: 100px;" onclick="javascript:ddtreemenu.flatten('P', 'expand');">모두펼치기</button>
                            </span>
                            <span class="button bt2">
                                <button type="button" style="width: 100px;" onclick="javascript:ddtreemenu.flatten('P', 'contact');">모두닫기</button>
                            </span>
                        </div>
                        <div style="border: 1px solid #D8D8D8; margin-left: 25px; padding: 0px; width: 400px; height: 900px; overflow: auto;">
                            <ul id="P" class="treeview"></ul>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>