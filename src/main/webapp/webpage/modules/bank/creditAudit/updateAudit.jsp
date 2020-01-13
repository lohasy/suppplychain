<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>银行--银行管理--授信配置</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/online/lgt-web.js"></script>
<script type="text/javascript">
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if($.isEmpty($("#allQuota").val())){
			layer.msg("额度不允许为空！");
			return false;
		}
		if($.isEmpty($("#interestRate").val())){
			layer.msg("利率不允许为空！");
			return false;
		}
		if($.isEmpty($("#financingRatio").val())){
			layer.msg("融资比例不允许为空！");
			return false;
		}
		if($.isEmpty($("#loanName").val())){
			layer.msg("融资账户名不允许为空！");
			return false;
		}
		if($.isEmpty($("#loanAccount").val())){
			layer.msg("融资银行账号不允许为空！");
			return false;
		}
		if($.isEmpty($("#loanOpenBank").val())){
			layer.msg("融资开户行为空！");
			return false;
		}
		if($.isEmpty($("#returnName").val())){
			layer.msg("回款账户名不允许为空！");
			return false;
		}
		if($.isEmpty($("#returnAccount").val())){
			layer.msg("回款银行账号允许为空！");
			return false;
		}
		if($.isEmpty($("#returnOpenBank").val())){
			layer.msg("回款开户行不允许为空！");
			return false;
		}
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
	<form style="margin:15px 15px;" name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/BankCreditAudit/updateAudit" accept-charset="UTF-8" method="post">
		<input type="hidden" id="supplierId" name="id" value="${supplier_enterprise.id}" />
		<input type="hidden" id="paramsId" name="paramsId.id" value="${supplier_enterprise.paramsId.id}" />
		<h4>额度利率</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">额度（元）：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="allQuota" name="paramsId.allQuota" class="form-control required" placeholder="请输入总额度" value="${supplier_enterprise.paramsId.allQuota}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">利率（%）：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="interestRate" name="paramsId.interestRate" class="form-control required" placeholder="请输入利率" value="${supplier_enterprise.paramsId.interestRate}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">融资比例（%）：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="financingRatio" name="paramsId.financingRatio" class="form-control required" placeholder="请输入融资比例" value="${supplier_enterprise.paramsId.financingRatio}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<h4>融资账户</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">账户名：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="loanName" name="paramsId.loanName" class="form-control required" placeholder="请输入融资账户名" value="${supplier_enterprise.paramsId.loanName}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">银行账号：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="loanAccount" name="paramsId.loanAccount" class="form-control required" placeholder="请输入融资银行卡号" value="${supplier_enterprise.paramsId.loanAccount}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">开户行：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="loanOpenBank" name="paramsId.loanOpenBank" class="form-control required" placeholder="请输入融资开户行" value="${supplier_enterprise.paramsId.loanOpenBank}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>


		<h4>回款账户</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">账户名：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="returnName" name="paramsId.returnName" class="form-control required" placeholder="请输入回款账户名" value="${supplier_enterprise.paramsId.returnName}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">银行账号：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="returnAccount" name="paramsId.returnAccount" class="form-control required" placeholder="请输入回款银行卡号" value="${supplier_enterprise.paramsId.returnAccount}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">开户行：<span class="text-warning">*</span></label>

			<div class="col-sm-4">
				<input type="text" id="returnOpenBank" name="paramsId.returnOpenBank" class="form-control required" placeholder="请输入回款开户行" value="${supplier_enterprise.paramsId.returnOpenBank}" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>
	</form>
</body>
</html>