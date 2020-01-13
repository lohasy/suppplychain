<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>银行--融资管理--融资复核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
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
	<form:form id="inputForm" name="inputForm" modelAttribute="financing_info" action="${ctx}/bank/examine" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="form-group" style="margin-top: 30px;margin-left: 30px;">
	        <div class="col-sm-9">
	           <label class="check-inline">
	            <div class="iradio_square-green" style="position: relative;"><input value="8" id="state" name="state" class="i-checks" style="position: absolute; opacity: 0;" type="radio" checked="checked"/><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div>
	            	复核通过</label>
	          <label class="check-inline">
	            <div class="iradio_square-green" style="position: relative;"><input value="7" id="state" name="state" class="i-checks" style="position: absolute; opacity: 0;" type="radio" /><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div>
	            	复核不通过</label>
	        </div>
	      </div>
	</form:form>
</body>
</html>