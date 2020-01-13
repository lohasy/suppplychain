<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--供应商配置参数</title>
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
<body style="background-color: #fff; ">
	<div>	
		<!--主体区-->
		<div class="wrapper wrapper-content fadeInRight">
			<form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/gys/saveInfo" accept-charset="UTF-8" method="post">
				<h4>额度利率</h4>
	      		<div class="form-group">
	        		<label class="col-sm-3 control-label">额度（元）：</label>
			        <div class="col-sm-4">
		          		<input type="text" id="allQuota" name="allQuota" class="form-control" placeholder="" value="${enterprise_params.allQuota}" readonly="">
			        </div>
		      	</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<div class="form-group">
		        	<label class="col-sm-3 control-label">利率（%）：</label>
		        	<div class="col-sm-4">
		          		<input type="text" id="interestRate" name="interestRate" class="form-control" placeholder="" value="${enterprise_params.interestRate}" readonly="">
		        	</div>
		      	</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<div class="form-group">
		        	<label class="col-sm-3 control-label">融资比例（%）：</label>
		        	<div class="col-sm-4">
		          		<input type="text" id="financingRatio" name="loan_proportion" class="form-control" placeholder="" value="${enterprise_params.financingRatio}" readonly="">
		        	</div>
		      	</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<h4>融资账户</h4>
		      	<div class="form-group">
		        	<label class="col-sm-3 control-label">账户名：</label>
		        	<div class="col-sm-4">
		          		<input type="text" id="requisition_account_name" name="requisition_account_name" class="form-control" placeholder="" value="王旭" readonly="">
		        	</div>
		      	</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<div class="form-group">
		        	<label class="col-sm-3 control-label">银行账号：</label>
			        <div class="col-sm-4">
		          		<input type="text" id="loanAccount" name="loanAccount" class="form-control" placeholder="" value="${enterprise_params.loanAccount}" readonly="">
		        	</div>
	   			</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<div class="form-group">
		        	<label class="col-sm-3 control-label">开户行：</label>
			        <div class="col-sm-4">
		          		<input type="text" id="loanOpenBank" name="loanOpenBank" class="form-control" placeholder="" value="${enterprise_params.loanOpenBank }" readonly="">
			        </div>
		      	</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<h4>回款账户</h4>
		      	<div class="form-group">
		        	<label class="col-sm-3 control-label">账户名：</label>
			        <div class="col-sm-4">
		          		<input type="text" id="returnName" name="returnName" class="form-control" placeholder="" value="${enterprise_params.returnName }" readonly="">
			        </div>
	      		</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<div class="form-group">
			        <label class="col-sm-3 control-label">银行账号：</label>
			        <div class="col-sm-4">
			          <input type="text" id="returnAccount" name="returnAccount" class="form-control" placeholder="" value="${enterprise_params.returnAccount }" readonly="">
			        </div>
		      	</div>
		      	<div class="hr-line-dashed"></div>
	
		      	<div class="form-group">
		        	<label class="col-sm-3 control-label">开户行：</label>
			        <div class="col-sm-4">
		          		<input type="text" id="returnOpenBank" name="returnOpenBank" class="form-control" placeholder="" value="${enterprise_params.returnOpenBank }" readonly="">
			        </div>
		      	</div>
		      	<div class="hr-line-dashed"></div>
			</form>
		</div>
	</div>
</body>
</html>