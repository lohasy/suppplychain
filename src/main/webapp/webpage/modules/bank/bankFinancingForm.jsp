<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>银行--融资管理--融资配置</title>
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
			$("#value").focus();
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
	<form id="inputForm" name="inputForm" action="${ctx}/bank/save" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${financing_info.id}"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right">金额（元）：<span style="color: red;">*</span></label></td>
		         <td class="width-35" ><input id="totalAmount" name="totalAmount" maxlength="50" value="${financing_info.totalAmount}" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right">利率（%）：<span style="color: red;">*</span></label></td>
		         <td  class="width-35" ><input id="interestRate" name="interestRate" maxlength="50" value="${financing_info.interestRate}" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right">融资比例（%）：<span style="color: red;">*</span></label></td>
		         <td  class="width-35" ><input id="financingRatio" name="financingRatio" maxlength="50" value="${financing_info.financingRatio}" class="form-control required"/></td>
		      </tr>
		   </tbody>
	   </table>   
	</form>
</body>
</html>