$().ready( function() {
    var $loginForm = $("#loginForm");
    var $enPassword = $("#enPassword");
    var $username = $("#username");
    var $password = $("#password");
    var $captcha = $("#captcha");
    var $captchaImage = $("#captchaImage");
    var $isRememberUsername = $("#isRememberUsername");

    // 记住用户名
    var $adminUsername = $.cookie('adminUsername');
    if( $adminUsername != null && $adminUsername != "null" ) {
        $isRememberUsername.prop("checked", true);
        $username.val($adminUsername);
        $password.focus();
    } else {
        $isRememberUsername.prop("checked", false);
        $username.focus();
    }

    // 更换验证码
    $captchaImage.click( function() {
        $captchaImage.attr("src", "/admin/common/captchaImage?timestamp=" + (new Date()).valueOf());
    });

    // 表单验证、记住用户名
    $('#loginButton').bind('click',function(){
        if ($username.val() == "") {
            $.messager.alert('登录','请输入您的用户名！','warning');
            return false;
        }
        if ($password.val() == "") {
            $.messager.alert('登录','请输入您的密码！','warning');
            return false;
        }
        if ($captcha.val() == "") {
            $.messager.alert('登录','请输入您的验证码！','warning');
            return false;
        }

        if ($isRememberUsername.prop("checked")) {
            $.cookie('adminUsername', $username.val(), {expires: 7 * 24 * 60 * 60});
        } else {
            $.cookie('adminUsername',null);
        }

        var modulus = null;
        var exponent = null;
        $.ajax({
            url : "/admin/common/public_key",
            type : "GET",
            async: false,
            dataType : "json",
            cache : false,
            success : function(data) {
                modulus = data.modulus;
                exponent = data.exponent;
            }
        });

        var rsaKey = new RSAKey();
        rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));
        var enpassword = hex2b64(rsaKey.encrypt($password.val()));
        $enPassword.val(enpassword);
        $loginForm.submit();
    });

    $("body").keydown(function(event) {
        if (event.keyCode == "13") {
            $('#loginButton').click();
        }
    });
    $(window).keydown(function (e) {
        if (e.which == 13) {
            return false;
        }
    });

    //得到焦点
    $("#password").focus(function(){
        $("#left_hand").animate({
            left: "150",
            top: " -65"
        },{step: function(){
            if(parseInt($("#left_hand").css("left"))>140){
                $("#left_hand").attr("class","left_hand");
            }
        }}, 2000);
        $("#right_hand").animate({
            right: "-63",
            top: "-65px"
        },{step: function(){
            if(parseInt($("#right_hand").css("right"))> -70){
                $("#right_hand").attr("class","right_hand");
            }
        }}, 2000);
    });
    //失去焦点
    $("#password").blur(function(){
        $("#left_hand").attr("class","initial_left_hand");
        $("#left_hand").attr("style","left:112px;top:-12px;");
        $("#right_hand").attr("class","initial_right_hand");
        $("#right_hand").attr("style","right:-92px;top:-12px");
    });


});