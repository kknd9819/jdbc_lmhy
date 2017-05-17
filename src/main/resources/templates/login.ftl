<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>云生源登录后台</title>
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta name="author" content="生源科技" />
    <meta name="copyright" content="生源科技" />
    <link rel="stylesheet" href="/resources/css/login.css">
    <link rel="stylesheet" href="/resources/common/jqueryeasyui/themes/default/easyui.css">
    <script type="text/javascript" src="/resources/common/jquery/jquery.js"></script>
    <script type="text/javascript" src="/resources/common/jqueryeasyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/resources/common/jqueryeasyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/resources/common/login/jsbn.js"></script>
    <script type="text/javascript" src="/resources/common/login/prng4.js"></script>
    <script type="text/javascript" src="/resources/common/login/rng.js"></script>
    <script type="text/javascript" src="/resources/common/login/rsa.js"></script>
    <script type="text/javascript" src="/resources/common/login/base64.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/resources/js/login.js"></script>
</head>
<body>

<div class="top_div"></div>
<form id="loginForm" action="login" method="post">
    <div style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 250px; text-align: center;">
        <div style="width: 165px; height: 96px; position: absolute;">
            <div class="tou"></div>
            <div class="initial_left_hand" id="left_hand"></div>
            <div class="initial_right_hand" id="right_hand"></div></div>
        <p style="margin:10px 0 -20px 0;font-size:18px;font-weight:600;">云生源后台管理系统</p>
        <p style="padding: 30px 0px 10px; position: relative;">
            <span  class="u_logo"></span>
            <input id="username" name="username" class="ipt" type="text" placeholder="请输入用户名" value="" maxlength="20">
        </p>
        <p style="position: relative;"><span class="p_logo"></span>
            <input type="password" id="password" class="ipt" type="password" placeholder="请输入密码" value="" maxlength="20">
            <input type="hidden" id="enPassword" name="enPassword" />
        </p>
        <p style="padding:10px  0px 0px 0px; position: relative;">
            <img id="captchaImage" class="captchaImage" src="/admin/common/captchaImage" width="120" height="38" valign="bottom" style="float:right; margin-right:31px;">
            <span class="p_yanzheng"></span>
            <input id="captcha" name="captcha" class="ipt1" type="text" placeholder="请输入验证码" value="" maxlength="4">
        </p>
        <div style="height: 50ppx; height:px;x; line-height: 50px; margin-top: 20px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
            <p style="margin: 0px 35px 20px 45px;"><span style="float: left;"><input id="isRememberUsername" type="checkbox"  value="true" /> 记住用户名</span>
                <span style="float: right;">
              <a id="loginButton" style="background: rgb(0, 142, 173); padding: 7px 20px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"  href="javascript:;">登录</a>
           </span>
            </p>
        </div>

    </div>
</form>

</body>
</html>