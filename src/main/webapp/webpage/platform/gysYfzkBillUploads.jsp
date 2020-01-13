<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--应付账款管理--批量上传应付账款</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		var validateForm;
	
		$(function () {
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
	
		function doSubmit(){
			if(validateForm.form()){
            	$("#inputForm").submit();
            	return true;
            }
			return false;
		}
	</script>
</head>
<body style="background-color: #fff;">
	<form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/yfzk/import?supplierParentId.id=${bill_info.supplierParentId.id}" accept-charset="UTF-8" enctype="multipart/form-data" method="post">
		<div>
			<input type="hidden" id="status" name="status" value="-1">
			<div class="upload_an clear" style="width:100%; margin-top: 30px; padding-left: 0px;">
  				<a href="javascript:void(0);" style="width: 220px; margin-left: 177px;" class="c-upload"><input type="file" id="importfile" name="file" />上传EXCEL</a>
			</div>
			<div class="upload_nei" style="width: 100%; text-align: center;">上传EXCEL前，先<a href="${ctx}/yfzk/import-template">下载EXCEL模板</a>，<br>根据下载的EXCEL模板填写数据。</div>
			<div class="upload_nei" style="width: 100%; text-align: center; height: 50px;"><span id="importmsg"></span></div>
			<div class="clear"></div>
 		</div>
	</form>
</body>
</html>