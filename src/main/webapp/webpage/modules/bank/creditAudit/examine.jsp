<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>银行--银行管理--授信审核</title>
<meta name="decorator" content="default" />
</head>
<body>
	<form style="margin:15px 15px;" target="_top" name="inputForm" id="inputForm" class="form-horizontal" enctype="multipart/form-data" action="${ctx}/BankCreditAudit/save2" accept-charset="UTF-8" method="post">
		<input type="hidden" id="supplier_enterpriseid" name="supplier_enterpriseid" value="${id}" />
		<input type="hidden" id="base-url" value="${ctx}" />
		<h4>额度利率</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">额度（元）：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="allQuota" name="allQuota" class="form-control required" placeholder="请输入总额度" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">利率（%）：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="interestRate" name="interestRate" class="form-control required" placeholder="请输入利率" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">融资比例（%）：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="financingRatio" name="financingRatio" class="form-control required" placeholder="请输入融资比例" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<h4>融资账户</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">账户名：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="loanName" name="loanName" class="form-control required" placeholder="请输入融资账户名" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">银行账号：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="loanAccount" name="loanAccount" class="form-control required" placeholder="请输入融资银行卡号" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">开户行：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="loanOpenBank" name="loanOpenBank" class="form-control required" placeholder="请输入融资开户行" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>


		<h4>回款账户</h4>
		<div class="form-group">
			<label class="col-sm-3 control-label">账户名：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="returnName" name="returnName" class="form-control required" placeholder="请输入回款账户名" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">银行账号：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="returnAccount" name="returnAccount" class="form-control required" placeholder="请输入回款银行卡号" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label">开户行：<span
				class="text-warning">*</span>
			</label>

			<div class="col-sm-4">
				<input type="text" id="returnOpenBank" name="returnOpenBank" class="form-control required" placeholder="请输入回款开户行" value="" />
			</div>
		</div>
		<div class="hr-line-dashed"></div>

		<div class="form-group">
			<label class="col-sm-3 control-label"></label>

			<div class="col-sm-9">
				<input type="text" id="remarks" name="remarks" class="form-control" style="width: 234px; height: 50px" placeholder="备注：" value="" />
				<br/><br/> 
				<input type="button" value="审核通过" name="state" class="btn btn-primary Pass" style="width: 100px" /> 
				<input type="button" value="审核不通过" name="state" class="btn btn-primary noPass" style="width: 100px" />
				<script type="text/javascript">
					$(".noPass").click(function() {
						var data = {
							id : $("#supplier_enterpriseid").val(),
							state : $(".noPass").val(),
							remarks : $("#remarks").val()
						};
						$.ajax({
							url : "${ctx}/BankCreditAudit/save3",
							data : data,
							success : function(data) {
								layer.msg("授信审核不通过！");
								setTimeout(function(){
									parent.$("iframe[data-id='"+ $("#base-url").val() +"/BankCreditAudit/gys-index']")[0].contentWindow.location.reload();
									var frameindex = parent.layer.getFrameIndex(window.name);
									parent.layer.close(frameindex);
								}, 1500);
							}
						});
					});
					$(".Pass").click(function() {
						var data = {
							supplier_enterpriseid : $("#supplier_enterpriseid").val(),
							allQuota : $("#allQuota").val(),
							interestRate : $("#interestRate").val(),
							financingRatio : $("#financingRatio").val(),
							loanName : $("#loanName").val(),
							loanAccount : $("#loanAccount").val(),
							loanOpenBank : $("#loanOpenBank").val(),
							returnName : $("#returnName").val(),
							returnAccount : $("#returnAccount").val(),
							returnOpenBank : $("#returnOpenBank").val(),
							state : $(".Pass").val(),
							remarks : $("#remarks").val()
						};
										
						if(!data.allQuota){
							layer.msg("额度不能为空！");
							return;
						}
										
					 	var oInp = document.getElementById('allQuota');									    
				        if(isNaN(Number(oInp.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
				        	layer.msg('额度输入不正确！');
					        return;									     
					    }
										        
		            	if (!data.interestRate) {
		            		layer.msg("利率不能为空！");
					    	return;
			         	}
								  	
             	 		var oInp = document.getElementById('interestRate');									    
                        if(isNaN(Number(oInp.value))){ 
                        	layer.msg('利率输入不正确！');
	                        return;
			            }
											
                    	if (!data.financingRatio) {
                    		layer.msg("融资比例不能为空！");
			       			return;
			        	}
						           	
	             	 	var oInp = document.getElementById('financingRatio');									    
                        if(isNaN(Number(oInp.value))){ 
                        	layer.msg('融资比例输入不正确！');
	                        return;
			            } 
						               
                    	if (!data.loanName) {
                    		layer.msg("融资账户名不能为空！");
		             		return;
		           	   	}
						              	
		            	if (!data.loanAccount) {
		            		layer.msg("融资银行账号不能为空！");
							return;
						}
							            	
	             	 	var oInp = document.getElementById('loanAccount');									    
	                 	if(isNaN(Number(oInp.value))){ 
	                 		layer.msg('融资银行账号输入不正确！');
	                 		return;
				    	}
								                 								                 
			           	if (!data.loanOpenBank) {
			           		layer.msg("融资开户行不能为空！");
							return;
						}
							             
			           	if (!data.returnName) {
			           		layer.msg("回款账户名不能为空！");
							return;
						}
							             
		           		if (!data.returnAccount) {
		           			layer.msg("回款银行账号不能为空！");
							return;
						}
							             	
				   	 	var oInp = document.getElementById('returnAccount');									    
			            if(isNaN(Number(oInp.value))){ 
		            		layer.msg('回款银行账号输入不正确！');
				            return;
					    }
									               
				       	if (!data.returnOpenBank) {
			       			layer.msg("回款开户行不能为空！");
				    		return;
				    	}
									            
						$.ajax({
							url : "${ctx}/BankCreditAudit/save2",
							data : data,
							success : function(data) {
								layer.msg("授信审核通过！");
								setTimeout(function(){
									parent.$("iframe[data-id='"+ $("#base-url").val() +"/BankCreditAudit/gys-index']")[0].contentWindow.location.reload();
									var frameindex = parent.layer.getFrameIndex(window.name);
									parent.layer.close(frameindex);
								}, 1500);
							}
						});

					});
				</script>

			</div>
		</div>
	</form>
</body>
</html>