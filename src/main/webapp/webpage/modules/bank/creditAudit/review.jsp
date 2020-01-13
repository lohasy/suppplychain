<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>银行--银行管理--授信复核</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	
</script>
</head>
<body>
	<form target="_top" name="mgmtform" id="mgmtform" class="form-horizontal" enctype="multipart/form-data" action="${ctx}/BankCreditReview/save2" accept-charset="UTF-8" method="post">
		<input type="hidden" name="supplier_enterpriseid" value="${supplier_enterprise.id}">
		<h4>额度利率</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">额度（元）：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="allQuota" class="form-control"
					placeholder="" value="${enterprise_params.allQuota}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">利率（%）：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="interestRate" class="form-control"
					placeholder="" value="${enterprise_params.interestRate}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">融资比例（%）：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="financingRatio" class="form-control"
					placeholder="" value="${enterprise_params.financingRatio}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<h4>融资账户</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">账户名：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="loanName" class="form-control"
					placeholder="" value="${enterprise_params.loanName}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">银行账号：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="loanAccount" class="form-control"
					placeholder="" value="${enterprise_params.loanAccount}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">开户行：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="loanOpenBank" class="form-control"
					placeholder="" value="${enterprise_params.loanOpenBank}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>


		<h4>回款账户</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">账户名：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="returnName" class="form-control"
					placeholder="" value="${enterprise_params.returnName}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">银行账号：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="returnAccount" class="form-control"
					placeholder="" value="${enterprise_params.returnAccount}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">开户行：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" name="returnOpenBank" class="form-control"
					placeholder="" value="${enterprise_params.returnOpenBank}" disabled="disabled">
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label"> </label>
			<div class="col-sm-9">
				<input type="text" name="remarks" class="form-control" style="width:234px;height: 50px" placeholder="备注：" value="${enterprise_params.returnOpenBank}">
		 		<br/> 
		 		<br/>
				<input type="submit" value="复核通过" name="state" class="btn btn-primary" style="width: 100px" /> 
				<input type="submit" value="复核不通过" name="state" class="btn btn-primary" style="width: 100px" />
			</div>
		</div>
	</form>
</body>
</html>