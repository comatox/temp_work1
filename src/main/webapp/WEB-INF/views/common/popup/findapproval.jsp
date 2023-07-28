<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("UTF-8");
    Map login = (Map) request.getSession().getAttribute("Login");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>요청부서 결재자 찾기</title>
    <link rel="stylesheet" type="text/css" href="/esecurity/assets/common/css/ztree/zTreeStyle.css"/>
    <script type="text/javascript" src="/esecurity/assets/common/js/jquery.ztree.all.min.js"></script>
    <script type="text/javascript">
      let tree;
      const menuId = "${param.menuId}";
      let deptEmpList = [];
      let resultList = [];
      let currentDeptId = "";

      $(document).ready(function () {
        setDeptTree();

        const paramRequestList = "${param.savedRequestList}";
        if (paramRequestList) {
          paramRequestList.split(",");
        }
      });

      function setDeptTree() {
        $.esutils.getApi(`/api/common/approval/request/dept/\${global.deptId}`, null, function (result) {
          if (result.data) {
            const _data = result.data.map(d => ({id: d.deptId, pId: d.updeptId, name: d.deptNm, open: true}));
            tree = $.fn.zTree.init($("#ztree"), {
              data: {
                simpleData: {
                  enable: true
                }
              },
              callback: {
                onClick: function (e, id, node) {
                  handleDeptClick(node.id);
                  currentDeptId = node.id;
                }
              }
            }, _data);
          }
        });
      }

      function handleDeptClick(deptId) {
        $.esutils.getApi(`/api/common/approval/request/emp`, {deptId, menuId}, function (result) {
          if (result.data) {
            deptEmpList = result.data;

            $("#requestEmpTable > tbody").empty();
            const html = result.data.map(d => {
              let apprInfoText = "";
              if (d.entrustYn === "Y") {
                apprInfoText = `\${d.entrustEmpNm} \${d.entrustJwNm || ''} (\${d.apprEmpId}) [위임] \${d.apprEmpNm} \${d.apprJwNm || ''}`;
              } else {
                apprInfoText = `\${d.apprEmpNm} \${d.apprJwNm || ''} (\${d.apprEmpId})`;
              }
              return `<tr>
                        <td style="text-align: center;"><input type="checkbox" class="deptEmpCheck" empId="\${d.apprEmpId}" \${resultList.some(d2 => d2.apprEmpId === d.apprEmpId) ? 'checked="checked"' : ''} /></td><td>\${apprInfoText}</td>
                      </tr>`;
            }).join("");
            $("#requestEmpTable > tbody").html(html);

            $("#requestEmpTable .deptEmpCheck").on("change", function () {
              const checked = $(this).prop("checked");
              const empId = $(this).attr("empId");
              if (checked) {
                const empInfo = deptEmpList.find(d => d.apprEmpId === empId);
                if (empInfo) addResultList(empInfo);
              } else {
                removeResultList(empId)
              }
            });
          }
        });
      }

      function addResultList(data) {
        resultList = resultList.concat(data);
        renderResultList();
      }

      function renderResultList() {
        $("#requestListTable tbody").empty();
        const html = resultList.map(data => {
          let apprInfoText = "";
          if (data.entrustYn === "Y") {
            apprInfoText = `\${data.entrustDeptNm} \${data.entrustEmpNm} \${data.entrustJwNm || ''} (\${data.apprEmpId}) -> [위임] \${data.apprEmpNm} \${data.apprJwNm || ''}`;
          } else {
            apprInfoText = `\${data.apprEmpNm} \${data.apprJwNm || ''} (\${data.apprEmpId})`;
          }
          return `<tr apprEmpId="\${data.apprEmpId}">
              <td style="text-align: center;">\${resultList.length}</td>
              <td>\${apprInfoText}</td>
              <td style="text-align: center;"><img src='/esecurity/assets/common/images/icon/ico_order_up.gif' style='cursor:pointer;' onclick="changeSeqUp('\${data.apprEmpId}')" /></td>
              <td style="text-align: center;"><img src='/esecurity/assets/common/images/icon/ico_order_down.gif' style='cursor:pointer;' onclick="changeSeqDown('\${data.apprEmpId}')" /></td>
              <td style="text-align: center;"><img src='/esecurity/assets/common/images/common/ico_sreply_del.gif' style='cursor:pointer;' onclick="removeResultList('\${data.apprEmpId}')" /></td>
            </tr>`
        }).join("");
        $("#requestListTable tbody").html(html);
      }

      function removeResultList(apprEmpId) {
        resultList = resultList.filter(d => d.apprEmpId !== apprEmpId);
        renderResultList();
        handleDeptClick(currentDeptId);
      }

      function changeSeqUp(apprEmpId) {
        const currentOrder = resultList.map(d => d.apprEmpId).indexOf(apprEmpId);
        if (currentOrder > 0) {
          resultList[currentOrder - 1] = resultList.splice(currentOrder, 1, resultList[currentOrder - 1])[0];
          renderResultList();
        }
      }

      function changeSeqDown(apprEmpId) {
        const currentOrder = resultList.map(d => d.apprEmpId).indexOf(apprEmpId);
        if (currentOrder < resultList.length - 1) {
          resultList[currentOrder + 1] = resultList.splice(currentOrder, 1, resultList[currentOrder + 1])[0];
          renderResultList();
        }
      }

      function fn_ok() {
        window.fnParentCallback(resultList);
        window.close();
      }

    </script>
