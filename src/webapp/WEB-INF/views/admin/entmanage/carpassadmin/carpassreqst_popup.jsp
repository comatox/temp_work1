<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript">
      let gridUtil;
      let carPassReqstView = {};
      let carPassReqstUserList = [];

      /***************************************************************************
       * 화면 onload 처리
       ***************************************************************************/
      $(document).ready(() => {

        let vstcarApplNo, docId;
        if (urlParam("vstcarApplNo") !== undefined) vstcarApplNo = urlParam("vstcarApplNo");
        if (urlParam("docId") !== undefined) docId = urlParam("docId");

        $.esutils.postApi("/api/entmanage/carPassReqst/view", {vstcarApplNo, docId}, (response) => {
          const {carPassReqstView = {}, carPassReqstUserList = []} = response;
          $.esutils.renderData("carPassReqstForm", carPassReqstView);
          initGrid(carPassReqstUserList);
          gridUtil.init();
        });

      });

      /***************************************************************************
       * Grid initializing
       ***************************************************************************/
      const initGrid = (data) => {
        gridUtil = new GridUtil({
          userData: data
          , isPaging: false
          , gridOptions: {
            width: 900,
            colData: [
              {
                headerName: "출입자명"
                , field: "empNm"
              },
              {
                headerName: "출입업체명"
                , field: "compNm"
              },
              {
                headerName: "차종"
                , field: "carKnd"
              },
              {
                headerName: "차량번호"
                , field: "carNo"
              },
              {
                headerName: "휴대전화"
                , field: "telNo"
              },
            ]
          }
        });
      }

      const urlParam = (name) => {
        let results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results === null) {
          return null;
        } else {
          return decodeURI(results[1]) || 0;
        }
      }

      const fnClose = () => {
        window.close();
      }

    </script>
    <title>
        방문객차량 출입현황 상세보기
    </title>
</head>
<body>
<!-- popup ::  START -->
<div id="popBody">
    <form id="carPassReqstForm" name="carPassReqstForm" method="post">
        <input type="hidden" id="vstcarApplNo" name="vstcarApplNo" value=""/>
        <h1 class="txt_title01">담당자 정보</h1>
        <table cellpadding="0" cellspacing="0" border="0" class="view_board">
            <colgroup>
                <col width="14%"/>
                <col width="36%"/>
                <col width="14%"/>
                <col width="36%"/>
            </colgroup>
            <tbody>
            <tr>
                <th>담당자명</th>
                <td view-data="empNm" name="empNm"></td>
                <th>담당자 이메일</th>
                <td view-data="empEmail" name="empEmail"></td>
            </tr>
            <tr>
                <th>회사명</th>
                <td view-data="compNm" name="compNm"></td>
                <th>부서명</th>
                <td view-data="deptNm" name="deptNm"></td>
            </tr>
            </tbody>
        </table>
        <h1 class="txt_title01">방문 정보</h1>
        <table cellpadding="0" cellspacing="0" border="0" class="view_board">
            <colgroup>
                <col width="14%"/>
                <col width="36%"/>
                <col width="14%"/>
                <col width="36%"/>
            </colgroup>
            <tbody>
            <!-- <tr>
                <th>방문업체</th>
                <td colspan="3" view-data="IO_COMP_NM" name="IO_COMP_NM"></td>
            </tr> -->
            <tr>
                <th>출입기간</th>
                <td colspan="3" view-data="ioDt" name="ioDt"></td>
            </tr>
            <tr>
                <th>주차장소</th>
                <td view-data="vstPlcNm" name="vstPlcNm"></td>
                <th>방문목적</th>
                <td view-data="vstObjNm" name="vstObjNm"></td>
            </tr>
            <tr>
                <th>방문사유</th>
                <td colspan="3" view-data="vstRsn" name="vstRsn"></td>
            </tr>
            </tbody>
        </table>
        <h1 class="txt_title01">방문업체 차량 상세정보</h1>
        <div id="grid" style="width:100%"></div>
        <!-- 버튼 시작 -->
        <div class="searchGroup">
            <div class="centerGroup">
                <span class="button bt_l1"><button type="button" style="width:80px;" onclick="javascript:fnClose()">닫기</button></span>
            </div>
        </div>
        <!-- 버튼 종료 -->
    </form>
</div>
</body>
</html>