<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- contentWrap start-->
<div class="contentWrap">
    <div id="content_area">
        <c:import url="/eSecurity/insNet/Main/insNetLeft.jsp" charEncoding="utf-8"></c:import>
        <div id="contentsArea">
            <!-- <h1 class="title">임시출입차량 신청 절차안내</h1> -->
            <img src="/esecurity/assets/common/images/common/subTitle/pios/tit_100.png"/>
            <!-- realContents start -->
            <div id="realContents">
                <h1 class="txt_title01">자산반출입 절차는 어떻게 되나요?</h1>

                <div class="pro_btm">
                    <div class="top"></div>
                    <div class="process_notice">
                        <div class="process_tip"></div>
                        <div class="process_cont">
                            <ul>
                                <li>1. 반입예정일 3일 경과 시 반입지연함 이동</li>
                                <li>2. 반출 신청 후 2주 이내 반출되지 않으면 자동 삭제됨</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process01">
                        <h1 class="txt_title01">반출 時</h1>
                        <img src="/esecurity/assets/common/images/common/out_process01.png">
                        <h1 class="txt_title01">재 반입</h1>
                        <img src="/esecurity/assets/common/images/common/out_process02.png">

                    </div>

                    <div class="process_contact">
                        <ul>
                            <li>* 문의 : SK쉴더스 이홍순 TL 031.5185-2294</li>
                        </ul>
                    </div>

                </div>
                <!-- 버튼 -->
                <div class="buttonGroup">
                    <div class="leftGroup">
                        <span class="button bt_l1"><button type="button" style="width:120px;" onclick="javascript:fn_getUrl('P08010103');">반·출입 신청</button></span>
                    </div>
                </div>
                <!-- 버튼 끝 -->
            </div>
        </div>
        <c:import url="/eSecurity/insNet/Main/insNetQuick.jsp" charEncoding="utf-8"></c:import>
    </div>
</div>