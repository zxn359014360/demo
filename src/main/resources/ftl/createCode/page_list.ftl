${"<#include \"/common/layout/layout_list.html\"/>"}
${"<@layout_head_list>"}
<title xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml"
       xmlns:v-on="http://www.w3.org/1999/xhtml">系统配置</title>
${"</@layout_head_list>"}
${"<@layout_list>"}
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i>首页
    <span class="c-gray en">&gt;</span>管理员管理
    <span class="c-gray en">&gt;</span>系统配置
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<div class="page-container">
    <!--start  条件查询  -->
    <div class="text-c" id="form">
        <button onclick="removeIframe()" class="btn btn-primary radius" style="margin-right: 20px;">关闭选项卡</button>
        <input type="text" name="s_parameterName"  placeholder="参数名称" style="width:250px" class="input-text getData">
        <button name="submit" id="submit" onclick="submit()" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
    </div>
    <!--end  条件查询 -->

    <!--start 工具栏 -->
    <div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
			<a href="javascript:;" onclick="${lastPackageName}_${modelValue}_add('添加','${r'${ctx}'}/${lastPackageName}/${modelValue}/add','','510')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
		</span>
    </div>
    <!--end  工具栏 -->

    <!--start table列表 -->
    <div class="mt-20" id="table">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <#list modelItemList as var>
                   <th width="70">${var}</th>
                </#list>
                <th width="70">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr class="text-c" v-for="item in dataList">
                 <#list modelItemList as var>
                 <td >
                    {{item.${var}}}
                </td>
                </#list>
                <td class="f-14 td-manage">
                    <input class="btn btn-success-outline size-S radius"  type="button" v-on:click="${lastPackageName}_${modelValue}_edit('编辑','${r'${ctx}'}/${lastPackageName}/${modelValue}/edit?id=',item.id,'','510')" title="编辑"  value="编辑">
                    <input class="btn btn-success-outline size-S radius"  type="button" v-on:click="${lastPackageName}_${modelValue}_delete('删除','${r'${ctx}'}/${lastPackageName}/${modelValue}/delete?id=',item.id,'','510')" title="删除"  value="删除">
                </td>
            </tr>

            </tbody>
        </table>
        <div class="pageBox">
            <div class="page-text">共{{num}}条记录</div>
            <div class="page-text">共{{number}}页</div>
            <div class="goto">
                <input type="text" v-model='gotoNumber' id="gotoPage" class="gotoPage" />
            </div>
            <div id="page" class="pagination"></div>
        </div>
    </div>
    <!--end table列表 -->
</div>

<!--table.js-->
<script type="text/javascript">
    web.pageTable('${r'${ctx}'}/${lastPackageName}/${modelValue}/valList');
</script>

<!--其他.js-->
<script type="text/javascript">
    //JS监听某个DIV区域的回车事件
    $("#form").bind("keydown", function (e) {
        // 兼容FF和IE和Opera
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            //回车执行查询
            $("#search").click();
        }
    });

    /*管理员-系统配置-添加*/
    function ${lastPackageName}_${modelValue}_add(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*管理员-系统配置-编辑*/
    function ${lastPackageName}_${modelValue}_edit(title,url,param,w,h){
        layer_show(title,url+param,w,h);
    }
    /*管理员-系统配置-删除*/
    function ${lastPackageName}_${modelValue}_delete(title,url,param,w,h){
        $.ajax({
            url : url+param,
            type : "POST",
            data : {},
            success: function(data){
                layer.msg('删除成功!',{icon:1,time:1000},function(){
                    window.location.reload();
                });
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                layer.msg('error!',{icon:1,time:1000});
            }
        });
    }
    /*管理员-系统配置-刷新缓存*/
    function ${lastPackageName}_${modelValue}_refresh_cache(title,url,param,w,h){
        $.ajax({
            url : url+param,
            type : "POST",
            data : {},
            success: function(data){
                layer.msg('刷新缓存成功!',{icon:1,time:1000},function(){
                    // window.location.reload();
                });
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                layer.msg('error!',{icon:1,time:1000});
            }
        });
    }

</script>

</body>
${"</@layout_list>"}
