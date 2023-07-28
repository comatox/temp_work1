<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

%>
<script>
  function linkMenu(menuId) {
    if (global.menuInfo && global.menuInfo.list) {
      const menuInfo = global.menuInfo.list.find(d => d.menuId === menuId);
      if (menuInfo.url) {
        window.location.href = menuInfo.url;
      } else {
        if (menuInfo.depth === 3) {
          $("div.lnb ul.depth3").removeClass("on");
          $(`div.lnb ul.depth3.\${menuInfo.menuId}`).addClass("on");
        }
      }
    }
  }

  $(document).ready(function () {
    if (global.menuInfo && global.menuInfo.list && global.menuInfo.current.depth2) {
      const depth3Menus = global.menuInfo.list.filter(
          d => d.depth === 3 && d.upMenuId === global.menuInfo.current.depth2.menuId);
      let menuContent = "";
      menuContent += "<h1>" + global.menuInfo.current.depth2.menuNm + "</h1>"
      depth3Menus.map(d3 => {
        menuContent += `<h2 id="\${d3.menuId}" class="depth3_title \${d3.menuId} \${global.menuInfo.current.depth3 && global.menuInfo.current.depth3.subMenuCount === 0 && d3.menuId === global.menuInfo.current.depth3.menuId ? 'on' : ''}"><a href=\"javascript:linkMenu('\${d3.menuId}');">\${d3.menuNm}</a></h2>`;
        menuContent += `<ul class="depth3 \${d3.menuId} \${global.menuInfo.current.depth3 && global.menuInfo.current.depth3.subMenuCount > 0 && d3.menuId === global.menuInfo.current.depth3.menuId ? 'on' : ''}">`;
        const depth4Menus = global.menuInfo.list.filter(d => d.depth === 4 && d.upMenuId === d3.menuId);
        depth4Menus.map(d4 => {
          menuContent += "<li class='deps1 " + (d4.menuId === global.menuInfo.current.menuId ? 'on' : '') + "'><a href=\"javascript:linkMenu('" + d4.menuId + "');\"> " + d4.menuNm + "</a></li>"
        });
        menuContent += "</ul>";
      })
      $("div.lnb").html(menuContent);
      $("div.lnb ul.depth3").on("click", function () {
        $("div.lnb ul.depth3").removeClass("on");
        $(this).addClass("on");
      });
    }
  });
</script>
<div id="leftarea">
    <!-- menu start -->
    <div id="security_gnb">
        <div class="lnb">
        </div>
        <div class="left_banner">
            <ul>
                <li>
                    <a href="javascript: void(0);">영업비밀보호 등 서약서</a>
                </li>
            </ul>
        </div>
    </div>
</div>