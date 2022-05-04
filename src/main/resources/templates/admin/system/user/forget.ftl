<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>忘记密码--开箱即用后台演示系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${staticUrl}/static/layui-v2.5.6/css/layui.css" media="all">
    <link rel="stylesheet" href="${staticUrl}/static/css/login.css?t=${.now?long}" media="all">
</head>

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login">
    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>开箱即用后台演示系统</h2>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">

                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone" for="LAY-user-login-cellphone"></label>
                    <input type="text" name="phone" id="LAY-user-login-cellphone" value = "${mobile!""}" lay-verify="phone" placeholder="请输入注册时的手机号" class="layui-input">
                </div>
                <div class="layui-form-item">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
                            <input type="text" name="vercode" id="LAY-user-login-vercode" lay-verify="required" placeholder="图形验证码" class="layui-input">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <img src="/genCaptcha" class="layadmin-user-login-codeimg" id="mycode">
                            </div>
                        </div>
                    </div>
                </div>
                <#--<div class="layui-form-item">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-smscode"></label>
                            <input type="text" name="vercode" id="LAY-user-login-smscode" lay-verify="required" placeholder="短信验证码" class="layui-input">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid" id="LAY-user-getsmscode">获取验证码</button>
                            </div>
                        </div>
                    </div>
                </div>-->

                <div class="layui-form-item" style="margin-bottom: 20px;">
                    <a  onclick="back();" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;color:#1ca8de;">返回</a>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="forget">找回密码</button>
                </div>
        </div>
    </div>

   <#-- <div class="layui-trans layadmin-user-login-footer">

        <p>© 2018 <a href="http://www.layui.com/" target="_blank">layui.com</a></p>
        <p>
            <span><a href="http://www.layui.com/admin/#get" target="_blank">获取授权</a></span>
            <span><a href="http://www.layui.com/admin/pro/" target="_blank">在线演示</a></span>
            <span><a href="http://www.layui.com/admin/" target="_blank">前往官网</a></span>
        </p>
    </div>-->

</div>

<script type="text/javascript" src="${staticUrl}/static/layui-v2.5.6/layui.js"></script>
<script>

    layui.use(['layer', 'form'], function() {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form;

        $("#mycode").on('click',function(){
            var t = Math.random();
            $("#mycode")[0].src="${base}/genCaptcha?t= "+t;
        });

        form.on('submit(forget)', function(data) {
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            $.ajax({
                type:"POST",
                url:"${base}/admin/system/user/subForget",
                dataType:"json",
                data:data.field,
                success:function(res){
                    layer.close(loadIndex);
                    if(res.code=="0"){
                        layer.msg(res.msg, {
                            offset: '15px'
                            ,icon: 1
                            ,time: 3000
                        }, function(){
                            location.href = "${base}/login"; //跳转到登入页
                        });

                    }else{
                        layer.msg(res.msg);
                        layer.close(loadIndex);
                        $("#mycode").click();
                    }
                }
            });

            return false;
        });
    });


    function back(){
        window.close();
    }
</script>
</body>
</html>