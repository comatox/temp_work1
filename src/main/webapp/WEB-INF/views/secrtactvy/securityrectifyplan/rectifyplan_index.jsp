<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<script type="text/javascript">

  function recifyPlanList() {
    $.esutils.href('/secrtactvy/securityrectifyplan/rectifyplan/list');
  }
</script>

<div class="wrap">
    <!-- contentWrap start-->
    <div class="contentWrap">
        <div id="content_area">
            <div id="contentsArea">
                <img src="/esecurity/assets/common/images/common/subTitle/secrt/tit_043.png"/>
                <!-- realContents start -->
                <div id="realContents">
                    <h1 class="txt_title01">시정계획서 작성 절차는 어떻게 되나요?</h1>
                    <div class="pro_btm">
                        <div class="top"></div>
                        <div class="process_notice">
                            <div class="process_tip"></div>
                            <div class="process_cont">
                                <ul>
                                    <li>건물출입규정 위반시<br/>(카메라 폰 렌즈 미봉인/봉인훼손)</li>
                                    <li>기타 위규 사항 위반시<br/>(물품 반출입 규정 위반/문서 반출 절차 위반 등)</li>
                                </ul>
                            </div>
                        </div>
                        <div class="process01">
                            <img src="/esecurity/assets/common/images/common/plan_process03.png" alt="">
                        </div>
                        <div class="process_contact">
                            <ul>
                                <!--							<li>기타 문의 : SK하이이엔지 최동준 과장 (031-8094-7412)</li><br>-->
                                <!--							<li>기타 문의 : SK하이스텍 안종필 대리 (031-8094-7712)</li><br>-->
                                <!--							<li>기타 문의 : 실리콘화일 안용진 책임 (031-8093-5731)</li>-->
                                <li>기타 문의 : SK 쉴더스 이귀범 수석 (031-8094-7279)</li>
                            </ul>
                        </div>
                    </div>
                    <!-- 버튼 -->
                    <div class="buttonGroup">
                        <div class="leftGroup">
                            <span class="button bt_l2"><button type="button" style="width:80px;" onclick="recifyPlanList();">진행현황</button></span>
                        </div>
                    </div>
                    <!-- 버튼 끝 -->
                </div>
            </div>
        </div>
    </div>
</div>