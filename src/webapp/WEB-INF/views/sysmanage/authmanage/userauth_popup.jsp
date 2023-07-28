<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<html>
<head>
    <script>
      let gridUtil;
      $(document).ready(function () {
        //기본 데이터 세팅
        defaultDataSet();

        // popup close
        onPopupClose();

        // 권한 저장 이벤트
        onAuthSave();

        gridUtil = new GridUtil({
          url: "/api/sysmanage/authManage/selectUserUseAuthList"
          , isPaging: false
          , gridOptions: {
            width: 630
            , colData: [
              {
                headerName: "권한명"
                , field: "authNm"
                , width: "50%"
              },
              {
                headerName: "권한상세"
                , field: "authRmrk"
                , width: "50%"
              },
              {
                headerName: ""
                , field: "authId"
                , hidden: true
              }
            ]
            , loadComplete: function (data) {
              // 체크박스 데이터에 의해 체크
              if (data.rows.length > 0) {
                for (let i = 0; i < data.rows.length; i++) {
                  if (data.rows[i].checkCnt > 0) {
                    $(this).jqGrid('setSelection', i+1, true);
                  }
                }
              }
            }
            , isRowDataCheckBox: true
          }
          , search: {
            formId: "userForm"
          }
        });

        gridUtil.init();
      });

      // get Param By QueryString
      const urlParam = (name) => {
        let results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results === null) {
          return null;
        } else {
          return decodeURI(results[1]) || 0;
        }
      }

      /*******************************
       * [부모창에서 넘어온 데이터 세팅]
       *******************************/
      function defaultDataSet() {
        $("#searchEmpId").val(urlParam("searchEmpId"));
        $("#authEmpId").val(urlParam("searchEmpId"));
        $("#managementKnd").val(urlParam("managementKnd"));
        $("#authKnd").val(urlParam("authKnd"));
        $("#acIp").val(global.acIp);
      }

      /*******************************
       * [권한 저장]
       *******************************/
      function onAuthSave() {
        $("#save").on("click", function () {
          if (confirm("권한을 부여하시겠습니까?")) {

            const selectedRowData = gridUtil.getChkCheckedDatas();
            const param = {...($.esutils.getFieldsValue($("#userForm"))), selectedRows: selectedRowData, empId: global.empId};

            $.esutils.postApi("/api/sysmanage/authManage/saveUserAuthManage", param, function (result) {
              if (result) {
                alert("권한을 부여하였습니다.");
                window.fnParentCallback();
                window.close();
              } else {
                alert("권한 부여 중 오류가 발생하였습니다.\n관리자에게 문의해주십시오.");
              }
            });
          }
        });
      }

      /*************
       * [팝업 닫기]
       *************/
      function onPopupClose() {
        $("#close").on("click", function () {
          window.close();
        });
      }
    </script>
</head>
<body>
<div id="popBody">
    <div id="popArea">
        <div class="pop_title"><h1>사용자 권한 관리</h1></div>
    </div>
    <div class="pop_content">
        <form name="userForm" id="userForm" method="post">
            <input type="hidden" id="searchEmpId" name="searchEmpId"/>
            <input type="hidden" id="authEmpId" name="authEmpId"/>
            <input type="hidden" id="managementKnd" name="managementKnd"/>
            <input type="hidden" id="authKnd" name="authKnd"/>
            <input type="hidden" id="authUseYn" name="authUseYn" value="Y"/>
            <input type="hidden" id="crtBy" name="crtBy" value=""/>
            <input type="hidden" id="acIp" name="acIp" value=""/>
        </form>
        <h1 class="txt_title01">부여 권한 목록</h1>
        <div id="grid"></div>
        <!-- 버튼 -->
        <div class="searchGroup">
            <div class="centerGroup">
                <span class="button bt_l1"><button id="close" type="button" style="width:80px;">닫기</button></span>
                <span class="button bt_l1"><button id="save" type="button" style="width:80px;">저장</button></span>
            </div>
        </div>
        <!-- 버튼 끝 -->
    </div>
</div>
</body>
</html>