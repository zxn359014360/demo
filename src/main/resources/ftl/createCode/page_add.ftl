${"<#include \"/common/layout/layout_list.html\"/>"}
${"<@layout_head_list>"}
<title>管理账号</title>
${"</@layout_head_list>"}
${"<@layout_list>"}


<body>
<article class="page-container">
    <form class="form form-horizontal" id="form-admin-add">

         <#list modelItemList as var>
         <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>${var}：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="" placeholder="${var}" id="${var}" name="${var}">
            </div>
        </#list>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${r'${ctx}'}/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="${r'${ctx}'}/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="${r'${ctx}'}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
    $(function(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-admin-add").validate({
            rules:{
                slName:{
                    required:true,
                    minlength:1,
                    maxlength:16
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $(form).ajaxSubmit({
                    type: 'post',
                    url: "${r'${ctx}'}/${lastPackageName}/${modelValue}/save?oper=save" ,
                    success: function(data){
                        layer.msg('添加成功!',{icon:1,time:1000},function(){
                            //先得到当前iframe层的索引
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.submit();
                        });
                    },
                    error: function(XmlHttpRequest, textStatus, errorThrown){
                        layer.msg('error!',{icon:1,time:1000});
                    }
                });

            }
        });
    });
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
${"</@layout_list>"}