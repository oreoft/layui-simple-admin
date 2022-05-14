<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>演示列表--${site.name}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="shortcut icon" href="${site.logo}">
    <link rel="stylesheet" href="${staticUrl}/static/layui-v2.5.6/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${staticUrl}/static/css/user.css" media="all"/>
    <style>
        .detail-body {
            margin: 20px 0 0;
            color: #333;
            word-wrap: break-word;
        }
    </style>
</head>
<body class="childrenBody">
<div class="layui-tab layui-tab-brief" lay-filter="tab-all">
    <#--  这个是多tab 这个可以动态/固定/或者不写(现在是固定) -->
    <ul class="layui-tab-title">
        <li data-status="1" class="layui-this">演示列表</li>
    </ul>
    <div class="layui-tab-content">
        <#-- 这里是查询表单 -->
        <div class="layui-tab-item layui-show">
            <div class="layui-field-box">
                <form class="layui-form">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">物品名字</label>
                            <div class="layui-input-inline">
                                <label for="name">
                                    <input type="text" value="" id="name"
                                           name="name" placeholder="请输入名字名称"
                                           class="layui-input search_input">
                                </label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <a class="layui-btn layui-btn-sm" lay-submit="" id="searchForm"
                               lay-filter="searchForm">查询</a>
                        </div>
                        <div class="layui-inline">
                            <button type="reset" class="layui-btn layui-btn-primary layui-btn-sm">重置</button>
                        </div>
                        <i class="layui-icon layui-icon-down" id="btn-down"></i>
                    </div>
                    <div class="layui-form-item hide" id="hideItem">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="layui-form users_list">
    <#-- 这是表格, 一定要写-->
    <table class="layui-table" id="tableDemo" lay-filter="tableDemo"></table>
    <#-- 这是表格级别toolbar,可以做一些表格增强 -->
    <#if orgFlag == null>
        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <a type="button" class="layui-btn layui-btn-normal layui-btn-sm" lay-event="newMaterial">新增</a>
                <a type="button" class="layui-btn layui-btn-normal layui-btn-sm" lay-event="addMaterial">入库</a>
                <a type="button" class="layui-btn layui-btn-normal layui-btn-sm" lay-event="outMaterial">出库</a>
                <a type="button" class="layui-btn layui-btn-normal layui-btn-sm" lay-event="delMaterial">删除</a>
            </div>
        </script>
    </#if>

    <#-- 这是数据行toolbar, 可以做一些数据扩展功能 -->
    <script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
