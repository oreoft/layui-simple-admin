<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增物品演示数据--${site.name}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <!-- 页面描述 -->
    <meta name="description" content="${site.description}"/>
    <!-- 页面关键词 -->
    <meta name="keywords" content="${site.keywords}"/>
    <!-- 网页作者 -->
    <meta name="author" content="${site.author}"/>
    <link rel="stylesheet" href="${staticUrl}/static/layui-v2.5.6/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${staticUrl}/static/css/main.css" media="all"/>
    <style type="text/css">
        .layui-form-item .layui-inline {
            width: 33.333%;
            float: left;
            margin-right: 0;
        }

        @media (max-width: 1240px) {
            .layui-form-item .layui-inline {
                width: 100%;
                float: none;
            }
        }

        .layui-form-item .role-box {
            position: relative;
        }

        .layui-form-item .role-box .jq-role-inline {
            height: 100%;
            overflow: auto;
        }

    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width:100%;">
    <div class="detail">
        <blockquote class="layui-elem-quote title" style="border-left:0px;">新增展示物品</blockquote>

        <div class="layui-form-item">
            <label class="layui-form-label layui-required"><em style="color:red;">*</em>物品名称</label>
            <div class="layui-input-block">
                <select name="name" id="name" lay-verify="required" lay-filter="name">
                    <option value="">请选择物品名称</option>
                    <#list materialDicList as materialDic>
                        <option value="${materialDic.name}">${materialDic.name}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label layui-required"><em style="color:red;">*</em>单位</label>
            <div class="layui-input-block">
                <select name="unit" id="unit" lay-verify="required" lay-filter="unit">
                    <option value="">请选择单位</option>
                    <#list materialDicList as materialDic>
                        <option value="${materialDic.unit}">${materialDic.unit}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <br>
            <br>
            <br>
            <br>
            <br>
            <p>防止写一些不干净的信息, 只能下拉选择体验哈~</p>
            <br>
            <br>
            <br>
            <br>
            <br>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label layui-required"><em style="color:red;">*</em>黄色预警值</label>
            <div class="layui-input-block">
                <input type="number" class="layui-input" lay-verify="required" id="lightWarningNum"
                       name="lightWarningNum" placeholder="请输入黄色预警值">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label layui-required"><em style="color:red;">*</em>红色预警值</label>
            <div class="layui-input-block">
                <input type="number" class="layui-input" lay-verify="required" id="heavyWarningNum"
                       name="heavyWarningNum" placeholder="请输入红色预警值">
            </div>
        </div>
    </div>

    <div class="box4">
        <button class="layui-btn layui-btn-sm" lay-submit="" lay-filter="commit">立即提交</button>
        <button type="reset"
                style="border:1px solid rgba(219,219,219,1); color:rgba(23,31,30,1); background:linear-gradient(180deg,rgba(255,255,255,1) 0%,rgba(232,232,232,1) 100%);"
                class="layui-btn layui-btn-primary layui-btn-sm">重置
        </button>
    </div>
</form>
<script type="text/javascript" src="${staticUrl}/static/layui-v2.5.6/layui.js"></script>
<script>
    layui.use(['form', 'jquery', 'layer'], function () {
        var form = layui.form,
            $ = layui.jquery,
            layer = layui.layer;

        form.on("submit(commit)", function (data) {
            console.log(data);
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            $.ajax({
                type: "POST",
                url: "${base}/mng/demo/add",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(data.field),
                success: function (res) {
                    layer.close(loadIndex);
                    if (res.success) {
                        parent.layer.msg("模拟物品添加成功!", {time: 200}, function () {
                            //刷新父页面
                            parent.location.reload();
                        });
                    } else {
                        layer.msg(res.message);
                    }
                }
            });
            return false;
        });

        // 下拉联动
        form.on('select(name)', function (data) {
            console.log('name选中结果' + JSON.stringify(data))
            $.getJSON("${base}/mng/demo/unit/list?name=" + data.value, function (data) {
                console.log('异步结果' + JSON.stringify(data))
                var optionstring = "";
                $.each(data.data, function (i, item) {
                    optionstring += "<option value=\"" + item + "\" >" + item + "</option>";
                });
                $("#unit").html('<option value=""></option>' + optionstring);
                // 重新渲染一下, 这个很重要
                form.render();
            });
        });


        // 悬浮提醒
        light_tip = 0;
        $('#lightWarningNum').hover(function () {
            light_tip = layui.layer.tips('库存若低于此值物品清单会高亮', this, {tips: [1, '#E2781B'], time: 30000});
        }, function () {
            layui.layer.close(light_tip);
        });

        heavy_tip = 0;
        $('#heavyWarningNum').hover(function () {
            heavy_tip = layui.layer.tips('库存低于此值不仅高亮并且每次出库会提醒管理员', this, {tips: [1, '#DF3708'], time: 30000});
        }, function () {
            layui.layer.close(heavy_tip);
        });

    });
</script>
</body>
</html>