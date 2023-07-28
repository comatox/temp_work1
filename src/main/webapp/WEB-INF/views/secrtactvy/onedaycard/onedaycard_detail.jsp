<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script type="text/javascript">
  $(document).ready(function () {
    const empcardApplNo = "${param.empcardApplNo}";

    $.esutils.renderData("cardViewForm",
        '/api/entmanage/empCard/admOnedayCard/' + empcardApplNo,
        (data) => {
        }, {loading: true}
    )
  });

  /**********************************
   * 목록
   **********************************/
  const cardList = function () {
    $.esutils.href('/secrtactvy/onedaycard/list')
  };
</script>

<!-- wrap start-->
<div class="wrap">
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <table width="100%" border="0">
                    <tr>
                        <td align="left">
                            <img src="/esecurity/assets/common/images/common/subTitle/pass/title_138.png" alt="일일사원증 발급"/>
                        </td>
                        <td align="right">
                            <div class="buttonGroup">
                                <div class="leftGroup">
									<span class="button bt_s2">
										<button type="button" style="width: 50px;" onclick="cardList();">목록</button>
									</span>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><img src="/esecurity/assets/common/images/common/line.png" width="100%" height="3"/></td>
                    </tr>
                </table>
                <div id="realContents">
                    <form id="cardViewForm" name="cardViewForm" method="post">
                        <div id="apprContents">
                            <h1 class="txt_title01 fl">신청대상자</h1>
                            <table cellpadding="0" cellspacing="0" border="0" class="view_board">
                                <tbody>
                                <tr>
                                    <th><label for="empGbnNm">소속 구분</label></th>
                                    <td colspan=3 id="empGbnNm" view-data="empGbnNm"></td>
                                </tr>
                                <tr>
                                    <th><label for="mpEmpNm">성명</label></th>
                                    <td colspan=3 id="mpEmpNm" view-data="mpEmpNm"></td>
                                </tr>
                                <tr>
                                    <th><label for="mpCompNm">부서명/업체명</label></th>
                                    <td id="mpCompNm" view-data="mpCompNm"></td>
                                    <th><label for="rcvCompNm">출입 사업장</label></th>
                                    <td id="rcvCompNm" view-data="rcvCompNm"></td>
                                </tr>
                                <tr>
                                    <th><label for="applyGbnNm">발급 구분</label></th>
                                    <td colspan=3 id="applyGbnNm" view-data="applyGbnNm"></td>
                                </tr>
                                <tr>
                                    <th><label for="cardNo">카드 번호</label></th>
                                    <td colspan=3 id="cardNo" view-data="cardNo"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="buttonGroup">
                            <div class="leftGroup">
                                <span class="button bt_s2">
                                    <button type="button" style="width: 50px;" onclick="cardList();">목록</button>
                                </span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>