</head>
<body>
<!-- realContents start -->
<div id="realContents">
    <form method="post" id="menuForm" name="menuForm">
        <input type="hidden" id="UP_MENU_ID" name="UP_MENU_ID" value=""/>
        <input type="hidden" id="DEPTH" name="DEPTH" value=""/>
        <input type="hidden" id="menuId" name="menuId" value=""/>
        <input type="hidden" id="empauth" name="empauth" value=""/>
        <input type="hidden" id="schema_nm" name="schema_nm" value=""/>

        <table cellpadding="0" cellspacing="0" border="0">
            <colgroup>
                <col width="10px"/>
                <col width="300px"/>
                <col width="10px"/>
                <col width="*"/>
                <col width="10px"/>
            </colgroup>
            <tr>
                <td></td>
                <td style="vertical-align: top;">
                    <div class="ztree" id="ztree"
                         style="border:1px solid #D8D8D8; line-height:0em; margin:0px; padding-left:10px; padding-top:10px; width: 240px; height: 325px; overflow: auto;">
                    </div>
                </td>
                <td></td>
                <td style="vertical-align: bottom;">
                    <div style="border:1px solid #D8D8D8; padding-top:10px; width: 353px; height: 325px; overflow: auto;">
                        <table cellpadding="0" cellspacing="0" caption="요청부서" border="0" id="requestEmpTable">
                            <caption>요청부서</caption>
                            <colgroup>
                                <col width="10%"/>
                                <col width="90%"/>
                            </colgroup>
                            <tbody></tbody>
                        </table>
                    </div>
                </td>
                <td></td>
            </tr>
            <tr>
                <td height="3px;" colspan="5"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td style="vertical-align: top;" colspan="3">
                    <h2 class="txt_title02">요청부서 결재선</h2>
                    <div style="height: 135px;" class="table_type3 scroll">
                        <table cellpadding="0" cellspacing="0" caption="요청부서" border="0" id="requestListTable">
                            <caption>요청부서</caption>
                            <colgroup>
                                <col width="10%"/>
                                <col width="60%"/>
                                <col width="10%"/>
                                <col width="10%"/>
                                <col width="10%"/>
                            </colgroup>
                            <thead>
                            <tr>
                                <th>순서</th>
                                <th>결재자 정보</th>
                                <th colspan="2">순서 변경</th>
                                <th>삭제</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td style="vertical-align: top;" colspan="3">
                    <!-- 버튼 -->
                    <div class="buttonGroup">
                        <div class="leftGroup">
                            <span class="button bt_s2"><button type="button" style="width: 50px;"
                                                               onclick="javascript:window.close();">닫기</button></span>&nbsp;&nbsp;
                            <span class="button bt_s1"><button type="button" style="width: 50px;"
                                                               onclick="javascript:fn_ok();">확인</button></span>
                        </div>
                    </div>
                    <!-- 버튼 끝 -->
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>