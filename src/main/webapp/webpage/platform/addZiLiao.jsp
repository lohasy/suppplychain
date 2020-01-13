<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>编辑资料</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<script>
		var validateForm;
		function doSubmit(){
			if(!$.isEmpty($("#name").val()) && !$.isEmpty($("#file").val()) && validateForm.form()){
				$("#inputForm").submit();
            	return true;
			}else{
				layer.msg("请填写资料名称和上传资料文件！");
				return false;
			}
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
	<form:form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/ziliao/add" accept-charset="UTF-8" method="post" enctype="multipart/form-data">
		<div class="popup_dao clear" style="width: 100%; margin-top: 10px; height: 285px;">
			<input type="hidden"  name="id" value="${material_info.id }"/>
	    	<div class="dao_an" style="width: 300px; margin-top: 20px;">
	    		<li>资料名称：<input type="text" id="name" name="name" value="${material_info.name }" /></li>
	    		<li style="margin-top: 15px;">选择文件：<input style="width: 150px; display: inline;" type="file" id="file" name="file" /></li>
	    		<c:if test="${not empty material_info.url }">
	    			<li style="margin-top: 15px;"><a href="${material_info.url }">查看/下载资料</a></li>
	    		</c:if>
	    	</div>
	    	<div class="dao_bot clear"></div>
	  	</div>
	</form:form>
</body>
</html>