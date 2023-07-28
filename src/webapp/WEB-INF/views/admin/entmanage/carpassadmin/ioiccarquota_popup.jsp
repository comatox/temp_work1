<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript">
      let gridUtil;
      let deptDivId, type;

      /***************************************************************************
       * 화면 onload 처리
       ***************************************************************************/
      $(document).ready(() => {

        if (urlParam("deptDivId") !== undefined) deptDivId = urlParam("deptDivId");
        if (urlParam("type") !== undefined) type = urlParam("type");

        if (type !== 'M') {
          $("#saveBtn").hide();
        }

        $.esutils.renderData("ioIcCarQuotaForm", "/api/entmanage/carPassAdmin/ioIcCarQuota/view/${param.deptDivId}");
      });

      const urlParam = (name) => {
        let results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results === null) {
          return null;
        } else {
          return decodeURI(results[1]) || 0;
        }
      }

      const fnSave = ()=> {
        const param = {
          deptDivId,
          userInfo : {
            empId: global.empId,
            acIp: global.acIp,
          },
          ...$.esutils.getFieldsValue($("#ioIcCarQuotaForm"))
        }

        $.esutils.postApi('/api/entmanage/carPassAdmin/ioIcCarQuota/update', param, (response) => {
          const msg = response.message;
          if (msg === "OK") {
            alert("방문객 차량 쿼터가 변경 되었습니다.");
            fnCallback();
          }
        });
      }

      const fnCallback = () => {
        if (window.fnParentCallback) {
          window.fnParentCallback();
        }
        window.close();
      }

    </script>
    <title>
        이천 - 방문차량 부서 쿼터 관리
    </title>
</head>
<body>
<!-- popup ::  START -->
<div id="popBody">
    <form id="ioIcCarQuotaForm" name="ioIcCarQuotaForm" method="post">
        <div style="float: left; border: 0px solid black; width:530px; margin-left:10px;">
            <div id="search_area">
                <!-- 검색 -->
                <div class="search_content">
                    <table cellpadding="0" cellspacing="0" summary="조회화면입니다" border="0" class="view_board01">
                        <colgroup>
                            <col width="103px;"/>
                            <col width="107px;"/>
                            <col wdith="103px;"/>
                            <col wdith="107px;"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>회사코드<span class="necessary">&nbsp;</span></th>
                            <td id="deptDivIdHtml"><span id="deptDivId" name="deptDivId" view-data="deptDivId"></span>
                            </td>
                            <th>회사명<span class="necessary">&nbsp;</span></th>
                            <td id="deptDivNmHtml">
                                <input type="text" id="deptDivNm" name="deptDivNm" view-data="deptDivNm" style="width:107px;"/>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <nobr>방문쿼터수<span class="necessary">&nbsp;</span></nobr>
                            </th>
                            <td id="carQtaHtml">
                                <input type="text" id="carQta" name="carQta" view-data="carQta" style="width:107px;" maxLength="12" maxByte="12"/>
                            </td>
                            <th>
                                <nobr>상시쿼터수<span class="necessary">&nbsp;</span></nobr>
                            </th>
                            <td id="regularQtaHtml">
                                <input type="text" id="regularQta" name="regularQta" view-data="regularQta" style="width:107px;" maxLength="12" maxByte="12"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                    <!-- 버튼 -->
                    <div class="searchGroup">
                        <div class="centerGroup">
                            <span class="button bt_l2"><button type="button" style="width:80px;" onclick="javascript: fnCallback();">닫기</button></span>
                            <span class="button bt_l1" id="saveBtn"><button type="button" style="width:80px;" onclick="javascript: fnSave();">저장</button></span>
                        </div>
                    </div>
                    <!-- 버튼 끝 -->
                </div>
            </div>
            <!-- 검색영역 끝 -->
        </div>
    </form>
</div>
</body>
</html>