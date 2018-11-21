/**
 * jsapi
 * @author qiuyy
 */
var web = (function(){
	
	/**
	 * fileinput 图片上传，未上传服务器前展示客户端图片
	 * @desc
	 *    fileId
	 *    imgId
	 *    imgDivId
	 */
	var setSrc = function(option){
		var paramDefault = {
				fileId:"",//不加#号
				imgId:"",//图片的id
				imgDivId:"localImag"//图片的上层Div的id
		};
		var defaults = $.extend(paramDefault, option);
		var docObj=document.getElementById(defaults.fileId);
        var imgObjPreview=document.getElementById(defaults.imgId);
        var msg = checkImg(docObj.value);//图片格式验证	
		if(msg!=""){
			parent.alTit(msg);
			return;
		}
		//var imgObjPreview=$("#imgFile");
		if(docObj.files && docObj.files[0]){
			//火狐下，直接设img属性
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '200px';
			imgObjPreview.style.height = '360px';
			//imgObjPreview.src = docObj.files[0].getAsDataURL();
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		}else{
			//IE下，使用滤镜
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			var localImagId = document.getElementById(paramDefault.imgDivId);
			//必须设置初始大小
			localImagId.style.width = "200px";
			localImagId.style.height = "360px";
			//图片异常的捕捉，防止用户修改后缀来伪造图片
		try{
			localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		}catch(e){
			parent.alTit("您上传的图片格式不正确，请重新选择!");
			return false;
		}
			imgObjPreview.style.display = 'none';
			document.selection.empty();
		}
		return true;
	}
	//检查图片格式
	var checkImg = function(file){
		var array = new Array(".jpg",".jpeg", ".bmp",".gif",".png");
	    var msg = "";
	    if(!file){
			msg = "请从新上传所有图片，并确定所有图片不超过2M";
		}
		
		while (file.indexOf("\\") != -1){
			file = file.slice(file.indexOf("\\") + 1);
		}
		var ext = file.slice(file.lastIndexOf(".")).toLowerCase();
		var i = 0;
		for (; i < array.length; i++) {
			if (array[i] == ext){ 
				i = -1;
				break; 
			}
		}
		if(i!=-1){
			msg = "您上传的图片格式不正确，请重新选择!";
		}
		return msg;
	}


	/**
	 * 全选/反选
	 * @param
	 * option
	 * 		obj checkbox对象 注意指定$parent
	 * 		action 动作，true 全选 false 反选，默认值true 全选
	 * callback
	 *      回调方法
	 * @return checkbox的value 逗号隔开多个
	 */
	var xinCheckbox = function(option,callback){
		var paramDefault = {
			obj : '',
			action:true
    	};
		var defaults = $.extend(paramDefault, option);
		var cbs = $(defaults.obj);
		var falseCbs = $(defaults.obj+":not(:checked)");//未选中的checkboxs
		var trueCbs = $(defaults.obj+":checked");//选中的checkboxs
		if(defaults.action){
			//全选
			cbs.prop("checked",defaults.action);
		}else{
			//反选
			falseCbs.prop("checked",true);
			trueCbs.prop("checked",false);
		}
		var trueNewCbs = $(defaults.obj+":checked");//重新被选中的checkboxs
		if(callback){
			var vals = "";
			$.each(trueNewCbs,function(i,obj){
				if(i!==(trueNewCbs.length-1)){
					vals += obj.value+",";
				}else{
					vals += obj.value;
				}
			})
			callback(vals);
		}
	}

	/**
	 *  qrcode
	 *  二维码转码
	 */
	var XinUtf16to8 = function(str){
		  var out, i, len, c;
		  out = "";
		  len = str.length;
		  for (i = 0; i < len; i++) {
		    c = str.charCodeAt(i);
		    if ((c >= 0x0001) && (c <= 0x007F)) {
		        out += str.charAt(i);
		    } else if (c > 0x07FF) {
		        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
		        out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
		        out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		    } else {
		        out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
		        out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		    }
		  }
		  return out;
	}

	/**
	 * 列表数据加载
	 * @param url
	 * @param pageInit
	 * @version 20170619版本
	 */
	var pageLoad = function(option){
		var paramDefault = {
			columns:'',
			url:''
		};
		var defaults = $.extend(paramDefault, option);


		var columnsArray = defaults.columns.split(",");
		var columns = [];
		for(var i=0;i<columnsArray.length;i++){
			var c = columnsArray[i];
			var newC = {};
			newC.data = c;
			columns.push(newC);
		}

		$('.table-sort').dataTable({
			/*"aaSorting": [[ 0, "desc" ]],*/
			"bStateSave": true,//状态保存
			"pading":false,
			"aoColumnDefs": [
				/*{"orderable":false,"aTargets":[0]}// 不参与排序的列*/
			],
			serverSide: true,
			ajax: function (pageParams, callback, settings) {
				var params = {};
				params.end = pageParams.length;//页面显示记录条数
				params.start = pageParams.start;//最后一条数据index
				params.page = (pageParams.start / pageParams.length)+1;//当前页码
				var input = $("#searchForm input,select");
				for(var i = 0; i < input.length; i++){
					var name = $(input[i]).attr('name');
					var val =  $(input[i]).val();
					if(val && typeof(val)!="undefined" && name){
						params[name] = val;
					}
				}
				$.ajax({
					type: "POST",
					url: defaults.url,
					cache: false,  //禁用缓存
					data: params,
					success: function (result) {
						result = JSON.parse(result);
						var resData = JSON.parse(result.data);
						var returnData = {};
						returnData.recordsTotal = result.total;//返回数据全部记录
						returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
						returnData.data = resData;//返回的数据列表
						callback(returnData);//调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染(此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕)
					}
				});
			},
			columns: columns
			/*columns: [
				{ "data": "srName" }
			]*/
		}).api();
	}

	/**
	 * 列表数据加载
	 * @param url
	 * @param pageInit
	 * @version 20170629版本
	 */
	var pageCallBackFun = null;
	var pageTable = function(url,obj,extendObj){
		if(extendObj != null){
            pageCallBackFun = extendObj.pageCallBackFun;
		}

		var data1 = {
            dataList: [],
            number : 0,
            num:0,
            map : {},
            gotoNumber : 1,
            optionNum : 0,
            optionChecked : {}
        }
		if(obj != null){
			for(var i in obj){
                data1[i] = obj[i];
			}
		}
		window.tableVue = new Vue({
			el: '#table',
			data: data1
		});
		window.tableInit = function(data){
			tableVue.dataList  = data.list != null && data.list != '' ? JSON.parse(data.list) : [];
	
			if(typeof mergeCell === "function"){
				tableVue.dataList=mergeCell(tableVue.dataList);
			}
			tableVue.number  = data.totalPage != null && data.totalPage != '' ? JSON.parse(data.totalPage) : 0;
			tableVue.map  = data.map != null && data.map != '' ? JSON.parse(data.map) : {};
			tableVue.num  = data.totalNum != null && data.totalNum != '' ? JSON.parse(data.totalNum) : 0;
			tableVue.gotoNumber  = window.thisPage;
            tableVue.optionNum = 0;
            // tableVue.optionChecked = {};
		};
		window.common.page(function(pageIndex, type){
			var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
			var params = window.common.submitData('#form');
			params.courrentPage = pageIndex;//当前页码
			window.thisPage = pageIndex;
			window.common.getTableData(url,params,function(data){
				layer.close(index);
				data = JSON.parse(data);
                //回调函数
                if(pageCallBackFun != null){
                    pageCallBackFun(data);
				}
				window.tableInit(data);
				var totalPage = data.totalPage;
				$('#page').jqPaginator('option', {
					totalPages: totalPage
				});
			})
		});
		window.submit = function(page){
			var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
			var params = window.common.submitData("#form");
			var pages = 1;
			if(page != null){
				pages = page;
			}
			params.courrentPage = pages;
			window.thisPage = page;
			window.common.getTableData(url,params,function(data){
				layer.close(index);
				data = JSON.parse(data);
				//回调函数
                if(pageCallBackFun != null){
                    pageCallBackFun(data);
                }
				window.tableInit(data);
				var totalPage = data.totalPage;
				$('#page').jqPaginator('option', {
					currentPage : pages,
					totalPages: totalPage
				});
			})
		};
		window.isInteger = function(a) {
		    return /^(\d+)$/.test(a);
		};
		$('#gotoPage').bind('keydown',function(event){
		    if(event.keyCode == "13") {
		        var page = $(this).val();
		        if(!window.isInteger(page) || page > window.tableVue.number){
		        	layer.msg('只能输入小于总页数的正整数');
		        	return;
		        }
		        window.submit(parseInt(page,10));
		    }
		});  
	}
	
    return {
    	checkbox:xinCheckbox,
    	utf16to8:XinUtf16to8,
    	setSrc:setSrc,
		pageLoad:pageLoad,
		pageTable:pageTable
    };
    
}());
	
/**
 * 判断是否是json的参数
 * @param obj
 * @returns {boolean}
 */
function isJson(obj){
    return typeof(obj) == "object"
        && Object.prototype.toString.call(obj).toLowerCase() == "[object object]"
        && !obj.length;
}	


