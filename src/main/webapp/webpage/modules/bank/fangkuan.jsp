<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>银行--融资管理--放款</title>
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
	<form id="inputForm" name="inputForm" action="${ctx}/bank/fangqing" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		<input name="id" type="hidden" value="${financing_info.id}"/>
		<input name="state" type="hidden" value="9"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<div class="form-group">
					<label class="col-sm-3 control-label" style="text-align: left;margin-top: 10px;margin-bottom: 10px">放款金额(元)：<span class="text-warning">*</span></label>
					<div class="col-sm-6">
						<input id="loanAmount" name="loanAmount" placeholder="请输入放款金额" type="text" class="form-control required">
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="form-group">
				    <label class="col-sm-3 control-label" style="text-align: left;margin-bottom: 10px">上传放款凭证：<span class="text-warning">*</span></label>
				    <div class="col-sm-6">
			      		<input id="uploadFile" name="uploadFile" placeholder="请选择放款凭证" aria-required="true" type="file" class="form-control required">
				    </div>
				</div>
				<div class="hr-line-dashed"></div>
			</tbody>
		</table>   
	</form>
</body>
</html>