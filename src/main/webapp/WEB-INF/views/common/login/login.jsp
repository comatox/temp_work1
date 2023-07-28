<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>입주사-Security</title>
    <meta name="title" content="입주사-Security"/>
    <meta name="Author" content="SK hystec"/>
    <meta name="description" content="입주사-Security"/>
    <meta name="keywords" content="입주사-Security"/>
    <meta name="viewport"
          content=' initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0,width=device-width'/>
    <script type="text/javascript"
            src="/esecurity/assets/common/js/jquery/jquery-1.7.1.js"></script>
    <script type="text/JavaScript" src="/esecurity/assets/common/js/common.js"></script>
    <link rel="stylesheet" type="text/css" href="/esecurity/assets/common/css/layout_main.css"/>
    <link rel='stylesheet' type='text/css' media='screen'
          href='/esecurity/assets/common/css/layout_user_login.css'/>
    <script src="/esecurity/assets/common/js/esecurity.js" type="text/javascript"></script>
    <script src="/esecurity/assets/common/js/base64.js" type="text/javascript"></script>
    <style>
      #login .footer_1 {
        position: relative;
        float: right;
        width: 100%;
        margin: 0 0.5%;
        text-align: left;
        font-size: 13px;
        line-height: 25px;
        color: black;
      }

      #login .footer {
        position: relative;
        float: left;
        width: 40%;
        margin: 0 30%;
        text-align: center;
        font-size: 12px;
        opacity: 0.7;
        line-height: 50px;
      }
    </style>
    <script>
      $(document).ready(function () {
        var errorCode = "<%= request.getParameter("error")%>";
        if (errorCode) {
          setTimeout(function () {
            if (errorCode == "FAIL_ID") {
              alert("사용자 아이디가 등록되지 않았습니다.");
            } else if (errorCode == "FAIL_PASSWORD") {
              alert("사용자 비밀번호가 일치하지 않습니다.");
            } else if (errorCode == "FAIL_LOGINCOUNT") {
              alert("로그인 실패 횟수가 10회를 초과하여 로그인 하실 수 없습니다.");
            } else if (errorCode == "FAIL_LEAVE") {
              alert("로그인 할 수 없는 사용자입니다.");
            }
          }, 50);
        }
      })
    </script>
</head>
<body>
<div id="login">
    <div class="header">
        <h1 class="top_logo security">
        </h1>
    </div>
    <div class="body security" id="Content">
        <div class="wrap">
            <div class="copy">
                <strong>
                    e-Security, 입주사
                </strong>
                <span>Welcome to e-Security system</span>
                <span>협력사와 원활한 업무 협의를 위해 체계적인 보안시스템을 제공합니다</span>
            </div>
            <form name="eSecurityForm" id="eSecurityForm" method="post"
                  action="/esecurity/login/process">
                <fieldset class="login">
                    <legend>Member Login</legend>
                    <label for="id">사번</label> <br>
                    <input type="text" placeholder="아이디" id="id" name="id"/><br>
                    <label for="password">비밀번호</label><br>
                    <input type="password" id="password" name="password" title="비밀번호" value=""/>
                    <input type="submit" id="loginBtn" value="로그인"/>
                    <ul>
                        <li><input type="checkbox" name="id_save" id="id_save" class="border_none">아이디/비번
                            저장
                        </li>
                    </ul>
                    <div class="footer_1">
                        <span>☎ 문의전화 : 031-5185-2294</span>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="footWrap">
        <div id="footer">
            <div class="foot_area">
                <div class="footer_logo">SK hystec</div>
                <div class="fl">입주사 구성원을 위한 시스템으로서 인가된 분만 사용할 수 있습니다. <br/>본 화면은 해상도 1280*1024에 최적화
                    되어 있습니다. <br/> COPYRIGHT ⓒ SK쉴더스, ALL RIGHTS RESERVED.
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>