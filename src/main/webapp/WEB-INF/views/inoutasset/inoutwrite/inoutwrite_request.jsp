<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<script type="text/javascript" src="/esecurity/assets/common/js/inoutasset/inoutwrite.js"></script>
<script type="text/javascript">
  $(document).ready(function () {
    <c:choose>
    <c:when test="${not empty inoutApplNo}">
    inoutApplNo: '${inoutApplNo}'
    </c:when>
    <c:otherwise>
    Inoutwrite.init({inoutApplNo: 37});
    </c:otherwise>
    </c:choose>
  });
</script>

<div id="contentsArea">
    <table width="100%" border="0">
        <tr>
            <td align="left">
                <img src="/esecurity/assets/common/images/common/subTitle/pios/title_169.png"/>
            </td>
            <td align="right">
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_s2">
                            <button type="button" style="width: 50px;" onclick="fn_list();">목록</button>
                        </span>
                        <span class="button bt_s1">
                            <button type="button" style="width: 80px;" onclick="javascript : fn_approval('SAVE');">저장</button>
                        </span>
                        <span class="button bt_s1">
                            <button type="button" style="width: 50px;" onclick="javascript : fn_approval('REPORT');">상신</button>
                        </span>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <img src="/esecurity/assets/common/images/common/line.png" width="100%" height="3"/>
            </td>
        </tr>
    </table>

    <!-- realContents start -->
    <div id="realContents">
        <h1 class="txt_title01 fl">반출입작성 등록</h1>
        <form id="frm" name="frm" method="post" enctype="multipart/form-data">
            <input type="hidden" id="writedate" name="writedate" value=""/>
            <input type="hidden" id="writeseq" name="writeseq" value=""/>
            <input type="hidden" id="inoutApplNo" name="inoutApplNo" value=""/>
            <input type="hidden" id="inoutserialno" name="inoutserialno" value=""/>
            <input type="hidden" id="approvalrequestyn" name="approvalrequestyn" value="0"/>
            <input type="hidden" id="indatedelayknd" name="indatedelayknd" value="0"/>

            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                <colgroup>
                    <col width="14%"/>
                    <col width="36%"/>
                    <col width="14%"/>
                    <col width="36%"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>회사명</th>
                    <td colspan="3" id="compNm"></td>
                </tr>
                <c:choose>
                    <c:when test="${not empty inoutApplNo}">
                        <tr>
                            <th>작성자</th>
                            <td colspan="3" id="empInfo"></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <th>작성자</th>
                            <td id="empInfo"></td>
                            <th>반출입번호</th>
                            <td id="inoutserialnoTxt"></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <th>작성일자</th>
                    <td id="writedateTxt"></td>
                    <th>
                        <label for="empTel">사내번호</label>
                    </th>
                    <td>
                        <input type="text" id="empTel" name="empTel" value=""/>
                    </td>
                </tr>

                <tr>
                    <th>구분</th>
                    <td colspan="3">
                        <input type="radio" name="articlekndno" id="articlekndno1" value="1" class="border_none" checked="checked"><label for="articlekndno1">물품</label>&nbsp;&nbsp;
                        <input type="radio" name="articlekndno" id="articlekndno2" value="2" class="border_none"><label for="articlekndno2">휴대용전산저장장치</label>&nbsp;&nbsp;
                        <input type="radio" name="articlekndno" id="articlekndno3" value="3" class="border_none"><label for="articlekndno3">문서</label>
                        <%--
                            <input type="radio" name="articlekndno" value="5" onclick="fn_articleChk('5')" class="border_none">Wafer/Substrate&nbsp;&nbsp;
                            <input type="radio" name="articlekndno" value="6" onclick="fn_articleChk('6')" class="border_none">원자재/완제품/PKG Chip
                        --%>
                    </td>
                </tr>

                <tr>
                    <th>
                        <label for="documentknd">내용등급</label>
                    </th>
                    <td colspan="3">
                        <select id="documentknd" name="documentknd" style="display: none;"></select>
                    </td>
                </tr>

                <tr>
                    <th>
                        <label for="articlegroupid">그룹</label>
                    </th>
                    <td>
                        <select id="articlegroupid" name="articlegroupid"></select>
                    </td>
                    <th>
                        <label for="prno">PRNo, 문서번호</label>
                    </th>
                    <td>
                        <input type="text" id="prno" name="prno" value="" maxlength="22" style="display: none;"/>
                    </td>
                </tr>

                <tr>
                    <th>
                        <label for="companyno">반출사업장</label>
                    </th>
                    <td>
                        <select id="companyno" name="companyno">
                            <option value="1101000001">이천</option>
                            <option value="1102000001">청주1</option>
                            <option value="1130000001">원자재통합자재창고1</option>
                            <option value="1105000001">청주2</option>
                            <option value="1106000001">청주3</option>
                            <option value="1109000001">청주4</option>
                            <option value="1110000001">원자재통합자재창고2</option>
                            <%--
                                <option value="1103000001">영동</option>
                                <option value="1107000001">분당사무소(서현)</option>
                            --%>
                            <option value="1108000001">분당사무소(정자)</option>
                            <option value="1601000001">서울센터원</option>
                            <%--
                                <option value="1131000001">청주완제품창고</option>
                            --%>
                        </select>
                    </td>
                    <th>
                        <label for="returncompanyareaknd">최종반입사업장</label>
                    </th>
                    <td>
                        <select id="returncompanyareaknd" name="returncompanyareaknd" style="display: none;">
                            <option value="">선택하세요.</option>
                            <option value="101">이천</option>
                            <option value="102">청주1</option>
                            <option value="130">원자재통합자재창고1</option>
                            <option value="105">청주2</option>
                            <option value="106">청주3</option>
                            <option value="109">청주4</option>
                            <option value="110">원자재통합자재창고2</option>
                            <option value="108">분당사무소(정자)</option>
                            <option value="601">서울센터원</option>
                            <%--
                                <option value="103">영동</option>
                                <option value="107">분당사무소(서현)</option>
                                <option value="131">청주완제품창고</option>
                            --%>
                        </select>
                    </td>
                </tr>

                <tr>
                    <th>
                        <label for="outcompanyknd">상대처구분</label>
                    </th>
                    <td colspan="3">
                        <select id="outcompanyknd" name="outcompanyknd">
                            <option value="1">자사사업장</option>
                            <option value="2">외부업체</option>
                            <%-- <option value="3">해외법인</option> --%>
                        </select>
                    </td>
                </tr>

                <tr>
                    <th>
                        <label for="mycompanyno">상대처</label>
                    </th>
                    <td>
                        <select id="mycompanyno" name="mycompanyno">
                            <option value="">선택하세요.</option>
                            <option value="1101000001">이천</option>
                            <option value="1102000001">청주1</option>
                            <option value="1130000001">원자재통합자재창고1</option>
                            <option value="1105000001">청주2</option>
                            <option value="1106000001">청주3</option>
                            <option value="1109000001">청주4</option>
                            <option value="1110000001">원자재통합자재창고2</option>
                            <option value="1108000001">분당사무소(정자)</option>
                            <option value="1601000001">서울센터원</option>
                            <option value="1102999999">이천_비상저류조</option>
                            <%--
                                <option value="1103000001">영동</option>
                                <option value="1107000001">분당사무소(서현)</option>
                                <option value="1131000001">청주완제품창고</option>
                            --%>
                        </select>&nbsp;
                        <select id="foreignComp" name="foreignComp" style="display: none;">
                        </select>&nbsp;

                        <input type="hidden" id="systempartnerid" name="systempartnerid" value=""/>
                        <input type="text" id="systempartnernm" name="systempartnernm" value="" style="display: none;"/>&nbsp;
                        <a href="fnPartner();" class="btn_type01" id="systempartnernmBtn" style="display: none;">
                            <span>확인</span>
                        </a>
                    </td>
                    <th>
                        <label for="outcompanydeptNm" id="outcompanydeptNmLabel">상대처 부서</label>
                    </th>
                    <td>
                        <input type="hidden" id="outcompanydeptCd" name="outcompanydeptCd"/>
                        <input type="text" id="outcompanydeptNm" name="outcompanydeptNm"/>&nbsp;
                        <a href="fnDept();" id="outcompanydeptBtn">
                            <span>확인</span>
                        </a>&nbsp;
                        <input type="text" id="partnerEtcNm" name="partnerEtcNm" style="display: none;"/>
                    </td>
                </tr>

                <%--
                    <tr>
                        <th>
                            <label for="sendYn">파견/주재원</label>
                        </th>
                        <td>
                            <input type="checkbox" id="sendYn" name="sendYn" value="Y" class="border_none" onclick="fn_sendYnChk();" /> 파견/주재원 반출시에만 선택하세요.
                        </td>
                        <th>
                            <label for="id02">증빙서류</label>
                        </th>
                        <td id="sendFileTd">
                            <input type="file" id="fileToUploadSend" name="fileToUploadSend" style="display: none;" />
                        </td>
                    </tr>
                --%>

                <tr>
                    <th>
                        <label for="inoutknd">반출구분</label>
                    </th>
                    <td>
                        <select id="inoutknd" name="inoutknd"></select>
                    </td>
                    <th>
                        <label for="indate">반입예정일자</label>
                    </th>
                    <td id="indateTd">
                        <input type="text" id="indate" name="indate" maxlength="10"/>&nbsp;
                    </td>
                </tr>

                <tr id="outreasonidTr">
                    <th>
                        <label for="outreasonid">반출사유</label>
                    </th>
                    <td>
                        <select id="outreasonid" name="outreasonid"></select>
                    </td>
                    <th>
                        <label for="outreasonsubknd">반출상세사유</label>
                    </th>
                    <td>
                        <select id="outreasonsubknd" name="outreasonsubknd" style="display: none;">
                            <option value="">선택하세요.</option>
                            <option value="1">Pump</option>
                            <option value="2">세정</option>
                            <option value="3">Anodizing</option>
                            <option value="4">MFC</option>
                            <option value="5">Gauge류</option>
                            <option value="6">Generator</option>
                            <option value="7">Chiller</option>
                            <option value="8">Matcher</option>
                            <option value="9">RF Generator</option>
                            <option value="10">Board류</option>
                            <option value="11">Part 기타</option>
                            <option value="12">가공</option>
                        </select></td>
                </tr>

                <tr>
                    <th>
                        <label for="inoutetc" id="inoutEtcTitle">기타상세<br/>반출사유</label>
                    </th>
                    <td colspan="3">
                        <textarea id="inoutetc" name="inoutetc" style="height: 60px;"></textarea>
                    </td>
                </tr>
                <tr id="copUrlTr">
                    <th>
                        <label for="copurl">CoP<br/>등록여부<br/>(URL입력)</label>
                    </th>
                    <td colspan="3">
                        <textarea id="copurl" name="copurl" style="height: 60px;"></textarea>
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 물품내역 --%>
            <h1 class="txt_title01">물품내역 등록</h1>
            <font color="red">※등록한 물품의 일괄 반출만 가능합니다.반출일자가 다를 경우 반출입증을 따로따로 작성해 주세요</font>
            <div style="float: right; margin-bottom: 0px; margin-top: -25px;">
                <span class="button bt2">
                    <button type="button" id="btn_addRow">물품내역 추가</button>
                </span>
            </div>

            <div id="grid" style="width: 100%">
                <div class="table_type3">
                    <table id="Jlist_1" border="0" style="display: none;">
                        <thead id="JHead_1">
                        <tr>
                            <th>그룹을 먼저 선택하세요.</th>
                        </tr>
                        </thead>
                        <tbody id="JBody_1"></tbody>
                    </table>
                    <table id="Jlist_2" border="0" style="display: none;">
                        <thead>
                        <tr>
                            <th>그룹을 먼저 선택하세요.</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>

            <div id="approvalWrap"></div>
        </form>

        <!-- 버튼 -->
        <div class="buttonGroup">
            <div class="leftGroup">
                <span class="button bt_s2">
                    <button type="button" class="btn_list">목록</button>
                </span>&nbsp;
                <span class="button bt_s1">
                    <button type="button" class="btn_save">저장</button>
                </span>&nbsp;
                <span class="button bt_s1">
                    <button type="button" class="btn_report">상신</button>
                </span>
            </div>
        </div>
        <!-- 버튼 끝 -->
    </div>
    <!-- realContents end -->

</div>