</div>
<div id="page"></div>
<script type="text/javascript" src="${staticUrl}/static/layui-v2.5.6/layui.js"></script>
<script type="text/javascript" src="${staticUrl}/static/js/tools.js"></script>
<script type="text/javascript" src="${staticUrl2}/DataTableExtend.js"></script>
<script>
    layui.use(['layer', 'form', 'table', 'util', 'element'], function () {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            element = layui.element,
            util = layui.util;

        function getList(status) {
            t1.toolbar = '#toolbarDemo';
            table.render(t1);
        }

        t1 = {
            elem: '#tableDemo',
            url: '${base}/mng/demo/list',
            id: 'tableDemo',
            method: 'post',
            defaultToolbar: ['print', 'exports'],
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                groups: 5, //只显示 1 个连续页码
                first: "首页", //显示首页
                last: "尾页", //显示尾页
                limits: [3, 10, 20, 1000]
            },
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {title: '序号', field: 'index', type: 'numbers', width: '5%', fixed: 'left'},
                {field: 'name', title: '物品名称', width: '18%'},
                {field: 'unit', title: '单位', width: '15%'},
                {
                    // 这是使用表格数据, 进行动态渲染, res里面的数据来更改表格的颜色
                    field: 'num', sort: true, title: '当前库存', templet: function (res) {
                        if (res.num <= res.heavyWarningNum) {
                            return '<div style="color: #DF3708">' + res.num + '</div>';
                        } else if(res.num <= res.lightWarningNum) {
                            return '<div style="color: #E2781B">' + res.num + '</div>';
                        } else {
                            return res.num;
                        }
                    }, width: '15%'
                },
                {field: 'mtime', title: '最新操作时间'},
            ]]
        };

        /**
         * 初始化进来table.render方法, 可以直接调用
         * 但是你有多个table的话, 就需要动态传参然后render
         */
        getList(1);

        /**
         * 这就是动态传参render方法
         */
        element.on('tab(tab-all)', function (data) {
            var status = $(this).attr('data-status');
            getList(status);
        });

        /**
         * tableDemo的监听方法
         */
        table.on('toolbar(tableDemo)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                , data = checkStatus.data;

            switch (obj.event) {
                case 'newMaterial':
                    layer.open({
                        title: "新增物品",
                        type: 2,
                        area: ['50%', '70%'],
                        content: "${base}/mng/demo/add",
                        success: function (layero, addIndex) {
                            setTimeout(function () {
                                layer.tips('点击此处返回清单列表', '.layui-layer-setwin .layui-layer-close', {
                                    tips: 3
                                });
                            }, 500);
                        }
                    });
                    break;
                case 'addMaterial':
                    layer.msg("还没写好, 嘿嘿~", {time: 800})
                    <#--if (data.length > 0) {-->
                    <#--    if (data.length > 1) {-->
                    <#--        layer.msg("每次只能入库一个物品哦~", {time: 800});-->
                    <#--        return-->
                    <#--    }-->
                    <#--    layer.open({-->
                    <#--        title: "物品入库",-->
                    <#--        type: 2,-->
                    <#--        area: ['50%', '60%'],-->
                    <#--        content: "${base}/mng/demo/input?id=" + data[0].id,-->
                    <#--        success: function (layero, backFactoryIndex) {-->
                    <#--            setTimeout(function () {-->
                    <#--                layer.tips('点击此处返回物品清单', '.layui-layer-setwin .layui-layer-close', {-->
                    <#--                    tips: 3-->
                    <#--                });-->
                    <#--            }, 500);-->
                    <#--        }-->
                    <#--    });-->
                    <#--} else {-->
                    <#--    layer.msg("你要勾选物品才能入库哦", {time: 800});-->
                    <#--}-->
                    break;
                case 'outMaterial':
                    layer.msg("还没写好, 嘿嘿~", {time: 800})
                    <#--if (data.length > 0) {-->
                    <#--    if (data.length > 1) {-->
                    <#--        layer.msg("每次只能出库一个物品哦~", {time: 800});-->
                    <#--        return-->
                    <#--    }-->
                    <#--    layer.open({-->
                    <#--        title: "物品出库",-->
                    <#--        type: 2,-->
                    <#--        area: ['50%', '60%'],-->
                    <#--        content: "${base}/mng/demo/output?id=" + data[0].id,-->
                    <#--        success: function (layero, backFactoryIndex) {-->
                    <#--            setTimeout(function () {-->
                    <#--                layer.tips('点击此处返回物品清单', '.layui-layer-setwin .layui-layer-close', {-->
                    <#--                    tips: 3-->
                    <#--                });-->
                    <#--            }, 500);-->
                    <#--        }-->
                    <#--    });-->
                    <#--} else {-->
                    <#--    layer.msg("你要勾选物品才能出库哦", {time: 800});-->
                    <#--}-->
                    break;
                case 'delMaterial':
                    if (data.length > 0) {
                        if (data.length > 1) {
                            layer.msg("每次只能删除一个物品哦~", {time: 800});
                            return
                        }
                        layer.confirm("确定删除吗?", {btn: ['恩,我确定', '我再想想']},
                            function () {
                                $.post("${base}/mng/demo/del", {"id": data[0].id}, function (res) {
                                    if (res.success) {
                                        layer.msg("删除成功~", {time: 800}, function () {
                                            table.reload('tableDemo', t1);
                                        });
                                    } else {
                                        layer.msg(res.message);
                                    }

                                });
                            }
                        )
                    } else {
                        layer.msg("你要勾选物品才能删除哦", {time: 800});
                    }
                    break;
            }
        });

        // 搜索
        form.on("submit(searchForm)", function (data) {
            t1.where = data.field;
            table.reload('tableDemo', t1);
        });

        // 支持回车搜索
        $('#name').on('keydown', function (event) {
            if (event.keyCode === 13) {
                $("#searchForm").trigger("click");
                return false
            }
        });


        // 筛选栏折叠展开
        let hide = true;
        $("#btn-down").click(function () {
            if (hide) {
                $("#btn-down").removeClass("layui-icon-down").addClass("layui-icon-up");
                $("#hideItem").removeClass("hide");
                hide = false;
            } else {
                $("#btn-down").removeClass("layui-icon-up").addClass("layui-icon-down");
                $("#hideItem").addClass("hide");
                hide = true;
            }
        })

        /**
         * 表格行点击事件
         * 这个可以让你的表格支持点击任意选中数据,并且互斥
         * @type {boolean}
         */
        var isRadio = true; // 是否单选
        table.on('row(tableDemo)', function (obj) {
            let flag = !obj.tr.find(':checkbox:first').prop('checked');
            // obj.tr.find(':checkbox').prop('checked', flag);
            if (flag) {
                // 未选中
                if (isRadio) $('.layui-table').find('.layui-form-checkbox').removeClass('layui-form-checked');
                obj.tr.find('.layui-form-checkbox').addClass('layui-form-checked');
                if (isRadio) $('.layui-table').find('input').prop('checked', false)
                obj.tr.find(':checkbox:first').prop('checked', flag);
            } else {
                obj.tr.find('.layui-form-checkbox').removeClass('layui-form-checked');
                obj.tr.find(':checkbox:first').prop('checked', flag);
            }
            // 修改缓存数据(layui操作缓存数据)
            layui.each(table.cache.tableDemo, function (i, l) {
                if (isRadio) l.LAY_CHECKED = false;
                if (obj.tr.index() === l.LAY_TABLE_INDEX) {
                    l.LAY_CHECKED = flag;
                }
            });
        });

        /**
         * 弹窗以后进行页面刷新
         */
        window.tableReload = function () {
            layer.closeAll();
            table.reload('tableDemo', t1);
        };

    });
</script>
</body>
</html>