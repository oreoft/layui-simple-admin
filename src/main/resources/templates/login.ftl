<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>超简单开箱即用管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" href="http://myshares.oss-cn-hangzhou.aliyuncs.com/logo.png">
    <#--<link rel="stylesheet" href="${staticUrl}/static/layui-v2.5.6/css/layui.css" media="all">-->
    <link rel="stylesheet" href="${staticUrl}/static/layui-v2.5.6/css/layui.css" media="all">
    <link rel="stylesheet" href="${staticUrl}/static/css/login.css?t=${.now?long}" media="all"/>
    <!-- 解决session过期，登陆页面无法跳出ifream的问题 -->
    <script language="JavaScript">
        if (window != top)
            top.location.href = location.href;
    </script>
<body>

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <form class="layui-form" action="${base}/login/main" method="post">
        <div class="layadmin-user-login-main">
            <div class="layadmin-user-login-box layadmin-user-login-header">
                <h1>超简单开箱即用管理系统</h1>
            </div>
            <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                <div class="layui-form-item" style="margin-bottom:15px;">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-username"
                           for="LAY-user-login-username"></label>
                    <input type="text" name="username" id="LAY-user-login-username" lay-verify="required"
                           placeholder="请输入登陆名/手机号" class="layui-input">
                </div>
                <div class="layui-form-item" style="margin-bottom:15px;">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                           for="LAY-user-login-password"></label>
                    <input type="password" name="password" id="LAY-user-login-password" lay-verify="required"
                           placeholder="请输入密码" class="layui-input">
                </div>
                <div class="layui-form-item" style="margin-bottom:15px;">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"
                                   for="LAY-user-login-vercode"></label>
                            <input type="text" name="code" id="LAY-user-login-vercode" lay-verify="required"
                                   placeholder="图形验证码" class="layui-input">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <img src="/genCaptcha" class="layadmin-user-login-codeimg" id="mycode">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item" style="margin-bottom: 20px;">
                    <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
                    <div class="layui-unselect layui-form-checkbox" lay-skin="primary"><span>记住密码</span><i
                                class="layui-icon layui-icon-ok"></i></div>
<#--                    <a href="/mng/serviceProvider/register" class="layadmin-user-jump-change layadmin-link"-->
<#--                       style="margin-top: 7px;color:#1ca8de;">注册账号？</a>-->
                    <a onclick="forget();" class="layadmin-user-jump-change layadmin-link"
                       style="margin-top: 7px;color:#1ca8de;">忘记密码？</a>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 入</button>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="layui-trans layadmin-user-login-footer">
    <p>Copyright © 2022超简单开箱即用管理系统演示 沪ICP备20005227号 </p>
</div>

<script type="text/javascript" src="${staticUrl}/static/layui-v2.5.6/layui.js"></script>
<script type="text/javascript" src="${staticUrl}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticUrl}/static/js/jquery.bcat.bgswitcher.js"></script>
<script>
    layui.use(['layer', 'form'], function () {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form;

        $("#mycode").on('click', function () {
            var t = Math.random();
            $("#mycode")[0].src = "${base}/genCaptcha?t= " + t;
        });

        form.on('submit(login)', function (data) {
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            if ($('form').find('input[type="checkbox"]')[0].checked) {
                data.field.rememberMe = true;
            } else {
                data.field.rememberMe = false;
            }
            $.post(data.form.action, data.field, function (res) {
                layer.close(loadIndex);
                if (res.success) {
                    location.href = "${base}/" + res.data.url;
                } else {
                    layer.msg(res.message);
                    $("#mycode").click();
                }
            });
            return false;
        });
    });

    function forget() {
        layer.msg("这个功能等你来实现~");
        // var mobile = $("#LAY-user-login-username").val();
        <#--window.open("${base}/admin/system/user/forget?mobile=" + mobile);-->
    }
</script>
</body>
</html>