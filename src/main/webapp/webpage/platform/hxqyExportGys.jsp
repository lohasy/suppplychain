<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>核心企业--邀请供应商--导入供应商</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		var validateForm;
		function doSubmit(){
			if(validateForm.form()){
				$("#inputForm").submit();
				return true;
			}
			return false;
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
<body>
	<form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/gys/import" accept-charset="UTF-8" method="post" enctype="multipart/form-data">
		<div class="popup_dao clear" style="width: 100%; margin-top: 10px; height: 285px;">
	    	<div class="dao_nav">
	    		<span><a href="">下载EXCEL模板</a></span>
	    		
	    		<label></label>
	    		<span>填写供应商信息</span>
	    		<label></label>
	    		<span>上传EXCEL文档</span>
	    	</div>
	    	<div class="dao_an" style="margin-top: 50px;">
	    		<a href="${ctx}/gys/import-template"><img src="${ctxStatic}/images/icon_07.png"></a>
	    	</div>
	    	<div class="dao_an" style="margin-top: 20px;">
	    		<a href="javascript:void(0);" class="a-upload"><input type="file" id="file" name="file"><img src="${ctxStatic}/images/icon_08.png"></a>
	    	</div>
	    	<div class="dao_bot clear"><span id="importmsg"></span></div>
	    	<div class="dao_bot clear"></div>
	  	</div>
	</form>
</body>
</html>