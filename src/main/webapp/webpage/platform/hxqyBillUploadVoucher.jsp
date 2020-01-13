<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>应付账款管理--上传凭证</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		var validateForm;

	    // 添加全局站点信息
	    var UPLOAD_URL = '${ctx}/yfzk/web-uploadVoucher';
	    var SWF_URL = '${ctxStatic}/online/Uploader.swf';
	
		function doSubmit(){
			if(validateForm.form()){
				$(".upload_list:eq(1) li").each(function(){
					$("#urls").val($("#urls").val() + $(this).find("span:eq(0)").attr("url") + ",");
				});
				if($("#urls").val().length > 0){
					$("#urls").val($("#urls").val().substring(0, $("#urls").val().length - 1));
				}
            	$("#inputForm").submit();
            	return true;
            }
			return false;
		}
		
		function filedelete(obj){
			$.ajax({
	        	url: '${ctx}/yfzk/web-removeVoucher',
	        	async : true,
	    		cache : true,
	    		data : { "url" : $(obj).attr("url") },
	    		type: "post",
	    		dataType: "text",
	    		success: function (data) {
					if(data == "success"){
						layer.msg('删除凭证成功！', {shift: 6});
						$(obj).parent().remove();
						return;
					}
					layer.msg(data, {shift: 6});
		        },
		        error: function (request) {
		        	layer.msg('删除凭证失败！', {shift: 6});
		        }
			});
		}
		
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body style="background-color: #fff;">
	<form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/yfzk/submit-voucher" accept-charset="UTF-8" method="post">
		<div>
			<!--引入CSS-->
			<link rel="stylesheet" type="text/css" href="${ctxStatic}/online/webuploader.css?20171227">
			<!--引入JS-->
			<script type="text/javascript" src="${ctxStatic}/online/webuploader.js"></script>
			
			<!--WebUploader只包含文件上传的底层实现，不包括UI部分。所以这里直接借用官方演示的界面-->
			<link rel="stylesheet" type="text/css" href="${ctxStatic}/online/clientuploader.css">
			
			<input type="hidden" id="bill_id" name="billId.id" value="${bill_info.id}">
			<input type="hidden" id="voucher_type" name="type" value="${voucher_type}">
			<input type="hidden" id="urls" name="urls" value="" />
	
			<div class="upload_list clear" style="width:100%; overflow: hidden; padding:0 15px 0 15px;">
	  			<!--Webuploader Block-->
	  			<div class="page-container">
	    			<div id="uploader" class="fd-container">
	      				<div class="queueList">
	        				<div id="dndArea" class="placeholder">
	          					<div id="filePicker" class="webuploader-container">
	          						<div class="webuploader-pick">点击选择图片</div>
	          						<div id="rt_rt_1cmfptsku19317nb78s1okknoe1" style="position: absolute; top: 0px; left: 205.5px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;">
	          							<input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*" />
	          							<label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
	          						</div>
	          					</div>
	        				</div>
	      				</div>
	      				<div class="statusBar" style="display:none;">
				        	<div class="progress" style="display: none;">
				          		<span class="text">0%</span>
					          	<span class="percentage" style="width: 0%;"></span>
					        </div>
					        <div class="info">共0张（0B），已上传0张</div>
					        <div class="btns">
				          		<div id="filePicker2" class="webuploader-container"><div class="webuploader-pick">继续添加</div><div id="rt_rt_1cmfptsll1cgm91mqpf1bne16nr6" style="position: absolute; top: 0px; left: 0px; width: 38px; height: 2px; overflow: hidden; bottom: auto; right: auto;"><input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label></div></div>
				          		<div class="uploadBtn state-pedding">开始上传</div>
					        </div>
	      				</div>
	    			</div>
	  			</div>
	  			<!--Webuploader Block END-->
	  			<div class="clear"></div>
			</div>
			<div class="popup_title"><span></span>已上传凭证</div>
			<div class="upload_list clear" style="width:100%; height:280px;margin:10px 5px 13px 5px">
	  			<ul></ul>
	  			<div class="clear"></div>
			</div>
			<div class="clear"></div>
			
			<!--引入页面控制JS-->
			<script type="text/javascript" src="${ctxStatic}/online/clientuploader.js?v=1.0.8"></script>
		</div>
	</form>
</body>
</html>